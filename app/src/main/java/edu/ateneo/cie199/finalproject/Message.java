package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/13/2017.
 */

public class Message {

    public static String MESSAGE_SWITCH1 = "Good job, ";
    public static String MESSAGE_SWITCH2 = "Go ";
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
    public static String MESSAGE_MANAGER_MAIN = "What would you like to do?";
    public static String MESSAGE_HP_RESTORED = "'s HP has been restored!";
    public static String MESSAGE_PP_RESTORED = "'s PP has been restored!";
    public static String MESSAGE_REVIVED = " has been revived!";
    public static String MESSAGE_SELECT_SWITCH = "Select other Pokemon to switch.";

    public static String ERROR_FAINTED = " has fainted and could not battle!";
    public static String ERROR_IN_BATTLE = " is already in battle!";
    public static String ERROR_NO_PP = "There's no PP left for this move!";
    public static String ERROR_NO_EFFECT = "It had no effect...";
    public static String ERROR_ECHO = "Jerome's words echoed... There's a time and place for everything, but not now.";

    protected String mMessage = "";
    protected PokemonInfo mDisplay;

    public Message(String mMessage, PokemonInfo mDisplay){
        this.mMessage = mMessage;
        this.mDisplay = mDisplay;
    }

    public Message(String mMessage){
        this.mMessage = mMessage;
    }
    public Message(){
        this.mMessage = "";
    }

    public String getMessage() {
        return mMessage;
    }
    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public void executeUpdate(Battle battle){
    }

    public boolean isEmpty(){
        if(this.mMessage.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

    public PokemonInfo getTarget(Battle battle){
        if(mDisplay instanceof PokemonInfoBuddy){
            return battle.getBuddyInfo();
        }
        else{
            return battle.getEnemyInfo();
        }
    }

    public void updateBattleInfo(Battle battle){
        if(mDisplay instanceof PokemonInfoBuddy){
            battle.setBuddyInfo(mDisplay);
        }
        else if(mDisplay instanceof PokemonInfo){
            battle.setEnemyInfo(mDisplay);
        }
    }
}
