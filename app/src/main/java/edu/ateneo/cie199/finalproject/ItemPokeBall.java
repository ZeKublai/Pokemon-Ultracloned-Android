package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.lang.Math.pow;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Pokeball interact with the PokéDexData.
 */

public class ItemPokeBall extends ItemTargetEnemy {

    /**
     * intialize the Pokeball
     */
    public ItemPokeBall() {
        this.mBallBonus = 1.0;
        this.mName = "Poke Ball";
        this.mImageIcon = R.drawable.bag_pokeball_icon;
        this.mImageBig = R.drawable.bag_pokeball;
        this.mImageSprite = R.drawable.bag_pokeball_sprite;
    }

    /**
     * Total quantity of the Pokeball
     * @param quantity integer value of the quantity
     */
    public ItemPokeBall(int quantity) {
        this.mBallBonus = 1.0;
        this.mName = "Poke Ball";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_pokeball_icon;
        this.mImageBig = R.drawable.bag_pokeball;
        this.mImageSprite = R.drawable.bag_pokeball_sprite;
    }

    protected double mBallBonus = 1.0;

    /**
     * The function of the Pokeball when used in battle
     * @param profile whoever is the targetting pokemon
     * @param info information of the pokemon
     * @param battle the battle state where the item is being used
     */
    @Override
    public void useInBattle(PokémonProfile profile, DisplayInfoSet info, Battle battle){
        if(battle instanceof TrainerBattle){
            String message = ((TrainerBattle) battle).getTrainer().getName() + " blocked the ball!";
            battle.addMessage(new Message(message));
            message = ((TrainerBattle) battle).getTrainer().getName() + ": Flicking balls at other people is cheating!";
            battle.addMessage(new Message(message));

        }
        else{
            int result = getResult(profile);
            String message = "... ";
            battle.addMessage(new MessageUpdateCatch(message, getTargetInfo(battle), battle.getSelectedItem()));
            for(int index = 1; index < result; index++){
                message = message + index + "... ";
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
    }

    /**
     * Used when attempting to catch a PokéDexData
     * @param profile details of the PokéDexData being caught
     * @return
     */
    private int getResult(PokémonProfile profile){
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

    /**
     * Uses a formula for catch rate
     * @param profile details of the PokéDexData being caught
     * @return value of the catch rate
     */
    private double getFinalCatchRate(PokémonProfile profile){
        double result = ((3.0*((double) profile.getHP()) - 2.0*((double) profile.getCurrentHP()))*mBallBonus*
                ((double) profile.getDexData().getCatchRate()))/(3.0*((double) profile.getHP()));
        return result;
    }

    /**
     * Another set of computing for catch rate
     * @param finalCatchRate obtained value from another equation
     * @return value of the new catch rate
     */
    private double getSecondCatchRate(double finalCatchRate){
        double result = floor(65536.0/(pow(255.0/(finalCatchRate), 3.0/16.0)));
        return result;
    }

    /**
     * The function of the Pokeball when used in the Manager Activity
     * @param profile whoever is the targetting pokemon
     * @param txvMessage message outputted when item is used
     * @param bag where the item is stored
     */
    @Override
    public void useInManager(PokémonProfile profile, TextView txvMessage, ArrayList<Item> bag){
        txvMessage.setText(Message.ERROR_ECHO);
    }

    /**
     * duplicate the Pokeball
     * @return duplicated copy
     */
    @Override
    public Item generateCopy(){
        return new ItemPokeBall(PokemonGoApp.getIntegerRNG(5) + 2);
    }
}
