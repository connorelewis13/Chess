package com.example.chess;

import java.util.HashMap;

public class Board {
    public HashMap<int[],Square> boardMap;
    //1,1 will be bottom left
    public Board(){
        this.boardMap = new HashMap<>();
        for(int x=1;x<=8;x++){
            for(int y=1;y<=8;y++){
                boardMap.put(new int[]{x,y},new Square(null));
            }
        }
    }
    private void putPiece(int x, int y,ChessPiece chessPiece){
        boardMap.put(new int[]{x,y},new Square(chessPiece));
    }
    
    private void resetBoard(){
        setPawns();
        setSpecialPieces();
    }

    private void setSpecialPieces() {
    }

    private void setPawns() {
    }
}
