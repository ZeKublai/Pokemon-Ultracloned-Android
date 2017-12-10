package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/20/2017.
 * This subclass of the object Message is to update the entire Pokémon which includes the image
 * displayed, HP bar and the EXP BAR. It is usually generated when a Pokémon is switched out from
 * battle or when a Pokémon has fainted.
 */

public class MessageUpdatePokemon extends Message {
    private PokémonProfile mProfile;

    /**
     * This function generates the the Message that will update the
     * entire display info set with the given PokémonProfile object.
     * @param mContent  The String object content of the Message that will be displayed.
     * @param mDisplay  The display info set that will be updated when the PokémonProfile changes.
     * @param profile   The PokémonProfile object that will be use to update the display info set.
     */
    public MessageUpdatePokemon(String mContent, DisplayInfoSet mDisplay, PokémonProfile profile) {
        super(mContent, mDisplay);
        this.mProfile = profile;
    }

    /**
     * This function gets the target from the given battle and updates the
     * Pokémon display target into that of the profile stored in the Message.
     * @param battle    The battle where the Message object executes the update.
     */
    @Override
    public void executeUpdate(Battle battle){
        getTarget(battle).updatePokemon(mProfile);
        updateBattleInfoSet(battle);
    }
}
