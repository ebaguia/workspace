/**
 * 
 */
package org.auckland.ac.nz.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author ebag753
 *
 */
public class VIDIVOXFrameRate extends VIDIVOXAudioVideoOperation {
    
    private JButton btnChangeFrameRate;
    
    private JButton btnCancel;
    
    public VIDIVOXFrameRate(JFrame mainFrame, 
                                 File mediaFile) {
        super(mainFrame, mediaFile, 3);
        logger.logInfo("VIDIVOXFrameRate::VIDIVOXFrameRate()");
        super.initialize();
        initialize();
    }
    
    @Override
    public void initialize() {
        logger.logInfo("VIDIVOXFrameRate::initialize()");
        btnChangeFrameRate = new JButton();
        btnChangeFrameRate.setBounds(10, 10, 105, 30);
        btnChangeFrameRate.setText("Change");
        btnChangeFrameRate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String inputFilename = settingsTable.getValueAt(1, 1).toString();;
                String outputFilename = settingsTable.getValueAt(2, 1).toString();;
                boolean proceedWithOperation = true;
                
                logger.logInfo("VIDIVOXFrameRate::btnChangeFrameRate.addActionListener() inputFilename= " + inputFilename);
                logger.logInfo("VIDIVOXFrameRate::btnChangeFrameRate.addActionListener() outputFilename= " + outputFilename);

                // Check the existence of the outputfile in order not to overwrite
                //
                File tempOutputFile = new File(outputFilename);
                if(tempOutputFile.exists() && !tempOutputFile.isDirectory()) {
                    int result = JOptionPane.showConfirmDialog(mainFrame,
                            "The file" + outputFilename + " exists, overwrite?",
                            "Existing file",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    switch(result){
                        case JOptionPane.YES_OPTION:
                            // Let's delete the existing one and replace with the new one
                            //
                            try {
                                tempOutputFile.delete();
                            } catch(SecurityException e) {
                                logger.logError("VIDIVOXFrameRate::btnChangeFrameRate::actionPerformed(): ERROR overwriting " + tempOutputFile.getAbsolutePath());
                                JOptionPane.showMessageDialog(mainFrame, 
                                        "There was a problem generating the audio file. Please check the logs.", 
                                        "VIDIVOX", 
                                        JOptionPane.ERROR_MESSAGE);
                                proceedWithOperation = false;
                            }
                            break;
                        case JOptionPane.NO_OPTION:
                            // do nothing
                            //
                        case JOptionPane.CLOSED_OPTION:
                        case JOptionPane.CANCEL_OPTION:
                            proceedWithOperation = false;
                            break;
                    }
                }
                
                if(proceedWithOperation) {
                    try {
                        logger.logInfo("VIDIVOXFrameRate::btnChangeFrameRate::actionPerformed():changeFrameRate = " + settingsTable.getValueAt(0, 1).toString());
                        logger.logInfo("VIDIVOXFrameRate::btnChangeFrameRate::actionPerformed():inputFilename = " + inputFilename);
                        logger.logInfo("VIDIVOXFrameRate::btnChangeFrameRate::actionPerformed():outputFilename = " + outputFilename);
                    
                        List<String> frameRateCommand = new ArrayList<String>();
                        frameRateCommand.add(VIDIVOXCommonInternals.FFMPEG_BIN);
                        frameRateCommand.add("-i");
                        frameRateCommand.add(inputFilename);
                        frameRateCommand.add("-r");
                        frameRateCommand.add(outputFilename);
                        
                        processBuilder = new VIDIVOXProcessBuilder(frameRateCommand);
                        if(processBuilder.startAndWait() == 0) {
                            logger.logInfo("VIDIVOXFrameRate::btnChangeFrameRate::actionPerformed(): DONE changing frame rate: " + inputFilename);
                            
                            // Display the command output
                            //
                            outputStreamTextArea.setText(processBuilder.getProcessOutput());
                        }
                        else {
                            throw new Exception("VIDIVOXFrameRate::btnChangeFrameRate::actionPerformed(): ERROR changing frame rate: " + inputFilename);
                        }
                        
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    } catch (ParseException e) {
                        logger.logError("VIDIVOXFrameRate::btnChangeFrameRate::actionPerformed(): " + e.getMessage());
                        JOptionPane.showMessageDialog(mainFrame, 
                                "Length/From format should be [HH:mm:ss]!", 
                                "VIDIVOX", 
                                JOptionPane.ERROR_MESSAGE);
                    } catch(Exception e) {
                        logger.logError("VIDIVOXFrameRate::btnChangeFrameRate::actionPerformed(): ERROR changing frame rate: " + inputFilename);
                        JOptionPane.showMessageDialog(mainFrame, 
                                "There was a problem generating the audio file. Please check the logs.", 
                                "VIDIVOX", 
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    logger.logInfo("VIDIVOXFrameRate::btnChangeFrameRate::actionPerformed() Output file exists and user cancels the operation.");
                }
            }
        });
        
        btnCancel = new JButton();
        btnCancel.setBounds(10, 50, 105, 30);
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearFrameRateSettings();
                frameRateSettings();
            }
        });
        
        buttonsPanel.add(btnChangeFrameRate);
        buttonsPanel.add(btnCancel);
        
        setComponentsVisible(false);
        buttonsPanel.setVisible(true);
        outputScrollPane.setVisible(true);
    }
    
    public void setComponentsVisible(boolean aFlag) {
        btnChangeFrameRate.setVisible(aFlag);
        btnCancel.setVisible(aFlag);
        settingsTable.setVisible(aFlag);
        buttonsPanel.setVisible(aFlag);
        outputScrollPane.setVisible(aFlag);
    }
    
    public void setComponentsEnabled(boolean bEnabled) {
        btnChangeFrameRate.setEnabled(bEnabled);
        btnCancel.setEnabled(bEnabled);
        settingsTable.setEnabled(bEnabled);
        buttonsPanel.setEnabled(bEnabled);
        outputScrollPane.setVisible(bEnabled);
    }
    
    public void setMediaFile(File mediaFile) {
        this.mediaFile = mediaFile;
    }
    
    public void clearFrameRateSettings() {
        logger.logInfo("VIDIVOXFrameRate::clearFrameRateSettings()");
        if(((VIDIVOXSettingsTableModel)settingsTable.getModel()).getRowCount() > 0) {
            ((VIDIVOXSettingsTableModel)settingsTable.getModel()).setRowCount(0);
            ((VIDIVOXSettingsTableModel)settingsTable.getModel()).setRowCount(4);
        }
    }
    
    public void frameRateSettings() {
        logger.logInfo("VIDIVOXFrameRate::frameRateSettings()");
        // First column
        //
        settingsTable.setValueAt("Rate in seconds (default:5)", 0, 0);
        settingsTable.setValueAt("Input filename", 1, 0);
        settingsTable.setValueAt("Ouput filename", 2, 0);
        
        settingsTable.setValueAt("5", 0, 1);
        
        try {
            // Retrieve the input file
            //
            settingsTable.setValueAt(mediaFile.getCanonicalPath(),1, 1);
            
            // Let us create new output file
            //
            int extensionNameIndex = mediaFile.getCanonicalPath().lastIndexOf(".");
            String extensionName = mediaFile.getCanonicalPath().substring(extensionNameIndex);
            String newOutputFileName = mediaFile.getCanonicalPath().substring(0, extensionNameIndex);
            settingsTable.setValueAt(newOutputFileName + "_new" + extensionName,2, 1);
        } catch (IOException e) {
            logger.logError(e.getMessage());
        }
    }

}
