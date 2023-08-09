package com.example.chess;

public class Move {
    private Coordinates initialCoordinates;
    private Coordinates finalCoordinates;

    public Move(Coordinates initialCoordinates,Coordinates finalCoordinates){
        this.initialCoordinates = initialCoordinates;
        this.finalCoordinates = finalCoordinates;
    }

    public void movePiece(Board board){
        ChessPiece piece = board.getSquareFromCoordinates(initialCoordinates).getSquarePiece();
        if (piece==null) throw new IllegalArgumentException();
        if (!isValidMove(board)) throw new IllegalArgumentException();
        if(board.isWhitesTurn() && piece.getPieceColor()==PieceColor.WHITE){
            board.putPiece(initialCoordinates,null);
            board.putPiece(finalCoordinates,piece);
            board.setWhitesTurn(false);
        }
        else if (!board.isWhitesTurn() && piece.getPieceColor()==PieceColor.BLACK) {
            board.putPiece(initialCoordinates,null);
            board.putPiece(finalCoordinates,piece);
            board.setWhitesTurn(true);
        }
        else throw new IllegalArgumentException();
    }

    private boolean isValidMove(Board board) {
        ChessPiece piece = null;
        return true;
    }
}
