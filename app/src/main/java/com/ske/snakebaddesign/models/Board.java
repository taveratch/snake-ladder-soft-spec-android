package com.ske.snakebaddesign.models;

import java.util.Random;

/**
 * Created by TAWEESOFT on 3/16/16 AD.
 */
public class Board {

    private Square[] squares;
    private int boardSize;
    private int side;
    public Board(int boardSize) {
        this.boardSize = boardSize;
        squares = new Square[boardSize*boardSize];
        initComponent();
    }

    public void initComponent(){
        for(int i=0;i<boardSize*boardSize;i++){
            squares[i] = new NormalSquare();
        }
    }

    public Square getSquare(int position){
        return squares[position];
    }

    public Square[] getAllSquare(){
        return squares;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void shuffleSquare(){
        int[] randoms = new int[boardSize];
        for(int i=0;i<boardSize;i++) {
            randoms[i] = 1 + new Random().nextInt(boardSize*boardSize-2);
        }
        for(int i=0;i<boardSize*boardSize;i++)
            squares[i] = new NormalSquare();
        for(int i=0 ; i<boardSize ;i++)
            squares[randoms[i]] = new RedSquare();
    }
}
