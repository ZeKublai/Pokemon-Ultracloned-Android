package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/29/2017.
 * This class handles different interaction about the caught Pokemons.
 * Initializes the needed values for the Manager Activity
 */

public class Manager {
    private int mMenuState = PokemonGoApp.STATE_MAIN;
    private Item mSelectedItem;
    private PokémonProfile mSelectedProfile1;
    private PokémonProfile mSelectedProfile2;
    private Player mPlayer;
    private PokémonList mPokemonAdapter = null;
    private ItemList mItemAdapter = null;

    private ManagerState mState;

    /**
     * initialize the components of the Activity
     * @param mMenuState determine the state of the Menu
     * @param mSelectedItem determine the selected item
     * @param mSelectedProfile1 determine the first pokemon for switching
     * @param mSelectedProfile2 determine the second pokemon for switching
     * @param mPlayer determines the Player
     * @param mPokemonAdapter determines the list of PokéDexData in your box storage
     * @param mItemAdapter determines the list of Items that the User owns
     */
    public Manager(int mMenuState, Item mSelectedItem, PokémonProfile mSelectedProfile1, PokémonProfile mSelectedProfile2, Player mPlayer, PokémonList mPokemonAdapter, ItemList mItemAdapter) {
        this.mMenuState = mMenuState;
        this.mSelectedItem = mSelectedItem;
        this.mSelectedProfile1 = mSelectedProfile1;
        this.mSelectedProfile2 = mSelectedProfile2;
        this.mPlayer = mPlayer;
        this.mPokemonAdapter = mPokemonAdapter;
        this.mItemAdapter = mItemAdapter;
    }

    /**
     * does nothing
     */
    public Manager(){
    }

    /**
     * get the selected item
     * @return selected item
     */
    public Item getSelectedItem() {
        return mSelectedItem;
    }

    /**
     * set the selected item
     * @param mSelectedItem selected item
     */
    public void setSelectedItem(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    /**
     * get the information of the first selected PokéDexData for switching
     * @return information of the first selected PokéDexData for switching
     */
    public PokémonProfile getSelectedProfile1() {
        return mSelectedProfile1;
    }

    /**
     * set the information of the first selected PokéDexData for switching
     * @param mSelectedProfile1
     */
    public void setSelectedProfile1(PokémonProfile mSelectedProfile1) {
        this.mSelectedProfile1 = mSelectedProfile1;
    }

    /**
     * get the information of the second selected PokéDexData for switching
     * @return information of the second selected PokéDexData for switching
     */
    public PokémonProfile getSelectedProfile2() {
        return mSelectedProfile2;
    }

    /**
     * set the information of the second selected PokéDexData for switching
     * @param mSelectedProfile2
     */
    public void setSelectedProfile2(PokémonProfile mSelectedProfile2) {
        this.mSelectedProfile2 = mSelectedProfile2;
    }

    /**
     * get the Player data
     * @return Player data
     */
    public Player getPlayer() {
        return mPlayer;
    }

    /**
     * set the Player data
     * @param mPlayer Player data
     */
    public void setPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

    /**
     * obtains the list of PokéDexData from storage
     * @return list of PokéDexData from storage
     */
    public PokémonList getPokemonAdapter() {
        return mPokemonAdapter;
    }

    /**
     * set the list of PokéDexData from storage
     * @param mPokemonAdapter list of PokéDexData from storage
     */
    public void setPokemonAdapter(PokémonList mPokemonAdapter) {
        this.mPokemonAdapter = mPokemonAdapter;
    }

    /**
     * obtains the list of Item
     * @return list of Iteme
     */
    public ItemList getItemAdapter() {
        return mItemAdapter;
    }

    /**
     * set the list of items
     * @param mItemAdapter list of items
     */
    public void setItemAdapter(ItemList mItemAdapter) {
        this.mItemAdapter = mItemAdapter;
    }

    /**
     * get the current state of the Manager
     * @return current state of the Manager
     */
    public ManagerState getState() {
        return mState;
    }

    /**
     * set the current state of the Manager
     * @param mState current state of the Manager
     */
    public void setState(ManagerState mState) {
        this.mState = mState;
    }
}
