package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Item Revive interact with the Pokémon.
 */

public class ItemRevive extends ItemTargetTeam {

    protected int mReviveFactor = 2;

    /**
     * Initialization of the Revive.
     */
    public ItemRevive(){
        this.mReviveFactor = 2;
        this.mName = "Revive";
        this.mImageIcon = R.drawable.bag_revive_icon;
        this.mImageBig = R.drawable.bag_revive;
        this.mImageSprite = R.drawable.bag_revive_icon;
    }

    /**
     * Initialization given the total quantity of the Revive.
     * @param quantity  Integer value of the quantity.
     */
    public ItemRevive(int quantity){
        this.mReviveFactor = 2;
        this.mName = "Revive";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_revive_icon;
        this.mImageBig = R.drawable.bag_revive;
        this.mImageSprite = R.drawable.bag_revive_icon;
    }

    /**
     * The function of the Revive when used in battle.
     * @param profile   Whoever is the targeted Pokémon.
     * @param info      The DisplayInfoSet to be updated.
     * @param battle    The Battle object where the Item is being used.
     */
    @Override
    public void useInBattle(PokémonProfile profile, DisplayInfoSet info, Battle battle){
        if(!revive(profile)) {
            battle.addMessage(new Message(Message.ERROR_NO_EFFECT));
        }
    }

    /**
     * Revives a fainted Pokémon.
     * @param profile   The Pokémon where the Item will be used.
     * @return boolean  True if revive was successful.
     */
    public boolean revive(PokémonProfile profile){
        if(profile.getCurrentHP() == 0){
            profile.setCurrentHP(profile.getHP()/mReviveFactor);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * The function of the Revive when used in the Manager activity.
     * @param profile       The Pokémon where the Item will be used.
     * @param txvMessage    The TextView for outputting the message.
     * @param bag           Where the Item is stored.
     */
    @Override
    public void useInManager(PokémonProfile profile, TextView txvMessage, ArrayList<Item> bag){
        useItem(bag);
        if(!revive(profile)){
            txvMessage.setText(Message.ERROR_NO_EFFECT);
        }
        else{
            txvMessage.setText(profile.getNickname() + Message.MESSAGE_REVIVED);
        }
    }

    /**
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemRevive(PokemonApp.getIntegerRNG(5) + 2);
    }

}
