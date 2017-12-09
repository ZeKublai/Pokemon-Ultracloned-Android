package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Revive interact with the Pokemon.
 */

public class ItemRevive extends ItemTargetTeam {

    protected int mReviveFactor = 2;

    /**
     * initialize the Revive
     */
    public ItemRevive(){
        this.mReviveFactor = 2;
        this.mName = "Revive";
        this.mImageIcon = R.drawable.bag_revive_icon;
        this.mImageBig = R.drawable.bag_revive;
        this.mImageSprite = R.drawable.bag_revive_icon;
    }

    /**
     * Total quantity of the Revive
     * @param quantity integer value of the quantity
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
     * The function of the Revive when used in battle
     * @param profile whoever is the targeted pokemon
     * @param info information of the pokemon
     * @param battle the battle state where the item is being used
     */
    @Override
    public void useInBattle(PokemonProfile profile, PokemonInfo info, Battle battle){
        if(!revive(profile)) {
            battle.addMessage(new Message(Message.ERROR_NO_EFFECT));
        }
    }

    public boolean revive(PokemonProfile profile){
        if(profile.getCurrentHP() == 0){
            profile.setCurrentHP(profile.getHP()/mReviveFactor);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * The function of the Revive when used in the manager activity
     * @param profile whoever is the targeted pokemon
     * @param txvMessage message outputted when item is used
     * @param bag where the item is stored
     */
    @Override
    public void useInManager(PokemonProfile profile, TextView txvMessage, ArrayList<Item> bag){
        useItem(bag);
        if(!revive(profile)){
            txvMessage.setText(Message.ERROR_NO_EFFECT);
        }
        else{
            txvMessage.setText(profile.getNickname() + Message.MESSAGE_REVIVED);
        }
    }

    /**
     * duplicate the Revive
     * @return duplicated copy
     */
    @Override
    public Item generateCopy(){
        return new ItemRevive(PokemonGoApp.getIntegerRNG(5) + 2);
    }

}
