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
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 * @author ebag753
 *
 */
public class VIDIVOXCutAudio extends VIDIVOXAudioVideoOperation {
    
    private JButton btnCut;
    
    private JButton btnCancel;
    
    public VIDIVOXCutAudio(JFrame mainFrame, JTable settingsTable,
                                 File mediaFile) {
        super(mainFrame, mediaFile, settingsTable);
        logger.logInfo("VIDIVOXCutAudio::VIDIVOXCutAudio()");
        super.initialize();
        initialize();
    }
    
    @Override
    public void initialize() {
        logger.logInfo("VIDIVOXCutAudio::VIDIVOXCutAudio()::initialize()");
        btnCut = new JButton();
        btnCut.setBounds(10, 10, 105, 30);
        btnCut.setText("Cut");
        btnCut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String cutInputFilename = settingsTable.getValueAt(2, 1).toString();
                String cutOutputFilename = settingsTable.getValueAt(3, 1).toString();
                Date cutTimeFrom = null;
                Date cutTimeLength = null;
                boolean proceedWithOperation = true;

                // Check the existence of the outputfile in order not to overwrite
                //
                File tempOutputFile = new File(cutOutputFilename);
                if(tempOutputFile.exists() && !tempOutputFile.isDirectory()) {
                    int result = JOptionPane.showConfirmDialog(mainFrame,
                            "The file" + cutOutputFilename + " exists, overwrite?",
                            "Existing file",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    switch(result){
                        case JOptionPane.YES_OPTION:
                            // Let's delete the existing one and replace with the new one
                            //
                            try {
                                tempOutputFile.delete();
                            } catch(SecurityException e) {
                                logger.logError("VIDIVOXEditAudioVideo::btnCut::actionPerformed(): ERROR overwriting " + tempOutputFile.getAbsolutePath());
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
                        cutTimeLength = timeFormat.parse(settingsTable.getValueAt(0, 1).toString());
                        cutTimeFrom = timeFormat.parse(settingsTable.getValueAt(1, 1).toString());
                        
                        logger.logInfo("VIDIVOXEditAudioVideo::btnCut::actionPerformed():cutTimeLength = " + settingsTable.getValueAt(0, 1).toString());
                        logger.logInfo("VIDIVOXEditAudioVideo::btnCut::actionPerformed():cutTimeFrom = " + settingsTable.getValueAt(1, 1).toString());
                        logger.logInfo("VIDIVOXEditAudioVideo::btnCut::actionPerformed():cutInputFilename = " + cutInputFilename);
                        logger.logInfo("VIDIVOXEditAudioVideo::btnCut::actionPerformed():cutOutputFilename = " + cutOutputFilename);
                    
                        List<String> cutAudioCommand = new ArrayList<String>();
                        cutAudioCommand.add(VIDIVOXCommonInternals.FFMPEG_BIN);
                        cutAudioCommand.add("-i");
                        cutAudioCommand.add(cutInputFilename);
                        cutAudioCommand.add("-acodec");
                        cutAudioCommand.add("copy");
                        cutAudioCommand.add("-ss");
                        cutAudioCommand.add(timeFormat.format(cutTimeFrom));
                        cutAudioCommand.add("-t");
                        cutAudioCommand.add(timeFormat.format(cutTimeLength));
                        cutAudioCommand.add(cutOutputFilename);
                        
                        logger.logInfo("VIDIVOXEditAudioVideo::btnCut::actionPerformed():cutAudioCommand " + cutAudioCommand.toString());
                        processBuilder = new VIDIVOXProcessBuilder(cutAudioCommand);
                        if(processBuilder.startAndWait() == 0) {
                            logger.logInfo("VIDIVOXEditAudioVideo::btnCut::actionPerformed(): DONE Cutting audio from " + cutInputFilename);
                            
                            // Display the command output
                            //
                            outputStreamTextArea.setText(processBuilder.getProcessOutput());
                        }
                        else {
                            throw new Exception("VIDIVOXEditAudioVideo::btnCut::actionPerformed(): ERROR Cutting audio from " + cutInputFilename);
                        }
                        
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    } catch (ParseException e) {
                        logger.logError("VIDIVOXEditAudioVideo::btnCut::actionPerformed(): " + e.getMessage());
                        JOptionPane.showMessageDialog(mainFrame, 
                                "Length/From format should be [HH:mm:ss]!", 
                                "VIDIVOX", 
                                JOptionPane.ERROR_MESSAGE);
                    } catch(Exception e) {
                        logger.logError("VIDIVOXEditAudioVideo::btnCut::actionPerformed(): ERROR Cutting audio from " + cutInputFilename);
                        JOptionPane.showMessageDialog(mainFrame, 
                                "There was a problem generating the audio file. Please check the logs.", 
                                "VIDIVOX", 
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    logger.logInfo("VIDIVOXEditAudioVideo::btnCut::actionPerformed() Output file exists and user cancels the cut audio operation.");
                }
            }
        });
        
        btnCancel = new JButton();
        btnCancel.setBounds(10, 50, 105, 30);
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearConfiguration();
                setConfiguration();
            }
        });
        
        buttonsPanel.add(btnCut);
        buttonsPanel.add(btnCancel);
        
        setComponentsVisible(false);
        buttonsPanel.setVisible(true);
        outputScrollPane.setVisible(true);
    }
    
    public void setComponentsVisible(boolean aFlag) {
        btnCut.setVisible(aFlag);
        btnCancel.setVisible(aFlag);
        buttonsPanel.setVisible(aFlag);
        outputScrollPane.setVisible(aFlag);
    }
    
    public void setComponentsEnabled(boolean bEnabled) {
        btnCut.setEnabled(bEnabled);
        btnCancel.setEnabled(bEnabled);
        buttonsPanel.setEnabled(bEnabled);
        outputScrollPane.setEnabled(bEnabled);
    }
    
    public void setConfiguration() {
        logger.logInfo("VIDIVOXCutAudio::cutAudioSettings()");
        // First column
        //
        settingsTable.setValueAt("Length (HH:mm:ss)", 0, 0);
        settingsTable.setValueAt("From (HH:mm:ss)", 1, 0);
        settingsTable.setValueAt("Input filename", 2, 0);
        settingsTable.setValueAt("Ouput filename", 3, 0);
        
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
            logger.logError(e.getMessage());
        }
        
        // Second column
        //
        settingsTable.setValueAt("00:00:00", 0, 1);
        settingsTable.setValueAt("00:00:00", 1, 1);
    }
}
