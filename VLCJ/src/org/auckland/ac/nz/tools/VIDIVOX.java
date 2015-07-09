package org.auckland.ac.nz.tools;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

import org.auckland.ac.nz.tools.VIDIVOXUserNotifications.FileOperation;

import java.io.*;

/**
 * 
 * @author ebag753
 *
 */
public class VIDIVOX {
    
    /* This defines the items in our menu */
    private enum MenuItems {
        OPEN,
        SAVE,
        EXIT,
        PLAY
    };
    
    private JMenu menuItems[];
    
    private JFrame frame;
    
    private VIDIVOXUserNotifications notification;
    
    private VIDIVOXMediaController mediaController;
    
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    
    private File mediaFile;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        new NativeDiscovery().discover();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VIDIVOX window = new VIDIVOX();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public VIDIVOX() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        
        notification = new VIDIVOXUserNotifications(frame);
        menuItems = new JMenu[MenuItems.values().length];
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        // FILE Menu
        //
        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);
        
        // FILE -> OPEN Menu
        //
        menuItems[MenuItems.OPEN.ordinal()] = new JMenu("Open");
        menuItems[MenuItems.OPEN.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    mediaFile = notification.openSaveFile(FileOperation.OPEN_FILE);
                    
                    if(mediaFile.isFile()) {
                        menuItems[MenuItems.PLAY.ordinal()].setEnabled(true);
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        mnFile.add(menuItems[MenuItems.OPEN.ordinal()]);
        
        // FILE -> SAVE Menu
        //
        menuItems[MenuItems.SAVE.ordinal()] = new JMenu("Save");
        menuItems[MenuItems.SAVE.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    mediaFile = notification.openSaveFile(FileOperation.SAVE_FILE);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        // TODO: save file functionality
        //
        menuItems[MenuItems.SAVE.ordinal()].setEnabled(false);
        mnFile.add(menuItems[MenuItems.SAVE.ordinal()]);
        
        // FILE -> EXIT Menu
        //
        menuItems[MenuItems.EXIT.ordinal()] = new JMenu("Exit");
        menuItems[MenuItems.EXIT.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notification.exitNotification();
            }
        });
        menuItems[MenuItems.EXIT.ordinal()].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                notification.exitNotification();
            }
        });
        mnFile.add(menuItems[MenuItems.EXIT.ordinal()]);
        
        // ACTIONS Menu
        //
        JMenu mnActions = new JMenu("Actions");
        menuBar.add(mnActions);
        
        // ACTIONS -> PLAY Menu
        //
        menuItems[MenuItems.PLAY.ordinal()] = new JMenu("Play");
        menuItems[MenuItems.PLAY.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if(menuItems[MenuItems.PLAY.ordinal()].isEnabled()) {
                        mediaController.setMediaFile(mediaFile);
                        mediaController.play();
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        menuItems[MenuItems.PLAY.ordinal()].setEnabled(false);
        mnActions.add(menuItems[MenuItems.PLAY.ordinal()]);
        
        // Initialize the VLCJ media player
        //
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        mediaController = new VIDIVOXMediaController(mediaPlayerComponent);
        frame.setContentPane(mediaPlayerComponent);
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
