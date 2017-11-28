package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/26/2017.
 */

public class Gender {
    public Gender(boolean mGender) {
        setGender(mGender);
    }

    public Gender() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    private String mName = "";
    private int mMainImage = 0;
    private int mBackImage = 0;
    private int mStandImage = 0;
    private int mWalkImage1 = 0;
    private int mWalkImage2 = 0;

    public int getMainImage() {
        return mMainImage;
    }
    public void setMainImage(int mMainImage) {
        this.mMainImage = mMainImage;
    }

    public int getBackImage() {
        return mBackImage;
    }
    public void setBackImage(int mBackImage) {
        this.mBackImage = mBackImage;
    }

    public int getStandImage() {
        return mStandImage;
    }
    public void setStandImage(int mStandImage) {
        this.mStandImage = mStandImage;
    }

    public int getWalkImage1() {
        return mWalkImage1;
    }
    public void setWalkImage1(int mWalkImage1) {
        this.mWalkImage1 = mWalkImage1;
    }

    public int getWalkImage2() {
        return mWalkImage2;
    }
    public void setWalkImage2(int mWalkImage2) {
        this.mWalkImage2 = mWalkImage2;
    }

    public void setGender(boolean gender){
        if(gender){
            this.mName = "boy";
            this.mMainImage = R.drawable.boy_main;
            this.mBackImage = R.drawable.boy_back;
            this.mStandImage = R.drawable.boy_stand;
            this.mWalkImage1 = R.drawable.boy_walk1;
            this.mWalkImage2 = R.drawable.boy_walk2;
        }
        else{
            this.mName = "girl";
            this.mMainImage = R.drawable.girl_main;
            this.mBackImage = R.drawable.girl_back;
            this.mStandImage = R.drawable.girl_stand;
            this.mWalkImage1 = R.drawable.girl_walk1;
            this.mWalkImage2 = R.drawable.girl_walk2;
        }

    }
}
