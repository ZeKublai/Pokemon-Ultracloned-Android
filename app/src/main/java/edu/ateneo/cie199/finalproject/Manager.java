package edu.ateneo.cie199.finalproject;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/29/2017.
 */

public class Manager {
    private int mMenuState = PokemonGoApp.STATE_MAIN;
    private Item mSelectedItem;
    private PokemonProfile mSelectedProfile1;
    private PokemonProfile mSelectedProfile2;
    private Player mPlayer;
    private PokemonList mPokemonAdapter = null;
    private ItemList mItemAdapter = null;

    private ManagerState mState;

    public Manager(int mMenuState, Item mSelectedItem, PokemonProfile mSelectedProfile1, PokemonProfile mSelectedProfile2, Player mPlayer, PokemonList mPokemonAdapter, ItemList mItemAdapter) {
        this.mMenuState = mMenuState;
        this.mSelectedItem = mSelectedItem;
        this.mSelectedProfile1 = mSelectedProfile1;
        this.mSelectedProfile2 = mSelectedProfile2;
        this.mPlayer = mPlayer;
        this.mPokemonAdapter = mPokemonAdapter;
        this.mItemAdapter = mItemAdapter;
    }

    public Manager(){

    }

    public int getMenuState() {
        return mMenuState;
    }
    public void setMenuState(int mMenuState) {
        this.mMenuState = mMenuState;
    }

    public Item getSelectedItem() {
        return mSelectedItem;
    }
    public void setSelectedItem(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    public PokemonProfile getSelectedProfile1() {
        return mSelectedProfile1;
    }
    public void setSelectedProfile1(PokemonProfile mSelectedProfile1) {
        this.mSelectedProfile1 = mSelectedProfile1;
    }

    public PokemonProfile getSelectedProfile2() {
        return mSelectedProfile2;
    }
    public void setSelectedProfile2(PokemonProfile mSelectedProfile2) {
        this.mSelectedProfile2 = mSelectedProfile2;
    }

    public Player getPlayer() {
        return mPlayer;
    }
    public void setPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

    public PokemonList getPokemonAdapter() {
        return mPokemonAdapter;
    }
    public void setPokemonAdapter(PokemonList mPokemonAdapter) {
        this.mPokemonAdapter = mPokemonAdapter;
    }

    public ItemList getItemAdapter() {
        return mItemAdapter;
    }
    public void setItemAdapter(ItemList mItemAdapter) {
        this.mItemAdapter = mItemAdapter;
    }

    public ManagerState getState() {
        return mState;
    }
    public void setState(ManagerState mState) {
        this.mState = mState;
    }
}
