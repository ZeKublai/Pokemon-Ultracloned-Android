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

import static java.lang.Math.floor;

public class CatchActivity extends AppCompatActivity {
    public static int FIGHT_COLOR = Color.argb(255, 238, 41, 41);
    public static int POKEMON_COLOR = Color.argb(255, 44, 224, 49);
    public static int BAG_COLOR = Color.argb(255, 252, 190, 26);
    public static int RUN_COLOR = Color.argb(255, 43, 154, 255);
    public static int BACK_COLOR = Color.argb(255, 3, 111, 114);
    public static int TRANSPARENT_COLOR = Color.argb(0, 0, 0, 0);

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

        //TODO CHECK IF POKEMON IS DEAD
        battle = new Battle(app.getPlayer().getPokemons()[0],
                new PokemonProfile(app.getSpawnCount(),
                        app.getPokemon(app.getCurrentGoal().getTitle())));
        battle.setBuddyPokemon(app.getPokemon(app.getPlayer().getPokemons()[0].getDexNumber()));
        battle.setEnemyPokemon(app.getPokemon(battle.getEnemyProfile().getDexNumber()));
        battle.getEnemyProfile().getMoves()[0] = app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size()));
        battle.getEnemyProfile().getMoves()[1] = app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size()));
        battle.getEnemyProfile().getMoves()[2] = app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size()));
        battle.getEnemyProfile().getMoves()[3] = app.getAllMoves().get(
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

        battle.addMessage("Wild " + battle.getEnemyPokemon().getName() + " appeared!", battle.NO_UPDATE);
        battle.addMessage("Go " + battle.getBuddyPokemon().getName() + "!", battle.UPDATE_BUDDY);
        messageState();

        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(0);
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
                        if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(1);
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
                        if(battle.getPhase() == battle.FIGHT_STATE){
                            PokemonGoApp app = (PokemonGoApp) getApplication();
                            executeAttack(1, app.getIntegerRNG(4));
                            messageState();
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(2);
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
                        if(battle.getPhase() == battle.FIGHT_STATE){
                            PokemonGoApp app = (PokemonGoApp) getApplication();
                            executeAttack(1, app.getIntegerRNG(4));
                            messageState();
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(3);
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
                        if(battle.getPhase() == battle.MAIN_STATE){
                            fightState();
                        }
                        else if(battle.getPhase() == battle.FIGHT_STATE){
                            PokemonGoApp app = (PokemonGoApp) getApplication();
                            executeAttack(2, app.getIntegerRNG(4));
                            messageState();
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(4);
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
                        if(battle.getPhase() == battle.MAIN_STATE){
                            pokemonState();
                        }
                        else if(battle.getPhase() == battle.FIGHT_STATE){
                            PokemonGoApp app = (PokemonGoApp) getApplication();
                            executeAttack(3, app.getIntegerRNG(4));
                            messageState();
                        }
                        else if(battle.getPhase() == battle.POKEMON_STATE){
                            switchBuddyPokemon(5);
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
                        Intent MainActivityIntent = new Intent(CatchActivity.this, MainActivity.class);
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
                    }
                }
        );
    }

    private void executeAttack(int playerMoveIndex, int enemyMoveIndex){
        if(buddyFirst()) {
            attack(playerMoveIndex, battle.getBuddyPokemon(), battle.getEnemyPokemon(), battle.getBuddyProfile(),
                    battle.getEnemyProfile(), battle.UPDATE_ENEMY_HP);
            if(checkVictory()){
                battleResult();
            }
            else {
                attack(enemyMoveIndex, battle.getEnemyPokemon(), battle.getBuddyPokemon(), battle.getEnemyProfile(),
                        battle.getBuddyProfile(), battle.UPDATE_BUDDY_HP);
            }
        }
        else{
            attack(enemyMoveIndex, battle.getEnemyPokemon(), battle.getBuddyPokemon(), battle.getEnemyProfile(),
                    battle.getBuddyProfile(), battle.UPDATE_BUDDY_HP);
            attack(playerMoveIndex, battle.getBuddyPokemon(), battle.getEnemyPokemon(), battle.getBuddyProfile(),
                    battle.getEnemyProfile(), battle.UPDATE_ENEMY_HP);
            if(checkVictory()){
                battleResult();
            }
        }
    }

    private void viewMessages(){
        if(battle.getCurrentMessage() < battle.getMessages().size() - 1){
            battle.setCurrentMessage(battle.getCurrentMessage() + 1);
            txvMessage.setText(battle.getMessages().get(battle.getCurrentMessage()));
            updateBattle();
        }
        else{
            battle.getMessages().clear();
            battle.getUpdates().clear();
            battle.setCurrentMessage(0);
            mainState();
        }
    }

    private void attack(int moveIndex, Pokemon attacker, Pokemon defender,
                        PokemonProfile attackerProfile, PokemonProfile defenderProfile,
                        Integer target){
        if(attackerProfile.getMoves()[moveIndex].getCurrentPP() > 0){
            battle.addMessage(attackerProfile.getNickname()
                    + " used " + attackerProfile.getMoves()[moveIndex].getName() + "!", target);
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
                    battle.addMessage("It's super effective!", battle.NO_UPDATE);
                }
                else if(typeMultiplier == 0.5){
                    battle.addMessage("It's not very effective...", battle.NO_UPDATE);
                }
                else if(typeMultiplier == 0){
                    battle.addMessage("It doesn't affect foe "
                            + defenderProfile.getNickname() + "...", battle.NO_UPDATE);
                }

                damage = damage * typeMultiplier;

                if(app.getIntegerRNG(16) < 1){
                    damage = damage * 2;
                    battle.addMessage("A critical hit!", battle.NO_UPDATE);
                }

                if(damage < 1.0){
                    damage = 1.0;
                }

                defenderProfile.setCurrentHP(defenderProfile.getCurrentHP() - (int)(damage));
                if(defenderProfile.getCurrentHP() < 0){
                    defenderProfile.setCurrentHP(0);
                }
            }
            else{
                battle.addMessage(attackerProfile.getNickname() + "'s attack missed!",
                        battle.NO_UPDATE);
            }
        }
    }

    private void battleResult(){
        battle.addMessage(battle.getEnemyProfile().getNickname() + " fainted", battle.NO_UPDATE);
        battle.addMessage(battle.getBuddyProfile().getNickname() + " gained "
                + battle.getEnemyProfile().getLevel()*battle.getBuddyProfile().getLevel()
                + " EXP points!", battle.UPDATE_BUDDY_EXP);
        battle.getBuddyProfile().setCurrentExp(battle.getBuddyProfile().getCurrentExp()
                + battle.getEnemyProfile().getLevel()*battle.getBuddyProfile().getLevel());

        if(battle.getBuddyProfile().getCurrentExp() >=
                battle.getBuddyProfile().getExperienceNeeded()){
            battle.getBuddyProfile().setCurrentExp(battle.getBuddyProfile().getCurrentExp()
                    - battle.getBuddyProfile().getExperienceNeeded());
            battle.getBuddyProfile().setLevel(battle.getBuddyProfile().getLevel() + 1);
            battle.addMessage(battle.getEnemyProfile().getNickname() + " grew to LV. "
                    + battle.getBuddyProfile().getLevel() + "!", battle.NO_UPDATE);
        }
    }

    private boolean checkVictory(){
        if(battle.getEnemyProfile().getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean buddyFirst(){
        if(battle.getBuddyProfile().getSpeed(battle.getBuddyPokemon().getBase().getSpeed()) >
                battle.getEnemyProfile().getSpeed(battle.getEnemyPokemon().getBase().getSpeed())){
            return true;
        }
        else{
            return false;
        }
    }

    private void switchBuddyPokemon(int pokemonIndex){
        if(!battle.getBuddyProfile().equals(player.getPokemons()[pokemonIndex])){
            battle.addMessage("Good job, " + battle.getBuddyProfile().getNickname()
                    + "!", battle.NO_UPDATE);

            PokemonGoApp app = (PokemonGoApp) getApplication();
            battle.setBuddyPokemon(app.getPokemon(player.getPokemons()[pokemonIndex].getDexNumber()));
            battle.setBuddyProfile(player.getPokemons()[pokemonIndex]);
            battle.addMessage("Go " + battle.getBuddyPokemon().getName() + "!", battle.UPDATE_BUDDY);
        }
        else{
            battle.addMessage(battle.getBuddyProfile().getNickname()
                    + " is already in battle!", battle.NO_UPDATE);
        }
    }

    private void updateBattle(){
        if(battle.getUpdates().get(battle.getCurrentMessage())
                .equals(battle.UPDATE_BUDDY)){
            updateBuddyPokemon();
        }
        else if(battle.getUpdates().get(battle.getCurrentMessage())
                .equals(battle.UPDATE_ENEMY)){
            updateEnemyPokemon();
        }
        else if(battle.getUpdates().get(battle.getCurrentMessage())
                .equals(battle.UPDATE_BUDDY_EXP)){
            updateBuddyExp();
        }
        else if(battle.getUpdates().get(battle.getCurrentMessage())
                .equals(battle.UPDATE_ENEMY_HP)){
            updateEnemyHp();
        }
        else if(battle.getUpdates().get(battle.getCurrentMessage())
                .equals(battle.UPDATE_BUDDY_HP)){
            updateBuddyHp();
        }
    }

    private void updateBuddyPokemon(){
        txvBuddyName.setText(battle.getBuddyProfile().getNickname());
        imgButtonBuddy.setImageResource(battle.getBuddyPokemon().getBackImage());
        updateBuddyHp();
        updateBuddyExp();
    }

    private void updateEnemyPokemon(){
        txvEnemyName.setText(battle.getEnemyProfile().getNickname());
        imgButtonEnemy.setImageResource(battle.getEnemyPokemon().getFrontImage());
        updateEnemyHp();
    }

    private void updateEnemyHp(){
        barEnemyHP.setMax(battle.getEnemyProfile().getHP(battle.getEnemyPokemon().getBase().getHP()));
        barEnemyHP.setProgress(battle.getEnemyProfile().getCurrentHP());
    }

    private void updateBuddyHp(){
        barBuddyHP.setMax(battle.getBuddyProfile().getHP(battle.getBuddyPokemon().getBase().getHP()));
        barBuddyHP.setProgress(battle.getBuddyProfile().getCurrentHP());
        txvBuddyHP.setText(battle.getBuddyProfile().getCurrentHP() + "/" +
                battle.getBuddyProfile().getHP(battle.getBuddyPokemon().getBase().getHP()));
    }

    private void updateBuddyExp(){
        barBuddyEXP.setMax(battle.getBuddyProfile().getExperienceNeeded());
        barBuddyEXP.setProgress(battle.getBuddyProfile().getCurrentExp());
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
        updateBattle();
        txvMessage.setText(battle.getMessages().get(battle.getCurrentMessage()));
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

        txvMessage.setText("What will " + battle.getBuddyProfile().getNickname() + " do?");
    }

    private void fightState(){
        battle.setPhase(battle.FIGHT_STATE);
        btn1.setClickable(false);
        btn2.setClickable(false);
        btn3.setClickable(!battle.getBuddyProfile().getMoves()[0].isEmpty());
        btn4.setClickable(!battle.getBuddyProfile().getMoves()[1].isEmpty());
        btn5.setClickable(!battle.getBuddyProfile().getMoves()[2].isEmpty());
        btn6.setClickable(!battle.getBuddyProfile().getMoves()[3].isEmpty());
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

        btn3.setText(battle.getBuddyProfile().getMoves()[0].getName());
        btn4.setText(battle.getBuddyProfile().getMoves()[1].getName());
        btn5.setText(battle.getBuddyProfile().getMoves()[2].getName());
        btn6.setText(battle.getBuddyProfile().getMoves()[3].getName());
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
