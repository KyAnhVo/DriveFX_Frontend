package com.drivefx;

import com.drivefx.ui.MainPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DriveFX extends Application {
    public static void main(String[] args) { launch(); }

    @Override
    public void start(Stage stage) throws Exception {
        Path dirPath = Paths.get("temp_file");
        try {
            Files.createDirectory(dirPath);
        }
        catch (IOException e) {
            System.out.println("File is already created");
        }

        stage.setOnCloseRequest(event -> {
            deleteDirectory(new File("temp_file"));
        });

        stage.setTitle("Drive FX");

        stage.setResizable(true);
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() / 1.5);
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() / 1.5);

        Scene scene = new Scene(new MainPane());
        statesBinding(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void statesBinding(Scene scene) {
        State.ScreenHeight.bind(scene.heightProperty());
        State.ScreenWidth.bind(scene.widthProperty());
    }

    private void deleteDirectory(File dir) {
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                deleteDirectory(file);
            }
        }
        dir.delete();
        System.out.println("Deleted " + dir.getAbsolutePath());
    }
}