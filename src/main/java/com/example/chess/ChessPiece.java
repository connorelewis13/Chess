package com.example.chess;

public class ChessPiece {
    private PieceType pieceType;
    private Color color;

    public ChessPiece(Color color, PieceType pieceType){
        this.color = color;
        this.pieceType=pieceType;
    }
    public PieceType getPieceType() {
        return pieceType;
    }
    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public Color getPieceColor() {
        return color;
    }
    public void setPieceColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString(){
        if(this.color == Color.BLACK) return "B." + this.pieceType+" ";
        else if(this.color == Color.WHITE) return "W." + this.pieceType+" ";
        return "";
    }
    public boolean isWhite(){
        if(color == Color.WHITE) return true;
        else return false;
    }
    public boolean isBlack(){
        if(color == Color.BLACK) return true;
        else return false;
    }
    public boolean isSameColor(ChessPiece chessPiece){
        if(chessPiece==null) return false;
        if(this.color == chessPiece.getPieceColor()) return true;
        else return false;
    }
}
