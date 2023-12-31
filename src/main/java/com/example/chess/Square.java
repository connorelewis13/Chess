package com.example.chess;

import java.util.ArrayList;

public class Square {
    private ChessPiece sqaurePiece;

    public Square(ChessPiece sqaurePiece){
        this.sqaurePiece = sqaurePiece;
    }

    public ChessPiece getSquarePiece() {
        return sqaurePiece;
    }

    @Override
    public String toString(){
        if(sqaurePiece==null) return "       ";
        return sqaurePiece + " ";
    }
}
