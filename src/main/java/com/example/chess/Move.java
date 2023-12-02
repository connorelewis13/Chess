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
        ArrayList<Coordinates[]> legalMoves = board.getLegalMoves();
        ChessPiece piece = board.getSquareFromCoordinates(initialCoordinates).getSquarePiece();
        ChessPiece otherPiece = board.getSquareFromCoordinates(finalCoordinates).getSquarePiece();
        if (piece==null) throw new IllegalArgumentException("Did not select a piece");
        if (!isValidMove()) throw new IllegalArgumentException(board.whyNotLegal(initialCoordinates,finalCoordinates));
        if(board.isWhitesTurn() && piece.getPieceColor()== Color.WHITE){
            board.putPiece(initialCoordinates,null);
            board.putPiece(finalCoordinates,piece);
            setBoardPieceConditions(0);
            board.setWhitesTurn(false);
            board.setKingChecked(Color.WHITE,false);
            legalMoves = board.getLegalMoves();
            if(legalMoves.size()==0 && board.getKingChecked(Color.BLACK)) board.setCheckMated(Color.BLACK);
            else if(legalMoves.size()==0) board.setStaleMate();
        }
        else if (!board.isWhitesTurn() && piece.getPieceColor()== Color.BLACK) {
            board.putPiece(initialCoordinates,null);
            board.putPiece(finalCoordinates,piece);
            setBoardPieceConditions(7);
            board.setWhitesTurn(true);
            board.setKingChecked(Color.BLACK,false);
            legalMoves = board.getLegalMoves();
            if(legalMoves.size()==0 && board.getKingChecked(Color.WHITE)) board.setCheckMated(Color.WHITE);
            else if(legalMoves.size()==0) board.setStaleMate();
        }
        else throw new IllegalArgumentException("Wrong Turn");
    }

    private void setBoardPieceConditions(int endOfBoard) {
        Coordinates[] startingCoordinates = new Coordinates[]{
                new Coordinates(0,0),
                new Coordinates(0,7),
                new Coordinates(7,7),
                new Coordinates(7,0),
        };
        Color color = piece1.getPieceColor();
        if(piece1.getPieceType()==PieceType.KING){
            board.setKingCoordinates(color,finalCoordinates);
            if(Math.abs(xf-xi)==2){
                Coordinates oldRookCoordinates = board.getRookCastledWithOldCoor(finalCoordinates);
                Coordinates newRookCoordinates = board.getRookCastledWithNewCoor(finalCoordinates);
                board.putPiece(oldRookCoordinates,null);
                board.putPiece(newRookCoordinates, new ChessPiece(color,PieceType.ROOK));
            }
            board.setKingHasMoved(color);
        }
        else if(piece1.getPieceType()==PieceType.ROOK){
            for(Coordinates c:startingCoordinates){
                if(initialCoordinates.equals(startingCoordinates)){
                    board.setRookHasMoved(initialCoordinates);
                }
            }
        }
        else if(piece1.getPieceType()==PieceType.PAWN){
            if(Math.abs(yi-yf)==2) board.setTwoSpacePawn(finalCoordinates);
            else if(Math.abs(yi-yf)==1 && Math.abs(xi-xf)==1 && piece2 == null){
                board.putPiece(board.getTwoSpacePawn(),null);
            }
            else if(yf==endOfBoard){
                board.setPassedPawn(finalCoordinates);
            }
        }
        else{
            board.setTwoSpacePawn(null);
            board.setPassedPawn(null);
        }
        for(Coordinates c:startingCoordinates){
            if(finalCoordinates.equals(c)){
                board.setRookHasMoved(finalCoordinates);
            }
        }
    }


    private boolean isValidMove() {
        ArrayList<Coordinates[]> legalMoves = board.getLegalMoves();
        for(Coordinates[] move: legalMoves){
            if(initialCoordinates.equals(move[0]) && finalCoordinates.equals(move[1])) return true;
        }
        return false;
    }
}
