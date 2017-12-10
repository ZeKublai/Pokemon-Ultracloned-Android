package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 * This subclass of the object Message is to update the only the HP bar of the
 * target Pokémon and it is usually generated when the Pokémon is damaged or healed.
 */

public class MessageUpdateHP extends Message {
    private PokémonProfile mProfile;

    /**
     * This function generates the the Message that will update the HP bar
     * of the selected display info set with the given PokémonProfile object.
     * @param mContent  The String object content of the Message that will be displayed.
     * @param mDisplay  The display info set that will be updated when the Message is shown.
     * @param profile   The PokémonProfile object that will be use to update the display info set.
     */
    public MessageUpdateHP(String mContent, DisplayInfoSet mDisplay, PokémonProfile profile) {
        super(mContent, mDisplay);
        this.mProfile = profile;
    }

    /**
     * This function gets the target from the given battle and updates the
     * HP bar of the target into that of the profile stored in the Message.
     * @param battle    The battle where the Message object executes the update.
     */
    @Override
    public void executeUpdate(Battle battle){
        getTarget(battle).updateHp(mProfile);
        updateBattleInfoSet(battle);
    }
}
