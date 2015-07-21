package org.auckland.ac.nz.tools;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.test.VlcjTest;
import uk.co.caprica.vlcj.test.basic.EqualizerFrame;
import uk.co.caprica.vlcj.test.basic.PlayerControlsPanel;
import uk.co.caprica.vlcj.test.basic.PlayerVideoAdjustPanel;

import org.auckland.ac.nz.tools.VIDIVOXUserNotifications.FileOperation;
import java.io.*;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;

/**
 * 
 * @author ebag753
 *
 */
public class VIDIVOX extends VlcjTest{
    
    private JMenu menuItems[];
    
    private JFrame frame;
    
    private JMenuBar menuBar;
    
    private JScrollPane settingsScrollPane;
    
    private JScrollPane outputAreaScrollPane;
    
    private JPanel buttonsPanel;
    
    private JTextArea outputStreamTextArea;
    
    private VIDIVOXUserNotifications notification;
    
    private VIDIVOXMediaController mediaController;
    
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    
    private MediaPlayer mediaPlayer;
    
    private VIDIVOXLogger logger;
    
    private File mediaFile;
    
    private VIDIVOXCutAudio editAudioVideo;
    private VIDIVOXFrameRate frameRate;
    private VIDIVOXFrameSize frameSize;
    
    private VIDIVOXPlayerControlsPanel playerControlsPane;
    
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
        logger = VIDIVOXLogger.getInstance();
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(240, 230, 140));
        frame.setBounds(200, 200, 793, 787);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        
        menuItems = new JMenu[VIDIVOXCommonInternals.MenuItems.values().length];
        
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        // FILE Menu
        //
        JMenu mnFile = new JMenu(VIDIVOXCommonInternals.MENU_FILE);
        menuBar.add(mnFile);
        mnFile.setMnemonic('f');
        
        // FILE -> OPEN Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.OPEN.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_FILE_OPEN);
        menuItems[VIDIVOXCommonInternals.MenuItems.OPEN.ordinal()].setMnemonic('o');
        menuItems[VIDIVOXCommonInternals.MenuItems.OPEN.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    mediaFile = notification.openSaveFile(FileOperation.OPEN_FILE);
                    
                    if(mediaFile != null && mediaFile.exists() && mediaFile.isFile()) {
                        mediaController = new VIDIVOXMediaController(mediaPlayerComponent);
                        mediaController.setMediaFile(mediaFile);
                        mediaController.play();
                        
                        //MediaMeta mediaData = mediaPlayerComponent.getMediaPlayer().getMediaMeta();
                        //logger.logInfo("URL: " + mediaData.getUrl());
                        
                        //ImageIcon imageurl = new ImageIcon("/home/ebag753/workspace/git-workspace/VLCJ/misc/trasera.jpg");
                        //Image img = imageurl.getImage();
                        //img = img.getScaledInstance(mediaPlayerComponent.getWidth() - 1, mediaPlayerComponent.getHeight() - 1, Image.SCALE_SMOOTH);
                        //mediaPlayerComponent.getGraphics().drawImage(img, 0, 0, mediaPlayerComponent);
                        
                        /*AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(new File(mediaFile.getParent(), mediaFile.getName()));
                        logger.logInfo(mediaFile.getParent());
                        logger.logInfo(mediaFile.getCanonicalPath());
                        if((fileFormat.getType() == AudioFileFormat.Type.AIFC) ||
                           (fileFormat.getType() == AudioFileFormat.Type.AIFF) ||
                           (fileFormat.getType() == AudioFileFormat.Type.AU) ||
                           (fileFormat.getType() == AudioFileFormat.Type.SND) ||
                           (fileFormat.getType() == AudioFileFormat.Type.WAVE)) {
                            logger.logInfo("File is an audio file");
                            // Show logo when playing audio
                            //
                            ImageIcon imageurl = new ImageIcon("/home/ebag753/workspace/git-workspace/VLCJ/misc/trasera.jpg");
                            Image img = imageurl.getImage();
                            img = img.getScaledInstance(mediaPlayerComponent.getWidth() - 1, mediaPlayerComponent.getHeight() - 1, Image.SCALE_SMOOTH);
                            mediaPlayerComponent.getGraphics().drawImage(img, 0, 0, mediaPlayerComponent);
                        }*/
                        
                        // We can now enable edit operations
                        //
                        menuItems[VIDIVOXCommonInternals.MenuItems.CUT_AUDIO.ordinal()].setEnabled(true);
                        menuItems[VIDIVOXCommonInternals.MenuItems.CFR.ordinal()].setEnabled(true);
                        menuItems[VIDIVOXCommonInternals.MenuItems.CFS.ordinal()].setEnabled(true);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        mnFile.add(menuItems[VIDIVOXCommonInternals.MenuItems.OPEN.ordinal()]);
        
        // FILE -> SAVE Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.SAVE.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_FILE_SAVE);
        menuItems[VIDIVOXCommonInternals.MenuItems.SAVE.ordinal()].setMnemonic('s');
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
        menuItems[VIDIVOXCommonInternals.MenuItems.EXIT.ordinal()].setMnemonic('x');
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
        
        // EDIT Menu
        //
        JMenu mnEdit = new JMenu(VIDIVOXCommonInternals.MENU_EDIT);
        menuBar.add(mnEdit);
        mnEdit.setMnemonic('e');
        
        // EDIT -> Cut Audio Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.CUT_AUDIO.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_EDIT_CUT_AUDIO);
        menuItems[VIDIVOXCommonInternals.MenuItems.CUT_AUDIO.ordinal()].setEnabled(false);
        menuItems[VIDIVOXCommonInternals.MenuItems.CUT_AUDIO.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(menuItems[VIDIVOXCommonInternals.MenuItems.CUT_AUDIO.ordinal()].isEnabled()) {
                    if(mediaPlayerComponent.getMediaPlayer().isPlaying()) {
                        mediaPlayerComponent.getMediaPlayer().stop();
                    }
                    clearSettingsTable();
                    setComponentsEnabled(false);
                    setComponentsVisible(false);
                    editAudioVideo.setMediaFile(mediaFile);
                    editAudioVideo.cutAudioSettings();
                    editAudioVideo.setComponentsEnabled(true);
                    editAudioVideo.setComponentsVisible(true);
                }
                
                frame.revalidate();
                frame.repaint();
            }
        });
        mnEdit.add(menuItems[VIDIVOXCommonInternals.MenuItems.CUT_AUDIO.ordinal()]);
        
        // EDIT -> Change Frame Rate Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.CFR.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_EDIT_CFR);
        menuItems[VIDIVOXCommonInternals.MenuItems.CFR.ordinal()].setEnabled(false);
        menuItems[VIDIVOXCommonInternals.MenuItems.CFR.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(menuItems[VIDIVOXCommonInternals.MenuItems.CFR.ordinal()].isEnabled()) {
                    if(mediaPlayerComponent.getMediaPlayer().isPlaying()) {
                        mediaPlayerComponent.getMediaPlayer().stop();
                    }
                    clearSettingsTable();
                    setComponentsEnabled(false);
                    setComponentsVisible(false);
                    frameRate.setMediaFile(mediaFile);
                    frameRate.frameRateSettings();
                    frameRate.setComponentsEnabled(true);
                    frameRate.setComponentsVisible(true);
                }
                
                frame.revalidate();
                frame.repaint();
            }
        });
        mnEdit.add(menuItems[VIDIVOXCommonInternals.MenuItems.CFR.ordinal()]);
        
        // EDIT -> Change Frame Size Menu
        //
        menuItems[VIDIVOXCommonInternals.MenuItems.CFS.ordinal()] = new JMenu(VIDIVOXCommonInternals.MENU_EDIT_CFS);
        menuItems[VIDIVOXCommonInternals.MenuItems.CFS.ordinal()].setEnabled(false);
        menuItems[VIDIVOXCommonInternals.MenuItems.CFS.ordinal()].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(menuItems[VIDIVOXCommonInternals.MenuItems.CFS.ordinal()].isEnabled()) {
                    if(mediaPlayerComponent.getMediaPlayer().isPlaying()) {
                        mediaPlayerComponent.getMediaPlayer().stop();
                    }
                    clearSettingsTable();
                    setComponentsEnabled(false);
                    setComponentsVisible(false);
                    frameSize.setMediaFile(mediaFile);
                    frameSize.frameSizeSettings();
                    frameSize.setComponentsEnabled(true);
                    frameSize.setComponentsVisible(true);
                }
                
                frame.revalidate();
                frame.repaint();
            }
        });
        mnEdit.add(menuItems[VIDIVOXCommonInternals.MenuItems.CFS.ordinal()]);
        
        // Initialize the VLCJ media player
        //
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        mediaPlayerComponent.setBounds(0, 0, 800, 365);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(mediaPlayerComponent);
        
        playerControlsPane = new VIDIVOXPlayerControlsPanel(mediaPlayerComponent.getMediaPlayer());
        playerControlsPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        playerControlsPane.setBounds(0, 364, 793, 89);
        frame.getContentPane().add(playerControlsPane);
        
        JLabel lblSettings = new JLabel("Settings");
        lblSettings.setBounds(10, 454, 70, 15);
        frame.getContentPane().add(lblSettings);
        
        notification = new VIDIVOXUserNotifications(frame, mediaPlayerComponent);
        
        JLabel lblOutput = new JLabel("Output");
        lblOutput.setBounds(10, 612, 70, 15);
        frame.getContentPane().add(lblOutput);
        
        editAudioVideo = new VIDIVOXCutAudio(frame, mediaFile);
        frameRate = new VIDIVOXFrameRate(frame, mediaFile);
        frameSize  = new VIDIVOXFrameSize(frame, mediaFile);
    }
    
    private void clearSettingsTable() {
        editAudioVideo.clearCutSettings();
        frameRate.clearFrameRateSettings();
        frameSize.clearFrameSizeSettings();
    }
    
    private void setComponentsEnabled(boolean bEnabled) {
        editAudioVideo.setComponentsEnabled(bEnabled);
        frameRate.setComponentsEnabled(bEnabled);
        frameSize.setComponentsEnabled(bEnabled);
    }
    
    private void setComponentsVisible(boolean bEnabled) {
        editAudioVideo.setComponentsVisible(bEnabled);
        frameRate.setComponentsVisible(bEnabled);
        frameSize.setComponentsVisible(bEnabled);
    }
}
