package edu.ateneo.cie199.finalproject;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/7/2017.
 * This class handles the functions to be used for the Battle object.
 */

public class Battle {
    protected Player mPlayer = new Player();

    protected Move mSelectedMove;
    protected PokémonProfile mSelectedPokemon = new PokémonProfile();
    protected Item mSelectedItem;
     boolean mEnemyCaught = false;

    protected boolean mRanAway = false;
    protected ArrayList<Type> mTypeChart = new ArrayList<>();

    protected Decision mPlayerDecision = new Decision();
    protected Decision mEnemyDecision = new Decision();

    protected DisplayInfoSet mBuddyInfo;
    protected DisplayInfoSet mEnemyInfo;

    protected PokémonProfile mBuddy = new PokémonProfile();
    protected PokémonProfile mEnemy = new PokémonProfile();

    protected ArrayList<Message> mMessages = new ArrayList<>();

    protected BattleState mBattleState;

    protected MoveList mMoveAdapter;
    protected PokémonList mPokemonAdapter;
    protected ItemList mItemAdapter;

    protected int mIndex = 0;


    /**
     * Child class TrainerBattle require a default constructor for a parent class battle
     */
    public Battle(){
        this.mMessages = new ArrayList<>();
    }

    /**
     * Initialize the data for the battle
     * @param app sets the moves of the Enemy PokéDexData
     * @param mBuddyInfo gets the HP, Exp of the Buddyy PokéDexData
     * @param mEnemyInfo gets the HP, Exp of the Enemy PokéDexData
     */
    public Battle(PokemonGoApp app, DisplayInfoSetBuddy mBuddyInfo, DisplayInfoSet mEnemyInfo){
        this.mBuddyInfo = mBuddyInfo;
        this.mEnemyInfo = mEnemyInfo;

        this.mPlayer = app.getPlayer();
        this.mBuddy = app.getPlayer().getBuddy();
        this.mTypeChart = app.getAllTypes();

        this.mEnemy = new PokémonProfile(app.getSpawnCount(), app.getPokemon(app.getCurrentGoal().getTitle()), PokemonGoApp.getIntegerRNG(app.getPlayer().getAverageLevel()) + 1);
        this.mEnemy.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        this.mEnemy.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        this.mEnemy.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        this.mEnemy.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        this.mEnemyInfo.updatePokemon(this.mEnemy);

        addMessage(new MessageUpdatePokemon("Wild " + this.mEnemy.getNickname() + " appeared!", this.mEnemyInfo, this.mEnemy));
        addMessage(new MessageUpdatePokemon("Go " + this.mBuddy.getNickname() + "!", this.mBuddyInfo, this.mBuddy));

        this.mBuddyInfo.getImage().setBackgroundResource(mPlayer.getGender().getBackImage());
    }

