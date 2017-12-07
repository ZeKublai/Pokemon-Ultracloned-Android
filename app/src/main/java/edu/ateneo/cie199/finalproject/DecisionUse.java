package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/20/2017.
 * This class is a subclass of the decision which handles the decision for using item
 */

public class DecisionUse extends Decision {
    private Item mSelectedItem;

    /**
     * Decision to use selected item
     * @param mSelectedItem selected item
     */
    public DecisionUse(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    /**
     * check if there is zero quantity of that item
     * @return boolean value if there is no quantity
     */
    @Override
    public boolean isError(){
        if(mSelectedItem.getQuantity() <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * get the data for the selected item
     * @return data for the selected item
     */
    public Item getSelectedItem() {
        return mSelectedItem;
    }

    /**
     * get the error message when quantity is zero
     * @return error message
     */
    @Override
    public Message getErrorMessage(){
        return new Message("You are out of " + mSelectedItem.getName() + "!");
    }

    /**
     * execute the selected item from the bag
     * @param battle default filler
     */
    @Override
    public void execute(Battle battle){
        battle.getSelectedItem().useItem(battle.getPlayer().getBag());
    }

    /**
     * update message to be displayed
     * @param battle default filler
     */
    @Override
    public void updateResults(Battle battle){
        battle.addMessage(new MessageUpdatePokemon(battle.getPlayer().getName() + " used " + mSelectedItem.getName() + "!", mSelectedItem.getTargetInfo(battle), mSelectedItem.getUpdateTarget(battle)));
        mSelectedItem.useInBattle(mSelectedItem.getExecuteTarget(battle), mSelectedItem.getTargetInfo(battle), battle);
    }

    /**
     * check if decision is empty
     * @return boolean value with a default value of false
     */
    @Override
    public boolean isEmpty(){
        return false;
    }
}
