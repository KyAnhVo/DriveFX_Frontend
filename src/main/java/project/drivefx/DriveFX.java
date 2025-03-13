package project.drivefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.tools.Tool;
import java.io.IOException;

public class DriveFX extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setTitle("Drive FX");
        stage.setScene(setMainScene());
        stage.setFullScreen(false);
        stage.setWidth(screenBounds.getWidth() / 2);
        stage.setHeight(screenBounds.getHeight() / 2);
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.show();
    }

    private Scene setMainScene() throws IOException {
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(setPrimaryPane());
        mainPane.setLeft(setSecondaryPane());
        mainPane.setTop(setToolbar());
        return new Scene(mainPane, 800, 400);
    }

    private Node setPrimaryPane() throws IOException {
        ScrollPane pane = new ScrollPane();
        pane.setContent(setGridPane());
        return pane;
    }

    private Node setSecondaryPane() throws IOException {
        Node pane = new ScrollPane();
        return pane;
    }

    private Node setToolbar() throws IOException {
        ToolBar toolbar = new ToolBar();
        ToolbarButtons toolbarButtons = new ToolbarButtons();
        toolbar.getItems().addAll(toolbarButtons.getButtons());
        return toolbar;
    }

    private Node setGridPane() throws IOException {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        Image folderImg = new Image(getClass().getResourceAsStream("folder.png"));
        gridPane.add(new ImageView(folderImg), 0, 0);
        gridPane.add(new ImageView(folderImg), 1, 0);
        gridPane.add(new ImageView(folderImg), 0, 1);
        gridPane.add(new ImageView(folderImg), 1, 1);

        return gridPane;
    }
}

// All buttons to be used for the toolbar.

class ToolbarButtons {
    Button cdFolder = new Button("View this file");
    Button cdParent = new Button("View parent folder");
    Button readFile = new Button("Read file");

    public ToolbarButtons() {}

    public Button[] getButtons() {
        return new Button[] {cdFolder, cdParent, readFile};
    }
}

class FileClickable extends Button {

}