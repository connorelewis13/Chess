package com.example.chess;

import java.util.ArrayList;

public class Move {
    private final Coordinates initialCoordinates;
    private final int xi;
    private final int yi;
    private final Coordinates finalCoordinates;
    private final int xf;
    private final int yf;
    private final Board board;
    private final ChessPiece piece1;
    private final ChessPiece piece2;

    public Move(Coordinates initialCoordinates,Coordinates finalCoordinates, Board board){
        this.initialCoordinates = initialCoordinates;
        this.finalCoordinates = finalCoordinates;
        this.board = board;
        this.piece1 = board.getSquareFromCoordinates(initialCoordinates).getSquarePiece();
        this.piece2 = board.getSquareFromCoordinates(finalCoordinates).getSquarePiece();
        xi = initialCoordinates.getX();
        yi = initialCoordinates.getY();
        xf = finalCoordinates.getX();
        yf = finalCoordinates.getY();
    }


    public void movePiece(){
        ChessPiece piece = board.getSquareFromCoordinates(initialCoordinates).getSquarePiece();
        ChessPiece otherPiece = board.getSquareFromCoordinates(finalCoordinates).getSquarePiece();
        if (piece==null) throw new IllegalArgumentException();
        if (!isValidMove()) throw new IllegalArgumentException();
        if(board.isWhitesTurn() && piece.getPieceColor()==PieceColor.WHITE){
            board.putPiece(initialCoordinates,null);
            board.putPiece(finalCoordinates,piece);
            if(piece.getPieceType()==PieceType.KING) board.setKingCoordinates(PieceColor.WHITE,finalCoordinates);
            else if(piece.getPieceType()==PieceType.PAWN){
                if(Math.abs(yi-yf)==2) board.setTwoSpacePawn(finalCoordinates);
                else if(Math.abs(yi-yf)==1 && Math.abs(xi-xf)==1 && otherPiece==null){
                    board.putPiece(board.getTwoSpacePawn(),null);
                }
                else if(yf==0){
                    board.setPassedPawn(finalCoordinates);
                    System.out.println("passed pawn: " +board.getPassedPawn());
                }
            }
            else{
                board.setTwoSpacePawn(null);
                board.setPassedPawn(null);
            }
            board.setWhitesTurn(false);
            //board.printMoves(board.getLegalMoves());
        }
        else if (!board.isWhitesTurn() && piece.getPieceColor()==PieceColor.BLACK) {
            board.putPiece(initialCoordinates,null);
            board.putPiece(finalCoordinates,piece);
            if(piece.getPieceType()==PieceType.KING) board.setKingCoordinates(PieceColor.BLACK,finalCoordinates);
            else if(piece.getPieceType()==PieceType.PAWN){
                if(Math.abs(yi-yf)==2) board.setTwoSpacePawn(finalCoordinates);
                else if(Math.abs(yi-yf)==1 && Math.abs(xi-xf)==1 && otherPiece==null){
                    board.putPiece(board.getTwoSpacePawn(),null);
                }
                else if(yf==7){
                    board.setPassedPawn(finalCoordinates);
                    System.out.println("passed pawn: " +board.getPassedPawn());
                }
            }
            else{
                board.setTwoSpacePawn(null);
                board.setPassedPawn(null);
            }
            board.setWhitesTurn(true);
            //board.printMoves(board.getLegalMoves());
        }
        else throw new IllegalArgumentException();
    }


    private boolean isValidMove() {
        ArrayList<Coordinates[]> legalMoves = board.getLegalMoves();
        for(Coordinates[] move: legalMoves){
            if(initialCoordinates.equals(move[0]) && finalCoordinates.equals(move[1])) return true;
        }
        return false;
//        if(piecesAreSameColor()) return false;
//        switch(piece1.getPieceType()){
//            case PAWN:
//                return isValidPawnMove();
//            case ROOK:
//                return isValidRookMove();
//            case QUEEN:
//                return isValidQueenMove();
//            case KNIGHT:
//                return isValidKnightMove();
//            case BISHOP:
//                return isValidBishopMove();
//            case KING:
//                return isValidKingMove();
//        }
//        return false;
    }

