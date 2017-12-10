package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Max Revive interact with the PokéDexData. It is a better Revive.
 */

public class ItemMaxRevive extends ItemRevive {

    /**
     * initialize the Max Revive
     */
    public ItemMaxRevive(){
        this.mReviveFactor = 1;
        this.mName = "Max Revive";
        this.mImageIcon = R.drawable.bag_maxrevive_icon;
        this.mImageBig = R.drawable.bag_maxrevive;
        this.mImageSprite = R.drawable.bag_maxrevive_icon;
    }

    /**
     * Total quantity of the Max Revive
     * @param quantity integer value of the quantity
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
     * duplicate the max revive
     * @return duplicated copy
     */
    @Override
    public Item generateCopy(){
        return new ItemMaxRevive(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
