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

    public static int DECISION_RUN = 17;

    private Player mPlayer = new Player();
    private PokemonProfile mSelectedPokemon = new PokemonProfile();
    private Item mSelectedItem = new Item();
    private boolean mEnemyCaught = false;
    private ArrayList<Type> mTypeChart = new ArrayList<>();

    private int mPlayerDecision = DECISION_NONE;
    private int mEnemyDecision = DECISION_NONE;

    private PokemonProfile mBuddy = new PokemonProfile();
    private PokemonProfile mEnemy = new PokemonProfile();

    private ArrayList<Message> mMessages = new ArrayList<>();
    private int mState = 0;
    private int mIndex = 0;

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

    //CHECK FUNCTIONS
    public boolean isEnemyFainted(){
        if(mEnemy.getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
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
        else if(mEnemyDecision > DECISION_ATTACK3){
            return false;
        }
        else if(mBuddy.getSpeed() > mEnemy.getSpeed()){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean hasItem(int bagIndex, int updateTarget){
        mSelectedItem = mPlayer.getBag()[bagIndex];
        if(mSelectedItem.getQuantity() <= 0){
            sendErrorMessage("You are out of " + mPlayer.getBag()[bagIndex].getName() + "!");
            return false;
        }
        else{
            addMessage(mPlayer.getName() + " used " + mSelectedItem.getName() + "!", updateTarget);
            mSelectedItem.setQuantity(mSelectedItem.getQuantity() - 1);
            return true;
        }
    }

    //BATTLE FUNCTIONS
    public void sendErrorMessage(String message){
        mMessages.clear();
        addMessage(message, Message.NO_UPDATE);
        mState = STATE_MESSAGE_LAST;
    }
    public void newTurn(){
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

    //BATTLE OPTIONS
    public void switchBuddyPokemon(int pokemonIndex){
        if(mPlayer.isPokemonFainted(pokemonIndex)){
            sendErrorMessage(mPlayer.getPokemons()[pokemonIndex].getNickname() + Message.ERROR_FAINTED);
        }
        else{
            if(!mBuddy.equals(mPlayer.getPokemons()[pokemonIndex])){
                addMessage(Message.MESSAGE_SWAP1 + mBuddy.getNickname() + "!", Message.NO_UPDATE);
                mBuddy = mPlayer.getPokemons()[pokemonIndex];
                addMessage(Message.MESSAGE_SWAP2 + mBuddy.getNickname() + "!", Message.UPDATE_BUDDY);
            }
            else{
                sendErrorMessage(mBuddy.getNickname() + Message.ERROR_IN_BATTLE);
            }
        }
    }
    public void attack(Move move, PokemonProfile attacker, PokemonProfile defender, Integer updateTarget){
        if(move.getCurrentPP() > 0){
            addMessage(attacker.getNickname() + " used " + move.getName() + "!", updateTarget);
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
                    addMessage(Message.MESSAGE_SUPER_EFFECTIVE, Message.NO_UPDATE);
                }
                else if(totalTypeMultiplier <= 0.5 && totalTypeMultiplier > 0){
                    addMessage(Message.MESSAGE_NOT_EFFECTIVE, Message.NO_UPDATE);
                }
                else if(totalTypeMultiplier == 0){
                    addMessage(Message.MESSAGE_NO_EFFECT + defender.getNickname()
                            + "...", Message.NO_UPDATE);
                }

                damage = damage * totalTypeMultiplier;

                if(PokemonGoApp.getIntegerRNG(16) < 1){
                    damage = damage * 2;
                    addMessage(Message.MESSAGE_CRITICAL, Message.NO_UPDATE);
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
                addMessage(attacker.getNickname() + Message.MESSAGE_MISSED, Message.NO_UPDATE);
            }
            move.setCurrentPP(move.getCurrentPP() - 1);
        }
        else{
            sendErrorMessage(Message.ERROR_NO_PP);
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
        else if(mPlayerDecision == DECISION_RUN){
            runAway();
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

    public void usePotion(PokemonProfile profile){
        if(hasItem(0, Message.UPDATE_BUDDY_HP)){
            if(!mPlayer.getBag()[0].usePotion(profile)){
                addMessage(Message.ERROR_NO_EFFECT, Message.NO_UPDATE);
            }
        }
    }
    public void useSuperPotion(PokemonProfile profile){
        if(hasItem(1, Message.UPDATE_BUDDY_HP)){
            if(!mPlayer.getBag()[1].useSuperPotion(profile)){
                addMessage(Message.ERROR_NO_EFFECT, Message.NO_UPDATE);
            }
        }
    }
    public void useMaxRevive(PokemonProfile profile){
        if(hasItem(2, Message.UPDATE_BUDDY_HP)){
            if(!mPlayer.getBag()[2].useMaxRevive(profile)){
                addMessage(Message.ERROR_NO_EFFECT, Message.NO_UPDATE);
            }
        }
    }
    public void usePokeBall(){
        if(hasItem(3, Message.UPDATE_CATCH)){
            int result = mPlayer.getBag()[3].usePokeball(mEnemy);
            catchResults(result);
        }
    }
    public void useGreatBall(){
        if(hasItem(4, Message.UPDATE_CATCH)){
            int result = mPlayer.getBag()[4].useGreatBall(mEnemy);
            catchResults(result);
        }
    }
    public void useUltraBall(){
        if(hasItem(5, Message.UPDATE_CATCH)){
            int result = mPlayer.getBag()[5].useUltraBall(mEnemy);
            catchResults(result);
        }
    }

    //END BATTLE
    public boolean isFinished(){
        return (isEnemyCaught()||isEnemyFainted()||getPlayer().isPlayerDefeated());
    }
    public void runAway(){
        addMessage(Message.MESSAGE_RUN_AWAY, Message.NO_UPDATE);
        setState(STATE_MESSAGE_LAST);
    }
    public void catchResults(int result){
        String message = "";
        for(int index = 1; index < result; index++){
            message = message + index + "...";
            addMessage(message, Message.UPDATE_CATCH);
        }

        if(result < 4){
            addMessage(mEnemy.getNickname() + Message.MESSAGE_ESCAPED, Message.UPDATE_ENEMY);
        }
        else if(result == 4){
            addMessage(mEnemy.getNickname() + Message.MESSAGE_CAUGHT, Message.NO_UPDATE);
            mEnemyCaught = true;
            if(mPlayer.getFreeSlot() != Player.MAX_POKEMON_SLOTS){
                mPlayer.getPokemons()[mPlayer.getFreeSlot()] = mEnemy;
                addMessage(mEnemy.getNickname() + Message.MESSAGE_TO_PARTY, Message.NO_UPDATE);
            }
            else{
                mPlayer.getBox().add(mEnemy);
                addMessage(mEnemy.getNickname() + Message.MESSAGE_TO_BOX, Message.NO_UPDATE);
            }

            mState = STATE_MESSAGE_LAST;
        }

    }
    public void enemyHasFainted(){
        addMessage(mEnemy.getNickname() + Message.MESSAGE_FAINTED, Message.NO_UPDATE);
        addMessage(mBuddy.getNickname() + " gained " + mEnemy.getLevel()* mBuddy.getLevel()
                + Message.MESSAGE_EXP_GAINED, Message.UPDATE_BUDDY_EXP);
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() + mEnemy.getLevel()* mBuddy.getLevel());
        if(mBuddy.getCurrentExp() >= mBuddy.getExperienceNeeded()){
            buddyLevelUp();
        }
        //TODO ADD MONEY REWARD IF TRAINER
        mState = STATE_MESSAGE_LAST;
    }
    public void buddyHasFainted(){
        addMessage(mBuddy.getNickname() + Message.MESSAGE_FAINTED, Message.UPDATE_BUDDY);
        if(mPlayer.isPlayerDefeated()){
            playerLoses();
        }
        mState = STATE_MESSAGE_LAST;
    }
    public void playerLoses(){
        addMessage(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS1, Message.NO_UPDATE);
        addMessage(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS2, Message.NO_UPDATE);
    }
    public void buddyLevelUp(){
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() - mBuddy.getExperienceNeeded());
        mBuddy.setLevel(mBuddy.getLevel() + 1);
        addMessage(mBuddy.getNickname() + Message.MESSAGE_LEVEL_UP + mBuddy.getLevel() + "!",
                Message.NO_UPDATE);
    }

    //GETTER AND SETTER FUNCTIONS
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

    public Item getSelectedItem() {
        return mSelectedItem;
    }
    public void setSelectedItem(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
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

    public boolean isEnemyCaught() {
        return mEnemyCaught;
    }
    public void setEnemyCaught(boolean mIsEnemyCaught) {
        this.mEnemyCaught = mIsEnemyCaught;
    }

    public PokemonProfile getSelectedPokemon() {
        return mSelectedPokemon;
    }
    public void setSelectedPokemon(PokemonProfile mSelectedPokemon) {
        this.mSelectedPokemon = mSelectedPokemon;
    }

    public ArrayList<Type> getTypeChart() {
        return mTypeChart;
    }
    public void setTypeChart(ArrayList<Type> mTypeChart) {
        this.mTypeChart = mTypeChart;
    }
}
