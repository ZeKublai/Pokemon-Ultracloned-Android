package edu.ateneo.cie199.finalproject;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by John on 11/29/2017.
 * This class contains all the functions and data members used to
 * create a custom button for displaying Pokémon and its stats.
 */

public class PokémonButton {

    private Button mButton;
    private ImageView mImage;
    private ProgressBar mProgressBar;

    /**
     * Creates the PokémonButton object given a Button, Image and ProgressBar object.
     * @param mButton   The Button object of the created PokémonButton object.
     * @param mImage    The ImageView object of the created PokémonButton object.
     * @param mProgressBar  The ProgressBar object of the created PokémonButton object.
     */
    public PokémonButton(Button mButton, ImageView mImage, ProgressBar mProgressBar) {
        this.mButton = mButton;
        this.mImage = mImage;
        this.mProgressBar = mProgressBar;
    }

    /**
     * Returns the Button object of the PokémonButton object.
     * @return  The Button object of the PokémonButton object.
     */
    public Button getButton() {
        return mButton;
    }

    /**
     * Sets the custom button's Visibility.
     * @param visibility    The Visibility to be set.
     */
    public void setVisibility(int visibility){
        mButton.setVisibility(visibility);
        mImage.setVisibility(visibility);
        mProgressBar.setVisibility(visibility);
    }

    /**
     * Updates the PokémonButton's Image, Progressbar
     * and Button object given a PokémonProfile object.
     * @param profile   The PokémonProfile that will be used to update the PokémonButton.
     */
    public void setData(PokémonProfile profile){
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
