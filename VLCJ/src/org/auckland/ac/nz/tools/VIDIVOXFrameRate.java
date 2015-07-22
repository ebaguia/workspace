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
public class VIDIVOXFrameRate extends VIDIVOXAudioVideoOperation {
    
    public VIDIVOXFrameRate(JFrame mainFrame, JTable settingsTable, JTextArea outputTextArea, JPanel buttonsPanel,
            File mediaFile) {
        super(mainFrame, mediaFile, settingsTable, outputTextArea, buttonsPanel);
        logger.logInfo("VIDIVOXFrameRate::VIDIVOXFrameRate()");
        super.initialize();
        initialize();
    }
    
    @Override
    public void initialize() {
        logger.logInfo("VIDIVOXFrameRate::initialize()");
        btnOperation.setText("Change");
        btnOperation.addActionListener(new VIDIVOXOperationButtonListener(this, 
                                       settingsTable, 
                                       mainFrame, 
                                       outputTextArea, 
                                       VIDIVOXCommonInternals.Operation.CFR));
        
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
