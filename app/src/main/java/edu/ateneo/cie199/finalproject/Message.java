package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/13/2017.
 * This class handles functions and data members needed to display battle messages
 * and control the flow of data being shown on the battle screen. This parent class
 * is the most basic of it kind where its only job is to show its content on screen.
 */

public class Message {

    public static String MESSAGE_SWITCH1 = "Good job, ";
    public static String MESSAGE_SWITCH2 = "Go ";
    public static String MESSAGE_PLAYER_LOSS1 = " is out of PokéDexData!";
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
    public static String MESSAGE_SELECT_SWITCH = "Select other PokéDexData to switch.";

    public static String ERROR_FAINTED = " has fainted and could not battle!";
    public static String ERROR_IN_BATTLE = " is already in battle!";
    public static String ERROR_NO_PP = "There's no PP left for this move!";
    public static String ERROR_NO_EFFECT = "It had no effect...";
    public static String ERROR_ECHO = "Jerome's words echoed... "
            + "There's a time and place for everything, but not now.";

    protected String mContent = "";
    protected DisplayInfoSet mDisplay;

    /**
     * Creates an instance of a Message object where its display will be updated upon execution.
     * @param mContent  The String object that contains the content of the message.
     * @param mDisplay  The display info set that will be used to update the battle scene.
     */
    public Message(String mContent, DisplayInfoSet mDisplay){
        this.mContent = mContent;
        this.mDisplay = mDisplay;
    }

    /**
     * Creates an instance of a Message object where its only purpose
     * is to be shown in battle without updating the battle scene.
     * @param mContent  The String object that contains the message.
     */
    public Message(String mContent){
        this.mContent = mContent;
    }

    /**
     * Creates a Message object with empty content.
     */
    public Message(){
        this.mContent = "";
    }

    /**
     * Returns the String object content of the Message object.
     * @return  The String object content of the Message object to be returned
     */
    public String getContent() {
        return mContent;
    }

    /**
     * Sets the String content of the Message object class given a String object.
     * @param mContent  The String object that will be set.
     */
    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    /**
     * This function is called when the Message object is shown on screen.
     * @param battle    The battle where the Message object execute the update.
     */
    public void executeUpdate(Battle battle){}

    /**
     * Returns if the content of the Message object is empty.
     * @return  True if the content of the Message object is empty
     */
    public boolean isEmpty(){
        return (this.mContent.equals(""));
    }

    /**
     * Returns the target of the update depending on the type of display that was given.
     * @param battle    The battle where the target will be obtained.
     * @return  The display info set where the Message object will update.
     */
    public DisplayInfoSet getTarget(Battle battle){
        if(mDisplay instanceof DisplayInfoSetBuddy){
            return battle.getBuddyInfo();
        }
        else{
            return battle.getEnemyInfo();
        }
    }

    /**
     * Updates the battle scene with the display info set of the Message given a battle object.
     * @param battle    The battle where the display info set that will be updated is obtained.
     */
    public void updateBattleInfoSet(Battle battle){
        if(mDisplay instanceof DisplayInfoSetBuddy){
            battle.setBuddyInfo(mDisplay);
        }
        else if(mDisplay instanceof DisplayInfoSet){
            battle.setEnemyInfo(mDisplay);
        }
    }
}
