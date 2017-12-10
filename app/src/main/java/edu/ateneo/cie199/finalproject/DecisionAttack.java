package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/20/2017.
 * This class is a subclass of the decision which handles the decision and computation for damaging
 * Updates the data of the battling PokéDexData
 */

public class DecisionAttack extends Decision {

    private PokémonProfile mAttacker;
    private PokémonProfile mDefender;
    private Move mMove;
    private int mAccuracyResult;
    private int mCriticalResult;
    private DisplayInfoSet mDisplayInfo;


    /**
     * Handles the method of triggering accuracy nd critical hit of the pokemon
     * @param mAttacker the pokemon that attacks
     * @param mDefender the pokemon that defends
     * @param mMove move to be analyzed if a special event is triggered
     * @param info show the information of the PokéDexData
     */
    public DecisionAttack(PokémonProfile mAttacker, PokémonProfile mDefender, Move mMove, DisplayInfoSet info) {
        this.mMove = mMove;
        this.mAccuracyResult = PokemonGoApp.getIntegerRNG(Move.MAX_ACCURACY);
        this.mCriticalResult = PokemonGoApp.getIntegerRNG(Move.MAX_CRITICAL);
        this.mDisplayInfo = info;
        this.mAttacker = mAttacker;
        this.mDefender = mDefender;
    }

    /**
     * set the defending PokéDexData
     * @param mDefender the pokemon that defends
     */
    public void setDefender(PokémonProfile mDefender) {
        this.mDefender = mDefender;
    }

    /**
     * get the pokemon move
     * @return pokemon move
     */
    public Move getMove() {
        return mMove;
    }

    /**
     * set the pokemon move
     * @param mMove pokemon move
     */
    public void setMove(Move mMove) {
        this.mMove = mMove;
    }

    /**
     * get the initial messages
     * @return message to be shown
     */
    public Message getInitialMessage(){
        return new MessageUpdatePokemon(mAttacker.getNickname() + " used " + mMove.getName() + "!", mDisplayInfo, mDefender);
    }

    /**
     * show if the move is effective to the pokemon
     * @return message to be shown
     */
    public Message getEffectiveMessage(){
        Type defenderType1 = mDefender.getDexData().getType1();
        Type defenderType2 = mDefender.getDexData().getType2();
        double typeMultiplier1 = mMove.getType().getMultiplier()[defenderType1.getId()];
        double typeMultiplier2 = mMove.getType().getMultiplier()[defenderType2.getId()];
        double totalTypeMultiplier = typeMultiplier1*typeMultiplier2;
        if(totalTypeMultiplier >= 2){
            return new Message(Message.MESSAGE_SUPER_EFFECTIVE);
        }
        else if(totalTypeMultiplier <= 0.5 && totalTypeMultiplier > 0){
            return new Message(Message.MESSAGE_NOT_EFFECTIVE);
        }
        else if(totalTypeMultiplier == 0){
            return new Message(Message.MESSAGE_NO_EFFECT + mDefender.getNickname() + "...");
        }
        else{
            return new Message();
        }
    }

    /**
     * show if the move is a critical hit
     * @return message to be shown
     */
    public Message getCriticalMessage(){
        if(mCriticalResult < 1){
            return new Message(Message.MESSAGE_CRITICAL);
        }
        else{
            return new Message();
        }
    }

    /**
     * show if the move misses
     * @return message to be shown
     */
    public Message getMissedMessage(){
        if(mAccuracyResult < mMove.getAccuracy()){
            return new Message();
        }
        else{
            return new Message(mAttacker.getNickname() + Message.MESSAGE_MISSED);
        }
    }

    /**
     * show error message
     * @return messgae to be shown when no more PP
     */
    @Override
    public Message getErrorMessage(){
        return new Message(Message.ERROR_NO_PP);
    }

    /**
     * check if there is still error
     * @return boolean value to check if there is still PP
     */
    @Override
    public boolean isError(){
        if(mMove.getCurrentPP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Executes the move done
     * @param battle the battle where the state is
     */
    @Override
    public void execute(Battle battle){
        mMove.execute(mAccuracyResult, mCriticalResult, mAttacker, mDefender);
    }

    /**
     * updates the results
     * @param battle the battle where the state is
     */
    @Override
    public void updateResults(Battle battle){
        if(mAttacker.noMorePP()){
            battle.addMessage(new Message(mAttacker.getNickname() + " has no PP left!"));
        }
        battle.addMessage(getInitialMessage());
        if(mAccuracyResult < mMove.getAccuracy()){
            battle.addMessage(getEffectiveMessage());
            battle.addMessage(getCriticalMessage());
        }
        else{
            battle.addMessage(getMissedMessage());
        }
        if(mMove.executeRecoil(mAttacker)){
            battle.addMessage(new MessageUpdatePokemon(mAttacker.getNickname() + " is damaged by recoil!", getAttackerInfo(battle), mAttacker));
        }
        if(battle.isBuddyFainted()){
            battle.addMessage(new MessageUpdatePokemon(battle.getBuddy().getNickname() + Message.MESSAGE_FAINTED, battle.getBuddyInfo(), battle.getBuddy()));
        }
    }

    /**
     * check if decision is empty
     * @return boolean value with a default value of false
     */
    @Override
    public boolean isEmpty(){
        return false;
    }

    /**
     * get information of the attacking pokemon
     * @param battle the battle where the state is
     * @return information of the attacking pokemon
     */
    private DisplayInfoSet getAttackerInfo(Battle battle){
        if(mDisplayInfo instanceof DisplayInfoSetBuddy){
            return battle.getEnemyInfo();
        }
        else{
            return battle.getBuddyInfo();
        }
    }
}
