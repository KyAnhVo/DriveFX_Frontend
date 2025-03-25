package com.drivefx.ui;

import com.drivefx.State;
import com.drivefx.network.APIHandler;
import com.drivefx.storage.FileSystemNode;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class DirectoryNavigatePane extends TilePane {
    public final ArrayList<FileView> views;
    public FileReadersPane fileReadersPane;

    public DirectoryNavigatePane(FileReadersPane fileReadersPane) throws Exception {
        super();
        this.views = new ArrayList<FileView>();
        this.fileReadersPane = fileReadersPane;

        reload();
    }

    /**
     * Reloading page, used for user or for after each cd.
     */
    public void reload() throws Exception {
        views.clear();
        this.getChildren().clear();

        // for cd back if parent isn't null
        FileSystemNode parent = State.fileSystemManager.getCurrentNode().getParent();
        if (parent != null)
            this.getChildren().add(new FileView("..", true, parent));

        for (FileSystemNode node : State.fileSystemManager.getCurrentNode().getChildren()) {
            FileView curr = new FileView(node.getName(), node.isDirectory, node);
            views.add(curr);
            this.getChildren().add(curr);
        }
    }

    /**
     * CD to currentNode's child directory.
     * @param name
     */
    public void cd(String name) throws Exception {

        try {
            State.fileSystemManager.cd(name);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            reload();
        }
    }

    /**
     * A VBox that has an icon for directory or txt file, and a name.
     */
    public class FileView extends VBox {
        static final String dirImgPath = "/com/drivefx/images/folder.png";
        static final String txtImgPath = "/com/drivefx/images/txtFile.png";
        Label name;
        ImageView img;
        FileSystemNode node;

        public FileView(String name, boolean isDir, FileSystemNode node) throws Exception {
            if (isDir) {
                img = new ImageView(
                        new Image(getClass().getResource(dirImgPath).toExternalForm())
                );
            }
            else {
                img = new ImageView(
                        new Image(getClass().getResource(txtImgPath).toExternalForm())
                );
            }

            // image size settings

            img.setPreserveRatio(false);

            img.setFitHeight(50);
            img.minHeight(50);
            img.maxHeight(50);

            img.setFitWidth(50);
            img.minWidth(50);
            img.maxWidth(50);

            // label name settings

            this.name = new Label(name);
            this.name.setTextAlignment(TextAlignment.CENTER);
            this.name.setAlignment(Pos.CENTER);
            this.name.setMaxWidth(70);
            this.name.setMinWidth(70);
            this.name.setFont(Font.font("Consolas", FontWeight.THIN, 15));

            // add all children to list

            this.setAlignment(Pos.CENTER);
            this.getChildren().addAll(img, this.name);

            this.node = node;

            mouseBehaviour(isDir);
        }

        private void mouseBehaviour(boolean isDir) throws Exception {
            // mouse behaviour on node
            this.setOnMouseClicked(e -> {
                if (isDir) {
                    try {
                        cd(this.name.getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    String awsPath;
                    try {
                        awsPath = this.node.getAwsPath();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    // if file is opened, go to that file
                    for (Tab tab : fileReadersPane.getTabs()) {
                        FileReadersPane.FileReadersTab frTab = (FileReadersPane.FileReadersTab) tab;
                        if (frTab.awsFilename.equals(awsPath)) {
                            fileReadersPane.getSelectionModel().select(frTab);
                            return;
                        }
                    }

                    // else, download from aws, add tab, and switch to that tab.
                    Task<String> task = new Task<>() {
                        @Override
                        protected String call() throws Exception {
                            fileReadersPane.addFile(awsPath);
                            return awsPath;
                        }
                    };
                }
            });
            this.setOnMouseEntered(e -> {
                this.setStyle("-fx-background-color: lightblue;");
            });
            this.setOnMouseExited(e -> {
                this.setStyle("-fx-background-color: transparent;");
            });
        }
    }
}
