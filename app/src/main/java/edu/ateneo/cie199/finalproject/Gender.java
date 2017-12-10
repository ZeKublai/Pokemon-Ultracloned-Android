package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/26/2017.
 * This class handles the Gender of the player and
 * contains the sprites and names of the Gender defined.
 */

public class Gender {
    private String mName = "";
    private int mMainImage = 0;
    private int mBackImage = 0;
    private int mStandImage = 0;
    private int mWalkImage1 = 0;
    private int mWalkImage2 = 0;

    /**
     * Creates a Gender from a boolean where true sets the Gender as a boy and false sets as a girl.
     * @param mGender   The Gender to be set.
     */
    public Gender(boolean mGender) {
        setGender(mGender);
    }

    /**
     * "The function is empty..."
     */
    public Gender() {
    }

    /**
     * Returns the name of the Gender.
     * @return  The name to be returned.
     */
    public String getName() {
        return mName;
    }

    /**
     * Returns the main image of the Gender.
     * @return  The Gender's main image.
     */
    public int getMainImage() {
        return mMainImage;
    }

    /**
     * Returns the back image of the Gender.
     * @return  The Gender's back image.
     */
    public int getBackImage() {
        return mBackImage;
    }

    /**
     * Returns the standing image of the Gender.
     * @return  The Gender's standing image.
     */
    public int getStandImage() {
        return mStandImage;
    }

    /**
     * Returns the first walking image of the Gender.
     * @return  The Gender's first walking image.
     */
    public int getWalkImage1() {
        return mWalkImage1;
    }

    /**
     * Returns the second walking image of the Gender.
     * @return  The Gender's second walking image.
     */
    public int getWalkImage2() {
        return mWalkImage2;
    }

    /**
     * Sets Gender as either a boy or a girl given a boolean value.
     * @param gender    Boy if true else girl.
     */
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
