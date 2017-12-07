package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/20/2017.
 * This class is a subclass of the decision which handles the decision for switching
 */

public class DecisionSwitch extends Decision {

    private PokemonProfile mNewBuddy;
    private PokemonProfile mCurrentBuddy;
    private PokemonInfo mDisplayInfo;

    /**
     * does nothing
     */
    public DecisionSwitch(){

    }

    /**
     * Decision to switch pokemon
     * @param mNewBuddy pokemon to become new buddy pokemon
     * @param mCurrentBuddy current buddy pokemon
     * @param info data of the pokemon
     */
    public DecisionSwitch(PokemonProfile mNewBuddy, PokemonProfile mCurrentBuddy, PokemonInfo info) {
        this.mNewBuddy = mNewBuddy;
        this.mCurrentBuddy = mCurrentBuddy;
        this.mDisplayInfo = info;
    }

    /**
     * get pokemon information
     * @return pokemon to become new buddy pokemon
     */
    public PokemonProfile getProfile() {
        return mNewBuddy;
    }

    /**
     * set the profile for the new buddy
     * @param mProfile profile of the pokemon
     */
    public void setProfile(PokemonProfile mProfile) {
        this.mNewBuddy = mProfile;
    }

    /**
     * get the current buddy pokemon
     * @return current buddy pokemon
     */
    public PokemonProfile getCurrentBuddy() {
        return mCurrentBuddy;
    }

    /**
     * set the current buddy pokemon
     * @param mCurrentBuddy current buddy pokemon
     */
    public void setCurrentBuddy(PokemonProfile mCurrentBuddy) {
        this.mCurrentBuddy = mCurrentBuddy;
    }

    /**
     * check if there is error
     * @return boolean value to check error
     */
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

    /**
     * get the error message
     * @return error message
     */
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

    /**
     * execute the decision to switch
     * @param battle default filler
     */
    @Override
    public void execute(Battle battle){
        battle.setBuddy(mNewBuddy);
        if(battle.getEnemyDecision() instanceof DecisionAttack){
            ((DecisionAttack) battle.getEnemyDecision()).setDefender(mNewBuddy);
            
        }
    }

    /**
     * update the message to be displayed
     * @param battle default filler
     */
    @Override
    public void updateResults(Battle battle){
        battle.addMessage(new Message(Message.MESSAGE_SWITCH1 + mCurrentBuddy.getNickname() + "!"));
        battle.addMessage(new MessageUpdatePokemon(Message.MESSAGE_SWITCH2 + mNewBuddy.getNickname() + "!", mDisplayInfo, mNewBuddy));
    }

    /**
     * check if decision is empty
     * @return boolean value with a default value of false
     */
    @Override
    public boolean isEmpty(){
        return false;
    }

}
