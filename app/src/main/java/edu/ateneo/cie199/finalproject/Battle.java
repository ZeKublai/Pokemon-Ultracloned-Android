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

    public static Integer NO_UPDATE = 0;
    public static Integer UPDATE_ENEMY = 1;
    public static Integer UPDATE_ENEMY_HP = 2;
    public static Integer UPDATE_BUDDY = 3;
    public static Integer UPDATE_BUDDY_EXP = 4;
    public static Integer UPDATE_BUDDY_HP = 5;

    private Pokemon mBuddyPokemon;
    private Pokemon mEnemyPokemon;

    private PokemonProfile mBuddyProfile;
    private PokemonProfile mEnemyProfile;

    private ArrayList<String> mMessages = new ArrayList<>();
    private ArrayList<Integer> mUpdates = new ArrayList<>();
    private int mPhase = 0;

    private int mCurrentMessage = 0;

    public Battle(PokemonProfile mBuddyProfile, PokemonProfile mEnemyProfile) {
        this.mBuddyProfile = mBuddyProfile;
        this.mEnemyProfile = mEnemyProfile;
        this.mPhase = 0;
        this.mMessages = new ArrayList<>();
        this.mUpdates = new ArrayList<>();
    }

    public Battle(Pokemon mBuddyPokemon, Pokemon mEnemyPokemon, PokemonProfile mBuddyProfile,
                  PokemonProfile mEnemyProfile) {
        this.mBuddyPokemon = mBuddyPokemon;
        this.mEnemyPokemon = mEnemyPokemon;
        this.mBuddyProfile = mBuddyProfile;
        this.mEnemyProfile = mEnemyProfile;
        this.mPhase = 0;
        this.mMessages = new ArrayList<>();
        this.mUpdates = new ArrayList<>();
    }

    public Pokemon getBuddyPokemon() {
        return mBuddyPokemon;
    }

    public void setBuddyPokemon(Pokemon mBuddyPokemon) {
        this.mBuddyPokemon = mBuddyPokemon;
    }

    public Pokemon getEnemyPokemon() {
        return mEnemyPokemon;
    }

    public void setEnemyPokemon(Pokemon mEnemyPokemon) {
        this.mEnemyPokemon = mEnemyPokemon;
    }

    public PokemonProfile getBuddyProfile() {
        return mBuddyProfile;
    }

    public void setBuddyProfile(PokemonProfile mBuddy) {
        this.mBuddyProfile = mBuddy;
    }

    public PokemonProfile getEnemyProfile() {
        return mEnemyProfile;
    }

    public void setEnemyProfile(PokemonProfile mEnemy) {
        this.mEnemyProfile = mEnemy;
    }

    public ArrayList<String> getMessages() {
        return mMessages;
    }

    public ArrayList<Integer> getUpdates() {
        return mUpdates;
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

    public void addMessage(String message, Integer update){
        mMessages.add(message);
        mUpdates.add(update);
    }
}
