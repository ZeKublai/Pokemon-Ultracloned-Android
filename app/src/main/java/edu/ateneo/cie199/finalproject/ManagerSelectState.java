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
     * determines the state of the Manager
     * @param mPokemonButtons trigger button for checking Pokemon dropdown option
     * @param mBackButton  trigger button for when selected state is canceled
     * @param mSwitchButton trigger for when switching pokemons
     * @param mMessage show messages
     * @param mManager accessing the Manager
     */
    public ManagerSelectState(ArrayList<PokemonButton> mPokemonButtons, Button mBackButton, Button mSwitchButton, TextView mMessage, Manager mManager) {
        this.mPokemonButtons = mPokemonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        mMessage.setText("Switch which Pokemon?");
    }

    /**
     * it selects the pokemon
     * @param ctx where the listview is to be displayed
     * @param view specific list of pokemon seen in the adapter
     * @param app access the PokemonGoApp functions
     * @param pos index of the listview
     */
    @Override
    public void executePokemonListView(Activity ctx, View view, PokemonGoApp app, int pos){
        mManager.setSelectedProfile1(mManager.getPlayer().getBox().get(pos));
        mManager.setState(switchState());
        view.setBackground(app.getShape(PokemonGoApp.BACK_COLOR));
    }

    /**
     * the selected pokemon can either be sent to box or added to party
     * @param ctx where the listview is to be displayed
     * @param app access the PokemonGoApp functions
     * @param pos index of the listview
     */
    @Override
    public void executePokemonButton(Activity ctx, PokemonGoApp app, int pos){
        mManager.setSelectedProfile1(mManager.getPlayer().getPokemons().get(pos));
        mManager.setState(switchState());
        mPokemonButtons.get(pos).getButton().setBackground(app.getShape(PokemonGoApp.BACK_COLOR));
    }

    /**
     * The two selected Pokemon would switch places
     */
    @Override
    public void executeSwitchButton(){
        noItemSelected();
        PokemonGoApp.setAsSwitchButton(mSwitchButton);
        mManager.getPokemonAdapter().notifyDataSetChanged();
        updatePokemons();
        mManager.setState(mainState());
    }
}
