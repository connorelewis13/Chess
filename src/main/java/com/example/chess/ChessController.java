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
    @FXML private HBox changePawnHbox;
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
    @FXML private Button queenButton;
    @FXML private Button rookButton;
    @FXML private Button bishopButton;
    @FXML private Button knightButton;


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
        changePawnHbox.setManaged(false);
        changePawnHbox.setVisible(false);
        for(int y = 0;y<8;y++){
            for(int x=0;x<8;x++){
                putButtonWithPiece(x, y);
            }
        }
    }

    private void putButtonWithPiece(int x, int y) {
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
                if(ChessApplication.chessBoard.getPassedPawn()==(null) && ChessApplication.chessBoard.getGameStatus()==GameStatus.GAME_ON){
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
                        putButtonWithPiece(initialX, initialY);
                        putButtonWithPiece(finalX, finalY);
                        if(Math.abs(finalY-initialY)==1 && Math.abs(finalX-initialX)==1){
                            putButtonWithPiece(finalX, initialY);
                        }
                        if(Math.abs(finalX-initialX)==2 && finalY==initialY){
                            for(int i = 0;i<7;i++){
                                putButtonWithPiece(i,initialY);
                            }
                        }
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
                }
                if(ChessApplication.chessBoard.getPassedPawn()!=null){
                    changePawnHbox.setVisible(true);
                    changePawnHbox.setManaged(true);
                }
                if(ChessApplication.chessBoard.getGameStatus()==GameStatus.WHITE_CHECKMATED){
                    WinnerLabel.setVisible(true);
                    WinnerLabel.setManaged(true);
                    turnHbox.setManaged(false);
                    turnHbox.setVisible(false);
                    ErrorLabel.setManaged(false);
                    ErrorLabel.setVisible(false);
                    WinnerLabel.setText("Black Won!");
                }
                else if(ChessApplication.chessBoard.getGameStatus()==GameStatus.BLACK_CHECKMATED){
                    WinnerLabel.setVisible(true);
                    WinnerLabel.setManaged(true);
                    turnHbox.setManaged(false);
                    turnHbox.setVisible(false);
                    ErrorLabel.setManaged(false);
                    ErrorLabel.setVisible(false);
                    WinnerLabel.setText("White Won!");
                }
                else if(ChessApplication.chessBoard.getGameStatus()==GameStatus.STALEMATE){
                    WinnerLabel.setVisible(true);
                    WinnerLabel.setManaged(true);
                    turnHbox.setManaged(false);
                    turnHbox.setVisible(false);
                    ErrorLabel.setManaged(false);
                    ErrorLabel.setVisible(false);
                    WinnerLabel.setText("Stalemate");
                }
                //ErrorLabel.setText(""+ChessApplication.chessBoard.getPassedPawn());
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

    public void queenButtonPressed(ActionEvent actionEvent) {
        PieceColor pieceColor = PieceColor.WHITE;
        if(ChessApplication.chessBoard.isWhitesTurn()) pieceColor = PieceColor.BLACK;
        ChessApplication.chessBoard.putPiece(ChessApplication.chessBoard.getPassedPawn(), new ChessPiece(pieceColor,PieceType.QUEEN));
        putButtonWithPiece(ChessApplication.chessBoard.getPassedPawn().getX(), ChessApplication.chessBoard.getPassedPawn().getY());
        ChessApplication.chessBoard.setPassedPawn(null);
        changePawnHbox.setVisible(false);
        changePawnHbox.setManaged(false);
    }

    public void rookButtonPressed(ActionEvent actionEvent) {
        PieceColor pieceColor = PieceColor.WHITE;
        if(ChessApplication.chessBoard.isWhitesTurn()) pieceColor = PieceColor.BLACK;
        ChessApplication.chessBoard.putPiece(ChessApplication.chessBoard.getPassedPawn(), new ChessPiece(pieceColor,PieceType.ROOK));
        putButtonWithPiece(ChessApplication.chessBoard.getPassedPawn().getX(), ChessApplication.chessBoard.getPassedPawn().getY());
        ChessApplication.chessBoard.setPassedPawn(null);
        changePawnHbox.setVisible(false);
        changePawnHbox.setManaged(false);
    }

    public void knightButtonPressed(ActionEvent actionEvent) {
        PieceColor pieceColor = PieceColor.WHITE;
        if(ChessApplication.chessBoard.isWhitesTurn()) pieceColor = PieceColor.BLACK;
        ChessApplication.chessBoard.putPiece(ChessApplication.chessBoard.getPassedPawn(), new ChessPiece(pieceColor,PieceType.KNIGHT));
        putButtonWithPiece(ChessApplication.chessBoard.getPassedPawn().getX(), ChessApplication.chessBoard.getPassedPawn().getY());
        ChessApplication.chessBoard.setPassedPawn(null);
        changePawnHbox.setVisible(false);
        changePawnHbox.setManaged(false);
    }

    public void bishopButtonPressed(ActionEvent actionEvent) {
        PieceColor pieceColor = PieceColor.WHITE;
        if(ChessApplication.chessBoard.isWhitesTurn()) pieceColor = PieceColor.BLACK;
        ChessApplication.chessBoard.putPiece(ChessApplication.chessBoard.getPassedPawn(), new ChessPiece(pieceColor,PieceType.BISHOP));
        putButtonWithPiece(ChessApplication.chessBoard.getPassedPawn().getX(), ChessApplication.chessBoard.getPassedPawn().getY());
        ChessApplication.chessBoard.setPassedPawn(null);
        changePawnHbox.setVisible(false);
        changePawnHbox.setManaged(false);
    }
}