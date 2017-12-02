package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class DecisionRun extends Decision {
    private Battle mBattle;
    public DecisionRun(Battle mBattle) {
        this.mBattle = mBattle;
    }
    @Override
    public Message getErrorMessage(){
        return new Message("You can't run away from a trainer battle!");
    }

    @Override
    public boolean isError(){
        return (mBattle instanceof TrainerBattle);
    }

    @Override
    public void execute(Battle battle){
        battle.setRanAway(true);
    }

    @Override
    public void updateResults(Battle battle){
        battle.addMessage(new Message(Message.MESSAGE_RUN_AWAY));
        battle.setBattleState(battle.getBattleState().standbyState());
    }

    @Override
    public boolean isEmpty(){
        return false;
    }
}
