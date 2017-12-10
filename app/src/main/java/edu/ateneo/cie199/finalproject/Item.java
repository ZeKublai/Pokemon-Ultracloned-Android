package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/13/2017.
 * This abstract class handles the functions and data members to be used for the class Item.
 */

public abstract class Item {

    protected String mName = "";
    protected int mQuantity = 0;
    protected int mImageIcon = 0;
    protected int mImageBig = 0;
    protected int mImageSprite = 0;

    /**
     * Gets the sprite image resource ID of the Item.
     * @return  The sprite image resource ID of the Item.
     */
    public int getImageSprite() {
        return mImageSprite;
    }

    /**
     * Gets the icon image resource ID  of the Item.
     * @return  The icon image resource ID  of the Item.
     */
    public int getImageIcon() {
        return mImageIcon;
    }

    /**
     * Gets the big image resource ID  of the Item.
     * @return  The big image resource ID  of the Item.
     */
    public int getImageBig() {
        return mImageBig;
    }

    /**
     * Gets the name of the Item.
     * @return  The name of the Item.
     */
    public String getName() {
        return mName;
    }

    /**
     * Gets the quantity of the Item.
     * @return  The quantity of the Item.
     */
    public int getQuantity() {
        return mQuantity;
    }

    /**
     * Sets the quantity of the Item.
     * @param mQuantity The quantity of the Item to be set.
     */
    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    /**
     * Get the String object for the ItemList object.
     * @return  The String object for the ItemList object.
     */
    public String getButtonString(){
        return mName + " x" + mQuantity;
    }

    /**
     * The function of the Item when used in battle.
     * @param profile   The Pokémon where the Item will be used.
     * @param info      The DisplayInfoSet of the Pokémon.
     * @param battle    The Battle where the item is being used.
     */
    public abstract void useInBattle(PokémonProfile profile, DisplayInfoSet info, Battle battle);

    /**
     * The function of the item when used in manager.
     * @param profile       The Pokémon where the Item will be used.
     * @param txvMessage    The TextView for outputting the message.
     * @param bag           Where the Item is stored.
     */
    public abstract void useInManager(PokémonProfile profile, TextView txvMessage, ArrayList<Item> bag);

    /**
     * Returns the Pokémon that will be updated upon execution of the Item.
     * @param battle    The Battle where the Item is being used.
     * @return          The intended Pokémon to be updated.
     */
    public abstract PokémonProfile getUpdateTarget(Battle battle);

    /**
     * Returns the Pokémon that where the Item will be used.
     * @param battle    The Battle where the Item is being used.
     * @return          The Pokémon that where the Item will be used.
     */
    public abstract PokémonProfile getExecuteTarget(Battle battle);

    /**
     * Returns the DisplayInfoSet that will be updated upon execution of the Item.
     * @param battle    The Battle where the Item is being used.
     * @return          The intended DisplayInfoSet to be updated.
     */
    public abstract DisplayInfoSet getTargetInfo(Battle battle);

    /**
     * Decrements the quantity of the Item by 1.
     * @param bag   Where the Item is stored.
     */
    public void useItem(ArrayList<Item> bag){
        this.mQuantity = this.mQuantity - 1;
        if(this.mQuantity == 0){
            bag.remove(this);
        }
    }

    /**
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    public abstract Item generateCopy();
}
