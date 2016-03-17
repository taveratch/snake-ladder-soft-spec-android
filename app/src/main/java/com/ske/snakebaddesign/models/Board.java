package com.ske.snakebaddesign.models;

/**
 * Created by TAWEESOFT on 3/16/16 AD.
 */
public class Board {

    private Square[][] squares;
    private int boardSize;
    private int side;
    public Board(int boardSize) {
        this.boardSize = boardSize;
        squares = new Square[boardSize][boardSize];
        initComponent();
    }

    public void initComponent(){
        for(int i=0;i<boardSize;i++){
            for(int k=0;k<boardSize;k++){
                    squares[i][k] = new NormalSquare();
            }
        }
    }

    public Square getSquare(int row, int column){
        return squares[row][column];
    }
}
