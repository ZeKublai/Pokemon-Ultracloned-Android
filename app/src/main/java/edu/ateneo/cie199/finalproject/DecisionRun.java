package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class is a subclass of the decision which handles the decision when running away
 */

public class DecisionRun extends Decision {
    private Battle mBattle;

    /**
     * decision to run
     * @param mBattle the state where the battle is
     */
    public DecisionRun(Battle mBattle) {
        this.mBattle = mBattle;
    }

    /**
     * get the error
     * @return error message
     */
    @Override
    public Message getErrorMessage(){
        return new Message("You can't run away from a trainer battle!");
    }

    /**
     * check if there is error
     * @return show error message
     */
    @Override
    public boolean isError(){
        return (mBattle instanceof TrainerBattle);
    }

    /**
     * execute the run away
     * @param battle default filler
     */
    @Override
    public void execute(Battle battle){
        battle.setRanAway(true);
    }

    /**
     * shows the message fo running away
     * overrides the stacked messages when running away
     * @param battle default filler
     */
    @Override
    public void updateResults(Battle battle){
        battle.addMessage(new Message(Message.MESSAGE_RUN_AWAY));
        battle.setBattleState(battle.getBattleState().standbyState());
    }

    /**
     * check if decision is empty
     * @return boolean value with default value at false
     */
    @Override
    public boolean isEmpty(){
        return false;
    }
}
