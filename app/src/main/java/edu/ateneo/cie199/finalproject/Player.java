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
    private ArrayList<Item> mBag = new ArrayList<>();

    private ArrayList<PokemonProfile> mPokemons = new ArrayList<>();
    private ArrayList<PokemonProfile> mBox = new ArrayList<>();

    public Player() {
        this.mName = "Red";
    }
    public Player(String mName) {
        this.mName = mName;
    }
    public Player(String mName, Pokemon starter) {
        this.mName = mName;
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

    public ArrayList<PokemonProfile> getPokemons() {
        return mPokemons;
    }
    public void setPokemons(ArrayList<PokemonProfile> mPokemons) {
        this.mPokemons = mPokemons;
    }

    public ArrayList<PokemonProfile> getBox() {
        return mBox;
    }
    public void setBox(ArrayList<PokemonProfile> mBox) {
        this.mBox = mBox;
    }

    public ArrayList<Item> getBag(){
        return mBag;
    }

    public int getFreeSlot(){
        return mPokemons.size();
    }
    public int getAverageLevel(){
        int totalLevel = 0;
        for(int index = 0; index < mPokemons.size(); index++){
            totalLevel = totalLevel + mPokemons.get(index).getLevel();
        }
        return totalLevel/mPokemons.size();
    }
    public PokemonProfile getBuddy(){
        for(int index = 0; index < mPokemons.size(); index++){
            if(mPokemons.get(index).getCurrentHP() > 0){
                return mPokemons.get(index);
            }
        }
        return new PokemonProfile();
    }

    public boolean increaseItem(Item item){
        for(int index = 0; index < mBag.size(); index++){
            if(mBag.get(index).getClass().equals(item.getClass())) {
                mBag.get(index).setQuantity(mBag.get(index).getQuantity() + item.getQuantity());
                return true;
            }
        }
        return false;
    }

    public void giveItem(Item item){
        if(!increaseItem(item)){
            mBag.add(item);
        }
    }
    public boolean isPokemonFainted(int pokemonIndex){
        if(mPokemons.get(pokemonIndex).getCurrentHP() <= 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isPlayerDefeated(){
        for(int index = 0; index < mPokemons.size(); index++){
            if(!isPokemonFainted(index)){
                return false;
            }
        }
        return true;
    }
}
