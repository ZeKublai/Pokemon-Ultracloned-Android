package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class ItemHyperPotion extends ItemPotion {
    public ItemHyperPotion(){
        this.mHealFactor = 200;
        this.mName = "Hyper Potion";
        this.mImageIcon = R.drawable.bag_hyperpotion_icon;
        this.mImageBig = R.drawable.bag_hyperpotion;
        this.mImageSprite = R.drawable.bag_hyperpotion_icon;
    }

    public ItemHyperPotion(int quantity){
        this.mHealFactor = 200;
        this.mName = "Hyper Potion";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_hyperpotion_icon;
        this.mImageBig = R.drawable.bag_hyperpotion;
        this.mImageSprite = R.drawable.bag_hyperpotion_icon;
    }
    @Override
    public Item generateCopy(){
        return new ItemHyperPotion(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
