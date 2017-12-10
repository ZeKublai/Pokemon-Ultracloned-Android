package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/13/2017.
 * This abstract class handles the functions and data members to be used for the class Item
 */

//TODO: REFACTOR THE IMAGE NAMES FOR THE ITEM SPRITES

public abstract class Item {

    protected String mName = "";
    protected int mQuantity = 0;
    protected int mImageIcon = 0;
    protected int mImageBig = 0;
    protected int mImageSprite = 0;

    /**
     * get the sprite of the Item
     * @return sprite of the Item
     */
    public int getImageSprite() {
        return mImageSprite;
    }

    /**
     * set the sprite of the Item
     * @param mImageSprite sprite of the Item
     */
    public void setImageSprite(int mImageSprite) {
        this.mImageSprite = mImageSprite;
    }

    /**
     * get the map sprite of the Item
     * @return map sprite of the Item
     */
    public int getImageIcon() {
        return mImageIcon;
    }

    /**
     * set the map sprite of the Item
     * @param mImageSide map sprite of the Item
     */
    public void setImageIcon(int mImageSide) {
        this.mImageIcon = mImageSide;
    }

    /**
     * get the Main menu sprite of the Item
     * @return main menu sprite of the Item
     */
    public int getImageBig() {
        return mImageBig;
    }

    /**
     * get the Main menu sprite of the Item
     * @param mImageBig main menu dprite of the Item
     */
    public void setImageBig(int mImageBig) {
        this.mImageBig = mImageBig;
    }

    /**
     * get the name of the item
     * @return name of the item
     */
    public String getName() {
        return mName;
    }

    /**
     * set the name of the item
     * @param mName name of the item
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * get the quantity of the item
     * @return quantity of the item
     */
    public int getQuantity() {
        return mQuantity;
    }

    /**
     * set the quantity of the item
     * @param mQuantity quantity of the item
     */
    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    /**
     * get the button name to be set on the button.
     * @return string message
     */
    public String getButtonString(){
        return mName + " x" + mQuantity;
    }

    /**
     * The function of the item when used in battle
     * @param profile whoever is the targetting pokemon
     * @param info information of the pokemon
     * @param battle the battle state where the item is being used
     */
    public abstract void useInBattle(PokémonProfile profile, DisplayInfoSet info, Battle battle);

    /**
     * The function of the item when used in Manager
     * @param profile whoever is the targetting pokemon
     * @param txvMessage message outputted when item is used
     * @param bag where the item is stored
     */
    public abstract void useInManager(PokémonProfile profile, TextView txvMessage, ArrayList<Item> bag);

    /**
     * Use on auto-selected PokéDexData when Item is used. i.e. pokeball used on PokéDexData
     * @param battle the battle state where the item is being used
     * @return intended target PokéDexData
     */
    public abstract PokémonProfile getUpdateTarget(Battle battle);

    /**
     * on which pokemon the item is to be used
     * @param battle the battle state where the item is being used
     * @return execute the item effect on the PokéDexData
     */
    public abstract PokémonProfile getExecuteTarget(Battle battle);

    /**
     * get the information of the selected PokéDexData
     * @param battle the battle state where the item is being used
     * @return information on the PokéDexData
     */
    public abstract DisplayInfoSet getTargetInfo(Battle battle);

    /**
     * decrements the quantity of the item by 1
     * @param bag where the item is stored
     */
    public void useItem(ArrayList<Item> bag){
        this.mQuantity = this.mQuantity - 1;
        if(this.mQuantity == 0){
            bag.remove(this);
        }
    }

    /**
     * duplicates the item
     * @return exact copy of te item
     */
    public abstract Item generateCopy();
}
