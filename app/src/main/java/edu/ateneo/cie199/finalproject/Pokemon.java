package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/5/2017.
 */

public class Pokemon {
    public static int MISSINGNO = 0;

    private int mDexNumber = MISSINGNO;
    private String mName = "MissingNo";
    private String mDescription = "??? Pokémon";
    private StatSet mBase = new StatSet();
    private Type mType1 = new Type();
    private Type mType2 = new Type();
    private int mFrontImage = 0;
    private int mIcon = 0;
    private int mBackImage = 0;
    private int mCatchRate = 0;
    private int mMaleRatio = 0;
    private int mFemaleRatio = 0;

    public Pokemon(int mDexNumber, String mName, String mDescription, StatSet mBase, Type mType1,
                   int mFrontImage, int mIcon, int mBackImage, int mCatchRate, int mMaleRatio,
                   int mFemaleRatio) {
        this.mDexNumber = mDexNumber;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mBase = mBase;
        this.mType1 = mType1;
        this.mFrontImage = mFrontImage;
        this.mIcon = mIcon;
        this.mBackImage = mBackImage;
        this.mCatchRate = mCatchRate;
        this.mMaleRatio = mMaleRatio;
        this.mFemaleRatio = mFemaleRatio;
    }

    public Pokemon() {
    }

    public int getMaleRatio() {
        return mMaleRatio;
    }
    public void setMaleRatio(int mMaleRatio) {
        this.mMaleRatio = mMaleRatio;
    }

    public int getFemaleRatio() {
        return mFemaleRatio;
    }
    public void setFemaleRatio(int mFemaleRatio) {
        this.mFemaleRatio = mFemaleRatio;
    }

    public int getDexNumber() {
        return mDexNumber;
    }
    public void setDexNumber(int mDexNumber) {
        this.mDexNumber = mDexNumber;
    }

    public String getName() {
        return mName;
    }
    public void setName(String mName) {
        this.mName = mName;
    }

    public StatSet getBase() {
        return mBase;
    }
    public void setBase(StatSet mBase) {
        this.mBase = mBase;
    }

    public Type getType1() {
        return mType1;
    }
    public void setType1(Type mType1) {
        this.mType1 = mType1;
    }

    public Type getType2() {
        return mType2;
    }
    public void setType2(Type mType2) {
        this.mType2 = mType2;
    }

    public int getFrontImage() {
        return mFrontImage;
    }
    public void setFrontImage(int mFrontImage) {
        this.mFrontImage = mFrontImage;
    }

    public int getIcon() {
        return mIcon;
    }
    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public int getBackImage() {
        return mBackImage;
    }
    public void setBackImage(int mBackImage) {
        this.mBackImage = mBackImage;
    }

    public int getCatchRate() {
        return mCatchRate;
    }
    public void setCatchRate(int mCatchRate) {
        this.mCatchRate = mCatchRate;
    }

    public String getDescription() {
        return mDescription;
    }
    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public boolean isEmpty(){
        if(this.mDexNumber == MISSINGNO){
            return true;
        }
        return false;
    }
}
