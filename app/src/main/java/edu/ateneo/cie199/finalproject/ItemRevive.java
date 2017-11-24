package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/21/2017.
 */

public class ItemRevive extends ItemTargetTeam {

    protected int mReviveFactor = 2;

    public int getReviveFactor() {
        return mReviveFactor;
    }

    public void setReviveFactor(int mReviveFactor) {
        this.mReviveFactor = mReviveFactor;
    }

    public ItemRevive(){
        this.mReviveFactor = 2;
        this.mName = "Revive";
        this.mImageIcon = R.drawable.bag_revive_icon;
        this.mImageBig = R.drawable.bag_revive;
        this.mImageSprite = R.drawable.bag_revive_icon;
    }

    public ItemRevive(int quantity){
        this.mReviveFactor = 2;
        this.mName = "Revive";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_revive_icon;
        this.mImageBig = R.drawable.bag_revive;
        this.mImageSprite = R.drawable.bag_revive_icon;
    }

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

    @Override
    public Item generateCopy(){
        return new ItemRevive(PokemonGoApp.getIntegerRNG(5) + 2);
    }

}
