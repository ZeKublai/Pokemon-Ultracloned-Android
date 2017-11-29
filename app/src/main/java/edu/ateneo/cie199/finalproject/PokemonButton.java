package edu.ateneo.cie199.finalproject;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by John on 11/29/2017.
 */

public class PokemonButton {

    private Button mButton;
    private ImageView mImage;
    private ProgressBar mProgressBar;

    public PokemonButton(Button mButton, ImageView mImage, ProgressBar mProgressBar) {
        this.mButton = mButton;
        this.mImage = mImage;
        this.mProgressBar = mProgressBar;
    }

    public Button getButton() {
        return mButton;
    }
    public void setButton(Button mButton) {
        this.mButton = mButton;
    }

    public ImageView getImage() {
        return mImage;
    }
    public void setImage(ImageView mImage) {
        this.mImage = mImage;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }
    public void setProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }

    public void setVisibility(int visibility){
        mButton.setVisibility(visibility);
        mImage.setVisibility(visibility);
        mProgressBar.setVisibility(visibility);
    }

    public void setData(PokemonProfile profile){
        mButton.setClickable(!profile.isEmpty());
        if(!profile.isEmpty()){
            if(profile.getCurrentHP() <= 0){
                PokemonGoApp.setButtonBorder(mButton, PokemonGoApp.DEAD_COLOR);
            }
            else{
                PokemonGoApp.setButtonBorder(mButton, PokemonGoApp.POKEMON_COLOR);
            }
            mButton.setText(profile.getButtonString());
            setVisibility(View.VISIBLE);
            mProgressBar.setMax(profile.getHP());
            mProgressBar.setProgress(profile.getCurrentHP());
            PokemonGoApp.updateHpBarColor(profile.getCurrentHP(), profile.getHP(), mProgressBar);
            mImage.setImageResource(profile.getDexData().getIcon());
        }
        else{
            setVisibility(View.INVISIBLE);
        }
    }

}
