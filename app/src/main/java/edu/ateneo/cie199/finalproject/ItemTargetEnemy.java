package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This abstract class handles the functions when an item is used on enemy Pokemon
 */

public abstract class ItemTargetEnemy extends Item {

    /**
     * On which pokemon the item is to be used
     * @param battle the battle state where the item is being used
     * @return intended target Pokemon
     */
    @Override
    public PokemonProfile getExecuteTarget(Battle battle){return battle.getEnemy();}

    /**
     * Use on auto-selected Pokemon when Item is used. i.e. pokeball used on Pokemon
     * @param battle the battle state where the item is being used
     * @return execute the item effect on the Pokemon
     */
    @Override
    public PokemonProfile getUpdateTarget(Battle battle){
        return battle.getEnemy();
    }

    /**
     * Get the information of the selected Pokemon
     * @param battle the battle state where the item is being used
     * @return information on the Pokemon
     */
    @Override
    public PokemonInfo getTargetInfo(Battle battle){
        return battle.getEnemyInfo();
    }
}
