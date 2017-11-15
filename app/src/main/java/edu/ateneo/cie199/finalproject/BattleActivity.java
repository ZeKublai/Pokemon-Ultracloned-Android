package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BattleActivity extends AppCompatActivity {
    public static int FIGHT_COLOR = Color.argb(255, 238, 41, 41);
    public static int POKEMON_COLOR = Color.argb(255, 44, 224, 49);
    public static int DEAD_COLOR = Color.argb(255, 137, 17, 6);
    public static int BAG_COLOR = Color.argb(255, 252, 190, 26);
    public static int RUN_COLOR = Color.argb(255, 43, 154, 255);
    public static int BACK_COLOR = Color.argb(255, 3, 111, 114);
    public static int TRANSPARENT_COLOR = Color.argb(0, 0, 0, 0);

    private Battle battle;
    MusicHandler music;

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
    private TextView txvEnemyLevel;
    private TextView txvBuddyLevel;
    private ImageButton imgButtonEnemy;
    private ImageButton imgButtonBuddy;

    ProgressBar barEnemyHP;
    ProgressBar barBuddyHP;
    ProgressBar barBuddyEXP;
    TextView txvBuddyHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final PokemonGoApp app = (PokemonGoApp) getApplication();
        setContentView(R.layout.activity_battle);
        app.setFontForContainer((RelativeLayout)findViewById(R.id.battle_group), "generation6.ttf");

        music = new MusicHandler();
        music.loopMusic(BattleActivity.this, MusicHandler.MUSIC_BATTLE);

        //TODO CHECK IF POKEMON IS DEAD
        battle = new Battle(app.getPlayer().getBuddy(),
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
        txvEnemyLevel = (TextView) findViewById(R.id.txv_battle_enemy_level);
        txvBuddyLevel = (TextView) findViewById(R.id.txv_battle_buddy_level);
        imgButtonEnemy = (ImageButton) findViewById(R.id.imgbtn_battle_enemy);
        imgButtonBuddy = (ImageButton) findViewById(R.id.imgbtn_battle_buddy);
        txvMessage = (TextView) findViewById(R.id.txv_battle_message);

        imgButtonBuddy.setBackgroundResource(R.drawable.player_back);
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

        battle.addMessage("Wild " + battle.getEnemy().getNickname() + " appeared!",
                Message.NO_UPDATE);
        battle.addMessage("Go " + battle.getBuddy().getNickname() + "!", Message.UPDATE_BUDDY);
        messageState();

        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playSfx(BattleActivity.this, MusicHandler.SFX_SELECT);
                        if(battle.getState() == battle.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP0);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM0);
                            battle.setState(battle.STATE_USE_ITEM);
                            pokemonState(battle.STATE_USE_ITEM);
                        }
                        else if(battle.getState() == battle.STATE_USE_ITEM){
                            battle.setSelectedPokemon(battle.getPlayer().getPokemons()[0]);
                            messageState();
                        }
                    }
                }
        );

        btn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playSfx(BattleActivity.this, MusicHandler.SFX_SELECT);
                        if(battle.getState() == battle.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP1);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM1);
                            battle.setState(battle.STATE_USE_ITEM);
                            pokemonState(battle.STATE_USE_ITEM);
                        }
                        else if(battle.getState() == battle.STATE_USE_ITEM){
                            battle.setSelectedPokemon(battle.getPlayer().getPokemons()[1]);
                            messageState();
                        }
                    }
                }
        );

        btn3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playSfx(BattleActivity.this, MusicHandler.SFX_SELECT);
                        if(battle.getState() == battle.STATE_FIGHT){
                            battle.setPlayerDecision(battle.DECISION_ATTACK0);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP2);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM2);
                            pokemonState(battle.STATE_USE_ITEM);
                        }
                        else if(battle.getState() == battle.STATE_USE_ITEM){
                            battle.setSelectedPokemon(battle.getPlayer().getPokemons()[2]);
                            messageState();
                        }
                    }
                }
        );

        btn4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playSfx(BattleActivity.this, MusicHandler.SFX_SELECT);
                        if(battle.getState() == battle.STATE_FIGHT){
                            battle.setPlayerDecision(battle.DECISION_ATTACK1);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP3);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM3);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_USE_ITEM){
                            battle.setSelectedPokemon(battle.getPlayer().getPokemons()[3]);
                            messageState();
                        }
                    }
                }
        );

        btn5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playSfx(BattleActivity.this, MusicHandler.SFX_SELECT);
                        if(battle.getState() == battle.STATE_MAIN){
                            fightState();
                        }
                        else if(battle.getState() == battle.STATE_FIGHT){
                            battle.setPlayerDecision(battle.DECISION_ATTACK2);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP4);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM4);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_USE_ITEM){
                            battle.setSelectedPokemon(battle.getPlayer().getPokemons()[4]);
                            messageState();
                        }
                    }
                }
        );

        btn6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playSfx(BattleActivity.this, MusicHandler.SFX_SELECT);
                        if(battle.getState() == battle.STATE_MAIN){
                            pokemonState(battle.STATE_POKEMON);
                        }
                        else if(battle.getState() == battle.STATE_FIGHT){
                            battle.setPlayerDecision(battle.DECISION_ATTACK3);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP5);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM5);
                            messageState();
                        }
                        else if(battle.getState() == battle.STATE_USE_ITEM){
                            battle.setSelectedPokemon(battle.getPlayer().getPokemons()[5]);
                            messageState();
                        }
                    }
                }
        );

        btn7.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playSfx(BattleActivity.this, MusicHandler.SFX_SELECT);
                        if(battle.getState() == battle.STATE_MAIN){
                            bagState();
                        }
                        else if(battle.getState() == battle.STATE_FIGHT){
                            mainState();
                        }
                        else if(battle.getState() == battle.STATE_POKEMON){
                            mainState();
                        }
                        else if(battle.getState() == battle.STATE_BAG){
                            mainState();
                        }
                        else if(battle.getState() == battle.STATE_USE_ITEM){
                            bagState();
                        }
                    }
                }
        );

        btnRun.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playSfx(BattleActivity.this, MusicHandler.SFX_SELECT);
                        endBattle();
                    }
                }
        );

        btnAction.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playSfx(BattleActivity.this, MusicHandler.SFX_SELECT);
                        viewMessages();
                    }
                }
        );
    }

    private void viewMessages(){
        if(battle.getIndex() < battle.getMessages().size()){
            updateBattleScene();
            txvMessage.setText(battle.getMessages().get(battle.getIndex()).getMessage());
            if(battle.getIndex() == battle.getMessages().size() - 1
                    && battle.getState() == battle.STATE_MESSAGE_FIRST){

                battle.secondMove();
                battle.setState(battle.STATE_MESSAGE_LAST);
            }

            battle.setIndex(battle.getIndex() + 1);
        }
        else{
            battle.resetMessages();
            if(battle.isEnemyCaught()){
                endBattle();
            }
            else if(battle.isEnemyFainted()){
                endBattle();
            }
            else if(battle.isBuddyFainted()){
                if(battle.getPlayer().isPlayerDefeated()){
                    endBattle();
                }
                else{
                    txvMessage.setText("Swap next Pokemon?");
                    pokemonState(battle.STATE_POKEMON);
                }
            }
            else{
                mainState();
            }
        }
    }

    private void endBattle(){
        Intent MainActivityIntent = new Intent(BattleActivity.this, MainActivity.class);
        MainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(MainActivityIntent, 0);
        finish();
    }

    private void updateBattleScene(){
        if(battle.getMessages().get(battle.getIndex()).getUpdate() == Message.UPDATE_BUDDY){
            updateBuddyPokemon();
        }
        else if(battle.getMessages().get(battle.getIndex()).getUpdate() == Message.UPDATE_ENEMY){
            updateEnemyPokemon();
        }
        else if(battle.getMessages().get(battle.getIndex()).getUpdate() == Message.UPDATE_BUDDY_EXP){
            updateBuddyExp();
        }
        else if(battle.getMessages().get(battle.getIndex()).getUpdate() == Message.UPDATE_ENEMY_HP){
            updateEnemyHp();
        }
        else if(battle.getMessages().get(battle.getIndex()).getUpdate() == Message.UPDATE_BUDDY_HP){
            updateBuddyHp();
        }
        else if(battle.getMessages().get(battle.getIndex()).getUpdate() == Message.UPDATE_CATCH){
            updateCatch();
        }
    }

    public void updateCatch(){
        imgButtonEnemy.setImageResource(battle.getSelectedItem().getImageSprite());
    }

    private void updateBuddyPokemon(){
        txvBuddyName.setText(battle.getBuddy().getNickname());
        txvBuddyLevel.setText("Lv" + battle.getBuddy().getLevel());
        imgButtonBuddy.setBackgroundResource(battle.getBuddy().getDexData().getBackImage());
        updateBuddyHp();
        updateBuddyExp();
    }

    private void updateEnemyPokemon(){
        txvEnemyName.setText(battle.getEnemy().getNickname());
        txvEnemyLevel.setText("Lv" + battle.getEnemy().getLevel());
        imgButtonEnemy.setImageResource(battle.getEnemy().getDexData().getMainImage());
        updateEnemyHp();
    }

    private void updateEnemyHp(){
        barEnemyHP.setMax(battle.getEnemy().getHP());
        barEnemyHP.setProgress(battle.getEnemy().getCurrentHP());
    }

    private void updateBuddyHp(){
        barBuddyHP.setMax(battle.getBuddy().getHP());
        barBuddyHP.setProgress(battle.getBuddy().getCurrentHP());
        txvBuddyHP.setText(battle.getBuddy().getCurrentHP() + "/" + battle.getBuddy().getHP());
    }

    private void updateBuddyExp(){
        barBuddyEXP.setMax(battle.getBuddy().getExperienceNeeded());
        barBuddyEXP.setProgress(battle.getBuddy().getCurrentExp());
    }

    private void messageState(){
        battle.setState(battle.STATE_MESSAGE_FIRST);
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

        battle.setIndex(0);
        battle.firstMove();
        viewMessages();
    }

    private void mainState(){
        battle.setState(battle.STATE_MAIN);
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
        PokemonGoApp app = (PokemonGoApp) getApplication();
        battle.setState(battle.STATE_FIGHT);
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

        btn3.setText(battle.getBuddy().getMoves()[0].getButtonString());
        btn4.setText(battle.getBuddy().getMoves()[1].getButtonString());
        btn5.setText(battle.getBuddy().getMoves()[2].getButtonString());
        btn6.setText(battle.getBuddy().getMoves()[3].getButtonString());
        btn7.setText("BACK");

        btn3.setBackgroundColor(battle.getBuddy().getMoves()[0].getType().getColor());
        btn4.setBackgroundColor(battle.getBuddy().getMoves()[1].getType().getColor());
        btn5.setBackgroundColor(battle.getBuddy().getMoves()[2].getType().getColor());
        btn6.setBackgroundColor(battle.getBuddy().getMoves()[3].getType().getColor());
        btn7.setBackgroundColor(BACK_COLOR);
    }

    private void pokemonState(int state){
        battle.setState(state);
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
        btn7.setVisibility(View.INVISIBLE);
        btn7.setVisibility(View.VISIBLE);

        btnRun.setVisibility(View.VISIBLE);
        btnAction.setVisibility(View.INVISIBLE);

        btn1.setText(battle.getPlayer().getPokemons()[0].getButtonString());
        btn2.setText(battle.getPlayer().getPokemons()[1].getButtonString());
        btn3.setText(battle.getPlayer().getPokemons()[2].getButtonString());
        btn4.setText(battle.getPlayer().getPokemons()[3].getButtonString());
        btn5.setText(battle.getPlayer().getPokemons()[4].getButtonString());
        btn6.setText(battle.getPlayer().getPokemons()[5].getButtonString());
        btn7.setText("BACK");

        setPokemonButtonColor(0, btn1);
        setPokemonButtonColor(1, btn2);
        setPokemonButtonColor(2, btn3);
        setPokemonButtonColor(3, btn4);
        setPokemonButtonColor(4, btn5);
        setPokemonButtonColor(5, btn6);
        btn7.setBackgroundColor(BACK_COLOR);
    }

    private void setPokemonButtonColor(int profileIndex, Button button){
        if(battle.getPlayer().isPokemonFainted(profileIndex) &&
                !battle.getPlayer().getPokemons()[profileIndex].isEmpty()){
            button.setBackgroundColor(DEAD_COLOR);
        }
        else{
            button.setBackgroundColor(POKEMON_COLOR);
        }
    }

    private void bagState(){
        battle.setState(battle.STATE_BAG);

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

        btn1.setText("Potion x" + battle.getPlayer().getPotions().getQuantity());
        btn2.setText("Super Potion x" + battle.getPlayer().getSuperPotions().getQuantity());
        btn3.setText("Max Revive x" + battle.getPlayer().getMaxRevives().getQuantity());
        btn4.setText("Poke Ball x" + battle.getPlayer().getPokeBall().getQuantity());
        btn5.setText("Great Ball x" + battle.getPlayer().getGreatBall().getQuantity());
        btn6.setText("Ultra Ball x" + battle.getPlayer().getUltraBall().getQuantity());
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

    @Override
    protected void onResume() {
        super.onResume();
        if(music == null){
            music.loopMusic(this, MusicHandler.MUSIC_BATTLE);
        }
        if(!music.getMusicPlayer().isPlaying()) {
            music.getMusicPlayer().start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.getMusicPlayer().pause();
    }
}
