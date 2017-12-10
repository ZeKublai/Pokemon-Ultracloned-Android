package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/20/2017.
 * This class is a subclass of the Decision which handles the Decision for switching Pokémon.
 */

public class DecisionSwitch extends Decision {

    private PokémonProfile mNewBuddy;
    private PokémonProfile mCurrentBuddy;
    private DisplayInfoSet mDisplayInfo;

    /**
     * Creates the DecisionSwitch object.
     * @param mNewBuddy     The Pokémon to become new buddy Pokémon.
     * @param mCurrentBuddy The current buddy Pokémon.
     * @param info          The DisplayInfoSet to be updated.
     */
    public DecisionSwitch(PokémonProfile mNewBuddy,
                          PokémonProfile mCurrentBuddy,
                          DisplayInfoSet info) {
        this.mNewBuddy = mNewBuddy;
        this.mCurrentBuddy = mCurrentBuddy;
        this.mDisplayInfo = info;
    }

    /**
     * Checks if the Decision has an error.
     * @return  True if the Decision has an error else false.
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
     * Returns the error Message.
     * @return  The error Message.
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
     * Sets the Player's current Pokémon to the new Pokémon.
     * @param battle    The Battle object where the Decision is executed.
     */
    @Override
    public void execute(Battle battle){
        battle.setBuddy(mNewBuddy);
        if(battle.getEnemyDecision() instanceof DecisionAttack){
            ((DecisionAttack) battle.getEnemyDecision()).setDefender(mNewBuddy);
        }
    }

    /**
     * Updates the results of the Decision and adds Messages to the ArrayList in the Battle class.
     * @param battle    The Battle object where the Decision is executed.
     */
    @Override
    public void updateResults(Battle battle){
        battle.addMessage(new Message(Message.MESSAGE_SWITCH1 + mCurrentBuddy.getNickname() + "!"));
        battle.addMessage(new MessageUpdatePokemon(
                Message.MESSAGE_SWITCH2
                + mNewBuddy.getNickname()
                + "!",
                mDisplayInfo,
                mNewBuddy
        ));
    }
}
