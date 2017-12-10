package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Item elixir interact with the PokéDexData
 */

public class ItemElixir extends ItemTargetTeam {

    protected int mPPRestoreFactor = 10;

    /**
     * Intialization of the Elixir
     */
    public ItemElixir() {
        this.mPPRestoreFactor = 10;
        this.mName = "Elixir";
        this.mImageIcon = R.drawable.bag_elixir_icon;
        this.mImageBig = R.drawable.bag_elixir;
        this.mImageSprite = R.drawable.bag_elixir_icon;
    }

    /**
     * Total quantity of the elixir
     * @param quantity integer value of the quantity
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
     * When the elixir is used in battle
     * @param profile whoever is the targetting pokemon
     * @param info information of the pokemon
     * @param battle the battle state where the item is being used
     */
    @Override
    public void useInBattle(PokémonProfile profile, DisplayInfoSet info, Battle battle){
        if(!restorePP(profile)){
            battle.addMessage(new Message(Message.ERROR_NO_EFFECT));
        }
    }

    /**
     * Restores all the moves of the PP by 10
     * @param profile whoever is the targetting pokemon
     * @return boolean value to confirm if PP was successfully used
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
     * When the Elixir is used in the Manager
     * @param profile whoever is the targetting pokemon
     * @param txvMessage message outputted when item is used
     * @param bag where the item is stored
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
     * Duplicate the elixir
     * @return
     */
    @Override
    public Item generateCopy(){
        return new ItemElixir(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
