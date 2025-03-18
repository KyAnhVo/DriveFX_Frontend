package com.drivefx.ui;

import com.drivefx.State;
import com.drivefx.storage.FileSystemManager;
import javafx.beans.property.*;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

/**
 * MainPane is the only direct child of the main scene
 */
public class MainPane extends BorderPane {
    BooleanProperty loggedIn;
    LoginPane loginPane;
    DirectoryNavigatePane directoryNavigatePane;
    CommandLineShell commandLineShell;

    ScrollPane leftPane;
    MenuBar menuBar;

    public MainPane() throws Exception {
        loginPane = new LoginPane();
        directoryNavigatePane = null;
        loggedIn = new SimpleBooleanProperty();
        loggedIn.bind(State.loggedIn);
        menuBar = new MenuBar();
        leftPane = new ScrollPane();
        commandLineShell = new  CommandLineShell();

        // TODO: implement menuBar
        menuBar.getMenus().add(new Menu("File"));

        this.setCenter(loginPane);
        this.setTop(menuBar);
        this.setBottom(commandLineShell);

        switchCenterPane();
    }

    private void switchCenterPane() {
        State.loggedIn.addListener((observable, oldValue, newValue) -> {
            // if loggedIn is switched to true (logged in), swap to directory navigator
            if (newValue && !oldValue) {
                try {
                    State.fileSystemManager = FileSystemManager.createFileSystemManager(State.authenticationService);
                    this.directoryNavigatePane = new DirectoryNavigatePane();
                    this.setCenter(directoryNavigatePane);
                }
                catch (Exception e) {
                    State.authenticationService = null;
                    State.loggedIn.set(false);
                    State.fileSystemManager = null;
                }

            }
            // if loggedIn is switched to false (logged out), swap to log in pane
            else if (!newValue && oldValue) {
                this.setCenter(loginPane);
            }
        });
    }



}
