package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BattleActivity extends AppCompatActivity {

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

    private ImageView imgIconBtn1;
    private ImageView imgIconBtn2;
    private ImageView imgIconBtn3;
    private ImageView imgIconBtn4;
    private ImageView imgIconBtn5;
    private ImageView imgIconBtn6;

    private ProgressBar barBtn1;
    private ProgressBar barBtn2;
    private ProgressBar barBtn3;
    private ProgressBar barBtn4;
    private ProgressBar barBtn5;
    private ProgressBar barBtn6;

    private ProgressBar barEnemyHP;
    private ProgressBar barBuddyHP;
    private ProgressBar barBuddyEXP;
    private TextView txvBuddyHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        final PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setFontForContainer((RelativeLayout)findViewById(R.id.battle_group), "generation6.ttf");

        music = new MusicHandler();
        music.initMusic(BattleActivity.this, MusicHandler.MUSIC_BATTLE);
        music.playMusic(app.getMusicSwitch());
        app.getMusicHandler().initButtonSfx(this);

        //TODO BALANCING
        battle = new Battle(app.getPlayer().getBuddy(),
                new PokemonProfile(app.getSpawnCount(),
                        app.getPokemon(app.getCurrentGoal().getTitle()), app.getPlayer().getAverageLevel()));
        battle.setPlayer(app.getPlayer());
        battle.setTypeChart(app.getAllTypes());
        battle.getEnemy().getMoves()[0] = new Move(app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size())));
        battle.getEnemy().getMoves()[1] = new Move(app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size())));
        battle.getEnemy().getMoves()[2] = new Move(app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size())));
        battle.getEnemy().getMoves()[3] = new Move(app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size())));

        txvEnemyName = (TextView) findViewById(R.id.txv_battle_enemy_name);
        txvBuddyName = (TextView) findViewById(R.id.txv_battle_buddy_name);
        txvEnemyLevel = (TextView) findViewById(R.id.txv_battle_enemy_level);
        txvBuddyLevel = (TextView) findViewById(R.id.txv_battle_buddy_level);
        imgButtonEnemy = (ImageButton) findViewById(R.id.imgbtn_battle_enemy);
        imgButtonBuddy = (ImageButton) findViewById(R.id.imgbtn_battle_buddy);
        txvMessage = (TextView) findViewById(R.id.txv_battle_message);

        imgButtonEnemy.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        imgButtonBuddy.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);

        btnAction = (Button) findViewById(R.id.btn_battle_action);
        btnAction.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        btn1 = (Button) findViewById(R.id.btn_battle_option1);
        btn2 = (Button) findViewById(R.id.btn_battle_option2);
        btn3 = (Button) findViewById(R.id.btn_battle_option3);
        btn4 = (Button) findViewById(R.id.btn_battle_option4);
        btn5 = (Button) findViewById(R.id.btn_battle_option5);
        btn6 = (Button) findViewById(R.id.btn_battle_option6);
        btn7 = (Button) findViewById(R.id.btn_battle_option7);
        btnRun = (Button) findViewById(R.id.btn_battle_run);

        btn1.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        btn2.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        btn3.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        btn4.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        btn5.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        btn6.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        btnRun.setBackgroundColor(PokemonGoApp.RUN_COLOR);

        barEnemyHP = (ProgressBar) findViewById(R.id.bar_battle_enemy_hp);
        barBuddyHP = (ProgressBar) findViewById(R.id.bar_battle_buddy_hp);
        barBuddyEXP = (ProgressBar) findViewById(R.id.bar_battle_buddy_exp);
        txvBuddyHP = (TextView) findViewById(R.id.txv_battle_buddy_hp);

        imgIconBtn1 = (ImageView) findViewById(R.id.img_battle_btn1);
        imgIconBtn2 = (ImageView) findViewById(R.id.img_battle_btn2);
        imgIconBtn3 = (ImageView) findViewById(R.id.img_battle_btn3);
        imgIconBtn4 = (ImageView) findViewById(R.id.img_battle_btn4);
        imgIconBtn5 = (ImageView) findViewById(R.id.img_battle_btn5);
        imgIconBtn6 = (ImageView) findViewById(R.id.img_battle_btn6);

        barBtn1 = (ProgressBar) findViewById(R.id.bar_battle_btn1);
        barBtn2 = (ProgressBar) findViewById(R.id.bar_battle_btn2);
        barBtn3 = (ProgressBar) findViewById(R.id.bar_battle_btn3);
        barBtn4 = (ProgressBar) findViewById(R.id.bar_battle_btn4);
        barBtn5 = (ProgressBar) findViewById(R.id.bar_battle_btn5);
        barBtn6 = (ProgressBar) findViewById(R.id.bar_battle_btn6);

        barEnemyHP.getProgressDrawable().setColorFilter(
                PokemonGoApp.BAR_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
        barBuddyHP.getProgressDrawable().setColorFilter(
                PokemonGoApp.BAR_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
        barBuddyEXP.getProgressDrawable().setColorFilter(
                PokemonGoApp.RUN_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);

        updateEnemyPokemon();
        imgButtonBuddy.setBackgroundResource(R.drawable.player_back);
        battle.addMessage("Wild " + battle.getEnemy().getNickname() + " appeared!",
                Message.UPDATE_ENEMY);
        battle.addMessage("Go " + battle.getBuddy().getNickname() + "!", Message.UPDATE_BUDDY);
        messageState();

        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(battle.getState() == PokemonGoApp.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP0);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM0);
                            pokemonState(PokemonGoApp.STATE_USE_ITEM);
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_USE_ITEM){
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
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(battle.getState() == PokemonGoApp.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP1);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM1);
                            pokemonState(PokemonGoApp.STATE_USE_ITEM);
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_USE_ITEM){
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
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(battle.getState() == PokemonGoApp.STATE_FIGHT){
                            battle.setPlayerDecision(battle.DECISION_ATTACK0);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP2);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM2);
                            pokemonState(PokemonGoApp.STATE_USE_ITEM);
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_USE_ITEM){
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
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(battle.getState() == PokemonGoApp.STATE_FIGHT){
                            battle.setPlayerDecision(battle.DECISION_ATTACK1);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP3);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM3);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_USE_ITEM){
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
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(battle.getState() == PokemonGoApp.STATE_MAIN){
                            fightState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_FIGHT){
                            battle.setPlayerDecision(battle.DECISION_ATTACK2);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP4);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM4);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_USE_ITEM){
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
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(battle.getState() == PokemonGoApp.STATE_MAIN){
                            pokemonState(PokemonGoApp.STATE_POKEMON);
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_FIGHT){
                            battle.setPlayerDecision(battle.DECISION_ATTACK3);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_POKEMON){
                            battle.setPlayerDecision(battle.DECISION_SWAP5);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_BAG){
                            battle.setPlayerDecision(battle.DECISION_ITEM5);
                            messageState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_USE_ITEM){
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
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(battle.getState() == PokemonGoApp.STATE_MAIN){
                            bagState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_FIGHT){
                            mainState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_POKEMON){
                            mainState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_BAG){
                            mainState();
                        }
                        else if(battle.getState() == PokemonGoApp.STATE_USE_ITEM){
                            bagState();
                        }
                    }
                }
        );

        btnRun.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        battle.setPlayerDecision(battle.DECISION_RUN);
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
            updateBattleScene();
            txvMessage.setText(battle.getMessages().get(battle.getIndex()).getMessage());
            if(battle.getIndex() == battle.getMessages().size() - 1
                    && battle.getState() == PokemonGoApp.STATE_MESSAGE_FIRST){
                battle.secondMove();
                battle.setState(PokemonGoApp.STATE_MESSAGE_LAST);
            }

            battle.setIndex(battle.getIndex() + 1);
        }
        else{
            if(battle.getPlayerDecision() == battle.DECISION_RUN){
                endBattle();
            }
            else{
                battle.newTurn();
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
    }

    private void endBattle(){
        Intent mainActivityIntent = new Intent(BattleActivity.this, MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(mainActivityIntent, 0);
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
        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.getMusicHandler().playSfx(this, battle.getBuddy().getDexData().getSound(), app.getSFXSwitch());
        txvBuddyName.setText(battle.getBuddy().getNickname());
        txvBuddyLevel.setText("Lv" + battle.getBuddy().getLevel());
        imgButtonBuddy.setBackgroundResource(battle.getBuddy().getDexData().getBackImage());
        updateBuddyHp();
        updateBuddyExp();
    }

    private void updateEnemyPokemon(){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.getMusicHandler().playSfx(this, battle.getEnemy().getDexData().getSound(), app.getSFXSwitch());
        txvEnemyName.setText(battle.getEnemy().getNickname());
        txvEnemyLevel.setText("Lv" + battle.getEnemy().getLevel());
        imgButtonEnemy.setImageResource(battle.getEnemy().getDexData().getMainImage());
        updateEnemyHp();
    }

    private void updateEnemyHp(){
        barEnemyHP.setMax(battle.getEnemy().getHP());
        barEnemyHP.setProgress(battle.getEnemy().getCurrentHP());
        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.updateHpBarColor(battle.getEnemy().getCurrentHP(), battle.getEnemy().getHP(), barEnemyHP);
    }

    private void updateBuddyHp(){
        barBuddyHP.setMax(battle.getBuddy().getHP());
        barBuddyHP.setProgress(battle.getBuddy().getCurrentHP());
        txvBuddyHP.setText(battle.getBuddy().getCurrentHP() + "/" + battle.getBuddy().getHP());
        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.updateHpBarColor(battle.getBuddy().getCurrentHP(), battle.getBuddy().getHP(), barBuddyHP);
    }

    private void updateBuddyExp(){
        barBuddyEXP.setMax(battle.getBuddy().getExperienceNeeded());
        barBuddyEXP.setProgress(battle.getBuddy().getCurrentExp());
    }

    private void messageState(){
        battle.setState(PokemonGoApp.STATE_MESSAGE_FIRST);
        setOptionIconsVisibilty(View.INVISIBLE);
        setOptionBarsVisibility(View.INVISIBLE);

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
        setOptionBarsVisibility(View.INVISIBLE);
        setOptionIconsVisibilty(View.INVISIBLE);
        battle.setState(PokemonGoApp.STATE_MAIN);
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

        btn5.setBackgroundColor(PokemonGoApp.FIGHT_COLOR);
        btn6.setBackgroundColor(PokemonGoApp.POKEMON_COLOR);
        btn7.setBackgroundColor(PokemonGoApp.BAG_COLOR);
        battle.setEnemyDecision(PokemonGoApp.getIntegerRNG(4) + 1);
        txvMessage.setText("What will " + battle.getBuddy().getNickname() + " do?");
    }

    private void setMoveButton(Button btn, Move move, ImageView icon){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        btn.setClickable(!move.isEmpty());
        btn.setText(move.getButtonString());
        if(!move.isEmpty()){
            btn.setVisibility(View.VISIBLE);
            app.setButtonBorder(btn, move.getType().getColor());
            icon.setVisibility(View.VISIBLE);
            icon.setImageResource(move.getType().getIcon());
        }
        else{
            btn.setVisibility(View.INVISIBLE);
            icon.setVisibility(View.INVISIBLE);
        }
    }

    private void fightState(){
        setOptionBarsVisibility(View.INVISIBLE);
        setOptionIconsVisibilty(View.INVISIBLE);
        setAllOptionsColor(PokemonGoApp.TRANSPARENT_COLOR);

        PokemonGoApp app = (PokemonGoApp) getApplication();

        battle.setState(PokemonGoApp.STATE_FIGHT);
        btn1.setClickable(false);
        btn2.setClickable(false);
        setMoveButton(btn3, battle.getBuddy().getMoves()[0], imgIconBtn3);
        setMoveButton(btn4, battle.getBuddy().getMoves()[1], imgIconBtn4);
        setMoveButton(btn5, battle.getBuddy().getMoves()[2], imgIconBtn5);
        setMoveButton(btn6, battle.getBuddy().getMoves()[3], imgIconBtn6);

        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);

        app.setAsBackButton(btn7);
        enableButton(btnRun);
        disableButton(btnAction);
    }

    private void pokemonState(int state){
        battle.setState(state);
        PokemonGoApp app = (PokemonGoApp) getApplication();
        setAllOptionsColor(PokemonGoApp.TRANSPARENT_COLOR);
        app.setPokemonButton(btn1, battle.getPlayer().getPokemons()[0], barBtn1, imgIconBtn1);
        app.setPokemonButton(btn2, battle.getPlayer().getPokemons()[1], barBtn2, imgIconBtn2);
        app.setPokemonButton(btn3, battle.getPlayer().getPokemons()[2], barBtn3, imgIconBtn3);
        app.setPokemonButton(btn4, battle.getPlayer().getPokemons()[3], barBtn4, imgIconBtn4);
        app.setPokemonButton(btn5, battle.getPlayer().getPokemons()[4], barBtn5, imgIconBtn5);
        app.setPokemonButton(btn6, battle.getPlayer().getPokemons()[5], barBtn6, imgIconBtn6);

        app.setAsBackButton(btn7);
        btn7.setClickable(!(battle.getBuddy().getCurrentHP() <= 0));

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

    private void enableAllOptions(){
        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn4.setClickable(true);
        btn5.setClickable(true);
        btn6.setClickable(true);
        showAllOptions();
    }

    private void showAllOptions(){
        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn4.setVisibility(View.VISIBLE);
        btn5.setVisibility(View.VISIBLE);
        btn6.setVisibility(View.VISIBLE);
    }

    private void setOptionIconsVisibilty(int view){
        imgIconBtn1.setVisibility(view);
        imgIconBtn2.setVisibility(view);
        imgIconBtn3.setVisibility(view);
        imgIconBtn4.setVisibility(view);
        imgIconBtn5.setVisibility(view);
        imgIconBtn6.setVisibility(view);
    }

    private void setAllOptionsColor(int color){
        btn1.setBackgroundColor(color);
        btn2.setBackgroundColor(color);
        btn3.setBackgroundColor(color);
        btn4.setBackgroundColor(color);
        btn5.setBackgroundColor(color);
        btn6.setBackgroundColor(color);
    }

    private void setOptionBarsVisibility(int view){
        barBtn1.setVisibility(view);
        barBtn2.setVisibility(view);
        barBtn3.setVisibility(view);
        barBtn4.setVisibility(view);
        barBtn5.setVisibility(view);
        barBtn6.setVisibility(view);
    }

    private void bagState(){
        setOptionBarsVisibility(View.INVISIBLE);
        setOptionIconsVisibilty(View.VISIBLE);
        battle.setState(PokemonGoApp.STATE_BAG);
        enableAllOptions();

        enableButton(btnRun);
        disableButton(btnAction);

        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setBagButton(btn1, battle.getPlayer().getBag()[0], imgIconBtn1);
        app.setBagButton(btn2, battle.getPlayer().getBag()[1], imgIconBtn2);
        app.setBagButton(btn3, battle.getPlayer().getBag()[2], imgIconBtn3);
        app.setBagButton(btn4, battle.getPlayer().getBag()[3], imgIconBtn4);
        app.setBagButton(btn5, battle.getPlayer().getBag()[4], imgIconBtn5);
        app.setBagButton(btn6, battle.getPlayer().getBag()[5], imgIconBtn6);
        app.setAsBackButton(btn7);
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
