package edu.ateneo.cie199.finalproject;

import android.graphics.Color;

/**
 * Created by John on 11/10/2017.
 * This class contains all the functions and data members needed
 * for the class Type which can be found in Moves and Pokémons.
 */

public class Type {
    public static int NONE = 0;
    public static int NORMAL = 1;
    public static int FIRE = 2;
    public static int WATER = 3;
    public static int ELECTRIC = 4;
    public static int GRASS = 5;
    public static int ICE = 6;
    public static int FIGHTING = 7;
    public static int POISON = 8;
    public static int GROUND = 9;
    public static int FLYING = 10;
    public static int PSYCHIC = 11;
    public static int BUG = 12;
    public static int ROCK = 13;
    public static int GHOST = 14;
    public static int DRAGON = 15;
    public static int DARK = 16;
    public static int STEEL = 17;
    public static int FAIRY = 18;

    public static int NONE_COLOR = Color.argb(0, 0, 0, 0);
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
    private int mColor = NONE_COLOR;

    private int mIcon = 0;
    private double[] mMultiplier = {1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1};

    /**
     * "The function is empty..."
     */
    public Type() {
    }

    /**
     * Creates a Type with the given parameters.
     * @param mName         The name of the created Type.
     * @param mId           The identification code of the created Type.
     * @param mColor        The color value of the created Type.
     * @param mMultiplier   The created Type's multiplier array.
     * @param mIcon         The image resource ID of the Type's icon.
     */
    public Type(String mName, int mId, int mColor, double[] mMultiplier, int mIcon) {
        this.mName = mName;
        this.mId = mId;
        this.mColor = mColor;
        this.mMultiplier = mMultiplier;
        this.mIcon = mIcon;
    }

    /**
     * Returns the identification code of the Type object.
     * @return  The identification code of the Type object.
     */
    public int getId() {
        return mId;
    }

    /**
     * Returns the color value of the Type object.
     * @return  The color value of the Type object.
     */
    public int getColor() {
        return mColor;
    }

    /**
     * Returns the name of the Type object.
     * @return  The name of the Type object.
     */
    public String getName() {
        return mName;
    }

    /**
     * Returns the multiplier array of the Type object.
     * @return  The multiplier array of the Type object.
     */
    public double[] getMultiplier(){
        return mMultiplier;
    }

    /**
     * Returns the image resource ID of the Type object's icon.
     * @return  The image resource ID of the Type object's icon.
     */
    public int getIcon() {
        return mIcon;
    }

}
