package edu.ateneo.cie199.finalproject;

import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.lang.Math.pow;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This class handles how the Poké Ball interact with the Pokémon.
 */

public class ItemPokeBall extends ItemTargetEnemy {

    /**
     * Intialization of the PokéBall.
     */
    public ItemPokeBall() {
        this.mBallBonus = 1.0;
        this.mName = "Poké Ball";
        this.mImageIcon = R.drawable.bag_pokeball_icon;
        this.mImageBig = R.drawable.bag_pokeball;
        this.mImageSprite = R.drawable.bag_pokeball_sprite;
    }

    /**
     * Initialization given the total quantity of the Poké Ball.
     * @param quantity  Integer value of the quantity.
     */
    public ItemPokeBall(int quantity) {
        this.mBallBonus = 1.0;
        this.mName = "Poké Ball";
        this.mQuantity = quantity;
        this.mImageIcon = R.drawable.bag_pokeball_icon;
        this.mImageBig = R.drawable.bag_pokeball;
        this.mImageSprite = R.drawable.bag_pokeball_sprite;
    }

    protected double mBallBonus = 1.0;

    /**
     * The function of the Poké Ball when used in battle.
     * @param profile   Whoever is the targeted Pokémon.
     * @param info      The DisplayInfoSet to be updated.
     * @param battle    The Battle object where the Item is being used.
     */
    @Override
    public void useInBattle(PokémonProfile profile, DisplayInfoSet info, Battle battle){
        if(battle instanceof TrainerBattle){
            String message = ((TrainerBattle) battle).getTrainer().getName() + " blocked the ball!";
            battle.addMessage(new Message(message));
            message = ((TrainerBattle) battle).getTrainer().getName()
                    + ": Flicking balls at other people is cheating!";
            battle.addMessage(new Message(message));

        }
        else{
            int result = getResult(profile);
            String message = "... ";
            battle.addMessage(new MessageUpdateCatch(
                    message,
                    getTargetInfo(battle),
                    battle.getSelectedItem()
            ));
            for(int index = 1; index < result; index++){
                message = message + index + "... ";
                battle.addMessage(new MessageUpdateCatch(
                        message,
                        getTargetInfo(battle),
                        battle.getSelectedItem()
                ));
            }
            if(result < 4){
                battle.addMessage(new MessageUpdatePokemon(
                        profile.getNickname()
                        + Message.MESSAGE_ESCAPED,
                        getTargetInfo(battle),
                        getUpdateTarget(battle)
                ));
            }
            else if(result == 4){
                battle.addMessage(new Message(profile.getNickname() + Message.MESSAGE_CAUGHT));
                battle.setEnemyCaught(true);
            }
        }
    }

    /**
     * Used when attempting to catch a Pokémon.
     * @param profile   The Pokémon being caught.
     * @return          Which shake the Pokémon was able to break free.
     */
    private int getResult(PokémonProfile profile){
        int catchRate = (int) getSecondCatchRate(getFinalCatchRate(profile));
        int attempt1 = PokemonApp.getIntegerRNG(65535);
        int attempt2 = PokemonApp.getIntegerRNG(65535);
        int attempt3 = PokemonApp.getIntegerRNG(65535);
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
     * The formula for final catch rate.
     * @param profile   The Pokémon being caught.
     * @return          The final value of the catch rate.
     */
    private double getFinalCatchRate(PokémonProfile profile){
        double result = ((3.0*((double) profile.getHP()) - 2.0*((double) profile.getCurrentHP()))*mBallBonus*
                ((double) profile.getDexData().getCatchRate()))/(3.0*((double) profile.getHP()));
        return result;
    }

    /**
     * The formula for the second catch rate.
     * @param finalCatchRate    The value of the final catch rate.
     * @return                  The value of the second catch rate.
     */
    private double getSecondCatchRate(double finalCatchRate){
        double result = floor(65536.0/(pow(255.0/(finalCatchRate), 3.0/16.0)));
        return result;
    }

    /**
     * The function of the Poké Ball when used in the Manager Activity.
     * @param profile       The Pokémon where the Item will be used.
     * @param txvMessage    The TextView for outputting the message.
     * @param bag           Where the Item is stored.
     */
    @Override
    public void useInManager(PokémonProfile profile, TextView txvMessage, ArrayList<Item> bag){
        txvMessage.setText(Message.ERROR_ECHO);
    }

    /**
     * Returns a duplicate of the Item.
     * @return  A duplicate of the Item.
     */
    @Override
    public Item generateCopy(){
        return new ItemPokeBall(PokemonApp.getIntegerRNG(5) + 2);
    }
}
