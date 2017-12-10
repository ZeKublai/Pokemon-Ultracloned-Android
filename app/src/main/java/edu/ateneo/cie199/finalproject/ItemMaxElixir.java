package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Max Elixir interact with the PokéDexData. It is a better Elixir.
 */

public class ItemMaxElixir extends ItemElixir {

    protected int mPPRestoreFactor = 999;

    /**
     * initialize the Max Elixir
     */
    public ItemMaxElixir() {
        this.mPPRestoreFactor = 999;
        this.mName = "Max Elixir";
        this.mImageIcon = R.drawable.bag_maxelixir_icon;
        this.mImageBig = R.drawable.bag_maxelixir;
        this.mImageSprite = R.drawable.bag_maxelixir_icon;
    }

    /**
     * Total quantity of the Max Elixir
     * @param quantity integer value of the quantity
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
     * duplicate the max elixir
     * @return duplicated copy
     */
    @Override
    public Item generateCopy(){
        return new ItemMaxElixir(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
