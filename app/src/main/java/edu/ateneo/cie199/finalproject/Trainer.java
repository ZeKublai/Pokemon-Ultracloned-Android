package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/4/2017.
 * This subclass of the Player class contains the functions
 * and data members used for showing Trainer data and TrainerBattles.
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

    /**
     * "The function is empty..."
     */
    public Trainer(){

    }

    /**
     * Creates a new Trainer object from the values given.
     * @param mName         The created Trainer's name.
     * @param mTeam         The created Trainer's Team object.
     * @param mTier         The created Trainer's tier value.
     * @param mTitle        The created Trainer's title to be set.
     * @param mIntro        The created Trainer's intro statement.
     * @param mWin          The created Trainer's win statement.
     * @param mLose         The created Trainer's lose statement.
     * @param mFavPokéDexData1  The created Trainer's first favorite Pokémon PokéDexData.
     * @param mFavPokéDexData2  The created Trainer's second favorite Pokémon PokéDexData.
     * @param mImageMain        The created Trainer's main image resource ID.
     * @param mImageIcon        The created Trainer's icon image resource ID.
     */
    public Trainer(String mName,
                   Team mTeam,
                   int mTier,
                   String mTitle,
                   String mIntro,
                   String mWin,
                   String mLose,
                   PokéDexData mFavPokéDexData1,
                   PokéDexData mFavPokéDexData2,
                   int mImageMain,
                   int mImageIcon) {
        this.mName = mName;
        this.mTeam = mTeam;
        this.mTier = mTier;
        this.mTitle = mTitle;
        this.mIntro = mIntro;
        this.mWin = mWin;
        this.mLose = mLose;
        this.mFavoritePokéDexData1 = mFavPokéDexData1;
        this.mFavoritePokéDexData2 = mFavPokéDexData2;
        this.mImageMain = mImageMain;
        this.mImageIcon = mImageIcon;
    }

    /**
     * Returns the tier value of the Trainer.
     * @return  The tier value of the Trainer.
     */
    public int getTier() {
        return mTier;
    }

    /**
     * Returns the title of the Trainer.
     * @return  The title of the Trainer.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the intro statement of the Trainer.
     * @return  The intro statement of the Trainer.
     */
    public String getIntro() {
        return mIntro;
    }

    /**
     * Returns the win statement of the Trainer.
     * @return  The win statement of the Trainer.
     */
    public String getWin() {
        return mWin;
    }

    /**
     * Returns the lose statement of the Trainer.
     * @return  The lose statement of the Trainer.
     */
    public String getLose() {
        return mLose;
    }

    /**
     * Returns the PokéDexData of the Trainer's first Favorite Pokémon.
     * @return  The PokéDexData of the Trainer's first Favorite Pokémon.
     */
    public PokéDexData getFavoritePokemon1() {
        return mFavoritePokéDexData1;
    }

    /**
     * Returns the PokéDexData of the Trainer's second Favorite Pokémon.
     * @return  The PokéDexData of the Trainer's second Favorite Pokémon.
     */
    public PokéDexData getFavoritePokemon2() {
        return mFavoritePokéDexData2;
    }

    /**
     * Returns the image resource ID of the Trainer's main image.
     * @return  The image resource ID of the Trainer's main image.
     */
    public int getImageMain() {
        return mImageMain;
    }

    /**
     * Returns the image resource ID of the Trainer's icon image.
     * @return  The image resource ID of the Trainer's icon image.
     */
    public int getImageIcon() {
        return mImageIcon;
    }

    /**
     * Returns true if the Trainer's name is empty.
     * @return  True if the Trainer's name is empty.
     */
    public boolean isEmpty(){
        return this.mName.isEmpty();
    }

    /**
     * Returns a copy of the Trainer object.
     * @return  A copy of the Trainer object.
     */
    public Trainer generateTrainer(){
        return new Trainer(
                this.mName,
                this.mTeam,
                this.mTier,
                this.mTitle,
                this.mIntro,
                this.mWin,
                this.mLose,
                this.mFavoritePokéDexData1,
                this.mFavoritePokéDexData2,
                this.mImageMain,
                this.mImageIcon
        );
    }
}
