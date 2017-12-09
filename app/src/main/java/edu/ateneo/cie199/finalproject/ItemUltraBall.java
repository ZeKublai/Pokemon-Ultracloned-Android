package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Great ball interact with the Pokemon. It is a better Great Ball
 */

public class ItemUltraBall extends ItemPokeBall {

    /**
     * initialize the Ultra ball
     */
    public ItemUltraBall() {
        this.mBallBonus = 2.0;
        this.mName = "Ultra Ball";
        this.mImageIcon = R.drawable.bag_ultraball_icon;
        this.mImageBig = R.drawable.bag_ultraball;
        this.mImageSprite = R.drawable.bag_ultraball_sprite;
    }

    /**
     * Total quantity of the Ultra ball
     * @param quantity integer value of the quantity
     */
    public ItemUltraBall(int quantity) {
        this.mBallBonus = 2.0;
        this.mName = "Ultra Ball";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_ultraball_icon;
        this.mImageBig = R.drawable.bag_ultraball;
        this.mImageSprite = R.drawable.bag_ultraball_sprite;
    }

    /**
     * duplicate the great ball
     * @return duplicated copy
     */
    @Override
    public Item generateCopy(){
        return new ItemUltraBall(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
