package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Potion interact with the Pokemon.
 */

public class ItemPotion extends ItemTargetTeam {
    protected int mHealFactor = 20;

    /**
     * initialize the potion
     */
    public ItemPotion() {
        this.mHealFactor = 20;
        this.mName = "Potion";
        this.mImageIcon = R.drawable.bag_potion_icon;
        this.mImageBig = R.drawable.bag_potion;
        this.mImageSprite = R.drawable.bag_potion_icon;
    }

    /**
     * Total quantity of the Potion
     * @param quantity integer value of the quantity
     */
    public ItemPotion(int quantity) {
        this.mHealFactor = 20;
        this.mName = "Potion";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_potion_icon;
        this.mImageBig = R.drawable.bag_potion;
        this.mImageSprite = R.drawable.bag_potion_icon;
    }

    /**
     * Get the value of the heal
     * @return value of the heal
     */
    public int getHealing() {
        return mHealFactor;
    }

    /**
     * Set the value of the heal
     * @param mHealing value of the heal
     */
    public void setHealing(int mHealing) {
        this.mHealFactor = mHealing;
    }

    /**
     * The function of the Potion when used in battle
     * @param profile whoever is the targetting pokemon
     * @param info information of the pokemon
     * @param battle the battle state where the item is being used
     */
    @Override
    public void useInBattle(PokemonProfile profile, PokemonInfo info, Battle battle){
        if(!heal(profile)){
            battle.addMessage(new Message(Message.ERROR_NO_EFFECT));
        }
    }

    /**
     * Executes the heal done on the Pokemon
     * @param profile whoever is the targeted pokemon
     * @return boolean value to check if the healing was successful
     */
    public boolean heal(PokemonProfile profile){
        if(profile.getCurrentHP() == profile.getHP()){
            return false;
        }
        if(profile.getCurrentHP() > 0){
            profile.setCurrentHP(profile.getCurrentHP() + mHealFactor);
            if(profile.getCurrentHP() > profile.getHP()){
                profile.setCurrentHP(profile.getHP());
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * The function of the potion when used in manager
     * @param profile whoever is the targeted pokemon
     * @param txvMessage message outputted when item is used
     * @param bag where the item is stored
     */
    @Override
    public void useInManager(PokemonProfile profile, TextView txvMessage, ArrayList<Item> bag){
        useItem(bag);
        if(!heal(profile)){
            txvMessage.setText(Message.ERROR_NO_EFFECT);
        }
        else{
            txvMessage.setText(profile.getNickname() + Message.MESSAGE_HP_RESTORED);
        }
    }

    /**
     * duplicate the potion
     * @return duplicated copy
     */
    @Override
    public Item generateCopy(){
        return new ItemPotion(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