    private boolean isValidKnightMove() {
        if((yf==yi+2 && xf==xi+1)
        ||(yf==yi+1 && xf==xi+2)
        ||(yf==yi-1 && xf==xi+2)
        ||(yf==yi-2 && xf==xi+1)
        ||(yf==yi-2 && xf==xi-1)
        ||(yf==yi-1 && xf==xi-2)
        ||(yf==yi+1 && xf==xi-2)
        ||(yf==yi+2 && xf==xi-1)
        ) return true;
        return false;
    }

    private boolean isValidQueenMove() {
        if(isValidBishopMove() || isValidRookMove()) return true;
        return false;
    }

    private boolean isValidBishopMove() {
        if(isDiagonal()) {
            if(noPiecesInBetween()){
                return true;
            }
        }
        return false;
    }

    private boolean noPiecesInBetween() {
        int i=0;
        int j=0;
        int dx = xf-xi;
        int dy = yf-yi;
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
            Coordinates coordinates = new Coordinates((xi+(n*i)),(yi+(n*j)));
            Square square = board.getSquareFromCoordinates(coordinates);
            if(square.getSquarePiece()!=null){
                return false;
            }
        }
        return true;
    }

    private boolean isValidKingMove() {
        if(xIsSame() && ((initialCoordinates.getY()== finalCoordinates.getY()+1)||(initialCoordinates.getY()== finalCoordinates.getY()-1))) return true;
        else if((initialCoordinates.getX()== finalCoordinates.getX()+1) && (yIsSame() || (initialCoordinates.getY()== finalCoordinates.getY()+1)||(initialCoordinates.getY()== finalCoordinates.getY()-1))) return true;
        else if((initialCoordinates.getX()== finalCoordinates.getX()-1) && (yIsSame() || (initialCoordinates.getY()== finalCoordinates.getY()+1)||(initialCoordinates.getY()== finalCoordinates.getY()-1))) return true;

        return false;
    }

    private boolean isValidRookMove() {
        if((xIsSame() || yIsSame()) && noPiecesInBetween()) return true;
        return false;
    }

    private boolean isValidPawnMove() {
        if(piece1.isWhite()){
            if(piece2==null){
                if(xIsSame() && (initialCoordinates.getY()== finalCoordinates.getY()+1)){
                    return true;
                }
                else if(initialCoordinates.getY()==6 && xIsSame() && (initialCoordinates.getY()== finalCoordinates.getY()+2)) return true;
            }
            else{
                if((initialCoordinates.getY()== finalCoordinates.getY()+1) && ((initialCoordinates.getX() == finalCoordinates.getX()-1) ||(initialCoordinates.getX() == finalCoordinates.getX()+1) )){
                    return true;
                }
            }
        }
        else if (piece1.isBlack()){
            if(piece2==null){
                if(xIsSame() && (initialCoordinates.getY() == finalCoordinates.getY()-1)){
                    return true;
                }
                else if(initialCoordinates.getY()==1 && xIsSame() && (initialCoordinates.getY()== finalCoordinates.getY()-2)) return true;
            }
            else{
                if((initialCoordinates.getY()== finalCoordinates.getY()-1) && ((initialCoordinates.getX() == finalCoordinates.getX()-1) ||(initialCoordinates.getX() == finalCoordinates.getX()+1) )){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean piecesAreSameColor() {
        if(piece2==null) return false;
        else if(piece1.getPieceColor()==piece2.getPieceColor()) return true;
        else return false;
    }
    private boolean xIsSame(){
        if(initialCoordinates.getX()== finalCoordinates.getX()){
            return true;
        }
        return false;
    }
    private boolean yIsSame(){
        if(initialCoordinates.getY()== finalCoordinates.getY()){
            return true;
        }
        return false;
    }
    private boolean isDiagonal(){
        for(int i=1;i<=7;i++){
            if(yi==yf+i && xi==xf+i) return true;
            else if(yi==yf+i && xi==xf-i) return true;
            else if(yi==yf-i && xi==xf-i) return true;
            else if(yi==yf-i && xi==xf+i) return true;
        }
        return false;
    }
}
