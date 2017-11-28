package edu.ateneo.cie199.finalproject;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John on 11/27/2017.
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

    @Override
    public void executeListView(int pos){
        mBattle.setSelectedPokemon(mBattle.getPlayer().getPokemons().get(pos));
        mBattle.setPlayerDecision(new DecisionUse(mBattle.getSelectedItem()));
        mBattle.checkErrorMessage();
    }

    @Override
    public void executeBagButton(){
        mBattle.setBattleState(mainState());
    }
}
