package com.example.chess;

public class Square {
    private ChessPiece sqaurePiece;

    public Square(ChessPiece sqaurePiece){
        this.sqaurePiece = sqaurePiece;
    }

    public ChessPiece getSquarePiece() {
        return sqaurePiece;
    }

    public void setSqaurePiece(ChessPiece sqaurePiece) {
        this.sqaurePiece = sqaurePiece;
    }
    @Override
    public String toString(){
        if(sqaurePiece==null) return " ";
        return sqaurePiece + " ";
    }
}
