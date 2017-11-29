package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/29/2017.
 */

public class ManagerSwitchState extends ManagerState {
    public ManagerSwitchState(ArrayList<PokemonButton> mPokemonButtons, Button mBackButton, Button mSwitchButton, TextView mMessage, Manager mManager) {
        this.mPokemonButtons = mPokemonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        this.mMessage.setText(Message.MESSAGE_SELECT_SWITCH);
    }

    @Override
    public void executePokemonListView(Activity ctx, View view, PokemonGoApp app, int pos) {
        mManager.setSelectedProfile2(mManager.getPlayer().getBox().get(pos));
        switchPokemon();
    }

    @Override
    public void executePokemonButton(Activity ctx, PokemonGoApp app, int pos) {
        mManager.setSelectedProfile2(mManager.getPlayer().getPokemons().get(pos));
        switchPokemon();
    }

    @Override
    public void executeSwitchButton(){
        noItemSelected();
        PokemonGoApp.setAsSwitchButton(mSwitchButton);
        mManager.getPokemonAdapter().notifyDataSetChanged();
        updatePokemons();
        mManager.setState(mainState());
    }

    public void switchPokemon(){
        PokemonProfile swap1 = new PokemonProfile(mManager.getSelectedProfile1());
        PokemonProfile swap2 = new PokemonProfile(mManager.getSelectedProfile2());
        mManager.getSelectedProfile1().loadProfile(swap2);
        mManager.getSelectedProfile2().loadProfile(swap1);
        mManager.getPokemonAdapter().notifyDataSetChanged();
        updatePokemons();
        mManager.setState(mainState());
    }
}
