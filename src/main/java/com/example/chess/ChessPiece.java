package com.example.chess;

public class ChessPiece {
    private int xLocation;
    private int yLocation;
    private PieceType pieceType;
    private PieceColor pieceColor;
    public int getXLocation() {
        return xLocation;
    }
    public void setXLocation(int xLocation) {
        this.xLocation = xLocation;
    }
    public int getYLocation() {
        return yLocation;
    }
    public void setYLocation(int yLocation) {
        this.yLocation = yLocation;
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

    public void move(int userX,int userY){
        setXLocation(userX);
        setYLocation(userY);
    }
}
