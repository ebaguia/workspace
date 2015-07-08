package org.auckland.ac.nz.tools;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class VIDIVOX {

    private JFrame frame;
    
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;

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
        
        // Initialize the VLCJ media player
        //
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        frame.setContentPane(mediaPlayerComponent);
        frame.setVisible(true);
        mediaPlayerComponent.getMediaPlayer().playMedia("/home/ebaguia/Downloads/test.avi");
    }

}
