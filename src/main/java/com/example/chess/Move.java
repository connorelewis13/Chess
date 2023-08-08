package com.example.chess;

public class Move {
    private Coordinates initialCoordinates;
    private Coordinates finalCoordinates;

    public Move(Coordinates initialCoordinates,Coordinates finalCoordinates){
        this.initialCoordinates = initialCoordinates;
        this.finalCoordinates = finalCoordinates;
    }

    public void movePiece(Move move, Board board){
        ChessPiece piece = board.getSquareFromCoordinates(initialCoordinates.getX(),initialCoordinates.getY()).getSquarePiece();
        //if (piece==null) throw new IllegalArgumentException();
        if(board.isWhitesTurn() && piece.getPieceColor()==PieceColor.WHITE){
            //board.putPiece();
        }
    }
}
