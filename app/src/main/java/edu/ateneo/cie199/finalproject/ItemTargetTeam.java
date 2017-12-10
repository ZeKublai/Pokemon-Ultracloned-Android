package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/13/2017.
 * This abstract class handles the functions when an item is used on team member PokéDexData
 */

public abstract class ItemTargetTeam extends Item {

    /**
     * On which pokemon the item is to be used
     * @param battle the battle state where the item is being used
     * @return intended target PokéDexData
     */
    @Override
    public PokémonProfile getExecuteTarget(Battle battle){return battle.getSelectedPokemon();}

    /**
     * execute the item effect on the PokéDexData
     * @param battle the battle state where the item is being used
     * @return
     */
    @Override
    public PokémonProfile getUpdateTarget(Battle battle){
        return battle.getBuddy();
    }

    /**
     * information on the PokéDexData
     * @param battle the battle state where the item is being used
     * @return
     */
    @Override
    public DisplayInfoSet getTargetInfo(Battle battle){
        return battle.getBuddyInfo();
    }

}
