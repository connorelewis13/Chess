package com.example.chess;

public class Move {
    private Coordinates initialCoordinates;
    private Coordinates finalCoordinates;
    private Board board;
    private ChessPiece piece1;
    private ChessPiece piece2;

    public Move(Coordinates initialCoordinates,Coordinates finalCoordinates, Board board){
        this.initialCoordinates = initialCoordinates;
        this.finalCoordinates = finalCoordinates;
        this.board = board;
        this.piece1 = board.getSquareFromCoordinates(initialCoordinates).getSquarePiece();
        this.piece2 = board.getSquareFromCoordinates(finalCoordinates).getSquarePiece();
    }

    public void movePiece(){
        ChessPiece piece = board.getSquareFromCoordinates(initialCoordinates).getSquarePiece();
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
            board.setWhitesTurn(true);
        }
        else throw new IllegalArgumentException();
    }

    private boolean isValidMove() {
        if(piecesAreSameColor()) return false;
        switch(piece1.getPieceType()){
            case PAWN:
                return isValidPawnMove();
            case ROOK:
                return isValidRookMove();
            case QUEEN:
                return isValidQueenMove();
            case KNIGHT:
                return isValidKnightMove();
            case BISHOP:
                return isValidBishopMove();
            case KING:
                return isValidKingMove();
        }
        return true;
    }

    private boolean isValidKnightMove() {
        return true;
    }

    private boolean isValidQueenMove() {
        return true;
    }

    private boolean isValidBishopMove() {
        return true;
    }

    private boolean isValidKingMove() {
        return true;
    }

    private boolean isValidRookMove() {
        return true;
    }

    private boolean isValidPawnMove() {
        return true;
    }

    private boolean piecesAreSameColor() {
        if(piece2==null) return false;
        else if(piece1.getPieceColor()==piece2.getPieceColor()) return true;
        else return false;
    }
}
