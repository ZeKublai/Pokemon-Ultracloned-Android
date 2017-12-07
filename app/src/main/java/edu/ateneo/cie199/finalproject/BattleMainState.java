package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/27/2017.
 * This class is a subclass of the battle state which handles the button function and what message to be displayed
 */

public class BattleMainState extends BattleState {

    public BattleMainState(Button mFightButton,
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
        initButtons();
        mBattle.setEnemyDecision(mBattle.generateEnemyDecision());
        mMessage.setText("What will " + mBattle.getBuddy().getNickname() + " do?");
    }

    /**
     * does nothing
     */
    public BattleMainState(){

    }

    /**
     * does nothing
     * @param pos position index relating to the list of moves, pokemons or items
     */
    @Override
    public void executeListView(int pos){

    }

    /**
     * does nothing
     * @param app used for calling the dialog data
     * @param ctx needed to initialize the dialog in the selected Activity
     * @param pos position in the listview
     */
    @Override
    public void executeLongPressListView(PokemonGoApp app, Activity ctx, int pos){

    }

    /**
     * check if the pokemon still has PP. if non, pokemon would struggle
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
     * does nothing
     */
    @Override
    public void executePokemonButton(){
        mBattle.setBattleState(pokemonState());
    }

    /**
     * does nothing
     */
    @Override
    public void executeBagButton(){
        mBattle.setBattleState(bagState());
    }

    /**
     * does nothing
     */
    @Override
    public void executeRunButton(){
        mBattle.setPlayerDecision(new DecisionRun(mBattle));
        mBattle.checkErrorMessage();
    }

    /**
     * does nothing
     */
    @Override
    public void executeActionButton(){

    }
}
