package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/29/2017.
 * This class focuses on the Select State of the Manager and extends the ManagerState
 */

public class ManagerSelectState extends ManagerState {

    /**
     * Initializes the ManagerSelectState.
     * @param mPokémonButtons   The ArrayList of PokémonButtons from the Player's party.
     * @param mBackButton       The back Button.
     * @param mSwitchButton     The switch Button.
     * @param mMessage          The TextView where the message is displayed.
     * @param mManager          The Manager where the state is stored.
     */
    public ManagerSelectState(ArrayList<PokémonButton> mPokémonButtons, Button mBackButton, Button mSwitchButton, TextView mMessage, Manager mManager) {
        this.mPokémonButtons = mPokémonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        mMessage.setText("Switch which PokéDexData?");
    }

    /**
     * Executes when the Pokémon in the box has been pressed.
     * @param contex    Where the ListView is to be displayed.
     * @param view      Specific list of Pokémon seen in the adapter.
     * @param app       Used to access the PokémonApp functions.
     * @param pos       The index at the ListView.
     */
    @Override
    public void executePokemonListView(Activity contex, View view, PokemonApp app, int pos){
        mManager.setSelectedProfile1(mManager.getPlayer().getBox().get(pos));
        mManager.setState(switchState());
        view.setBackground(app.getShape(PokemonApp.BACK_COLOR));
    }

    /**
     * Executes when the Pokémon in the party has been pressed.
     * @param contex    Where the ListView is to be displayed.
     * @param app       Used to access the PokémonApp functions.
     * @param pos       The index at the ListView.
     */
    @Override
    public void executePokemonButton(Activity contex, PokemonApp app, int pos){
        mManager.setSelectedProfile1(mManager.getPlayer().getPokemons().get(pos));
        mManager.setState(switchState());
        mPokémonButtons.get(pos).getButton().setBackground(app.getShape(PokemonApp.BACK_COLOR));
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
}
