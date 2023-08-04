package com.example.chess;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ChessController {
    @FXML private Label welcomeText;
    @FXML private GridPane BoardGridPane;
    @FXML private Button StartButton;
    private Coordinates initialLocation;
    private Coordinates finalLocation;

    public void StartButtonPressed(ActionEvent actionEvent) {
        ChessApplication.chessBoard.resetBoard();
        for(int y = 0;y<8;y++){
            for(int x=0;x<8;x++){
                putButtonWithPiece(y,x);
            }
        }
    }

    private void putButtonWithPiece(int y, int x) {
        Button button = new Button();
        button.setMinHeight(80);
        button.setMaxHeight(80);
        button.setMinWidth(80);
        button.setMaxWidth(80);
        button.setText(getPieceOnSquare(x, y));
        button.setOnAction(chessButtonPressed);
        BoardGridPane.add(button, x, y);
    }

    EventHandler<ActionEvent> chessButtonPressed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(initialLocation==null){
                Button buttonPressed = (Button)event.getSource();
                int y = GridPane.getColumnIndex(buttonPressed);
                int x = GridPane.getRowIndex(buttonPressed);
                initialLocation = new Coordinates(x,y);
            }
            else{
                Button buttonPressed = (Button)event.getSource();
                int finalY = GridPane.getColumnIndex(buttonPressed);
                int finalX = GridPane.getRowIndex(buttonPressed);
                finalLocation = new Coordinates(finalX,finalY);
                ChessApplication.chessBoard.movePiece(initialLocation,finalLocation);
                int initialX = initialLocation.getX();
                int initialY = initialLocation.getY();
                putButtonWithPiece(initialY,initialX);
                putButtonWithPiece(finalY,finalX);
                initialLocation=null;
            }
        }
    };

    private String getPieceOnSquare(int x, int y) {
        Square square = ChessApplication.chessBoard.getSquareFromCoordinates(x,y);
        return square.toString();
    }
}