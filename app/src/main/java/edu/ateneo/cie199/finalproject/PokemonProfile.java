package edu.ateneo.cie199.finalproject;

import java.util.Random;

import static java.lang.Math.floor;

/**
 * Created by John on 11/4/2017.
 */

public class PokemonProfile {
    public static int MAX_POKEMON_LEVEL = 100;
    public static int MAX_IV_VALUE = 31;
    public static int MAX_NATURE_VALUE = 110;
    public static int MAX_POKEMON_MOVES = 4;

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

    private Pokemon mDexData = new Pokemon();
    private StatSet mIV = new StatSet();
    private StatSet mEV = new StatSet();
    private StatSet mNature = new StatSet();

    private Move[] mMoves = new Move[MAX_POKEMON_MOVES];

    public PokemonProfile(int mId, Pokemon pokemon) {
        this.mId = mId;
        this.mDexNumber = pokemon.getDexNumber();
        this.mNickname = pokemon.getName();
        this.mDexData = pokemon;

        if(pokemon.getFemaleRatio() + pokemon.getMaleRatio() == 0){
            this.mGender = GENDER_NONE;
        }
        else {
            if (PokemonGoApp.getIntegerRNG(pokemon.getFemaleRatio()
                    + pokemon.getMaleRatio()) > pokemon.getMaleRatio()) {
                this.mGender = GENDER_FEMALE;
            } else {
                this.mGender = GENDER_MALE;
            }
        }

        this.mLevel = PokemonGoApp.getIntegerRNG(MAX_POKEMON_LEVEL) + 1;
        this.mIV = new StatSet(MAX_IV_VALUE);
        this.mEV = new StatSet();
        this.mNature = new StatSet(MAX_NATURE_VALUE);
        this.mCurrentHP = getHP();
        this.mCurrentExp = 0;

        for(int index = 0; index < mMoves.length; index++){
            mMoves[index] = new Move();
        }
    }

    public PokemonProfile(int mId, int mLevel, Pokemon pokemon) {
        this.mId = mId;
        this.mDexNumber = pokemon.getDexNumber();
        this.mNickname = pokemon.getName();
        this.mDexData = pokemon;

        Random s = new Random();
        if(pokemon.getFemaleRatio() + pokemon.getMaleRatio() == 0){
            this.mGender = GENDER_NONE;
        }
        else {
            if (s.nextInt(pokemon.getFemaleRatio() + pokemon.getMaleRatio()) > pokemon.getMaleRatio()) {
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

        for(int index = 0; index < mMoves.length; index++){
            mMoves[index] = new Move();
        }
    }

    public PokemonProfile() {
        for(int index = 0; index < mMoves.length; index++){
            mMoves[index] = new Move();
        }
    }

    private int getStat(int baseStat, int ivStat, int evStat, int level, int natureStat){
        double stat = floor(floor((2 * baseStat + ivStat + evStat) * level / 100 + 5) 
                * natureStat / 100);
        return ((int) stat);
    }

    public Pokemon getDexData() {
        return mDexData;
    }
    public void setDexData(Pokemon mDexData) {
        this.mDexData = mDexData;
    }

    public int getGender() {
        return mGender;
    }
    public int getId() {
        return mId;
    }
    public int getDexNumber() {
        return mDexNumber;
    }
    public void setDexNumber(int mDexNumber) {
        this.mDexNumber = mDexNumber;
    }

    public String getNickname() {
        return mNickname;
    }
    public void setNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public int getLevel() {
        return mLevel;
    }
    public void setLevel(int mLevel) {
        this.mLevel = mLevel;
    }

    public int getCurrentHP() {
        return mCurrentHP;
    }
    public void setCurrentHP(int mCurrentHP) {
        this.mCurrentHP = mCurrentHP;
    }

    public int getCurrentExp() {
        return mCurrentExp;
    }
    public void setCurrentExp(int mCurrentExp) {
        this.mCurrentExp = mCurrentExp;
    }

    public StatSet getEV(){
        return mEV;
    }

    public int getHP(){
        return ((int) floor((2 * mDexData.getBase().getHP()
                + mIV.getHP() + mEV.getHP()) * mLevel / 100 + mLevel + 10));
    }
    public int getAttack(){
        return getStat(mDexData.getBase().getAttack(), mIV.getAttack(), mEV.getAttack(), mLevel,
                mNature.getAttack());
    }
    public int getDefense(){
        return getStat(mDexData.getBase().getDefense(), mIV.getDefense(), mEV.getDefense(), mLevel,
                mNature.getDefense());
    }
    public int getSpAttack(){
        return getStat(mDexData.getBase().getSpAttack(), mIV.getSpAttack(), mEV.getSpAttack(),
                mLevel, mNature.getSpAttack());
    }
    public int getSpDefense(){
        return getStat(mDexData.getBase().getSpDefense(), mIV.getSpDefense(), mEV.getSpDefense(),
                mLevel, mNature.getSpDefense());
    }
    public int getSpeed(){
        return getStat(mDexData.getBase().getSpeed(), mIV.getSpeed(), mEV.getSpeed(), mLevel,
                mNature.getSpeed());
    }

    public int getAttack(Move move){
        if(move.getCategory() == Move.PHYSICAL){
            return getAttack();
        }
        else if(move.getCategory() == Move.SPECIAL){
            return getSpAttack();

        }
        return 0;
    }
    public int getDefense(Move move){
        if(move.getCategory() == Move.PHYSICAL){
            return getDefense();
        }
        else if(move.getCategory() == Move.SPECIAL){
            return getSpDefense();

        }
        return 0;
    }


    /*TODO
    Needs Tweaking
     */
    public int getExperienceNeeded(){
        return mLevel*1000;
    }
    public int getTotalExperience() {
        int totalExperience = 0;
        for(int index = 1; index < mLevel; index++){
            totalExperience = totalExperience + mLevel*1000;
        }
        totalExperience = totalExperience + mCurrentExp;
        return totalExperience;
    }

    public Move[] getMoves() {
        return mMoves;
    }
    public void setMoves(Move[] mMoves) {
        this.mMoves = mMoves;
    }

    public boolean isEmpty(){
        if(this.mDexNumber == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public String getButtonString(){
        if(this.isEmpty()){
            return "\n";
        }
        else{
            return mNickname + "\n HP " + mCurrentHP + "/" + getHP();
        }
    }
}
