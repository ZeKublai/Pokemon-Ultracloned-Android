package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John, Duke and JV on 11/27/2017.
 * This class is a subclass of the battle state where the Player will select the
 * Pokémon that will be switched out. If the Player's current Pokémon has fainted
 * the state could not be exited unless a Pokémon has been selected to be switched out.
 */

public class BattlePokemonState extends BattleMainState{
    /**
     * Creates a BattlePokemonState given the parameters.
     * @param mFightButton      The fight Button of the BattleActivity.
     * @param mPokemonButton    The Pokémon Button of the BattleActivity.
     * @param mBagButton        The bag Button of the BattleActivity.
     * @param mRunButton        The run Button of the BattleActivity.
     * @param mActionButton     The action Button of the BattleActivity.
     * @param mOptionList       The ListView of options of the BattleActivity.
     * @param mBattle           The Battle object of the BattleActivity.
     * @param mMessage          The TextView that show the Messages of the Battle object.
     */
    protected BattlePokemonState(Button mFightButton,
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

        resetSideButtons();
        mOptionList.setAdapter(mBattle.getPokemonAdapter());
        showOptions();
        if(mBattle instanceof TrainerBattle && mBattle.isEnemyFainted()){
            TrainerBattle battle = (TrainerBattle) mBattle;
            mMessage.setText(
                    battle.getTrainer().getName()
                    + " is about to send out "
                    + battle.getTrainer().getBuddy().getNickname()
                    + "! Switch next Pokémon?"
            );
        }
        else {
            mMessage.setText("Which PokéDexData to switch?");
        }

        enableButton(mRunButton);
        disableButton(mActionButton);
        if(!mBattle.isBuddyFainted()) {
            PokemonApp.setAsCancelButton(mPokemonButton);
        }
    }

    /**
     * The Pokémon selected would be switched out if possible.
     * @param pos   The current index of the ListView.
     */
    @Override
    public void executeListView(int pos){
        mBattle.setPlayerDecision(new DecisionSwitch(
                mBattle.getPlayer().getPokemons().get(pos),
                mBattle.getBuddy(),
                mBattle.getBuddyInfo()
        ));
        if(mBattle.isEnemyFainted() && mBattle instanceof TrainerBattle){
            mBattle.doPlayerDecision();
            if(mBattle.getPlayerDecision().isError()){
                mBattle.addMessage(mBattle.getPlayerDecision().getErrorMessage());
            }
            else{
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
            }
            mBattle.setBattleState(standbyState());
        }
        else{
            mBattle.checkErrorMessage();
        }
    }

    /**
     * Shows a Dialog that contains the data of the Pokémon.
     * @param app       Used for calling the Dialog data.
     * @param context   Needed to initialize the Dialog in the selected Activity.
     * @param pos       Position in the ListView.
     */
    @Override
    public void executeLongPressListView(PokemonApp app, Activity context, int pos){
        getPokemonDialog(app, context, mBattle.getPlayer().getPokemons().get(pos));
    }

    /**
     * Goes back to the main menu.
     */
    @Override
    public void executePokemonButton(){
        if(mBattle.isEnemyFainted() && mBattle instanceof TrainerBattle){
            TrainerBattle battle = (TrainerBattle) mBattle;
            battle.addMessage(new Message(
                    battle.getPlayer().getName()
                    + " did not switch Pokémon..."
            ));
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
        else{
            mBattle.setBattleState(mainState());
        }
    }

    /**
     * get the data of the pokemon
     * @param app       Used for calling the Dialog data.
     * @param context   Needed to initialize the Dialog in the selected Activity.
     * @param profile   The Pokémon that would be displayed in the Dialog.
     */
    public void getPokemonDialog(final PokemonApp app,
                                 Activity context,
                                 final PokémonProfile profile){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.pokemon_profile_dialog);
        final EditText edtNickname = (EditText) dialog.findViewById(R.id.edt_profile_nickname);
        edtNickname.setText(profile.getNickname());
        app.loadPokemonDetails(dialog, context, profile);
        Button btnDialogOk = (Button) dialog.findViewById(R.id.btn_profile_back);
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.getMusicHandler().playButtonSfx(app.getSFXSwitch());

                if(edtNickname.getText().toString().length() > 15){
                    edtNickname.setError("Nickname is too long!");
                }
                else{
                    if(edtNickname.getText().toString().isEmpty()){
                        profile.setNickname(profile.getDexData().getName());
                    }
                    else{
                        profile.setNickname(edtNickname.getText().toString());
                    }
                    mBattle.getBuddyInfo().updatePokemon(mBattle.getBuddy());
                    mMessage.setText("What will " + mBattle.getBuddy().getNickname() + " do?");
                    dialog.dismiss();
                }
            }
        });
        app.setAsOkButton(btnDialogOk);
    }
}
