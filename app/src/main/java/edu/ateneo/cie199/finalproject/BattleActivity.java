package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BattleActivity extends AppCompatActivity {
    public static int FIGHT_COLOR = Color.argb(255, 238, 41, 41);
    public static int POKEMON_COLOR = Color.argb(255, 44, 224, 49);
    public static int BAG_COLOR = Color.argb(255, 252, 190, 26);
    public static int RUN_COLOR = Color.argb(255, 43, 154, 255);
    public static int BACK_COLOR = Color.argb(255, 3, 111, 114);
    public static int TRANSPARENT_COLOR = Color.argb(0, 0, 0, 0);

    private Battle battle;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btnAction;
    private Button btnRun;

    private TextView txvMessage;
    private TextView txvEnemyName;
    private TextView txvBuddyName;
    private ImageButton imgButtonEnemy;
    private ImageButton imgButtonBuddy;

    ProgressBar barEnemyHP;
    ProgressBar barBuddyHP;
    ProgressBar barBuddyEXP;
    TextView txvBuddyHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        //Stops the Main_song
        MusicBackground.bg_player.release();

        //Plays music
        MusicBackground.BattleSong(this,R.raw.battle_song);

        PokemonGoApp app = (PokemonGoApp) getApplication();

        //TODO CHECK IF POKEMON IS DEAD
        battle = new Battle(app.getPlayer().getPokemons()[0],
                new PokemonProfile(app.getSpawnCount(),
                        app.getPokemon(app.getCurrentGoal().getTitle())));
        battle.setPlayer(app.getPlayer());
        battle.setTypeChart(app.getAllTypes());
        battle.getEnemy().getMoves()[0] = app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size()));
        battle.getEnemy().getMoves()[1] = app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size()));
        battle.getEnemy().getMoves()[2] = app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size()));
        battle.getEnemy().getMoves()[3] = app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size()));

        txvEnemyName = (TextView) findViewById(R.id.txv_battle_enemy_name);
        txvBuddyName = (TextView) findViewById(R.id.txv_battle_buddy_name);
        imgButtonEnemy = (ImageButton) findViewById(R.id.imgbtn_battle_enemy);
        imgButtonBuddy = (ImageButton) findViewById(R.id.imgbtn_battle_buddy);
        txvMessage = (TextView) findViewById(R.id.txv_battle_message);

        imgButtonEnemy.setBackgroundColor(TRANSPARENT_COLOR);
        imgButtonBuddy.setBackgroundColor(TRANSPARENT_COLOR);

        btnAction = (Button) findViewById(R.id.btn_battle_action);
        btnAction.setBackgroundColor(TRANSPARENT_COLOR);
        btn1 = (Button) findViewById(R.id.btn_battle_option1);
        btn2 = (Button) findViewById(R.id.btn_battle_option2);
        btn3 = (Button) findViewById(R.id.btn_battle_option3);
        btn4 = (Button) findViewById(R.id.btn_battle_option4);
        btn5 = (Button) findViewById(R.id.btn_battle_option5);
        btn6 = (Button) findViewById(R.id.btn_battle_option6);
        btn7 = (Button) findViewById(R.id.btn_battle_option7);
        btnRun = (Button) findViewById(R.id.btn_battle_run);
        btnRun.setBackgroundColor(RUN_COLOR);

        barEnemyHP = (ProgressBar) findViewById(R.id.bar_battle_enemy_hp);
        barBuddyHP = (ProgressBar) findViewById(R.id.bar_battle_buddy_hp);
        barBuddyEXP = (ProgressBar) findViewById(R.id.bar_battle_buddy_exp);
        txvBuddyHP = (TextView) findViewById(R.id.txv_battle_buddy_hp);


        barEnemyHP.getProgressDrawable().setColorFilter(
                Color.argb(255, 0, 225, 231),android.graphics.PorterDuff.Mode.SRC_IN);
        barBuddyHP.getProgressDrawable().setColorFilter(
                Color.argb(255, 0, 225, 231),android.graphics.PorterDuff.Mode.SRC_IN);
        barBuddyEXP.getProgressDrawable().setColorFilter(
                RUN_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);

        updateEnemyPokemon();

        battle.addMessage("Wild " + battle.getEnemy().getNickname() + " appeared!", battle.NO_UPDATE);
        battle.addMessage("Go " + battle.getBuddy().getNickname() + "!", battle.UPDATE_BUDDY);
        messageState();

        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MusicBackground.ButtonPressed(BattleActivity.this);
                        if(battle.getPhase() == battle.POKEMON_STATE){
                            battle.setPlayerDecision(battle.DECISION_SWAP0);
                            messageState();
                        }
                        else if(battle.getPhase() == battle.BAG_STATE){

                        }
                    }
                }
        );

        btn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MusicBackground.ButtonPressed(BattleActivity.this);
                        if(battle.getPhase() == battle.POKEMON_STATE){
                            battle.setPlayerDecision(battle.DECISION_SWAP1);
                            messageState();
                        }
                        else if(battle.getPhase() == battle.BAG_STATE){

                        }
                    }
                }
        );

        btn3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MusicBackground.ButtonPressed(BattleActivity.this);
                        if(battle.getPhase() == battle.FIGHT_STATE){
                            battle.setPlayerDecision(battle.DECISION_ATTACK0);
                            messageState();
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            battle.setPlayerDecision(battle.DECISION_SWAP2);
                            messageState();
                        }
                        else if(battle.getPhase() == battle.BAG_STATE){

                        }
                    }
                }
        );

        btn4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MusicBackground.ButtonPressed(BattleActivity.this);
                        if(battle.getPhase() == battle.FIGHT_STATE){
                            battle.setPlayerDecision(battle.DECISION_ATTACK1);
                            messageState();
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            battle.setPlayerDecision(battle.DECISION_SWAP3);
                            messageState();
                        }
                        else if(battle.getPhase() == battle.BAG_STATE){

                        }
                    }
                }
        );

        btn5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MusicBackground.ButtonPressed(BattleActivity.this);
                        if(battle.getPhase() == battle.MAIN_STATE){
                            fightState();
                        }
                        else if(battle.getPhase() == battle.FIGHT_STATE){
                            battle.setPlayerDecision(battle.DECISION_ATTACK2);
                            messageState();
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            battle.setPlayerDecision(battle.DECISION_SWAP4);
                            messageState();
                        }
                        else if(battle.getPhase() == battle.BAG_STATE){

                        }
                    }
                }
        );

        btn6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MusicBackground.ButtonPressed(BattleActivity.this);
                        if(battle.getPhase() == battle.MAIN_STATE){
                            pokemonState();
                        }
                        else if(battle.getPhase() == battle.FIGHT_STATE){
                            battle.setPlayerDecision(battle.DECISION_ATTACK3);
                            messageState();
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            battle.setPlayerDecision(battle.DECISION_SWAP5);
                            messageState();
                        }
                        else if(battle.getPhase() == battle.BAG_STATE){

                        }
                    }
                }
        );

        btn7.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MusicBackground.ButtonPressed(BattleActivity.this);
                        if(battle.getPhase() == battle.MAIN_STATE){
                            bagState();
                        }
                        else if(battle.getPhase() == battle.FIGHT_STATE){
                            mainState();
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            mainState();
                        }
                        else if(battle.getPhase() == battle.BAG_STATE){
                            mainState();
                        }
                    }
                }
        );

        btnRun.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MusicBackground.battle_player.release();
                        MusicBackground.MainSong(BattleActivity.this,R.raw.main_song);
                        Intent MainActivityIntent = new Intent(BattleActivity.this, MainActivity.class);
                        MainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(MainActivityIntent, 0);
                        finish();
                    }
                }
        );

        btnAction.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MusicBackground.ButtonPressed(BattleActivity.this);
                        viewMessages();
                    }
                }
        );
    }

    private void viewMessages(){
        if(battle.getCurrentMessage() < battle.getMessages().size()){
            txvMessage.setText(battle.getMessages().get(battle.getCurrentMessage()));
            updateBattle();

            if(battle.getCurrentMessage() == battle.getMessages().size() - 1
                    && battle.getPhase() == battle.MESSAGE_STATE_FIRST){
                if(battle.buddyFirst()){
                    battle.executeEnemy(battle.getEnemyDecision());
                }
                else{
                    battle.executePlayer(battle.getPlayerDecision());
                }
                battle.setPhase(battle.MESSAGE_STATE_LAST);
            }

            battle.setCurrentMessage(battle.getCurrentMessage() + 1);
        }
        else{
            battle.getMessages().clear();
            battle.getUpdates().clear();
            battle.setCurrentMessage(0);
            battle.setPlayerDecision(battle.DECISION_NONE);
            battle.setEnemyDecision(battle.DECISION_NONE);
            if(battle.checkVictory()){
                MusicBackground.battle_player.release();
                Intent MainActivityIntent = new Intent(BattleActivity.this, MainActivity.class);
                MainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(MainActivityIntent, 0);
                finish();
            }
            else if(battle.getBuddy().getCurrentHP() <= 0){
                if(battle.checkPlayerDefeat()){
                    MusicBackground.battle_player.release();
                    Intent MainActivityIntent = new Intent(BattleActivity.this, MainActivity.class);
                    MainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(MainActivityIntent, 0);
                    finish();
                }
                else{
                    txvMessage.setText("Swap next Pokemon?");
                    pokemonState();
                }
            }
            else{
                mainState();
            }
        }
    }

    private void updateBattle(){
        if(battle.getUpdates().get(battle.getCurrentMessage()).equals(battle.UPDATE_BUDDY)){
            updateBuddyPokemon();
        }
        else if(battle.getUpdates().get(battle.getCurrentMessage()).equals(battle.UPDATE_ENEMY)){
            updateEnemyPokemon();
        }
        else if(battle.getUpdates().get(battle.getCurrentMessage()).equals(battle.UPDATE_BUDDY_EXP)){
            updateBuddyExp();
        }
        else if(battle.getUpdates().get(battle.getCurrentMessage()).equals(battle.UPDATE_ENEMY_HP)){
            updateEnemyHp();
        }
        else if(battle.getUpdates().get(battle.getCurrentMessage()).equals(battle.UPDATE_BUDDY_HP)){
            updateBuddyHp();
        }
    }

    private void updateBuddyPokemon(){
        txvBuddyName.setText(battle.getBuddy().getNickname());
        imgButtonBuddy.setImageResource(battle.getBuddy().getDexData().getBackImage());
        updateBuddyHp();
        updateBuddyExp();
    }

    private void updateEnemyPokemon(){
        txvEnemyName.setText(battle.getEnemy().getNickname());
        imgButtonEnemy.setImageResource(battle.getEnemy().getDexData().getFrontImage());
        updateEnemyHp();
    }

    private void updateEnemyHp(){
        barEnemyHP.setMax(battle.getEnemy().getHP());
        barEnemyHP.setProgress(battle.getEnemy().getCurrentHP());
    }

    private void updateBuddyHp(){
        barBuddyHP.setMax(battle.getBuddy().getHP());
        barBuddyHP.setProgress(battle.getBuddy().getCurrentHP());
        txvBuddyHP.setText(battle.getBuddy().getCurrentHP() + "/" +
                battle.getBuddy().getHP());
    }

    private void updateBuddyExp(){
        barBuddyEXP.setMax(battle.getBuddy().getExperienceNeeded());
        barBuddyEXP.setProgress(battle.getBuddy().getCurrentExp());
    }

    private void messageState(){
        battle.setPhase(battle.MESSAGE_STATE_FIRST);
        btn1.setClickable(false);
        btn2.setClickable(false);
        btn3.setClickable(false);
        btn4.setClickable(false);
        btn5.setClickable(false);
        btn6.setClickable(false);
        btn7.setClickable(false);
        btnRun.setClickable(false);
        btnAction.setClickable(true);

        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        btn5.setVisibility(View.INVISIBLE);
        btn6.setVisibility(View.INVISIBLE);
        btn7.setVisibility(View.INVISIBLE);
        btnRun.setVisibility(View.INVISIBLE);
        btnAction.setVisibility(View.VISIBLE);

        battle.setCurrentMessage(0);
        if(battle.buddyFirst()){
            battle.executePlayer(battle.getPlayerDecision());
        }
        else{
            battle.executeEnemy(battle.getEnemyDecision());
        }
        viewMessages();
    }

    private void mainState(){
        battle.setPhase(battle.MAIN_STATE);
        btn1.setClickable(false);
        btn2.setClickable(false);
        btn3.setClickable(false);
        btn4.setClickable(false);
        btn5.setClickable(true);
        btn6.setClickable(true);
        btn7.setClickable(true);
        btnRun.setClickable(true);
        btnAction.setClickable(false);

        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        btn5.setVisibility(View.VISIBLE);
        btn6.setVisibility(View.VISIBLE);
        btn7.setVisibility(View.VISIBLE);
        btnRun.setVisibility(View.VISIBLE);
        btnAction.setVisibility(View.INVISIBLE);

        btn5.setText("FIGHT");
        btn6.setText("POKEMON");
        btn7.setText("BAG");

        btn5.setBackgroundColor(FIGHT_COLOR);
        btn6.setBackgroundColor(POKEMON_COLOR);
        btn7.setBackgroundColor(BAG_COLOR);
        battle.setEnemyDecision(PokemonGoApp.getIntegerRNG(4) + 1);
        txvMessage.setText("What will " + battle.getBuddy().getNickname() + " do?");
    }

    private void fightState(){
        battle.setPhase(battle.FIGHT_STATE);
        btn1.setClickable(false);
        btn2.setClickable(false);
        btn3.setClickable(!battle.getBuddy().getMoves()[0].isEmpty());
        btn4.setClickable(!battle.getBuddy().getMoves()[1].isEmpty());
        btn5.setClickable(!battle.getBuddy().getMoves()[2].isEmpty());
        btn6.setClickable(!battle.getBuddy().getMoves()[3].isEmpty());
        btn7.setClickable(true);
        btnRun.setClickable(true);
        btnAction.setClickable(false);

        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn4.setVisibility(View.VISIBLE);
        btn5.setVisibility(View.VISIBLE);
        btn6.setVisibility(View.VISIBLE);
        btn7.setVisibility(View.VISIBLE);
        btnAction.setVisibility(View.INVISIBLE);

        btn3.setText(battle.getBuddy().getMoves()[0].getName());
        btn4.setText(battle.getBuddy().getMoves()[1].getName());
        btn5.setText(battle.getBuddy().getMoves()[2].getName());
        btn6.setText(battle.getBuddy().getMoves()[3].getName());
        btn7.setText("BACK");

        btn3.setBackgroundColor(FIGHT_COLOR);
        btn4.setBackgroundColor(FIGHT_COLOR);
        btn5.setBackgroundColor(FIGHT_COLOR);
        btn6.setBackgroundColor(FIGHT_COLOR);
        btn7.setBackgroundColor(BACK_COLOR);
    }

    private void pokemonState(){
        battle.setPhase(battle.POKEMON_STATE);
        btn1.setClickable(!battle.getPlayer().getPokemons()[0].isEmpty());
        btn2.setClickable(!battle.getPlayer().getPokemons()[1].isEmpty());
        btn3.setClickable(!battle.getPlayer().getPokemons()[2].isEmpty());
        btn4.setClickable(!battle.getPlayer().getPokemons()[3].isEmpty());
        btn5.setClickable(!battle.getPlayer().getPokemons()[4].isEmpty());
        btn6.setClickable(!battle.getPlayer().getPokemons()[5].isEmpty());
        btn7.setClickable(!(battle.getBuddy().getCurrentHP() <= 0));
        btnRun.setClickable(true);
        btnAction.setClickable(false);

        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn4.setVisibility(View.VISIBLE);
        btn5.setVisibility(View.VISIBLE);
        btn6.setVisibility(View.VISIBLE);
        if(battle.getBuddy().getCurrentHP() <= 0){
            btn7.setVisibility(View.INVISIBLE);
        }
        else{
            btn7.setVisibility(View.VISIBLE);
        }
        btnRun.setVisibility(View.VISIBLE);
        btnAction.setVisibility(View.INVISIBLE);

        btn1.setText(battle.getPlayer().getPokemons()[0].getNickname());
        btn2.setText(battle.getPlayer().getPokemons()[1].getNickname());
        btn3.setText(battle.getPlayer().getPokemons()[2].getNickname());
        btn4.setText(battle.getPlayer().getPokemons()[3].getNickname());
        btn5.setText(battle.getPlayer().getPokemons()[4].getNickname());
        btn6.setText(battle.getPlayer().getPokemons()[5].getNickname());
        btn7.setText("BACK");

        btn1.setBackgroundColor(POKEMON_COLOR);
        btn2.setBackgroundColor(POKEMON_COLOR);
        btn3.setBackgroundColor(POKEMON_COLOR);
        btn4.setBackgroundColor(POKEMON_COLOR);
        btn5.setBackgroundColor(POKEMON_COLOR);
        btn6.setBackgroundColor(POKEMON_COLOR);
        btn7.setBackgroundColor(BACK_COLOR);
    }

    private void bagState(){
        battle.setPhase(battle.BAG_STATE);

        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn4.setClickable(true);
        btn5.setClickable(true);
        btn6.setClickable(true);
        btn7.setClickable(true);
        btnRun.setClickable(true);
        btnAction.setClickable(false);

        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn4.setVisibility(View.VISIBLE);
        btn5.setVisibility(View.VISIBLE);
        btn6.setVisibility(View.VISIBLE);
        btn7.setVisibility(View.VISIBLE);
        btnAction.setVisibility(View.INVISIBLE);

        btn1.setText("Potion x" + battle.getPlayer().getPotions());
        btn2.setText("Super Potion x" + battle.getPlayer().getSuperPotions());
        btn3.setText("Max Revive x" + battle.getPlayer().getMaxRevives());
        btn4.setText("Poke Ball x" + battle.getPlayer().getPokeBall());
        btn5.setText("Great Ball x" + battle.getPlayer().getGreatBall());
        btn6.setText("Ultra Ball x" + battle.getPlayer().getUltraBall());
        btn7.setText("BACK");

        btn1.setBackgroundColor(BAG_COLOR);
        btn2.setBackgroundColor(BAG_COLOR);
        btn3.setBackgroundColor(BAG_COLOR);
        btn4.setBackgroundColor(BAG_COLOR);
        btn5.setBackgroundColor(BAG_COLOR);
        btn6.setBackgroundColor(BAG_COLOR);
        btn7.setBackgroundColor(BACK_COLOR);

    }
    @Override
    public void onBackPressed(){

    }

}
