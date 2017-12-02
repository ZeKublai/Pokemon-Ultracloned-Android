package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by John on 11/27/2017.
 */

public class BattlePokemonState extends BattleMainState{
    public BattlePokemonState(Button mFightButton,
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
        mOptionList.setAdapter(mBattle.getPokemonAdapter());
        showOptions();
        if(mBattle instanceof TrainerBattle && mBattle.isEnemyFainted()){
            TrainerBattle battle = (TrainerBattle) mBattle;
            mMessage.setText(battle.getTrainer().getName() + " is about to send out " + battle.getTrainer().getBuddy().getNickname() + "! Switch next Pokemon?");
        }
        else {
            mMessage.setText("Which Pokemon to switch?");
        }

        enableButton(mRunButton);
        disableButton(mActionButton);
        if(!mBattle.isBuddyFainted()) {
            PokemonGoApp.setAsCancelButton(mPokemonButton);
        }
    }

    @Override
    public void executeListView(int pos){
        mBattle.setPlayerDecision(new DecisionSwitch(mBattle.getPlayer().getPokemons().get(pos), mBattle.getBuddy(), mBattle.getBuddyInfo()));
        if(mBattle.isEnemyFainted() && mBattle instanceof TrainerBattle){
            mBattle.doPlayerDecision();
            if(mBattle.getPlayerDecision().isError()){
                mBattle.addMessage(mBattle.getPlayerDecision().getErrorMessage());
            }
            else{
                TrainerBattle battle = (TrainerBattle) mBattle;
                battle.setEnemy(battle.getTrainer().getBuddy());
                battle.addMessage(new MessageUpdatePokemon(battle.getTrainer().getName() + " has sent out " + battle.getTrainer().getBuddy().getNickname() + "!", battle.getEnemyInfo(), battle.getEnemy()));
            }
            mBattle.setBattleState(standbyState());
        }
        else{
            mBattle.checkErrorMessage();
        }
    }

    @Override
    public void executeLongPressListView(PokemonGoApp app, Activity ctx, int pos){
        getPokemonDialog(app, ctx, mBattle.getPlayer().getPokemons().get(pos));
    }


    @Override
    public void executePokemonButton(){
        if(mBattle.isEnemyFainted() && mBattle instanceof TrainerBattle){
            TrainerBattle battle = (TrainerBattle) mBattle;
            battle.addMessage(new Message(battle.getPlayer().getName() + " did not switch Pokemon..."));
            battle.setEnemy(battle.getTrainer().getBuddy());
            battle.addMessage(new MessageUpdatePokemon(battle.getTrainer().getName() + " has sent out " + battle.getTrainer().getBuddy().getNickname() + "!", battle.getEnemyInfo(), battle.getEnemy()));
            battle.setBattleState(standbyState());
        }
        else{
            mBattle.setBattleState(mainState());
        }
    }

    public void getPokemonDialog(final PokemonGoApp app, Activity ctx, final PokemonProfile profile){
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.pokemon_profile_dialog);
        final EditText edtNickname = (EditText) dialog.findViewById(R.id.edt_profile_nickname);
        edtNickname.setText(profile.getNickname());
        app.loadPokemonDetails(dialog, ctx, profile);
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
