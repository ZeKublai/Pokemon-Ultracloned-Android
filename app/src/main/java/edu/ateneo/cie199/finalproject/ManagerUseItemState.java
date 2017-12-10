package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/29/2017.
 * This class focuses on the UseItem State of the Manager and extends the ManagerState
 */

public class ManagerUseItemState extends ManagerState {

    /**
     * determines the state of the Manager
     * @param mPokémonButtons
     * @param mBackButton
     * @param mSwitchButton
     * @param mMessage
     * @param mManager
     */
    public ManagerUseItemState(ArrayList<PokémonButton> mPokémonButtons, Button mBackButton, Button mSwitchButton, TextView mMessage, Manager mManager) {
        this.mPokémonButtons = mPokémonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        mMessage.setText("Use " + mManager.getSelectedItem().getName() + " on which PokéDexData?");
    }

    /**
     * Apply the item effect on the pokemon found on the box storage
     * @param ctx where the listview is to be displayed
     * @param view specific list of pokemon seen in the adapter
     * @param app access the PokemonGoApp functions
     * @param pos index of the listview
     */
    @Override
    public void executePokemonListView(Activity ctx, View view, PokemonGoApp app, int pos){
        useSelectedItem(mManager.getPlayer().getBox().get(pos));
    }

    /**
     * Apply the item effect on the pokemon found on the party
     * @param ctx where the listview is to be displayed
     * @param app access the PokemonGoApp functions
     * @param pos index of the listview
     */
    @Override
    public void executePokemonButton(Activity ctx, PokemonGoApp app, int pos){
        useSelectedItem(mManager.getPlayer().getPokemons().get(pos));
    }

    /**
     * returns to the main state
     */
    @Override
    public void executeBackButton(){
        noItemSelected();
        mManager.setState(mainState());
    }

    /**
     * Apply the item effect on the given PokéDexData
     * @param profile data of the PokéDexData
     */
    public void useSelectedItem(PokémonProfile profile){
        if(mManager.getSelectedItem().getQuantity() > 0){
            mManager.getSelectedItem().useInManager(profile, mMessage, mManager.getPlayer().getBag());
            if(mManager.getSelectedItem().getQuantity() <= 0){
                mainState();
            }
        }
        else{
            mManager.setState(mainState());
            mManager.getItemAdapter().itemSelected(ItemList.NO_ITEM_SELECTED);
        }
        mManager.getItemAdapter().notifyDataSetChanged();
        mManager.getPokemonAdapter().notifyDataSetChanged();
        updatePokemons();
    }
}
