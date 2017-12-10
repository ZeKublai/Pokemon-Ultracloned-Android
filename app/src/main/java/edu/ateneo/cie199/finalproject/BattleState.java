package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/7/2017.
 * Shows the state of the battle. Dictate button function and what message to be displayed
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
     * Shows the list of data depending on what is selected
     * @param pos position index relating to the list of moves, pokemons or items
     */
    public abstract void executeListView(int pos);

    /**
     * Shows the PokéDexData detail when long pressed
     * @param app used for calling the dialog data
     * @param ctx needed to initialize the dialog in the selected Activity
     * @param pos position in the listview
     */
    public abstract void executeLongPressListView(PokemonGoApp app, Activity ctx, int pos);

    /**
     * Event triggered at that state for the said button
     */
    public abstract void executeFightButton();

    /**
     * Event triggered at that state for the said button
     */
    public abstract void executePokemonButton();

    /**
     * Event triggered at that state for the said button
     */
    public abstract void executeBagButton();

    /**
     * Event triggered at that state for the said button
     */
    public abstract void executeRunButton();

    /**
     * Event triggered at that state for the said button
     */
    public abstract void executeActionButton();

    /**
     * Sets the button to either be clickable, visible or not
     */
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

    /**
     * Set a button to be disabled
     * @param btn selected button
     */
    public void disableButton(Button btn){
        btn.setClickable(false);
        btn.setVisibility(View.INVISIBLE);
    }

    /**
     * Set a button to be enabled
     * @param btn selected button
     */
    public void enableButton(Button btn){
        btn.setVisibility(View.VISIBLE);
        btn.setClickable(true);
    }

    /**
     * shows the listview
     */
    public void showOptions(){
        mOptionList.setClickable(true);
        mOptionList.setVisibility(View.VISIBLE);
    }

    /**
     * hides the listview
     */
    public void hideOptions(){
        mOptionList.setClickable(false);
        mOptionList.setVisibility(View.INVISIBLE);
    }

    /**
     * initializes the buttons
     */
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

    /**
     * Performs the first move.
     * Computes all the computation.
     * outputs a list of messages to be displayed
     * @return all the information
     */
    public BattleFirstMoveState firstMoveState(){
        return new BattleFirstMoveState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    /**
     * Performs the second move.
     * Computes all the computation.
     * outputs a list of messages to be displayed
     * @return all the information
     */
    public BattleSecondMoveState secondMoveState(){
        return new BattleSecondMoveState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    /**
     * Displays the main menu for the start of the firt and new turn
     * choose among fight, bag, switch or run
     * @return all the information
     */
    public BattleMainState mainState(){
        return new BattleMainState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    /**
     * Displays the moves that can be chosen
     * @return all the information
     */
    public BattleFightState fightState(){
        return new BattleFightState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    /**
     * State that allows the user to switch pokemon
     * Activated when buddy died or when pokemon is switched out
     * @return all the information
     */
    public BattlePokemonState pokemonState(){
        return new BattlePokemonState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    /**
     * Displays the items in the bag that can be chosen
     * @return all the information
     */
    public BattleBagState bagState(){
        return new BattleBagState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    /**
     * Trigger when a certain PokéDexData is targeted
     * @return all the information
     */
    public BattleUseItemState useItemState(){
        return new BattleUseItemState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }

    /**
     * Stops displaying the messages when the conditions are reached.
     * @return all the information
     */
    public BattleStandbyState standbyState(){
        return new BattleStandbyState(this.mFightButton, this.mPokemonButton,
                this.mBagButton, this.mRunButton, this.mActionButton, this.mOptionList,
                this.mBattle, this.mMessage);
    }
}
