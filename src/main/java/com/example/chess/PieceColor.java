package com.example.chess;

public enum PieceColor {
    WHITE,
    BLACK;

    public PieceColor oppositeColor(){
        if(this==WHITE) return BLACK;
        else if(this==BLACK) return WHITE;
        else return null;
    }
}
