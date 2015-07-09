package org.auckland.ac.nz.tools;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
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
        menuItems = new JMenu[VIDIVOXCommonInternals.MenuItems.values().length];
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        // FILE Menu
        //
        JMenu mnFile = new JMenu(VIDIVOXCommonInternals.MENU_FILE);
        menuBar.add(mnFile);
        
        // FILE -> OPEN Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.OPEN.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_FILE_OPEN);
        menuItems[VIDIVOXCommonInternals.MenuItems.OPEN.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    mediaFile = notification.openSaveFile(FileOperation.OPEN_FILE);
                    
                    if(mediaFile.isFile()) {
                        menuItems[VIDIVOXCommonInternals.MenuItems.PLAY.ordinal()].setEnabled(true);
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        mnFile.add(menuItems[VIDIVOXCommonInternals.MenuItems.OPEN.ordinal()]);
        
        // FILE -> SAVE Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.SAVE.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_FILE_SAVE);
        menuItems[VIDIVOXCommonInternals.MenuItems.SAVE.ordinal()].addMouseListener(new MouseAdapter() {
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
        menuItems[VIDIVOXCommonInternals.MenuItems.SAVE.ordinal()].setEnabled(false);
        mnFile.add(menuItems[VIDIVOXCommonInternals.MenuItems.SAVE.ordinal()]);
        
        // FILE -> EXIT Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.EXIT.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_FILE_EXIT);
        menuItems[VIDIVOXCommonInternals.MenuItems.EXIT.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notification.exitNotification();
            }
        });
        menuItems[VIDIVOXCommonInternals.MenuItems.EXIT.ordinal()].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                notification.exitNotification();
            }
        });
        mnFile.add(menuItems[VIDIVOXCommonInternals.MenuItems.EXIT.ordinal()]);
        
        // ACTIONS Menu
        //
        JMenu mnActions = new JMenu(VIDIVOXCommonInternals.MENU_ACTIONS);
        menuBar.add(mnActions);
        
        // ACTIONS -> PLAY Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.PLAY.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_ACTIONS_PLAY);
        menuItems[VIDIVOXCommonInternals.MenuItems.PLAY.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if(menuItems[VIDIVOXCommonInternals.MenuItems.PLAY.ordinal()].isEnabled()) {
                        mediaController.setMediaFile(mediaFile);
                        mediaController.play();
                        
                        // Enable other operations once the video is playing
                        //
                        menuItems[VIDIVOXCommonInternals.MenuItems.PAUSE.ordinal()].setEnabled(true);
                        menuItems[VIDIVOXCommonInternals.MenuItems.RESUME.ordinal()].setEnabled(true);
                        menuItems[VIDIVOXCommonInternals.MenuItems.STOP.ordinal()].setEnabled(true);
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        menuItems[VIDIVOXCommonInternals.MenuItems.PLAY.ordinal()].setEnabled(false);
        mnActions.add(menuItems[VIDIVOXCommonInternals.MenuItems.PLAY.ordinal()]);
        
        // ACTIONS -> PAUSE Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.PAUSE.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_ACTIONS_PAUSE);
        menuItems[VIDIVOXCommonInternals.MenuItems.PAUSE.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(menuItems[VIDIVOXCommonInternals.MenuItems.PAUSE.ordinal()].isEnabled()) {
                    mediaController.setMediaFile(mediaFile);
                    mediaController.pause();
                }
            }
        });
        menuItems[VIDIVOXCommonInternals.MenuItems.PAUSE.ordinal()].setEnabled(false);
        mnActions.add(menuItems[VIDIVOXCommonInternals.MenuItems.PAUSE.ordinal()]);
        
        // ACTIONS -> RESUME Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.RESUME.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_ACTIONS_RESUME);
        menuItems[VIDIVOXCommonInternals.MenuItems.RESUME.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(menuItems[VIDIVOXCommonInternals.MenuItems.RESUME.ordinal()].isEnabled()) {
                    mediaController.setMediaFile(mediaFile);
                    mediaController.resume();
                }
            }
        });
        menuItems[VIDIVOXCommonInternals.MenuItems.RESUME.ordinal()].setEnabled(false);
        mnActions.add(menuItems[VIDIVOXCommonInternals.MenuItems.RESUME.ordinal()]);
        
        // ACTIONS -> STOP Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.STOP.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_ACTIONS_STOP);
        menuItems[VIDIVOXCommonInternals.MenuItems.STOP.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(menuItems[VIDIVOXCommonInternals.MenuItems.STOP.ordinal()].isEnabled()) {
                    mediaController.setMediaFile(mediaFile);
                    mediaController.stop();
                    menuItems[VIDIVOXCommonInternals.MenuItems.PAUSE.ordinal()].setEnabled(false);
                    menuItems[VIDIVOXCommonInternals.MenuItems.RESUME.ordinal()].setEnabled(false);
                    menuItems[VIDIVOXCommonInternals.MenuItems.STOP.ordinal()].setEnabled(false);
                }
            }
        });
        menuItems[VIDIVOXCommonInternals.MenuItems.STOP.ordinal()].setEnabled(false);
        mnActions.add(menuItems[VIDIVOXCommonInternals.MenuItems.STOP.ordinal()]);
        
        // EDIT Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.EDIT.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_EDIT);
        menuBar.add(menuItems[VIDIVOXCommonInternals.MenuItems.EDIT.ordinal()]);
        
        // Initialize the VLCJ media player
        //
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        mediaController = new VIDIVOXMediaController(mediaPlayerComponent);
        frame.setContentPane(mediaPlayerComponent);
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
