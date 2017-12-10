package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Item Elixir interact with the Pokémon.
 */

public class ItemElixir extends ItemTargetTeam {
    protected int mPPRestoreFactor = 10;

    /**
     * Intialization of the Elixir.
     */
    public ItemElixir() {
        this.mPPRestoreFactor = 10;
        this.mName = "Elixir";
        this.mImageIcon = R.drawable.bag_elixir_icon;
        this.mImageBig = R.drawable.bag_elixir;
        this.mImageSprite = R.drawable.bag_elixir_icon;
    }

    /**
     * Initialization given the total quantity of the Elixir.
     * @param quantity  Integer value of the quantity.
     */
    public ItemElixir(int quantity) {
        this.mPPRestoreFactor = 10;
        this.mName = "Elixir";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_elixir_icon;
        this.mImageBig = R.drawable.bag_elixir;
        this.mImageSprite = R.drawable.bag_elixir_icon;
    }

    /**
     * When the Elixir is used in battle.
     * @param profile   Whoever is the targeted Pokémon.
     * @param info      The DisplayInfoSet to be updated.
     * @param battle    The Battle object where the Item is being used.
     */
    @Override
    public void useInBattle(PokémonProfile profile, DisplayInfoSet info, Battle battle){
        if(!restorePP(profile)){
            battle.addMessage(new Message(Message.ERROR_NO_EFFECT));
        }
    }

    /**
     * Restores all the Move's PP by 10.
     * @param profile   The Pokémon where the Item will be used.
     * @return boolean  True if restoration was successful.
     */
    public boolean restorePP(PokémonProfile profile){
        if(profile.allMovesPPisFull()){
            return false;
        }
        for(int index = 0; index < PokémonProfile.MAX_POKEMON_MOVES; index++){
            Move move = profile.getMoves().get(index);
            move.setCurrentPP(move.getCurrentPP() + mPPRestoreFactor);
            if(move.getCurrentPP() > move.getMaxPP()){
                move.setCurrentPP(move.getMaxPP());
            }
        }
        return true;
    }

    /**
     * When the Elixir is used in the Manager.
     * @param profile       The Pokémon where the Item will be used.
     * @param txvMessage    The TextView for outputting the message.
     * @param bag           Where the Item is stored.
     */
    @Override
    public void useInManager(PokémonProfile profile, TextView txvMessage, ArrayList<Item> bag){
        useItem(bag);
        if(!restorePP(profile)){
            txvMessage.setText(Message.ERROR_NO_EFFECT);
        }
        else{
            txvMessage.setText(profile.getNickname() + Message.MESSAGE_PP_RESTORED);
        }
    }

    /**
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemElixir(PokemonApp.getIntegerRNG(5) + 2);
    }
}
