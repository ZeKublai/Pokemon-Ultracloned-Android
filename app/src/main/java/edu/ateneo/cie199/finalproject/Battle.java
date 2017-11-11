package edu.ateneo.cie199.finalproject;

import java.util.ArrayList;

/**
 * Created by John on 11/7/2017.
 */

public class Battle {
    //BUTTON STATES
    public static int MESSAGE_STATE = 0;
    public static int MAIN_STATE = 1;
    public static int FIGHT_STATE = 2;
    public static int POKEMON_STATE = 3;
    public static int BAG_STATE = 4;
    public static int SWAP_POKEMON_STATE = 5;

    private PokemonProfile mBuddy;
    private PokemonProfile mEnemy;

    private ArrayList<String> mMessages = new ArrayList<>();
    private int mPhase = 0;

    private int mCurrentMessage = 0;

    public Battle(PokemonProfile mBuddy, PokemonProfile mEnemy) {
        this.mBuddy = mBuddy;
        this.mEnemy = mEnemy;
        this.mPhase = 0;
        this.mMessages = new ArrayList<>();
    }

    public PokemonProfile getBuddy() {
        return mBuddy;
    }

    public void setBuddy(PokemonProfile mBuddy) {
        this.mBuddy = mBuddy;
    }

    public PokemonProfile getEnemy() {
        return mEnemy;
    }

    public void setEnemy(PokemonProfile mEnemy) {
        this.mEnemy = mEnemy;
    }

    public ArrayList<String> getMessages() {
        return mMessages;
    }

    public void setMessages(ArrayList<String> mMessages) {
        this.mMessages = mMessages;
    }

    public int getPhase() {
        return mPhase;
    }

    public void setPhase(int mPhase) {
        this.mPhase = mPhase;
    }

    public int getCurrentMessage() {
        return mCurrentMessage;
    }

    public void setCurrentMessage(int mCurrentMessage) {
        this.mCurrentMessage = mCurrentMessage;
    }
}
