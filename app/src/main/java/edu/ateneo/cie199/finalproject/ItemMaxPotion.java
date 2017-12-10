package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Max Potion interact with the Pokémon.
 */

public class ItemMaxPotion extends ItemPotion {

    /**
     * Initialization of the Max Potion.
     */
    public ItemMaxPotion() {
        this.mHealFactor = 9999;
        this.mName = "Max Potion";
        this.mImageIcon = R.drawable.bag_maxpotion_icon;
        this.mImageBig = R.drawable.bag_maxpotion;
        this.mImageSprite = R.drawable.bag_maxpotion_icon;
    }

    /**
     * Initialization given the total quantity of the Max Potion.
     * @param quantity  Integer value of the quantity.
     */
    public ItemMaxPotion(int quantity) {
        this.mHealFactor = 9999;
        this.mName = "Max Potion";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_maxpotion_icon;
        this.mImageBig = R.drawable.bag_maxpotion;
        this.mImageSprite = R.drawable.bag_maxpotion_icon;
    }

    /**
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemMaxPotion(PokemonApp.getIntegerRNG(5) + 2);
    }
}
