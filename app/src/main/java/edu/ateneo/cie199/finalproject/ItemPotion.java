package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Potion interact with the Pokémon.
 */

public class ItemPotion extends ItemTargetTeam {
    protected int mHealFactor = 20;

    /**
     * Initialization of the Potion.
     */
    public ItemPotion() {
        this.mHealFactor = 20;
        this.mName = "Potion";
        this.mImageIcon = R.drawable.bag_potion_icon;
        this.mImageBig = R.drawable.bag_potion;
        this.mImageSprite = R.drawable.bag_potion_icon;
    }

    /**
     * Initialization given the total quantity of the Potion.
     * @param quantity  Integer value of the quantity.
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
     * The function of the Potion when used in battle.
     * @param profile   Whoever is the targeted Pokémon.
     * @param info      The DisplayInfoSet to be updated.
     * @param battle    The Battle object where the Item is being used.
     */
    @Override
    public void useInBattle(PokémonProfile profile, DisplayInfoSet info, Battle battle){
        if(!heal(profile)){
            battle.addMessage(new Message(Message.ERROR_NO_EFFECT));
        }
    }

    /**
     * Executes the heal done on the Pokémon.
     * @param profile   The Pokémon where the Item will be used.
     * @return          True if healing was successful.
     */
    public boolean heal(PokémonProfile profile){
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
     * The function of the Potion when used in the Manager.
     * @param profile       The Pokémon where the Item will be used.
     * @param txvMessage    The TextView for outputting the message.
     * @param bag           Where the Item is stored.
     */
    @Override
    public void useInManager(PokémonProfile profile, TextView txvMessage, ArrayList<Item> bag){
        useItem(bag);
        if(!heal(profile)){
            txvMessage.setText(Message.ERROR_NO_EFFECT);
        }
        else{
            txvMessage.setText(profile.getNickname() + Message.MESSAGE_HP_RESTORED);
        }
    }

    /**
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemPotion(PokemonApp.getIntegerRNG(5) + 2);
    }
}
