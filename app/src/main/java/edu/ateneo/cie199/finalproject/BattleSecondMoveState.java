package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/28/2017.
 * This class is a subclass of the battle state where the second move is
 * executed and the Player would then be scrolling through resulting Messages.
 */

public class BattleSecondMoveState extends BattleState {
    /**
     * Creates a BattleSecondMoveState given the parameters.
     * @param mFightButton      The fight Button of the BattleActivity.
     * @param mPokemonButton    The Pokémon Button of the BattleActivity.
     * @param mBagButton        The bag Button of the BattleActivity.
     * @param mRunButton        The run Button of the BattleActivity.
     * @param mActionButton     The action Button of the BattleActivity.
     * @param mOptionList       The ListView of options of the BattleActivity.
     * @param mBattle           The Battle object of the BattleActivity.
     * @param mMessage          The TextView that show the Messages of the Battle object.
     */
    protected BattleSecondMoveState(Button mFightButton,
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
        mBattle.setMessageIndex(0);
        mBattle.getMessages().clear();
        mBattle.secondMove();
        mBattle.checkVictory();
        viewMessages();
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
     * Does nothing.
     */
    @Override
    public void executeFightButton(){

    }

    /**
     * Does nothing.
     */
    @Override
    public void executePokemonButton(){

    }

    /**
     * Does nothing.
     */
    @Override
    public void executeBagButton(){

    }

    /**
     * Does nothing.
     */
    @Override
    public void executeRunButton(){

    }

    /**
     * Shows the next Message.
     */
    @Override
    public void executeActionButton(){
        viewMessages();
    }

    /**
     * Increments the Messages to be viewed and proceeds to the next BattleState accordingly.
     */
    protected void viewMessages(){
        if(mBattle.getMessageIndex() < mBattle.getMessages().size()){
            mBattle.getMessages().get(mBattle.getMessageIndex()).executeUpdate(mBattle);
            mMessage.setText(mBattle.getMessages().get(mBattle.getMessageIndex()).getContent());
            mBattle.setMessageIndex(mBattle.getMessageIndex() + 1);
        }
        else{
            if(mBattle.isFinished()){

            }
            else if(mBattle.isEnemyFainted() || mBattle.isBuddyFainted()){
                if(mBattle.isEnemyFainted()
                        && mBattle.getPlayer().getPokemons().size() == 1
                        && mBattle instanceof TrainerBattle){
                    TrainerBattle battle = (TrainerBattle) mBattle;
                    battle.setEnemy(battle.getTrainer().getBuddy());
                    battle.addMessage(new MessageUpdatePokemon(
                            battle.getTrainer().getName()
                            + " has sent out "
                            + battle.getTrainer().getBuddy().getNickname()
                            + "!",
                            battle.getEnemyInfo(),
                            battle.getEnemy()
                    ));
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
