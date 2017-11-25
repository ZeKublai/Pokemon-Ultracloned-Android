package edu.ateneo.cie199.finalproject;

import java.util.ArrayList;

import static java.lang.Math.floor;

/**
 * Created by John on 11/7/2017.
 */

public class Battle {
    private Player mPlayer = new Player();

    private Move mSelectedMove;
    private PokemonProfile mSelectedPokemon = new PokemonProfile();
    private Item mSelectedItem;
    private boolean mEnemyCaught = false;

    private boolean mRanAway = false;
    private ArrayList<Type> mTypeChart = new ArrayList<>();

    private Decision mPlayerDecision = new Decision();
    private Decision mEnemyDecision = new Decision();

    private PokemonInfo mBuddyInfo;
    private PokemonInfo mEnemyInfo;

    private PokemonProfile mBuddy = new PokemonProfile();
    private PokemonProfile mEnemy = new PokemonProfile();

    private ArrayList<Message> mMessages = new ArrayList<>();
    private int mState = PokemonGoApp.STATE_MESSAGE_LAST;
    private int mIndex = 0;

    public Battle(){
        this.mState = PokemonGoApp.STATE_MESSAGE_LAST;
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
        if(!(mPlayerDecision instanceof DecisionAttack)){
            return true;
        }
        else if(!(mEnemyDecision instanceof DecisionAttack)){
            return false;
        }
        else{
            if(mBuddy.getSpeed() > mEnemy.getSpeed()){
                return true;
            }
            else if(mBuddy.getSpeed() == mEnemy.getSpeed()){
                if(PokemonGoApp.getIntegerRNG(2) > 0){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
    }

    //BATTLE FUNCTIONS
    public void checkErrorMessage(){
        if(mPlayerDecision.isError()){
            mMessages.clear();
            addMessage(mPlayerDecision.getErrorMessage());
            mState = PokemonGoApp.STATE_MESSAGE_LAST;
        }
    }
    public void newTurn(){
        getMessages().clear();
        setIndex(0);
        setPlayerDecision(new Decision());
        setEnemyDecision(new Decision());
        mSelectedPokemon = new PokemonProfile();
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
        if(isBuddyFirst() && !isFinished()){
            doEnemyDecision();
        }
        else{
            doPlayerDecision();
        }
    }
    public void initializeMessages(){
        setIndex(0);
        checkErrorMessage();
        firstMove();
        checkVictory();
        checkCaught();
    }
    public Decision generateEnemyDecision(){
        if(getEnemy().noMorePP()){
            return new DecisionAttack(mEnemy, mBuddy, new MoveStruggle(this), getBuddyInfo());
        }
        Move move = getEnemy().getMoves().get(PokemonGoApp.getIntegerRNG(4));
        while(move.getCurrentPP() <= 0){
            move = getEnemy().getMoves().get(PokemonGoApp.getIntegerRNG(4));
        }
        return new DecisionAttack(getEnemy(), getBuddy(), move, getBuddyInfo());
    }


    //BATTLE OPTIONS
    public void doPlayerDecision(){
        if(!mPlayerDecision.isError()){
            mPlayerDecision.execute(this);
            mPlayerDecision.updateResults(this);
        }
    }
    public void doEnemyDecision(){
        if(!mEnemyDecision.isError()){
            mEnemyDecision.execute(this);
            mEnemyDecision.updateResults(this);
        }
    }

    //END BATTLE
    public void checkVictory(){
        if(isBuddyFainted()){
            buddyHasFainted();
        }
        else if(isEnemyFainted()){
            enemyHasFainted();
        }
    }
    public void checkCaught(){
        if(mEnemyCaught){
            if(mPlayer.getFreeSlot() < Player.MAX_POKEMON_SLOTS){
                mPlayer.getPokemons().add(mEnemy);
                addMessage(new Message(mEnemy.getNickname() + Message.MESSAGE_TO_PARTY));
            }
            else{
                mPlayer.getBox().add(mEnemy);
                addMessage(new Message(mEnemy.getNickname() + Message.MESSAGE_TO_BOX));
            }

            mState = PokemonGoApp.STATE_MESSAGE_LAST;
        }
    }
    public boolean isFinished(){
        return (isEnemyCaught()||isEnemyFainted()||getPlayer().isPlayerDefeated()||mRanAway);
    }
    public void enemyHasFainted(){
        //TODO ADD MONEY REWARD IF TRAINER
        addMessage(new Message(mEnemy.getNickname() + Message.MESSAGE_FAINTED));
        addMessage(new MessageUpdateExp(mBuddy.getNickname() + " gained " + mEnemy.getLevel()* mBuddy.getLevel() * 10
                + Message.MESSAGE_EXP_GAINED, mBuddyInfo, mBuddy));
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() + mEnemy.getLevel() * mBuddy.getLevel() * 10);
        if(mBuddy.getCurrentExp() >= mBuddy.getExperienceNeeded()){
            buddyLevelUp();
        }
        mState = PokemonGoApp.STATE_MESSAGE_LAST;
    }
    public void buddyHasFainted(){
        if(mPlayer.isPlayerDefeated()){
            playerLoses();
        }
        mState = PokemonGoApp.STATE_MESSAGE_LAST;
    }
    public void playerLoses(){
        addMessage(new Message(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS1));
        addMessage(new Message(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS2));
    }
    public void buddyLevelUp(){
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() - mBuddy.getExperienceNeeded());
        mBuddy.setLevel(mBuddy.getLevel() + 1);
        addMessage(new MessageUpdatePokemon(mBuddy.getNickname() + Message.MESSAGE_LEVEL_UP + mBuddy.getLevel() + "!", mBuddyInfo, mBuddy));
        if(mBuddy.getCurrentExp() >= mBuddy.getExperienceNeeded()){
            buddyLevelUp();
        }
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

    public void addMessage(Message message){
        if(!message.isEmpty()){
            message.setMessage(message.getMessage() + "∇");
            mMessages.add(message);
        }
    }

    public Item getSelectedItem() {
        return mSelectedItem;
    }
    public void setSelectedItem(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    public Decision getPlayerDecision() {
        return mPlayerDecision;
    }
    public void setPlayerDecision(Decision mPlayerDecision) {
        this.mPlayerDecision = mPlayerDecision;
    }

    public boolean isRanAway() {
        return mRanAway;
    }
    public void setRanAway(boolean mRanAway) {
        this.mRanAway = mRanAway;
    }

    public Decision getEnemyDecision() {
        return mEnemyDecision;
    }
    public void setEnemyDecision(Decision mEnemyDecision) {
        this.mEnemyDecision = mEnemyDecision;
    }

    public PokemonInfo getBuddyInfo() {
        return mBuddyInfo;
    }
    public void setBuddyInfo(PokemonInfo mBuddyInfo) {
        this.mBuddyInfo = mBuddyInfo;
    }

    public PokemonInfo getEnemyInfo() {
        return mEnemyInfo;
    }
    public void setEnemyInfo(PokemonInfo mEnemyInfo) {
        this.mEnemyInfo = mEnemyInfo;
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

    public Move getSelectedMove() {
        return mSelectedMove;
    }
    public void setSelectedMove(Move mSelectedMove) {
        this.mSelectedMove = mSelectedMove;
    }

    public ArrayList<Type> getTypeChart() {
        return mTypeChart;
    }
    public void setTypeChart(ArrayList<Type> mTypeChart) {
        this.mTypeChart = mTypeChart;
    }
}
