package com.example.chess;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ChessController {
    @FXML private Label welcomeText;
    @FXML private GridPane BoardGridPane;
    @FXML private Button StartButton;
    @FXML private Label ErrorLabel;
    @FXML private Label WinnerLabel;
    @FXML private HBox turnHbox;
    private Coordinates initialLocation;
    private Coordinates finalLocation;
    private Background greyBG = new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY));
    private Background greenBG = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    @FXML private Label WhitesTurnLabel;
    @FXML private Label BlacksTurnLabel;


    public void StartButtonPressed(ActionEvent actionEvent) {
//        BoardGridPane.setVisible(true);
//        BoardGridPane.setManaged(true);
        ChessApplication.chessBoard.setCheckMated(null);
        turnHbox.setManaged(true);
        turnHbox.setVisible(true);
        ErrorLabel.setManaged(true);
        ErrorLabel.setVisible(true);
        WinnerLabel.setVisible(false);
        WinnerLabel.setManaged(false);
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
                    Move move = new Move(initialLocation,finalLocation, ChessApplication.chessBoard);
                    move.movePiece();
                    int initialX = initialLocation.getX();
                    int initialY = initialLocation.getY();
                    putButtonWithPiece(initialY, initialX);
                    putButtonWithPiece(finalY, finalX);
                    initialLocation = null;
                    if(!ChessApplication.chessBoard.isWhitesTurn()){
                        WhitesTurnLabel.setBackground(greyBG);
                        BlacksTurnLabel.setBackground(greenBG);
                    }
                    else{
                        WhitesTurnLabel.setBackground(greenBG);
                        BlacksTurnLabel.setBackground(greyBG);
                    }
                }
                if(ChessApplication.chessBoard.getGameStatus()==GameStatus.WHITE_CHECKMATED){
//                    BoardGridPane.setVisible(false);
//                    BoardGridPane.setManaged(false);
                    WinnerLabel.setVisible(true);
                    WinnerLabel.setManaged(true);
                    turnHbox.setManaged(false);
                    turnHbox.setVisible(false);
                    ErrorLabel.setManaged(false);
                    ErrorLabel.setVisible(false);
                    WinnerLabel.setText("Black Won!");
                }
                else if(ChessApplication.chessBoard.getGameStatus()==GameStatus.BLACK_CHECKMATED){
//                    BoardGridPane.setVisible(false);
//                    BoardGridPane.setManaged(false);
                    WinnerLabel.setVisible(true);
                    WinnerLabel.setManaged(true);
                    turnHbox.setManaged(false);
                    turnHbox.setVisible(false);
                    ErrorLabel.setManaged(false);
                    ErrorLabel.setVisible(false);
                    WinnerLabel.setText("White Won!");
                }
            }
            catch (IllegalArgumentException e){
                initialLocation=null;
                ErrorLabel.setText("Illegal Move");
            }
        }
    };

    private String getPieceOnSquare(int x, int y) {
        Square square = ChessApplication.chessBoard.getSquareFromCoordinates(new Coordinates(x,y));
        return square.toString();
    }
}