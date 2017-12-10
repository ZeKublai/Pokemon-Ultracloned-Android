package edu.ateneo.cie199.finalproject;

import com.google.android.gms.maps.model.Marker;
import java.util.ArrayList;

/**
 * Created by John on 11/3/2017.
 * This class contains all the functions and data members of the Player class. The
 * Player class is the link for the user to use in order to interact with the game.
 */

public class Player {
    public static int MAX_POKéMON_SLOTS = 6;

    protected String mName = "";
    private Marker mMarker = null;

    private Gender mGender = new Gender();
    protected ArrayList<Item> mBag = new ArrayList<>();
    protected ArrayList<PokémonProfile> mPokémons = new ArrayList<>();
    protected ArrayList<PokémonProfile> mBox = new ArrayList<>();

    /**
     * "The function is empty..."
     */
    public Player() {

    }

    /**
     * Creates a new Player object given a gender, name and starter. Used when starting a new game.
     * @param mGender   The gender of the created Player.
     * @param mName     The name of the created Player.
     * @param starter   The starter Pokémon of the created Player.
     */
    public Player(Gender mGender, String mName, PokémonProfile starter) {
        this.mGender = mGender;
        this.mName = mName;
        mPokémons.add(new PokémonProfile(starter));

        mBag.add(new ItemPotion(5));
        mBag.add(new ItemRevive(5));
        mBag.add(new ItemElixir(5));
        mBag.add(new ItemPokeBall(5));
        mBag.add(new ItemGreatBall(5));
        mBag.add(new ItemUltraBall(5));
    }

    /**
     * Returns the gender of the Player.
     * @return  The gender of the Player.
     */
    public Gender getGender() {
        return mGender;
    }

    /**
     * Sets the gender of the Player.
     * @param mGender   The Gender object to be set.
     */
    public void setGender(Gender mGender) {
        this.mGender = mGender;
    }

    /**
     * Returns the name of the Player.
     * @return  The name of the Player.
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets the name of the Player.
     * @param mName The name to be set.
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Returns the marker of the Player.
     * @return  The marker of the Player.
     */
    public Marker getMarker() {
        return mMarker;
    }

    /**
     * Sets the marker of the Player.
     * @param mMarker   The Marker object to be set.
     */
    public void setMarker(Marker mMarker) {
        this.mMarker = mMarker;
    }

    /**
     * Returns the Pokémons currently at the Player's party.
     * @return  An ArrayList of Pokémons currently at the Player's party.
     */
    public ArrayList<PokémonProfile> getPokemons() {
        return mPokémons;
    }

    /**
     * Returns the Pokémons stored in the Player's box.
     * @return  An ArrayList of Pokémons stored in the Player's box.
     */
    public ArrayList<PokémonProfile> getBox() {
        return mBox;
    }

    /**
     * Returns the items that the Player has.
     * @return  An ArrayList of items that the Player has.
     */
    public ArrayList<Item> getBag(){
        return mBag;
    }

    /**
     * Returns the index of the free slot in the Player's party.
     * @return
     */
    public int getFreeSlot(){
        return mPokémons.size();
    }

    /**
     * Returns the average level of all of the Pokémons in the Player's party.
     * @return  The average level of all of the Pokémons in the Player's party.
     */
    public int getAverageLevel(){
        int totalLevel = 0;
        for(int index = 0; index < mPokémons.size(); index++){
            totalLevel = totalLevel + mPokémons.get(index).getLevel();
        }
        return totalLevel/ mPokémons.size();
    }

    /**
     * Returns the first available Pokémon for battle.
     * @return  The first available Pokémon for battle.
     */
    public PokémonProfile getBuddy(){
        for(int index = 0; index < mPokémons.size(); index++){
            if(mPokémons.get(index).getCurrentHP() > 0){
                return mPokémons.get(index);
            }
        }
        return new PokémonProfile();
    }

    /**
     * If item exists in the bag, increment the item's quantity by
     * the given item's amount and return true else return false.
     * @param item  The item to be incremented.
     * @return      True if item has been incremented and false if item does not exist in the bag.
     */
    public boolean increaseItem(Item item){
        for(int index = 0; index < mBag.size(); index++){
            if(mBag.get(index).getClass().equals(item.getClass())) {
                mBag.get(index).setQuantity(mBag.get(index).getQuantity() + item.getQuantity());
                return true;
            }
        }
        return false;
    }

    /**
     * Increments the given item's quantity by the given item's amount
     * if it exists in the bag else adds the item to the bag ArrayList.
     * @param item  The item to be given to the Player.
     */
    public void giveItem(Item item){
        if(!increaseItem(item)){
            mBag.add(item);
        }
    }

    /**
     * Given the party index, checks the Pokémon at that index
     * and returns true if it has 0 HP and false otherwise.
     * @param pokemonIndex  The index location of the Player's party.
     * @return              True if Pokémon at index has fainted else false.
     */
    public boolean isPokemonFainted(int pokemonIndex){
        return (mPokémons.get(pokemonIndex).getCurrentHP() <= 0);
    }

    /**
     * Transfers a PokémonProfile from either the bag or the box to either the box or the box.
     * @param profile       The PokémonProfile that will be transferred.
     * @param origin        The origin of the PokémonProfile.
     * @param destination   The destination of the PokémonProfile.
     */
    public void transferPokemon(PokémonProfile profile,
                                ArrayList<PokémonProfile> origin,
                                ArrayList<PokémonProfile> destination){
        destination.add(profile);
        origin.remove(profile);
    }

    /**
     * Returns true if all of the Player's Pokémon are fainted else false.
     * @return  True if all of the Player's Pokémon are fainted else false.
     */
    public boolean isDefeated(){
        for(int index = 0; index < mPokémons.size(); index++){
            if(!isPokemonFainted(index)){
                return false;
            }
        }
        return true;
    }
}
