package com.drivefx;

import com.drivefx.authentication.AuthenticationService;
import com.drivefx.storage.FileSystemManager;
import javafx.beans.property.*;

public class State {
    /**
     * loggedIn is true if user is logged in, false otherwise.
     */
    public final static BooleanProperty loggedIn = new SimpleBooleanProperty(false);

    /**
     * Depth of the file stack. 0 means user is currently at home directory.
     */
    public final static IntegerProperty fileStackDepth = new SimpleIntegerProperty(0);

    /**
     * Height of scene
     */
    public final static IntegerProperty ScreenHeight = new SimpleIntegerProperty(0);

    /**
     * Width of scene
     */
    public final static IntegerProperty ScreenWidth = new SimpleIntegerProperty(0);

    /**
     * true if user is accessing a txt file, false otherwise
     */
    public final static BooleanProperty editingFile = new SimpleBooleanProperty(false);

    // Logic backend
    public static AuthenticationService authenticationService = null;
    public static FileSystemManager fileSystemManager = null;
}
