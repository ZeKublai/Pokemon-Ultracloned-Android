package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/13/2017.
 * This abstract class handles the functions when an item is used on team member Pokemon
 */

public abstract class ItemTargetTeam extends Item {

    /**
     * On which pokemon the item is to be used
     * @param battle the battle state where the item is being used
     * @return intended target Pokemon
     */
    @Override
    public PokemonProfile getExecuteTarget(Battle battle){return battle.getSelectedPokemon();}

    /**
     * execute the item effect on the Pokemon
     * @param battle the battle state where the item is being used
     * @return
     */
    @Override
    public PokemonProfile getUpdateTarget(Battle battle){
        return battle.getBuddy();
    }

    /**
     * information on the Pokemon
     * @param battle the battle state where the item is being used
     * @return
     */
    @Override
    public PokemonInfo getTargetInfo(Battle battle){
        return battle.getBuddyInfo();
    }

}
