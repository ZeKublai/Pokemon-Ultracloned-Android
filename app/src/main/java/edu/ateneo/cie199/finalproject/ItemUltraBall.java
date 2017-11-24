package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class ItemUltraBall extends ItemPokeBall {
    public ItemUltraBall() {
        this.mBallBonus = 2.0;
        this.mName = "Ultra Ball";
        this.mImageIcon = R.drawable.bag_ultraball_icon;
        this.mImageBig = R.drawable.bag_ultraball;
        this.mImageSprite = R.drawable.bag_ultraball_sprite;
    }

    public ItemUltraBall(int quantity) {
        this.mBallBonus = 2.0;
        this.mName = "Ultra Ball";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_ultraball_icon;
        this.mImageBig = R.drawable.bag_ultraball;
        this.mImageSprite = R.drawable.bag_ultraball_sprite;
    }

    @Override
    public Item generateCopy(){
        return new ItemUltraBall(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
