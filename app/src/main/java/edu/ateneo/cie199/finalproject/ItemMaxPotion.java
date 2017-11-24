package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class ItemMaxPotion extends ItemPotion {
    public ItemMaxPotion() {
        this.mHealFactor = 9999;
        this.mName = "Max Potion";
        this.mImageIcon = R.drawable.bag_maxpotion_icon;
        this.mImageBig = R.drawable.bag_maxpotion;
        this.mImageSprite = R.drawable.bag_maxpotion_icon;
    }

    public ItemMaxPotion(int quantity) {
        this.mHealFactor = 9999;
        this.mName = "Max Potion";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_maxpotion_icon;
        this.mImageBig = R.drawable.bag_maxpotion;
        this.mImageSprite = R.drawable.bag_maxpotion_icon;
    }
    @Override
    public Item generateCopy(){
        return new ItemMaxPotion(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
