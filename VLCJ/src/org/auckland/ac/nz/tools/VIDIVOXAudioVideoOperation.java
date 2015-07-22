/**
 * 
 */
package org.auckland.ac.nz.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 * @author ebag753
 *
 */
public class VIDIVOXAudioVideoOperation {
    public JFrame mainFrame = null;
    
    public JScrollPane outputScrollPane = null;
    
    public JTable settingsTable = null;
    
    public File mediaFile = null;
    
    public VIDIVOXProcessBuilder processBuilder = null;
    
    public String[] settingsColNames = {"Item", "Value"};
    
    public VIDIVOXLogger logger = VIDIVOXLogger.getInstance();
    
    public SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
    public VIDIVOXSettingsTableModel settingsTableModel= null;
    
    public JTextArea outputTextArea = null;
    
    public JButton btnOperation = null;
    
    public JButton btnCancel = null;
    
    public JPanel buttonsPanel = null;
    
    public VIDIVOXAudioVideoOperation(JFrame mainFrame,
                                      File mediaFile, JTable settingsTable, JTextArea outputTextArea, JPanel buttonsPanel) {
        logger.logInfo("VIDIVOXAudioVideoOperation::VIDIVOXAudioVideoOperation() Constructor");
        this.mainFrame = mainFrame; 
        this.mediaFile = mediaFile;
        this.settingsTable = settingsTable;
        this.outputTextArea = outputTextArea;
        this.buttonsPanel = buttonsPanel;
    }
    
    public void initialize() {
        logger.logInfo("VIDIVOXAudioVideoOperation::initialize()");
        btnCancel = new JButton();
        btnCancel.setText("Reset");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearConfiguration();
                setConfiguration();
            }
        });
        buttonsPanel.add(btnCancel);
        
        btnOperation = new JButton();
        buttonsPanel.add(btnOperation);
    }
    
    public void setMediaFile(File mediaFile) {
        this.mediaFile = mediaFile;
    }
    
    public void setComponentsVisible() {
        // Do nothing; need to override
    }
    
    public void setComponentsEnabled() {
        // Do nothing; need to override
    }
    
    public void setConfiguration() {
        // Do nothing; need to override
    }
    
    public void clearConfiguration() {
        logger.logInfo("VIDIVOXAudioVideoOperation::clearConfiguration()");
        if(((VIDIVOXSettingsTableModel)settingsTable.getModel()).getRowCount() > 0) {
            ((VIDIVOXSettingsTableModel)settingsTable.getModel()).setRowCount(0);
            ((VIDIVOXSettingsTableModel)settingsTable.getModel()).setRowCount(VIDIVOXCommonInternals.COL_ROW);
        }
    }
    
    public void clearOutputTextArea() {
        outputTextArea.setText(null);
    }
}

