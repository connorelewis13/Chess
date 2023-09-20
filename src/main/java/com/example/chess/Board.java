package com.example.chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
    private HashMap<Coordinates,Square> boardMap;
    //1,1 will be bottom left
    private boolean whitesTurn;

    public Board(){
        whitesTurn=true;
        this.boardMap = new HashMap<>();
        putNullSquares();
    }

    public void setWhitesTurn(boolean whitesTurn){
        this.whitesTurn=whitesTurn;
    }

    private void putNullSquares() {
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                this.boardMap.put(new Coordinates(x,y),new Square(null));
            }
        }
    }

    public boolean isWhitesTurn(){
        return this.whitesTurn;
    }
    public void putPiece(Coordinates coordinates,ChessPiece chessPiece){
        this.boardMap.put(coordinates,new Square(chessPiece));
    }
    
    public void resetBoard(){
        whitesTurn=true;
        putNullSquares();
        setPawns();
        setSpecialPieces();
    }

    private void setSpecialPieces() {
        PieceType[] pieceTypes = new PieceType[]{PieceType.ROOK,PieceType.KNIGHT,PieceType.BISHOP,PieceType.QUEEN,PieceType.KING,PieceType.BISHOP,PieceType.KNIGHT,PieceType.ROOK};
        for(int x=0;x<8;x++){
            putPiece(new Coordinates(x,0),new ChessPiece(PieceColor.BLACK,pieceTypes[x]));
            putPiece(new Coordinates(x,7),new ChessPiece(PieceColor.WHITE,pieceTypes[x]));
        }
    }

    private void setPawns() {
        for(int x=0;x<8;x++){
            putPiece(new Coordinates(x,6), new ChessPiece(PieceColor.WHITE,PieceType.PAWN));
            putPiece(new Coordinates(x,1), new ChessPiece(PieceColor.BLACK,PieceType.PAWN));
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

    public Square getSquareFromCoordinates(Coordinates coordinates){
        return boardMap.get(coordinates);
    }

    public ArrayList<Square> getAllPieceSquares(){
        ArrayList<Square> pieceSquaresArrayList = new ArrayList<>();
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                Square square = this.boardMap.get(new Coordinates(x,y));
                if(square.getSquarePiece()!=null){
                    pieceSquaresArrayList.add(square);
                }
            }
        }
        return pieceSquaresArrayList;
    }
    public ArrayList<Square> getAllPieceSquares(PieceColor pieceColor){
        ArrayList<Square> pieceSquaresArrayList = new ArrayList<>();
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                Square square = this.boardMap.get(new Coordinates(x,y));
                if(square.getSquarePiece()!=null && square.getSquarePiece().getPieceColor()==pieceColor){
                    pieceSquaresArrayList.add(square);
                }
            }
        }
        return pieceSquaresArrayList;
    }
}
