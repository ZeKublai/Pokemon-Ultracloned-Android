package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 12/1/2017.
 */

public class MessageUpdateTrainer extends Message {
    private TrainerBattle mTrainerBattle;
    public MessageUpdateTrainer(String mMessage, TrainerBattle mBattle) {
        super(mMessage, mBattle.getEnemyInfo());
        this.mTrainerBattle = mBattle;
    }

    @Override
    public void executeUpdate(Battle battle){
        mDisplay.getImage().setBackgroundResource(mTrainerBattle.getTrainer().getImageMain());
    }
}
