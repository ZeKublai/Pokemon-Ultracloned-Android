package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/20/2017.
 */

public class DecisionUse extends Decision {
    private Item mSelectedItem;

    public DecisionUse(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    @Override
    public boolean isError(){
        if(mSelectedItem.getQuantity() <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Item getSelectedItem() {
        return mSelectedItem;
    }
    public void setSelectedItem(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    @Override
    public Message getErrorMessage(){
        return new Message("You are out of " + mSelectedItem.getName() + "!");
    }

    @Override
    public void execute(Battle battle){
        battle.getSelectedItem().useItem(battle.getPlayer().getBag());
    }

    @Override
    public void updateResults(Battle battle){
        battle.addMessage(new MessageUpdatePokemon(battle.getPlayer().getName() + " used " + mSelectedItem.getName() + "!", mSelectedItem.getTargetInfo(battle), mSelectedItem.getUpdateTarget(battle)));
        mSelectedItem.useInBattle(mSelectedItem.getExecuteTarget(battle), mSelectedItem.getTargetInfo(battle), battle);
    }

    @Override
    public boolean isEmpty(){
        return false;
    }
}
