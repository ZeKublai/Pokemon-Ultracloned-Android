package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 12/1/2017.
 * This subclass of the object Message is to temporary replace the image of the display info set to
 * the image of the current trainer opponent. It is usually generated during the start or at the
 * end of a trainer battle.
 */

public class MessageUpdateTrainer extends Message {
    private TrainerBattle mTrainerBattle;

    /**
     * This function generates the Message that will change the image into that of the trainer.
     * @param mContent  The String object content of the Message that will be displayed.
     * @param mBattle   The TrainerBattle object where the trainer data will be obtained.
     */
    public MessageUpdateTrainer(String mContent, TrainerBattle mBattle) {
        super(mContent, mBattle.getEnemyInfo());
        this.mTrainerBattle = mBattle;
    }

    /**
     * Sets the display image set of the Message to that of the main image of the Trainer.
     * @param battle    The battle where the Message object executes the update.
     */
    @Override
    public void executeUpdate(Battle battle){
        mDisplay.getImage().setBackgroundResource(mTrainerBattle.getTrainer().getImageMain());
    }
}
