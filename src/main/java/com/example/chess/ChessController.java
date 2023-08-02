package com.example.chess;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ChessController {
    @FXML private Label welcomeText;
    @FXML private GridPane BoardGridPane;
    @FXML private Button StartButton;
    @FXML
    protected void onHelloButtonClick() {

    }

    public void StartButtonPressed(ActionEvent actionEvent) {
        ChessApplication.chessBoard.resetBoard();
        for(int x = 0;x<8;x++){
            for(int y=0;y<8;y++){
                Button button = new Button();
                button.setText(getPieceOnSquare(x,y));
                BoardGridPane.add(button,x,y);
            }
        }
    }

    private String getPieceOnSquare(int x, int y) {
        Coordinates coordinates = new Coordinates(x+1,y+1);
        return "";
    }
}