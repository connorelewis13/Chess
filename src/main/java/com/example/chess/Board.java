package com.example.chess;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private HashMap<Coordinates,Square> boardMap;
    private HashMap<Coordinates,Color> colorBoardMap;
    private boolean checkingIfBeingPutInCheck;
    private boolean whitesTurn;
    private ArrayList<Coordinates[]> possibleMoves;
    private ArrayList<Coordinates[]> legalMoves;

    private boolean whiteKingChecked;
    private boolean blackKingChecked;

    private Coordinates whiteKingCoordinates;
    private Coordinates blackKingCoordinates;
    private GameStatus gameStatus;
    private Coordinates twoSpacePawn;

    private Coordinates passedPawn;

    private boolean whiteKingHasMoved;
    private boolean white07RookHasMoved;
    private boolean white77RookHasMoved;
    private boolean blackKingHasMoved;
    private boolean black00RookHasMoved;
    private boolean black70RookHasMoved;


    public Board(){
        whitesTurn=true;
        checkingIfBeingPutInCheck = false;
        this.boardMap = new HashMap<>();
        this.colorBoardMap = new HashMap<>();
        putNullSquares();
        putColors();
        possibleMoves = new ArrayList<>();
        legalMoves = new ArrayList<>();
        blackKingCoordinates=null;
        whiteKingCoordinates=null;
        whiteKingChecked=false;
        blackKingChecked=false;
        gameStatus = GameStatus.GAME_ON;
        twoSpacePawn = null;
        passedPawn=null;
        whiteKingHasMoved=false;
        white07RookHasMoved =false;
        white77RookHasMoved =false;
        blackKingHasMoved=false;
        black00RookHasMoved =false;
        black70RookHasMoved =false;
    }

    public HashMap<Coordinates, Color> getColorBoardMap() {
        return colorBoardMap;
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
    private void putColors(){
        Color initialColor = Color.WHITE;
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                this.colorBoardMap.put(new Coordinates(x,y), initialColor);
                initialColor = initialColor.oppositeColor();
            }
            initialColor = initialColor.oppositeColor();
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
        this.boardMap.clear();
        putNullSquares();
        possibleMoves.clear();
        legalMoves.clear();
        whiteKingChecked=false;
        blackKingChecked=false;
        gameStatus = GameStatus.GAME_ON;
        twoSpacePawn = null;
        passedPawn=null;
        whitesTurn=true;
        setPawns();
        setSpecialPieces();
        setLegalMoves();
        blackKingCoordinates= new Coordinates(4,0);
        whiteKingCoordinates= new Coordinates(4,7);
        whiteKingHasMoved=false;
        white07RookHasMoved =false;
        white77RookHasMoved =false;
        blackKingHasMoved=false;
        black00RookHasMoved =false;
        black70RookHasMoved =false;
    }

    private void setSpecialPieces() {
        PieceType[] pieceTypes = new PieceType[]{PieceType.ROOK,PieceType.KNIGHT,PieceType.BISHOP,PieceType.QUEEN,PieceType.KING,PieceType.BISHOP,PieceType.KNIGHT,PieceType.ROOK};
        for(int x=0;x<8;x++){
            putPiece(new Coordinates(x,0),new ChessPiece(Color.BLACK,pieceTypes[x]));
            putPiece(new Coordinates(x,7),new ChessPiece(Color.WHITE,pieceTypes[x]));
        }
    }

    private void setPawns() {
        for(int x=0;x<8;x++){
            putPiece(new Coordinates(x,6), new ChessPiece(Color.WHITE,PieceType.PAWN));
            putPiece(new Coordinates(x,1), new ChessPiece(Color.BLACK,PieceType.PAWN));
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

    private ArrayList<Coordinates> getAllPieceCoordinates(Color color){
        ArrayList<Coordinates> pieceCoordinates = new ArrayList<>();
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                Square square = this.boardMap.get(new Coordinates(x,y));
                if(square.getSquarePiece()!=null){
                    if(square.getSquarePiece().getPieceColor()== color){
                        pieceCoordinates.add(new Coordinates(x,y));
                    }
                }
            }
        }
        return pieceCoordinates;
    }

    public void setLegalMoves(){
        possibleMoves.clear();
        legalMoves.clear();
        ArrayList<Coordinates[]> notTurnMoves1 = new ArrayList<>();
        ArrayList<Coordinates[]> turnMoves1 = new ArrayList<>();
        Color color;
        Coordinates kingCoordinates;
        if(whitesTurn){ color = Color.WHITE; kingCoordinates=whiteKingCoordinates;} else{
            color = Color.BLACK; kingCoordinates = blackKingCoordinates;}
        ArrayList<Coordinates> notTurnPieceCoordinates = getAllPieceCoordinates(color.oppositeColor());
        //printCoordinatesList(notTurnPieceCoordinates);
        for(Coordinates coordinates:notTurnPieceCoordinates){
            addPieceMoves(coordinates);
        }
        for(Coordinates[] move: possibleMoves){
            notTurnMoves1.add(move);
        }
        possibleMoves.clear();
        setKingChecked(color,false);
        for(Coordinates[] move:notTurnMoves1){
            if(move[1].equals(kingCoordinates)){
                setKingChecked(color,true);
                System.out.println(getKingChecked(color));
            }
        }
        ArrayList<Coordinates> turnPieceCoordinates= getAllPieceCoordinates(color);
        for(Coordinates coordinates:turnPieceCoordinates){
            addPieceMoves(coordinates);
        }
        for(Coordinates[] move: possibleMoves){
                turnMoves1.add(move);
        }
        for(Coordinates[] move: turnMoves1){
            if(doesntPutKingInCheck(move, color)){
                legalMoves.add(move);
            }
        }
    }

    public void setStaleMate() {
        gameStatus=GameStatus.STALEMATE;
    }

    private boolean doesntPutKingInCheck(Coordinates[] move, Color color) {
        if(checkingIfBeingPutInCheck){
            return true;
        }
        else{
            possibleMoves.clear();
            Boolean returnBool = true;
            ChessPiece piece = getSquareFromCoordinates(move[0]).getSquarePiece();
            ChessPiece piece2 = getSquareFromCoordinates(move[1]).getSquarePiece();
            putPiece(move[1],piece);
            putPiece(move[0],null);
            if(piece.getPieceType()==PieceType.KING){
                setKingCoordinates(color,move[1]);
            }
            ArrayList<Coordinates> notTurnPieceCoordinates = getAllPieceCoordinates(color.oppositeColor());
            checkingIfBeingPutInCheck=true;
            for(Coordinates coordinates:notTurnPieceCoordinates){
                addPieceMoves(coordinates);
            }
            checkingIfBeingPutInCheck=false;
            for(Coordinates[] move1 : possibleMoves){
                if(move1[1].equals(getKingCoordinates(color))) returnBool = false;
            }
            putPiece(move[1],piece2);
            putPiece(move[0],piece);
            if(piece.getPieceType()==PieceType.KING){
                setKingCoordinates(color,move[0]);
            }
            possibleMoves.clear();
            return returnBool;
        }
    }

    private void addPieceMoves(Coordinates coordinates) {
        ChessPiece chessPiece = boardMap.get(coordinates).getSquarePiece();
        switch(chessPiece.getPieceType()){
            case PAWN:
                addPawnMoves(coordinates);
                break;
            case ROOK:
                addRookMoves(coordinates);
                break;
            case QUEEN:
                addQueenMoves(coordinates);
                break;
            case KNIGHT:
                addKnightMoves(coordinates);
                break;
            case BISHOP:
                addBishopMoves(coordinates);
                break;
            case KING:
                addKingMoves(coordinates);
                break;
        }
    }

    private void addQueenMoves(Coordinates coordinates) {
        addBishopMoves(coordinates);
        addRookMoves(coordinates);
    }

    private void addBishopMoves(Coordinates coordinates) {
        Color color = getSquareFromCoordinates(coordinates).getSquarePiece().getPieceColor();
        ArrayList<Coordinates> possibleCoordinates = new ArrayList<>();
        for(int n=1;n<8;n++){
            possibleCoordinates.add(new Coordinates(coordinates.getX()+n, coordinates.getY()+n));
            possibleCoordinates.add(new Coordinates(coordinates.getX()-n, coordinates.getY()-n));
            possibleCoordinates.add(new Coordinates(coordinates.getX()+n, coordinates.getY()-n));
            possibleCoordinates.add(new Coordinates(coordinates.getX()-n, coordinates.getY()+n));
        }
        for(Coordinates possibleCoordinate: possibleCoordinates){
            if(possibleCoordinate.isInBounds() && !hasPiece(possibleCoordinate, color) && noPiecesInBetween(coordinates,possibleCoordinate)) addMove(coordinates,possibleCoordinate);
        }
    }

    private void addKnightMoves(Coordinates coordinates) {
        Color color = getSquareFromCoordinates(coordinates).getSquarePiece().getPieceColor();
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
            if(possibleCoordinate.isInBounds() && !hasPiece(possibleCoordinate, color)) addMove(coordinates,possibleCoordinate);
        }
    }

    private void addKingMoves(Coordinates coordinates) {
        Color color = getSquareFromCoordinates(coordinates).getSquarePiece().getPieceColor();
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
            if(possibleCoordinate.isInBounds() && !hasPiece(possibleCoordinate, color)) addMove(coordinates,possibleCoordinate);
        }
        checkForCastling(coordinates);
    }

    private void checkForCastling(Coordinates coordinates) {
        ArrayList<Coordinates[]> savePossibleMoves = new ArrayList<>();
        savePossibleMoves.addAll(possibleMoves);
        ArrayList<Coordinates> possibleCoordinatesArrayList = new ArrayList<>();
        Color color = getSquareFromCoordinates(coordinates).getSquarePiece().getPieceColor();
        if(color == Color.WHITE){
            if(!whiteKingHasMoved && !whiteKingChecked){
                if(!white07RookHasMoved && noPiecesInBetween(coordinates, new Coordinates(0,7))){
                    Coordinates[] move = new Coordinates[]{coordinates, new Coordinates(3,7)};
                    if(doesntPutKingInCheck(move, Color.WHITE)) possibleCoordinatesArrayList.add(new Coordinates(2,7));
                }
                if(!white77RookHasMoved && noPiecesInBetween(coordinates, new Coordinates(7,7))){
                    Coordinates[] move = new Coordinates[]{coordinates, new Coordinates(5,7)};
                    if(doesntPutKingInCheck(move, Color.WHITE)) possibleCoordinatesArrayList.add(new Coordinates(6,7));
                }
            }
        }
        if(color == Color.BLACK){
            if(!blackKingHasMoved && !blackKingChecked){
                if(!black00RookHasMoved && noPiecesInBetween(coordinates, new Coordinates(0,0))){
                    Coordinates[] move = new Coordinates[]{coordinates, new Coordinates(3,0)};
                    if(doesntPutKingInCheck(move, Color.BLACK)) possibleCoordinatesArrayList.add(new Coordinates(2,0));
                }
                if(!black70RookHasMoved && noPiecesInBetween(coordinates, new Coordinates(7,0))){
                    Coordinates[] move = new Coordinates[]{coordinates, new Coordinates(5,0)};
                    if(doesntPutKingInCheck(move, Color.BLACK)) possibleCoordinatesArrayList.add(new Coordinates(6,0));
                }
            }
        }
        for(Coordinates c:possibleCoordinatesArrayList){
            addMove(coordinates,c);
        }
        possibleMoves.addAll(savePossibleMoves);
    }

    private void addRookMoves(Coordinates coordinates) {
        Color color = getSquareFromCoordinates(coordinates).getSquarePiece().getPieceColor();
        for(int i=0; i<8;i++){
            Coordinates possibleCoordinates = new Coordinates(coordinates.getX(),i);
            if(!possibleCoordinates.equals(coordinates) && !hasPiece(possibleCoordinates, color) && noPiecesInBetween(coordinates,possibleCoordinates)){
                addMove(coordinates,possibleCoordinates);
            }
            possibleCoordinates = new Coordinates(i,coordinates.getY());
            if(!possibleCoordinates.equals(coordinates) && !hasPiece(possibleCoordinates, color) && noPiecesInBetween(coordinates,possibleCoordinates)){
                addMove(coordinates,possibleCoordinates);
            }
        }
    }

    private void addPawnMoves(Coordinates coordinates) {
        ChessPiece chessPiece = boardMap.get(coordinates).getSquarePiece();
        int yDirection = 1;
        Color oppositeColor = Color.WHITE;
        if(chessPiece.isWhite()){
            yDirection = -1;
            oppositeColor = Color.BLACK;
        }
        Coordinates possibleCoordinates = new Coordinates(coordinates.getX(),coordinates.getY()+yDirection);
        if(possibleCoordinates.isInBounds()){
            if(!hasPiece(possibleCoordinates)){
                addMove(coordinates, possibleCoordinates);
            }
        }
        possibleCoordinates = new Coordinates(coordinates.getX(),coordinates.getY()+2*yDirection);
        if(possibleCoordinates.isInBounds()){
            if(!hasPiece(possibleCoordinates) && noPiecesInBetween(coordinates,possibleCoordinates)){
                addMove(coordinates, possibleCoordinates);
            }
        }
        possibleCoordinates= new Coordinates(coordinates.getX()+1,coordinates.getY()+yDirection);
        if(possibleCoordinates.isInBounds()){
            if(hasPiece(possibleCoordinates,oppositeColor)){
                addMove(coordinates, possibleCoordinates);
            }
            else if((new Coordinates(coordinates.getX()+1,coordinates.getY())).equals(twoSpacePawn)){
                if(hasPiece(twoSpacePawn,oppositeColor)){
                    addMove(coordinates,possibleCoordinates);
                }
            }
        }
        possibleCoordinates = new Coordinates(coordinates.getX()-1,coordinates.getY()+yDirection);
        if(possibleCoordinates.isInBounds()){
            if(hasPiece(possibleCoordinates,oppositeColor)){
                addMove(coordinates, possibleCoordinates);
            }
            else if((new Coordinates(coordinates.getX()-1,coordinates.getY())).equals(twoSpacePawn)){
                if(hasPiece(twoSpacePawn,oppositeColor)){
                    addMove(coordinates,possibleCoordinates);
                }
            }
        }
    }

    private void addMove(Coordinates coordinates, Coordinates possibleCoordinates) {
        possibleMoves.add(new Coordinates[]{coordinates, possibleCoordinates});
    }

    private boolean hasPiece(Coordinates coordinates){
        if(!coordinates.isInBounds()) return false;
        return boardMap.get(coordinates).getSquarePiece()!=null;
    }
    private boolean hasPiece(Coordinates coordinates, Color color){
        ChessPiece piece = boardMap.get(coordinates).getSquarePiece();
        if(piece!=null){
            return piece.getPieceColor()== color;
        }
        return false;
    }

    public void setKingChecked(Color color, Boolean boo){
        if (color == Color.WHITE){
            whiteKingChecked=boo;
        }
        else if(color == Color.BLACK){
            blackKingChecked=boo;
        }
    }
    private Coordinates getKingCoordinates(Color pc){
        if(pc== Color.WHITE) return whiteKingCoordinates;
        else if (pc== Color.BLACK) return blackKingCoordinates;
        else return null;
    }

    public void setCheckMated(Color color){
        if(color == Color.WHITE){
            gameStatus=GameStatus.WHITE_CHECKMATED;
        }
        else if(color == Color.BLACK){
            gameStatus=GameStatus.BLACK_CHECKMATED;
        }
        else{
            gameStatus=GameStatus.GAME_ON;
        }
    }
    private boolean noPiecesInBetween(Coordinates c1, Coordinates c2){
        ArrayList<Coordinates> coordinatesArrayList = getCoordinatesInBetween(c1,c2);
        for(Coordinates coordinates:coordinatesArrayList){
            if(hasPiece(coordinates)) return false;
        }
        return true;
    }

    private ArrayList<Coordinates> getCoordinatesInBetween(Coordinates c1, Coordinates c2){
        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        int i=0;
        int j=0;
        int dx = c2.getX()-c1.getX();
        int dy = c2.getY()-c1.getY();
        int max = Math.max(Math.abs(dx),Math.abs(dy));
        if(dx>0){
            i=1;
        }
        else if(dx<0){
            i=-1;
        }
        if(dy>0){
            j=1;
        }
        else if(dy<0){
            j=-1;
        }
        for(int n=1;n<max;n++){
            coordinatesArrayList.add(new Coordinates((c1.getX()+(n*i)),(c1.getY()+(n*j))));
        }
        return coordinatesArrayList;
    }
    public void printMoves(ArrayList<Coordinates[]> moves){
        String toPrint = "";
        for(Coordinates[] move: moves){
            toPrint+= ""+move[0]+", " +move[1] +"\n";
        }
        System.out.println(toPrint);
    }
    public void printCoordinatesList(ArrayList<Coordinates> coordinates){
        String toPrint = "";
        for(Coordinates coordinates1: coordinates){
            toPrint+= coordinates1+ "\n";
        }
        System.out.println(toPrint);
    }
    public ArrayList<Coordinates[]> getLegalMoves(){
        setLegalMoves();
        return legalMoves;
    }
    public void setKingCoordinates(Color color, Coordinates coordinates){
        if(color == Color.WHITE){
            whiteKingCoordinates.setCoordinates(coordinates);
        }
        else if(color == Color.BLACK){
            blackKingCoordinates.setCoordinates(coordinates);
        }
    }

    public GameStatus getGameStatus(){
        return this.gameStatus;
    }


    public void setTwoSpacePawn(Coordinates coordinates){
        twoSpacePawn=coordinates;
    }
    public Coordinates getTwoSpacePawn (){
        return twoSpacePawn;
    }
    public Coordinates getPassedPawn(){
        return passedPawn;
    }
    public void setPassedPawn(Coordinates cor){
        this.passedPawn=cor;
    }

    public boolean getKingChecked(Color color){
        if(color == Color.WHITE){
            return whiteKingChecked;
        }
        else return blackKingChecked;
    }
    public void setKingHasMoved(Color color){
        if(color == Color.WHITE){
            whiteKingHasMoved=true;
        }
        if(color == Color.BLACK){
            blackKingHasMoved=true;
        }
    }
    public void setRookHasMoved(Coordinates c){
        int x =c.getX();
        int y =c.getY();
        if(x==0){
            if(y==0) black00RookHasMoved=true;
            if(y==7) white07RookHasMoved=true;
        }
        else if(x==7){
            if(y==0) black70RookHasMoved=true;
            if(y==7) white77RookHasMoved=true;
        }
    }
    public Coordinates getRookCastledWithOldCoor(Coordinates finalCoordinates){
        int x = finalCoordinates.getX();
        int y = finalCoordinates.getY();
        if(x==2){
            if(y==0) return new Coordinates(0,0);
            if(y==7) return new Coordinates(0,7);
        }
        else if(x==6){
            if(y==0) return new Coordinates(7,0);
            if(y==7) return new Coordinates(7,7);
        }
        return null;
    }

    public Coordinates getRookCastledWithNewCoor(Coordinates finalCoordinates){
        int x = finalCoordinates.getX();
        int y = finalCoordinates.getY();
        if(x==2){
            if(y==0) return new Coordinates(3,0);
            if(y==7) return new Coordinates(3,7);
        }
        else if(x==6){
            if(y==0) return new Coordinates(5,0);
            if(y==7) return new Coordinates(5,7);
        }
        return null;
    }

    public String whyNotLegal(Coordinates c1, Coordinates c2){
        Coordinates[] coordinates = {c1,c2};
        if(legalMoves.contains(coordinates)) return "Move is valid";
        else if(!c2.isInBounds()) return "Final coor is out of bounds";
        else if(!doesntPutKingInCheck(coordinates,getSquareFromCoordinates(c1).getSquarePiece().getPieceColor())) return "puts your king in check";
        else if(hasPiece(c2,getSquareFromCoordinates(c1).getSquarePiece().getPieceColor())) return "other piece is same color";
        else if(wrongTurn(c1)) return "wrong turn";
        return "move does not correspond to piece";
    }
    private boolean wrongTurn(Coordinates initial){
        if((whitesTurn && !hasPiece(initial, Color.WHITE)) || (!whitesTurn && hasPiece(initial, Color.WHITE))) return true;
        return false;
    }

    public void printLegalMoves(){
        String returnString ="";
        for(Coordinates[] move:legalMoves){
            returnString+= move[0]+", "+move[1]+"\n";
        }
        System.out.println(returnString);
    }
}