    /**
     * Check if the Enemy PokéDexData has fainted
     * @return true if the Enemy PokéDexData fainted; false if not
     */
    public boolean isEnemyFainted(){
        if(mEnemy.getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Check if your current pokemon has fainted
     * @return true if the Enemy PokéDexData fainted; false if not
     */
    public boolean isBuddyFainted(){
        if(getBuddy().getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Determines who gets the first move
     * @return true if Buddy pokemon has higher speed; else, Enemy pokemon first
     */
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

    /**
     * Checks if the move choice of the user is invalid
     */
    public void checkErrorMessage(){
        if(mPlayerDecision.isError()){
            addMessage(mPlayerDecision.getErrorMessage());
            this.mBattleState = mBattleState.standbyState();
        }
        else{
            this.mBattleState = mBattleState.firstMoveState();
        }
    }

    /**
     * Resets the stacked instructions of the previous turn
     */
    public void newTurn(){
        getMessages().clear();
        setIndex(0);
        setPlayerDecision(new Decision());
        setEnemyDecision(new Decision());
        mSelectedPokemon = new PokémonProfile();
    }

    /**
     * Sets who attacks first for the said turn
     */
    public void firstMove(){
        if(isBuddyFirst()){
            doPlayerDecision();
        }
        else{
            doEnemyDecision();
        }
    }

    /**
     * Sets who attacks second for the said turn
     */
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

    /**
     * Intializes the stacked instruction set
     */
    public void initializeMessages(){
        setIndex(0);
        mMessages.clear();
        firstMove();
        checkVictory();
        enemyHasBeenCaught();
    }

    /**
     * Generates a move for the enemy PokéDexData
     * @return random move selected for the enemy PokéDexData
     */
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


    /**
     * Runs the selected move of the user.
     * Updates the PokéDexData info such as HP, PP and item count
     */
    public void doPlayerDecision(){
        if(!mPlayerDecision.isError()){
            mPlayerDecision.execute(this);
            mPlayerDecision.updateResults(this);
        }
    }

    /**
     * Runs the random move of the enemy PokéDexData.
     * Updates the PokéDexData info such as HP and PP.
     */
    public void doEnemyDecision(){
        if(!mEnemyDecision.isError()){
            mEnemyDecision.execute(this);
            mEnemyDecision.updateResults(this);
        }
    }

    /**
     * Determines the winner of the battle.
     */
    public void checkVictory(){
        if(isBuddyFainted()){
            buddyHasFainted();
        }
        else if(isEnemyFainted()){
            enemyHasFainted();
        }
    }

    /**
     * Determines that the enemy has been caught. adds PokéDexData to team or box.
     */
    public void enemyHasBeenCaught(){
        if(mEnemyCaught){
            if(mPlayer.getFreeSlot() < Player.MAX_POKéMON_SLOTS){
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

    /**
     * Determines how the activity can end
     * @return selected way of ending the battle
     */
    public boolean isFinished(){
        return (isEnemyCaught()||isEnemyFainted()||getPlayer().isDefeated()||isRanAway());
    }

    /**
     * Sets that the enemy pokemon has fainted.
     */
    public void enemyHasFainted(){
        addMessage(new Message(mEnemy.getNickname() + Message.MESSAGE_FAINTED));
        addMessage(new MessageUpdateExp(mBuddy.getNickname() + " gained " + mEnemy.getLevel()* mBuddy.getLevel() * 10
                + Message.MESSAGE_EXP_GAINED, mBuddyInfo, mBuddy));
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() + mEnemy.getLevel() * mBuddy.getLevel() * 10);
        if(mBuddy.getCurrentExp() >= mBuddy.getExpNeeded() && mBuddy.getLevel() < PokémonProfile.MAX_POKEMON_LEVEL){
            buddyLevelUp();
        }
        this.mBattleState = mBattleState.standbyState();
    }

    /**
     * Sets that your pokemon has fainted.
     */
    public void buddyHasFainted(){
        if(mPlayer.isDefeated()){
            addMessage(new Message(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS1));
            addMessage(new Message(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS2));
        }
        this.mBattleState = mBattleState.standbyState();
    }

    /**
     * Computes for the exp gained of your buddy PokéDexData
     */
    public void buddyLevelUp(){
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() - mBuddy.getExpNeeded());
        mBuddy.setLevel(mBuddy.getLevel() + 1);
        addMessage(new MessageUpdatePokemon(mBuddy.getNickname() + Message.MESSAGE_LEVEL_UP + mBuddy.getLevel() + "!", mBuddyInfo, mBuddy));
        if(mBuddy.getCurrentExp() >= mBuddy.getExpNeeded()){
            buddyLevelUp();
        }
    }

    //GETTER AND SETTER FUNCTIONS

    /**
     * returns the data of the Player
     * @return the Player
     */
    public Player getPlayer() {
        return mPlayer;
    }

    /**
     * sets the Player
     * @param mPlayer the Player data
     */
    public void setPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

    /**
     * get the currently used pokemon
     * @return the first pokemon in the team
     */
    public PokémonProfile getBuddy() {
        return mBuddy;
    }

    /**
     * set the PokéDexData to be used for battle
     * @param mBuddy the first PokéDexData in the team
     */
    public void setBuddy(PokémonProfile mBuddy) {
        this.mBuddy = mBuddy;
    }

    /**
     * gets the current enemy PokéDexData
     * @return data of the enemy PokéDexData
     */
    public PokémonProfile getEnemy() {
        return mEnemy;
    }

    /**
     * sets the current enemy PokéDexData
     * @param mEnemy data of the enemy PokéDexData
     */
    public void setEnemy(PokémonProfile mEnemy) {
        this.mEnemy = mEnemy;
    }

    /**
     * gets the messages to be shown
     * @return the stack of messages
     */
    public ArrayList<Message> getMessages() {
        return mMessages;
    }

    /**
     * get the index of the current message pointed in the arraylist
     * @return the integer of the current index
     */
    public int getIndex() {
        return mIndex;
    }

    /**
     * set the index of the current message pointed in the arraylist
     * @param mIndex the integer of the current index
     */
    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    /**
     * add message to the message list
     * @param message the object message to be added
     */
    public void addMessage(Message message){
        if(!message.isEmpty()){
            message.setContent(message.getContent() + "∇");
            mMessages.add(message);
        }
    }

    /**
     * get the data of te selected item
     * @return the selected item
     */
    public Item getSelectedItem() {
        return mSelectedItem;
    }

    /**
     * set the current selected item
     * @param mSelectedItem the selected item
     */
    public void setSelectedItem(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    /**
     * get the decision of the player
     * @return player's decision
     */
    public Decision getPlayerDecision() {
        return mPlayerDecision;
    }

    /**
     * set the decision of the player
     * @param mPlayerDecision player's decision
     */
    public void setPlayerDecision(Decision mPlayerDecision) {
        this.mPlayerDecision = mPlayerDecision;
    }

    /**
     * Determine if the player ran way or not
     * @return true if the player ran away; false if not
     */
    public boolean isRanAway() {
        return mRanAway;
    }

    /**
     * set if the player ran away or not
     * @param mRanAway true if the player ran away; false if not
     */
    public void setRanAway(boolean mRanAway) {
        this.mRanAway = mRanAway;
    }

    /**
     * get the decision of the enemy pokemon
     * @return enemy PokéDexData's decision
     */
    public Decision getEnemyDecision() {
        return mEnemyDecision;
    }

    /**
     * set the decision of the enemy pokemon
     * @param mEnemyDecision enemy PokéDexData's decision
     */
    public void setEnemyDecision(Decision mEnemyDecision) {
        this.mEnemyDecision = mEnemyDecision;
    }

    /**
     * get the data of the selected buddy PokéDexData
     * @return the buddy PokéDexData
     */
    public DisplayInfoSet getBuddyInfo() {
        return mBuddyInfo;
    }

    /**
     * set the data of the selected buddy PokéDexData
     * @param mBuddyInfo the buddy PokéDexData
     */
    public void setBuddyInfo(DisplayInfoSet mBuddyInfo) {
        this.mBuddyInfo = mBuddyInfo;
    }

    /**
     * get data of the Enemy PokéDexData. HP, PP, etc.
     * @return enemy pokemon's data. HP, PP, etc.
     */
    public DisplayInfoSet getEnemyInfo() {
        return mEnemyInfo;
    }

    /**
     * set data of the Enemy PokéDexData. HP, PP, etc.
     * @param mEnemyInfo enemy pokemon's data. HP, PP, etc.
     */
    public void setEnemyInfo(DisplayInfoSet mEnemyInfo) {
        this.mEnemyInfo = mEnemyInfo;
    }

    /**
     * Determined if the wild PokéDexData is caught
     * @return true if the pokemon is caught; else if not
     */
    public boolean isEnemyCaught() {
        return mEnemyCaught;
    }

    /**
     * set if the wild PokéDexData is caught
     * @param mIsEnemyCaught true if the pokemon is caught; else if not
     */
    public void setEnemyCaught(boolean mIsEnemyCaught) {
        this.mEnemyCaught = mIsEnemyCaught;
    }

    /**
     * get data of the selected pokemon to be switched or used item on.
     * @return selected pokemon
     */
    public PokémonProfile getSelectedPokemon() {
        return mSelectedPokemon;
    }

    /**
     *
     * @param mSelectedPokemon
     */
    public void setSelectedPokemon(PokémonProfile mSelectedPokemon) {
        this.mSelectedPokemon = mSelectedPokemon;
    }

    /**
     * get selected move of the PokéDexData
     * @return selected move of the PokéDexData
     */
    public Move getSelectedMove() {
        return mSelectedMove;
    }

    /**
     * set the selected move of the PokéDexData
     * @param mSelectedMove selected move of the PokéDexData
     */
    public void setSelectedMove(Move mSelectedMove) {
        this.mSelectedMove = mSelectedMove;
    }

    /**
     * get the list of the types for the PokéDexData and its moves
     * @return list of the types for the PokéDexData and its moves
     */
    public ArrayList<Type> getTypeChart() {
        return mTypeChart;
    }

    /**
     * set the the types for the PokéDexData and its moves
     * @param mTypeChart list of the types for the PokéDexData and its moves
     */
    public void setTypeChart(ArrayList<Type> mTypeChart) {
        this.mTypeChart = mTypeChart;
    }

    /**
     * get the state of the battle
     * @return battle state
     */
    public BattleState getBattleState() {
        return mBattleState;
    }

    /**
     * set the current state of the battle
     * @param mBattleState battle state
     */
    public void setBattleState(BattleState mBattleState) {
        this.mBattleState = mBattleState;
    }

    /**
     * get list of Moves of the PokéDexData
     * @return list of PokéDexData moves
     */
    public MoveList getMoveAdapter() {
        return mMoveAdapter;
    }

    /**
     * set the move for the PokéDexData
     * @param mMoveAdapter list of PokéDexData moves
     */
    public void setMoveAdapter(MoveList mMoveAdapter) {
        this.mMoveAdapter = mMoveAdapter;
    }

    /**
     * get list of PokéDexData
     * @return list of PokéDexData
     */
    public PokémonList getPokemonAdapter() {
        return mPokemonAdapter;
    }

    /**
     * set the PokéDexData team for the battle
     * @param mPokemonAdapter list of PokéDexData for the battle
     */
    public void setPokemonAdapter(PokémonList mPokemonAdapter) {
        this.mPokemonAdapter = mPokemonAdapter;
    }

    /**
     * get the list of Item
     * @return list of Item
     */
    public ItemList getItemAdapter() {
        return mItemAdapter;
    }

    /**
     * set the list of Item of the player
     * @param mItemAdapter list of Item
     */
    public void setItemAdapter(ItemList mItemAdapter) {
        this.mItemAdapter = mItemAdapter;
    }
}
