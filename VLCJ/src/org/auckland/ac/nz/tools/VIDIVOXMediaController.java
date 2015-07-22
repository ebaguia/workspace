package org.auckland.ac.nz.tools;

import java.io.*;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

/**
 * 
 * @author ebag753
 *
 */
public class VIDIVOXMediaController {
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    
    private File mediaFile;
    
    public VIDIVOXMediaController(EmbeddedMediaPlayerComponent mediaPlayerComponent) {
        this.mediaPlayerComponent = mediaPlayerComponent;
    }
    
    public void setMediaPlayerComponent(EmbeddedMediaPlayerComponent mediaPlayerComponent) {
        this.mediaPlayerComponent = mediaPlayerComponent;
    }
    
    public void setMediaFile(File mediaFile) {
        this.mediaFile = mediaFile;
    }
    
    public void play() throws IOException {
        if(mediaFile.isFile()) {
            mediaPlayerComponent.getMediaPlayer().playMedia(mediaFile.getCanonicalPath());
        }
    }
    
    public void pause() {
        if(mediaPlayerComponent.isValid()) {
            mediaPlayerComponent.getMediaPlayer().pause();
        }
    }
    
    public void stop() {
        if(mediaPlayerComponent.isValid()) {
            mediaPlayerComponent.getMediaPlayer().stop();
        }
    }
    
    public void resume() {
        if(mediaPlayerComponent.isValid()) {
            mediaPlayerComponent.getMediaPlayer().play();
        }
    }
}

