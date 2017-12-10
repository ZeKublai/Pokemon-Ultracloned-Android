package edu.ateneo.cie199.finalproject;

import java.util.Random;

/**
 * Created by John on 11/5/2017.
 * This class stores the values of the many different attributes of the Pokémon.
 */

public class StatSet {
    private int mHP = 0;
    private int mAttack = 0;
    private int mDefense = 0;
    private int mSpAttack = 0;
    private int mSpDefense = 0;
    private int mSpeed = 0;

    /**
     * Creates a StatSet with 0 values.
     */
    public StatSet() {
        this.mHP = 0;
        this.mAttack = 0;
        this.mDefense = 0;
        this.mSpAttack = 0;
        this.mSpDefense = 0;
        this.mSpeed = 0;
    }

    /**
     * Creates a StatSet with random values from 0 to a maximum value.
     * @param maxStat   The maximum value for the randomly generated stat.
     */
    public StatSet(int maxStat) {
        Random s = new Random();
        this.mHP = s.nextInt(maxStat);
        this.mAttack = s.nextInt(maxStat);
        this.mDefense = s.nextInt(maxStat);
        this.mSpAttack = s.nextInt(maxStat);
        this.mSpDefense = s.nextInt(maxStat);
        this.mSpeed = s.nextInt(maxStat);
    }

    /**
     * Creates a StatSet from the given values.
     * @param mHP           The HP stat that will be set.
     * @param mAttack       The attack stat that will be set.
     * @param mDefense      The defense stat that will be set.
     * @param mSpAttack     The special attack stat that will be set.
     * @param mSpDefense    The special defense stat that will be set.
     * @param mSpeed        The speed stat that will be set.
     */
    public StatSet(int mHP, int mAttack, int mDefense, int mSpAttack, int mSpDefense, int mSpeed) {
        this.mHP = mHP;
        this.mAttack = mAttack;
        this.mDefense = mDefense;
        this.mSpAttack = mSpAttack;
        this.mSpDefense = mSpDefense;
        this.mSpeed = mSpeed;
    }

    /**
     * Returns the HP stat from the StatSet.
     * @return  The HP stat from the StatSet.
     */
    public int getHP() {
        return mHP;
    }

    /**
     * Returns the attack stat from the StatSet.
     * @return  The attack stat from the StatSet.
     */
    public int getAttack() {
        return mAttack;
    }

    /**
     * Returns the defense stat from the StatSet.
     * @return  The defense stat from the StatSet.
     */
    public int getDefense() {
        return mDefense;
    }

    /**
     * Returns the special attack stat from the StatSet.
     * @return  The special attack stat from the StatSet.
     */
    public int getSpAttack() {
        return mSpAttack;
    }

    /**
     * Returns the special defense stat from the StatSet.
     * @return  The special defense stat from the StatSet.
     */
    public int getSpDefense() {
        return mSpDefense;
    }

    /**
     * Returns the speed stat from the StatSet.
     * @return  The speed stat from the StatSet.
     */
    public int getSpeed() {
        return mSpeed;
    }

    /**
     * Returns all of the StatSet values in a String object.
     * @return  All of the StatSet values in a String object.
     */
    @Override
    public String toString() {
        return this.mHP + "," + this.mAttack + "," + this.mDefense + "," +
                this.mSpAttack + "," + this.mSpDefense + "," + this.mSpeed;
    }
}
