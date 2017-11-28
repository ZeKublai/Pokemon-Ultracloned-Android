package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John on 11/27/2017.
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

    public BattleMainState(){

    }

    @Override
    public void executeListView(int pos){

    }

    @Override
    public void executeLongPressListView(PokemonGoApp app, Activity ctx, int pos){

    }

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

    @Override
    public void executePokemonButton(){
        mBattle.setBattleState(pokemonState());
    }

    @Override
    public void executeBagButton(){
        mBattle.setBattleState(bagState());
    }

    @Override
    public void executeRunButton(){
        mBattle.setPlayerDecision(new DecisionRun());
        mBattle.setBattleState(firstMoveState());
    }

    @Override
    public void executeActionButton(){

    }
}
