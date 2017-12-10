package edu.ateneo.cie199.finalproject;

import java.util.ArrayList;

import static java.lang.Math.floor;

/**
 * Created by John on 11/4/2017.
 * This class contains the functions and data members for the individual profiles of Pokémons.
 */

public class PokémonProfile {
    public static int MAX_POKEMON_LEVEL = 100;
    public static int MAX_IV_VALUE = 31;
    public static int MAX_NATURE_VALUE = 110;
    public static int MAX_POKEMON_MOVES = 4;
    public static int MIN_HP = 0;

    public static int GENDER_NONE = 0;
    public static int GENDER_MALE = 1;
    public static int GENDER_FEMALE = 2;

    private int mId = 0;
    private int mDexNumber = 0;
    private String mNickname = "";
    private int mGender = GENDER_NONE;
    private int mLevel = 0;
    private int mCurrentHP = 0;
    private int mCurrentExp = 0;

    private PokéDexData mDexData = new PokéDexData();
    private StatSet mIV = new StatSet();
    private StatSet mEV = new StatSet();
    private StatSet mNature = new StatSet();

    private ArrayList<Move> mMoves = new ArrayList<>();

    /**
     * Creates a PokémonProfile object from a PokéDexData object and a level value. Usually
     * used in generating the enemy in battles and for initializing and loading player data.
     * @param mId       The given identification number for the created PokémonProfile object.
     * @param pokemon   The PokéDexData for the created PokémonProfile object.
     * @param mLevel    The level value for the created PokémonProfile object.
     */
    public PokémonProfile(int mId, PokéDexData pokemon, int mLevel) {
        this.mId = mId;
        this.mDexNumber = pokemon.getDexNumber();
        this.mNickname = pokemon.getName();
        this.mDexData = pokemon;

        if(pokemon.getFemaleRatio() + pokemon.getMaleRatio() == 0){
            this.mGender = GENDER_NONE;
        }
        else {
            if (PokemonApp.getIntegerRNG(pokemon.getFemaleRatio()
                    + pokemon.getMaleRatio()) > pokemon.getMaleRatio()) {
                this.mGender = GENDER_FEMALE;
            } else {
                this.mGender = GENDER_MALE;
            }
        }

        this.mLevel = mLevel;
        this.mIV = new StatSet(MAX_IV_VALUE);
        this.mEV = new StatSet();
        this.mNature = new StatSet(MAX_NATURE_VALUE);
        this.mCurrentHP = getHP();
        this.mCurrentExp = 0;
    }

    /**
     * Creates a new PokémonProfile from an existing PokémonProfile.
     * @param profile   The PokémonProfile that will be used to create the new PokémonProfile.
     */
    public PokémonProfile(PokémonProfile profile) {
        this.mId = profile.mId;
        this.mDexNumber = profile.mDexNumber;
        this.mNickname = profile.mNickname;
        this.mGender = profile.mGender;
        this.mLevel = profile.mLevel;
        this.mCurrentHP = profile.mCurrentHP;
        this.mCurrentExp = profile.mCurrentExp;
        this.mDexData = profile.mDexData;
        this.mIV = profile.mIV;
        this.mEV = profile.mEV;
        this.mNature = profile.mNature;
        this.mMoves = profile.mMoves;
    }

    /**
     * Updates the PokémonProfile values from an existing PokémonProfile.
     * @param profile   The PokémonProfile that will be used to update the PokémonProfile.
     */
    public void loadProfile(PokémonProfile profile){
        this.mId = profile.mId;
        this.mDexNumber = profile.mDexNumber;
        this.mNickname = profile.mNickname;
        this.mGender = profile.mGender;
        this.mLevel = profile.mLevel;
        this.mCurrentHP = profile.mCurrentHP;
        this.mCurrentExp = profile.mCurrentExp;
        this.mDexData = profile.mDexData;
        this.mIV = profile.mIV;
        this.mEV = profile.mEV;
        this.mNature = profile.mNature;
        this.mMoves = profile.mMoves;
    }

    /**
     * "The function is empty..."
     */
    public PokémonProfile() {
    }

    /**
     * This function computes for the total stat of the given attribute of the PokémonProfile.
     * @param baseStat      The base stat of the attribute.
     * @param ivStat        The IV stat of the attribute.
     * @param evStat        The EV stat of the attribute.
     * @param level         The level value of the PokémonProfile.
     * @param natureStat    The nature stat of the attribute.
     * @return              The total value of the given stat of the PokémonProfile.
     */
    private int getStat(int baseStat, int ivStat, int evStat, int level, int natureStat){
        double stat = floor(floor((
                2.0
                * ((double)baseStat)
                + ((double)ivStat)
                + ((double)evStat)) *
                ((double)level)
                / 100.0 + 5.0)
                * ((double)natureStat)
                / 100.0
        );
        return ((int) stat);
    }

