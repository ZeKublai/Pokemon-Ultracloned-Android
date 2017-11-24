package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class ItemGreatBall extends ItemPokeBall {
    public ItemGreatBall() {
        this.mBallBonus = 1.5;
        this.mName = "Great Ball";
        this.mImageIcon = R.drawable.bag_greatball_icon;
        this.mImageBig = R.drawable.bag_greatball;
        this.mImageSprite = R.drawable.bag_greatball_sprite;
    }

    public ItemGreatBall(int quantity) {
        this.mBallBonus = 1.5;
        this.mName = "Great Ball";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_greatball_icon;
        this.mImageBig = R.drawable.bag_greatball;
        this.mImageSprite = R.drawable.bag_greatball_sprite;
    }

    @Override
    public Item generateCopy(){
        return new ItemGreatBall(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
