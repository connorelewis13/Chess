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
            setBoardPieceConditions(0);
            board.setWhitesTurn(false);
            board.setKingChecked(PieceColor.WHITE,false);
            if(board.getLegalMoves().size()==0 && board.getKingChecked(PieceColor.BLACK)) board.setCheckMated(PieceColor.BLACK);
            else if(board.getLegalMoves().size()==0) board.setStaleMate();
        }
        else if (!board.isWhitesTurn() && piece.getPieceColor()==PieceColor.BLACK) {
            board.putPiece(initialCoordinates,null);
            board.putPiece(finalCoordinates,piece);
            setBoardPieceConditions(7);
            board.setWhitesTurn(true);
            board.setKingChecked(PieceColor.BLACK,false);
            if(board.getLegalMoves().size()==0 && board.getKingChecked(PieceColor.WHITE)) board.setCheckMated(PieceColor.WHITE);
            else if(board.getLegalMoves().size()==0) board.setStaleMate();
        }
        else throw new IllegalArgumentException();
    }

    private void setBoardPieceConditions(int endOfBoard) {
        Coordinates[] startingCoordinates = new Coordinates[]{
                new Coordinates(0,0),
                new Coordinates(0,7),
                new Coordinates(7,7),
                new Coordinates(7,0),
        };
        PieceColor pieceColor = piece1.getPieceColor();
        if(piece1.getPieceType()==PieceType.KING){
            board.setKingCoordinates(pieceColor,finalCoordinates);
            if(Math.abs(xf-xi)==2){
                Coordinates oldRookCoordinates = board.getRookCastledWithOldCoor(finalCoordinates);
                Coordinates newRookCoordinates = board.getRookCastledWithNewCoor(finalCoordinates);
                board.putPiece(oldRookCoordinates,null);
                board.putPiece(newRookCoordinates, new ChessPiece(pieceColor,PieceType.ROOK));
            }
            board.setKingHasMoved(pieceColor);
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
