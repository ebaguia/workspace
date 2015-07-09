package org.auckland.ac.nz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.IOException;

/**
 * This class implements all user notifications.
 * 
 * @author ebaguia
 *
 */
public class VIDIVOXUserNotifications {
	private JFrame mainFrame = null;
	private JDialog exitNotificationDialog = null;
	private JPanel notifyPanel = null;
	
	/* Constants */
	public enum FileOperation {
	  OPEN_FILE,
	  SAVE_FILE
	};
	private final String EXIT_APPLICATION_MSG = new String("Are you sure you want to exit from the application?");
	
	/**
	 * 
	 * @param mainFrame
	 */
	public VIDIVOXUserNotifications(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	/**
	 * Handles the notification when the user exits the application.
	 */
	public void exitNotification() {
		if(null != mainFrame) {
		    JButton yesButton = new JButton("Yes");
            JButton noButton = new JButton("No");
		    
		    notifyPanel = new JPanel();
		    notifyPanel.add(new JLabel(EXIT_APPLICATION_MSG));
		    
			exitNotificationDialog = new JDialog(mainFrame, "Exit Application", true);
			exitNotificationDialog.setPreferredSize(new Dimension(380,60));
			exitNotificationDialog.setResizable(false);
			exitNotificationDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			yesButton.addActionListener(new ActionListener() {
			    @Override public void actionPerformed(ActionEvent a) {
			        // We are now going to exit the application
			        //
			        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                System.exit(0);
			    }
			});
			
			noButton.addActionListener(new ActionListener() {
                @Override public void actionPerformed(ActionEvent a) {
                    // Close this dialog only but not the application
                    //
                    exitNotificationDialog.dispose();
                }
            });
			
			notifyPanel.add(yesButton);
			notifyPanel.add(noButton);
			
			exitNotificationDialog.setContentPane(notifyPanel);
			exitNotificationDialog.pack();
			exitNotificationDialog.setLocationRelativeTo(mainFrame);
			exitNotificationDialog.setVisible(true);
		}
	}
	
	public File openSaveFile(FileOperation operation) throws IOException {
	    File selectedFile = null;
	    JFileChooser openSaveFileDialog = new JFileChooser(new File(".").getCanonicalPath());
	    
	    switch (operation) {
	        case OPEN_FILE: 
	            openSaveFileDialog.showOpenDialog(mainFrame);
	            break;
	        case SAVE_FILE:
	            openSaveFileDialog.showSaveDialog(mainFrame);
	            break;
	    }
	    
	    selectedFile = openSaveFileDialog.getSelectedFile();
	    return selectedFile;
	}
}
