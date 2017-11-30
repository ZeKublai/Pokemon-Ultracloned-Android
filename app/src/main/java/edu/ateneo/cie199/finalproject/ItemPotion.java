package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/21/2017.
 */

public class ItemPotion extends ItemTargetTeam {
    protected int mHealFactor = 20;

    public ItemPotion() {
        this.mHealFactor = 20;
        this.mName = "Potion";
        this.mImageIcon = R.drawable.bag_potion_icon;
        this.mImageBig = R.drawable.bag_potion;
        this.mImageSprite = R.drawable.bag_potion_icon;
    }

    public ItemPotion(int quantity) {
        this.mHealFactor = 20;
        this.mName = "Potion";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_potion_icon;
        this.mImageBig = R.drawable.bag_potion;
        this.mImageSprite = R.drawable.bag_potion_icon;
    }

    public int getHealing() {
        return mHealFactor;
    }
    public void setHealing(int mHealing) {
        this.mHealFactor = mHealing;
    }

    @Override
    public void useInBattle(PokemonProfile profile, PokemonInfo info, Battle battle){
        if(!heal(profile)){
            battle.addMessage(new Message(Message.ERROR_NO_EFFECT));
        }
    }

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

    @Override
    public Item generateCopy(){
        return new ItemPotion(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
