package com.example.chess;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private HashMap<Coordinates,Square> boardMap;
    //1,1 will be bottom left
    public Board(){
        this.boardMap = new HashMap<>();
        for(int x=1;x<9;x++){
            for(int y=1;y<9;y++){
                //this.boardMap.put(new Coordinates(x,y),new Square(new ChessPiece(PieceColor.WHITE,PieceType.PAWN)));
                this.boardMap.put(new Coordinates(x,y),new Square(null));
            }
        }
    }
    private void putPiece(int x, int y,ChessPiece chessPiece){
        this.boardMap.put(new Coordinates(x,y),new Square(chessPiece));
    }
    
    public void resetBoard(){
        setPawns();
        setSpecialPieces();
    }

    private void setSpecialPieces() {
        PieceType[] pieceTypes = new PieceType[]{PieceType.ROOK,PieceType.KNIGHT,PieceType.BISHOP,PieceType.QUEEN,PieceType.KING,PieceType.BISHOP,PieceType.KNIGHT,PieceType.ROOK};
        for(int x=1;x<=8;x++){
            putPiece(x,1,new ChessPiece(PieceColor.WHITE,pieceTypes[x-1]));
            putPiece(x,8,new ChessPiece(PieceColor.BLACK,pieceTypes[x-1]));
        }
    }

    private void setPawns() {
        for(int x=1;x<=8;x++){
            putPiece(x,2, new ChessPiece(PieceColor.WHITE,PieceType.PAWN));
            putPiece(x,7, new ChessPiece(PieceColor.BLACK,PieceType.PAWN));
        }
    }
    @Override
    public String toString(){
        String returnString = "";
        for(int x=1;x<9;x++){
            for(int y=1;y<9;y++){
                returnString += this.boardMap.get(new Coordinates(x,y)).toString();
            }
            returnString+="\n";
        }
        return returnString;
    }
}
