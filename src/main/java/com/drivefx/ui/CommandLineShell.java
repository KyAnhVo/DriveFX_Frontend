package com.drivefx.ui;

import com.drivefx.State;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.util.Stack;

public class CommandLineShell extends TextArea implements Runnable {
    Stack<String> pastCalls = new Stack<>();
    int promptPosition = 0;

    public CommandLineShell() {
        super();
        this.setEditable(true);
        this.setWrapText(true);
        this.prefHeightProperty().bind(State.ScreenHeight.multiply(0.2));
        this.setText("~/>");

        // miscellaneous
        this.setStyle("-fx-control-inner-background: #333333; " +  // Dark grey background
                "-fx-text-fill: #FFFFFF; " +                 // White text
                "-fx-font-family: 'Fira Code'; " +              // Monospace font
                "-fx-font-size: 14px; " +
                "-fx-border-color: #555555; " +              // Slight border
                "-fx-highlight-fill: #666666; " +           // Selection color
                "-fx-highlight-text-fill: #FFFFFF;");
    }

    @Override
    public void run() {

    }
}
