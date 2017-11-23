package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class ItemMaxRevive extends ItemRevive {
    public ItemMaxRevive(){
        this.mReviveFactor = 1;
        this.mName = "Max Revive";
        this.mImageIcon = R.drawable.bag_maxrevive_icon;
        this.mImageBig = R.drawable.bag_maxrevive;
        this.mImageSprite = R.drawable.bag_maxrevive_icon;
    }

    public ItemMaxRevive(int quantity){
        this.mReviveFactor = 1;
        this.mName = "Max Revive";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_maxrevive_icon;
        this.mImageBig = R.drawable.bag_maxrevive;
        this.mImageSprite = R.drawable.bag_maxrevive_icon;
    }
    @Override
    public Item generateCopy(){
        return new ItemMaxRevive(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
