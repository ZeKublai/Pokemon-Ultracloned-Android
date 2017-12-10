package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/29/2017.
 * This class focuses on the UseItem State of the Manager that extends from ManagerState.
 */

public class ManagerUseItemState extends ManagerState {

    /**
     * Initializes the ManagerUseItemState.
     * @param mPokémonButtons   The ArrayList of PokémonButtons from the Player's party.
     * @param mBackButton       The back Button.
     * @param mSwitchButton     The switch Button.
     * @param mMessage          The TextView where the message is displayed.
     * @param mManager          The Manager where the state is stored.
     */
    public ManagerUseItemState(ArrayList<PokémonButton> mPokémonButtons,
                               Button mBackButton,
                               Button mSwitchButton,
                               TextView mMessage,
                               Manager mManager) {
        this.mPokémonButtons = mPokémonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        mMessage.setText("Use " + mManager.getSelectedItem().getName() + " on which PokéDexData?");
    }

    /**
     * Applies the Item's effect on the Pokémon found on the box storage.
     * @param contex    Where the ListView is to be displayed.
     * @param view      Specific list of Pokémon seen in the adapter.
     * @param app       Used to access the PokémonApp functions.
     * @param pos       The index at the ListView.
     */
    @Override
    public void executePokemonListView(Activity contex, View view, PokemonApp app, int pos){
        useSelectedItem(mManager.getPlayer().getBox().get(pos));
    }

    /**
     * Apply the Item's effect on the Pokémon found on the party.
     * @param contex    Where the ListView is to be displayed.
     * @param app       Used to access the PokémonApp functions.
     * @param pos       The index at the ListView.
     */
    @Override
    public void executePokemonButton(Activity contex, PokemonApp app, int pos){
        useSelectedItem(mManager.getPlayer().getPokemons().get(pos));
    }

    /**
     * Returns to the ManagerMainState.
     */
    @Override
    public void executeBackButton(){
        noItemSelected();
        mManager.setState(mainState());
    }

    /**
     * Apply the Item's effect on the given Pokémon.
     * @param profile   The given Pokémon.
     */
    public void useSelectedItem(PokémonProfile profile){
        if(mManager.getSelectedItem().getQuantity() > 0){
            mManager.getSelectedItem().useInManager(
                    profile,
                    mMessage,
                    mManager.getPlayer().getBag()
            );
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
        updatePokémonButtons();
    }
}
