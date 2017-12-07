package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/28/2017.
 * This class is a subclass of the battle state which handles the button function and what message to be displayed
 */

public class BattleSecondMoveState extends BattleState {
    public BattleSecondMoveState(Button mFightButton,
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
        mBattle.setIndex(0);
        mBattle.getMessages().clear();
        mBattle.secondMove();
        mBattle.checkVictory();
        viewMessages();
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
     * does nothing
     */
    public void executeFightButton(){

    }

    /**
     * does nothing
     */
    @Override
    public void executePokemonButton(){

    }

    /**
     * does nothing
     */
    @Override
    public void executeBagButton(){

    }

    /**
     * does nothing
     */
    @Override
    public void executeRunButton(){

    }

    /**
     * does nothing
     */
    @Override
    public void executeActionButton(){
        viewMessages();
    }

    /**
     * shows the messages to be displayed in succession
     */
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
                if(mBattle.isEnemyFainted() && mBattle.getPlayer().getPokemons().size() == 1 && mBattle instanceof TrainerBattle){
                    TrainerBattle battle = (TrainerBattle) mBattle;
                    battle.setEnemy(battle.getTrainer().getBuddy());
                    battle.addMessage(new MessageUpdatePokemon(battle.getTrainer().getName() + " has sent out " + battle.getTrainer().getBuddy().getNickname() + "!", battle.getEnemyInfo(), battle.getEnemy()));
                    battle.setBattleState(standbyState());
                }
                else {
                    mBattle.setBattleState(pokemonState());
                }
            }
            else{
                mBattle.newTurn();
                mBattle.setBattleState(mBattle.getBattleState().mainState());
            }

        }
    }

}
