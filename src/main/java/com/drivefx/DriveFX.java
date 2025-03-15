package com.drivefx;

import com.drivefx.ui.MainPane;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
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
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            stage.setWidth(newValue.doubleValue());
        });
    }


}