package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/13/2017.
 */

public class Message {
    public static Integer NO_UPDATE = 0;
    public static Integer UPDATE_ENEMY = 1;
    public static Integer UPDATE_ENEMY_HP = 2;
    public static Integer UPDATE_BUDDY = 3;
    public static Integer UPDATE_BUDDY_EXP = 4;
    public static Integer UPDATE_BUDDY_HP = 5;
    public static Integer UPDATE_CATCH = 6;

    public static String MESSAGE_SWAP1 = "Good job, ";
    public static String MESSAGE_SWAP2 = "Go ";
    public static String MESSAGE_PLAYER_LOSS1 = " is out of Pokemon!";
    public static String MESSAGE_PLAYER_LOSS2 = " whited out!";
    public static String MESSAGE_FAINTED = " fainted";
    public static String MESSAGE_EXP_GAINED = " EXP points!";
    public static String MESSAGE_LEVEL_UP = " grew to LV. ";
    public static String MESSAGE_SUPER_EFFECTIVE = "It's super effective!";
    public static String MESSAGE_NOT_EFFECTIVE = "It's not very effective...";
    public static String MESSAGE_NO_EFFECT = "It doesn't affect foe ";
    public static String MESSAGE_CRITICAL = "A critical hit!";
    public static String MESSAGE_MISSED = "'s attack missed!";
    public static String MESSAGE_RUN_AWAY = "Got away safely!";
    public static String MESSAGE_ESCAPED =  " broke free!";
    public static String MESSAGE_CAUGHT = " was caught!";
    public static String MESSAGE_TO_PARTY = " has been added to the party!";
    public static String MESSAGE_TO_BOX = " has been sent to BOX 1!";

    public static String ERROR_FAINTED = " has fainted and could not battle!";
    public static String ERROR_IN_BATTLE = " is already in battle!";
    public static String ERROR_NO_PP = "There's no PP left for this move!";
    public static String ERROR_NO_EFFECT = "It had no effect...";

    public String mMessage = "";
    public int mUpdate = 0;

    public Message(String mMessage, int mUpdate) {
        this.mMessage = mMessage;
        this.mUpdate = mUpdate;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public int getUpdate() {
        return mUpdate;
    }

    public void setUpdate(int mUpdate) {
        this.mUpdate = mUpdate;
    }
}
