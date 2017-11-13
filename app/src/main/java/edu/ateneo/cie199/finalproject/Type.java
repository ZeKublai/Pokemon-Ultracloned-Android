package edu.ateneo.cie199.finalproject;

import android.graphics.Color;

/**
 * Created by John on 11/10/2017.
 */

public class Type {
    public static int NORMAL = 0;
    public static int FIRE = 1;
    public static int WATER = 2;
    public static int ELECTRIC = 3;
    public static int GRASS = 4;
    public static int ICE = 5;
    public static int FIGHTING = 6;
    public static int POISON = 7;
    public static int GROUND = 8;
    public static int FLYING = 9;
    public static int PSYCHIC = 10;
    public static int BUG = 11;
    public static int ROCK = 12;
    public static int GHOST = 13;
    public static int DRAGON = 14;
    public static int DARK = 15;
    public static int STEEL = 16;
    public static int FAIRY = 17;

    public static int NORMAL_COLOR = Color.argb(255, 151, 158, 123);
    public static int FIRE_COLOR = Color.argb(255, 244, 146, 0);
    public static int WATER_COLOR = Color.argb(255, 0, 116, 232);
    public static int ELECTRIC_COLOR = Color.argb(255, 219, 208, 0);
    public static int GRASS_COLOR = Color.argb(255, 0, 198, 6);
    public static int ICE_COLOR = Color.argb(255, 0, 221, 218);
    public static int FIGHTING_COLOR = Color.argb(255, 186, 0, 0);
    public static int POISON_COLOR = Color.argb(255, 148, 0, 186);
    public static int GROUND_COLOR = Color.argb(255, 214, 185, 0);
    public static int FLYING_COLOR = Color.argb(255, 168, 140, 255);
    public static int PSYCHIC_COLOR = Color.argb(255, 216, 30, 148);
    public static int BUG_COLOR = Color.argb(255, 156, 209, 0);
    public static int ROCK_COLOR = Color.argb(255, 137, 119, 0);
    public static int GHOST_COLOR = Color.argb(255, 80, 9, 142);
    public static int DRAGON_COLOR = Color.argb(255, 39, 10, 145);
    public static int DARK_COLOR = Color.argb(255, 61, 49, 41);
    public static int STEEL_COLOR = Color.argb(255, 102, 102, 102);
    public static int FAIRY_COLOR = Color.argb(255, 249, 149, 217);

    private String mName = "";

    private int mId = 0;

    private int mColor = BattleActivity.FIGHT_COLOR;
    private double[] mMultiplier = {1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1};

    public Type() {
    }

    public Type(String mName, int mId, int mColor, double[] mMultiplier) {
        this.mName = mName;
        this.mId = mId;
        this.mColor = mColor;
        this.mMultiplier = mMultiplier;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Type(double[] mMultiplier) {
        this.mMultiplier = mMultiplier;
    }

    public double[] getMultiplier(){
        return mMultiplier;
    }



}
