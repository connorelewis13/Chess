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
        if(xIsSame() || yIsSame()) return true;
        return false;
    }

    private boolean isValidPawnMove() {
        if(piece1.isWhite()){
            if(piece2==null){
                if(xIsSame() && (initialCoordinates.getY()== finalCoordinates.getY()+1)){
                    return true;
                }
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
}
