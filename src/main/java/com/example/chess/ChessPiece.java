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

    @Override
    public String toString(){
        if(this.pieceColor==PieceColor.BLACK) return "B." + this.pieceType+" ";
        else if(this.pieceColor==PieceColor.WHITE) return "W." + this.pieceType+" ";
        return "";
    }
    public boolean isWhite(){
        if(pieceColor==PieceColor.WHITE) return true;
        else return false;
    }
    public boolean isBlack(){
        if(pieceColor==PieceColor.BLACK) return true;
        else return false;
    }
    public boolean isSameColor(ChessPiece chessPiece){
        if(chessPiece==null) return false;
        if(this.pieceColor == chessPiece.getPieceColor()) return true;
        else return false;
    }
}
