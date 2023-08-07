package com.example.chess;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private HashMap<Coordinates,Square> boardMap;
    //1,1 will be bottom left
    private boolean whitesTurn;

    public Board(){
        whitesTurn=true;
        this.boardMap = new HashMap<>();
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                this.boardMap.put(new Coordinates(x,y),new Square(null));
            }
        }
    }
    public boolean getWhitesTurn(){
        return this.whitesTurn;
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
        for(int x=0;x<8;x++){
            putPiece(x,0,new ChessPiece(PieceColor.BLACK,pieceTypes[x]));
            putPiece(x,7,new ChessPiece(PieceColor.WHITE,pieceTypes[x]));
        }
    }

    private void setPawns() {
        for(int x=0;x<8;x++){
            putPiece(x,6, new ChessPiece(PieceColor.WHITE,PieceType.PAWN));
            putPiece(x,1, new ChessPiece(PieceColor.BLACK,PieceType.PAWN));
        }
    }
    @Override
    public String toString(){
        String returnString = "";
        if(whitesTurn){
            returnString+="White's Turn \n";
        }
        else{
            returnString+="Black's Turn \n";
        }
        for(int y=0;y<=7;y++){
            for(int x=0;x<=7;x++){
                returnString += this.boardMap.get(new Coordinates(x,y)).toString();
            }
            returnString+="\n";
        }
        return returnString;
    }

    public Square getSquareFromCoordinates(int x, int y){
        return boardMap.get(new Coordinates(x,y));
    }

    public void movePiece(Coordinates initialCor, Coordinates finalCor){
        ChessPiece piece = boardMap.get(initialCor).getSqaurePiece();
        if(whitesTurn && piece.getPieceColor()==PieceColor.WHITE){
            boardMap.put(initialCor,new Square(null));
            boardMap.put(finalCor,new Square(piece));
            whitesTurn=false;
        }
        else if (!whitesTurn && piece.getPieceColor()==PieceColor.BLACK) {
            boardMap.put(initialCor,new Square(null));
            boardMap.put(finalCor,new Square(piece));
            whitesTurn=true;
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}
