package edu.ateneo.cie199.finalproject;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by John on 11/3/2017.
 */

public class PokemonGoApp extends Application{

    private GoogleMap mMap;
    private Player mPlayer = new Player();

    private Marker mSelectedMarker = null;
    private Marker mCurrentGoal = null;

    private int mSpawnCount = 0;
    private ArrayList<Marker> mMarkers = new ArrayList<>();
    private ArrayList<Pokemon> mPokemons = new ArrayList<>();

    //RANDOM NUMBER GENERATOR
    private int mIntegrSelect = 0;
    private double mDoubleSelect = 0.0;
    private Random s = new Random();

    /**
     * This function returns a random number from 0 to a given length.
     * @param length    the upper bound for the random number generation
     * @return          a random number from 0 to length
     */
    public int getIntegerRNG(int length){
        mIntegrSelect = (s.nextInt(length));
        return mIntegrSelect;
    }
    public double getDoubleRNG(){
        mDoubleSelect = (s.nextDouble());
        return mDoubleSelect;
    }

    public Player getPlayer(){
        return mPlayer;
    }

    public GoogleMap getMap(){
        return mMap;
    }
    public void setMap(GoogleMap map){
        this.mMap = map;
    }

    public Marker getSelectedMarker(){
        return mSelectedMarker;
    }
    public void setSelectedMarker(Marker marker){
        mSelectedMarker = marker;
    }

    public int getSpawnCount() {
        return mSpawnCount;
    }
    public void setSpawnCount(int mSpawnCount) {
        this.mSpawnCount = mSpawnCount;
    }

    public Marker getCurrentGoal() {
        return mCurrentGoal;
    }
    public void setCurrentGoal(Marker marker) {
        this.mCurrentGoal = marker;
    }


    public ArrayList<Marker> getMarkers(){
        return mMarkers;
    }
    public void addMarkers(Marker marker){
        mMarkers.add(marker);
    }
    public void deleteMarker(Marker marker){
        for(int index = 0; index < mMarkers.size(); index++){
            if(marker.getId() == mMarkers.get(index).getId()){
                mMarkers.remove(index);
            }
        }
    }

    public ArrayList<Pokemon> getAllPokemons(){
        return mPokemons;
    }
    public void addPokemon(Pokemon pokemon){
        mPokemons.add(pokemon);
    }
    public Pokemon getPokemon(String title){
        for(int index = 0; index < mPokemons.size(); index++){
            if(mPokemons.get(index).getName().equals(title)){
                return mPokemons.get(index);
            }
        }
        return mPokemons.get(0);
    }

    public void moveMapCamera(LatLng position, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
    }

    public int getDrawableId(String name){
        try {
            Field fld = R.drawable.class.getField(name);
            return fld.getInt(null);
        } catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

}
