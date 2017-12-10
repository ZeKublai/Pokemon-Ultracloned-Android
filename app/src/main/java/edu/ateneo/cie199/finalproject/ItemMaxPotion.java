package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Max Potion interact with the PokéDexData. It is a better Hyper Potion.
 */

public class ItemMaxPotion extends ItemPotion {

    /**
     * initialize the Max Potion
     */
    public ItemMaxPotion() {
        this.mHealFactor = 9999;
        this.mName = "Max Potion";
        this.mImageIcon = R.drawable.bag_maxpotion_icon;
        this.mImageBig = R.drawable.bag_maxpotion;
        this.mImageSprite = R.drawable.bag_maxpotion_icon;
    }

    /**
     * Total quantity of the Max Potion
     * @param quantity integer value of the quantity
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
     * duplicate the max potion
     * @return duplicated copy
     */
    @Override
    public Item generateCopy(){
        return new ItemMaxPotion(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
