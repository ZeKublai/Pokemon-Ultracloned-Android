package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/29/2017.
 * This class focuses on the Switch State of the Manager and extends the ManagerState
 */

public class ManagerSwitchState extends ManagerState {



    /**
     * determines the state of the Manager
     * @param mPokemonButtons trigger button for checking Pokemon dropdown option
     * @param mBackButton  trigger button for when selected state is canceled
     * @param mSwitchButton trigger for when switching pokemons
     * @param mMessage show messages
     * @param mManager accessing the Manager
     */
    public ManagerSwitchState(ArrayList<PokemonButton> mPokemonButtons, Button mBackButton, Button mSwitchButton, TextView mMessage, Manager mManager) {
        this.mPokemonButtons = mPokemonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        this.mMessage.setText(Message.MESSAGE_SELECT_SWITCH);
    }

    /**
     * it switches the pokemon from the box to the party
     * @param ctx where the listview is to be displayed
     * @param view specific list of pokemon seen in the adapter
     * @param app access the PokemonGoApp functions
     * @param pos index of the listview
     */
    @Override
    public void executePokemonListView(Activity ctx, View view, PokemonGoApp app, int pos) {
        mManager.setSelectedProfile2(mManager.getPlayer().getBox().get(pos));
        switchPokemon();
    }

    /**
     * it switches the pokemon from the party to the box
     * @param ctx where the listview is to be displayed
     * @param app access the PokemonGoApp functions
     * @param pos index of the listview
     */
    @Override
    public void executePokemonButton(Activity ctx, PokemonGoApp app, int pos) {
        mManager.setSelectedProfile2(mManager.getPlayer().getPokemons().get(pos));
        switchPokemon();
    }

    /**
     * triggers the switch event until canceled
     */
    @Override
    public void executeSwitchButton(){
        noItemSelected();
        PokemonGoApp.setAsSwitchButton(mSwitchButton);
        mManager.getPokemonAdapter().notifyDataSetChanged();
        updatePokemons();
        mManager.setState(mainState());
    }

    /**
     * The two selected Pokemon would switch places
     */
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
