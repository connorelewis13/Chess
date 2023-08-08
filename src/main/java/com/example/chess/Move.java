package com.example.chess;

public class Move {
    private Coordinates initialCoordinates;
    private Coordinates finalCoordinates;

    public Move(Coordinates initialCoordinates,Coordinates finalCoordinates){
        this.initialCoordinates = initialCoordinates;
        this.finalCoordinates = finalCoordinates;
    }

    public void movePiece(Board board){
        ChessPiece piece = board.getSquareFromCoordinates(initialCoordinates.getX(),initialCoordinates.getY()).getSquarePiece();
        if (piece==null) throw new IllegalArgumentException();
        if (!isValidMove()) throw new IllegalArgumentException();
        if(board.isWhitesTurn() && piece.getPieceColor()==PieceColor.WHITE){
            board.putPiece(initialCoordinates,null);
            board.putPiece(finalCoordinates,piece);
            board.setWhitesTurn(false);
        }
        else if (!board.isWhitesTurn() && piece.getPieceColor()==PieceColor.BLACK) {
            board.putPiece(initialCoordinates,null);
            board.putPiece(finalCoordinates,piece);
            board.setWhitesTurn(false);
        }
    }

    private boolean isValidMove() {
        return true;
    }
}
