package com.drivefx.ui;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginPane extends StackPane {
    TextField emailField;
    PasswordField passwordField;
    Label emailLabel, passwordLabel;
    Button loginButton;




    public LoginPane() {
        emailField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Login");
        emailLabel = new Label("Email:\t");
        passwordLabel = new Label("Password:\t");


        VBox vbox = new VBox(new HBox(emailLabel, emailField), new HBox(passwordLabel, passwordField), loginButton);
        vbox.setSpacing(10);
        this.getChildren().add(vbox);
    }
}
