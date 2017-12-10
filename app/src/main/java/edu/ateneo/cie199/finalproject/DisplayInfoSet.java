package edu.ateneo.cie199.finalproject;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by John on 11/20/2017.
 * This class handles the functions and data members of how a Pokémon is shown in the Battle scene.
 */

public class DisplayInfoSet {
    protected TextView mTxvName;
    protected TextView mTxvLevel;
    protected ProgressBar mBarHP;
    protected ImageButton mImage;

    /**
     * Creates the DisplayInfoSet with the given parameters.
     * @param mTxvName  The TextView that would contain the Pokémon's name.
     * @param mTxvLevel The TextView that would contain the Pokémon's level.
     * @param mBarHP    The ProgressBar that would contain the Pokémon's HP.
     * @param mImage    The ImageButton that would conaint the Pokémon's image.
     */
    public DisplayInfoSet(TextView mTxvName,
                          TextView mTxvLevel,
                          ProgressBar mBarHP,
                          ImageButton mImage) {
        this.mTxvName = mTxvName;
        this.mTxvLevel = mTxvLevel;
        this.mBarHP = mBarHP;
        this.mImage = mImage;

        mImage.setBackgroundColor(PokemonApp.TRANSPARENT_COLOR);
        mBarHP.getProgressDrawable().setColorFilter(
                PokemonApp.BAR_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    /**
     * "The function is empty..."
     */
    public DisplayInfoSet() {
    }

    /**
     * Returns the DisplayInfoSet's ImageButton.
     * @return  The DisplayInfoSet's ImageButton.
     */
    public ImageView getImage() {
        return mImage;
    }

    /**
     * Sets the color of the ProgressBar according the to Pokémon's HP.
     * @param profile   The Pokémon where the HP value is obtained.
     */
    public void updateHp(PokémonProfile profile){
        mBarHP.setMax(profile.getHP());
        mBarHP.setProgress(profile.getCurrentHP());
        PokemonApp.updateHpBarColor(profile.getCurrentHP(), profile.getHP(), mBarHP);
    }

    /**
     * Sets the EXP value of the ProgressBar from the Pokémon's EXP.
     * @param profile   The Pokémon where the EXP value is obtained.
     */
    public void updateExp(PokémonProfile profile){

    }

    /**
     * Updates the entire DisplayInfoSet with a given Pokémon.
     * @param profile   The Pokémon where the data is obtained.
     */
    public void updatePokemon(PokémonProfile profile){
        mTxvName.setText(profile.getNickname());
        mTxvLevel.setText("Lv" + profile.getLevel());
        mImage.setBackgroundResource(profile.getDexData().getMainImage());
        updateHp(profile);
    }

    /**
     * Sets the ImageButton's image to that of the given Item.
     * @param item  The Item where the image resource ID of the would be obtained.
     */
    public void updateCatch(Item item){
        mImage.setBackgroundResource(item.getImageSprite());
    }
}
