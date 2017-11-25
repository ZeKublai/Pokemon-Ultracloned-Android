package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/5/2017.
 */

public class Move {
    public static int PHYSICAL = 0;
    public static int SPECIAL = 1;
    public static int STATUS = 2;

    public static int MAX_ACCURACY = 100;

    private String mName = "";
    private Type mType = new Type();
    private int mCategory = 0;
    private int mMaxPP = 0;
    private int mCurrentPP = 0;
    private int mPower = 0;
    private int mAccuracy = 0;

    public Move(String mName, Type mType, int mCategory, int mMaxPP, int mCurrentPP, int mPower,
                int mAccuracy) {
        this.mName = mName;
        this.mType = mType;
        this.mCategory = mCategory;
        this.mMaxPP = mMaxPP;
        this.mCurrentPP = mCurrentPP;
        this.mPower = mPower;
        this.mAccuracy = mAccuracy;
    }

    public Move(Move move){
        this.mName = move.mName;
        this.mType = move.mType;
        this.mCategory = move.mCategory;
        this.mMaxPP = move.mMaxPP;
        this.mCurrentPP = move.mCurrentPP;
        this.mPower = move.mPower;
        this.mAccuracy = move.mAccuracy;
    }

    public Move() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type mType) {
        this.mType = mType;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int mCategory) {
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

    public boolean isEmpty(){
        if(mName.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    public String getButtonString(){
        if(mName.equals("")){
            return "\n";
        }
        else{
            return mName + "\n" + mType.getName() + "\t PP " + mCurrentPP + "/" + mMaxPP;
        }
    }

    public static int decodeCategory(String Category){
        if(Category.equals("Physical")){
            return 0;
        }
        else if (Category.equals("Special")){
            return 1;
        }
        else {
            return 0;
        }
    }
}
