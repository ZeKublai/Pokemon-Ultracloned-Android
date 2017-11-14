package edu.ateneo.cie199.finalproject;

import java.util.ArrayList;

import static java.lang.Math.floor;

/**
 * Created by John on 11/7/2017.
 */

public class Battle {
    //BUTTON STATES
    public static int STATE_MESSAGE_FIRST = 0;
    public static int STATE_MESSAGE_LAST = 1;
    public static int STATE_MAIN = 2;
    public static int STATE_FIGHT = 3;
    public static int STATE_POKEMON = 4;
    public static int STATE_BAG = 5;
    public static int STATE_USE_ITEM = 6;

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

    public static int DECISION_ITEM0 = 11;
    public static int DECISION_ITEM1 = 12;
    public static int DECISION_ITEM2 = 13;
    public static int DECISION_ITEM3 = 14;
    public static int DECISION_ITEM4 = 15;
    public static int DECISION_ITEM5 = 16;

    private Player mPlayer = new Player();
    private PokemonProfile mSelectedPokemon = new PokemonProfile();
    private boolean mEnemyCaught = false;
    private ArrayList<Type> mTypeChart = new ArrayList<>();

    private int mPlayerDecision = DECISION_NONE;
    private int mEnemyDecision = DECISION_NONE;

    private PokemonProfile mBuddy = new PokemonProfile();
    private PokemonProfile mEnemy = new PokemonProfile();

    private ArrayList<Message> mMessages = new ArrayList<>();
    private int mState = 0;
    private int mIndex = 0;

    private String MESSAGE_SWAP1 = "Good job, ";
    private String MESSAGE_SWAP2 = "Go ";
    private String MESSAGE_PLAYER_LOSS1 = " is out of Pokemon!";
    private String MESSAGE_PLAYER_LOSS2 = " whited out!";
    private String MESSAGE_FAINTED = " fainted";
    private String MESSAGE_EXP_GAINED = " EXP points!";
    private String MESSAGE_LEVEL_UP = " grew to LV. ";
    private String MESSAGE_SUPER_EFFECTIVE = "It's super effective!";
    private String MESSAGE_NOT_EFFECTIVE = "It's not very effective...";
    private String MESSAGE_NO_EFFECT = "It doesn't affect foe ";
    private String MESSAGE_CRITICAL = "A critical hit!";
    private String MESSAGE_MISSED = "'s attack missed!";

    private String ERROR_FAINTED = " has fainted and could not battle!";
    private String ERROR_IN_BATTLE = " is already in battle!";
    private String ERROR_NO_PP = "There's no PP left for this move!";


    public Battle(PokemonProfile mBuddy, PokemonProfile mEnemy) {
        this.mBuddy = mBuddy;
        this.mEnemy = mEnemy;
        this.mState = 0;
        this.mMessages = new ArrayList<>();
    }

