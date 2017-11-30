package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public abstract class ItemTargetTeam extends Item {
    @Override
    public PokemonProfile getExecuteTarget(Battle battle){return battle.getSelectedPokemon();}

    @Override
    public PokemonProfile getUpdateTarget(Battle battle){
        return battle.getBuddy();
    }

    @Override
    public PokemonInfo getTargetInfo(Battle battle){
        return battle.getBuddyInfo();
    }

}
