package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/4/2017.
 */

public class Trainer extends Player{

    private Team mTeam;
    private int mTier;
    private String mTitle;
    private String mIntro;
    private String mWin;
    private String mLose;
    private PokéDexData mFavoritePokéDexData1;
    private PokéDexData mFavoritePokéDexData2;

    private int mImageMain;
    private int mImageIcon;

    public Trainer(){
        this.mName = "";
    }

    public Trainer(String mName, Team mTeam, int mTier, String mTitle, String mIntro, String mWin, String mLose, PokéDexData mFavoritePokéDexData1, PokéDexData mFavoritePokéDexData2, int mImageMain, int mImageIcon) {
        this.mName = mName;
        this.mTeam = mTeam;
        this.mTier = mTier;
        this.mTitle = mTitle;
        this.mIntro = mIntro;
        this.mWin = mWin;
        this.mLose = mLose;
        this.mFavoritePokéDexData1 = mFavoritePokéDexData1;
        this.mFavoritePokéDexData2 = mFavoritePokéDexData2;
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

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
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

    public PokéDexData getFavoritePokemon1() {
        return mFavoritePokéDexData1;
    }
    public void setFavoritePokemon1(PokéDexData mFavoritePokéDexData1) {
        this.mFavoritePokéDexData1 = mFavoritePokéDexData1;
    }

    public PokéDexData getFavoritePokemon2() {
        return mFavoritePokéDexData2;
    }
    public void setFavoritePokemon2(PokéDexData mFavoritePokéDexData2) {
        this.mFavoritePokéDexData2 = mFavoritePokéDexData2;
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
        return new Trainer(this.mName, this.mTeam, this.mTier, this.mTitle, this.mIntro, this.mWin, this.mLose, this.mFavoritePokéDexData1, this.mFavoritePokéDexData2, this.mImageMain, this.mImageIcon);
    }
}
