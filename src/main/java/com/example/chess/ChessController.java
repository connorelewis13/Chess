package com.example.chess;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ChessController {
    @FXML private Label welcomeText;
    @FXML private GridPane BoardGridPane;
    @FXML private Button StartButton;
    @FXML private Label ErrorLabel;
    private Coordinates initialLocation;
    private Coordinates finalLocation;
    private Background greyBG = new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY));
    private Background greenBG = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    @FXML private Label WhitesTurnLabel;
    @FXML private Label BlacksTurnLabel;


    public void StartButtonPressed(ActionEvent actionEvent) {
        ChessApplication.chessBoard.resetBoard();
        WhitesTurnLabel.setBackground(greenBG);
        BlacksTurnLabel.setBackground(greyBG);
        WhitesTurnLabel.setVisible(true);
        BlacksTurnLabel.setVisible(true);
        StartButton.setText("Restart");
        for(int y = 0;y<8;y++){
            for(int x=0;x<8;x++){
                putButtonWithPiece(y,x);
            }
        }
    }

    private void putButtonWithPiece(int y, int x) {
        Button button = new Button();
        button.setStyle("-fx-font-weight: bold; -fx-font-size: 10;");
        button.setMinHeight(70);
        button.setMaxHeight(70);
        button.setMinWidth(70);
        button.setMaxWidth(70);
        button.setText(getPieceOnSquare(x, y));
        button.setOnAction(chessButtonPressed);
        BoardGridPane.add(button, x, y);
    }

    EventHandler<ActionEvent> chessButtonPressed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                ErrorLabel.setText("");
                if (initialLocation == null) {
                    Button buttonPressed = (Button) event.getSource();
                    int x = GridPane.getColumnIndex(buttonPressed);
                    int y = GridPane.getRowIndex(buttonPressed);
                    initialLocation = new Coordinates(x, y);
                } else {
                    Button buttonPressed = (Button) event.getSource();
                    int finalX = GridPane.getColumnIndex(buttonPressed);
                    int finalY = GridPane.getRowIndex(buttonPressed);
                    finalLocation = new Coordinates(finalX, finalY);
                    ChessApplication.chessBoard.movePiece(initialLocation, finalLocation);
                    int initialX = initialLocation.getX();
                    int initialY = initialLocation.getY();
                    putButtonWithPiece(initialY, initialX);
                    putButtonWithPiece(finalY, finalX);
                    initialLocation = null;
                    if(ChessApplication.chessBoard.getWhitesTurn()){
                        WhitesTurnLabel.setBackground(greyBG);
                        BlacksTurnLabel.setBackground(greenBG);
                    }
                    else{
                        WhitesTurnLabel.setBackground(greenBG);
                        BlacksTurnLabel.setBackground(greyBG);
                    }
                }
            }
            catch (IllegalArgumentException e){
                initialLocation=null;
                if(ChessApplication.chessBoard.getWhitesTurn()){
                    ErrorLabel.setText("It is White's Turn");
                }
                else{
                    ErrorLabel.setText("It is Black's Turn");
                }

            }
        }
    };

    private String getPieceOnSquare(int x, int y) {
        Square square = ChessApplication.chessBoard.getSquareFromCoordinates(x,y);
        return square.toString();
    }
}