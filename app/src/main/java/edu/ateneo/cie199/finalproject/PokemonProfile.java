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

    private int mId = 0;
    private int mDexNumber = 0;
    private String mNickname = "";
    private boolean mGender = true;
    private int mLevel = 0;
    private int mCurrentHP = 0;
    private int mCurrentExp = 0;

    private StatSet mIV = new StatSet();
    private StatSet mEV = new StatSet();
    private StatSet mNature = new StatSet();

    private Move[] mMoves = new Move[MAX_POKEMON_MOVES];

    public PokemonProfile(int mId, Pokemon pokemon) {
        this.mId = mId;
        this.mDexNumber = pokemon.getDexNumber();
        this.mNickname = pokemon.getName();

        Random s = new Random();

        if(s.nextInt(pokemon.getFemaleRatio() + pokemon.getMaleRatio()) > pokemon.getMaleRatio()){
            this.mGender = false;
        }
        else{
            this.mGender = true;
        }

        this.mLevel = s.nextInt(MAX_POKEMON_LEVEL);
        this.mIV = new StatSet(MAX_IV_VALUE);
        this.mEV = new StatSet();
        this.mNature = new StatSet(MAX_NATURE_VALUE);
        this.mCurrentHP = getHP(pokemon.getBase().getHP());
        this.mCurrentExp = getExperienceNeeded(mLevel - 1);

        for(int index = 0; index < mMoves.length; index++){
            mMoves[index] = new Move();
        }
    }

    public PokemonProfile(int mId, int mLevel, Pokemon pokemon) {
        this.mId = mId;
        this.mDexNumber = pokemon.getDexNumber();
        this.mNickname = pokemon.getName();

        Random s = new Random();

        if(s.nextInt(pokemon.getFemaleRatio() + pokemon.getMaleRatio()) > pokemon.getMaleRatio()){
            this.mGender = false;
        }
        else{
            this.mGender = true;
        }

        this.mLevel = mLevel;
        this.mIV = new StatSet(MAX_IV_VALUE);
        this.mEV = new StatSet();
        this.mNature = new StatSet(MAX_NATURE_VALUE);
        this.mCurrentHP = getHP(pokemon.getBase().getHP());
        this.mCurrentExp = getExperienceNeeded(mLevel - 1);

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

    public boolean getGender() {
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

    public int getHP(int baseHP){
        return ((int) floor((2 * baseHP + mIV.getHP() + mEV.getHP()) * mLevel / 100 + mLevel + 10));
    }
    public int getAttack(int baseAttack){
        return getStat(baseAttack, mIV.getAttack(), mEV.getAttack(), mLevel, mNature.getAttack());
    }
    public int getDefense(int baseDefense){
        return getStat(baseDefense, mIV.getDefense(), mEV.getDefense(), mLevel,
                mNature.getDefense());
    }
    public int getSpAttack(int baseSpAttack){
        return getStat(baseSpAttack, mIV.getSpAttack(), mEV.getSpAttack(), mLevel,
                mNature.getSpAttack());
    }
    public int getSpDefense(int baseSpDefense){
        return getStat(baseSpDefense, mIV.getSpDefense(), mEV.getSpDefense(), mLevel,
                mNature.getSpDefense());
    }
    public int getSpeed(int baseSpeed){
        return getStat(baseSpeed, mIV.getSpeed(), mEV.getSpeed(), mLevel, mNature.getSpeed());
    }

    /*TODO
    Needs Tweaking
     */
    public int getExperienceNeeded(int level){
        return level*1000;
    }

    public Move[] getMoves() {
        return mMoves;
    }
    public void setMoves(Move[] mMoves) {
        this.mMoves = mMoves;
    }
}
