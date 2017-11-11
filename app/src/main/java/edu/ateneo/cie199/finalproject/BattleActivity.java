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

import java.util.ArrayList;

import static java.lang.Math.floor;

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

        PokemonGoApp app = (PokemonGoApp) getApplication();
        player = app.getPlayer();
        enemyPokemon = app.getPokemon(app.getCurrentGoal().getTitle());
        buddyPokemon = app.getPokemon(app.getPlayer().getPokemons()[0].getDexNumber());
        battle = new Battle(app.getPlayer().getPokemons()[0], new PokemonProfile(app.getSpawnCount(), enemyPokemon));
        battle.getEnemy().getMoves()[0] = app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size()));
        battle.getEnemy().getMoves()[1] = app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size()));
        battle.getEnemy().getMoves()[2] = app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size()));
        battle.getEnemy().getMoves()[3] = app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size()));

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

        battle.getMessages().add("Wild " + enemyPokemon.getName() + " appeared!");
        battle.getMessages().add("Go " + buddyPokemon.getName() + "!");
        messageState();

        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(0);
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
                        if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(1);
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
                        if(battle.getPhase() == battle.FIGHT_STATE){
                            if(buddyFirst()) {
                                attack(0, buddyPokemon, enemyPokemon, battle.getBuddy(),
                                        battle.getEnemy());
                                attack(2, enemyPokemon, buddyPokemon, battle.getEnemy(),
                                        battle.getBuddy());
                            }
                            else{
                                attack(2, enemyPokemon, buddyPokemon, battle.getEnemy(),
                                        battle.getBuddy());
                                attack(0, buddyPokemon, enemyPokemon, battle.getBuddy(),
                                        battle.getEnemy());
                            }
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(2);
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
                        if(battle.getPhase() == battle.FIGHT_STATE){
                            if(buddyFirst()) {
                                attack(1, buddyPokemon, enemyPokemon, battle.getBuddy(),
                                        battle.getEnemy());
                                attack(2, enemyPokemon, buddyPokemon, battle.getEnemy(),
                                        battle.getBuddy());
                            }
                            else{
                                attack(2, enemyPokemon, buddyPokemon, battle.getEnemy(),
                                        battle.getBuddy());
                                attack(1, buddyPokemon, enemyPokemon, battle.getBuddy(),
                                        battle.getEnemy());
                            }
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(3);
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
                        if(battle.getPhase() == battle.MAIN_STATE){
                            fightState();
                        }
                        else if(battle.getPhase() == battle.FIGHT_STATE){
                            if(buddyFirst()) {
                                attack(2, buddyPokemon, enemyPokemon, battle.getBuddy(),
                                        battle.getEnemy());
                                attack(2, enemyPokemon, buddyPokemon, battle.getEnemy(),
                                        battle.getBuddy());
                            }
                            else{
                                attack(2, enemyPokemon, buddyPokemon, battle.getEnemy(),
                                        battle.getBuddy());
                                attack(2, buddyPokemon, enemyPokemon, battle.getBuddy(),
                                        battle.getEnemy());
                            }
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(4);
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
                        if(battle.getPhase() == battle.MAIN_STATE){
                            pokemonState();
                        }
                        else if(battle.getPhase() == battle.FIGHT_STATE){
                            if(buddyFirst()) {
                                attack(3, buddyPokemon, enemyPokemon, battle.getBuddy(),
                                        battle.getEnemy());
                                attack(2, enemyPokemon, buddyPokemon, battle.getEnemy(),
                                        battle.getBuddy());
                            }
                            else{
                                attack(2, enemyPokemon, buddyPokemon, battle.getEnemy(),
                                        battle.getBuddy());
                                attack(3, buddyPokemon, enemyPokemon, battle.getBuddy(),
                                        battle.getEnemy());
                            }
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(5);
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
                        viewMessages();
                        updateBuddyPokemon();
                        updateEnemyPokemon();
                    }
                }
        );
    }

    private void viewMessages(){
        if(battle.getCurrentMessage() < battle.getMessages().size() - 1){
            battle.setCurrentMessage(battle.getCurrentMessage() + 1);
            txvMessage.setText(battle.getMessages().get(battle.getCurrentMessage()));
        }
        else{
            battle.getMessages().clear();
            battle.setCurrentMessage(0);
            mainState();
        }
    }

    private void attack(int moveIndex, Pokemon attacker, Pokemon defender,
                        PokemonProfile attackerProfile, PokemonProfile defenderProfile){
        if(attackerProfile.getMoves()[moveIndex].getCurrentPP() > 0){
            battle.getMessages().add(attackerProfile.getNickname()
                    + " used " + attackerProfile.getMoves()[moveIndex].getName() + "!");
            PokemonGoApp app = (PokemonGoApp) getApplication();
            if(app.getIntegerRNG(100) < attackerProfile.getMoves()[moveIndex].getAccuracy()){
                int attackStat = 0;
                int defenseStat = 0;
                if(attackerProfile.getMoves()[moveIndex].getCategory() == Move.PHYSICAL){
                    attackStat = attackerProfile.getAttack(attacker.getBase().getAttack());
                    defenseStat = defenderProfile.getDefense(defender.getBase().getDefense());
                }
                else if(attackerProfile.getMoves()[moveIndex].getCategory() == Move.SPECIAL){
                    attackStat = attackerProfile.getSpAttack(attacker.getBase().getSpAttack());
                    defenseStat = defenderProfile.getSpDefense(defender.getBase().getSpDefense());
                }

                double damage = floor(floor(floor(2 * attackerProfile.getLevel() / 5 + 2) *
                        attackStat * attackerProfile.getMoves()[moveIndex].getPower() /
                        defenseStat) / 50) + 2;

                double typeMultiplier = app.getAllTypes().get(attackerProfile.
                        getMoves()[moveIndex].getType()).getMultiplier()[defender.getType()];

                if(typeMultiplier == 2){
                    battle.getMessages().add("It's super effective!");
                }
                else if(typeMultiplier == 0.5){
                    battle.getMessages().add("It's not very effective...");
                }
                else if(typeMultiplier == 0){
                    battle.getMessages().add("It doesn't affect foe "
                            + defenderProfile.getNickname() + "...");
                }

                damage = damage * typeMultiplier;

                if(app.getIntegerRNG(16) > 1){
                    damage = damage * 2;
                    battle.getMessages().add("A critical hit!");
                }

                defenderProfile.setCurrentHP(defenderProfile.getCurrentHP() - (int)(damage));
                updateEnemyPokemon();
                updateBuddyPokemon();
                if(checkVictory()){
                    battleResult();
                }
            }
            else{
                battle.getMessages().add(attackerProfile.getNickname() + "'s attack missed!");
            }
        }
        messageState();
    }

    private void battleResult(){
        battle.getMessages().add(battle.getEnemy().getNickname() + " fainted");
        battle.getMessages().add(battle.getBuddy().getNickname() + " gained "
                + battle.getEnemy().getLevel()*battle.getBuddy().getLevel()
                + " EXP points!");
        battle.getBuddy().setCurrentExp(battle.getBuddy().getCurrentExp()
                + battle.getEnemy().getLevel()*battle.getBuddy().getLevel());

        if(battle.getBuddy().getCurrentExp() >=
                battle.getBuddy().getExperienceNeeded()){
            battle.getBuddy().setCurrentExp(battle.getBuddy().getCurrentExp()
                    - battle.getBuddy().getExperienceNeeded());
            battle.getBuddy().setLevel(battle.getBuddy().getLevel() + 1);
            battle.getMessages().add(battle.getEnemy().getNickname() + " grew to LV. "
                    + battle.getBuddy().getLevel() + "!");
        }
    }

    private boolean checkVictory(){
        if(battle.getEnemy().getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean buddyFirst(){
        if(battle.getBuddy().getSpeed(buddyPokemon.getBase().getSpeed()) >
                battle.getEnemy().getSpeed(enemyPokemon.getBase().getSpeed())){
            return true;
        }
        else{
            return false;
        }
    }

    private void switchBuddyPokemon(int pokemonIndex){
        if(!battle.getBuddy().equals(player.getPokemons()[pokemonIndex])){
            battle.getMessages().add("Good job, " + battle.getBuddy().getNickname()
                    + "!");
            PokemonGoApp app = (PokemonGoApp) getApplication();
            buddyPokemon = app.getPokemon(player.getPokemons()[pokemonIndex].getDexNumber());
            battle.setBuddy(player.getPokemons()[pokemonIndex]);
            battle.getMessages().add("Go " + buddyPokemon.getName() + "!");
        }
    }

    private void updateBuddyPokemon(){
        txvBuddyName.setText(battle.getBuddy().getNickname());
        imgButtonBuddy.setImageResource(buddyPokemon.getBackImage());
        barBuddyHP.setMax(battle.getBuddy().getHP(buddyPokemon.getBase().getHP()));
        barBuddyEXP.setMax(battle.getBuddy().getExperienceNeeded());
        barBuddyHP.setProgress(battle.getBuddy().getCurrentHP());
        barBuddyEXP.setProgress(battle.getBuddy().getCurrentExp());
        txvBuddyHP.setText(battle.getBuddy().getCurrentHP() + "/" +
                battle.getBuddy().getHP(buddyPokemon.getBase().getHP()));
    }

    private void updateEnemyPokemon(){
        txvEnemyName.setText(battle.getEnemy().getNickname());
        imgButtonEnemy.setImageResource(enemyPokemon.getFrontImage());
        barEnemyHP.setMax(battle.getEnemy().getHP(enemyPokemon.getBase().getHP()));
        barEnemyHP.setProgress(battle.getEnemy().getCurrentHP());
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
        btn1.setClickable(!player.getPokemons()[0].isEmpty());
        btn2.setClickable(!player.getPokemons()[1].isEmpty());
        btn3.setClickable(!player.getPokemons()[2].isEmpty());
        btn4.setClickable(!player.getPokemons()[3].isEmpty());
        btn5.setClickable(!player.getPokemons()[4].isEmpty());
        btn6.setClickable(!player.getPokemons()[5].isEmpty());
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
