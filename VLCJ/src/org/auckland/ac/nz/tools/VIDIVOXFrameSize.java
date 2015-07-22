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
public class VIDIVOXFrameSize extends VIDIVOXAudioVideoOperation {
    
    public VIDIVOXFrameSize(JFrame mainFrame, JTable settingsTable, JTextArea outputTextArea, JPanel buttonsPanel,
            File mediaFile) {
        super(mainFrame, mediaFile, settingsTable, outputTextArea, buttonsPanel);
        logger.logInfo("VIDIVOXFrameSize::VIDIVOXFrameSize()");
        super.initialize();
        initialize();
    }
    
    @Override
    public void initialize() {
        logger.logInfo("VIDIVOXFrameSize::initialize()");
        btnOperation.setText("Change");
        btnOperation.addActionListener(new VIDIVOXOperationButtonListener(this, 
                                        settingsTable, 
                                        mainFrame, 
                                        outputTextArea, 
                                        VIDIVOXCommonInternals.Operation.CFS));
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
        logger.logInfo("VIDIVOXFrameSize::frameSizeSettings()");
        // First column
        //
        settingsTable.setValueAt("Width in pixels", 0, 0);
        settingsTable.setValueAt("Height in pixels", 1, 0);
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Second column
        //
        settingsTable.setValueAt("320", 0, 1);
        settingsTable.setValueAt("240", 1, 1);
    }
}
