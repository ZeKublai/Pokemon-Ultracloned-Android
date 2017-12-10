package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/29/2017.
 * This class handles different interaction about the caught Pokémons.
 * Initializes the needed values for the Manager Activity.
 */

public class Manager {
    private Item mSelectedItem;
    private PokémonProfile mSelectedProfile1;
    private PokémonProfile mSelectedProfile2;
    private Player mPlayer;
    private PokémonList mPokemonAdapter = null;
    private ItemList mItemAdapter = null;
    private ManagerState mState;

    /**
     * Does nothing.
     */
    public Manager(){
    }

    /**
     * Get the selected Item.
     * @return selected Item.
     */
    public Item getSelectedItem() {
        return mSelectedItem;
    }

    /**
     * Sets the selected Item.
     * @param mSelectedItem The selected Item to be set.
     */
    public void setSelectedItem(Item mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    /**
     * Get the first selected Pokémon for switching.
     * @return  The first selected Pokémon for switching.
     */
    public PokémonProfile getSelectedProfile1() {
        return mSelectedProfile1;
    }

    /**
     * Sets the first selected Pokémon for switching.
     * @param mSelectedProfile1 The selected Pokémon to be set for switching.
     */
    public void setSelectedProfile1(PokémonProfile mSelectedProfile1) {
        this.mSelectedProfile1 = mSelectedProfile1;
    }

    /**
     * Gets the second selected Pokémon for switching.
     * @return  The second selected Pokémon for switching.
     */
    public PokémonProfile getSelectedProfile2() {
        return mSelectedProfile2;
    }

    /**
     * Sets the second selected Pokémon for switching.
     * @param mSelectedProfile2 The selected Pokémon to be set for switching.
     */
    public void setSelectedProfile2(PokémonProfile mSelectedProfile2) {
        this.mSelectedProfile2 = mSelectedProfile2;
    }

    /**
     * Gets the Player data.
     * @return  The Player data.
     */
    public Player getPlayer() {
        return mPlayer;
    }

    /**
     * Sets the Player data.
     * @param mPlayer   The Player data to be set.
     */
    public void setPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

    /**
     * Returns the list of Pokémon from the box.
     * @return  The list of Pokémon from the box.
     */
    public PokémonList getPokemonAdapter() {
        return mPokemonAdapter;
    }

    /**
     * Sets the list of Pokémon from the box.
     * @param mPokemonAdapter   The list of Pokémon to be set.
     */
    public void setPokemonAdapter(PokémonList mPokemonAdapter) {
        this.mPokemonAdapter = mPokemonAdapter;
    }

    /**
     * Returns the list of Items from the Player's bag.
     * @return  The list of Items from the Player's bag.
     */
    public ItemList getItemAdapter() {
        return mItemAdapter;
    }

    /**
     * Sets the list of Items from the Player's bag.
     * @param mItemAdapter   The list of Items to be set.
     */
    public void setItemAdapter(ItemList mItemAdapter) {
        this.mItemAdapter = mItemAdapter;
    }

    /**
     * Returns the current state of the Manager.
     * @return  The current state of the Manager.
     */
    public ManagerState getState() {
        return mState;
    }

    /**
     * Sets the current state of the Manager.
     * @param mState    The state of the Manager to be set.
     */
    public void setState(ManagerState mState) {
        this.mState = mState;
    }
}
