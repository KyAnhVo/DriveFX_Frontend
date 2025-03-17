package com.drivefx.ui;

import com.drivefx.State;
import com.drivefx.storage.FileSystemManager;
import javafx.beans.property.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

/**
 * MainPane is the only direct child of the main scene
 */
public class MainPane extends BorderPane {
    BooleanProperty loggedIn;
    LoginPane loginPane;
    DirectoryNavigatePane directoryNavigatePane;

    ScrollPane leftPane;
    ButtonBar topButtonBar;

    public MainPane() throws Exception {
        loginPane = new LoginPane();
        directoryNavigatePane = null;
        loggedIn = new SimpleBooleanProperty();
        loggedIn.bind(State.loggedIn);
        topButtonBar = new ButtonBar();
        leftPane = new ScrollPane();

        this.setCenter(loginPane);
        loginPane.prefWidthProperty().bind(State.ScreenWidth.multiply(0.8));

        this.setTop(topButtonBar);

        this.setLeft(leftPane);
        leftPane.prefWidthProperty().bind(State.ScreenWidth.multiply(0.2));

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
