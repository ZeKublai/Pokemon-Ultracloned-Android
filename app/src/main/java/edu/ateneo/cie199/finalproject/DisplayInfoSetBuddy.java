package edu.ateneo.cie199.finalproject;

import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by John on 11/21/2017.
 */

public class DisplayInfoSetBuddy extends DisplayInfoSet {

    private TextView mTxvHP;
    private ProgressBar mBarExp;

    public DisplayInfoSetBuddy() {
    }

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
        mImage.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        mImage.setBackgroundResource(R.drawable.boy_back);
        mBarHP.getProgressDrawable().setColorFilter(
                PokemonGoApp.BAR_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
        mBarExp.getProgressDrawable().setColorFilter(
                PokemonGoApp.RUN_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public TextView getTxvHP() {
        return mTxvHP;
    }
    public void setTxvHP(TextView mTxvHP) {
        this.mTxvHP = mTxvHP;
    }

    public ProgressBar getBarExp() {
        return mBarExp;
    }
    public void setBarExp(ProgressBar mBarExp) {
        this.mBarExp = mBarExp;
    }

    @Override
    public void updateHp(PokémonProfile profile){
        mBarHP.setMax(profile.getHP());
        mBarHP.setProgress(profile.getCurrentHP());
        updateHpText(profile);
        PokemonGoApp.updateHpBarColor(profile.getCurrentHP(), profile.getHP(), mBarHP);
    }

    private void updateHpText(PokémonProfile profile){
        mTxvHP.setText(profile.getCurrentHP() + "/" + profile.getHP());
    }

    @Override
    public void updateExp(PokémonProfile profile){
        mBarExp.setMax(profile.getExpNeeded());
        mBarExp.setProgress(profile.getCurrentExp());
    }

    @Override
    public void updatePokemon(PokémonProfile profile){
        mTxvName.setText(profile.getNickname());
        mTxvLevel.setText("Lv" + profile.getLevel());
        mImage.setBackgroundResource(profile.getDexData().getBackImage());
        updateHp(profile);
        updateExp(profile);
    }

    public void updateCatch(Item item){
        mImage.setBackgroundResource(item.getImageSprite());
    }

}
