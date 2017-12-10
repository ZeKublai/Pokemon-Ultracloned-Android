package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/27/2017.
 * This class is a subclass of the battle state which shows the main
 * menu and is usually called after all the Messages have been shown.
 */

public class BattleMainState extends BattleState {
    /**
     * Creates a BattleMainState given the parameters.
     * @param mFightButton      The fight Button of the BattleActivity.
     * @param mPokemonButton    The Pokémon Button of the BattleActivity.
     * @param mBagButton        The bag Button of the BattleActivity.
     * @param mRunButton        The run Button of the BattleActivity.
     * @param mActionButton     The action Button of the BattleActivity.
     * @param mOptionList       The ListView of options of the BattleActivity.
     * @param mBattle           The Battle object of the BattleActivity.
     * @param mMessage          The TextView that show the Messages of the Battle object.
     */
    protected BattleMainState(Button mFightButton,
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

        setButtons(true, View.VISIBLE);
        disableButton(mActionButton);
        hideOptions();
        resetSideButtons();
        mBattle.setEnemyDecision(mBattle.generateEnemyDecision());
        mMessage.setText("What will " + mBattle.getBuddy().getNickname() + " do?");
    }

    /**
     * Does nothing.
     */
    public BattleMainState(){

    }

    /**
     * Does nothing.
     * @param pos   The position index of the ListView of Moves, Pokémons or Items.
     */
    @Override
    public void executeListView(int pos){

    }

    /**
     * Does nothing.
     * @param app       Used for calling the Dialog data.
     * @param context   Needed to initialize the Dialog in the selected Activity.
     * @param pos       Position in the ListView.
     */
    @Override
    public void executeLongPressListView(PokemonApp app, Activity context, int pos){

    }

    /**
     * Check if the Player's current Pokémon still has PP and if so, the
     * Player's Decision would automatically be set to use the Move Struggle
     * else proceed to show the list of Moves the Player's current Pokémon has.
     */
    public void executeFightButton(){
        if(mBattle.getBuddy().noMorePP()){
            mBattle.setPlayerDecision(new DecisionAttack(mBattle.getBuddy(),
                    mBattle.getEnemy(), new MoveStruggle(mBattle), mBattle.getEnemyInfo()));
            mBattle.setBattleState(firstMoveState());
        }
        else{
            mBattle.setBattleState(fightState());
        }
    }

    /**
     * Opens the List of Pokémon in the Player's party.
     */
    @Override
    public void executePokemonButton(){
        mBattle.setBattleState(pokemonState());
    }

    /**
     * Opens the List of Items in the Player's bag.
     */
    @Override
    public void executeBagButton(){
        mBattle.setBattleState(bagState());
    }

    /**
     * Sets the Player's Decision to run and proceeds to check if it is possible.
     */
    @Override
    public void executeRunButton(){
        mBattle.setPlayerDecision(new DecisionRun(mBattle));
        mBattle.checkErrorMessage();
    }

    /**
     * Does nothing.
     */
    @Override
    public void executeActionButton(){

    }
}
