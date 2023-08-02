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
        for(int y = 0;y<8;y++){
            for(int x=0;x<8;x++){
                Button button = new Button();

                button.setMinHeight(80);
                button.setMaxHeight(80);
                button.setMinWidth(80);
                button.setMaxWidth(80);
                button.setText(getPieceOnSquare(x,y));
                BoardGridPane.add(button,x,y);
            }
        }
    }

    private String getPieceOnSquare(int x, int y) {
        Square square = ChessApplication.chessBoard.getSquareFromCoordinates(x,y);
        return square.toString();
    }
}