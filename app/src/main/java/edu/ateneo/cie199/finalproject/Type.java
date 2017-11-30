package edu.ateneo.cie199.finalproject;

import android.graphics.Color;

/**
 * Created by John on 11/10/2017.
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

    public int getIcon() {
        return mIcon;
    }
    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }
    public static int getIdfromString(String type){
        if(type.equals("None")){
            return NONE;
        }
        else if(type.equals("Normal")){
            return NORMAL;
        }
        else if(type.equals("Fire")){
            return FIRE;
        }
        else if(type.equals("Water")){
            return WATER;
        }
        else if(type.equals("Electric")){
            return ELECTRIC;
        }
        else if(type.equals("Grass")){
            return GRASS;
        }
        else if(type.equals("Ice")){
            return ICE;
        }
        else if (type.equals("Fighting")){
            return FIGHTING;
        }
        else if(type.equals("Poison")){
            return POISON;
        }
        else if(type.equals("Ground")){
            return GROUND;
        }
        else if(type.equals("Flying")){
            return FLYING;
        }
        else if(type.equals("Psychic")){
            return PSYCHIC;
        }
        else if(type.equals("Bug")){
            return BUG;
        }
        else if(type.equals("Rock")){
            return ROCK;
        }
        else if(type.equals("Ghost")){
            return GHOST;
        }
        else if(type.equals("Dragon")){
            return DRAGON;
        }
        else if(type.equals("Dark")){
            return DARK;
        }
        else if(type.equals("Steel")){
            return STEEL;
        }
        else if(type.equals("Fairy")){
            return FAIRY;
        }
        return 0;
    }
    public int getIcon() {
        return mIcon;
    }
    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }

}
