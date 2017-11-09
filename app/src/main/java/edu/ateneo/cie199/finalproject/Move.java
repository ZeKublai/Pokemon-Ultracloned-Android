package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/5/2017.
 */

public class Move {
    private String mName = "";
    private int mType = 0;
    private boolean mCategory = true;
    private int mMaxPP = 0;
    private int mCurrentPP = 0;
    private int mPower = 0;
    private int mAccuracy = 0;

    public Move(String mName, int mType, boolean mCategory, int mMaxPP, int mCurrentPP, int mPower,
                int mAccuracy) {
        this.mName = mName;
        this.mType = mType;
        this.mCategory = mCategory;
        this.mMaxPP = mMaxPP;
        this.mCurrentPP = mCurrentPP;
        this.mPower = mPower;
        this.mAccuracy = mAccuracy;
    }

    public Move() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public boolean isCategory() {
        return mCategory;
    }

    public void setCategory(boolean mCategory) {
        this.mCategory = mCategory;
    }

    public int getMaxPP() {
        return mMaxPP;
    }

    public void setMaxPP(int mMaxPP) {
        this.mMaxPP = mMaxPP;
    }

    public int getCurrentPP() {
        return mCurrentPP;
    }

    public void setCurrentPP(int mCurrentPP) {
        this.mCurrentPP = mCurrentPP;
    }

    public int getPower() {
        return mPower;
    }

    public void setPower(int mPower) {
        this.mPower = mPower;
    }

    public int getAccuracy() {
        return mAccuracy;
    }

    public void setAccuracy(int mAccuracy) {
        this.mAccuracy = mAccuracy;
    }
}
