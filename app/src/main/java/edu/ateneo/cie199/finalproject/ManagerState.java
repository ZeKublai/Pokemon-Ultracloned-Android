package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/29/2017.
 */

public abstract class ManagerState {
    protected ArrayList<PokemonButton> mPokemonButtons = new ArrayList<>();
    protected Button mBackButton;
    protected Button mSwitchButton;
    protected TextView mMessage;
    protected Manager mManager;


    public void executePokemonListView(Activity ctx, View view, PokemonGoApp app, int pos){

    }

    public void executePokemonButton(Activity ctx, PokemonGoApp app, int pos){

    }

    public void executeItemListView(int pos){
        PokemonGoApp.setAsSwitchButton(mSwitchButton);
        mManager.setSelectedItem(mManager.getPlayer().getBag().get(pos));
        if(mManager.getSelectedItem() instanceof ItemTargetTeam){
            mManager.getItemAdapter().itemSelected(pos);
            PokemonGoApp.setAsCancelButton(mBackButton);
            mManager.setState(useItemState());
        }
        else{
            mManager.getItemAdapter().itemSelected(ItemList.NO_ITEM_SELECTED);
            mManager.getSelectedItem().useInManager(mManager.getSelectedProfile1(), mMessage, mManager.getPlayer().getBag());
            mManager.setState(mainState());
            mMessage.setText(Message.ERROR_ECHO);
            PokemonGoApp.setAsBackButton(mBackButton);
        }
        updatePokemons();
        mManager.getPokemonAdapter().notifyDataSetChanged();
    }

    public void executeBackButton(){

    }

    public void executeSwitchButton(){
        PokemonGoApp.setAsCancelButton(mSwitchButton);
        noItemSelected();
        mManager.setState(selectState());
    }

    public void initializeTeam(){
        for(int index = 0; index < mPokemonButtons.size(); index++){
            mPokemonButtons.get(index).getButton().setClickable(false);
            mPokemonButtons.get(index).setVisibility(View.INVISIBLE);
        }
        updatePokemons();
    }

    public void updatePokemons(){
        for(int index = 0; index < mManager.getPlayer().getPokemons().size(); index++) {
            mPokemonButtons.get(index).setData(mManager.getPlayer().getPokemons().get(index));
        }
    }

    public void noItemSelected(){
        mManager.getItemAdapter().itemSelected(ItemList.NO_ITEM_SELECTED);
        mManager.getItemAdapter().notifyDataSetChanged();
    }

    public ArrayList<PokemonButton> getPokemonButtons() {
        return mPokemonButtons;
    }
    public void setPokemonButtons(ArrayList<PokemonButton> mPokemonButtons) {
        this.mPokemonButtons = mPokemonButtons;
    }

    public Button getBackButton() {
        return mBackButton;
    }
    public void setBackButton(Button mBackButton) {
        this.mBackButton = mBackButton;
    }

    public Button getSwitchButton() {
        return mSwitchButton;
    }
    public void setSwitchButton(Button mSwitchButton) {
        this.mSwitchButton = mSwitchButton;
    }

    public TextView getMessage() {
        return mMessage;
    }
    public void setMessage(TextView mMessage) {
        this.mMessage = mMessage;
    }

    public Manager getManager() {
        return mManager;
    }
    public void setManager(Manager mManager) {
        this.mManager = mManager;
    }

    public ManagerUseItemState useItemState(){
        return new ManagerUseItemState(mPokemonButtons, mBackButton, mSwitchButton, mMessage, mManager);
    }

    public ManagerMainState mainState(){
        return new ManagerMainState(mPokemonButtons, mBackButton, mSwitchButton, mMessage, mManager);
    }

    public ManagerSelectState selectState(){
        return new ManagerSelectState(mPokemonButtons, mBackButton, mSwitchButton, mMessage, mManager);
    }

    public ManagerSwitchState switchState(){
        return new ManagerSwitchState(mPokemonButtons, mBackButton, mSwitchButton, mMessage, mManager);
    }
}
