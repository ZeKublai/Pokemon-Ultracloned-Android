package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class ItemSuperPotion extends ItemPotion {
    public ItemSuperPotion() {
        this.mHealFactor = 50;
        this.mName = "Super Potion";
        this.mImageIcon = R.drawable.bag_superpotion_icon;
        this.mImageBig = R.drawable.bag_superpotion;
        this.mImageSprite = R.drawable.bag_superpotion_icon;
    }

    public ItemSuperPotion(int quantity) {
        this.mHealFactor = 50;
        this.mName = "Super Potion";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_superpotion_icon;
        this.mImageBig = R.drawable.bag_superpotion;
        this.mImageSprite = R.drawable.bag_superpotion_icon;
    }

    @Override
    public Item generateCopy(){
        return new ItemSuperPotion(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
