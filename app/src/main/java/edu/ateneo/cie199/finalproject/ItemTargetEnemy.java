package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/21/2017.
 * This abstract class handles the functions when an item is used on enemy PokéDexData
 */

public abstract class ItemTargetEnemy extends Item {

    /**
     * On which pokemon the item is to be used
     * @param battle the battle state where the item is being used
     * @return intended target PokéDexData
     */
    @Override
    public PokémonProfile getExecuteTarget(Battle battle){return battle.getEnemy();}

    /**
     * Use on auto-selected PokéDexData when Item is used. i.e. pokeball used on PokéDexData
     * @param battle the battle state where the item is being used
     * @return execute the item effect on the PokéDexData
     */
    @Override
    public PokémonProfile getUpdateTarget(Battle battle){
        return battle.getEnemy();
    }

    /**
     * Get the information of the selected PokéDexData
     * @param battle the battle state where the item is being used
     * @return information on the PokéDexData
     */
    @Override
    public DisplayInfoSet getTargetInfo(Battle battle){
        return battle.getEnemyInfo();
    }
}
