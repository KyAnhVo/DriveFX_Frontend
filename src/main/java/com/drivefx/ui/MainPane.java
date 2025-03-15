package com.drivefx.ui;

import com.drivefx.State;
import com.drivefx.authentication.AuthenticationService;
import javafx.beans.property.*;
import javafx.scene.control.Button;
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

    public MainPane() {
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


    }





}