    /**
     * Returns the PokéDex Data of the PokémonProfile.
     * @return  The PokéDex Data of the PokémonProfile.
     */
    public PokéDexData getDexData() {
        return mDexData;
    }

    /**
     * Returns the gender value of the PokémonProfile.
     * @return  The gender value of the PokémonProfile.
     */
    public int getGender() {
        return mGender;
    }

    /**
     * Sets the gender value of the PokémonProfile.
     * @param mGender   The gender value to be set.
     */
    public void setGender(int mGender) {this.mGender = mGender;}

    /**
     * Returns the identification number of the PokémonProfile.
     * @return  The identification number of the PokémonProfile.
     */
    public int getId() {
        return mId;
    }

    /**
     * Returns the PokéDex number of the PokémonProfile.
     * @return  The PokéDex number of the PokémonProfile.
     */
    public int getDexNumber() {
        return mDexNumber;
    }

    /**
     * Returns the nickname of the PokémonProfile.
     * @return  The nickname of the PokémonProfile.
     */
    public String getNickname() {
        return mNickname;
    }

    /**
     * Sets the nickname of the PokémonProfile.
     * @param mNickname The nickname to be set.
     */
    public void setNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    /**
     * Returns the level value of the PokémonProfile.
     * @return  The level value of the PokémonProfile.
     */
    public int getLevel() {
        return mLevel;
    }

    /**
     * Sets the level value of the PokémonProfile.
     * @param mLevel    The level value to be set.
     */
    public void setLevel(int mLevel) {
        this.mLevel = mLevel;
    }

    /**
     * Returns the current HP value of the PokémonProfile.
     * @return  The current HP value fo the PokémonProfile.
     */
    public int getCurrentHP() {
        return mCurrentHP;
    }

    /**
     * Sets the current HP value of the PokémonProfile.
     * @param mCurrentHP    The current HP value to be set.
     */
    public void setCurrentHP(int mCurrentHP) {
        this.mCurrentHP = mCurrentHP;
    }

    /**
     * Returns the current EXP value of the PokémonProfile.
     * @return  The current EXP value of the PokémonProfile.
     */
    public int getCurrentExp() {
        return mCurrentExp;
    }

    /**
     * Sets the current EXP value of the PokémonProfile.
     * @param mCurrentExp   The current EXP value to be set.
     */
    public void setCurrentExp(int mCurrentExp) {
        this.mCurrentExp = mCurrentExp;
    }

    /**
     * Returns the EV StatSet of the PokémonProfile.
      * @return The EV StatSet of the PokémonProfile.
     */
    public StatSet getEV(){
        return mEV;
    }

    /**
     * Returns the IV StatSet of the PokémonProfile.
     * @return  The IV StatSet of the PokémonProfile.
     */
    public StatSet getIV() {return mIV;}

    /**
     * Returns the nature StatSet of the PokémonProfile.
     * @return  The nature StatSet of the PokémonProfile.
     */
    public StatSet getNature() {return mNature;}

    /**
     * Sets the EV StatSet of the PokémonProfile.
     * @param Stat  The EV StatSet to be set.
     */
    public void setEV(StatSet Stat){
        this.mEV = Stat;
    }

    /**
     * Sets the IV StatSet of the PokémonProfile.
     * @param Stat  The IV StatSet to be set.
     */
    public void setIV(StatSet Stat){
        this.mIV = Stat;
    }

    /**
     * Sets the nature StatSet of the PokémonProfile.
     * @param Stat  The nature StatSet to be set.
     */
    public void setNature(StatSet Stat){
        this.mNature = Stat;
    }

    /**
     * Returns the total HP of the PokémonProfile.
     * @return  The total HP of the PokémonProfile.
     */
    public int getHP(){
        return ((int) floor((2
                * mDexData.getBase().getHP()
                + mIV.getHP()
                + mEV.getHP())
                * mLevel
                / 100
                + mLevel
                + 10)
        );
    }

    /**
     * Returns the total attack of the PokémonProfile.
     * @return  The total attack of the PokémonProfile.
     */
    public int getAttack(){
        return getStat(
                mDexData.getBase().getAttack(),
                mIV.getAttack(),
                mEV.getAttack(),
                mLevel,
                mNature.getAttack()
        );
    }

