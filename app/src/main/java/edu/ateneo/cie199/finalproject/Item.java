package edu.ateneo.cie199.finalproject;

import static java.lang.Math.floor;
import static java.lang.Math.pow;

/**
 * Created by John on 11/13/2017.
 */

public class Item {
    public static int NO_EFFECT = 0;
    public static int POTION_HEAL = 20;
    public static int SUPER_POTION_HEAL = 50;
    public static int HYPER_POTION_HEAL = 200;
    public static int MAX_POTION_HEAL = 9999;
    public static int REVIVE_DIVIDER = 2;
    public static int MAX_REVIVE_DIVIDER = 1;
    public static int ELIXIR_RESTORE = 10;
    public static int MAX_ELIXIR_RESTORE = 999;
    public static double POKE_BALL_BONUS = 1;
    public static double GREAT_BALL_BONUS = 1.5;
    public static double ULTRA_BALL_BONUS = 2;

    private String mName = "";
    private int mQuantity = 0;
    private int mImageIcon = 0;
    private int mImageBig = 0;
    private int mImageSprite = 0;
    private int mEffect = 0;

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
        this.mEffect = item.mEffect;
    }

    public Item(String mName, int mQuantity, int mImageIcon, int mImageBig, int mImageSprite, int mEffect) {
        this.mName = mName;
        this.mQuantity = mQuantity;
        this.mImageIcon = mImageIcon;
        this.mImageBig = mImageBig;
        this.mImageSprite = mImageSprite;
        this.mEffect = mEffect;
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

    public boolean healPokemon(PokemonProfile profile){
        if(profile.getCurrentHP() > 0){
            profile.setCurrentHP(profile.getCurrentHP() + mEffect);
            if(profile.getCurrentHP() > profile.getHP()){
                profile.setCurrentHP(profile.getHP());
            }
            return true;
        }
        else{
            return false;
        }
    }


    public boolean revivePokemon(PokemonProfile profile){
        if(profile.getCurrentHP() == 0){
            profile.setCurrentHP(profile.getHP()/mEffect);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean restorePP(PokemonProfile profile){
        for(int index = 0; index < PokemonProfile.MAX_POKEMON_MOVES; index++){
            Move move = profile.getMoves()[index];
            move.setCurrentPP(move.getCurrentPP() + mEffect);
            if(move.getCurrentPP() > move.getMaxPP()){
                move.setCurrentPP(move.getMaxPP());
            }
        }
        return true;
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
        return useBall(profile, POKE_BALL_BONUS);
    }
    public int useGreatBall(PokemonProfile profile){
        return useBall(profile, GREAT_BALL_BONUS);
    }
    public int useUltraBall(PokemonProfile profile){
        return useBall(profile, ULTRA_BALL_BONUS);
    }

    public String getButtonString(){
        return mName + " x" + mQuantity;
    }

}
