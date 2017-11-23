package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/21/2017.
 */

public class ItemElixir extends ItemTargetTeam {

    protected int mPPRestoreFactor = 10;

    public ItemElixir() {
        this.mPPRestoreFactor = 10;
        this.mName = "Elixir";
        this.mImageIcon = R.drawable.bag_elixir_icon;
        this.mImageBig = R.drawable.bag_elixir;
        this.mImageSprite = R.drawable.bag_elixir_icon;
    }

    public ItemElixir(int quantity) {
        this.mPPRestoreFactor = 10;
        this.mName = "Elixir";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_elixir_icon;
        this.mImageBig = R.drawable.bag_elixir;
        this.mImageSprite = R.drawable.bag_elixir_icon;
    }

    public int getRestoreFactor() {
        return mPPRestoreFactor;
    }
    public void setRestoreFactor(int mRestoreFactor) {
        this.mPPRestoreFactor = mRestoreFactor;
    }

    @Override
    public void useInBattle(PokemonProfile profile, PokemonInfo info, Battle battle){
        if(!restorePP(profile)){
            battle.addMessage(new Message(Message.ERROR_NO_EFFECT));
        }
    }

    public boolean restorePP(PokemonProfile profile){
        if(profile.allMovesPPisFull()){
            return false;
        }
        for(int index = 0; index < PokemonProfile.MAX_POKEMON_MOVES; index++){
            Move move = profile.getMoves().get(index);
            move.setCurrentPP(move.getCurrentPP() + mPPRestoreFactor);
            if(move.getCurrentPP() > move.getMaxPP()){
                move.setCurrentPP(move.getMaxPP());
            }
        }
        return true;
    }

    @Override
    public void useInManager(PokemonProfile profile, TextView txvMessage, ArrayList<Item> bag){
        useItem(bag);
        if(!restorePP(profile)){
            txvMessage.setText(Message.ERROR_NO_EFFECT);
        }
        else{
            txvMessage.setText(profile.getNickname() + Message.MESSAGE_PP_RESTORED);
        }
    }

    @Override
    public Item generateCopy(){
        return new ItemElixir(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
