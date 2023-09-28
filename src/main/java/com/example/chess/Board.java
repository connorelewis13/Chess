package com.example.chess;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private HashMap<Coordinates,Square> boardMap;
    //1,1 will be bottom left
    private boolean whitesTurn;
    private ArrayList<Coordinates[]> turnPossibleMoves;
    private ArrayList<Coordinates[]> notTurnPossibleMoves;

    private boolean whiteKingChecked;
    private boolean blackKingChecked;

    private Coordinates whiteKingCoordinates;
    private Coordinates blackKingCoordinates;

    public Board(){
        whitesTurn=true;
        this.boardMap = new HashMap<>();
        putNullSquares();
        turnPossibleMoves = new ArrayList<>();
        notTurnPossibleMoves = new ArrayList<>();
        blackKingCoordinates=null;
        whiteKingCoordinates=null;
        whiteKingChecked=false;
        blackKingChecked=false;
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
        getAllLegalMoves();
        blackKingCoordinates= new Coordinates(4,0);
        whiteKingCoordinates= new Coordinates(4,7);
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

    public ArrayList<Coordinates> getAllPieceCoordinates(){
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

    public ArrayList<Coordinates> getAllPieceCoordinates(PieceColor pieceColor){
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
        turnPossibleMoves.clear();
        notTurnPossibleMoves.clear();
        PieceColor pieceColor;
        Coordinates kingCoordinates;
        if(whitesTurn){ pieceColor=PieceColor.WHITE; kingCoordinates=whiteKingCoordinates;} else{pieceColor=PieceColor.BLACK; kingCoordinates = blackKingCoordinates;}
        ArrayList<Coordinates> notTurnPieceCoordinates = getAllPieceCoordinates(pieceColor.oppositeColor());
        for(Coordinates coordinates:notTurnPieceCoordinates){
            addPieceMoves(coordinates);
        }
        for(Coordinates[] move:turnPossibleMoves){
            notTurnPossibleMoves.add(move);
        }
        turnPossibleMoves.clear();
        for(Coordinates[] move:notTurnPossibleMoves){
            if(move[1].equals(kingCoordinates)){
                setKingChecked(pieceColor,true);
            }
        }
        ArrayList<Coordinates> turnPieceCoordinates= getAllPieceCoordinates(pieceColor);
        for(Coordinates coordinates:turnPieceCoordinates){
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
                addValidKingMove(coordinates);
        }
    }

    private void addQueenMoves(Coordinates coordinates) {
        addBishopMoves(coordinates);
        addRookMoves(coordinates);
    }

    private void addBishopMoves(Coordinates coordinates) {
        PieceColor pieceColor = getSquareFromCoordinates(coordinates).getSquarePiece().getPieceColor();
        ArrayList<Coordinates> possibleCoordinates = new ArrayList<>();
        for(int n=1;n<8;n++){
            possibleCoordinates.add(new Coordinates(coordinates.getX()+n, coordinates.getY()+n));
            possibleCoordinates.add(new Coordinates(coordinates.getX()-n, coordinates.getY()-n));
            possibleCoordinates.add(new Coordinates(coordinates.getX()+n, coordinates.getY()-n));
            possibleCoordinates.add(new Coordinates(coordinates.getX()-n, coordinates.getY()+n));
        }
        for(Coordinates possibleCoordinate: possibleCoordinates){
            if(possibleCoordinate.isInBounds() && !hasPiece(possibleCoordinate,pieceColor)) addMove(coordinates,possibleCoordinate);
        }
    }

    private void addKnightMoves(Coordinates coordinates) {
        PieceColor pieceColor = getSquareFromCoordinates(coordinates).getSquarePiece().getPieceColor();
        Coordinates[] possibleCoordinates = new Coordinates[]{
                new Coordinates(coordinates.getX()-1, coordinates.getY()-2),
                new Coordinates(coordinates.getX()+1, coordinates.getY()-2),
                new Coordinates(coordinates.getX()+2, coordinates.getY()-1),
                new Coordinates(coordinates.getX()-2, coordinates.getY()-1),
                new Coordinates(coordinates.getX()+1, coordinates.getY()+2),
                new Coordinates(coordinates.getX()-1, coordinates.getY()+2),
                new Coordinates(coordinates.getX()-2, coordinates.getY()+1),
                new Coordinates(coordinates.getX()+2, coordinates.getY()+1),
        };
        for(Coordinates possibleCoordinate:possibleCoordinates){
            if(possibleCoordinate.isInBounds() && !hasPiece(possibleCoordinate,pieceColor)) addMove(coordinates,possibleCoordinate);
        }
    }

    private void addValidKingMove(Coordinates coordinates) {
        PieceColor pieceColor = getSquareFromCoordinates(coordinates).getSquarePiece().getPieceColor();
        Coordinates[] possibleCoordinates = new Coordinates[]{
                new Coordinates(coordinates.getX(), coordinates.getY()-1),
                new Coordinates(coordinates.getX(), coordinates.getY()+1),
                new Coordinates(coordinates.getX()-1, coordinates.getY()-1),
                new Coordinates(coordinates.getX()-1, coordinates.getY()+1),
                new Coordinates(coordinates.getX()+1, coordinates.getY()-1),
                new Coordinates(coordinates.getX()+1, coordinates.getY()+1),
                new Coordinates(coordinates.getX()-1, coordinates.getY()),
                new Coordinates(coordinates.getX()+1, coordinates.getY()),
        };
        for(Coordinates possibleCoordinate:possibleCoordinates){
            if(possibleCoordinate.isInBounds() && !hasPiece(possibleCoordinate,pieceColor)) addMove(coordinates,possibleCoordinate);
        }
    }

    private void addRookMoves(Coordinates coordinates) {
        PieceColor pieceColor = getSquareFromCoordinates(coordinates).getSquarePiece().getPieceColor();
        for(int i=0; i<8;i++){
            Coordinates possibleCoordinates = new Coordinates(coordinates.getX(),i);
            if(!possibleCoordinates.equals(coordinates) && !hasPiece(possibleCoordinates,pieceColor)){
                addMove(coordinates,possibleCoordinates);
            }
            possibleCoordinates = new Coordinates(i,coordinates.getY());
            if(!possibleCoordinates.equals(coordinates) && !hasPiece(possibleCoordinates,pieceColor)){
                addMove(coordinates,possibleCoordinates);
            }
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
        turnPossibleMoves.add(new Coordinates[]{coordinates, possibleCoordinates});
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

    public void setKingChecked(PieceColor pieceColor, Boolean boo){
        if (pieceColor==PieceColor.WHITE){
            whiteKingChecked=boo;
        }
        else if(pieceColor==PieceColor.BLACK){
            blackKingChecked=boo;
        }
    }
}
