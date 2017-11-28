package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John on 11/27/2017.
 */

public abstract class BattleState {
    protected Button mFightButton;
    protected Button mPokemonButton;
    protected Button mBagButton;
    protected Button mRunButton;
    protected Button mActionButton;
    protected ListView mOptionList;
    protected Battle mBattle;
    protected TextView mMessage;

    public abstract void executeListView(int pos);
    public abstract void executeLongPressListView(PokemonGoApp app, Activity ctx, int pos);
    public abstract void executeFightButton();
    public abstract void executePokemonButton();
    public abstract void executeBagButton();
    public abstract void executeRunButton();
    public abstract void executeActionButton();

    public void setButtons(boolean clickable, int visibility){
        mFightButton.setClickable(clickable);
        mPokemonButton.setClickable(clickable);
        mBagButton.setClickable(clickable);
        mRunButton.setClickable(clickable);

        mFightButton.setVisibility(visibility);
        mPokemonButton.setVisibility(visibility);
        mBagButton.setVisibility(visibility);
        mRunButton.setVisibility(visibility);
    }

    public void disableButton(Button btn){
        btn.setClickable(false);
        btn.setVisibility(View.INVISIBLE);
    }

    public void enableButton(Button btn){
        btn.setVisibility(View.VISIBLE);
        btn.setClickable(true);
    }

    public void showOptions(){
        mOptionList.setClickable(true);
        mOptionList.setVisibility(View.VISIBLE);
    }

    public void hideOptions(){
        mOptionList.setClickable(false);
        mOptionList.setVisibility(View.INVISIBLE);
    }

    public void initButtons(){
        mFightButton.setText("FIGHT");
        mPokemonButton.setText("POKEMON");
        mBagButton.setText("BAG");
        mRunButton.setText("RUN");
        mFightButton.setBackgroundColor(PokemonGoApp.FIGHT_COLOR);
        mPokemonButton.setBackgroundColor(PokemonGoApp.POKEMON_COLOR);
        mBagButton.setBackgroundColor(PokemonGoApp.BAG_COLOR);
        mRunButton.setBackgroundColor(PokemonGoApp.RUN_COLOR);
    }

    public BattleFirstMoveState firstMoveState(){
        return new BattleFirstMoveState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    public BattleSecondMoveState secondMoveState(){
        return new BattleSecondMoveState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    public BattleMainState mainState(){
        return new BattleMainState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    public BattleFightState fightState(){
        return new BattleFightState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    public BattlePokemonState pokemonState(){
        return new BattlePokemonState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    public BattleBagState bagState(){
        return new BattleBagState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    public BattleUseItemState useItemState(){
        return new BattleUseItemState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    public BattleStandbyState standbyState(){
        return new BattleStandbyState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }
}
