package edu.ateneo.cie199.finalproject;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/27/2017.
 * This class is a subclass of the battle state where the Player selects an Item which
 * will be used on either on the Pokémon on the Player's party or on the enemy Pokémon.
 */

public class BattleBagState extends BattleMainState {
    /**
     * Creates a BattleBagState given the parameters.
     * @param mFightButton      The fight Button of the BattleActivity.
     * @param mPokemonButton    The Pokémon Button of the BattleActivity.
     * @param mBagButton        The bag Button of the BattleActivity.
     * @param mRunButton        The run Button of the BattleActivity.
     * @param mActionButton     The action Button of the BattleActivity.
     * @param mOptionList       The ListView of options of the BattleActivity.
     * @param mBattle           The Battle object of the BattleActivity.
     * @param mMessage          The TextView that show the Messages of the Battle object.
     */
    protected BattleBagState(Button mFightButton,
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
        mOptionList.setAdapter(mBattle.getItemAdapter());
        showOptions();

        mMessage.setText("Which item will you use?");
        enableButton(mRunButton);
        disableButton(mActionButton);
        PokemonApp.setAsCancelButton(mBagButton);
    }

    /**
     * The Item from the ListView is selected. If the Item targets an enemy,
     * the Item is then be used immediately else the Pokémon menu will show
     * so the Player can select which Pokémon will the Item will be used on.
     * @param pos   The current index in the ListView.
     */
    @Override
    public void executeListView(int pos){
        mBattle.setSelectedItem(mBattle.getPlayer().getBag().get(pos));
        if(mBattle.getSelectedItem() instanceof ItemTargetEnemy){
            mBattle.setPlayerDecision(new DecisionUse(mBattle.getSelectedItem()));
            mBattle.checkErrorMessage();
        }
        else{
            mBattle.setBattleState(useItemState());
        }
    }

    /**
     * Returns to the main menu.
     */
    @Override
    public void executeBagButton(){
        mBattle.setBattleState(mainState());
    }
}
