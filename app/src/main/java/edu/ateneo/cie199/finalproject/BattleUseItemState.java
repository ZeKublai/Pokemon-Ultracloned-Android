package edu.ateneo.cie199.finalproject;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/27/2017.
 * This class is a subclass of the battle state which handles the button function and what message to be displayed
 */

public class BattleUseItemState extends BattleMainState {
    public BattleUseItemState(Button mFightButton,
                              Button mPokemonButton,
                              Button mBagButton,
                              Button mRunButton,
                              Button mActionButton,
                              ListView mOptionList,
                              Battle mBattle,
                              TextView mMessage) {
        this.mFightButton = mFightButton;
        this.mPokemonButton = mPokemonButton;
        this.mBagButton = mBagButton;
        this.mRunButton = mRunButton;
        this.mActionButton = mActionButton;
        this.mOptionList = mOptionList;
        this.mBattle = mBattle;
        this.mMessage = mMessage;

        initButtons();
        mOptionList.setAdapter(mBattle.getPokemonAdapter());
        showOptions();

        mMessage.setText("Use " + mBattle.getSelectedItem().getName() + " on which Pokemon?");
        enableButton(mRunButton);
        disableButton(mActionButton);
        PokemonGoApp.setAsCancelButton(mBagButton);
    }

    /**
     * get the list of item to be used on a pokemon
     * @param pos position index relating to the list of moves, pokemons or items
     */
    @Override
    public void executeListView(int pos){
        mBattle.setSelectedPokemon(mBattle.getPlayer().getPokemons().get(pos));
        mBattle.setPlayerDecision(new DecisionUse(mBattle.getSelectedItem()));
        mBattle.checkErrorMessage();
    }

    /**
     * returns to the main state
     */
    @Override
    public void executeBagButton(){
        mBattle.setBattleState(mainState());
    }
}