    /**
     * Returns the total defense of the PokémonProfile.
     * @return  The total defense of the PokémonProfile.
     */
    public int getDefense(){
        return getStat(
                mDexData.getBase().getDefense(),
                mIV.getDefense(),
                mEV.getDefense(),
                mLevel,
                mNature.getDefense()
        );
    }

    /**
     * Returns the total special attack of the PokémonProfile.
     * @return  The total special attack of the PokémonProfile.
     */
    public int getSpAttack(){
        return getStat(
                mDexData.getBase().getSpAttack(),
                mIV.getSpAttack(),
                mEV.getSpAttack(),
                mLevel,
                mNature.getSpAttack()
        );
    }

    /**
     * Returns the total special defense of the PokémonProfile.
     * @return  The total special defense of the PokémonProfile.
     */
    public int getSpDefense(){
        return getStat(
                mDexData.getBase().getSpDefense(),
                mIV.getSpDefense(),
                mEV.getSpDefense(),
                mLevel,
                mNature.getSpDefense()
        );
    }

    /**
     * Returns the total speed of the PokémonProfile.
     * @return  The total speed of the PokémonProfile.
     */
    public int getSpeed(){
        return getStat(
                mDexData.getBase().getSpeed(),
                mIV.getSpeed(),
                mEV.getSpeed(),
                mLevel,
                mNature.getSpeed()
        );
    }

    /**
     * Returns if the PokémonProfile can evolve or not.
     * @param nextDex   The PokéDextData of the PokémonProfile's next evolution.
     * @return          Returns true if the PokémonProfile can evolve else false.
     */
    public boolean canEvolve(PokéDexData nextDex){
        if(nextDex.isEmpty()){
            return false;
        }
        if(mLevel >= nextDex.getLevelRequirement()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Evolves the PokémonProfile to the next evolution.
     * @param nextDex   The PokéDexData of the PokémonProfile's next evolution.
     */
    public void evolve(PokéDexData nextDex){
        this.mDexData = nextDex;
    }

    /**
     * Returns the amount of EXP needed for the PokémonProfile to level up.
     * @return  The amount of EXP needed for the PokémonProfile to level up.
     */
    public int getExpNeeded(){
        return mLevel*100;
    }

    /**
     * Returns the total amount of EXP obtained by the PokémonProfile.
     * @return  The total amount of EXP obtained by the PokémonProfile.
     */
    public int getTotalExp() {
        int totalExperience = 0;
        for(int index = 1; index < mLevel; index++){
            totalExperience = totalExperience + index*100;
        }
        totalExperience = totalExperience + mCurrentExp;
        return totalExperience;
    }

    /**
     * Returns the PokémonProfile's ArrayList of Moves.
     * @return  The PokémonProfile's ArrayList of Moves.
     */
    public ArrayList<Move> getMoves() {
        return mMoves;
    }

    /**
     * Returns true if the PokémonProfile is a MissingNo.
     * @return  True if the PokémonProfile is a MissingNo.
     */
    public boolean isEmpty(){
        if(this.mDexNumber == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Returns if all of all of the PP of the PokémonProfile's Moves are maxed out.
     * @return  True if all of all of the PP of the PokémonProfile's Moves are maxed out else false.
     */
    public boolean allMovesPPisFull(){
        int fullMoveCount = 0;
        for(int index = 0; index < MAX_POKEMON_MOVES; index++){
            if(mMoves.get(index).getCurrentPP() == mMoves.get(index).getMaxPP()){
                fullMoveCount = fullMoveCount + 1;
            }
        }
        if(fullMoveCount == MAX_POKEMON_MOVES){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Returns the String that will be displayed in the PokémonButton.
     * @return  the String that will be displayed in the PokémonButton.
     */
    public String getButtonString(){
        if(this.isEmpty()){
            return "\n\n";
        }
        else{
            return mNickname + "\nLv" + mLevel + "\tHP " + mCurrentHP + "/" + getHP() + "\n";
        }
    }

    /**
     * Returns if the PP of all the PokémonProfile's Moves are 0.
     * @return  True if the PP of all the PokémonProfile's Moves are 0 else false.
     */
    public boolean noMorePP(){
        for(int index = 0; index < mMoves.size(); index++){
            if(mMoves.get(index).getCurrentPP() > 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a String object of the PokémonProfile's gender.
     * @return  A String object of the PokémonProfile's gender.
     */
    public String getGenderString(){
        if(mGender == GENDER_MALE){
            return "♂";
        }
        else if(mGender == GENDER_FEMALE){
            return "♀";
        }
        return "";
    }
}
