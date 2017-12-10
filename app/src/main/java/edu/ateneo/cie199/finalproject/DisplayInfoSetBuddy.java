package edu.ateneo.cie199.finalproject;

import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by John on 11/21/2017.
 * This subclass handles the functions and data members of how
 * the Player's current Pokémon is shown in the Battle scene.
 */

public class DisplayInfoSetBuddy extends DisplayInfoSet {
    private TextView mTxvHP;
    private ProgressBar mBarExp;

    /**
     *
     * Creates the DisplayInfoSetBuddy with the given parameters.
     * @param mTxvName  The TextView that would contain the Pokémon's name.
     * @param mTxvHP    The TextView that would contain the Pokémon's HP.
     * @param mTxvLevel The TextView that would contain the Pokémon's level.
     * @param mBarHP    The ProgressBar that would contain the Pokémon's HP.
     * @param mBarExp   The ProgressBar that would contain the Pokémon's EXP.
     * @param mImage    The ImageButton that would conaint the Pokémon's image.
     */
    public DisplayInfoSetBuddy(TextView mTxvName,
                               TextView mTxvHP,
                               TextView mTxvLevel,
                               ProgressBar mBarHP,
                               ProgressBar mBarExp,
                               ImageButton mImage) {
        this.mTxvName = mTxvName;
        this.mTxvHP = mTxvHP;
        this.mTxvLevel = mTxvLevel;
        this.mBarHP = mBarHP;
        this.mBarExp = mBarExp;
        this.mImage = mImage;
        mImage.setBackgroundColor(PokemonApp.TRANSPARENT_COLOR);
        mImage.setBackgroundResource(R.drawable.boy_back);
        mBarHP.getProgressDrawable().setColorFilter(
                PokemonApp.BAR_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
        mBarExp.getProgressDrawable().setColorFilter(
                PokemonApp.RUN_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    /**
     * Sets the color of the ProgressBar and TextView according the to Pokémon's HP.
     * @param profile   The Pokémon where the HP value is obtained.
     */
    @Override
    public void updateHp(PokémonProfile profile){
        mBarHP.setMax(profile.getHP());
        mBarHP.setProgress(profile.getCurrentHP());
        updateHpText(profile);
        PokemonApp.updateHpBarColor(profile.getCurrentHP(), profile.getHP(), mBarHP);
    }

    /**
     * Sets the TextView according the to Pokémon's HP.
     * @param profile   The Pokémon where the HP value is obtained.
     */
    private void updateHpText(PokémonProfile profile){
        mTxvHP.setText(profile.getCurrentHP() + "/" + profile.getHP());
    }

    /**
     * Sets the EXP value of the ProgressBar from the Pokémon's EXP.
     * @param profile   The Pokémon where the EXP value is obtained.
     */
    @Override
    public void updateExp(PokémonProfile profile){
        mBarExp.setMax(profile.getExpNeeded());
        mBarExp.setProgress(profile.getCurrentExp());
    }

    /**
     * Updates the entire DisplayInfoSet with a given Pokémon.
     * @param profile   The Pokémon where the data is obtained.
     */
    @Override
    public void updatePokemon(PokémonProfile profile){
        mTxvName.setText(profile.getNickname());
        mTxvLevel.setText("Lv" + profile.getLevel());
        mImage.setBackgroundResource(profile.getDexData().getBackImage());
        updateHp(profile);
        updateExp(profile);
    }

    /**
     * Sets the ImageButton's image to that of the given Item.
     * @param item  The Item where the image resource ID of the would be obtained.
     */
    public void updateCatch(Item item){
        mImage.setBackgroundResource(item.getImageSprite());
    }
}
