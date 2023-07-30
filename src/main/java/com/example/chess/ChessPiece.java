package com.example.chess;

public class ChessPiece {
    private PieceType pieceType;
    private PieceColor pieceColor;

    public ChessPiece(PieceColor pieceColor,PieceType pieceType){
        this.pieceColor=pieceColor;
        this.pieceType=pieceType;
    }
    public PieceType getPieceType() {
        return pieceType;
    }
    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }
    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }
}
