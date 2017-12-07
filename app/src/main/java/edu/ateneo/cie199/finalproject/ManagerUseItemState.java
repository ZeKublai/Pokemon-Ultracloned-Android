package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/29/2017.
 */

public class ManagerUseItemState extends ManagerState {
    public ManagerUseItemState(ArrayList<PokemonButton> mPokemonButtons, Button mBackButton, Button mSwitchButton, TextView mMessage, Manager mManager) {
        this.mPokemonButtons = mPokemonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        mMessage.setText("Use " + mManager.getSelectedItem().getName() + " on which Pokemon?");
    }

    @Override
    public void executePokemonListView(Activity ctx, View view, PokemonGoApp app, int pos){
        useSelectedItem(mManager.getPlayer().getBox().get(pos));
    }
    @Override
    public void executePokemonButton(Activity ctx, PokemonGoApp app, int pos){
        useSelectedItem(mManager.getPlayer().getPokemons().get(pos));
    }

    @Override
    public void executeBackButton(){
        noItemSelected();
        mManager.setState(mainState());
    }

    public void useSelectedItem(PokemonProfile profile){
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
