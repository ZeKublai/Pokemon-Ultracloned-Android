package edu.ateneo.cie199.finalproject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by John on 11/3/2017.
 */

public class Player {
    public static int MAX_POKEMON_SLOTS = 6;
    public static int MAX_BAG_SLOTS = 6;

    private String mName = "";
    private Marker mMarker = null;
    private int[] mBag = new int[MAX_BAG_SLOTS];

    private PokemonProfile[] mPokemons = new PokemonProfile[MAX_POKEMON_SLOTS];
    private ArrayList<PokemonProfile> mBox = new ArrayList<>();

    public Player() {
        this.mName = "Red";
        for(int index = 0; index < MAX_POKEMON_SLOTS; index++){
            this.mPokemons[index] = new PokemonProfile();
        }
        this.mBag = new int[]{10, 10, 10, 10, 10, 10};
    }

    public Player(String mName) {
        this.mName = mName;
        for(int index = 0; index < MAX_POKEMON_SLOTS; index++){
            this.mPokemons[index] = new PokemonProfile();
        }
        this.mBag = new int[]{10, 10, 10, 10, 10, 10};
    }

    public Player(String mName, Pokemon starter) {
        this.mName = mName;
        this.mPokemons[0] = new PokemonProfile(0, starter);
        for(int index = 1; index < MAX_POKEMON_SLOTS; index++){
            this.mPokemons[index] = new PokemonProfile();
        }
        this.mBag = new int[]{10, 10, 10, 10, 10, 10};
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

    public int getPotions(){
        return mBag[0];
    }
    public int getSuperPotions(){
        return mBag[1];
    }
    public int getMaxRevives(){
        return mBag[2];
    }
    public int getPokeBall(){
        return mBag[3];
    }
    public int getGreatBall(){
        return mBag[4];
    }
    public int getUltraBall(){
        return mBag[5];
    }
    public void setPotions(int value){
        mBag[0] = value;
    }
    public void setSuperPotions(int value){
        mBag[1] = value;
    }
    public void setMaxRevives(int value){
        mBag[2] = value;
    }
    public void setPokeBall(int value){
        mBag[3] = value;
    }
    public void setGreatBall(int value){
        mBag[4] = value;
    }
    public void setUltraBall(int value){
        mBag[5] = value;
    }

    public PokemonProfile getBuddy(){
        for(int index = 0; index < mPokemons.length; index++){
            if(mPokemons[index].getCurrentHP() > 0){
                return mPokemons[index];
            }
        }
        return new PokemonProfile();
    }
}
