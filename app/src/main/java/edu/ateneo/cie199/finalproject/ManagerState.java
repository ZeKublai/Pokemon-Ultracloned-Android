package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/29/2017.
 * This abstract class focuses on the states of the Manager object.
 */

public abstract class ManagerState {
    protected ArrayList<PokémonButton> mPokémonButtons = new ArrayList<>();
    protected Button mBackButton;
    protected Button mSwitchButton;
    protected TextView mMessage;
    protected Manager mManager;


    /**
     * Executes when the Pokémon in the box has been pressed.
     * @param contex    Where the ListView is to be displayed.
     * @param view      Specific list of Pokémon seen in the adapter.
     * @param app       Used to access the PokémonApp functions.
     * @param pos       The index at the ListView.
     */
    public void executePokemonListView(Activity contex, View view, PokemonApp app, int pos){

    }

    /**
     * Executes when the Pokémon in the party has been pressed.
     * @param contex    Where the ListView is to be displayed.
     * @param app       Used to access the PokémonApp functions.
     * @param pos       The index at the ListView.
     */
    public void executePokemonButton(Activity contex, PokemonApp app, int pos){

    }

    /**
     * Executes when the Item in the ListView is pressed.
     * @param pos   Index of the selected Item in the ListView.
     */
    public void executeItemListView(int pos){
        PokemonApp.setAsSwitchButton(mSwitchButton);
        mManager.setSelectedItem(mManager.getPlayer().getBag().get(pos));
        if(mManager.getSelectedItem() instanceof ItemTargetTeam){
            mManager.getItemAdapter().itemSelected(pos);
            PokemonApp.setAsCancelButton(mBackButton);
            mManager.setState(useItemState());
        }
        else{
            mManager.getItemAdapter().itemSelected(ItemList.NO_ITEM_SELECTED);
            mManager.getSelectedItem().useInManager(
                    mManager.getSelectedProfile1(),
                    mMessage,
                    mManager.getPlayer().getBag()
            );
            mManager.setState(mainState());
            mMessage.setText(Message.ERROR_ECHO);
            PokemonApp.setAsBackButton(mBackButton);
        }
        updatePokémonButtons();
        mManager.getPokemonAdapter().notifyDataSetChanged();
    }

    /**
     * Does nothing.
     */
    public void executeBackButton(){

    }

    /**
     * Runs the implementation of the switch Button.
     */
    public void executeSwitchButton(){
        PokemonApp.setAsCancelButton(mSwitchButton);
        noItemSelected();
        mManager.setState(selectState());
    }

    /**
     * Resets the Buttons that contain the Pokémon in the Player's party.
     */
    public void ResetPokémonButtons(){
        for(int index = 0; index < mPokémonButtons.size(); index++){
            mPokémonButtons.get(index).getButton().setClickable(false);
            mPokémonButtons.get(index).setVisibility(View.INVISIBLE);
        }
        updatePokémonButtons();
    }

    /**
     * Updates the Buttons that contain the Pokémon in the Player's party.
     */
    public void updatePokémonButtons(){
        for(int index = 0; index < mManager.getPlayer().getPokemons().size(); index++) {
            mPokémonButtons.get(index).setData(mManager.getPlayer().getPokemons().get(index));
        }
    }

    /**
     * Sets the Item Adapter such that the it doesn't select an Item.
     */
    public void noItemSelected(){
        mManager.getItemAdapter().itemSelected(ItemList.NO_ITEM_SELECTED);
        mManager.getItemAdapter().notifyDataSetChanged();
    }


    /**
     * Gets the back Button.
     * @return  The back Button.
     */
    public Button getBackButton() {
        return mBackButton;
    }

    /**
     * Gets the switch Button.
     * @return  The switch Button.
     */
    public Button getSwitchButton() {
        return mSwitchButton;
    }

    /**
     * Gets the TextView containing the message.
     * @return  The TextView containing the message.
     */
    public TextView getMessage() {
        return mMessage;
    }

    /**
     * Sets the TextView containing the message.
     * @return  The TextView containing the message to be set.
     */
    public void setMessage(TextView mMessage) {
        this.mMessage = mMessage;
    }

    /**
     * Gets the Manager object.
     * @return  The Manager object.
     */
    public Manager getManager() {
        return mManager;
    }

    /**
     * Returns the current state as a ManagerUseItemState.
     * @return  The current state as a ManagerUseItemState.
     */
    public ManagerUseItemState useItemState(){
        return new ManagerUseItemState(
                mPokémonButtons,
                mBackButton,
                mSwitchButton,
                mMessage,
                mManager
        );
    }

    /**
     * Returns the current state as a ManagerMainState.
     * @return  The current state as a ManagerMainState.
     */
    public ManagerMainState mainState(){
        return new ManagerMainState(
                mPokémonButtons,
                mBackButton,
                mSwitchButton,
                mMessage,
                mManager
        );
    }

    /**
     * Returns the current state as a ManagerSelectState.
     * @return  The current state as a ManagerSelectState.
     */
    public ManagerSelectState selectState(){
        return new ManagerSelectState(
                mPokémonButtons,
                mBackButton,
                mSwitchButton,
                mMessage,
                mManager
        );
    }

    /**
     * Returns the current state as a ManagerSwitchState.
     * @return  The current state as a ManagerSwitchState.
     */
    public ManagerSwitchState switchState(){
        return new ManagerSwitchState(
                mPokémonButtons,
                mBackButton,
                mSwitchButton,
                mMessage,
                mManager
        );
    }
}
