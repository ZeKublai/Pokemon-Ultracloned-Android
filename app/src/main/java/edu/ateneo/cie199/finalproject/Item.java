package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/13/2017.
 */

public abstract class Item {

    protected String mName = "";
    protected int mQuantity = 0;
    protected int mImageIcon = 0;
    protected int mImageBig = 0;
    protected int mImageSprite = 0;

    public int getImageSprite() {
        return mImageSprite;
    }
    public void setImageSprite(int mImageSprite) {
        this.mImageSprite = mImageSprite;
    }

    public int getImageIcon() {
        return mImageIcon;
    }
    public void setImageIcon(int mImageSide) {
        this.mImageIcon = mImageSide;
    }

    public int getImageBig() {
        return mImageBig;
    }
    public void setImageBig(int mImageFront) {
        this.mImageBig = mImageFront;
    }

    public String getName() {
        return mName;
    }
    public void setName(String mName) {
        this.mName = mName;
    }

    public int getQuantity() {
        return mQuantity;
    }
    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getButtonString(){
        return mName + " x" + mQuantity;
    }

    public abstract void useInBattle(PokemonProfile profile, PokemonInfo info, Battle battle);
    public abstract void useInManager(PokemonProfile profile, TextView txvMessage, ArrayList<Item> bag);
    public abstract PokemonProfile getUpdateTarget(Battle battle);
    public abstract PokemonProfile getExecuteTarget(Battle battle);
    public abstract PokemonInfo getTargetInfo(Battle battle);

    public void useItem(ArrayList<Item> bag){
        this.mQuantity = this.mQuantity - 1;
        if(this.mQuantity == 0){
            bag.remove(this);
        }
    }

    public abstract Item generateCopy();
}
