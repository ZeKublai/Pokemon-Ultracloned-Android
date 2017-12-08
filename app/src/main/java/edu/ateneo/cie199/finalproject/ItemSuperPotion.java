package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Super Potion interact with the Pokemon. It is a better Potion
 */

public class ItemSuperPotion extends ItemPotion {

    /**
     * initialize the Super Potion
     */
    public ItemSuperPotion() {
        this.mHealFactor = 50;
        this.mName = "Super Potion";
        this.mImageIcon = R.drawable.bag_superpotion_icon;
        this.mImageBig = R.drawable.bag_superpotion;
        this.mImageSprite = R.drawable.bag_superpotion_icon;
    }

    /**
     * Total quantity of the Super Potion
     * @param quantity integer value of the quantity
     */
    public ItemSuperPotion(int quantity) {
        this.mHealFactor = 50;
        this.mName = "Super Potion";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_superpotion_icon;
        this.mImageBig = R.drawable.bag_superpotion;
        this.mImageSprite = R.drawable.bag_superpotion_icon;
    }

    /**
     * duplicate the Super Potion
     * @return duplicated copy
     */
    @Override
    public Item generateCopy(){
        return new ItemSuperPotion(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
