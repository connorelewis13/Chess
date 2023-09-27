package com.example.chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ArrayList<Coordinates> getAllPieceSquares(){
        ArrayList<Coordinates> pieceCoordinates = new ArrayList<>();
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                Square square = this.boardMap.get(new Coordinates(x,y));
                if(square.getSquarePiece()!=null){
                    pieceCoordinates.add(new Coordinates(x,y));
                }
            }
        }
        return pieceCoordinates;
    }

    public ArrayList<Coordinates> getAllPieceSquares(PieceColor pieceColor){
        ArrayList<Coordinates> pieceCoordinates = new ArrayList<>();
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                Square square = this.boardMap.get(new Coordinates(x,y));
                if(square.getSquarePiece()!=null){
                    if(square.getSquarePiece().getPieceColor()==pieceColor){
                        pieceCoordinates.add(new Coordinates(x,y));
                    }
                }
            }
        }
        return pieceCoordinates;
    }


    public ArrayList<Coordinates[]> getAllLegalMoves(){
        PieceColor pieceColor;
        if(whitesTurn){ pieceColor=PieceColor.WHITE;} else{pieceColor=PieceColor.BLACK;}
        ArrayList<Coordinates> pieceCoordinates= getAllPieceSquares(pieceColor);
        for(Coordinates coordinates:pieceCoordinates){
            ArrayList<Coordinates[]> pieceMoves = getPieceMoves(coordinates);
        }
        return null;
    }

    private ArrayList<Coordinates[]> getPieceMoves(Coordinates coordinates) {
        ChessPiece chessPiece = boardMap.get(coordinates).getSquarePiece();
        switch(chessPiece.getPieceType()){
            case PAWN:
                return getPawnMoves(coordinates);
            case ROOK:
                return getRookMoves(coordinates);
            case QUEEN:
                return getQueenMoves(coordinates);
            case KNIGHT:
                return getKnightMoves(coordinates);
            case BISHOP:
                return getBishopMoves(coordinates);
            case KING:
                return isValidKingMove();
        }
    }

    private ArrayList<Coordinates[]> getPawnMoves(Coordinates coordinates) {
        ChessPiece chessPiece = boardMap.get(coordinates).getSquarePiece();
        ArrayList<Coordinates[]> movesArrayList = new ArrayList<>();
        if(chessPiece.getPieceColor()==PieceColor.WHITE){
            if(boardMap.get(new Coordinates(coordinates.getX(),coordinates.getY()-1)).getSquarePiece()==null){
                movesArrayList.add(new Coordinates[]{coordinates,new Coordinates(coordinates.getX(),coordinates.getY()-1)});
            }
            if(boardMap.get(new Coordinates(coordinates.getX(),coordinates.getY()-2)).getSquarePiece()==null){
                movesArrayList.add(new Coordinates[]{coordinates,new Coordinates(coordinates.getX(),coordinates.getY()-2)});
            }
            if(boardMap.get(new Coordinates(coordinates.getX()+1,coordinates.getY()-1)).getSquarePiece()!=null){
                if(boardMap.get(new Coordinates(coordinates.getX()+1,coordinates.getY()-1)).getSquarePiece().getPieceColor()==PieceColor.BLACK){
                    movesArrayList.add(new Coordinates[]{coordinates,new Coordinates(coordinates.getX()+1,coordinates.getY()-1)});
                }
            }
            if(boardMap.get(new Coordinates(coordinates.getX()+1,coordinates.getY()+1)).getSquarePiece()!=null){
                if(boardMap.get(new Coordinates(coordinates.getX()+1,coordinates.getY()+1)).getSquarePiece().getPieceColor()==PieceColor.BLACK){
                    movesArrayList.add(new Coordinates[]{coordinates,new Coordinates(coordinates.getX()+1,coordinates.getY()+1)});
                }
            }
        }
        else{
            possibleCoordinates.add(new Coordinates(coordinates.getX(),coordinates.getY()+1));
            possibleCoordinates.add(new Coordinates(coordinates.getX(),coordinates.getY()+2));
            possibleCoordinates.add(new Coordinates(coordinates.getX()+1,coordinates.getY()+1));
            possibleCoordinates.add(new Coordinates(coordinates.getX()-1,coordinates.getY()+1));
        }
        return null;
    }
}
