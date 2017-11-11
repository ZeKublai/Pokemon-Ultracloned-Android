package edu.ateneo.cie199.finalproject;

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

    private double[] mMultiplier = {0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0};

    public Type(double[] mMultiplier) {
        this.mMultiplier = mMultiplier;
    }

    public double[] getMultiplier(){
        return mMultiplier;
    }



}
