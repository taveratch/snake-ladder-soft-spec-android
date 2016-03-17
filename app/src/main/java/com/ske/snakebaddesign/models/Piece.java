package com.ske.snakebaddesign.models;

/**
 * Created by TAWEESOFT on 3/16/16 AD.
 */
public class Piece {
    private int color;
    private int position;
    private Square currentSquare;
    public Piece(int color){
        this.color = color;
        this.position = 0;
    }



    public int getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void reset(){
        this.position = 0;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public void setCurrentSquare(Square currentSquare) {
        this.currentSquare = currentSquare;
    }
}
