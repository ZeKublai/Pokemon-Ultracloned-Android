package edu.ateneo.cie199.finalproject;

import static java.lang.Math.floor;

/**
 * Created by John on 11/20/2017.
 */

public class DecisionAttack extends Decision {
    public static int DEFAULT_CRITICAL_CHANCE = 16;

    private PokemonProfile mAttacker;
    private PokemonProfile mDefender;
    private Move mMove;
    private int mAccuracyResult;
    private int mCriticalResult;
    private PokemonInfo mDisplayInfo;


    public DecisionAttack(PokemonProfile mAttacker, PokemonProfile mDefender, Move mMove, PokemonInfo info) {
        this.mMove = mMove;
        this.mAccuracyResult = PokemonGoApp.getIntegerRNG(Move.MAX_ACCURACY);
        this.mCriticalResult = PokemonGoApp.getIntegerRNG(DEFAULT_CRITICAL_CHANCE);
        this.mDisplayInfo = info;
        this.mAttacker = mAttacker;
        this.mDefender = mDefender;
    }

    public PokemonInfo getDisplayInfo() {
        return mDisplayInfo;
    }

    public void setDisplayInfo(PokemonInfo mDisplayInfo) {
        this.mDisplayInfo = mDisplayInfo;
    }

    public PokemonProfile getAttacker() {
        return mAttacker;
    }

    public void setAttacker(PokemonProfile mAttacker) {
        this.mAttacker = mAttacker;
    }

    public PokemonProfile getDefender() {
        return mDefender;
    }

    public void setDefender(PokemonProfile mDefender) {
        this.mDefender = mDefender;
    }

    public Move getMove() {
        return mMove;
    }

    public void setMove(Move mMove) {
        this.mMove = mMove;
    }

    public Message getInitialMessage(){
        return new MessageUpdatePokemon(mAttacker.getNickname() + " used " + mMove.getName() + "!", mDisplayInfo, mDefender);
    }

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

    public Message getCriticalMessage(){
        if(mCriticalResult < 1){
            return new Message(Message.MESSAGE_CRITICAL);
        }
        else{
            return new Message();
        }
    }

    public Message getMissedMessage(){
        if(mAccuracyResult < mMove.getAccuracy()){
            return new Message();
        }
        else{
            return new Message(mAttacker.getNickname() + Message.MESSAGE_MISSED);
        }
    }

    @Override
    public Message getErrorMessage(){
        return new Message(Message.ERROR_NO_PP);
    }

    @Override
    public boolean isError(){
        if(mMove.getCurrentPP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void execute(Battle battle){
        mMove.execute(mAccuracyResult, mCriticalResult, mAttacker, mDefender);
    }

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

    @Override
    public boolean isEmpty(){
        return false;
    }

    private PokemonInfo getAttackerInfo(Battle battle){
        if(mDisplayInfo instanceof PokemonInfoBuddy){
            return battle.getEnemyInfo();
        }
        else{
            return battle.getBuddyInfo();
        }
    }
}
