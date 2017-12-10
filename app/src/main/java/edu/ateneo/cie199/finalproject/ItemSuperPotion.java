package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Super Potion interact with the Pokémon.
 */

public class ItemSuperPotion extends ItemPotion {

    /**
     * Initialization of the Super Potion
     */
    public ItemSuperPotion() {
        this.mHealFactor = 50;
        this.mName = "Super Potion";
        this.mImageIcon = R.drawable.bag_superpotion_icon;
        this.mImageBig = R.drawable.bag_superpotion;
        this.mImageSprite = R.drawable.bag_superpotion_icon;
    }

    /**
     * Initialization given the total quantity of the Super Potion.
     * @param quantity  Integer value of the quantity.
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
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemSuperPotion(PokemonApp.getIntegerRNG(5) + 2);
    }
}
