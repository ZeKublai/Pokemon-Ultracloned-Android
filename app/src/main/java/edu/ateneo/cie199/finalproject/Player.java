package edu.ateneo.cie199.finalproject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import static java.lang.Math.floor;
import static java.lang.Math.pow;

/**
 * Created by John on 11/3/2017.
 */

public class Player {
    public static int MAX_POKEMON_SLOTS = 6;
    public static int MAX_BAG_SLOTS = 6;

    private String mName = "";
    private Marker mMarker = null;
    private Item[] mBag = new Item[MAX_BAG_SLOTS];

    private PokemonProfile[] mPokemons = new PokemonProfile[MAX_POKEMON_SLOTS];
    private ArrayList<PokemonProfile> mBox = new ArrayList<>();

    public Player() {
        this.mName = "Red";
        for(int index = 0; index < MAX_POKEMON_SLOTS; index++){
            this.mPokemons[index] = new PokemonProfile();
        }
    }
    public Player(String mName) {
        this.mName = mName;
        for(int index = 0; index < MAX_POKEMON_SLOTS; index++){
            this.mPokemons[index] = new PokemonProfile();
        }
    }
    public Player(String mName, Pokemon starter) {
        this.mName = mName;
        this.mPokemons[0] = new PokemonProfile(0, starter, 5);
        for(int index = 1; index < MAX_POKEMON_SLOTS; index++){
            this.mPokemons[index] = new PokemonProfile();
        }
    }

    public String getName() {
        return mName;
    }
    public void setName(String mName) {
        this.mName = mName;
    }

    public Marker getMarker() {
        return mMarker;
    }
    public void setMarker(Marker mMarker) {
        this.mMarker = mMarker;
    }

    public PokemonProfile[] getPokemons() {
        return mPokemons;
    }
    public void setPokemons(PokemonProfile[] mPokemons) {
        this.mPokemons = mPokemons;
    }

    public ArrayList<PokemonProfile> getBox() {
        return mBox;
    }
    public void setBox(ArrayList<PokemonProfile> mBox) {
        this.mBox = mBox;
    }

    public Item[] getBag(){
        return mBag;
    }

    public int getFreeSlot(){
        for(int index = 0; index < mPokemons.length; index++){
            if(mPokemons[index].getDexNumber() == Pokemon.MISSINGNO){
                return index;
            }
        }
        return MAX_POKEMON_SLOTS;
    }
    public int getAverageLevel(){
        int totalLevel = 0;
        for(int index = 0; index < MAX_POKEMON_SLOTS; index++){
            totalLevel = totalLevel + mPokemons[index].getLevel();
        }
        return totalLevel/getFreeSlot();
    }
    public PokemonProfile getBuddy(){
        for(int index = 0; index < mPokemons.length; index++){
            if(mPokemons[index].getCurrentHP() > 0){
                return mPokemons[index];
            }
        }
        return new PokemonProfile();
    }

    public void giveItem(Item item){
        for(int index = 0; index < MAX_BAG_SLOTS; index++){
            if(mBag[index].getName().equals(item.getName())){
                mBag[index].setQuantity(mBag[index].getQuantity() + item.getQuantity());
            }
        }
    }
    public boolean isPokemonFainted(int pokemonIndex){
        if(mPokemons[pokemonIndex].getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isPlayerDefeated(){
        for(int index = 0; index < mPokemons.length; index++){
            if(!isPokemonFainted(index)){
                return false;
            }
        }
        return true;
    }
}
