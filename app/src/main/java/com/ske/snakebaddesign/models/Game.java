package com.ske.snakebaddesign.models;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by TAWEESOFT on 3/15/16 AD.
 */
public class Game extends Observable{

    private List<Player> players;
    private int numberOfPlayers;
    private int boardSize;
    private int turn =0;
    private DieCup dieCup;
    private int wonPlayer = -1;
    public Game(int numberOfPlayers,int boardSize){
        this.numberOfPlayers = numberOfPlayers;
        this.boardSize = boardSize;
        dieCup = new DieCup();
        AbstractDie normalDie = new Die();
        dieCup.addDie(normalDie);
        players = new ArrayList<>();
        players.add(new Player(Color.BLACK));
        players.add(new Player(Color.RED));
//        players.add(new Player(Color.WHITE));
//        players.add(new Player(Color.YELLOW));
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void rollDice(){
        Player player = players.get(turn);
        turn++;
        turn %= numberOfPlayers;
        int oldPosition = player.getPosition();
        int face = dieCup.roll();
        player.setPosition(adjustPosition(oldPosition,face));
        setChanged();
        notifyObservers(player);
    }


    public void reset(){
        for(Player player : players)
            player.reset();
        turn = 0;
        dieCup.clear();
        wonPlayer = -1;
    }

    public List<Player> getPlayers() {
        return players;
    }

    private int adjustPosition(int current, int distance) {
        current = current + distance;
        int maxSquare = boardSize * boardSize - 1;
        if(current > maxSquare) {
            current = maxSquare - (current - maxSquare);
        }
        return current;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public boolean isEnd(){
        for(Player player : players)
            if(player.getPosition() == boardSize * boardSize - 1){
                wonPlayer = player.getNumber()-1;
                return true;
            }
        return false;
    }

    public Player getWinner(){
        return players.get(wonPlayer);
    }

    public int getDiceFace(){
        return dieCup.getFace();
    }

    public int[] getPlayerColors(){
        int[] colors = new int[numberOfPlayers];
        for(int i=0;i<numberOfPlayers;i++){
            colors[i] = players.get(i).getPiece().getColor();
        }
        return colors;
    }



}
