package com.drivefx;

import com.drivefx.ui.MainPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class DriveFX extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Drive FX");

        stage.setResizable(true);
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);

        Scene scene = new Scene(new MainPane());
        statesBinding(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void statesBinding(Scene scene) {
        State.ScreenHeight.bind(scene.heightProperty());
        State.ScreenWidth.bind(scene.widthProperty());
    }
}