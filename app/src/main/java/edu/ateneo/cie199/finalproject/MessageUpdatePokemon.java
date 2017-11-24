package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/20/2017.
 */

public class MessageUpdatePokemon extends Message {
    private PokemonProfile mProfile;
    public MessageUpdatePokemon(String mMessage, PokemonInfo mDisplay, PokemonProfile profile) {
        super(mMessage, mDisplay);
        this.mProfile = profile;
    }

    @Override
    public void executeUpdate(Battle battle){
        getTarget(battle).updatePokemon(mProfile);
        updateBattleInfo(battle);
    }
}
