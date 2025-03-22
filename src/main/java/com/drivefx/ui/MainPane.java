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
    FileReadersPane fileReadersPane;

    ScrollPane leftPane;
    MenuBar menuBar;

    public MainPane() throws Exception {
        setupStateAndCenterPanes();
        setupMenuBar();

        this.setCenter(loginPane);
        this.setTop(menuBar);

        for (Menu menu : menuBar.getMenus()) {
            menu.setDisable(true);
        }
        // this.setBottom(commandLineShell);

        switchCenterPane();
    }

    private void setupStateAndCenterPanes() {
        loginPane = new LoginPane();
        directoryNavigatePane = null;
        fileReadersPane = new  FileReadersPane();

        loggedIn = new SimpleBooleanProperty();
        loggedIn.bind(State.loggedIn);
        menuBar = new MenuBar();
        leftPane = new ScrollPane();
        commandLineShell = new  CommandLineShell();
    }

    private void setupMenuBar() {
        Menu centerPaneMenu = new Menu("Center pane");
        MenuItem directoryMenuItem = new MenuItem("Directory");
        MenuItem fileEditMenuItem = new MenuItem("File Edit");
        centerPaneMenu.getItems().addAll(directoryMenuItem, fileEditMenuItem);
        menuBar.getMenus().add(centerPaneMenu);
        directoryMenuItem.setOnAction(e -> {
            State.editingFile.set(false);
        });
        fileEditMenuItem.setOnAction(e -> {
            State.editingFile.set(true);
        });
    }

    private void switchCenterPane() {
        State.loggedIn.addListener((observable, oldValue, newValue) -> {
            // if loggedIn is switched to true (logged in), swap to directory navigator
            if (newValue && !oldValue) {
                try {
                    State.fileSystemManager = FileSystemManager.createFileSystemManager(State.authenticationService);
                    this.directoryNavigatePane = new DirectoryNavigatePane();
                    this.setCenter(this.directoryNavigatePane);

                    for (Menu menu : menuBar.getMenus()) {
                        menu.setDisable(false);
                    }

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

        State.editingFile.addListener((observable, oldValue, newValue) -> {
            if (!State.loggedIn.get()) {
                State.editingFile.set(false);
                return;
            }
            if (newValue && !oldValue) {
                if (fileReadersPane.getTabs().isEmpty()) {
                    State.editingFile.set(false);
                    return;
                }
                this.setCenter(fileReadersPane);
            }
            if (!newValue && oldValue) {
                this.setCenter(directoryNavigatePane);
            }
        });
    }
}
