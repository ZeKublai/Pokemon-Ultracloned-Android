package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class MessageUpdateExp extends Message {
    private PokemonProfile mProfile;
    public MessageUpdateExp(String mMessage, PokemonInfo mDisplay, PokemonProfile profile) {
        super(mMessage, mDisplay);
        this.mProfile = profile;
    }

    @Override
    public void executeUpdate(Battle battle){
        getTarget(battle).updateExp(mProfile);
        updateBattleInfo(battle);
    }
}