    public Battle(PokemonProfile mBuddy, PokemonProfile mEnemy, Player mPlayer) {
        this.mBuddy = mBuddy;
        this.mEnemy = mEnemy;
        this.mPlayer = mPlayer;
        this.mState = 0;
        this.mMessages = new ArrayList<>();
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

    //CHECK FUNCTIONS
    public boolean isEnemyFainted(){
        if(mEnemy.getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isPokemonFainted(int pokemonIndex){
        if(mPlayer.getPokemons()[pokemonIndex].getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isPlayerDefeated(){
        for(int index = 0; index < mPlayer.getPokemons().length; index++){
            if(!isPokemonFainted(index)){
                return false;
            }
        }
        return true;
    }
    public boolean isBuddyFainted(){
        if(getBuddy().getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isBuddyFirst(){
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

    //BATTLE FUNCTIONS
    public void sendErrorMessage(String message){
        mMessages.clear();
        addMessage(message, Message.NO_UPDATE);
        mState = STATE_MESSAGE_LAST;
    }

    public void resetMessages(){
        getMessages().clear();
        setIndex(0);
        setPlayerDecision(DECISION_NONE);
        setEnemyDecision(DECISION_NONE);
    }

    public void firstMove(){
        if(isBuddyFirst()){
            doPlayerDecision();
        }
        else{
            doEnemyDecision();
        }
    }

    public void secondMove(){
        if(isBuddyFirst()){
            doEnemyDecision();
        }
        else{
            doPlayerDecision();
        }
    }

    public void switchBuddyPokemon(int pokemonIndex){
        if(isPokemonFainted(pokemonIndex)){
            sendErrorMessage(mPlayer.getPokemons()[pokemonIndex].getNickname() + ERROR_FAINTED);
        }
        else{
            if(!mBuddy.equals(mPlayer.getPokemons()[pokemonIndex])){
                addMessage(MESSAGE_SWAP1 + mBuddy.getNickname() + "!", Message.NO_UPDATE);
                mBuddy = mPlayer.getPokemons()[pokemonIndex];
                addMessage(MESSAGE_SWAP2 + mBuddy.getNickname() + "!", Message.UPDATE_BUDDY);
            }
            else{
                sendErrorMessage(mBuddy.getNickname() + ERROR_IN_BATTLE);
            }
        }
    }

    public boolean hasItem(int bagIndex){
        Item selectedItem = mPlayer.getBag()[bagIndex];
        if(selectedItem.getQuantity() == 0){
            sendErrorMessage("You are out of " + mPlayer.getBag()[bagIndex].getName() + "!");
            return false;
        }
        else{
            addMessage(mPlayer.getName() + " used " + selectedItem.getName() + "!",
                    Message.UPDATE_BUDDY_HP);
            selectedItem.setQuantity(selectedItem.getQuantity() - 1);
            return true;
        }
    }

    public void usePotion(PokemonProfile profile){
        if(hasItem(0)){
            if(!mPlayer.usePotion(profile)){
                sendErrorMessage("It had no effect...");
            }
        }
    }

    public void useSuperPotion(PokemonProfile profile){
        if(hasItem(1)){
            if(!mPlayer.useSuperPotion(profile)){
                sendErrorMessage("It had no effect...");
            }
        }
    }

    public void useMaxRevive(PokemonProfile profile){
        if(hasItem(2)){
            if(!mPlayer.useMaxRevive(profile)){
                sendErrorMessage("It had no effect...");
            }
        }
    }

    public void usePokeBall(){
        if(hasItem(3)){
            int result = mPlayer.usePokeball(mEnemy);
            catchResults(result);
        }
    }

    public PokemonProfile getSelectedPokemon() {
        return mSelectedPokemon;
    }
    public void setSelectedPokemon(PokemonProfile mSelectedPokemon) {
        this.mSelectedPokemon = mSelectedPokemon;
    }

    public void catchResults(int result){
        String message = "";
        for(int index = 1; index <= result; index++){
            if(index == result){
                if(result < 4){
                    addMessage(mEnemy.getNickname() + " broke free!", Message.NO_UPDATE);
                }
                else if(result == 4){
                    addMessage(mEnemy.getNickname() + " was caught!", Message.NO_UPDATE);
                    mEnemyCaught = true;
                    if(mPlayer.getFreeSlot() != Player.MAX_POKEMON_SLOTS){
                        mPlayer.getPokemons()[mPlayer.getFreeSlot()] = mEnemy;
                        addMessage(mEnemy.getNickname() + " has been added to the party!", Message.NO_UPDATE);
                    }
                    else{
                        mPlayer.getBox().add(mEnemy);
                        addMessage(mEnemy.getNickname() + " has been sent to BOX 1!", Message.NO_UPDATE);
                    }

                    mState = STATE_MESSAGE_LAST;
                }
            }
            else{
                message = message + index + "...";
                addMessage(message, Message.NO_UPDATE);
            }
        }
    }

    public boolean isEnemyCaught() {
        return mEnemyCaught;
    }
    public void setEnemyCaught(boolean mIsEnemyCaught) {
        this.mEnemyCaught = mIsEnemyCaught;
    }

    public void useGreatBall(){
        if(hasItem(4)){
            mPlayer.useGreatBall(mEnemy);
            int result = mPlayer.useGreatBall(mEnemy);
            catchResults(result);
        }
    }

    public void useUltraBall(){
        if(hasItem(5)){
            mPlayer.useUltraBall(mEnemy);
            int result = mPlayer.useUltraBall(mEnemy);
            catchResults(result);
        }
    }

    public void enemyHasFainted(){
        addMessage(mEnemy.getNickname() + MESSAGE_FAINTED, Message.NO_UPDATE);
        addMessage(mBuddy.getNickname() + " gained " + mEnemy.getLevel()* mBuddy.getLevel()
                + MESSAGE_EXP_GAINED, Message.UPDATE_BUDDY_EXP);
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() + mEnemy.getLevel()* mBuddy.getLevel());
        if(mBuddy.getCurrentExp() >= mBuddy.getExperienceNeeded()){
            buddyLevelUp();
        }
        //TODO ADD MONEY REWARD IF TRAINER
        mState = STATE_MESSAGE_LAST;
    }

    public void buddyHasFainted(){
        addMessage(mBuddy.getNickname() + MESSAGE_FAINTED, Message.NO_UPDATE);
        if(isPlayerDefeated()){
            playerLoses();
        }
        mState = STATE_MESSAGE_LAST;
    }

    public void playerLoses(){
        addMessage(getPlayer().getName() + MESSAGE_PLAYER_LOSS1, Message.NO_UPDATE);
        addMessage(getPlayer().getName() + MESSAGE_PLAYER_LOSS2, Message.NO_UPDATE);
    }

    public void buddyLevelUp(){
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() - mBuddy.getExperienceNeeded());
        mBuddy.setLevel(mBuddy.getLevel() + 1);
        addMessage(mBuddy.getNickname() + MESSAGE_LEVEL_UP + mBuddy.getLevel() + "!",
                Message.NO_UPDATE);
    }

    public void attack(Move move, PokemonProfile attacker, PokemonProfile defender, Integer target){
        if(move.getCurrentPP() > 0){
            addMessage(attacker.getNickname() + " used " + move.getName() + "!", target);
            if(PokemonGoApp.getIntegerRNG(Move.MAX_ACCURACY) < move.getAccuracy()){
                int attackStat = attacker.getAttack(move);
                int defenseStat = defender.getDefense(move);

                double damage = floor(floor(floor(2 * attacker.getLevel() / 5 + 2) *
                        attackStat * move.getPower() / defenseStat) / 50) + 2;

                Type defenderType1 = defender.getDexData().getType1();
                Type defenderType2 = defender.getDexData().getType2();

                double typeMultiplier1 = move.getType().getMultiplier()[defenderType1.getId()];
                double typeMultiplier2 = move.getType().getMultiplier()[defenderType2.getId()];
                double totalTypeMultiplier = typeMultiplier1*typeMultiplier2;

                if(totalTypeMultiplier >= 2){
                    addMessage(MESSAGE_SUPER_EFFECTIVE, Message.NO_UPDATE);
                }
                else if(totalTypeMultiplier <= 0.5){
                    addMessage(MESSAGE_NOT_EFFECTIVE, Message.NO_UPDATE);
                }
                else if(totalTypeMultiplier == 0){
                    addMessage(MESSAGE_NO_EFFECT + defender.getNickname()
                            + "...", Message.NO_UPDATE);
                }

                damage = damage * totalTypeMultiplier;

                if(PokemonGoApp.getIntegerRNG(16) < 1){
                    damage = damage * 2;
                    addMessage(MESSAGE_CRITICAL, Message.NO_UPDATE);
                }

                if(damage < 1.0){
                    damage = 1.0;
                }

                defender.setCurrentHP(defender.getCurrentHP() - (int)(damage));
                if(defender.getCurrentHP() < 0){
                    defender.setCurrentHP(0);
                    if(isBuddyFainted()){
                        buddyHasFainted();
                    }
                    if(isEnemyFainted()){
                        enemyHasFainted();
                    }
                }
            }
            else{
                addMessage(attacker.getNickname() + MESSAGE_MISSED, Message.NO_UPDATE);
            }
            move.setCurrentPP(move.getCurrentPP() - 1);
        }
        else{
            sendErrorMessage(ERROR_NO_PP);
        }
    }

    public void doPlayerDecision(){
        if(mPlayerDecision == DECISION_ATTACK0){
            attack(mBuddy.getMoves()[0], getBuddy(), getEnemy(), Message.UPDATE_ENEMY_HP);
        }
        else if(mPlayerDecision == DECISION_ATTACK1){
            attack(mBuddy.getMoves()[1], getBuddy(), getEnemy(), Message.UPDATE_ENEMY_HP);
        }
        else if(mPlayerDecision == DECISION_ATTACK2){
            attack(mBuddy.getMoves()[2], getBuddy(), getEnemy(), Message.UPDATE_ENEMY_HP);
        }
        else if(mPlayerDecision == DECISION_ATTACK3){
            attack(mBuddy.getMoves()[3], getBuddy(), getEnemy(), Message.UPDATE_ENEMY_HP);
        }
        else if(mPlayerDecision == DECISION_SWAP0){
            switchBuddyPokemon(0);
        }
        else if(mPlayerDecision == DECISION_SWAP1){
            switchBuddyPokemon(1);
        }
        else if(mPlayerDecision == DECISION_SWAP2){
            switchBuddyPokemon(2);
        }
        else if(mPlayerDecision == DECISION_SWAP3){
            switchBuddyPokemon(3);
        }
        else if(mPlayerDecision == DECISION_SWAP4){
            switchBuddyPokemon(4);
        }
        else if(mPlayerDecision == DECISION_SWAP5){
            switchBuddyPokemon(5);
        }
        else if(mPlayerDecision == DECISION_ITEM0){
            usePotion(mSelectedPokemon);
        }
        else if(mPlayerDecision == DECISION_ITEM1){
            useSuperPotion(mSelectedPokemon);
        }
        else if(mPlayerDecision == DECISION_ITEM2){
            useMaxRevive(mSelectedPokemon);
        }
        else if(mPlayerDecision == DECISION_ITEM3){
            usePokeBall();
        }
        else if(mPlayerDecision == DECISION_ITEM4){
            useGreatBall();
        }
        else if(mPlayerDecision == DECISION_ITEM5){
            useUltraBall();
        }
    }
    public void doEnemyDecision(){
        if(mEnemyDecision == DECISION_ATTACK0){
            attack(mEnemy.getMoves()[0], getEnemy(), getBuddy(), Message.UPDATE_BUDDY_HP);
        }
        else if(mEnemyDecision == DECISION_ATTACK1){
            attack(mEnemy.getMoves()[1], getEnemy(), getBuddy(), Message.UPDATE_BUDDY_HP);
        }
        else if(mEnemyDecision == DECISION_ATTACK2){
            attack(mEnemy.getMoves()[2], getEnemy(), getBuddy(), Message.UPDATE_BUDDY_HP);
        }
        else if(mEnemyDecision == DECISION_ATTACK3){
            attack(mEnemy.getMoves()[3], getEnemy(), getBuddy(), Message.UPDATE_BUDDY_HP);
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

    public ArrayList<Message> getMessages() {
        return mMessages;
    }
    public void setMessages(ArrayList<Message> mMessages) {
        this.mMessages = mMessages;
    }

    public int getState() {
        return mState;
    }
    public void setState(int mState) {
        this.mState = mState;
    }

    public int getIndex() {
        return mIndex;
    }
    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public void addMessage(String message, Integer update){
        mMessages.add(new Message(message + "∇", update));
    }
}
