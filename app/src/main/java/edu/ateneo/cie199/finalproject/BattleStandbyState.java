package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John on 11/28/2017.
 */

public class BattleStandbyState extends BattleState {
    public BattleStandbyState(Button mFightButton,
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

        setButtons(false, View.INVISIBLE);
        enableButton(mActionButton);
        hideOptions();
        if(!(mBattle.isEnemyFainted() || mBattle.isBuddyFainted()) || mBattle.getPlayerDecision().isError()){
            viewMessages();
        }
    }

    @Override
    public void executeListView(int pos){

    }

    @Override
    public void executeLongPressListView(PokemonGoApp app, Activity ctx, int pos){

    }

    public void executeFightButton(){

    }

    @Override
    public void executePokemonButton(){

    }

    @Override
    public void executeBagButton(){

    }

    @Override
    public void executeRunButton(){

    }

    @Override
    public void executeActionButton(){
        viewMessages();
    }

    protected void viewMessages(){
        if(mBattle.getIndex() < mBattle.getMessages().size()){
            mBattle.getMessages().get(mBattle.getIndex()).executeUpdate(mBattle);
            mMessage.setText(mBattle.getMessages().get(mBattle.getIndex()).getMessage());
            mBattle.setIndex(mBattle.getIndex() + 1);
        }
        else{
            if(mBattle.isFinished()){

            }
            else if(mBattle.isEnemyFainted() || mBattle.isBuddyFainted()){
                mBattle.setBattleState(pokemonState());
            }
            else{
                mBattle.newTurn();
                mBattle.setBattleState(mainState());
            }
        }
    }
}
