package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Max Revive interact with the Pokémon.
 */

public class ItemMaxRevive extends ItemRevive {

    /**
     * Initialization of the Max Revive.
     */
    public ItemMaxRevive(){
        this.mReviveFactor = 1;
        this.mName = "Max Revive";
        this.mImageIcon = R.drawable.bag_maxrevive_icon;
        this.mImageBig = R.drawable.bag_maxrevive;
        this.mImageSprite = R.drawable.bag_maxrevive_icon;
    }

    /**
     * Initialization given the total quantity of the Max Revive.
     * @param quantity  Integer value of the quantity.
     */
    public ItemMaxRevive(int quantity){
        this.mReviveFactor = 1;
        this.mName = "Max Revive";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_maxrevive_icon;
        this.mImageBig = R.drawable.bag_maxrevive;
        this.mImageSprite = R.drawable.bag_maxrevive_icon;
    }

    /**
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemMaxRevive(PokemonApp.getIntegerRNG(5) + 2);
    }
}
