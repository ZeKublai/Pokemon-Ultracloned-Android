package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/5/2017.
 * This abstract class contains functions and data members
 * needed for the Pokémon to have and perform Moves.
 */

public abstract class Move {
    public static int CATEGORY_PHYSICAL = 0;
    public static int CATEGORY_SPECIAL = 1;
    public static int CATEGORY_STATUS = 2;
    public static int CATEGORY_NONE = 3;

    public static int MAX_CRITICAL = 16;
    public static int MAX_ACCURACY = 100;
    public static double MIN_DAMAGE = 1.0;

    protected String mName = "";
    protected Type mType = new Type();
    protected int mMaxPP = 0;
    protected int mCurrentPP = 0;
    protected int mPower = 0;
    protected int mAccuracy = 0;

    /**
     * "The function is empty..."
     */
    public Move() {
    }

    /**
     * Returns the name of the Move.
     * @return  The name of the Move.
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets the name of the Move.
     * @param mName The name of the Move to be set.
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Returns the type of the Move.
     * @return  The type of the Move.
     */
    public Type getType() {
        return mType;
    }

    /**
     * Sets the type of Move.
     * @param mType The Type Object to be set.
     */
    public void setType(Type mType) {
        this.mType = mType;
    }

    /**
     * Returns the max PP value of the Move.
     * @return  The max PP value of the Move.
     */
    public int getMaxPP() {
        return mMaxPP;
    }

    /**
     * Returns the current PP value of the Move.
     * @return  The current PP value of the Move.
     */
    public int getCurrentPP() {
        return mCurrentPP;
    }

    /**
     * Sets the current PP value of the Move.
     * @param mCurrentPP    The current PP value to be set.
     */
    public void setCurrentPP(int mCurrentPP) {
        this.mCurrentPP = mCurrentPP;
    }

    /**
     * Returns the accuracy value of the Move.
     * @return  The accuracy value of the Move.
     */
    public int getAccuracy() {
        return mAccuracy;
    }

    /**
     * Returns if the name of the Move is empty.
     * @return  True if the name of the Move is empty.
     */
    public boolean isEmpty(){
        return (mName.isEmpty());
    }

    /**
     * Given a String object, the function returns the category identification number of the move.
     * @param Category  The String object to be passed to identify the category.
     * @return          The category identification number.
     */
    public static int decodeCategory(String Category){
        int categoryNumber;
        if (Category.equals("Physical")){
            categoryNumber = CATEGORY_PHYSICAL;
        }
        else if (Category.equals("Special")){
            categoryNumber = CATEGORY_SPECIAL;
        }
        else if(Category.equals("Status")){
            categoryNumber = CATEGORY_STATUS;
        }
        else{
            return CATEGORY_NONE;
        }
        return categoryNumber;
    }

    /**
     * Returns a duplicate of the Move object.
     * @return  A duplicate of the Move object.
     */
    public abstract Move generateCopy();

    /**
     * Given a PokémonProfile attacker, this function executes the Move on the PokémonProfile
     * defender depending on the given accuracy roll and the critical roll.
     * @param accuracyResult    Usually a random number generated from 0 to 100.
     * @param criticalResult    Usually a random number generated from 0 to 16.
     * @param attacker          The PokémonProfile that would perform the Move.
     * @param defender          The defending PokémonProfile.
     */
    public abstract void execute(int accuracyResult,
                                 int criticalResult,
                                 PokémonProfile attacker,
                                 PokémonProfile defender);

    /**
     * Executes recoil damage on the PokémonProfile attacker.
     * @param attacker  The PokémonProfile that would receive the recoil damage.
     * @return          True if execution of recoil is successful.
     */
    public abstract boolean executeRecoil(PokémonProfile attacker);
}
