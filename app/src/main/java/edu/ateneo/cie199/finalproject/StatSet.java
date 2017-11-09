package edu.ateneo.cie199.finalproject;

import java.util.Random;

/**
 * Created by John on 11/5/2017.
 */

public class StatSet {
    private int mHP = 0;
    private int mAttack = 0;
    private int mDefense = 0;
    private int mSpAttack = 0;
    private int mSpDefense = 0;
    private int mSpeed = 0;

    public StatSet() {
        this.mHP = 0;
        this.mAttack = 0;
        this.mDefense = 0;
        this.mSpAttack = 0;
        this.mSpDefense = 0;
        this.mSpeed = 0;
    }

    public StatSet(int maxStat) {
        Random s = new Random();
        this.mHP = s.nextInt(maxStat);
        this.mAttack = s.nextInt(maxStat);
        this.mDefense = s.nextInt(maxStat);
        this.mSpAttack = s.nextInt(maxStat);
        this.mSpDefense = s.nextInt(maxStat);
        this.mSpeed = s.nextInt(maxStat);
    }

    public StatSet(int mHP, int mAttack, int mDefense, int mSpAttack, int mSpDefense, int mSpeed) {
        this.mHP = mHP;
        this.mAttack = mAttack;
        this.mDefense = mDefense;
        this.mSpAttack = mSpAttack;
        this.mSpDefense = mSpDefense;
        this.mSpeed = mSpeed;
    }


    public int getHP() {
        return mHP;
    }
    public void setHP(int mEVHP) {
        this.mHP = mEVHP;
    }

    public int getAttack() {
        return mAttack;
    }
    public void setAttack(int mEVAttack) {
        this.mAttack = mEVAttack;
    }

    public int getDefense() {
        return mDefense;
    }
    public void setDefense(int mEVDefense) {
        this.mDefense = mEVDefense;
    }

    public int getSpAttack() {
        return mSpAttack;
    }
    public void setSpAttack(int mEVSpAttack) {
        this.mSpAttack = mEVSpAttack;
    }

    public int getSpDefense() {
        return mSpDefense;
    }
    public void setSpDefense(int mEVSpDefense) {
        this.mSpDefense = mEVSpDefense;
    }

    public int getSpeed() {
        return mSpeed;
    }
    public void setSpeed(int mEVSpeed) {
        this.mSpeed = mEVSpeed;
    }
}
