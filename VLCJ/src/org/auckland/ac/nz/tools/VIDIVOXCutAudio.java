/**
 * 
 */
package org.auckland.ac.nz.tools;

import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 * @author ebag753
 *
 */
public class VIDIVOXCutAudio extends VIDIVOXAudioVideoOperation {
    
    public VIDIVOXCutAudio(JFrame mainFrame, JTable settingsTable, JTextArea outputTextArea, JPanel buttonsPanel,
            File mediaFile) {
        super(mainFrame, mediaFile, settingsTable, outputTextArea, buttonsPanel);
        logger.logInfo("VIDIVOXCutAudio::VIDIVOXCutAudio()");
        super.initialize();
        initialize();
    }
    
    @Override
    public void initialize() {
        logger.logInfo("VIDIVOXCutAudio::VIDIVOXCutAudio()::initialize()");
        btnOperation.setText("Cut");
        btnOperation.addActionListener(new VIDIVOXOperationButtonListener(this, 
                                        settingsTable, 
                                        mainFrame, 
                                        outputTextArea, 
                                        VIDIVOXCommonInternals.Operation.CUT_AUDIO));
        
        setComponentsVisible(false);
        buttonsPanel.setVisible(true);
    }
    
    public void setComponentsVisible(boolean aFlag) {
        btnOperation.setVisible(aFlag);
        btnCancel.setVisible(aFlag);
        buttonsPanel.setVisible(aFlag);
    }
    
    public void setComponentsEnabled(boolean bEnabled) {
        btnOperation.setEnabled(bEnabled);
        btnCancel.setEnabled(bEnabled);
        buttonsPanel.setEnabled(bEnabled);
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
