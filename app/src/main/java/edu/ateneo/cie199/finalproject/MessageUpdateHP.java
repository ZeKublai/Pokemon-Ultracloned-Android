package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class MessageUpdateHP extends Message {
    private PokemonProfile mProfile;
    public MessageUpdateHP(String mMessage, PokemonInfo mDisplay, PokemonProfile profile) {
        super(mMessage, mDisplay);
        this.mProfile = profile;
    }

    @Override
    public void executeUpdate(Battle battle){
        getTarget(battle).updateHp(mProfile);
        updateBattleInfo(battle);
    }
}
