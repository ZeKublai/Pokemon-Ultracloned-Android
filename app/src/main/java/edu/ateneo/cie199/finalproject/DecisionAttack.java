package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/20/2017.
 * This class is a subclass of the Decision object which handles the decision
 * and computation for damaging and updating the data of the battling Pokémon.
 */

public class DecisionAttack extends Decision {
    private PokémonProfile mAttacker;
    private PokémonProfile mDefender;
    private Move mMove;
    private int mAccuracyResult;
    private int mCriticalResult;
    private DisplayInfoSet mDisplayInfo;

    /**
     * Creates the DecisionAttack object and handles the method
     * of generating the accuracy and critical hit of the Move.
     * @param mAttacker The attacking Pokémon.
     * @param mDefender The defending Pokémon.
     * @param mMove     The Move that the attacking Pokémon is using.
     * @param info      The DisplayInfoSet to be updated after execution.
     */
    public DecisionAttack(PokémonProfile mAttacker,
                          PokémonProfile mDefender,
                          Move mMove,
                          DisplayInfoSet info) {
        this.mMove = mMove;
        this.mAccuracyResult = PokemonApp.getIntegerRNG(Move.MAX_ACCURACY);
        this.mCriticalResult = PokemonApp.getIntegerRNG(Move.MAX_CRITICAL);
        this.mDisplayInfo = info;
        this.mAttacker = mAttacker;
        this.mDefender = mDefender;
    }

    /**
     * Sets the defending Pokémon.
     * @param mDefender The defending Pokémon to be set.
     */
    public void setDefender(PokémonProfile mDefender) {
        this.mDefender = mDefender;
    }

    /**
     * Returns the initial Message.
     * @return  The initial Message to be shown.
     */
    private Message getInitialMessage(){
        return new MessageUpdatePokemon(
                mAttacker.getNickname()
                + " used "
                + mMove.getName()
                + "!",
                mDisplayInfo,
                mDefender
        );
    }

    /**
     * Returns the Message on how effective the execution of the Move is.
     * @return  The Message on how effective the execution of the Move is.
     */
    private Message getEffectiveMessage(){
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
     * Returns the critical Message if the Move is a critical hit.
     * @return  The critical Message if the Move is a critical hit.
     */
    private Message getCriticalMessage(){
        if(mCriticalResult < 1){
            return new Message(Message.MESSAGE_CRITICAL);
        }
        else{
            return new Message();
        }
    }

    /**
     * Returns the missed Message if the Move is a miss.
     * @return  The missed Message if the Move is a miss.
     */
    private Message getMissedMessage(){
        if(mAccuracyResult < mMove.getAccuracy()){
            return new Message();
        }
        else{
            return new Message(mAttacker.getNickname() + Message.MESSAGE_MISSED);
        }
    }

    /**
     * Returns the error Message.
     * @return  The error Message.
     */
    @Override
    public Message getErrorMessage(){
        return new Message(Message.ERROR_NO_PP);
    }

    /**
     * Checks if the Decision has an error.
     * @return  True if the Decision has an error else false.
     */
    @Override
    public boolean isError(){
        return (mMove.getCurrentPP() <= 0);
    }

    /**
     * Executes the Move done.
     * @param battle    The Battle object where the Decision is executed.
     */
    @Override
    public void execute(Battle battle){
        mMove.execute(mAccuracyResult, mCriticalResult, mAttacker, mDefender);
    }

    /**
     * Updates the results of the Decision and adds Messages to the ArrayList in the Battle class.
     * @param battle    The Battle object where the Decision is executed.
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
            battle.addMessage(new MessageUpdatePokemon(
                    mAttacker.getNickname()
                    + " is damaged by recoil!",
                    getAttackerInfo(battle),
                    mAttacker
            ));
        }
        if(battle.isBuddyFainted()){
            battle.addMessage(new MessageUpdatePokemon(
                    battle.getBuddy().getNickname()
                    + Message.MESSAGE_FAINTED,
                    battle.getBuddyInfo(),
                    battle.getBuddy()
            ));
        }
    }

    /**
     * Returns the DisplayInfoSet of the attacking Pokémon.
     * @param battle    The Battle object where the Decision is executed.
     * @return          The DisplayInfoSet of the attacking Pokémon.
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
