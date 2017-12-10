package edu.ateneo.cie199.finalproject;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/7/2017.
 * This class handles the functions to be used for the Battle object.
 */

public class Battle {
    protected Player mPlayer = new Player();
    protected BattleState mBattleState;
    
    protected PokémonProfile mSelectedPokémon = new PokémonProfile();
    protected Move mSelectedMove;
    protected Item mSelectedItem;

    protected boolean mEnemyCaught = false;
    protected boolean mRanAway = false;
    
    protected ArrayList<Type> mTypeChart = new ArrayList<>();

    protected Decision mPlayerDecision = new Decision();
    protected Decision mEnemyDecision = new Decision();

    protected DisplayInfoSet mBuddyInfo;
    protected DisplayInfoSet mEnemyInfo;

    protected PokémonProfile mBuddy = new PokémonProfile();
    protected PokémonProfile mEnemy = new PokémonProfile();

    protected ArrayList<Message> mMessages = new ArrayList<>();
    protected int mMessageIndex = 0;

    protected MoveList mMoveAdapter;
    protected PokémonList mPokémonAdapter;
    protected ItemList mItemAdapter;
    
    /**
     * Child class TrainerBattle requires a default constructor for a parent class battle.
     */
    public Battle(){
        this.mMessages = new ArrayList<>();
    }

