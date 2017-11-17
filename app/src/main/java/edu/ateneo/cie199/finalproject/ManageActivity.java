package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ManageActivity extends AppCompatActivity {

    private int mMenuState;
    private Item mSelectedItem;
    private PokemonProfile mSelectedProfile1;
    private PokemonProfile mSelectedProfile2;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;

    Button btnHeal;
    Button btnRevive;
    Button btnRestore;
    Button btnBack;
    Button btnSwap;

    ImageView icon1;
    ImageView icon2;
    ImageView icon3;
    ImageView icon4;
    ImageView icon5;
    ImageView icon6;

    ImageView iconHeal;
    ImageView iconRevive;
    ImageView iconRestore;

    ProgressBar bar1;
    ProgressBar bar2;
    ProgressBar bar3;
    ProgressBar bar4;
    ProgressBar bar5;
    ProgressBar bar6;

    TextView txvMessage;

    private CustomList mPokemonAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        final PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setFontForContainer((RelativeLayout) findViewById(R.id.manager_group), "generation6.ttf");

        mPokemonAdapter = new CustomList(ManageActivity.this, app.getPlayer().getBox());
        final ListView lsvPokemons = (ListView)findViewById(R.id.lsv_pokemon_box);
        lsvPokemons.setAdapter(mPokemonAdapter);

        app.setFontForContainer((ListView) findViewById(R.id.lsv_pokemon_box), "generation6.ttf");
        lsvPokemons.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        if(getMenuState() == PokemonGoApp.STATE_MAIN){

                        }
                        else if(getMenuState() == PokemonGoApp.STATE_SWAP_POKEMON1){
                            PokemonGoApp app = (PokemonGoApp) getApplication();
                            setSelectedProfile1(app.getPlayer().getBox().get(pos));
                            view.setBackground(app.getShape(PokemonGoApp.BACK_COLOR));
                            setMenuState(PokemonGoApp.STATE_SWAP_POKEMON2, "Select other Pokemon to switch.");
                        }
                        else if(getMenuState() == PokemonGoApp.STATE_SWAP_POKEMON2){
                            switchSecondPokemon(app.getPlayer().getBox().get(pos));
                        }
                        else{
                            useItemManager(app.getPlayer().getBox().get(pos));
                        }
                    }
                }
        );

        btn1 = (Button) findViewById(R.id.btn_pokemon1);
        btn2 = (Button) findViewById(R.id.btn_pokemon2);
        btn3 = (Button) findViewById(R.id.btn_pokemon3);
        btn4 = (Button) findViewById(R.id.btn_pokemon4);
        btn5 = (Button) findViewById(R.id.btn_pokemon5);
        btn6 = (Button) findViewById(R.id.btn_pokemon6);

        btnHeal = (Button) findViewById(R.id.btn_pokemon_heal);
        btnRevive = (Button) findViewById(R.id.btn_pokemon_revive);
        btnRestore = (Button) findViewById(R.id.btn_pokemon_restore);
        btnSwap = (Button) findViewById(R.id.btn_pokemon_swap);

        icon1 = (ImageView) findViewById(R.id.img_pokemon1);
        icon2 = (ImageView) findViewById(R.id.img_pokemon2);
        icon3 = (ImageView) findViewById(R.id.img_pokemon3);
        icon4 = (ImageView) findViewById(R.id.img_pokemon4);
        icon5 = (ImageView) findViewById(R.id.img_pokemon5);
        icon6 = (ImageView) findViewById(R.id.img_pokemon6);

        iconHeal = (ImageView) findViewById(R.id.img_pokemon_heal);
        iconRevive = (ImageView) findViewById(R.id.img_pokemon_revive);
        iconRestore = (ImageView) findViewById(R.id.img_pokemon_restore);

        bar1 = (ProgressBar) findViewById(R.id.bar_pokemon1);
        bar2 = (ProgressBar) findViewById(R.id.bar_pokemon2);
        bar3 = (ProgressBar) findViewById(R.id.bar_pokemon3);
        bar4 = (ProgressBar) findViewById(R.id.bar_pokemon4);
        bar5 = (ProgressBar) findViewById(R.id.bar_pokemon5);
        bar6 = (ProgressBar) findViewById(R.id.bar_pokemon6);

        txvMessage = (TextView) findViewById(R.id.txv_pokemon_message);

        setMenuState(PokemonGoApp.STATE_MAIN, Message.MESSAGE_MANAGER_MAIN);

        btnBack = (Button) findViewById(R.id.btn_pokemon_back);
        app.setAsBackButton(btnBack);
        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mainActivityIntent = new Intent(ManageActivity.this, MainActivity.class);
                        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(mainActivityIntent, 0);
                        finish();
                    }
                }
        );

        updatePokemons();
        mPokemonAdapter.notifyDataSetChanged();
        resetItemButtons();
        setPokemonButton(btn1, app.getPlayer().getPokemons()[0]);
        setPokemonButton(btn2, app.getPlayer().getPokemons()[1]);
        setPokemonButton(btn3, app.getPlayer().getPokemons()[2]);
        setPokemonButton(btn4, app.getPlayer().getPokemons()[3]);
        setPokemonButton(btn5, app.getPlayer().getPokemons()[4]);
        setPokemonButton(btn6, app.getPlayer().getPokemons()[5]);

        resetSwitchButton();
        btnSwap.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swapState();
                    }
                }
        );
        setItemButton(btnHeal, app.getPlayer().getBag()[0], PokemonGoApp.STATE_HEAL_POKEMON);
        setItemButton(btnRevive, app.getPlayer().getBag()[1], PokemonGoApp.STATE_REVIVE_POKEMON);
        setItemButton(btnRestore, app.getPlayer().getBag()[2], PokemonGoApp.STATE_RESTORE_POKEMON);
    }

    public void setPokemonButton(Button button, PokemonProfile pokemonProfile){
        final Button btn = button;
        final PokemonProfile profile = pokemonProfile;
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(getMenuState() == PokemonGoApp.STATE_MAIN){

                        }
                        else if(getMenuState() == PokemonGoApp.STATE_SWAP_POKEMON1){
                            switchFirstPokemon(btn, profile);
                        }
                        else if(getMenuState() == PokemonGoApp.STATE_SWAP_POKEMON2){
                            switchSecondPokemon(profile);
                        }
                        else{
                            useItemManager(profile);
                        }
                    }
                }
        );
    }

    public void setItemButton(Button button, Item selectedItem, final int targetState){
        final Button btn = button;
        final Item item = selectedItem;
        final int state = targetState;
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectItem(targetState, item, btn);
                    }
                }
        );
    }

    public void switchFirstPokemon(Button btn, PokemonProfile profile){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        setSelectedProfile1(profile);
        setMenuState(PokemonGoApp.STATE_SWAP_POKEMON2, "Select other Pokemon to switch.");
        app.setButtonBorder(btn, PokemonGoApp.BACK_COLOR);
    }

    public void switchSecondPokemon(PokemonProfile profile){
        setSelectedProfile2(profile);
        swapPokemon();
    }

    public void updatePokemons(){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setPokemonButton(btn1, app.getPlayer().getPokemons()[0], bar1, icon1);
        app.setPokemonButton(btn2, app.getPlayer().getPokemons()[1], bar2, icon2);
        app.setPokemonButton(btn3, app.getPlayer().getPokemons()[2], bar3, icon3);
        app.setPokemonButton(btn4, app.getPlayer().getPokemons()[3], bar4, icon4);
        app.setPokemonButton(btn5, app.getPlayer().getPokemons()[4], bar5, icon5);
        app.setPokemonButton(btn6, app.getPlayer().getPokemons()[5], bar6, icon6);
    }

    public void resetItemButtons(){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setBagButton(btnHeal, app.getPlayer().getBag()[0], iconHeal);
        app.setBagButton(btnRevive, app.getPlayer().getBag()[1], iconRevive);
        app.setBagButton(btnRestore, app.getPlayer().getBag()[2], iconRestore);
        resetSwitchButton();
    }

    public void resetSwitchButton(){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        btnSwap.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        app.setButtonBorder(btnSwap, PokemonGoApp.RUN_COLOR);
        btnSwap.setText("SWITCH");
    }

    public int getMenuState() {
        return mMenuState;
    }
    public void setMenuState(int mMenuState, String message) {
        this.mMenuState = mMenuState;
        txvMessage.setText(message);
    }

    public Item getSelectedItem() {
        return mSelectedItem;
    }
    public void setSelectedItem(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    public PokemonProfile getSelectedProfile1() {
        return mSelectedProfile1;
    }
    public void setSelectedProfile1(PokemonProfile mSelectedProfile1) {
        this.mSelectedProfile1 = mSelectedProfile1;
    }

    public PokemonProfile getSelectedProfile2() {
        return mSelectedProfile2;
    }
    public void setSelectedProfile2(PokemonProfile mSelectedProfile2) {
        this.mSelectedProfile2 = mSelectedProfile2;
    }

    public boolean hasItem(){
        if(mSelectedItem.getQuantity() <= 0){
            txvMessage.setText("You are out of " + mSelectedItem.getName() + "!");
            return false;
        }
        else{
            return true;
        }
    }

    public void executeItem(boolean itemSuccess){
        if(itemSuccess){
            PokemonGoApp app = (PokemonGoApp) getApplication();
            txvMessage.setText(app.getPlayer().getName() + " used " + mSelectedItem.getName() + "!");
            mSelectedItem.setQuantity(mSelectedItem.getQuantity() - 1);
        }
        else{
            txvMessage.setText("It will have no effect...");
        }
    }

    public void useItemManager(PokemonProfile profile){
        if(hasItem()){
            if(getMenuState() == PokemonGoApp.STATE_HEAL_POKEMON){
                executeItem(mSelectedItem.healPokemon(profile));
                itemState(btnHeal);
            }
            else if(getMenuState() == PokemonGoApp.STATE_REVIVE_POKEMON){
                executeItem(mSelectedItem.revivePokemon(profile));
                itemState(btnRevive);
            }
            else if(getMenuState() == PokemonGoApp.STATE_RESTORE_POKEMON){
                executeItem(mSelectedItem.restorePP(profile));
                itemState(btnRestore);
            }
            mPokemonAdapter.notifyDataSetChanged();
            updatePokemons();
        }
    }

    public void swapState(){
        if(getMenuState() == PokemonGoApp.STATE_SWAP_POKEMON1 || getMenuState() == PokemonGoApp.STATE_SWAP_POKEMON2){
            setMenuState(PokemonGoApp.STATE_MAIN, Message.MESSAGE_MANAGER_MAIN);
            resetItemButtons();
            mPokemonAdapter.notifyDataSetChanged();
            updatePokemons();
        }
        else{
            PokemonGoApp app = (PokemonGoApp) getApplication();
            resetItemButtons();
            app.setAsCancelButton(btnSwap);
            setMenuState(PokemonGoApp.STATE_SWAP_POKEMON1, "Switch which Pokemon?");
        }
    }

    public void selectItem(int itemState, Item item, Button btn){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        if(getMenuState() == itemState){
            resetItemButtons();
            setMenuState(PokemonGoApp.STATE_MAIN, Message.MESSAGE_MANAGER_MAIN);
        }
        else{
            setMenuState(itemState, "Use " + item.getName() + " on which Pokemon?");
            setSelectedItem(item);
            itemState(btn);
        }
    }

    public void itemState(Button btn){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        resetItemButtons();
        app.setAsCancelButton(btn);
    }

    public void swapPokemon(){
        PokemonProfile swap1 = new PokemonProfile(mSelectedProfile1);
        PokemonProfile swap2 = new PokemonProfile(mSelectedProfile2);
        mSelectedProfile1.loadProfile(swap2);
        mSelectedProfile2.loadProfile(swap1);
        mPokemonAdapter.notifyDataSetChanged();
        updatePokemons();
        setMenuState(PokemonGoApp.STATE_MAIN, Message.MESSAGE_MANAGER_MAIN);
        resetItemButtons();
    }
}
