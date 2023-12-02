package com.example.chess;

public enum Color {
    WHITE,
    BLACK;

    public Color oppositeColor(){
        if(this==WHITE) return BLACK;
        else if(this==BLACK) return WHITE;
        else return null;
    }
}
