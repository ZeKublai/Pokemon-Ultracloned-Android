package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BattleActivity extends AppCompatActivity {
    public static int FIGHT_COLOR = Color.argb(255, 238, 41, 41);
    public static int POKEMON_COLOR = Color.argb(255, 44, 224, 49);
    public static int BAG_COLOR = Color.argb(255, 252, 190, 26);
    public static int RUN_COLOR = Color.argb(255, 43, 154, 255);
    public static int BACK_COLOR = Color.argb(255, 3, 111, 114);
    public static int TRANSPARENT_COLOR = Color.argb(0, 0, 0, 0);

    private Pokemon enemyPokemon;
    private Pokemon buddyPokemon;
    private Player player;
    private Battle battle;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btnAction;
    Button btnRun;
    TextView txvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        final PokemonGoApp app = (PokemonGoApp) getApplication();
        player = app.getPlayer();
        enemyPokemon = app.getPokemon(app.getCurrentGoal().getTitle());
        buddyPokemon = app.getAllPokemons().get(app.getPlayer().getPokemons()[0].getDexNumber() - 1);
        battle = new Battle(app.getPlayer().getPokemons()[0], new PokemonProfile(app.getSpawnCount(), enemyPokemon));

        TextView txvEnemyName = (TextView) findViewById(R.id.txv_battle_enemy_name);
        TextView txvBuddyName = (TextView) findViewById(R.id.txv_battle_buddy_name);
        txvMessage = (TextView) findViewById(R.id.txv_battle_message);
        ImageButton imgButtonEnemy = (ImageButton) findViewById(R.id.imgbtn_battle_enemy);
        ImageButton imgButtonBuddy = (ImageButton) findViewById(R.id.imgbtn_battle_buddy);

        txvEnemyName.setText(battle.getEnemy().getNickname());
        txvBuddyName.setText(battle.getBuddy().getNickname());
        imgButtonEnemy.setImageResource(enemyPokemon.getFrontImage());
        imgButtonBuddy.setImageResource(buddyPokemon.getBackImage());
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

        ProgressBar barEnemyHP = (ProgressBar) findViewById(R.id.bar_battle_enemy_hp);
        ProgressBar barBuddyHP = (ProgressBar) findViewById(R.id.bar_battle_buddy_hp);
        ProgressBar barBuddyEXP = (ProgressBar) findViewById(R.id.bar_battle_buddy_exp);
        TextView txvBuddyHP = (TextView) findViewById(R.id.txv_battle_buddy_hp);

        barEnemyHP.setMax(battle.getEnemy().getHP(enemyPokemon.getBase().getHP()));
        barBuddyHP.setMax(battle.getBuddy().getHP(buddyPokemon.getBase().getHP()));
        barBuddyEXP.setMax(battle.getBuddy().getExperienceNeeded(battle.getBuddy().getLevel()));

        barEnemyHP.getProgressDrawable().setColorFilter(
                Color.argb(255, 0, 225, 231),android.graphics.PorterDuff.Mode.SRC_IN);
        barBuddyHP.getProgressDrawable().setColorFilter(
                Color.argb(255, 0, 225, 231),android.graphics.PorterDuff.Mode.SRC_IN);
        barBuddyEXP.getProgressDrawable().setColorFilter(
                RUN_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);

        barEnemyHP.setProgress(battle.getEnemy().getCurrentHP());
        barBuddyHP.setProgress(battle.getBuddy().getCurrentHP() - 40);
        barBuddyEXP.setProgress(battle.getBuddy().getCurrentExp() -
                battle.getBuddy().getExperienceNeeded(battle.getBuddy().getLevel() - 1)+100);

        battle.getMessages().add("Wild " + enemyPokemon.getName() + " appeared!");
        battle.getMessages().add("Go " + buddyPokemon.getName() + "!");
        messageState();

        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        btn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        btn3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(battle.getPhase() == battle.MAIN_STATE){

                        }
                    }
                }
        );

        btn4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(battle.getPhase() == battle.MAIN_STATE){

                        }
                        else if(battle.getPhase() == battle.FIGHT_STATE){

                        }
                    }
                }
        );

        btn5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(battle.getPhase() == battle.MAIN_STATE){
                            fightState();
                        }
                        else if(battle.getPhase() == battle.FIGHT_STATE){

                        }
                    }
                }
        );

        btn6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(battle.getPhase() == battle.MAIN_STATE){
                            pokemonState();
                        }
                        else if(battle.getPhase() == battle.FIGHT_STATE){

                        }
                    }
                }
        );

        btn7.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                        if(battle.getPhase() == battle.MESSAGE_STATE){
                            if(battle.getCurrentMessage() < battle.getMessages().size() - 1){
                                battle.setCurrentMessage(battle.getCurrentMessage() + 1);
                                txvMessage.setText(battle.getMessages().
                                        get(battle.getCurrentMessage()));
                            }
                            else{
                                mainState();
                            }
                        }
                    }
                }
        );
    }

    private void messageState(){
        battle.setPhase(battle.MESSAGE_STATE);
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
        txvMessage.setText(battle.getMessages().get(0));
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

        txvMessage.setText("What will " + battle.getBuddy().getNickname() + " do?");
    }

    private void fightState(){
        battle.setPhase(battle.FIGHT_STATE);
        btn1.setClickable(false);
        btn2.setClickable(false);
        btn3.setClickable(true);
        btn4.setClickable(true);
        btn5.setClickable(true);
        btn6.setClickable(true);
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
        btn1.setClickable(player.getPokemons()[0].getDexNumber() != 0);
        btn2.setClickable(player.getPokemons()[1].getDexNumber() != 0);
        btn3.setClickable(player.getPokemons()[2].getDexNumber() != 0);
        btn4.setClickable(player.getPokemons()[3].getDexNumber() != 0);
        btn5.setClickable(player.getPokemons()[4].getDexNumber() != 0);
        btn6.setClickable(player.getPokemons()[5].getDexNumber() != 0);
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

        btn1.setText(player.getPokemons()[0].getNickname());
        btn2.setText(player.getPokemons()[1].getNickname());
        btn3.setText(player.getPokemons()[2].getNickname());
        btn4.setText(player.getPokemons()[3].getNickname());
        btn5.setText(player.getPokemons()[4].getNickname());
        btn6.setText(player.getPokemons()[5].getNickname());
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

        btn1.setClickable(player.getPotions() > 0);
        btn2.setClickable(player.getSuperPotions() > 0);
        btn3.setClickable(player.getMaxRevives() > 0);
        btn4.setClickable(player.getPokeBall() > 0);
        btn5.setClickable(player.getGreatBall() > 0);
        btn6.setClickable(player.getUltraBall() > 0);
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

        btn1.setText("Potion x" + player.getPotions());
        btn2.setText("Super Potion x" + player.getSuperPotions());
        btn3.setText("Max Revive x" + player.getMaxRevives());
        btn4.setText("Poke Ball x" + player.getPokeBall());
        btn5.setText("Great Ball x" + player.getGreatBall());
        btn6.setText("Ultra Ball x" + player.getUltraBall());
        btn7.setText("BACK");

        btn1.setBackgroundColor(BAG_COLOR);
        btn2.setBackgroundColor(BAG_COLOR);
        btn3.setBackgroundColor(BAG_COLOR);
        btn4.setBackgroundColor(BAG_COLOR);
        btn5.setBackgroundColor(BAG_COLOR);
        btn6.setBackgroundColor(BAG_COLOR);
        btn7.setBackgroundColor(BACK_COLOR);

    }
}
