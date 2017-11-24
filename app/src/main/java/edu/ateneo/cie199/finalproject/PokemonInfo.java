package edu.ateneo.cie199.finalproject;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by John on 11/20/2017.
 */

public class PokemonInfo {
    protected TextView mTxvName;
    protected TextView mTxvLevel;
    protected ProgressBar mBarHP;
    protected ImageButton mImage;

    public PokemonInfo(TextView mTxvName,
                       TextView mTxvLevel,
                       ProgressBar mBarHP,
                       ImageButton mImage) {
        this.mTxvName = mTxvName;
        this.mTxvLevel = mTxvLevel;
        this.mBarHP = mBarHP;
        this.mImage = mImage;

        mImage.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        mBarHP.getProgressDrawable().setColorFilter(
                PokemonGoApp.BAR_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public PokemonInfo() {
    }

    public TextView getTxvName() {
        return mTxvName;
    }
    public void setTxvName(TextView mTxvName) {
        this.mTxvName = mTxvName;
    }

    public TextView getTxvLevel() {
        return mTxvLevel;
    }
    public void setTxvLevel(TextView mTxvLevel) {
        this.mTxvLevel = mTxvLevel;
    }

    public ProgressBar getBarHP() {
        return mBarHP;
    }
    public void setBarHP(ProgressBar mBarHP) {
        this.mBarHP = mBarHP;
    }

    public ImageView getImage() {
        return mImage;
    }
    public void setImage(ImageButton mImage) {
        this.mImage = mImage;
    }

    public void updateHp(PokemonProfile profile){
        mBarHP.setMax(profile.getHP());
        mBarHP.setProgress(profile.getCurrentHP());
        PokemonGoApp.updateHpBarColor(profile.getCurrentHP(), profile.getHP(), mBarHP);
    }

    public void updateExp(PokemonProfile profile){

    }

    public void updatePokemon(PokemonProfile profile){
        mTxvName.setText(profile.getNickname());
        mTxvLevel.setText("Lv" + profile.getLevel());
        mImage.setBackgroundResource(profile.getDexData().getMainImage());
        updateHp(profile);
    }

    public void updateCatch(Item item){
        mImage.setBackgroundResource(item.getImageSprite());
    }

}
