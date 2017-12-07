package edu.ateneo.cie199.finalproject;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/27/2017.
 * This class is a subclass of the battle state which handles the button function and what message to be displayed
 */

public class BattleBagState extends BattleMainState {
    public BattleBagState(Button mFightButton,
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
        mOptionList.setAdapter(mBattle.getItemAdapter());
        showOptions();

        mMessage.setText("Which item will you use?");
        enableButton(mRunButton);
        disableButton(mActionButton);
        PokemonGoApp.setAsCancelButton(mBagButton);
    }

    /**
     * Shows the list of items
     * @param pos current index of the listview
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
     * returns to main state
     */
    @Override
    public void executeBagButton(){
        mBattle.setBattleState(mainState());
    }
}
