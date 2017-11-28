package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class DecisionRun extends Decision {
    public DecisionRun() {
    }

    @Override
    public void execute(Battle battle){
        battle.setRanAway(true);
    }

    @Override
    public void updateResults(Battle battle){
        battle.addMessage(new Message(Message.MESSAGE_RUN_AWAY));
        //battle.setState(PokemonGoApp.STATE_MESSAGE_LAST);
        battle.setBattleState(battle.getBattleState().secondMoveState());
    }

    @Override
    public boolean isEmpty(){
        return false;
    }
}
