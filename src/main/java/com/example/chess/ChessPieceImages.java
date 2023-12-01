package com.example.chess;

import javafx.scene.image.Image;

//Images provided by CreativeCommons. No changes were made.
//Link to license: https://creativecommons.org/licenses/by-sa/3.0/deed.en

public class ChessPieceImages {
    private Image wPawn= new Image(ChessController.class.getResourceAsStream("WhitePawn.png"));
    private Image bPawn= new Image(ChessController.class.getResourceAsStream("BlackPawn.png"));
    private Image wKing= new Image(ChessController.class.getResourceAsStream("WhiteKing.png"));
    private Image bKing= new Image(ChessController.class.getResourceAsStream("BlackKing.png"));
    private Image wKnight= new Image(ChessController.class.getResourceAsStream("WhiteKnight.png"));
    private Image bKnight= new Image(ChessController.class.getResourceAsStream("BlackKnight.png"));
    private Image wQueen= new Image(ChessController.class.getResourceAsStream("WhiteQueen.png"));
    private Image bQueen= new Image(ChessController.class.getResourceAsStream("BlackQueen.png"));
    private Image wBishop= new Image(ChessController.class.getResourceAsStream("WhiteBishop.png"));
    private Image bBishop= new Image(ChessController.class.getResourceAsStream("BlackBishop.png"));
    private Image wRook= new Image(ChessController.class.getResourceAsStream("WhiteRook.png"));
    private Image bRook= new Image(ChessController.class.getResourceAsStream("BlackRook.png"));

    public Image getPieceImage(ChessPiece piece){
        PieceType pt = piece.getPieceType();
        if(piece.isWhite()){
            switch (pt) {
                case PAWN:
                    return wPawn;
                case KING:
                    return wKing;
                case QUEEN:
                    return wQueen;
                case ROOK:
                    return wRook;
                case KNIGHT:
                    return wKnight;
                case BISHOP:
                    return wBishop;
            }
        }
        else if(piece.isBlack()){
            switch (pt){
                case PAWN:
                    return bPawn;
                case KING:
                    return bKing;
                case QUEEN:
                    return bQueen;
                case ROOK:
                    return bRook;
                case KNIGHT:
                    return bKnight;
                case BISHOP:
                    return bBishop;
            }
        }
        return bBishop;
    }
}
