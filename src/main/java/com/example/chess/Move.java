package com.example.chess;

public class Move {
    private Coordinates initialCoordinates;
    private int xi;
    private int yi;
    private Coordinates finalCoordinates;
    private int xf;
    private int yf;
    private Board board;
    private ChessPiece piece1;
    private ChessPiece piece2;

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
        return false;
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
        if(isDiagonal()) return true;
        return false;
    }

    private boolean isValidKingMove() {
        if(xIsSame() && ((initialCoordinates.getY()== finalCoordinates.getY()+1)||(initialCoordinates.getY()== finalCoordinates.getY()-1))) return true;
        else if((initialCoordinates.getX()== finalCoordinates.getX()+1) && (yIsSame() || (initialCoordinates.getY()== finalCoordinates.getY()+1)||(initialCoordinates.getY()== finalCoordinates.getY()-1))) return true;
        else if((initialCoordinates.getX()== finalCoordinates.getX()-1) && (yIsSame() || (initialCoordinates.getY()== finalCoordinates.getY()+1)||(initialCoordinates.getY()== finalCoordinates.getY()-1))) return true;

        return false;
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
                else if(yi==6 && xIsSame() && (initialCoordinates.getY()== finalCoordinates.getY()+2)) return true;
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
                else if(yi==1 && xIsSame() && (initialCoordinates.getY()== finalCoordinates.getY()-2)) return true;
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
        int initialX = initialCoordinates.getX();
        int initialY = initialCoordinates.getY();
        int finalX = finalCoordinates.getX();
        int finalY = finalCoordinates.getY();
        for(int i=1;i<=7;i++){
            if(initialY==finalY+i && initialX==finalX+i) return true;
            else if(initialY==finalY+i && initialX==finalX-i) return true;
            else if(initialY==finalY-i && initialX==finalX-i) return true;
            else if(initialY==finalY-i && initialX==finalX+i) return true;
        }
        return false;
    }
}