    /**
     * Initializes the data for the battle.
     * @param app           The PokemonApp Application class used to generate the Pokémon.
     * @param mBuddyInfo    The DisplayInfoSet that would be used for the Player's Pokémon.
     * @param mEnemyInfo    The DisplayInfoSet that would be used for the Trainer's Pokémon.
     */
    public Battle(PokemonApp app, DisplayInfoSetBuddy mBuddyInfo, DisplayInfoSet mEnemyInfo){
        this.mBuddyInfo = mBuddyInfo;
        this.mEnemyInfo = mEnemyInfo;

        this.mPlayer = app.getPlayer();
        this.mBuddy = app.getPlayer().getBuddy();
        this.mTypeChart = app.getAllTypes();

        this.mEnemy = new PokémonProfile(
                app.getSpawnCount(),
                app.getPokemon(app.getCurrentGoal().getTitle()),
                PokemonApp.getIntegerRNG(app.getPlayer().getAverageLevel()) + 1
        );
        this.mEnemy.getMoves().add(app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size())).generateCopy()
        );
        this.mEnemy.getMoves().add(app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size())).generateCopy()
        );
        this.mEnemy.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(
                app.getAllMoves().size())).generateCopy()
        );
        this.mEnemy.getMoves().add(app.getAllMoves().get(
                app.getIntegerRNG(app.getAllMoves().size())).generateCopy()
        );
        this.mEnemyInfo.updatePokemon(this.mEnemy);

        addMessage(new MessageUpdatePokemon(
                "Wild "
                + this.mEnemy.getNickname()
                + " appeared!",
                this.mEnemyInfo,
                this.mEnemy
        ));
        addMessage(new MessageUpdatePokemon(
                "Go "
                + this.mBuddy.getNickname()
                + "!",
                this.mBuddyInfo,
                this.mBuddy
        ));

        this.mBuddyInfo.getImage().setBackgroundResource(mPlayer.getGender().getBackImage());
    }

    /**
     * Checks if the enemy Pokémon has fainted.
     * @return  True if the enemy Pokémon has fainted else false.
     */
    public boolean isEnemyFainted(){
        return (mEnemy.getCurrentHP() <= 0);
    }

    /**
     * CheckS if the Player's current Pokémon has fainted.
     * @return  True if the Player's current Pokémon has fainted else false.
     */
    public boolean isBuddyFainted(){
        return (getBuddy().getCurrentHP() <= 0);
    }

    /**
     * Returns if the Player makes the first move.
     * @return  True if Player uses an item, switches Pokémon
     *          or buddy Pokémon has a higher speed else false.
     */
    private boolean isBuddyFirst(){
        if(!(mPlayerDecision instanceof DecisionAttack)){
            return true;
        }
        else{
            if(mBuddy.getSpeed() > mEnemy.getSpeed()){
                return true;
            }
            else if(mBuddy.getSpeed() == mEnemy.getSpeed()){
                if(PokemonApp.getIntegerRNG(2) > 0){
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
     * Checks if the move choice of the Player is valid.
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
     * Resets the Message ArrayList to prepare for the next turn.
     */
    public void newTurn(){
        getMessages().clear();
        setMessageIndex(0);
        setPlayerDecision(new Decision());
        setEnemyDecision(new Decision());
        mSelectedPokémon = new PokémonProfile();
    }

    /**
     * Executes the first move.
     */
    private void firstMove(){
        if(isBuddyFirst()){
            doPlayerDecision();
        }
        else{
            doEnemyDecision();
        }
    }

    /**
     * Executes the second move.
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
     * Initializes the Message ArrayList for the first move set.
     */
    public void initializeMessages(){
        setMessageIndex(0);
        mMessages.clear();
        firstMove();
        checkVictory();
        enemyHasBeenCaught();
    }

    /**
     * Selects a Move for the enemy Pokémon and sets the enemy's Decision.
     * @return  A random Move selected from the enemy Pokémon's Move list.
     */
    public Decision generateEnemyDecision(){
        if(getEnemy().noMorePP()){
            return new DecisionAttack(mEnemy, mBuddy, new MoveStruggle(this), getBuddyInfo());
        }
        Move move = getEnemy().getMoves().get(PokemonApp.getIntegerRNG(4));
        while(move.getCurrentPP() <= 0){
            move = getEnemy().getMoves().get(PokemonApp.getIntegerRNG(4));
        }
        return new DecisionAttack(getEnemy(), getBuddy(), move, getBuddyInfo());
    }


    /**
     * If the Player's Decision is valid, executes the Player's Decision.
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
    private void doEnemyDecision(){
        if(!mEnemyDecision.isError()){
            mEnemyDecision.execute(this);
            mEnemyDecision.updateResults(this);
        }
    }

    /**
     * Add Messages if either the enemy Pokémon or the Player's current Pokémon has been defeated.
     */
    public void checkVictory(){
        if(isBuddyFainted()){
            buddyHasFainted();
        }
        else if(isEnemyFainted()){
            rewardPlayer();
        }
    }

    /**
     * Checks if the enemy Pokémon has been caught and if so, adds it to the Player's party or box.
     */
    private void enemyHasBeenCaught(){
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
     * Returns true the battle is finished.
     * @return  True if the battle is finished.
     */
    public boolean isFinished(){
        return (isEnemyCaught()||isEnemyFainted()||getPlayer().isDefeated()||isRanAway());
    }

    /**
     * Rewards the Player's Pokémon with EXP when Pokémon foe has been defeated .
     */
    protected void rewardPlayer(){
        addMessage(new Message(mEnemy.getNickname() + Message.MESSAGE_FAINTED));
        addMessage(new MessageUpdateExp(
                mBuddy.getNickname()
                + " gained "
                + mEnemy.getLevel()* mBuddy.getLevel() * 10
                + Message.MESSAGE_EXP_GAINED,
                mBuddyInfo,
                mBuddy
        ));
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() + mEnemy.getLevel() * mBuddy.getLevel() * 10);
        if(mBuddy.getCurrentExp() >= mBuddy.getExpNeeded()
                && mBuddy.getLevel() < PokémonProfile.MAX_POKEMON_LEVEL){
            buddyLevelUp();
        }
        this.mBattleState = mBattleState.standbyState();
    }

    /**
     * Executes if the Player is out of Pokémon.
     */
    protected void buddyHasFainted(){
        if(mPlayer.isDefeated()){
            addMessage(new Message(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS1));
            addMessage(new Message(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS2));
        }
        this.mBattleState = mBattleState.standbyState();
    }

    /**
     * Computes and gives EXP to the Player's current Pokémon
     * and if enough EXP is given, the Pokémon levels up.
     */
    protected void buddyLevelUp(){
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() - mBuddy.getExpNeeded());
        mBuddy.setLevel(mBuddy.getLevel() + 1);
        addMessage(new MessageUpdatePokemon(
                mBuddy.getNickname()
                + Message.MESSAGE_LEVEL_UP
                + mBuddy.getLevel()
                + "!",
                mBuddyInfo,
                mBuddy
        ));
        if(mBuddy.getCurrentExp() >= mBuddy.getExpNeeded()){
            buddyLevelUp();
        }
    }

    /**
     * Returns the Player of the Battle object.
     * @return  The Player of the Battle object.
     */
    public Player getPlayer() {
        return mPlayer;
    }

    /**
     * Returns the current Pokémon the Player is using.
     * @return  The current Pokémon the Player is using.
     */
    public PokémonProfile getBuddy() {
        return mBuddy;
    }

    /**
     * Sets the Pokémon the Player will use in Battle.
     * @param mBuddy    The Player's Pokémon that will be set.
     */
    public void setBuddy(PokémonProfile mBuddy) {
        this.mBuddy = mBuddy;
    }

    /**
     * Returns the current enemy Pokémon.
     * @return  The current enemy Pokémon.
     */
    public PokémonProfile getEnemy() {
        return mEnemy;
    }

    /**
     * Sets the current enemy Pokémon.
     * @param mEnemy The enemy Pokémon to be set.
     */
    public void setEnemy(PokémonProfile mEnemy) {
        this.mEnemy = mEnemy;
    }

    /**
     * Returns the ArrayList of the Messages to be shown.
     * @return  The ArrayList of Messages of the Battle object.
     */
    public ArrayList<Message> getMessages() {
        return mMessages;
    }

    /**
     * Gets the index of the current Message pointed in the ArrayList.
     * @return  The value of the current Message index.
     */
    public int getMessageIndex() {
        return mMessageIndex;
    }

    /**
     * Sets the index of the current Message of the ArrayList.
     * @param mIndex    The value of the current Message index to be set.
     */
    public void setMessageIndex(int mIndex) {
        this.mMessageIndex = mIndex;
    }

    /**
     * Adds a new Message to the Message ArrayList.
     * @param message   The Message object to be added.
     */
    public void addMessage(Message message){
        if(!message.isEmpty()){
            message.setContent(message.getContent() + "∇");
            mMessages.add(message);
        }
    }

    /**
     * Gets the Player's selected Item.
     * @return  The Player's selected Item.
     */
    public Item getSelectedItem() {
        return mSelectedItem;
    }

    /**
     * Sets the Player's selected Item.
     * @param mSelectedItem The Player's selected Item to be set.
     */
    public void setSelectedItem(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    /**
     * Gets the Decision of the Player.
     * @return  The Player's Decision.
     */
    public Decision getPlayerDecision() {
        return mPlayerDecision;
    }

    /**
     * Sets the Decision of the Player.
     * @param mPlayerDecision   The Player's Decision to be set.
     */
    public void setPlayerDecision(Decision mPlayerDecision) {
        this.mPlayerDecision = mPlayerDecision;
    }

    /**
     * Returns if the Player wants to run away or not.
     * @return  True if the Player wants to run away else false.
     */
    protected boolean isRanAway() {
        return mRanAway;
    }

    /**
     * Sets the boolean value if the Player wants to run away or not.
     * @param mRanAway  The boolean value if the Player wants to run away or not.
     */
    public void setRanAway(boolean mRanAway) {
        this.mRanAway = mRanAway;
    }

    /**
     * Returns the Decision of the enemy Pokémon.
     * @return  The enemy Pokémon's Decision.
     */
    public Decision getEnemyDecision() {
        return mEnemyDecision;
    }

    /**
     * Sets the Decision of the enemy Pokémon.
     * @param mEnemyDecision    The enemy Pokémon's Decision to be set.
     */
    public void setEnemyDecision(Decision mEnemyDecision) {
        this.mEnemyDecision = mEnemyDecision;
    }

    /**
     * Gets the DisplayInfoSet of the Player's current Pokémon.
     * @return  The DisplayInfoSet of the Player's current Pokémon.
     */
    public DisplayInfoSet getBuddyInfo() {
        return mBuddyInfo;
    }

    /**
     * Sets the DisplayInfoSet of the Player's current Pokémon.
     * @param mBuddyInfo    The DisplayInfoSet of the Player's current Pokémon to be set.
     */
    public void setBuddyInfo(DisplayInfoSet mBuddyInfo) {
        this.mBuddyInfo = mBuddyInfo;
    }

    /**
     * Gets the DisplayInfoSet of the enemy Pokémon.
     * @return  The DisplayInfoSet of the enemy Pokémon.
     */
    public DisplayInfoSet getEnemyInfo() {
        return mEnemyInfo;
    }

    /**
     * Sets the DisplayInfoSet of the enemy Pokémon.
     * @param mEnemyInfo    The DisplayInfoSet of the enemy Pokémon to be set.
     */
    public void setEnemyInfo(DisplayInfoSet mEnemyInfo) {
        this.mEnemyInfo = mEnemyInfo;
    }

    /**
     * Returns if the wild Pokémon is caught.
     * @return  True if the wild Pokémon is caught else false.
     */
    private boolean isEnemyCaught() {
        return mEnemyCaught;
    }

    /**
     * Sets the boolean value if the wild Pokémon is caught.
     * @param mIsEnemyCaught    The boolean value to be set if the wild Pokémon is caught.
     */
    public void setEnemyCaught(boolean mIsEnemyCaught) {
        this.mEnemyCaught = mIsEnemyCaught;
    }

    /**
     * Gets the Player's selected Pokémon to be switched or used an item on.
     * @return  The Player's selected Pokémon.
     */
    public PokémonProfile getSelectedPokemon() {
        return mSelectedPokémon;
    }

    /**
     * Sets the Player's selected Pokémon.
     * @param mSelectedPokemon  The selected Pokémon to be set.
     */
    public void setSelectedPokemon(PokémonProfile mSelectedPokemon) {
        this.mSelectedPokémon = mSelectedPokemon;
    }

    /**
     * Gets Player's selected Move of the Pokémon.
     * @return  The Player's selected Move of the Pokémon.
     */
    public Move getSelectedMove() {
        return mSelectedMove;
    }

    /**
     * Sets the Player's selected Move of the Pokémon.
     * @param mSelectedMove The Player's selected Move of the Pokémon to be set.
     */
    public void setSelectedMove(Move mSelectedMove) {
        this.mSelectedMove = mSelectedMove;
    }

    /**
     * Gets the ArrayList of Types for the Pokémon and Moves.
     * @return  The ArrayList of types for the Pokémon and Moves.
     */
    public ArrayList<Type> getTypeChart() {
        return mTypeChart;
    }

    /**
     * Returns the state of the Battle object.
     * @return  The BattleState object of the Battle.
     */
    public BattleState getBattleState() {
        return mBattleState;
    }

    /**
     * Set the current state of the Battle object.
     * @param mBattleState  The BattleState object to be set.
     */
    public void setBattleState(BattleState mBattleState) {
        this.mBattleState = mBattleState;
    }

    /**
     * Returns a MoveList object that contains the list of Moves of the Player's current Pokémon.
     * @return  A MoveList object that contains the list of Moves of the Player's current Pokémon.
     */
    public MoveList getMoveAdapter() {
        return mMoveAdapter;
    }

    /**
     * Set the MoveList of the Battle object.
     * @param mMoveAdapter  The MoveList object to be set.
     */
    public void setMoveAdapter(MoveList mMoveAdapter) {
        this.mMoveAdapter = mMoveAdapter;
    }

    /**
     * Gets a PokémonList object that contains all the Pokémon in the Player's party.
     * @return  A PokémonList object that contains all the Pokémon in the Player's party.
     */
    public PokémonList getPokemonAdapter() {
        return mPokémonAdapter;
    }

    /**
     * Set the PokémonList object that contains all the Pokémon in the Player's party.
     * @param mPokemonAdapter   The PokémonList object to be set.
     */
    public void setPokemonAdapter(PokémonList mPokemonAdapter) {
        this.mPokémonAdapter = mPokemonAdapter;
    }

    /**
     * Gets a ItemList object that contains all the Items in the Player's bag.
     * @return  A ItemList object that contains all the Items in the Player's bag.
     */
    public ItemList getItemAdapter() {
        return mItemAdapter;
    }

    /**
     * Set the ItemList object that contains all the Items in the Player's bag.
     * @param mItemAdapter  The ItemList object to be set.
     */
    public void setItemAdapter(ItemList mItemAdapter) {
        this.mItemAdapter = mItemAdapter;
    }
}
