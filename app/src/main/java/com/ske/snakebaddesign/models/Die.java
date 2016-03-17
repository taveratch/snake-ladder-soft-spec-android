package com.ske.snakebaddesign.models;

import java.util.Random;

/**
 * Created by TAWEESOFT on 3/15/16 AD.
 */
public class Die extends AbstractDie {

    public int roll(){
        return 1 + new Random().nextInt(6);
    }

}
