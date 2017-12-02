package edu.ateneo.cie199.finalproject;

import java.util.ArrayList;

/**
 * Created by John on 11/7/2017.
 */

public class Battle {
    protected Player mPlayer = new Player();

    protected Move mSelectedMove;
    protected PokemonProfile mSelectedPokemon = new PokemonProfile();
    protected Item mSelectedItem;
    protected boolean mEnemyCaught = false;

    protected boolean mRanAway = false;
    protected ArrayList<Type> mTypeChart = new ArrayList<>();

    protected Decision mPlayerDecision = new Decision();
    protected Decision mEnemyDecision = new Decision();

    protected PokemonInfo mBuddyInfo;
    protected PokemonInfo mEnemyInfo;

    protected PokemonProfile mBuddy = new PokemonProfile();
    protected PokemonProfile mEnemy = new PokemonProfile();

    protected ArrayList<Message> mMessages = new ArrayList<>();

    protected BattleState mBattleState;

    protected MoveList mMoveAdapter;
    protected PokemonList mPokemonAdapter;
    protected ItemList mItemAdapter;

    protected int mIndex = 0;

    public Battle(){
        this.mMessages = new ArrayList<>();
    }

    public Battle(PokemonGoApp app, PokemonInfoBuddy mBuddyInfo, PokemonInfo mEnemyInfo){
        this.mBuddyInfo = mBuddyInfo;
        this.mEnemyInfo = mEnemyInfo;

        this.mPlayer = app.getPlayer();
        this.mBuddy = app.getPlayer().getBuddy();
        this.mTypeChart = app.getAllTypes();

        this.mEnemy = new PokemonProfile(app.getSpawnCount(), app.getPokemon(app.getCurrentGoal().getTitle()), app.getPlayer().getAverageLevel());
        this.mEnemy.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        this.mEnemy.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        this.mEnemy.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        this.mEnemy.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        this.mEnemyInfo.updatePokemon(this.mEnemy);

        addMessage(new MessageUpdatePokemon("Wild " + this.mEnemy.getNickname() + " appeared!", this.mEnemyInfo, this.mEnemy));
        addMessage(new MessageUpdatePokemon("Go " + this.mBuddy.getNickname() + "!", this.mBuddyInfo, this.mBuddy));
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
            addMessage(mPlayerDecision.getErrorMessage());
            this.mBattleState = mBattleState.standbyState();
        }
        else{
            this.mBattleState = mBattleState.firstMoveState();
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
        if(!isFinished()){
            if(isBuddyFirst()){
                doEnemyDecision();
            }
            else{
                doPlayerDecision();
            }
        }
    }
    public void initializeMessages(){
        setIndex(0);
        mMessages.clear();
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
            this.mBattleState = mBattleState.standbyState();
        }
    }
    public boolean isFinished(){
        return (isEnemyCaught()||isEnemyFainted()||getPlayer().isDefeated()||isRanAway());
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
        this.mBattleState = mBattleState.standbyState();
    }
    public void buddyHasFainted(){
        if(mPlayer.isDefeated()){
            addMessage(new Message(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS1));
            addMessage(new Message(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS2));
        }
        this.mBattleState = mBattleState.standbyState();
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

    public BattleState getBattleState() {
        return mBattleState;
    }
    public void setBattleState(BattleState mBattleState) {
        this.mBattleState = mBattleState;
    }

    public MoveList getMoveAdapter() {
        return mMoveAdapter;
    }
    public void setMoveAdapter(MoveList mMoveAdapter) {
        this.mMoveAdapter = mMoveAdapter;
    }

    public PokemonList getPokemonAdapter() {
        return mPokemonAdapter;
    }
    public void setPokemonAdapter(PokemonList mPokemonAdapter) {
        this.mPokemonAdapter = mPokemonAdapter;
    }

    public ItemList getItemAdapter() {
        return mItemAdapter;
    }
    public void setItemAdapter(ItemList mItemAdapter) {
        this.mItemAdapter = mItemAdapter;
    }
}
