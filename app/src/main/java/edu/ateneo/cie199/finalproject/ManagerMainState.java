package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/29/2017.
 */

public class ManagerMainState extends ManagerState {

    public ManagerMainState(ArrayList<PokemonButton> mPokemonButtons, Button mBackButton, Button mSwitchButton, TextView mMessage, Manager mManager) {
        this.mPokemonButtons = mPokemonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        this.mMessage.setText(Message.MESSAGE_MANAGER_MAIN);
        initializeTeam();

        PokemonGoApp.setAsBackButton(mBackButton);
        PokemonGoApp.setAsSwitchButton(mSwitchButton);
        mManager.getItemAdapter().itemSelected(ItemList.NO_ITEM_SELECTED);
        mManager.getPokemonAdapter().notifyDataSetChanged();
        mManager.getItemAdapter().notifyDataSetChanged();
    }
    @Override
    public void executePokemonListView(Activity ctx, View view, PokemonGoApp app, int pos){
        showPokemonMenu(ctx, view, app, mManager.getPlayer().getBox().get(pos),
                mManager.getPlayer().getBox(), mManager.getPlayer().getPokemons(), "ADD TO PARTY",
                app.getPlayer().getPokemons().size() < Player.MAX_POKEMON_SLOTS);
    }
    @Override
    public void executePokemonButton(Activity ctx, PokemonGoApp app, int pos){
        showPokemonMenu(ctx, mPokemonButtons.get(pos).getButton(), app,
                mManager.getPlayer().getPokemons().get(pos), mManager.getPlayer().getPokemons(),
                mManager.getPlayer().getBox(), "SEND TO BOX", true);
    }

    public void showPokemonMenu(final Activity ctx, View view,
                                final PokemonGoApp app,
                                final PokemonProfile profile,
                                final ArrayList<PokemonProfile> origin,
                                final ArrayList<PokemonProfile> destination,
                                String transferLabel,
                                boolean canTransfer){
        PopupMenu popup = new PopupMenu(ctx, view);
        popup.getMenuInflater().inflate(R.menu.pokemon_profile_menu, popup.getMenu());

        popup.getMenu().getItem(1).setVisible(profile.canEvolve(app.getPokemon(profile.getDexData().getNextDex())));
        popup.getMenu().getItem(2).setVisible(mManager.getPlayer().getPokemons().size() + mManager.getPlayer().getBox().size() > 1);
        popup.getMenu().getItem(3).setTitle(transferLabel);
        popup.getMenu().getItem(3).setVisible(canTransfer);

        for(int index = 0; index < popup.getMenu().size(); index++){
            PokemonGoApp.applyFontToMenuItem(ctx, popup.getMenu().getItem(index));
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_summary :{
                        showPokemonProfileDialog(app, ctx, profile);
                        break;
                    }
                    case R.id.action_evolve :{
                        profile.evolve(app.getPokemon(profile.getDexData().getNextDex()));
                        mMessage.setText(profile.getNickname() + " has evolved into a " + profile.getDexData().getName() + "!");
                        break;
                    }
                    case R.id.action_release :{
                        mMessage.setText("Bye bye " + profile.getNickname() + "!");
                        origin.remove(profile);
                        break;
                    }
                    case R.id.action_send_to_box :{
                        mManager.getPlayer().transferPokemon(profile, origin, destination);
                        break;
                    }
                }
                initializeTeam();
                mManager.getPokemonAdapter().notifyDataSetChanged();
                return true;
            }
        });
        popup.show();
    }

    public void showPokemonProfileDialog(final PokemonGoApp app, Activity ctx, final PokemonProfile profile){
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
                    updatePokemons();
                    dialog.dismiss();
                }
            }
        });
        app.setAsOkButton(btnDialogOk);
    }

}
