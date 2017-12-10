package edu.ateneo.cie199.finalproject;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/27/2017.
 * This class is a subclass of the battle state where the
 * Player will select the move the current Pokémon will perform.
 */

public class BattleFightState extends BattleMainState {
    /**
     * Creates a BattleFightState given the parameters.
     * @param mFightButton      The fight Button of the BattleActivity.
     * @param mPokemonButton    The Pokémon Button of the BattleActivity.
     * @param mBagButton        The bag Button of the BattleActivity.
     * @param mRunButton        The run Button of the BattleActivity.
     * @param mActionButton     The action Button of the BattleActivity.
     * @param mOptionList       The ListView of options of the BattleActivity.
     * @param mBattle           The Battle object of the BattleActivity.
     * @param mMessage          The TextView that show the Messages of the Battle object.
     */
    protected BattleFightState(Button mFightButton,
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
        mOptionList.setAdapter(mBattle.getMoveAdapter());
        showOptions();
        mMessage.setText("What will " + mBattle.getBuddy().getNickname() + " do?");

        enableButton(mRunButton);
        disableButton(mActionButton);
        PokemonApp.setAsCancelButton(mFightButton);
    }

    /**
     * The Move at the given index is selected and the Player's Decision
     * is then set. Afterwards, errors for the Move is then checked.
     * @param pos   The current index of the ListView.
     */
    @Override
    public void executeListView(int pos){
        mBattle.setSelectedMove(mBattle.getBuddy().getMoves().get(pos));
        mBattle.setPlayerDecision(new DecisionAttack(
                mBattle.getBuddy(),
                mBattle.getEnemy(),
                mBattle.getSelectedMove(),
                mBattle.getEnemyInfo()
        ));
        mBattle.checkErrorMessage();
    }

    /**
     * Returns to the main menu.
     */
    @Override
    public void executeFightButton(){
        mBattle.setBattleState(mainState());
    }
}
