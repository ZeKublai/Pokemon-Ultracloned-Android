package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/20/2017.
 */

public class MessageUpdateCatch extends Message {
    private Item mItem;
    public MessageUpdateCatch(String mMessage, PokemonInfo mDisplay, Item item) {
        super(mMessage, mDisplay);
        this.mItem = item;
    }

    @Override
    public void executeUpdate(Battle battle){
        getTarget(battle).updateCatch(mItem);
        updateBattleInfo(battle);
    }
}
