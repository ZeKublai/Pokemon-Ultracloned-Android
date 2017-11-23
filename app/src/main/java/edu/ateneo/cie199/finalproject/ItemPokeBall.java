package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.lang.Math.pow;

/**
 * Created by John on 11/21/2017.
 */

public class ItemPokeBall extends ItemTargetEnemy {

    public ItemPokeBall() {
        this.mBallBonus = 1.0;
        this.mName = "Poke Ball";
        this.mImageIcon = R.drawable.bag_pokeball_icon;
        this.mImageBig = R.drawable.bag_pokeball;
        this.mImageSprite = R.drawable.bag_pokeball_sprite;
    }

    public ItemPokeBall(int quantity) {
        this.mBallBonus = 1.0;
        this.mName = "Poke Ball";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_pokeball_icon;
        this.mImageBig = R.drawable.bag_pokeball;
        this.mImageSprite = R.drawable.bag_pokeball_sprite;
    }

    protected double mBallBonus = 1.0;
    public double getBallBonus() {
        return mBallBonus;
    }
    public void setBallBonus(double mBallBonus) {
        this.mBallBonus = mBallBonus;
    }

    @Override
    public void useInBattle(PokemonProfile profile, PokemonInfo info, Battle battle){
        int result = getResult(profile);
        String message = "";
        for(int index = 1; index < result; index++){
            message = message + index + "...";
            battle.addMessage(new MessageUpdateCatch(message, getTargetInfo(battle), battle.getSelectedItem()));
        }
        if(result < 4){
            battle.addMessage(new MessageUpdatePokemon(profile.getNickname() + Message.MESSAGE_ESCAPED, getTargetInfo(battle), getUpdateTarget(battle)));
        }
        else if(result == 4){
            battle.addMessage(new Message(profile.getNickname() + Message.MESSAGE_CAUGHT));
            battle.setEnemyCaught(true);
        }
    }

    private int getResult(PokemonProfile profile){
        int catchRate = (int) getSecondCatchRate(getFinalCatchRate(profile));
        int attempt1 = PokemonGoApp.getIntegerRNG(65535);
        int attempt2 = PokemonGoApp.getIntegerRNG(65535);
        int attempt3 = PokemonGoApp.getIntegerRNG(65535);
        if(attempt1 >= catchRate){
            return 1;
        }
        else{
            if(attempt2 >= catchRate){
                return 2;
            }
            else{
                if(attempt3 >= catchRate){
                    return 3;
                }
                else{
                    return 4;
                }
            }
        }
    }

    private double getFinalCatchRate(PokemonProfile profile){
        double result = ((3.0*((double) profile.getHP()) - 2.0*((double) profile.getCurrentHP()))*mBallBonus*
                ((double) profile.getDexData().getCatchRate()))/(3.0*((double) profile.getHP()));
        return result;
    }
    private double getSecondCatchRate(double finalCatchRate){
        double result = floor(65536.0/(pow(255.0/(finalCatchRate), 3.0/16.0)));
        return result;
    }

    @Override
    public void useInManager(PokemonProfile profile, TextView txvMessage, ArrayList<Item> bag){
        txvMessage.setText(Message.ERROR_ECHO);
    }

    @Override
    public Item generateCopy(){
        return new ItemPokeBall(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
