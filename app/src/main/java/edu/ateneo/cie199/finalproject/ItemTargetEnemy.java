package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public abstract class ItemTargetEnemy extends Item {
    @Override
    public PokemonProfile getExecuteTarget(Battle battle){return battle.getEnemy();}

    @Override
    public PokemonProfile getUpdateTarget(Battle battle){
        return battle.getEnemy();
    }

    @Override
    public PokemonInfo getTargetInfo(Battle battle){
        return battle.getEnemyInfo();
    }
}
