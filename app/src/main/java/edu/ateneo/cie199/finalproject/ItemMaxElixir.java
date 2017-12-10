package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Max Elixir interact with the Pokémon.
 */

public class ItemMaxElixir extends ItemElixir {

    protected int mPPRestoreFactor = 999;

    /**
     * Initialization of the Max Elixir.
     */
    public ItemMaxElixir() {
        this.mPPRestoreFactor = 999;
        this.mName = "Max Elixir";
        this.mImageIcon = R.drawable.bag_maxelixir_icon;
        this.mImageBig = R.drawable.bag_maxelixir;
        this.mImageSprite = R.drawable.bag_maxelixir_icon;
    }

    /**
     * Initialization given the total quantity of the Max Elixir.
     * @param quantity  Integer value of the quantity.
     */
    public ItemMaxElixir(int quantity) {
        this.mPPRestoreFactor = 999;
        this.mName = "Max Elixir";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_maxelixir_icon;
        this.mImageBig = R.drawable.bag_maxelixir;
        this.mImageSprite = R.drawable.bag_maxelixir_icon;
    }

    /**
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemMaxElixir(PokemonApp.getIntegerRNG(5) + 2);
    }
}
