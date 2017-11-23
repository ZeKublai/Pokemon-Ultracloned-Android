package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BattleActivity extends AppCompatActivity {

    private Battle battle;
    MusicHandler music;

    private Button btnAction;
    private Button btnFight;
    private Button btnPokemon;
    private Button btnBag;
    private Button btnRun;

    private TextView txvMessage;
    private MoveList mMoveAdapter = null;
    private PokemonList mPokemonAdapter = null;
    private ItemList mItemAdapter = null;
    private ListView lsvOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        final PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setFontForContainer((RelativeLayout)findViewById(R.id.battle_group), "generation6.ttf");

        //MUSIC INITIALIZATION
        music = new MusicHandler();
        music.initMusic(BattleActivity.this, MusicHandler.MUSIC_BATTLE);
        music.playMusic(app.getMusicSwitch());
        app.getMusicHandler().initButtonSfx(this);

        //BATTLE INITIALIZATION
        battle = new Battle();
        battle.setBuddy(app.getPlayer().getBuddy());
        battle.setPlayer(app.getPlayer());
        battle.setTypeChart(app.getAllTypes());

        //TODO: ENEMY INITIALIZATION NEEDS MODIFICATION
        battle.setEnemy(new PokemonProfile(app.getSpawnCount(), app.getPokemon(app.getCurrentGoal().getTitle()), app.getPlayer().getAverageLevel()));
        battle.getEnemy().getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        battle.getEnemy().getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        battle.getEnemy().getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        battle.getEnemy().getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());

        //UI INITIALIZATION
        battle.setBuddyInfo(new PokemonInfoBuddy((TextView) findViewById(R.id.txv_battle_buddy_name),
                (TextView) findViewById(R.id.txv_battle_buddy_hp),
                (TextView) findViewById(R.id.txv_battle_buddy_level),
                (ProgressBar) findViewById(R.id.bar_battle_buddy_hp),
                (ProgressBar) findViewById(R.id.bar_battle_buddy_exp),
                (ImageButton) findViewById(R.id.imgbtn_battle_buddy)));

        battle.setEnemyInfo(new PokemonInfo((TextView) findViewById(R.id.txv_battle_enemy_name),
                (TextView) findViewById(R.id.txv_battle_enemy_level),
                (ProgressBar) findViewById(R.id.bar_battle_enemy_hp),
                (ImageButton) findViewById(R.id.imgbtn_battle_enemy)));

        battle.getEnemyInfo().updatePokemon(battle.getEnemy());
        battle.addMessage(new MessageUpdatePokemon("Wild " + battle.getEnemy().getNickname() + " appeared!", battle.getEnemyInfo(), battle.getEnemy()));
        battle.addMessage(new MessageUpdatePokemon("Go " + battle.getBuddy().getNickname() + "!", battle.getBuddyInfo(), battle.getBuddy()));

        txvMessage = (TextView) findViewById(R.id.txv_battle_message);

        btnAction = (Button) findViewById(R.id.btn_battle_action);
        btnAction.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);

        btnFight = (Button) findViewById(R.id.btn_battle_attack);
        btnPokemon = (Button) findViewById(R.id.btn_battle_pokemon);
        btnBag = (Button) findViewById(R.id.btn_battle_bag);
        btnRun = (Button) findViewById(R.id.btn_battle_run);

        initButtons();

        mMoveAdapter = new MoveList(BattleActivity.this, battle.getBuddy().getMoves());
        mPokemonAdapter = new PokemonList(BattleActivity.this, battle.getPlayer().getPokemons());
        mItemAdapter = new ItemList(BattleActivity.this, battle.getPlayer().getBag());

        lsvOptions = (ListView)findViewById(R.id.lsv_battle_options);
        messageState();

        app.setFontForContainer((ListView) findViewById(R.id.lsv_battle_options), "generation6.ttf");
        lsvOptions.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(battle.getState() == PokemonGoApp.STATE_FIGHT){
                            battle.setSelectedMove(battle.getBuddy().getMoves().get(pos));
                            battle.setPlayerDecision(new DecisionAttack(battle.getBuddy(), battle.getEnemy(), battle.getSelectedMove(), battle.getEnemyInfo()));
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_POKEMON){
                            battle.setPlayerDecision(new DecisionSwitch(battle.getPlayer().getPokemons().get(pos), battle.getBuddy(), battle.getBuddyInfo()));
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_BAG){
                            battle.setSelectedItem(battle.getPlayer().getBag().get(pos));
                            if(battle.getSelectedItem() instanceof ItemTargetEnemy){
                                battle.setPlayerDecision(new DecisionUse(battle.getSelectedItem()));
                                messageState();
                            }
                            else{
                                pokemonState(PokemonGoApp.STATE_USE_ITEM);
                            }
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_USE_ITEM){
                            battle.setSelectedPokemon(battle.getPlayer().getPokemons().get(pos));
                            battle.setPlayerDecision(new DecisionUse(battle.getSelectedItem()));
                            messageState();
                        }
                    }
                }
        );

        btnFight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(battle.getBuddy().noMorePP()){
                            battle.setPlayerDecision(new DecisionAttack(battle.getBuddy(), battle.getEnemy(), new MoveStruggle(battle), battle.getEnemyInfo()));
                            messageState();
                        }
                        else{
                            fightState();
                        }
                    }
                }
        );
        btnPokemon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        pokemonState(PokemonGoApp.STATE_POKEMON);
                    }
                }
        );
        btnBag.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        bagState();
                    }
                }
        );

        btnRun.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        battle.setPlayerDecision(new DecisionRun());
                        messageState();
                    }
                }
        );

        btnAction.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        viewMessages();
                    }
                }
        );
    }

    private void viewMessages(){
        if(battle.getIndex() < battle.getMessages().size()){
            battle.getMessages().get(battle.getIndex()).executeUpdate(battle);
            txvMessage.setText(battle.getMessages().get(battle.getIndex()).getMessage());
            if(battle.getIndex() == battle.getMessages().size() - 1
                    && battle.getState() == PokemonGoApp.STATE_MESSAGE_FIRST){
                battle.secondMove();
                battle.setState(PokemonGoApp.STATE_MESSAGE_LAST);
            }

            battle.setIndex(battle.getIndex() + 1);
        }
        else{
            battle.newTurn();
            mMoveAdapter.notifyDataSetChanged();
            mMoveAdapter = new MoveList(BattleActivity.this, battle.getBuddy().getMoves());
            mPokemonAdapter.notifyDataSetChanged();
            mItemAdapter.notifyDataSetChanged();
            if(battle.isFinished()){
                endBattle();
            }
            else if(battle.isBuddyFainted()){
                txvMessage.setText("Swap next Pokemon?");
                pokemonState(PokemonGoApp.STATE_POKEMON);
            }
            else{
                mainState();
            }

        }
    }

    private void endBattle(){
        Intent mainActivityIntent = new Intent(BattleActivity.this, MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(mainActivityIntent, 0);
        finish();
    }

    private void messageState(){
        battle.setState(PokemonGoApp.STATE_MESSAGE_FIRST);
        setButtons(false, View.INVISIBLE);
        enableButton(btnAction);
        hideAllOptions();

        battle.initializeMessages();
        viewMessages();
    }

    private void setButtons(boolean clickable, int visibility){
        btnFight.setClickable(clickable);
        btnPokemon.setClickable(clickable);
        btnBag.setClickable(clickable);
        btnRun.setClickable(clickable);

        btnFight.setVisibility(visibility);
        btnPokemon.setVisibility(visibility);
        btnBag.setVisibility(visibility);
        btnRun.setVisibility(visibility);
    }

    private void mainState(){
        battle.setState(PokemonGoApp.STATE_MAIN);
        setButtons(true, View.VISIBLE);
        disableButton(btnAction);
        hideAllOptions();
        initButtons();
        battle.setEnemyDecision(battle.generateEnemyDecision());
        txvMessage.setText("What will " + battle.getBuddy().getNickname() + " do?");
    }

    private void initButtons(){
        btnFight.setText("FIGHT");
        btnPokemon.setText("POKEMON");
        btnBag.setText("BAG");
        btnRun.setText("RUN");
        btnFight.setBackgroundColor(PokemonGoApp.FIGHT_COLOR);
        btnPokemon.setBackgroundColor(PokemonGoApp.POKEMON_COLOR);
        btnBag.setBackgroundColor(PokemonGoApp.BAG_COLOR);
        btnRun.setBackgroundColor(PokemonGoApp.RUN_COLOR);
    }

    private void showAllOptions(){
        lsvOptions.setClickable(true);
        lsvOptions.setVisibility(View.VISIBLE);
    }

    private void hideAllOptions(){
        lsvOptions.setClickable(false);
        lsvOptions.setVisibility(View.INVISIBLE);
    }

    private void fightState(){
        battle.setState(PokemonGoApp.STATE_FIGHT);
        lsvOptions.setAdapter(mMoveAdapter);
        showAllOptions();
        txvMessage.setText("What will " + battle.getBuddy().getNickname() + " do?");

        enableButton(btnRun);
        disableButton(btnAction);
    }

    private void pokemonState(int state){
        battle.setState(state);
        lsvOptions.setAdapter(mPokemonAdapter);
        showAllOptions();

        if(state == PokemonGoApp.STATE_USE_ITEM){
            txvMessage.setText("Use " + battle.getSelectedItem().getName() + " on which Pokemon?");
        }
        else{
            txvMessage.setText("Which Pokemon to switch?");
        }

        enableButton(btnRun);
        disableButton(btnAction);
    }

    private void disableButton(Button btn){
        btn.setClickable(false);
        btn.setVisibility(View.INVISIBLE);
    }

    private void enableButton(Button btn){
        btn.setVisibility(View.VISIBLE);
        btn.setClickable(true);
    }

    private void bagState(){
        battle.setState(PokemonGoApp.STATE_BAG);
        lsvOptions.setAdapter(mItemAdapter);
        showAllOptions();

        txvMessage.setText("Which item will you use?");

        enableButton(btnRun);
        disableButton(btnAction);
    }


    @Override
    public void onBackPressed(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        PokemonGoApp app = (PokemonGoApp) getApplication();
        if(music == null){
            music.initMusic(this, MusicHandler.MUSIC_BATTLE);
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
