package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Great ball interact with the Pokemon. It is a better Pokeball
 */

public class ItemGreatBall extends ItemPokeBall {

    /**
     * initialize the Great ball
     */
    public ItemGreatBall() {
        this.mBallBonus = 1.5;
        this.mName = "Great Ball";
        this.mImageIcon = R.drawable.bag_greatball_icon;
        this.mImageBig = R.drawable.bag_greatball;
        this.mImageSprite = R.drawable.bag_greatball_sprite;
    }

    /**
     * Total quantity of the Great ball
     * @param quantity integer value of the quantity
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
     * duplicate the great ball
     * @return duplicated copy
     */
    @Override
    public Item generateCopy(){
        return new ItemGreatBall(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
