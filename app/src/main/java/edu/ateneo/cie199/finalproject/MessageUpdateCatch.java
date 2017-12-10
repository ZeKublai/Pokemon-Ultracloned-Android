package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/20/2017.
 * This subclass's primary function is serve as the Message that will be used then the player
 * decides to use a PokéBall. It is mainly used to change the image of the wild Pokémon target
 * into the of choice to emulate the catching part of using a PokéBall type item.
 */

public class MessageUpdateCatch extends Message {
    private Item mItem;

    /**
     * Creates a Message where for the function of the PokéBall type items.
     * @param mContent  The String object content of the Message that will be displayed.
     * @param mDisplay  The display info set that will be updated when the Pokéball item is used.
     * @param item      The PokéBall item that was used to update the display info set.
     */
    public MessageUpdateCatch(String mContent, DisplayInfoSet mDisplay, Item item) {
        super(mContent, mDisplay);
        this.mItem = item;
    }

    /**
     * This function gets the target from the given battle and changes the
     * image of the target into that of the item stored in the Message.
     * @param battle    The battle where the Message object executes the update.
     */
    @Override
    public void executeUpdate(Battle battle){
        getTarget(battle).updateCatch(mItem);
        updateBattleInfoSet(battle);
    }
}
