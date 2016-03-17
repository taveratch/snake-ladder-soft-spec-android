package com.ske.snakebaddesign.models;

import android.graphics.Color;

/**
 * Created by TAWEESOFT on 3/16/16 AD.
 */
public class RedSquare implements Square {

    private int color = Color.parseColor("#e67e22");

    @Override
    public int getColor() {
        return color;
    }
}
