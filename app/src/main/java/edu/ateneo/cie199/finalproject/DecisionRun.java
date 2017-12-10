package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class is a subclass of the Decision class which handles the Decision when running away.
 */

public class DecisionRun extends Decision {
    private Battle mBattle;

    /**
     * Creates the DecisionRun object.
     * @param mBattle   The Battle object where the Decision is executed.
     */
    public DecisionRun(Battle mBattle) {
        this.mBattle = mBattle;
    }

    /**
     * Returns the error Message.
     * @return  The error Message.
     */
    @Override
    public Message getErrorMessage(){
        return new Message("You can't run away from a trainer battle!");
    }

    /**
     * Checks if the Decision has an error.
     * @return  True if the Decision has an error else false.
     */
    @Override
    public boolean isError(){
        return (mBattle instanceof TrainerBattle);
    }

    /**
     * Sets the boolean for running away to true.
     * @param battle    The Battle object where the Decision is executed.
     */
    @Override
    public void execute(Battle battle){
        battle.setRanAway(true);
    }

    /**
     * Updates the results of the Decision and adds Messages to the ArrayList in the Battle class.
     * @param battle    The Battle object where the Decision is executed.
     */
    @Override
    public void updateResults(Battle battle){
        battle.addMessage(new Message(Message.MESSAGE_RUN_AWAY));
        battle.setBattleState(battle.getBattleState().standbyState());
    }
}
