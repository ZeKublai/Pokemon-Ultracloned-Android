package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/13/2017.
 * This abstract class handles the functions when an Item is used on a party member Pokémon.
 */

public abstract class ItemTargetTeam extends Item {

    /**
     * Returns the Pokémon that where the Item will be used.
     * @param battle    The Battle where the Item is being used.
     * @return          The Pokémon that where the Item will be used.
     */
    @Override
    public PokémonProfile getExecuteTarget(Battle battle){return battle.getSelectedPokemon();}

    /**
     * Returns the Pokémon that will be updated upon execution of the Item.
     * @param battle    The Battle where the Item is being used.
     * @return          The intended Pokémon to be updated.
     */
    @Override
    public PokémonProfile getUpdateTarget(Battle battle){
        return battle.getBuddy();
    }

    /**
     * Returns the DisplayInfoSet that will be updated upon execution of the Item.
     * @param battle    The Battle where the Item is being used.
     * @return          The intended DisplayInfoSet to be updated.
     */
    @Override
    public DisplayInfoSet getTargetInfo(Battle battle){
        return battle.getBuddyInfo();
    }

}
