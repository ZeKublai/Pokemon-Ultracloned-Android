package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/20/2017.
 */

public class DecisionSwitch extends Decision {

    private PokemonProfile mNewBuddy;
    private PokemonProfile mCurrentBuddy;
    private PokemonInfo mDisplayInfo;

    public DecisionSwitch(){

    }

    public DecisionSwitch(PokemonProfile mNewBuddy, PokemonProfile mCurrentBuddy, PokemonInfo info) {
        this.mNewBuddy = mNewBuddy;
        this.mCurrentBuddy = mCurrentBuddy;
        this.mDisplayInfo = info;
    }

    public PokemonProfile getProfile() {
        return mNewBuddy;
    }

    public void setProfile(PokemonProfile mProfile) {
        this.mNewBuddy = mProfile;
    }

    public PokemonProfile getCurrentBuddy() {
        return mCurrentBuddy;
    }

    public void setCurrentBuddy(PokemonProfile mCurrentBuddy) {
        this.mCurrentBuddy = mCurrentBuddy;
    }

    @Override
    public boolean isError(){
        if(mNewBuddy.getCurrentHP() <= 0){
            return true;
        }
        else{
            if(mCurrentBuddy.equals(mNewBuddy)){
                return true;
            }
            else{
                return false;
            }
        }
    }

    @Override
    public Message getErrorMessage(){
        if(mNewBuddy.getCurrentHP() <= 0){
            return new Message(mNewBuddy.getNickname() + Message.ERROR_FAINTED);
        }
        else{
            if(mCurrentBuddy.equals(mNewBuddy)){
                return new Message(mNewBuddy.getNickname() + Message.ERROR_IN_BATTLE);
            }
            else{
                return new Message();
            }
        }
    }

    @Override
    public void execute(Battle battle){
        battle.setBuddy(mNewBuddy);
        if(battle.getEnemyDecision() instanceof DecisionAttack){
            ((DecisionAttack) battle.getEnemyDecision()).setDefender(mNewBuddy);
            
        }
    }

    @Override
    public void updateResults(Battle battle){
        battle.addMessage(new Message(Message.MESSAGE_SWITCH1 + mCurrentBuddy.getNickname() + "!"));
        battle.addMessage(new MessageUpdatePokemon(Message.MESSAGE_SWITCH2 + mNewBuddy.getNickname() + "!", mDisplayInfo, mNewBuddy));
    }

    @Override
    public boolean isEmpty(){
        return false;
    }

}
