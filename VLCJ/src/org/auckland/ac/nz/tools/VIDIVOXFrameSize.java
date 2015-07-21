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
public class VIDIVOXFrameSize extends VIDIVOXAudioVideoOperation {
    
    private JButton btnChangeFrameSize;
    
    private JButton btnCancel;
    
    public VIDIVOXFrameSize(JFrame mainFrame, 
                                 File mediaFile) {
        super(mainFrame, mediaFile, 4);
        logger.logInfo("VIDIVOXFrameSize::VIDIVOXFrameSize()");
        super.initialize();
        initialize();
    }
    
    @Override
    public void initialize() {
        logger.logInfo("VIDIVOXFrameSize::initialize()");
        btnChangeFrameSize = new JButton();
        btnChangeFrameSize.setBounds(10, 10, 105, 30);
        btnChangeFrameSize.setText("Change");
        btnChangeFrameSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int pixelWidth = Integer.parseInt(settingsTable.getValueAt(0, 1).toString());
                int pixelHeight = Integer.parseInt(settingsTable.getValueAt(1, 1).toString());;
                String inputFilename = settingsTable.getValueAt(2, 1).toString();
                String outputFilename = settingsTable.getValueAt(3, 1).toString();;
                boolean proceedWithOperation = true;
                
                logger.logInfo("VIDIVOXFrameSize::btnChangeFrameSize.addActionListener() inputFilename= " + inputFilename);
                logger.logInfo("VIDIVOXFrameSize::btnChangeFrameSize.addActionListener() outputFilename= " + outputFilename);

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
                                logger.logError("VIDIVOXFrameSize::btnChangeFrameSize::actionPerformed(): ERROR overwriting " + tempOutputFile.getAbsolutePath());
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
                        logger.logInfo("VIDIVOXFrameSize::btnChangeFrameSize::actionPerformed():pixelWidth = " + pixelWidth);
                        logger.logInfo("VIDIVOXFrameSize::btnChangeFrameSize::actionPerformed():pixelHeight = " + pixelHeight);
                        logger.logInfo("VIDIVOXFrameSize::btnChangeFrameSize::actionPerformed():inputFilename = " + inputFilename);
                        logger.logInfo("VIDIVOXFrameSize::btnChangeFrameSize::actionPerformed():outputFilename = " + outputFilename);
                    
                        List<String> frameSizeCommand = new ArrayList<String>();
                        frameSizeCommand.add(VIDIVOXCommonInternals.FFMPEG_BIN);
                        frameSizeCommand.add("-i");
                        frameSizeCommand.add(inputFilename);
                        frameSizeCommand.add("-s");
                        frameSizeCommand.add(pixelWidth + "x" + pixelHeight);
                        frameSizeCommand.add(outputFilename);
                        
                        outputStreamTextArea.setText("Changing frame size. Please wait...\n");
                        processBuilder = new VIDIVOXProcessBuilder(frameSizeCommand);
                        if(processBuilder.startAndWait() == 0) {
                            logger.logInfo("VIDIVOXFrameSize::btnChangeFrameSize::actionPerformed(): DONE changing frame size: " + inputFilename);
                            
                            // Display the command output
                            //
                            outputStreamTextArea.setText(null);
                            outputStreamTextArea.setText(processBuilder.getProcessOutput());
                        }
                        else {
                            outputStreamTextArea.setText("ERROR changing frame size. Please check the log file.");
                            throw new Exception("VIDIVOXFrameSize::btnChangeFrameSize::actionPerformed(): ERROR changing frame size: " + inputFilename);
                        }
                        
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    } catch (ParseException e) {
                        logger.logError("VIDIVOXFrameSize::btnChangeFrameSize::actionPerformed(): " + e.getMessage());
                        JOptionPane.showMessageDialog(mainFrame, 
                                "Length/From format should be [HH:mm:ss]!", 
                                "VIDIVOX", 
                                JOptionPane.ERROR_MESSAGE);
                    } catch(Exception e) {
                        logger.logError("VIDIVOXFrameSize::btnChangeFrameSize::actionPerformed(): ERROR changing frame size: " + inputFilename);
                        JOptionPane.showMessageDialog(mainFrame, 
                                "There was a problem generating the audio file. Please check the logs.", 
                                "VIDIVOX", 
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    logger.logInfo("VIDIVOXFrameSize::btnChangeFrameSize::actionPerformed() Output file exists and user cancels the operation.");
                }
            }
        });
        
        btnCancel = new JButton();
        btnCancel.setBounds(10, 50, 105, 30);
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearFrameSizeSettings();
                frameSizeSettings();
            }
        });
        
        buttonsPanel.add(btnChangeFrameSize);
        buttonsPanel.add(btnCancel);
        
        setComponentsVisible(false);
        buttonsPanel.setVisible(true);
        outputScrollPane.setVisible(true);
    }
    
    public void setComponentsVisible(boolean aFlag) {
        btnChangeFrameSize.setVisible(aFlag);
        btnCancel.setVisible(aFlag);
        settingsTable.setVisible(aFlag);
        buttonsPanel.setVisible(aFlag);
        outputScrollPane.setVisible(aFlag);
    }
    
    public void setComponentsEnabled(boolean bEnabled) {
        btnChangeFrameSize.setEnabled(bEnabled);
        btnCancel.setEnabled(bEnabled);
        settingsTable.setEnabled(bEnabled);
        buttonsPanel.setEnabled(bEnabled);
        outputScrollPane.setEnabled(bEnabled);
    }
    
    public void setMediaFile(File mediaFile) {
        this.mediaFile = mediaFile;
    }
    
    public void clearFrameSizeSettings() {
        logger.logInfo("VIDIVOXFrameSize::clearframeSizeSettings()");
        if(((VIDIVOXSettingsTableModel)settingsTable.getModel()).getRowCount() > 0) {
            ((VIDIVOXSettingsTableModel)settingsTable.getModel()).setRowCount(0);
            ((VIDIVOXSettingsTableModel)settingsTable.getModel()).setRowCount(4);
        }
    }
    
    public void frameSizeSettings() {
        logger.logInfo("VIDIVOXFrameSize::frameSizeSettings()");
        // First column
        //
        settingsTable.setValueAt("Width in pixels", 0, 0);
        settingsTable.setValueAt("Height in pixels", 1, 0);
        settingsTable.setValueAt("Input filename", 2, 0);
        settingsTable.setValueAt("Ouput filename", 3, 0);
        
        settingsTable.setValueAt("320", 0, 1);
        settingsTable.setValueAt("240", 1, 1);
        
        try {
            // Retrieve the input file
            //
            settingsTable.setValueAt(mediaFile.getCanonicalPath(),2, 1);
            
            // Let us create new output file
            //
            int extensionNameIndex = mediaFile.getCanonicalPath().lastIndexOf(".");
            String extensionName = mediaFile.getCanonicalPath().substring(extensionNameIndex);
            String newOutputFileName = mediaFile.getCanonicalPath().substring(0, extensionNameIndex);
            settingsTable.setValueAt(newOutputFileName + "_new" + extensionName,3, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
