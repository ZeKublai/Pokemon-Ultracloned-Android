package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/29/2017.
 */

public class ManagerSelectState extends ManagerState {

    public ManagerSelectState(ArrayList<PokemonButton> mPokemonButtons, Button mBackButton, Button mSwitchButton, TextView mMessage, Manager mManager) {
        this.mPokemonButtons = mPokemonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        mMessage.setText("Switch which Pokemon?");
    }

    @Override
    public void executePokemonListView(Activity ctx, View view, PokemonGoApp app, int pos){
        mManager.setSelectedProfile1(mManager.getPlayer().getBox().get(pos));
        mManager.setState(switchState());
        view.setBackground(app.getShape(PokemonGoApp.BACK_COLOR));
    }

    @Override
    public void executePokemonButton(Activity ctx, PokemonGoApp app, int pos){
        mManager.setSelectedProfile1(mManager.getPlayer().getPokemons().get(pos));
        mManager.setState(switchState());
        mPokemonButtons.get(pos).getButton().setBackground(app.getShape(PokemonGoApp.BACK_COLOR));
    }

    @Override
    public void executeSwitchButton(){
        noItemSelected();
        PokemonGoApp.setAsSwitchButton(mSwitchButton);
        mManager.getPokemonAdapter().notifyDataSetChanged();
        updatePokemons();
        mManager.setState(mainState());
    }
}
