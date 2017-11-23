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

import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity {

    private int mMenuState;
    private Item mSelectedItem;
    private PokemonProfile mSelectedProfile1;
    private PokemonProfile mSelectedProfile2;

    ArrayList<Button> btnPokemons = new ArrayList<>();
    ArrayList<ImageView> imgPokemons = new ArrayList<>();
    ArrayList<ProgressBar> barPokemons = new ArrayList<>();

    Button btnBack;
    Button btnSwitch;

    TextView txvMessage;
    MusicHandler music;

    private PokemonList mPokemonAdapter = null;
    private ItemList mItemAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        final PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setFontForContainer((RelativeLayout) findViewById(R.id.manager_group), "generation6.ttf");

        music = new MusicHandler();
        music.initMusic(ManageActivity.this, MusicHandler.MUSIC_MANAGE);
        music.playMusic(app.getMusicSwitch());
        app.getMusicHandler().initButtonSfx(this);

        mPokemonAdapter = new PokemonList(ManageActivity.this, app.getPlayer().getBox());
        mItemAdapter = new ItemList(ManageActivity.this, app.getPlayer().getBag());
        final ListView lsvPokemons = (ListView)findViewById(R.id.lsv_pokemon_box);
        final ListView lsvItems = (ListView) findViewById(R.id.lsv_player_bag);
        lsvPokemons.setAdapter(mPokemonAdapter);
        lsvItems.setAdapter(mItemAdapter);

        app.setFontForContainer((ListView) findViewById(R.id.lsv_pokemon_box), "generation6.ttf");
        app.setFontForContainer((ListView) findViewById(R.id.lsv_player_bag), "generation6.ttf");

        btnPokemons.add((Button) findViewById(R.id.btn_pokemon1));
        btnPokemons.add((Button) findViewById(R.id.btn_pokemon2));
        btnPokemons.add((Button) findViewById(R.id.btn_pokemon3));
        btnPokemons.add((Button) findViewById(R.id.btn_pokemon4));
        btnPokemons.add((Button) findViewById(R.id.btn_pokemon5));
        btnPokemons.add((Button) findViewById(R.id.btn_pokemon6));

        imgPokemons.add((ImageView) findViewById(R.id.img_pokemon1));
        imgPokemons.add((ImageView) findViewById(R.id.img_pokemon2));
        imgPokemons.add((ImageView) findViewById(R.id.img_pokemon3));
        imgPokemons.add((ImageView) findViewById(R.id.img_pokemon4));
        imgPokemons.add((ImageView) findViewById(R.id.img_pokemon5));
        imgPokemons.add((ImageView) findViewById(R.id.img_pokemon6));

        barPokemons.add((ProgressBar) findViewById(R.id.bar_pokemon1));
        barPokemons.add((ProgressBar) findViewById(R.id.bar_pokemon2));
        barPokemons.add((ProgressBar) findViewById(R.id.bar_pokemon3));
        barPokemons.add((ProgressBar) findViewById(R.id.bar_pokemon4));
        barPokemons.add((ProgressBar) findViewById(R.id.bar_pokemon5));
        barPokemons.add((ProgressBar) findViewById(R.id.bar_pokemon6));

        txvMessage = (TextView) findViewById(R.id.txv_pokemon_message);
        btnSwitch = (Button) findViewById(R.id.btn_pokemon_switch);
        btnBack = (Button) findViewById(R.id.btn_pokemon_back);

        mainState();

        initializeTeam();
        updatePokemons();
        mPokemonAdapter.notifyDataSetChanged();
        mItemAdapter.notifyDataSetChanged();

        lsvPokemons.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        executePokemonButton(view, app.getPlayer().getBox().get(pos));
                    }
                }
        );

        lsvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        resetSwitchButton();
                        setSelectedItem(app.getPlayer().getBag().get(pos));
                        if(mSelectedItem instanceof ItemTargetTeam){
                            mItemAdapter.itemSelected(pos);
                            setMenuState(PokemonGoApp.STATE_POKEMON, "Use " + mSelectedItem.getName() + " on which Pokemon?");
                            app.setAsCancelButton(btnBack);
                        }
                        else{
                            mItemAdapter.itemSelected(ItemList.NO_ITEM_SELECTED);
                            mSelectedItem.useInManager(mSelectedProfile1, txvMessage, app.getPlayer().getBag());
                            setMenuState(PokemonGoApp.STATE_MAIN, Message.ERROR_ECHO);
                            app.setAsBackButton(btnBack);
                        }
                        updatePokemons();
                        mPokemonAdapter.notifyDataSetChanged();
                    }
                }
        );

        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(getMenuState() == PokemonGoApp.STATE_POKEMON){
                            noItemSelected();
                            mainState();
                        }
                        else{
                            Intent mainActivityIntent = new Intent(ManageActivity.this, MainActivity.class);
                            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivityIfNeeded(mainActivityIntent, 0);
                            finish();
                        }
                    }
                }
        );

        resetSwitchButton();
        btnSwitch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        noItemSelected();
                        switchState();
                    }
                }
        );
    }

    public void noItemSelected(){
        mItemAdapter.itemSelected(ItemList.NO_ITEM_SELECTED);
        mItemAdapter.notifyDataSetChanged();
    }

    public void mainState(){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        setMenuState(PokemonGoApp.STATE_MAIN, Message.MESSAGE_MANAGER_MAIN);
        mItemAdapter.itemSelected(ItemList.NO_ITEM_SELECTED);
        app.setAsBackButton(btnBack);
        resetSwitchButton();
    }

    public void initializeTeam(){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        for(int index = 0; index < btnPokemons.size(); index++){
            btnPokemons.get(index).setClickable(false);
            btnPokemons.get(index).setVisibility(View.INVISIBLE);
        }

        for(int index = 0; index < app.getPlayer().getPokemons().size(); index++){
            barPokemons.get(index).setVisibility(View.VISIBLE);
            initializePokemonButton(btnPokemons.get(index), app.getPlayer().getPokemons().get(index));
        }
    }

    public void initializePokemonButton(Button button, PokemonProfile pokemonProfile){
        button.setVisibility(View.VISIBLE);
        button.setClickable(true);
        final Button btn = button;
        final PokemonProfile profile = pokemonProfile;
        final PokemonGoApp app = (PokemonGoApp) getApplication();
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    executePokemonButton(btn, profile);
                    }
                }
        );
    }

    public void useItem(PokemonProfile profile){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        if(mSelectedItem.getQuantity() > 0){
            mSelectedItem.useInManager(profile, txvMessage, app.getPlayer().getBag());
            if(mSelectedItem.getQuantity() <= 0){
                mainState();
            }
        }
        else{
            setMenuState(PokemonGoApp.STATE_MAIN, Message.MESSAGE_MANAGER_MAIN);
            mItemAdapter.itemSelected(ItemList.NO_ITEM_SELECTED);
        }
        mItemAdapter.notifyDataSetChanged();
        mPokemonAdapter.notifyDataSetChanged();
        updatePokemons();
    }

    public void switchFirstPokemon(View view, PokemonProfile profile){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        setSelectedProfile1(profile);
        setMenuState(PokemonGoApp.STATE_SWAP_POKEMON2, Message.MESSAGE_SELECT_SWITCH);
        view.setBackground(app.getShape(PokemonGoApp.BACK_COLOR));
    }
    public void switchSecondPokemon(PokemonProfile profile){
        setSelectedProfile2(profile);
        switchPokemon();
    }

    public void executePokemonButton(View view, PokemonProfile profile){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
        if(getMenuState() == PokemonGoApp.STATE_MAIN){

        }
        else if(getMenuState() == PokemonGoApp.STATE_SWAP_POKEMON1){
            switchFirstPokemon(view, profile);
        }
        else if(getMenuState() == PokemonGoApp.STATE_SWAP_POKEMON2){
            switchSecondPokemon(profile);
            mainState();
        }
        else if(getMenuState() == PokemonGoApp.STATE_POKEMON){
            useItem(profile);
        }
    }

    public void updatePokemons(){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        for(int index = 0; index < app.getPlayer().getPokemons().size(); index++) {
            app.setPokemonButton(btnPokemons.get(index), app.getPlayer().getPokemons().get(index),
                    barPokemons.get(index), imgPokemons.get(index));
        }
    }
    public void resetSwitchButton(){
        btnSwitch.setBackgroundColor(PokemonGoApp.RUN_COLOR);
        btnSwitch.setText("SWITCH");
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

    public void switchState(){
        if(getMenuState() == PokemonGoApp.STATE_SWAP_POKEMON1 || getMenuState() == PokemonGoApp.STATE_SWAP_POKEMON2){
            setMenuState(PokemonGoApp.STATE_MAIN, Message.MESSAGE_MANAGER_MAIN);
            resetSwitchButton();
            mPokemonAdapter.notifyDataSetChanged();
            updatePokemons();
        }
        else{
            PokemonGoApp app = (PokemonGoApp) getApplication();
            app.setAsCancelButton(btnSwitch);
            setMenuState(PokemonGoApp.STATE_SWAP_POKEMON1, "Switch which Pokemon?");
        }
    }
    public void switchPokemon(){
        PokemonProfile swap1 = new PokemonProfile(mSelectedProfile1);
        PokemonProfile swap2 = new PokemonProfile(mSelectedProfile2);
        mSelectedProfile1.loadProfile(swap2);
        mSelectedProfile2.loadProfile(swap1);
        mPokemonAdapter.notifyDataSetChanged();
        updatePokemons();
        setMenuState(PokemonGoApp.STATE_MAIN, Message.MESSAGE_MANAGER_MAIN);
    }

    @Override
    public void onBackPressed(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        PokemonGoApp app = (PokemonGoApp) getApplication();
        if(music == null){
            music.initMusic(this, MusicHandler.MUSIC_MANAGE);
        }
        if(!music.getMusicPlayer().isPlaying()) {
            music.playMusic(app.getMusicSwitch());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.getMusicPlayer().pause();
    }
}
