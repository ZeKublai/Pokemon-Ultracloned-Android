package edu.ateneo.cie199.finalproject;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John on 11/27/2017.
 */

public class BattleFightState extends BattleMainState {
    public BattleFightState(Button mFightButton,
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
        mOptionList.setAdapter(mBattle.getMoveAdapter());
        showOptions();
        mMessage.setText("What will " + mBattle.getBuddy().getNickname() + " do?");

        enableButton(mRunButton);
        disableButton(mActionButton);
        PokemonGoApp.setAsCancelButton(mFightButton);
    }

    @Override
    public void executeListView(int pos){
        mBattle.setSelectedMove(mBattle.getBuddy().getMoves().get(pos));
        mBattle.setPlayerDecision(new DecisionAttack(mBattle.getBuddy(), mBattle.getEnemy(), mBattle.getSelectedMove(), mBattle.getEnemyInfo()));
        mBattle.checkErrorMessage();
    }

    @Override
    public void executeFightButton(){
        mBattle.setBattleState(mainState());
    }
}
