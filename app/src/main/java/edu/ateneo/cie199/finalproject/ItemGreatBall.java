package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Great Ball interact with the Pokémon.
 */

public class ItemGreatBall extends ItemPokeBall {

    /**
     * Initializes of the Great Ball.
     */
    public ItemGreatBall() {
        this.mBallBonus = 1.5;
        this.mName = "Great Ball";
        this.mImageIcon = R.drawable.bag_greatball_icon;
        this.mImageBig = R.drawable.bag_greatball;
        this.mImageSprite = R.drawable.bag_greatball_sprite;
    }

    /**
     * Initialization given the total quantity of the Great Ball.
     * @param quantity  Integer value of the quantity.
     */
    public ItemGreatBall(int quantity) {
        this.mBallBonus = 1.5;
        this.mName = "Great Ball";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_greatball_icon;
        this.mImageBig = R.drawable.bag_greatball;
        this.mImageSprite = R.drawable.bag_greatball_sprite;
    }

    /**
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemGreatBall(PokemonApp.getIntegerRNG(5) + 2);
    }
}
