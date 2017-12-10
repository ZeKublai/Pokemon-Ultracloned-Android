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
     * Initializes the ManagerSwitchState.
     * @param mPokémonButtons   The ArrayList of PokémonButtons from the Player's party.
     * @param mBackButton       The back Button.
     * @param mSwitchButton     The switch Button.
     * @param mMessage          The TextView where the message is displayed.
     * @param mManager          The Manager where the state is stored.
     */
    public ManagerSwitchState(ArrayList<PokémonButton> mPokémonButtons,
                              Button mBackButton,
                              Button mSwitchButton,
                              TextView mMessage,
                              Manager mManager) {
        this.mPokémonButtons = mPokémonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        this.mMessage.setText(Message.MESSAGE_SELECT_SWITCH);
    }

    /**
     * Executes when the Pokémon in the box has been pressed.
     * @param contex    Where the ListView is to be displayed.
     * @param view      Specific list of Pokémon seen in the adapter.
     * @param app       Used to access the PokémonApp functions.
     * @param pos       The index at the ListView.
     */
    @Override
    public void executePokemonListView(Activity contex, View view, PokemonApp app, int pos) {
        mManager.setSelectedProfile2(mManager.getPlayer().getBox().get(pos));
        switchPokemon();
    }

    /**
     * Executes when the Pokémon in the party has been pressed.
     * @param contex    Where the ListView is to be displayed.
     * @param app       Used to access the PokémonApp functions.
     * @param pos       The index at the ListView.
     */
    @Override
    public void executePokemonButton(Activity contex, PokemonApp app, int pos) {
        mManager.setSelectedProfile2(mManager.getPlayer().getPokemons().get(pos));
        switchPokemon();
    }

    /**
     * Returns to the ManagerMainState.
     */
    @Override
    public void executeSwitchButton(){
        noItemSelected();
        PokemonApp.setAsSwitchButton(mSwitchButton);
        mManager.getPokemonAdapter().notifyDataSetChanged();
        updatePokémonButtons();
        mManager.setState(mainState());
    }

    /**
     * Switches the 2 selected Pokémon.
     */
    public void switchPokemon(){
        PokémonProfile swap1 = new PokémonProfile(mManager.getSelectedProfile1());
        PokémonProfile swap2 = new PokémonProfile(mManager.getSelectedProfile2());
        mManager.getSelectedProfile1().loadProfile(swap2);
        mManager.getSelectedProfile2().loadProfile(swap1);
        mManager.getPokemonAdapter().notifyDataSetChanged();
        updatePokémonButtons();
        mManager.setState(mainState());
    }
}
