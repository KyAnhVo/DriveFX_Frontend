package com.drivefx.ui;

import com.drivefx.authentication.AuthenticationService;
import com.drivefx.storage.FileSystemManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginPane extends StackPane {
    TextField emailField;
    PasswordField passwordField;
    Label emailLabel, passwordLabel, errorLabel;
    Button loginButton;
    ImageView logo, loadingGif;

    public LoginPane() {
        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(200);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(200);

        loginButton = new Button("Login");
        emailLabel = new Label("Email:\t");
        passwordLabel = new Label("Password:\t");
        errorLabel = new Label("");

        // logo
        logo = new ImageView(
                new Image(getClass().getResource("/com/drivefx/images/logo.png").toExternalForm()));
        logo.setPreserveRatio(true);
        logo.fitHeightProperty().bind(this.heightProperty().divide(8));
        logo.setVisible(true);

        // loadingGif
        loadingGif = new ImageView(
                new Image(getClass().getResource("/com/drivefx/images/loading.gif").toExternalForm()));
        loadingGif.setPreserveRatio(true);
        loadingGif.fitHeightProperty().bind(heightProperty().divide(3));
        loadingGif.setVisible(false);

        // Settings for email HBox
        HBox emailBox = new HBox(emailLabel, emailField);
        emailBox.setSpacing(10);
        emailBox.setAlignment(Pos.CENTER);

        // Settings for password HBox
        HBox passwordBox = new HBox(passwordLabel, passwordField);
        passwordBox.setSpacing(10);
        passwordBox.setAlignment(Pos.CENTER);

        // Settings for loginButton
        loginButton.setOnAction(e -> { login(); });
        loginButton.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(logo, emailBox, passwordBox, errorLabel, loginButton, loadingGif);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        this.getChildren().add(vbox);

        // Shortcut keys
        this.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                this.login();
            }
        });

        // decorations
        this.setStyle("-fx-background-color: cyan");
    }

    private void login() {

        // setup backend task
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                try {
                    com.drivefx.State.authenticationService = AuthenticationService.login(
                            emailField.getText(), passwordField.getText()
                    );
                    com.drivefx.State.fileSystemManager = FileSystemManager.createFileSystemManager(
                            com.drivefx.State.authenticationService
                    );
                }
                catch (Exception e) {
                    com.drivefx.State.authenticationService = null;
                    com.drivefx.State.fileSystemManager = null;
                    throw e;
                }
                return true;
            }
        };

        // when task is succeeded, loadingGif is disabled and State.loggedIn is true
        task.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                taskSetOnComplete();
                com.drivefx.State.loggedIn.set(true);
            });
        });

        // when task failed, show error message and disable gif
        task.setOnFailed(e -> {
            Platform.runLater(() -> {
                taskSetOnComplete();
                errorLabel.setText(task.getException().getMessage());
            });
        });



        // while running, disable all buttons and text fields, also turning on loading gif.
        task.setOnRunning(e -> {
            Platform.runLater(() -> {
                loadingGif.setVisible(true);
                errorLabel.setText("");
                loginButton.setDisable(true);
                emailField.setDisable(true);
                passwordField.setDisable(true);
            });
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void taskSetOnComplete() {
        loadingGif.setVisible(false);
        loginButton.setDisable(false);
        emailField.setDisable(false);
        passwordField.setDisable(false);
    }

}
