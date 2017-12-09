package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Hyper Potion interact with the Pokemon. It is a better Super Potion.
 */

public class ItemHyperPotion extends ItemPotion {

    /**
     * initialize the Hyper Potion
     */
    public ItemHyperPotion(){
        this.mHealFactor = 200;
        this.mName = "Hyper Potion";
        this.mImageIcon = R.drawable.bag_hyperpotion_icon;
        this.mImageBig = R.drawable.bag_hyperpotion;
        this.mImageSprite = R.drawable.bag_hyperpotion_icon;
    }
    /**
     * Total quantity of the Hyper Potion
     * @param quantity integer value of the quantity
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
     * duplicate the hyper potion
     * @return duplicated copy
     */
    @Override
    public Item generateCopy(){
        return new ItemHyperPotion(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
