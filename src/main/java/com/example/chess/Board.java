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
        for(int i=1;i<=8;i++){
            putPiece(i,2, new ChessPiece(PieceColor.WHITE,PieceType.PAWN));
            putPiece(i,7, new ChessPiece(PieceColor.BLACK,PieceType.PAWN));
        }
    }
}
