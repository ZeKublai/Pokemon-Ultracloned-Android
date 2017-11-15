package edu.ateneo.cie199.finalproject;

import static java.lang.Math.floor;
import static java.lang.Math.pow;

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

    public boolean usePotion(PokemonProfile profile){
        if(profile.getCurrentHP() > 0){
            profile.setCurrentHP(profile.getCurrentHP() + 20);
            if(profile.getCurrentHP() > profile.getHP()){
                profile.setCurrentHP(profile.getHP());
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean useSuperPotion(PokemonProfile profile){
        if(profile.getCurrentHP() > 0){
            profile.setCurrentHP(profile.getCurrentHP() + 50);
            if(profile.getCurrentHP() > profile.getHP()){
                profile.setCurrentHP(profile.getHP());
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean useMaxRevive(PokemonProfile profile){
        if(profile.getCurrentHP() == 0){
            profile.setCurrentHP(profile.getHP());
            return true;
        }
        else{
            return false;
        }
    }

    public int getFinalCatchRate(PokemonProfile profile, double ballBonus){
        double result = ((3.0*profile.getHP() - 2.0*profile.getCurrentHP())*ballBonus*
                profile.getDexData().getCatchRate())/(3.0*profile.getHP());
        return (int) result;
    }
    public int getSecondCatchRate(int finalCatchRate){
        double result = floor(65536.0/(pow(255.0/((double)finalCatchRate), 3.0/16.0)));
        return (int) result;
    }

    public int useBall(PokemonProfile profile, double ballBonus){
        int finalCatchRate = getFinalCatchRate(profile, ballBonus);
        int attempt1 = PokemonGoApp.getIntegerRNG(65535);
        int attempt2 = PokemonGoApp.getIntegerRNG(65535);
        int attempt3 = PokemonGoApp.getIntegerRNG(65535);
        if(attempt1 >= getSecondCatchRate(finalCatchRate)){
            return 1;
        }
        else{
            if(attempt2 >= getSecondCatchRate(finalCatchRate)){
                return 2;
            }
            else{
                if(attempt3 >= getSecondCatchRate(finalCatchRate)){
                    return 3;
                }
                else{
                    return 4;
                }
            }
        }
    }
    public int usePokeball(PokemonProfile profile){
        return useBall(profile, 1.0);
    }
    public int useGreatBall(PokemonProfile profile){
        return useBall(profile, 1.5);
    }
    public int useUltraBall(PokemonProfile profile){
        return useBall(profile, 2.0);
    }

}
