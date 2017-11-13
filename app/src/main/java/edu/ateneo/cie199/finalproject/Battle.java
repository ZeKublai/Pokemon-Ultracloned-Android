package edu.ateneo.cie199.finalproject;

import java.util.ArrayList;

import static java.lang.Math.floor;

/**
 * Created by John on 11/7/2017.
 */

public class Battle {
    //BUTTON STATES
    public static int MESSAGE_STATE_FIRST = 0;
    public static int MESSAGE_STATE_LAST = 1;
    public static int MAIN_STATE = 2;
    public static int FIGHT_STATE = 3;
    public static int POKEMON_STATE = 4;
    public static int BAG_STATE = 5;

    public static int DECISION_NONE = 0;
    public static int DECISION_ATTACK0 = 1;
    public static int DECISION_ATTACK1 = 2;
    public static int DECISION_ATTACK2 = 3;
    public static int DECISION_ATTACK3 = 4;

    public static int DECISION_SWAP0 = 5;
    public static int DECISION_SWAP1 = 6;
    public static int DECISION_SWAP2 = 7;
    public static int DECISION_SWAP3 = 8;
    public static int DECISION_SWAP4 = 9;
    public static int DECISION_SWAP5 = 10;

    public static Integer NO_UPDATE = 0;
    public static Integer UPDATE_ENEMY = 1;
    public static Integer UPDATE_ENEMY_HP = 2;
    public static Integer UPDATE_BUDDY = 3;
    public static Integer UPDATE_BUDDY_EXP = 4;
    public static Integer UPDATE_BUDDY_HP = 5;

    private Player mPlayer;
    private ArrayList<Type> mTypeChart = new ArrayList<>();

    private int mPlayerDecision = DECISION_NONE;
    private int mEnemyDecision = DECISION_NONE;

    private PokemonProfile mBuddy;
    private PokemonProfile mEnemy;

    private ArrayList<String> mMessages = new ArrayList<>();
    private ArrayList<Integer> mUpdates = new ArrayList<>();
    private int mPhase = 0;

    private int mCurrentMessage = 0;

    public Battle(PokemonProfile mBuddy, PokemonProfile mEnemy) {
        this.mBuddy = mBuddy;
        this.mEnemy = mEnemy;
        this.mPhase = 0;
        this.mMessages = new ArrayList<>();
        this.mUpdates = new ArrayList<>();
    }

    public Battle(PokemonProfile mBuddy, PokemonProfile mEnemy, Player mPlayer) {
        this.mBuddy = mBuddy;
        this.mEnemy = mEnemy;
        this.mPlayer = mPlayer;
        this.mPhase = 0;
        this.mMessages = new ArrayList<>();
        this.mUpdates = new ArrayList<>();
    }

    public int getPlayerDecision() {
        return mPlayerDecision;
    }
    public void setPlayerDecision(int mPlayerDecision) {
        this.mPlayerDecision = mPlayerDecision;
    }

    public int getEnemyDecision() {
        return mEnemyDecision;
    }
    public void setEnemyDecision(int mEnemyDecision) {
        this.mEnemyDecision = mEnemyDecision;
    }

    public ArrayList<Type> getTypeChart() {
        return mTypeChart;
    }
    public void setTypeChart(ArrayList<Type> mTypeChart) {
        this.mTypeChart = mTypeChart;
    }

