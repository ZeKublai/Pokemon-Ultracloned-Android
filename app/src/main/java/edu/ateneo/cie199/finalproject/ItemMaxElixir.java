package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class ItemMaxElixir extends ItemElixir {

    protected int mPPRestoreFactor = 999;

    public ItemMaxElixir() {
        this.mPPRestoreFactor = 999;
        this.mName = "Max Elixir";
        this.mImageIcon = R.drawable.bag_maxelixir_icon;
        this.mImageBig = R.drawable.bag_maxelixir;
        this.mImageSprite = R.drawable.bag_maxelixir_icon;
    }

    public ItemMaxElixir(int quantity) {
        this.mPPRestoreFactor = 999;
        this.mName = "Max Elixir";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_maxelixir_icon;
        this.mImageBig = R.drawable.bag_maxelixir;
        this.mImageSprite = R.drawable.bag_maxelixir_icon;
    }
    @Override
    public Item generateCopy(){
        return new ItemMaxElixir(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
