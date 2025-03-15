package com.drivefx;

import javafx.beans.property.*;

public class State {
    /**
     * loggedIn is true if user is logged in, false otherwise.
     */
    public static BooleanProperty loggedIn = new SimpleBooleanProperty(false);

    /**
     * Depth of the file stack. 0 means user is currently at home directory.
     */
    public static IntegerProperty fileStackDepth = new SimpleIntegerProperty(0);

    /**
     * Height of screen (stage, scene)
     */
    public static IntegerProperty ScreenHeight = new SimpleIntegerProperty(0);

    /**
     * Width of screen (stage, scene)
     */
    public static IntegerProperty ScreenWidth = new SimpleIntegerProperty(0);
}
