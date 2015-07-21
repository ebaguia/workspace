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
    
    public int numberOfCoulumns = 0;
    
    public VIDIVOXAudioVideoOperation(JFrame mainFrame,
                                      File mediaFile, 
                                      int numberOfCoulumns) {
        logger.logInfo("VIDIVOXAudioVideoOperation::VIDIVOXAudioVideoOperation() Constructor");
        this.mainFrame = mainFrame; 
        this.mediaFile = mediaFile;
        this.numberOfCoulumns = numberOfCoulumns;
    }
    
    public void initialize() {
        logger.logInfo("VIDIVOXAudioVideoOperation::initialize()");
        settingsScrollPane = new JScrollPane();
        settingsScrollPane.setBounds(10, 471, 635, 129);
        settingsScrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        //settingsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        settingsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainFrame.getContentPane().add(settingsScrollPane);
        
        buttonsPanel = new JPanel();
        buttonsPanel.setBounds(657, 471, 126, 129);
        buttonsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        buttonsPanel.setLayout(null);
        mainFrame.getContentPane().add(buttonsPanel);
        
        settingsTable = new JTable(new VIDIVOXSettingsTableModel(settingsColNames, numberOfCoulumns));
        settingsTable.setFillsViewportHeight(false);
        settingsTable.setSize(500, 100);
        settingsTable.getColumnModel().getColumn(0).setCellRenderer(new VIDIVOXCellRenderer());
        settingsTable.getColumnModel().getColumn(1).setCellRenderer(new VIDIVOXCellRenderer());
        Font font = settingsTable.getTableHeader().getFont();
        settingsTable.getTableHeader().setFont(font.deriveFont(Font.BOLD));
        
        settingsScrollPane.setViewportView(settingsTable);
        
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
}
