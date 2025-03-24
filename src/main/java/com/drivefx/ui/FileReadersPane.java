package com.drivefx.ui;

import com.drivefx.State;
import com.drivefx.network.APIHandler;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;

public class FileReadersPane extends TabPane {
    private int tempFiles = 0;

    public FileReadersPane() {}

    public void addFile(String awsFilename) throws Exception {
        String localFilename = "temp_file_" + tempFiles + ".txt";
        tempFiles++;
        String presignedURL = APIHandler.getPresignedURLDownload(awsFilename, State.awsDownloadFileAPI);
        APIHandler.downloadFile(presignedURL, localFilename);

        FileReadersTab newTab = new FileReadersTab(awsFilename, localFilename);
        this.getTabs().add(newTab);
        this.getSelectionModel().select(newTab);
    }

    public static class FileReadersTab extends Tab {
        String awsFilename, tempFilename, awsFilenameNoPath;
        VBox rootBox;

        ButtonBar buttonBar = new ButtonBar();
        Button save = new Button("save");
        Button revert = new Button("revert to latest commit");
        Button delete = new Button("delete file");

        TextArea textArea = new TextArea();

        public FileReadersTab(String awsFilename, String tempFilename) throws Exception {
            this.awsFilename = awsFilename;
            this.tempFilename = tempFilename;
            this.buttonBar.getButtons().addAll(save, revert, delete);

            rootBox = new VBox();
            rootBox.setSpacing(10);
            rootBox.getChildren().addAll(buttonBar, textArea);
            this.setContent(rootBox);

            String[] files = awsFilename.split("/");
            awsFilenameNoPath = files[files.length - 1];
            this.setText(awsFilenameNoPath);

            Task<String> task = readFileToTextArea(tempFilename);

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }

        private Task<String> readFileToTextArea(String tempFilename) {
            Task<String> task = new Task<>() {
                @Override
                protected String call() throws Exception {
                    try {
                        FileInputStream fileReader = new FileInputStream(com.drivefx.State.tempFilesDirPath + tempFilename);
                        String fileContents = new String(fileReader.readAllBytes());
                        fileReader.close();
                        return fileContents;
                    }
                    catch (Exception e) {
                        throw new RuntimeException("File reading failed");
                    }
                }
            };
            task.setOnSucceeded(event -> {
                textArea.setText(task.getValue());
            });
            task.setOnFailed(event -> {
                throw new RuntimeException(task.getException());
            });
            return task;
        }
    }
}
