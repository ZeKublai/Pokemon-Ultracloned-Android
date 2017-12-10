package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/29/2017.
 * This abstract class focuses on the entirety of the Manager State
 */

public abstract class ManagerState {
    protected ArrayList<PokémonButton> mPokémonButtons = new ArrayList<>();
    protected Button mBackButton;
    protected Button mSwitchButton;
    protected TextView mMessage;
    protected Manager mManager;


    /**
     * does nothing
     * @param ctx where the listview is to be displayed
     * @param view specific list of pokemon seen in the adapter
     * @param app access the PokemonApp functions
     * @param pos index of the listview
     */
    public void executePokemonListView(Activity ctx, View view, PokemonApp app, int pos){

    }

    /**
     * does nothing
     * @param ctx where the listview is to be displayed
     * @param app access the PokemonApp functions
     * @param pos index of the listview
     */
    public void executePokemonButton(Activity ctx, PokemonApp app, int pos){

    }

    /**
     * executes what the item in the list view does
     * @param pos index of the listview
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
            mManager.getSelectedItem().useInManager(mManager.getSelectedProfile1(), mMessage, mManager.getPlayer().getBag());
            mManager.setState(mainState());
            mMessage.setText(Message.ERROR_ECHO);
            PokemonApp.setAsBackButton(mBackButton);
        }
        updatePokemons();
        mManager.getPokemonAdapter().notifyDataSetChanged();
    }

    /**
     * does nothing
     */
    public void executeBackButton(){

    }

    /**
     * runs the implementation of the switch PokéDexData through the select State
     */
    public void executeSwitchButton(){
        PokemonApp.setAsCancelButton(mSwitchButton);
        noItemSelected();
        mManager.setState(selectState());
    }

    /**
     * sets the buttons into the party of 6 pokemon of the user
     */
    public void initializeTeam(){
        for(int index = 0; index < mPokémonButtons.size(); index++){
            mPokémonButtons.get(index).getButton().setClickable(false);
            mPokémonButtons.get(index).setVisibility(View.INVISIBLE);
        }
        updatePokemons();
    }

    /**
     * updates the party through the changes in the button
     */
    public void updatePokemons(){
        for(int index = 0; index < mManager.getPlayer().getPokemons().size(); index++) {
            mPokémonButtons.get(index).setData(mManager.getPlayer().getPokemons().get(index));
        }
    }

    /**
     * notifies that there in no selected item
     */
    public void noItemSelected(){
        mManager.getItemAdapter().itemSelected(ItemList.NO_ITEM_SELECTED);
        mManager.getItemAdapter().notifyDataSetChanged();
    }

    /**
     * get the button set to a PokéDexData
     * @return button set to a PokéDexData
     */
    public ArrayList<PokémonButton> getPokemonButtons() {
        return mPokémonButtons;
    }

    /**
     * set the button set to a PokéDexData
     * @param mPokémonButtons button set to a PokéDexData
     */
    public void setPokemonButtons(ArrayList<PokémonButton> mPokémonButtons) {
        this.mPokémonButtons = mPokémonButtons;
    }

    /**
     * get the button for the back option
     * @return button for the back option
     */
    public Button getBackButton() {
        return mBackButton;
    }

    /**
     * set the button for the back option
     * @param mBackButton button for the back option
     */
    public void setBackButton(Button mBackButton) {
        this.mBackButton = mBackButton;
    }

    /**
     * get the button pressed for switching
     * @return button pressed for switching
     */
    public Button getSwitchButton() {
        return mSwitchButton;
    }

    /**
     * set the button pressed for switching
     * @param mSwitchButton button pressed for switching
     */
    public void setSwitchButton(Button mSwitchButton) {
        this.mSwitchButton = mSwitchButton;
    }

    /**
     * get the outputted message
     * @return outputted message
     */
    public TextView getMessage() {
        return mMessage;
    }

    /**
     * set the outputted message
     * @param mMessage outputted message
     */
    public void setMessage(TextView mMessage) {
        this.mMessage = mMessage;
    }

    /**
     * get the generated Manager
     * @return generated Manager
     */
    public Manager getManager() {
        return mManager;
    }

    /**
     * set the desired Manager
     * @param mManager generate Manager
     */
    public void setManager(Manager mManager) {
        this.mManager = mManager;
    }

    /**
     * changes to the Manager acitivity when triggering the use item state
     * @return set of instructions for the given state
     */
    public ManagerUseItemState useItemState(){
        return new ManagerUseItemState(mPokémonButtons, mBackButton, mSwitchButton, mMessage, mManager);
    }

    /**
     * changes to the Manager acitivity when triggering the main state
     * @return set of instructions for the given state
     */
    public ManagerMainState mainState(){
        return new ManagerMainState(mPokémonButtons, mBackButton, mSwitchButton, mMessage, mManager);
    }

    /**
     * changes to the Manager acitivity when triggering the select state
     * @return set of instructions for the given state
     */
    public ManagerSelectState selectState(){
        return new ManagerSelectState(mPokémonButtons, mBackButton, mSwitchButton, mMessage, mManager);
    }

    /**
     * changes to the Manager acitivity when triggering the switch state
     * @return set of instructions for the given state
     */
    public ManagerSwitchState switchState(){
        return new ManagerSwitchState(mPokémonButtons, mBackButton, mSwitchButton, mMessage, mManager);
    }
}
