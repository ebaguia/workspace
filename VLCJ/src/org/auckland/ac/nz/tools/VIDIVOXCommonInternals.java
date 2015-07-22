/**
 * 
 */
package org.auckland.ac.nz.tools;

/**
 * @author ebag753
 *
 */
public final class VIDIVOXCommonInternals {
    
    /* This defines the items in our menu */
    public enum MenuItems {
        OPEN,
        SAVE,
        EXIT,
        PLAY,
        PAUSE,
        RESUME,
        STOP,
        EDIT,
        CUT_AUDIO,
        CFR,
        CFS
    };

    public final static String YES_MSG = "Yes";
    public final static String NO_MSG = "No";
    
    public final static String EXIT_APP = "Exit Application";
    public final static String EXIT_NOTIFICATION = "Are you sure you want to exit from the application?";
    
    public final static String FILE_EXIST = "File Exist";
    
    public final static String MENU_FILE = "File";
    public final static String MENU_FILE_OPEN = "Open";
    public final static String MENU_FILE_SAVE = "Save";
    public final static String MENU_FILE_EXIT = "Exit";
    
    public final static String MENU_ACTIONS = "Actions";
    public final static String MENU_ACTIONS_PLAY = "Play";
    public final static String MENU_ACTIONS_PAUSE = "Pause";
    public final static String MENU_ACTIONS_RESUME = "Resume";
    public final static String MENU_ACTIONS_STOP = "Stop";
    
    public final static String MENU_EDIT = "Edit";
    public final static String MENU_EDIT_CUT_AUDIO = "Cut Audio";
    public final static String MENU_EDIT_CFR = "Change Frame Rate";
    public final static String MENU_EDIT_CFS = "Change Frame Size";
    
    public final static String FFMPEG_BIN = "/usr/local/bin/ffmpeg";
    public final static String FFMPEG_SECONDS = "00:00:";
    
    public final static int COL_ROW = 10;
    
}

