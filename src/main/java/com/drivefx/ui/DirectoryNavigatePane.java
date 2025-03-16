package com.drivefx.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class DirectoryNavigatePane extends TilePane {
    ArrayList<fileView> views;

    public DirectoryNavigatePane() throws Exception {
        super();
        views = new ArrayList<fileView>();

        for (int i = 0; i < 30; i++) {

        }
    }

    private class fileView extends VBox {
        static final String dirImgPath = "/com/drivefx/images/folder.png";
        static final String txtImgPath = "/com/drivefx/images/txtFile.png";
        Label name;
        ImageView img;

        public fileView(String name, boolean isDir) {
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
            img.setPreserveRatio(false);

            img.setFitHeight(50);
            img.minHeight(50);
            img.maxHeight(50);

            img.setFitWidth(50);
            img.minWidth(50);
            img.maxWidth(50);

            this.name = new Label(name);
            this.name.setTextAlignment(TextAlignment.CENTER);
            this.name.setMaxWidth(70);
            this.name.setMinWidth(70);
            this.name.setFont(Font.font("Consolas", FontWeight.THIN, 15));

            this.getChildren().addAll(img, this.name);
            this.setAlignment(Pos.CENTER);
        }
    }
}