    public boolean checkVictory(){
        if(mEnemy.getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkPokemonDefeat(int pokemonIndex){
        if(mPlayer.getPokemons()[pokemonIndex].getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkPlayerDefeat(){
        for(int index = 0; index < mPlayer.getPokemons().length; index++){
            if(!checkPokemonDefeat(index)){
                return false;
            }
        }
        return true;
    }
    public boolean buddyFirst(){
        if(mPlayerDecision > DECISION_ATTACK3){
            return true;
        }
        if(mEnemyDecision > DECISION_ATTACK3){
            return false;
        }
        if(mBuddy.getSpeed() > mEnemy.getSpeed()){
            return true;
        }
        else{
            return false;
        }
    }

    public void sendErrorMessage(String message){
        mMessages.clear();
        addMessage(message, NO_UPDATE);
        mPhase = MESSAGE_STATE_LAST;
    }

    public void switchBuddyPokemon(int pokemonIndex){
        if(checkPokemonDefeat(pokemonIndex)){
            sendErrorMessage(mPlayer.getPokemons()[pokemonIndex].getNickname()
                    + " has fainted and could not battle!");
        }
        else{
            if(!mBuddy.equals(mPlayer.getPokemons()[pokemonIndex])){
                addMessage("Good job, " + mBuddy.getNickname() + "!", NO_UPDATE);

                mBuddy = mPlayer.getPokemons()[pokemonIndex];
                addMessage("Go " + mBuddy.getNickname() + "!", UPDATE_BUDDY);
            }
            else{
                sendErrorMessage(mBuddy.getNickname() + " is already in battle!");
            }
        }
    }
    public void battleResult(){
        addMessage(mEnemy.getNickname() + " fainted", NO_UPDATE);
        addMessage(mBuddy.getNickname() + " gained "
                + mEnemy.getLevel()* mBuddy.getLevel()
                + " EXP points!", UPDATE_BUDDY_EXP);
        mBuddy.setCurrentExp(mBuddy.getCurrentExp()
                + mEnemy.getLevel()* mBuddy.getLevel());

        if(mBuddy.getCurrentExp() >= mBuddy.getExperienceNeeded()){
            mBuddy.setCurrentExp(mBuddy.getCurrentExp()
                    - mBuddy.getExperienceNeeded());
            mBuddy.setLevel(mBuddy.getLevel() + 1);
            addMessage(mBuddy.getNickname() + " grew to LV. " + mBuddy.getLevel()
                    + "!", NO_UPDATE);
        }
    }

    public void attack(int moveIndex, PokemonProfile attackerProfile,
                        PokemonProfile defenderProfile, Integer target){
        if(attackerProfile.getMoves()[moveIndex].getCurrentPP() > 0){
            addMessage(attackerProfile.getNickname() + " used "
                    + attackerProfile.getMoves()[moveIndex].getName() + "!", target);
            //PokemonGoApp app = (PokemonGoApp) getApplication();
            if(PokemonGoApp.getIntegerRNG(100) <
                    attackerProfile.getMoves()[moveIndex].getAccuracy()){
                int attackStat = 0;
                int defenseStat = 0;
                if(attackerProfile.getMoves()[moveIndex].getCategory() == Move.PHYSICAL){
                    attackStat = attackerProfile.getAttack();
                    defenseStat = defenderProfile.getDefense();
                }
                else if(attackerProfile.getMoves()[moveIndex].getCategory() == Move.SPECIAL){
                    attackStat = attackerProfile.getSpAttack();
                    defenseStat = defenderProfile.getSpDefense();
                }

                double damage = floor(floor(floor(2 * attackerProfile.getLevel() / 5 + 2) *
                        attackStat * attackerProfile.getMoves()[moveIndex].getPower() /
                        defenseStat) / 50) + 2;

                double typeMultiplier = attackerProfile.getMoves()[moveIndex]
                        .getType().getMultiplier()[defenderProfile.getDexData().getType().getId()];

                if(typeMultiplier == 2){
                    addMessage("It's super effective!", NO_UPDATE);
                }
                else if(typeMultiplier == 0.5){
                    addMessage("It's not very effective...", NO_UPDATE);
                }
                else if(typeMultiplier == 0){
                    addMessage("It doesn't affect foe " + defenderProfile.getNickname() + "...",
                            NO_UPDATE);
                }

                damage = damage * typeMultiplier;

                if(PokemonGoApp.getIntegerRNG(16) < 1){
                    damage = damage * 2;
                    addMessage("A critical hit!", NO_UPDATE);
                }

                if(damage < 1.0){
                    damage = 1.0;
                }

                defenderProfile.setCurrentHP(defenderProfile.getCurrentHP() - (int)(damage));
                if(defenderProfile.getCurrentHP() < 0){
                    defenderProfile.setCurrentHP(0);
                    if(mBuddy.getCurrentHP() <= 0){
                        addMessage(mBuddy.getNickname() + " fainted", NO_UPDATE);
                        if(checkPlayerDefeat()){
                            addMessage(getPlayer().getName() + " is out of Pokemon!", NO_UPDATE);
                            addMessage(getPlayer().getName() + " whited out!", NO_UPDATE);
                        }
                        mPhase = MESSAGE_STATE_LAST;
                    }
                }
            }
            else{
                addMessage(attackerProfile.getNickname() + "'s attack missed!", NO_UPDATE);
            }
            attackerProfile.getMoves()[moveIndex].setCurrentPP(
                    attackerProfile.getMoves()[moveIndex].getCurrentPP() - 1);
        }
        else{
            sendErrorMessage("There's no PP left for this move!");
        }
        if(checkVictory()){
            battleResult();
            mPhase = MESSAGE_STATE_LAST;
        }
    }

    public void executePlayer(int decision){
        if(decision == DECISION_ATTACK0){
            attack(0, getBuddy(), getEnemy(), UPDATE_ENEMY_HP);
        }
        else if(decision == DECISION_ATTACK1){
            attack(1, getBuddy(), getEnemy(), UPDATE_ENEMY_HP);
        }
        else if(decision == DECISION_ATTACK2){
            attack(2, getBuddy(), getEnemy(), UPDATE_ENEMY_HP);
        }
        else if(decision == DECISION_ATTACK3){
            attack(3, getBuddy(), getEnemy(), UPDATE_ENEMY_HP);
        }
        else if(decision == DECISION_SWAP0){
            switchBuddyPokemon(0);
        }
        else if(decision == DECISION_SWAP1){
            switchBuddyPokemon(1);
        }
        else if(decision == DECISION_SWAP2){
            switchBuddyPokemon(2);
        }
        else if(decision == DECISION_SWAP3){
            switchBuddyPokemon(3);
        }
        else if(decision == DECISION_SWAP4){
            switchBuddyPokemon(4);
        }
        else if(decision == DECISION_SWAP5){
             switchBuddyPokemon(5);
        }
    }

    public void executeEnemy(int decision){
        if(decision == DECISION_ATTACK0){
            attack(0, getEnemy(), getBuddy(), UPDATE_BUDDY_HP);
        }
        else if(decision == DECISION_ATTACK1){
            attack(1, getEnemy(), getBuddy(), UPDATE_BUDDY_HP);
        }
        else if(decision == DECISION_ATTACK2){
            attack(2, getEnemy(), getBuddy(), UPDATE_BUDDY_HP);
        }
        else if(decision == DECISION_ATTACK3){
            attack(3, getEnemy(), getBuddy(), UPDATE_BUDDY_HP);
        }
    }

    public Player getPlayer() {
        return mPlayer;
    }
    public void setPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

    public PokemonProfile getBuddy() {
        return mBuddy;
    }
    public void setBuddy(PokemonProfile mBuddy) {
        this.mBuddy = mBuddy;
    }

    public PokemonProfile getEnemy() {
        return mEnemy;
    }
    public void setEnemy(PokemonProfile mEnemy) {
        this.mEnemy = mEnemy;
    }

    public ArrayList<String> getMessages() {
        return mMessages;
    }
    public ArrayList<Integer> getUpdates() {
        return mUpdates;
    }

    public void setMessages(ArrayList<String> mMessages) {
        this.mMessages = mMessages;
    }

    public int getPhase() {
        return mPhase;
    }
    public void setPhase(int mPhase) {
        this.mPhase = mPhase;
    }

    public int getCurrentMessage() {
        return mCurrentMessage;
    }
    public void setCurrentMessage(int mCurrentMessage) {
        this.mCurrentMessage = mCurrentMessage;
    }

    public void addMessage(String message, Integer update){
        mMessages.add(message);
        mUpdates.add(update);
    }
}
