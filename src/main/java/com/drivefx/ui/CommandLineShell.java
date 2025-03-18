package com.drivefx.ui;

import com.drivefx.State;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.util.Stack;

public class CommandLineShell extends TextArea{
    Stack<String> pastCalls = new Stack<>();
    int promptPosition = 0;

    public CommandLineShell() {
        super();
        this.setEditable(true);
        this.setWrapText(true);
        this.prefHeightProperty().bind(State.ScreenHeight.multiply(0.33));


        // add text, move cursor forward
        this.appendText(
                "COMMAND LINE SHELL - V1.0\n"
                        +"Command line shell for DriveFX.\n"
                        + "~/>"
        );
        this.positionCaret(this.getText().length());
        this.promptPosition = this.getCaretPosition();
        this.keyIntercept();


        // miscellaneous
        this.setStyle("-fx-control-inner-background: #333333; " +  // Dark grey background
                "-fx-text-fill: #FFFFFF; " +                 // White text
                "-fx-font-family: 'Fira Code'; " +              // Monospace font
                "-fx-font-size: 14px; " +
                "-fx-border-color: #555555; " +              // Slight border
                "-fx-highlight-fill: #666666; " +           // Selection color
                "-fx-highlight-text-fill: #FFFFFF;");
    }

    private void keyIntercept() {
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode keyCode = event.getCode();
            int anchorPosition = this.getSelection().getStart();

            if (keyCode == KeyCode.ENTER) {
                this.appendText("\n");
                this.appendText("~/>");
                this.positionCaret(this.getText().length());
                this.promptPosition = this.getCaretPosition();
            }
            else if (keyCode == KeyCode.BACK_SPACE) {
                if (anchorPosition <= promptPosition) {
                    event.consume();
                }
            }
            else if (keyCode == KeyCode.DELETE) {
                if (anchorPosition <  promptPosition) {
                    event.consume();
                }
            }

        });
    }


}
