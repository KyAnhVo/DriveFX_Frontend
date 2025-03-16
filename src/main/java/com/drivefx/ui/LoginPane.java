package com.drivefx.ui;

import com.drivefx.State;
import com.drivefx.authentication.AuthenticationService;
import com.drivefx.storage.FileSystemManager;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginPane extends StackPane {
    TextField emailField;
    PasswordField passwordField;
    Label emailLabel, passwordLabel, errorLabel;
    Button loginButton;
    ImageView logo;

    public LoginPane() {
        emailField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Login");
        emailLabel = new Label("Email:\t");
        passwordLabel = new Label("Password:\t");
        errorLabel = new Label("");

        // test image
        logo = new ImageView(new Image(getClass().getResource("/com/drivefx/images/logo.png").toExternalForm()));

        // Settings for email HBox
        HBox emailBox = new HBox(emailLabel, emailField);
        emailBox.setSpacing(10);
        emailBox.setAlignment(Pos.CENTER);

        // Settings for password HBox
        HBox passwordBox = new HBox(passwordLabel, passwordField);
        passwordBox.setSpacing(10);
        passwordBox.setAlignment(Pos.CENTER);

        // Settings for logo
        logo.setPreserveRatio(true);
        logo.fitHeightProperty().bind(this.heightProperty().divide(8));

        // Settings for loginButton
        loginButton.setOnAction(e -> { login(); });
        loginButton.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(logo, emailBox, passwordBox, errorLabel, loginButton);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        this.getChildren().add(vbox);


        // decorations
        this.setStyle("-fx-background-color: cyan");
    }

    private void login() {
        try {
            State.authenticationService = AuthenticationService.login(
                    emailField.getText(), passwordField.getText()
            );
            State.fileSystemManager = FileSystemManager.createFileSystemManager(
                    State.authenticationService
            );
        }
        catch (Exception e) {
            State.authenticationService = null;
            State.fileSystemManager = null;
            errorLabel.setText(e.getMessage());
            return;
        }
        State.loggedIn.set(true);
        System.out.println("Logged in");
    }


}
