package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/13/2017.
 */

public class Item {
    private String mName = "";
    private int mQuantity = 0;
    private int mImageIcon = 0;
    private int mImageBig = 0;

    private int mImageSprite = 0;

    public Item(String mName, int mQuantity) {
        this.mName = mName;
        this.mQuantity = mQuantity;
    }

    public Item() {
    }

    public Item(Item item){
        this.mName = item.mName;
        this.mQuantity = item.mQuantity;
        this.mImageIcon = item.mImageIcon;
        this.mImageBig = item.mImageBig;
        this.mImageSprite = item.mImageSprite;
    }

    public Item(String mName, int mQuantity, int mImageIcon, int mImageBig, int mImageSprite) {
        this.mName = mName;
        this.mQuantity = mQuantity;
        this.mImageIcon = mImageIcon;
        this.mImageBig = mImageBig;
        this.mImageSprite = mImageSprite;
    }

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
}
