package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/7/2017.
 * The state of the battle where it dictates Button functions and what Messages to be displayed.
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

    /**
     * Selects the object at the given index when the ListView is pressed.
     * @param pos   The current index of the ListView.
     */
    public abstract void executeListView(int pos);

    /**
     * Executes the when the ListView is long-pressed.
     * @param app       Used for calling the Dialog data.
     * @param context   Needed to initialize the Dialog in the selected Activity.
     * @param pos       Position in the ListView.
     */
    public abstract void executeLongPressListView(PokemonApp app, Activity context, int pos);

    /**
     * Executes the when the fight Button is pressed.
     */
    public abstract void executeFightButton();

    /**
     * Executes the when the Pokémon Button is pressed.
     */
    public abstract void executePokemonButton();

    /**
     * Executes the when the bag Button is pressed.
     */
    public abstract void executeBagButton();

    /**
     * Executes the when the run Button is pressed.
     */
    public abstract void executeRunButton();

    /**
     * Executes the when the action Button is pressed.
     */
    public abstract void executeActionButton();

    /**
     * Sets the button visibility and if it can be clicked or not.
     */
    protected void setButtons(boolean clickable, int visibility){
        mFightButton.setClickable(clickable);
        mPokemonButton.setClickable(clickable);
        mBagButton.setClickable(clickable);
        mRunButton.setClickable(clickable);

        mFightButton.setVisibility(visibility);
        mPokemonButton.setVisibility(visibility);
        mBagButton.setVisibility(visibility);
        mRunButton.setVisibility(visibility);
    }

    /**
     * Sets a button to be disabled.
     * @param button   The selected button.
     */
    protected void disableButton(Button button){
        button.setClickable(false);
        button.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets a button to be enabled.
     * @param button    The selected button
     */
    protected void enableButton(Button button){
        button.setVisibility(View.VISIBLE);
        button.setClickable(true);
    }

    /**
     * Sets the ListView to be clickable and visible.
     */
    protected void showOptions(){
        mOptionList.setClickable(true);
        mOptionList.setVisibility(View.VISIBLE);
    }

    /**
     * Sets the ListView to be not clickable and invisible.
     */
    protected void hideOptions(){
        mOptionList.setClickable(false);
        mOptionList.setVisibility(View.INVISIBLE);
    }

    /**
     * Resets the buttons to their respective texts and color.
     */
    protected void resetSideButtons(){
        mFightButton.setText("FIGHT");
        mPokemonButton.setText("POKEMON");
        mBagButton.setText("BAG");
        mRunButton.setText("RUN");
        mFightButton.setBackgroundColor(PokemonApp.FIGHT_COLOR);
        mPokemonButton.setBackgroundColor(PokemonApp.POKEMON_COLOR);
        mBagButton.setBackgroundColor(PokemonApp.BAG_COLOR);
        mRunButton.setBackgroundColor(PokemonApp.RUN_COLOR);
    }

    /**
     * Returns a BattleFirstMoveState with the same values as this BattleState.
     * @return  A BattleFirstMoveState with the same values as this BattleState.
     */
    protected BattleFirstMoveState firstMoveState(){
        return new BattleFirstMoveState(
                this.mFightButton,
                this.mPokemonButton,
                this.mBagButton,
                this.mRunButton,
                this.mActionButton,
                this.mOptionList,
                this.mBattle,
                this.mMessage
        );
    }

    /**
     * Returns a BattleSecondMoveState with the same values as this BattleState.
     * @return  A BattleSecondMoveState with the same values as this BattleState.
     */
    protected BattleSecondMoveState secondMoveState(){
        return new BattleSecondMoveState(
                this.mFightButton,
                this.mPokemonButton,
                this.mBagButton,
                this.mRunButton,
                this.mActionButton,
                this.mOptionList,
                this.mBattle,
                this.mMessage
        );
    }

    /**
     * Returns a BattleMainState with the same values as this BattleState.
     * @return  A BattleMainState with the same values as this BattleState.
     */
    protected BattleMainState mainState(){
        return new BattleMainState(
                this.mFightButton,
                this.mPokemonButton,
                this.mBagButton,
                this.mRunButton,
                this.mActionButton,
                this.mOptionList,
                this.mBattle,
                this.mMessage
        );
    }

    /**
     * Returns a BattleFightState with the same values as this BattleState.
     * @return  A BattleFightState with the same values as this BattleState.
     */
    protected BattleFightState fightState(){
        return new BattleFightState(
                this.mFightButton,
                this.mPokemonButton,
                this.mBagButton,
                this.mRunButton,
                this.mActionButton,
                this.mOptionList,
                this.mBattle,
                this.mMessage
        );
    }

    /**
     * Returns a BattlePokemonState with the same values as this BattleState.
     * @return  A BattlePokemonState with the same values as this BattleState.
     */
    protected BattlePokemonState pokemonState(){
        return new BattlePokemonState(
                this.mFightButton,
                this.mPokemonButton,
                this.mBagButton,
                this.mRunButton,
                this.mActionButton,
                this.mOptionList,
                this.mBattle,
                this.mMessage
        );
    }

    /**
     * Returns a BattleBagState with the same values as this BattleState.
     * @return  A BattleBagState with the same values as this BattleState.
     */
    protected BattleBagState bagState(){
        return new BattleBagState(
                this.mFightButton,
                this.mPokemonButton,
                this.mBagButton,
                this.mRunButton,
                this.mActionButton,
                this.mOptionList,
                this.mBattle,
                this.mMessage
        );
    }

    /**
     * Returns a BattleUseItemState with the same values as this BattleState.
     * @return  A BattleUseItemState with the same values as this BattleState.
     */
    protected BattleUseItemState useItemState(){
        return new BattleUseItemState(
                this.mFightButton,
                this.mPokemonButton,
                this.mBagButton,
                this.mRunButton,
                this.mActionButton,
                this.mOptionList,
                this.mBattle,
                this.mMessage
        );
    }

    /**
     * Returns a BattleStandbyState with the same values as this BattleState.
     * @return  A BattleStandbyState with the same values as this BattleState.
     */
    public BattleStandbyState standbyState(){
        return new BattleStandbyState(
                this.mFightButton,
                this.mPokemonButton,
                this.mBagButton,
                this.mRunButton,
                this.mActionButton,
                this.mOptionList,
                this.mBattle,
                this.mMessage
        );
    }
}
