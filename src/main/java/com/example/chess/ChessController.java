package com.example.chess;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ChessController {
    @FXML private HBox changePawnHbox;
    @FXML private GridPane BoardGridPane;
    @FXML private Button StartButton;
    @FXML private Label ErrorLabel;
    @FXML private Label WinnerLabel;
    @FXML private HBox turnHbox;
    private Coordinates initialLocation;
    private Coordinates finalLocation;
    private Background greyBG = new Background(new BackgroundFill(javafx.scene.paint.Color.GREY, CornerRadii.EMPTY, Insets.EMPTY));
    private Background greenBG = new Background(new BackgroundFill(javafx.scene.paint.Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    @FXML private Label WhitesTurnLabel;
    @FXML private Label BlacksTurnLabel;
    @FXML private Button queenButton;
    @FXML private Button rookButton;
    @FXML private Button bishopButton;
    @FXML private Button knightButton;
    private ChessPieceImages chessPieceImages = new ChessPieceImages();


    public void StartButtonPressed(ActionEvent actionEvent) {
//        BoardGridPane.setVisible(true);
//        BoardGridPane.setManaged(true);
        ChessApplication.chessBoard.setCheckMated(null);
        turnHbox.setManaged(true);
        turnHbox.setVisible(true);
        ErrorLabel.setManaged(true);
        ErrorLabel.setVisible(true);
        ErrorLabel.setText("");
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
        //ChessApplication.chessBoard.printLegalMoves();
    }

    private void putButtonWithPiece(int x, int y) {
        Color squareColor = ChessApplication.chessBoard.getColorBoardMap().get(new Coordinates(x,y));
        Button button = new Button();
        button.setStyle("-fx-font-weight: bold; -fx-font-size: 10;");
        button.setMinHeight(70);
        button.setMaxHeight(70);
        button.setMinWidth(70);
        button.setMaxWidth(70);
        if(squareColor==Color.WHITE) button.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        if(squareColor==Color.BLACK) button.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setStyle("-fx-border-color: #000000; -fx-border-width: 1px;");
        //button.setBackground();
        //button.setText(getPieceOnSquare(x, y));
        try{
            button.setGraphic(new ImageView(getImageOnSquare(x,y)));
        }
        catch (NullPointerException e){
            button.setText(getPieceStringOnSquare(x, y));
        }
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
                        buttonPressed.setStyle("-fx-border-color: #7FFF00; -fx-border-width: 2px;");
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
                            for(int i = 0;i<8;i++){
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
                    //ChessApplication.chessBoard.printLegalMoves();
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
                putButtonWithPiece(initialLocation.getX(), initialLocation.getY());
                initialLocation=null;
                ErrorLabel.setText(e.getMessage());
            }
        }
    };

    private String getPieceStringOnSquare(int x, int y) {
        Square square = ChessApplication.chessBoard.getSquareFromCoordinates(new Coordinates(x,y));
        return square.toString();
    }
    private ChessPiece getChessPieceOnSquare(int x, int y){
        return ChessApplication.chessBoard.getSquareFromCoordinates(new Coordinates(x,y)).getSquarePiece();
    }
    private Image getImageOnSquare(int x, int y){
        ChessPiece chessPiece = getChessPieceOnSquare(x,y);
        //System.out.print(squareString);
        return chessPieceImages.getPieceImage(chessPiece);
    }

    public void queenButtonPressed(ActionEvent actionEvent) {
        Color color = Color.WHITE;
        if(ChessApplication.chessBoard.isWhitesTurn()) color = Color.BLACK;
        ChessApplication.chessBoard.putPiece(ChessApplication.chessBoard.getPassedPawn(), new ChessPiece(color,PieceType.QUEEN));
        putButtonWithPiece(ChessApplication.chessBoard.getPassedPawn().getX(), ChessApplication.chessBoard.getPassedPawn().getY());
        ChessApplication.chessBoard.setPassedPawn(null);
        changePawnHbox.setVisible(false);
        changePawnHbox.setManaged(false);
    }

    public void rookButtonPressed(ActionEvent actionEvent) {
        Color color = Color.WHITE;
        if(ChessApplication.chessBoard.isWhitesTurn()) color = Color.BLACK;
        ChessApplication.chessBoard.putPiece(ChessApplication.chessBoard.getPassedPawn(), new ChessPiece(color,PieceType.ROOK));
        putButtonWithPiece(ChessApplication.chessBoard.getPassedPawn().getX(), ChessApplication.chessBoard.getPassedPawn().getY());
        ChessApplication.chessBoard.setPassedPawn(null);
        changePawnHbox.setVisible(false);
        changePawnHbox.setManaged(false);
    }

    public void knightButtonPressed(ActionEvent actionEvent) {
        Color color = Color.WHITE;
        if(ChessApplication.chessBoard.isWhitesTurn()) color = Color.BLACK;
        ChessApplication.chessBoard.putPiece(ChessApplication.chessBoard.getPassedPawn(), new ChessPiece(color,PieceType.KNIGHT));
        putButtonWithPiece(ChessApplication.chessBoard.getPassedPawn().getX(), ChessApplication.chessBoard.getPassedPawn().getY());
        ChessApplication.chessBoard.setPassedPawn(null);
        changePawnHbox.setVisible(false);
        changePawnHbox.setManaged(false);
    }

    public void bishopButtonPressed(ActionEvent actionEvent) {
        Color color = Color.WHITE;
        if(ChessApplication.chessBoard.isWhitesTurn()) color = Color.BLACK;
        ChessApplication.chessBoard.putPiece(ChessApplication.chessBoard.getPassedPawn(), new ChessPiece(color,PieceType.BISHOP));
        putButtonWithPiece(ChessApplication.chessBoard.getPassedPawn().getX(), ChessApplication.chessBoard.getPassedPawn().getY());
        ChessApplication.chessBoard.setPassedPawn(null);
        changePawnHbox.setVisible(false);
        changePawnHbox.setManaged(false);
    }
}