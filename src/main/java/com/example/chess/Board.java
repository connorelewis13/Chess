package com.example.chess;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private HashMap<Coordinates,Square> boardMap;
    //1,1 will be bottom left
    private boolean whitesTurn;
    private ArrayList<Coordinates[]> possibleMoves;

    public Board(){
        whitesTurn=true;
        this.boardMap = new HashMap<>();
        putNullSquares();
        possibleMoves = new ArrayList<>();
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


    public void getAllLegalMoves(){
        possibleMoves.clear();
        PieceColor pieceColor;
        if(whitesTurn){ pieceColor=PieceColor.WHITE;} else{pieceColor=PieceColor.BLACK;}
        ArrayList<Coordinates> pieceCoordinates= getAllPieceSquares(pieceColor);
        for(Coordinates coordinates:pieceCoordinates){
            addPieceMoves(coordinates);
        }
    }

    private void addPieceMoves(Coordinates coordinates) {
        ChessPiece chessPiece = boardMap.get(coordinates).getSquarePiece();
        switch(chessPiece.getPieceType()){
            case PAWN:
                addPawnMoves(coordinates);
            case ROOK:
                addRookMoves(coordinates);
            case QUEEN:
                addQueenMoves(coordinates);
            case KNIGHT:
                addKnightMoves(coordinates);
            case BISHOP:
                addBishopMoves(coordinates);
            case KING:
                addValidKingMove();
        }
    }

    private void addPawnMoves(Coordinates coordinates) {
        ChessPiece chessPiece = boardMap.get(coordinates).getSquarePiece();
        int yDirection = 1;
        PieceColor oppositeColor = PieceColor.WHITE;
        if(chessPiece.isWhite()){
            yDirection = -1;
            oppositeColor = PieceColor.BLACK;
        }
        Coordinates possibleCoordinates = new Coordinates(coordinates.getX(),coordinates.getY()+yDirection);
        if(possibleCoordinates.isInBounds()){
            if(!hasPiece(possibleCoordinates)){
                addMove(coordinates, possibleCoordinates);
            }
        }
        possibleCoordinates = new Coordinates(coordinates.getX(),coordinates.getY()+2*yDirection);
        if(possibleCoordinates.isInBounds()){
            if(!hasPiece(possibleCoordinates)){
                addMove(coordinates, possibleCoordinates);
            }
        }
        possibleCoordinates= new Coordinates(coordinates.getX()+1,coordinates.getY()+yDirection);
        if(possibleCoordinates.isInBounds()){
            if(hasPiece(possibleCoordinates,oppositeColor)){
                addMove(coordinates, possibleCoordinates);
            }
        }
        possibleCoordinates = new Coordinates(coordinates.getX()-1,coordinates.getY()+yDirection);
        if(possibleCoordinates.isInBounds()){
            if(hasPiece(possibleCoordinates,oppositeColor)){
                addMove(coordinates, possibleCoordinates);
            }
        }
    }

    private void addMove(Coordinates coordinates, Coordinates possibleCoordinates) {
        possibleMoves.add(new Coordinates[]{coordinates, possibleCoordinates});
    }

    private boolean hasPiece(Coordinates coordinates){
        return boardMap.get(coordinates).getSquarePiece()!=null;
    }
    private boolean hasPiece(Coordinates coordinates, PieceColor pieceColor){
        ChessPiece piece = boardMap.get(coordinates).getSquarePiece();
        if(piece!=null){
            return piece.getPieceColor()==pieceColor;
        }
        return false;
    }
}
