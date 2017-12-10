package edu.ateneo.cie199.finalproject;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/27/2017.
 * This class is a subclass of the battle state where the Player has selected an
 * Item and the Player would have to choose a Pokemon in the party to use an Item.
 */

public class BattleUseItemState extends BattleMainState {
    /**
     * Creates a BattleUseItemState given the parameters.
     * @param mFightButton      The fight Button of the BattleActivity.
     * @param mPokemonButton    The Pokémon Button of the BattleActivity.
     * @param mBagButton        The bag Button of the BattleActivity.
     * @param mRunButton        The run Button of the BattleActivity.
     * @param mActionButton     The action Button of the BattleActivity.
     * @param mOptionList       The ListView of options of the BattleActivity.
     * @param mBattle           The Battle object of the BattleActivity.
     * @param mMessage          The TextView that show the Messages of the Battle object.
     */
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

        resetSideButtons();
        mOptionList.setAdapter(mBattle.getPokemonAdapter());
        showOptions();

        mMessage.setText("Use " + mBattle.getSelectedItem().getName() + " on which PokéDexData?");
        enableButton(mRunButton);
        disableButton(mActionButton);
        PokemonApp.setAsCancelButton(mBagButton);
    }

    /**
     * The Pokémon will selected and the Player's Decision
     * would then be set to use the Item on the selected Pokémon.
     * @param pos   The current index of the ListView.
     */
    @Override
    public void executeListView(int pos){
        mBattle.setSelectedPokemon(mBattle.getPlayer().getPokemons().get(pos));
        mBattle.setPlayerDecision(new DecisionUse(mBattle.getSelectedItem()));
        mBattle.checkErrorMessage();
    }

    /**
     * Goes back to the main menu.
     */
    @Override
    public void executeBagButton(){
        mBattle.setBattleState(mainState());
    }
}
