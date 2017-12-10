package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Great ball interact with the Pokémon.
 */

public class ItemUltraBall extends ItemPokeBall {

    /**
     * Initialization of the Ultra ball.
     */
    public ItemUltraBall() {
        this.mBallBonus = 2.0;
        this.mName = "Ultra Ball";
        this.mImageIcon = R.drawable.bag_ultraball_icon;
        this.mImageBig = R.drawable.bag_ultraball;
        this.mImageSprite = R.drawable.bag_ultraball_sprite;
    }

    /**
     * Initialization given the total quantity of the Ultra Ball.
     * @param quantity  Integer value of the quantity.
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
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemUltraBall(PokemonApp.getIntegerRNG(5) + 2);
    }
}
