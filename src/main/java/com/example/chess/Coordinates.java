package com.example.chess;

public class Coordinates {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o){
        if(o==null || getClass()!=o.getClass()) return false;
        Coordinates coordinates2 = (Coordinates) o;
        return (this.x == coordinates2.getX()) && (this.y == coordinates2.getY());
    }
    @Override
    public int hashCode(){
        return this.x;
    }
    public boolean isInBounds(){
        return (this.x<8 && this.x>=0) && (this.y<8 && this.y>=0);
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
