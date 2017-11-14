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
        this.mBag = new Item[]{new Item("Potion", 10),
                new Item("Super Potion", 10),
                new Item("Max Revive", 10),
                new Item("Poke Ball", 10),
                new Item("Great Ball", 10),
                new Item("Ultra Ball", 10)};
    }

    public Player(String mName) {
        this.mName = mName;
        for(int index = 0; index < MAX_POKEMON_SLOTS; index++){
            this.mPokemons[index] = new PokemonProfile();
        }
        this.mBag = new Item[]{new Item("Potion", 10),
                new Item("Super Potion", 10),
                new Item("Max Revive", 10),
                new Item("Poke Ball", 10),
                new Item("Great Ball", 10),
                new Item("Ultra Ball", 10)};
    }

    public Player(String mName, Pokemon starter) {
        this.mName = mName;
        this.mPokemons[0] = new PokemonProfile(0, starter);
        for(int index = 1; index < MAX_POKEMON_SLOTS; index++){
            this.mPokemons[index] = new PokemonProfile();
        }
        this.mBag = new Item[]{new Item("Potion", 10),
                new Item("Super Potion", 10),
                new Item("Max Revive", 10),
                new Item("Poke Ball", 10),
                new Item("Great Ball", 10),
                new Item("Ultra Ball", 10)};
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

    public Item getPotions(){
        return mBag[0];
    }
    public Item getSuperPotions(){
        return mBag[1];
    }
    public Item getMaxRevives(){
        return mBag[2];
    }
    public Item getPokeBall(){
        return mBag[3];
    }
    public Item getGreatBall(){
        return mBag[4];
    }
    public Item getUltraBall(){
        return mBag[5];
    }
    public Item[] getBag(){
        return mBag;
    }

    public void setPotions(Item item){
        mBag[0] = item;
    }
    public void setSuperPotions(Item item){
        mBag[1] = item;
    }
    public void setMaxRevives(Item item){
        mBag[2] = item;
    }
    public void setPokeBall(Item item){
        mBag[3] = item;
    }
    public void setGreatBall(Item item){
        mBag[4] = item;
    }
    public void setUltraBall(Item item){
        mBag[5] = item;
    }

    public boolean usePotion(PokemonProfile profile){
        if(profile.getCurrentHP() > 0){
            profile.setCurrentHP(profile.getCurrentHP() + 20);
            if(profile.getCurrentHP() > profile.getHP()){
                profile.setCurrentHP(profile.getHP());
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean useSuperPotion(PokemonProfile profile){
        if(profile.getCurrentHP() > 0){
            profile.setCurrentHP(profile.getCurrentHP() + 50);
            if(profile.getCurrentHP() > profile.getHP()){
                profile.setCurrentHP(profile.getHP());
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean useMaxRevive(PokemonProfile profile){
        if(profile.getCurrentHP() == 0){
            profile.setCurrentHP(profile.getHP());
            return true;
        }
        else{
            return false;
        }
    }

    public int getFinalCatchRate(PokemonProfile profile, double ballBonus){
        double result = ((3.0*profile.getHP() - 2.0*profile.getCurrentHP())*ballBonus*
                profile.getDexData().getCatchRate())/(3.0*profile.getHP());
        return (int) result;
    }

    public int getSecondCatchRate(int finalCatchRate){
        double result = floor(65536.0/(pow(255.0/((double)finalCatchRate), 3.0/16.0)));
        return (int) result;
    }

    //TODO CATCH THAT THING
    public int useBall(PokemonProfile profile, double ballBonus){
        int finalCatchRate = getFinalCatchRate(profile, ballBonus);
        int attempt1 = PokemonGoApp.getIntegerRNG(65535);
        int attempt2 = PokemonGoApp.getIntegerRNG(65535);
        int attempt3 = PokemonGoApp.getIntegerRNG(65535);
        if(attempt1 >= getSecondCatchRate(finalCatchRate)){
            return 1;
        }
        else{
            if(attempt2 >= getSecondCatchRate(finalCatchRate)){
                return 2;
            }
            else{
                if(attempt3 >= getSecondCatchRate(finalCatchRate)){
                    return 3;
                }
                else{
                    return 4;
                }
            }
        }
    }

    public int usePokeball(PokemonProfile profile){
        return useBall(profile, 1.0);
    }
    public int useGreatBall(PokemonProfile profile){
        return useBall(profile, 1.5);
    }
    public int useUltraBall(PokemonProfile profile){
        return useBall(profile, 2.0);
    }

    public int getFreeSlot(){
        for(int index = 0; index < mPokemons.length; index++){
            if(mPokemons[index].getDexNumber() == Pokemon.MISSINGNO){
                return index;
            }
        }
        return MAX_POKEMON_SLOTS;
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
}
