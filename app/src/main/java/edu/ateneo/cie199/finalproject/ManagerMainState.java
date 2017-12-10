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
 * Created by John, Duke and JV on 11/29/2017.
 * This class focuses on the Main State of the Manager and extends the ManagerState
 */

public class ManagerMainState extends ManagerState {
    /**
     * Initializes the ManagerMainState.
     * @param mPokémonButtons   The ArrayList of PokémonButtons from the Player's party.
     * @param mBackButton       The back Button.
     * @param mSwitchButton     The switch Button.
     * @param mMessage          The TextView where the message is displayed.
     * @param mManager          The Manager where the state is stored.
     */
    public ManagerMainState(ArrayList<PokémonButton> mPokémonButtons,
                            Button mBackButton,
                            Button mSwitchButton,
                            TextView mMessage,
                            Manager mManager) {
        this.mPokémonButtons = mPokémonButtons;
        this.mBackButton = mBackButton;
        this.mSwitchButton = mSwitchButton;
        this.mMessage = mMessage;
        this.mManager = mManager;

        this.mMessage.setText(Message.MESSAGE_MANAGER_MAIN);
        ResetPokémonButtons();

        PokemonApp.setAsBackButton(mBackButton);
        PokemonApp.setAsSwitchButton(mSwitchButton);
        mManager.getItemAdapter().itemSelected(ItemList.NO_ITEM_SELECTED);
        mManager.getPokemonAdapter().notifyDataSetChanged();
        mManager.getItemAdapter().notifyDataSetChanged();
    }

    /**
     * Executes when the Pokémon in the box has been pressed.
     * @param contex    Where the ListView is to be displayed.
     * @param view      Specific list of Pokémon seen in the adapter.
     * @param app       Used to access the PokémonApp functions.
     * @param pos       The index at the ListView.
     */
    @Override
    public void executePokemonListView(Activity contex, View view, PokemonApp app, int pos){
        showPokemonMenu(contex, view, app, mManager.getPlayer().getBox().get(pos),
                mManager.getPlayer().getBox(), mManager.getPlayer().getPokemons(), "ADD TO PARTY",
                app.getPlayer().getPokemons().size() < Player.MAX_POKéMON_SLOTS);
    }

    /**
     * Executes when the Pokémon in the party has been pressed.
     * @param contex    Where the ListView is to be displayed.
     * @param app       Used to access the PokémonApp functions.
     * @param pos       The index at the ListView.
     */
    @Override
    public void executePokemonButton(Activity contex, PokemonApp app, int pos){
        showPokemonMenu(contex, mPokémonButtons.get(pos).getButton(), app,
                mManager.getPlayer().getPokemons().get(pos), mManager.getPlayer().getPokemons(),
                mManager.getPlayer().getBox(), "SEND TO BOX", true);
    }

    /**
     * Shows the dropdown menu containing the options for the selected Pokémon.
     * @param contex        Where the ListView is to be displayed.
     * @param view          Specific list of Pokémon seen in the adapter.
     * @param app           Used to access the PokémonApp functions.
     * @param profile       The selected Pokémon.
     * @param origin        Where the Pokémon came from which is either from box or party.
     * @param destination   Where the Pokémon would go which is either to box or party.
     * @param transferLabel The option shown depending on the origin.
     * @param canTransfer   The boolean value that says if the Pokémon can be transfered.
     */
    public void showPokemonMenu(final Activity contex, View view,
                                final PokemonApp app,
                                final PokémonProfile profile,
                                final ArrayList<PokémonProfile> origin,
                                final ArrayList<PokémonProfile> destination,
                                String transferLabel,
                                boolean canTransfer){
        PopupMenu popup = new PopupMenu(contex, view);
        popup.getMenuInflater().inflate(R.menu.pokemon_profile_menu, popup.getMenu());

        popup.getMenu().getItem(1).setVisible(profile.canEvolve(app.getPokemon(
                profile.getDexData().getNextDex()
        )));
        popup.getMenu().getItem(2).setVisible(
                mManager.getPlayer().getPokemons().size()
                + mManager.getPlayer().getBox().size() > 1
        );
        popup.getMenu().getItem(3).setTitle(transferLabel);
        popup.getMenu().getItem(3).setVisible(canTransfer);

        for(int index = 0; index < popup.getMenu().size(); index++){
            PokemonApp.applyFontToMenuItem(contex, popup.getMenu().getItem(index));
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_summary :{
                        showPokemonProfileDialog(app, contex, profile);
                        break;
                    }
                    case R.id.action_evolve :{
                        profile.evolve(app.getPokemon(profile.getDexData().getNextDex()));
                        mMessage.setText(
                                profile.getNickname()
                                + " has evolved into a "
                                + profile.getDexData().getName()
                                + "!"
                        );
                        app.savePlayerData();
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
                ResetPokémonButtons();
                mManager.getPokemonAdapter().notifyDataSetChanged();
                return true;
            }
        });
        popup.show();
    }

    /**
     * Displays the Dialaog containing all the data of the selected Pokémon.
     * @param app           Used to access the PokémonApp functions.
     * @param contex        Where the ListView is to be displayed.
     * @param profile       The selected Pokémon.
     */
    public void showPokemonProfileDialog(final PokemonApp app,
                                         Activity contex,
                                         final PokémonProfile profile){
        final Dialog dialog = new Dialog(contex);
        dialog.setContentView(R.layout.pokemon_profile_dialog);
        final EditText edtNickname = (EditText) dialog.findViewById(R.id.edt_profile_nickname);
        edtNickname.setText(profile.getNickname());
        app.loadPokemonDetails(dialog, contex, profile);
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
                    updatePokémonButtons();
                    dialog.dismiss();
                }
            }
        });
        app.setAsOkButton(btnDialogOk);
    }

}
