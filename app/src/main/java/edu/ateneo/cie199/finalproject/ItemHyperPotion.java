package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Hyper Potion interact with the PokéDexData.
 */

public class ItemHyperPotion extends ItemPotion {

    /**
     * Initializes of the Hyper Potion.
     */
    public ItemHyperPotion(){
        this.mHealFactor = 200;
        this.mName = "Hyper Potion";
        this.mImageIcon = R.drawable.bag_hyperpotion_icon;
        this.mImageBig = R.drawable.bag_hyperpotion;
        this.mImageSprite = R.drawable.bag_hyperpotion_icon;
    }

    /**
     * Initialization given the total quantity of the Hyper Potion.
     * @param quantity  Integer value of the quantity.
     */
    public ItemHyperPotion(int quantity){
        this.mHealFactor = 200;
        this.mName = "Hyper Potion";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_hyperpotion_icon;
        this.mImageBig = R.drawable.bag_hyperpotion;
        this.mImageSprite = R.drawable.bag_hyperpotion_icon;
    }

    /**
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemHyperPotion(PokemonApp.getIntegerRNG(5) + 2);
    }
}
