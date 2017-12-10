package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/20/2017.
 * This class is a subclass of the Decision which handles the Decision for using Item.
 */

public class DecisionUse extends Decision {
    private Item mSelectedItem;

    /**
     * Creates the DecisionUse to use the selected Item.
     * @param mSelectedItem The selected Item to be used.
     */
    public DecisionUse(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    /**
     * Checks if there is zero quantity of that Item.
     * @return  True if Item has 0 quantity.
     */
    @Override
    public boolean isError(){
        return (mSelectedItem.getQuantity() <= 0);
    }

    /**
     * Returns the error Message.
     * @return  The error Message.
     */
    @Override
    public Message getErrorMessage(){
        return new Message("You are out of " + mSelectedItem.getName() + "!");
    }

    /**
     * Uses the selected Item.
     * @param battle    The Battle object where the Decision is executed.
     */
    @Override
    public void execute(Battle battle){
        battle.getSelectedItem().useItem(battle.getPlayer().getBag());
    }

    /**
     * Updates the results of the Decision and adds Messages to the ArrayList in the Battle class.
     * @param battle    The Battle object where the Decision is executed.
     */
    @Override
    public void updateResults(Battle battle){
        battle.addMessage(new MessageUpdatePokemon(battle.getPlayer().getName() + " used " + mSelectedItem.getName() + "!", mSelectedItem.getTargetInfo(battle), mSelectedItem.getUpdateTarget(battle)));
        mSelectedItem.useInBattle(mSelectedItem.getExecuteTarget(battle), mSelectedItem.getTargetInfo(battle), battle);
    }
}
