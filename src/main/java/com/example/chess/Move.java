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
                    //System.out.println("passed pawn: " +board.getPassedPawn());
                }
            }
            else{
                board.setTwoSpacePawn(null);
                board.setPassedPawn(null);
            }
            board.setWhitesTurn(false);
            if(board.getLegalMoves().size()==0 && board.getKingChecked(PieceColor.BLACK)) board.setCheckMated(PieceColor.BLACK);
            else if(board.getLegalMoves().size()==0) board.setStaleMate();
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
                    //System.out.println("passed pawn: " +board.getPassedPawn());
                }
            }
            else{
                board.setTwoSpacePawn(null);
                board.setPassedPawn(null);
            }
            board.setWhitesTurn(true);
            if(board.getLegalMoves().size()==0 && board.getKingChecked(PieceColor.WHITE)) board.setCheckMated(PieceColor.WHITE);
            else if(board.getLegalMoves().size()==0) board.setStaleMate();
        }
        else throw new IllegalArgumentException();
    }


    private boolean isValidMove() {
        ArrayList<Coordinates[]> legalMoves = board.getLegalMoves();
        for(Coordinates[] move: legalMoves){
            if(initialCoordinates.equals(move[0]) && finalCoordinates.equals(move[1])) return true;
        }
        return false;
    }
}
