package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/13/2017.
 */

public class Item {
    private String mName = "";
    private int mQuantity = 0;
    private int mImageSide = 0;
    private int mImageFront = 0;

    public Item(String mName, int mQuantity) {
        this.mName = mName;
        this.mQuantity = mQuantity;
    }

    public Item() {
    }

    public Item(String mName, int mQuantity, int mImageSide, int mImageFront) {
        this.mName = mName;
        this.mQuantity = mQuantity;
        this.mImageSide = mImageSide;
        this.mImageFront = mImageFront;
    }

    public int getImageSide() {
        return mImageSide;
    }

    public void setImageSide(int mImageSide) {
        this.mImageSide = mImageSide;
    }

    public int getImageFront() {
        return mImageFront;
    }

    public void setImageFront(int mImageFront) {
        this.mImageFront = mImageFront;
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
