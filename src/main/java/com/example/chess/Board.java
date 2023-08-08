package com.example.chess;

import java.util.HashMap;

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

    public Square getSquareFromCoordinates(int x, int y){
        return boardMap.get(new Coordinates(x,y));
    }

    public void movePiece(Coordinates initialCor, Coordinates finalCor){
        ChessPiece piece = boardMap.get(initialCor).getSquarePiece();
        if (piece==null) throw new IllegalArgumentException();
        //if(!isValidMove(initialCor,finalCor)) throw new IllegalArgumentException();
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

    private boolean isValidMove(Coordinates initialCor, Coordinates finalCor) {
        ChessPiece piece = boardMap.get(initialCor).getSquarePiece();
        if(piecesAreSameColor(initialCor,finalCor)) return false;
        switch(piece.getPieceType()){
            case PAWN:
                return isValidPawnMove(initialCor,finalCor);
            case ROOK:
                return isValidRookMove(initialCor,finalCor);
            case QUEEN:
                return isValidQueenMove(initialCor,finalCor);
            case KNIGHT:
                return isValidKnightMove(initialCor,finalCor);
            case BISHOP:
                return isValidBishopMove(initialCor,finalCor);
            case KING:
                return isValidKingMove(initialCor,finalCor);
        }
        return false;
    }

    private boolean isValidKingMove(Coordinates initialCor, Coordinates finalCor) {
        ChessPiece piece = boardMap.get(initialCor).getSquarePiece();
        return false;
    }

    private boolean isValidBishopMove(Coordinates initialCor, Coordinates finalCor) {
        ChessPiece piece = boardMap.get(initialCor).getSquarePiece();
        return false;
    }

    private boolean isValidKnightMove(Coordinates initialCor, Coordinates finalCor) {
        ChessPiece piece = boardMap.get(initialCor).getSquarePiece();
        return false;
    }

    private boolean isValidQueenMove(Coordinates initialCor, Coordinates finalCor) {
        ChessPiece piece = boardMap.get(initialCor).getSquarePiece();
        return false;
    }

    private boolean isValidRookMove(Coordinates initialCor, Coordinates finalCor) {
        ChessPiece piece = boardMap.get(initialCor).getSquarePiece();
        return false;
    }

    private boolean isValidPawnMove(Coordinates initialCor, Coordinates finalCor) {
        ChessPiece piece = boardMap.get(initialCor).getSquarePiece();
        if(piece.getPieceColor()==PieceColor.WHITE){

        }
        else{

        }
        return false;
    }

    private boolean piecesAreSameColor(Coordinates initialCor, Coordinates finalCor) {
        if(boardMap.get(initialCor).getSquarePiece().getPieceColor()==boardMap.get(finalCor).getSquarePiece().getPieceColor()) return true;
        else return false;
    }
}
