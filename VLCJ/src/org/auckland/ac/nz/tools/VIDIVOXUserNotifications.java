package org.auckland.ac.nz.tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

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
	private EmbeddedMediaPlayerComponent mediaPlayerComponent = null;
	
	/* Constants */
	public enum FileOperation {
	  OPEN_FILE,
	  SAVE_FILE
	};
	
	/**
	 * 
	 * @param mainFrame
	 */
	public VIDIVOXUserNotifications(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	/**
     * 
     * @param mainFrame
     */
    public VIDIVOXUserNotifications(JFrame mainFrame, EmbeddedMediaPlayerComponent mediaPlayerComponent) {
        this.mainFrame = mainFrame;
        this.mediaPlayerComponent = mediaPlayerComponent;
    }
	
	/**
	 * Handles the notification when the user exits the application.
	 */
	public void exitNotification() {
		if(null != mainFrame) {
		    JButton yesButton = new JButton(VIDIVOXCommonInternals.YES_MSG);
            JButton noButton = new JButton(VIDIVOXCommonInternals.NO_MSG);
		    
		    notifyPanel = new JPanel();
		    notifyPanel.add(new JLabel(VIDIVOXCommonInternals.EXIT_NOTIFICATION));
		    
			exitNotificationDialog = new JDialog(mainFrame, VIDIVOXCommonInternals.EXIT_APP, true);
			exitNotificationDialog.setPreferredSize(new Dimension(380,60));
			exitNotificationDialog.setResizable(false);
			exitNotificationDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			yesButton.addActionListener(new ActionListener() {
			    @Override public void actionPerformed(ActionEvent a) {
			        // We are now going to exit the application
			        //
			        if((mediaPlayerComponent != null) && (mediaPlayerComponent.isValid())) {
			            mediaPlayerComponent.release();
			        }
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
	
	/**
	 * 
	 * @param operation
	 * @return
	 * @throws IOException
	 */
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
