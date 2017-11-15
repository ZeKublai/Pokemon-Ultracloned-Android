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
