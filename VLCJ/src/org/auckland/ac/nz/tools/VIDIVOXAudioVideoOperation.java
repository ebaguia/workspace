/**
 * 
 */
package org.auckland.ac.nz.tools;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 * @author ebag753
 *
 */
public class VIDIVOXAudioVideoOperation {
    public JFrame mainFrame = null;
    
    public JScrollPane settingsScrollPane = null;
    
    public JScrollPane outputScrollPane = null;
    
    public JTable settingsTable = null;
    
    public File mediaFile = null;
    
    public VIDIVOXProcessBuilder processBuilder = null;
    
    public String[] settingsColNames = {"Item", "Value"};
    
    public VIDIVOXLogger logger = VIDIVOXLogger.getInstance();
    
    public SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
    public JPanel buttonsPanel = null;
    
    public JTextArea outputStreamTextArea = null;
    
    public VIDIVOXSettingsTableModel settingsTableModel= null;
    
    public VIDIVOXAudioVideoOperation(JFrame mainFrame,
                                      File mediaFile, JTable settingsTable) {
        logger.logInfo("VIDIVOXAudioVideoOperation::VIDIVOXAudioVideoOperation() Constructor");
        this.mainFrame = mainFrame; 
        this.mediaFile = mediaFile;
        this.settingsTable = settingsTable;
    }
    
    public void initialize() {
        logger.logInfo("VIDIVOXAudioVideoOperation::initialize()");
        
        buttonsPanel = new JPanel();
        buttonsPanel.setBounds(657, 471, 126, 129);
        buttonsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        buttonsPanel.setLayout(null);
        mainFrame.getContentPane().add(buttonsPanel);
        
        outputStreamTextArea = new JTextArea();
        //outputStreamTextArea.setLineWrap(true);
        outputStreamTextArea.setEditable(false);
        outputStreamTextArea.setVisible(true);
        outputScrollPane = new JScrollPane(outputStreamTextArea);
        outputScrollPane.setBounds(10, 631, 771, 98);
        outputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainFrame.getContentPane().add(outputScrollPane);
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
}

