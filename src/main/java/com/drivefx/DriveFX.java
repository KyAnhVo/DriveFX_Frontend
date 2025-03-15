package com.drivefx;

import com.drivefx.ui.MainPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DriveFX extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Drive FX");
        stage.setResizable(true);

        Scene scene = new Scene(new MainPane());

        stage.setScene(scene);
        stage.show();
    }
}