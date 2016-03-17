package com.ske.snakebaddesign.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAWEESOFT on 3/15/16 AD.
 */
public class DieCup {
    private List<AbstractDie> dice;
    private int score;
    public DieCup(){
        dice = new ArrayList<>();
    }

    public int roll(){
        score = 0;
        for(AbstractDie die : dice)
            score += die.roll();
        return score;
    }

    public void clear(){
        score = 0;
    }

    public void addDie(AbstractDie die){
        dice.add(die);
    }

    public int getFace(){
        return score;
    }

}
