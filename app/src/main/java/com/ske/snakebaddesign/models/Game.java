package com.ske.snakebaddesign.models;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

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
    private int[] colors = {Color.RED , Color.BLACK , Color.WHITE , Color.YELLOW};
    private Board board;
    private QuestionGenerator questionGenerator;
    public Game(int numberOfPlayers,int boardSize){
        this.numberOfPlayers = numberOfPlayers;
        this.boardSize = boardSize;
        questionGenerator = new QuestionGenerator(100);
        questionGenerator.generateQuestion();
        board = new Board(boardSize);
        shuffleSquare();
        dieCup = new DieCup();
        AbstractDie normalDie = new Die();
        dieCup.addDie(normalDie);
        players = new ArrayList<>();
        Player.clearRunner();
        for(int i =0 ;i<numberOfPlayers ; i++) {
            players.add(new Player(colors[i]));
        }
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
        movePlayer(player, face);
        setChanged();
        notifyObservers(player);
        if(player.getCurrentSquare().getClass().equals(RedSquare.class)) {
            setChanged();
            notifyObservers(questionGenerator.getQuestions().get(new Random().nextInt(questionGenerator.getQuestions().size())));
        }
    }

    public void movePlayer(Player player , int value){
        player.setPosition(adjustPosition(player.getPosition(),value));
        player.setSquare(board.getSquare(player.getPosition()));
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

    public Square[] getAllSquare(){
        return board.getAllSquare();
    }

    public Board getBoard() {
        return board;
    }



    public void penalty(){
        Player player = players.get(turn == 0 ? numberOfPlayers-1 : turn-1 );
        int value = dieCup.roll() * -1;
        movePlayer(player,value);
    }


}
