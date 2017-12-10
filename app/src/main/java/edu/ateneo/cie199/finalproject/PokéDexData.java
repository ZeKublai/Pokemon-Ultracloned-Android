package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/5/2017.
 * This class contains the functions and data members for the base data of Pokémons.
 */

public class PokéDexData {
    public static int MISSINGNO = 0;
    private int mDexNumber = MISSINGNO;
    private String mName = "MissingNo";
    private String mDescription = "??? Pokémon";
    private StatSet mBase = new StatSet();
    private Type mType1 = new Type();
    private Type mType2 = new Type();
    private int mLevelRequirement = 0;
    private int mNextDex = 0;
    private int mMainImage = 0;
    private int mIcon = 0;
    private int mBackImage = 0;
    private int mSound = 0;
    private int mCatchRate = 0;
    private int mMaleRatio = 0;
    private int mFemaleRatio = 0;
    private String mHeight = "";
    private String mWeight = "";

    /**
     * "The function is empty..."
     */
    public PokéDexData() {
    }

    /**
     * Creates the PokéDexData from the given values.
     * @param mDexNumber        The PokéDex number of the Pokémon.
     * @param mName             The name of the Pokémon.
     * @param mType1            The first type of the Pokémon.
     * @param mType2            The second type of the Pokémon.
     * @param mDescription      The Pokémon's PokéDex description.
     * @param mCatchRate        The Pokémon's catch rate.
     * @param mFemaleRatio      The Pokémon's female ratio.
     * @param mMaleRatio        The Pokémon's male ratio.
     * @param mBaseHP           The Pokémon's base HP.
     * @param mBaseAttack       The Pokémon's base attack stat.
     * @param mBaseDefense      The Pokémon's base defense stat.
     * @param mBaseSpAttack     The Pokémon's base special attack stat.
     * @param mBaseSpDefense    The Pokémon's base special defense stat.
     * @param mBaseSpeed        The Pokemon's base speed stat.
     * @param mLevelRequirement The Pokemon's evolution level requirement.
     * @param mNextDex          The Pokemon's next evolution's PokéDex number.
     * @param mMainImage        The Pokémon's main image resource.
     * @param mBackImage        The Pokémon's back image resource.
     * @param mIcon             The Pokémon's icon image resource.
     * @param mSound            The Pokémon's cry sound resource.
     * @param mHeight           The Pokémon's height.
     * @param mWeight           The Pokémon's weight.
     */
    public PokéDexData(int mDexNumber,
                       String mName,
                       Type mType1,
                       Type mType2,
                       String mDescription,
                       int mCatchRate,
                       int mFemaleRatio,
                       int mMaleRatio,
                       int mBaseHP,
                       int mBaseAttack,
                       int mBaseDefense,
                       int mBaseSpAttack,
                       int mBaseSpDefense,
                       int mBaseSpeed,
                       int mLevelRequirement,
                       int mNextDex,
                       int mMainImage,
                       int mBackImage,
                       int mIcon,
                       int mSound,
                       String mHeight,
                       String mWeight
    ) {
        this.mDexNumber = mDexNumber;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mBase = new StatSet(
                mBaseHP,
                mBaseAttack,
                mBaseDefense,
                mBaseSpAttack,
                mBaseSpDefense,
                mBaseSpeed
        );
        this.mType1 = mType1;
        this.mType2 = mType2;
        this.mLevelRequirement = mLevelRequirement;
        this.mNextDex = mNextDex;
        this.mMainImage = mMainImage;
        this.mIcon = mIcon;
        this.mBackImage = mBackImage;
        this.mSound = mSound;
        this.mCatchRate = mCatchRate;
        this.mMaleRatio = mMaleRatio;
        this.mFemaleRatio = mFemaleRatio;
        this.mHeight = mHeight;
        this.mWeight = mWeight;
    }

    /**
     * Returns the sound resource of the Pokémon's cry.
     * @return  The sound resource of the Pokémon's cry.
     */
    public int getSound() {
        return mSound;
    }

    /**
     * Returns the level requirement value of the PokéDexData.
     * @return  The level requirement value of the PokéDexData.
     */
    public int getLevelRequirement() {
        return mLevelRequirement;
    }

    /**
     * Returns the next evolution's PokéDex number of the PokéDexData.
     * @return  The next evolution's PokéDex number of the PokéDexData.
     */
    public int getNextDex() {
        return mNextDex;
    }

    /**
     * Returns the male ratio of the PokéDexData.
     * @return The male ratio of the PokéDexData.
     */
    public int getMaleRatio() {
        return mMaleRatio;
    }

    /**
     * Returns the female ratio of the PokéDexData.
     * @return  The female ratio of the PokéDexData.
     */
    public int getFemaleRatio() {
        return mFemaleRatio;
    }

    /**
     * Returns the PokéDex number of the Pokémon.
     * @return  The PokéDex number of the Pokémon.
     */
    public int getDexNumber() {
        return mDexNumber;
    }

    /**
     * Returns the name of the Pokémon.
     * @return The name of the Pokémon.
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets the name of the Pokémon.
     * @param mName The name of the Pokémon to be set.
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Returns the base StatSet of the Pokémon.
     * @return  The base StatSet of the Pokémon.
     */
    public StatSet getBase() {
        return mBase;
    }

    /**
     * Returns the Pokémon's first type.
     * @return  The Pokémon's first type.
     */
    public Type getType1() {
        return mType1;
    }

    /**
     * Returns the Pokémon's second type.
     * @return  The Pokémon's second type.
     */
    public Type getType2() {
        return mType2;
    }

    /**
     * Returns the main image resource of the PokéDexData.
     * @return  The main image resource of the PokéDexData.
     */
    public int getMainImage() {
        return mMainImage;
    }

    /**
     * Returns the icon image resource of the PokéDexData.
     * @return  The icon image resource of the PokéDexData.
     */
    public int getIcon() {
        return mIcon;
    }

    /**
     * Returns the back image resource of the PokéDexData.
     * @return  The back image resource of the PokéDexData.
     */
    public int getBackImage() {
        return mBackImage;
    }

    /**
     * Returns the catch rate of the PokéDexData.
     * @return  The catch rate resource of the PokéDexData.
     */
    public int getCatchRate() {
        return mCatchRate;
    }

    /**
     * Returns the Pokémon's description of the PokéDexData.
     * @return  The Pokémon's description of the PokéDexData.
     */
    public String getDescription() {
        return mDescription;
    }

    public boolean isEmpty(){
        return (this.mDexNumber == MISSINGNO);
    }

    /**
     * Returns the Pokémon's height of the PokéDexData.
     * @return  The Pokémon's height of the PokéDexData.
     */
    public String getHeight() {
        return mHeight;
    }

    /**
     * Returns the Pokémon's weight of the PokéDexData.
     * @return  The Pokémon's weight of the PokéDexData.
     */
    public String getWeight() {
        return mWeight;
    }
}
