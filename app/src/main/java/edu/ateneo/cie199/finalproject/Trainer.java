package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/4/2017.
 */

public class Trainer extends Player{

    private Team mTeam;
    private int mTier;
    private String mIntro;
    private String mWin;
    private String mLose;
    private Pokemon mFavoritePokemon1;
    private Pokemon mFavoritePokemon2;

    private int mImageMain;
    private int mImageIcon;

    public Trainer(){
        this.mName = "";
    }

    public Trainer(String mName, Team mTeam, int mTier, String mIntro, String mWin, String mLose, Pokemon mFavoritePokemon1, Pokemon mFavoritePokemon2, int mImageMain, int mImageIcon) {
        this.mName = mName;
        this.mTeam = mTeam;
        this.mTier = mTier;
        this.mIntro = mIntro;
        this.mWin = mWin;
        this.mLose = mLose;
        this.mFavoritePokemon1 = mFavoritePokemon1;
        this.mFavoritePokemon2 = mFavoritePokemon2;
        this.mImageMain = mImageMain;
        this.mImageIcon = mImageIcon;
    }

    public Team getTeam() {
        return mTeam;
    }
    public void setTeam(Team mTeam) {
        this.mTeam = mTeam;
    }

    public int getTier() {
        return mTier;
    }
    public void setTier(int mTier) {
        this.mTier = mTier;
    }

    public String getIntro() {
        return mIntro;
    }
    public void setIntro(String mIntro) {
        this.mIntro = mIntro;
    }

    public String getWin() {
        return mWin;
    }
    public void setWin(String mWin) {
        this.mWin = mWin;
    }

    public String getLose() {
        return mLose;
    }
    public void setLose(String mLose) {
        this.mLose = mLose;
    }

    public Pokemon getFavoritePokemon1() {
        return mFavoritePokemon1;
    }
    public void setFavoritePokemon1(Pokemon mFavoritePokemon1) {
        this.mFavoritePokemon1 = mFavoritePokemon1;
    }

    public Pokemon getFavoritePokemon2() {
        return mFavoritePokemon2;
    }
    public void setFavoritePokemon2(Pokemon mFavoritePokemon2) {
        this.mFavoritePokemon2 = mFavoritePokemon2;
    }

    public int getImageMain() {
        return mImageMain;
    }
    public void setImageMain(int mImageMain) {
        this.mImageMain = mImageMain;
    }

    public int getImageIcon() {
        return mImageIcon;
    }
    public void setImageIcon(int mImageIcon) {
        this.mImageIcon = mImageIcon;
    }

    public boolean isEmpty(){
        return this.mName.isEmpty();
    }

    public Trainer generateTrainer(){
        return new Trainer(this.mName, this.mTeam, this.mTier, this.mIntro, this.mWin, this.mLose, this.mFavoritePokemon1, this.mFavoritePokemon2, this.mImageMain, this.mImageIcon);
    }
}
