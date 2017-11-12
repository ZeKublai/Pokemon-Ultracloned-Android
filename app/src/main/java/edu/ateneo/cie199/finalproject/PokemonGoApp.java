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
    private ArrayList<Move> mMoves = new ArrayList<>();
    private ArrayList<Type> mTypes = new ArrayList<>();

    /**
     * This function returns a random number from 0 to a given length.
     * @param length    the upper bound for the random number generation
     * @return          a random number from 0 to length
     */
    public static int getIntegerRNG(int length){
        Random s = new Random();
        int mIntegrSelect = (s.nextInt(length));
        return mIntegrSelect;
    }
    public double getDoubleRNG(){
        Random s = new Random();
        double mDoubleSelect = (s.nextDouble());
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
    public Pokemon getPokemon(int dexNumber){
        for(int index = 0; index < mPokemons.size(); index++){
            if(mPokemons.get(index).getDexNumber() == dexNumber){
                return mPokemons.get(index);
            }
        }
        return mPokemons.get(0);
    }

    public ArrayList<Type> getAllTypes(){return mTypes;}
    public ArrayList<Move> getAllMoves(){return mMoves;}

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

    //TODO: MAKE IT LOAD FROM FILE INSTEAD OF HARD CODE
    public void loadAllPokemon(){
        addPokemon(new Pokemon(1, "Bulbasaur", "It can go for days without eating a single morsel. In the bulb on its back, it stores energy.", new StatSet(45, 49, 49, 65, 65, 45), Type.GRASS, R.drawable.bulbasaur_main, R.drawable.bulbasaur_map, R.drawable.bulbasaur_back, 190, 7, 1));
        addPokemon(new Pokemon(2, "Ivysaur", "The bulb on its back grows by drawing energy. It gives off an aroma when it is ready to bloom.", new StatSet(60, 62, 63, 80, 80, 60), Type.GRASS, R.drawable.ivysaur_main, R.drawable.ivysaur_map, R.drawable.ivysaur_back, 45, 7, 1));
        addPokemon(new Pokemon(3, "Venusaur", "The flower on its back catches the sun's rays. The sunlight is then absorbed and used for energy.", new StatSet(80, 82, 83, 100, 100, 80), Type.GRASS, R.drawable.venusaur_main, R.drawable.venusaur_map, R.drawable.venusaur_back, 45, 7, 1));
        addPokemon(new Pokemon(4, "Charmander", "The flame at the tip of its tail makes a sound as it burns. You can only hear it in quiet places.", new StatSet(39, 52, 43, 60, 50, 65), Type.FIRE, R.drawable.charmander_main, R.drawable.charmander_map, R.drawable.charmander_back, 190, 7, 1));
        addPokemon(new Pokemon(5, "Charmeleon", "It is very hotheaded by nature, so it constantly seeks opponents. It calms down only when it wins.", new StatSet(58, 64, 58, 80, 65, 80), Type.FIRE, R.drawable.charmeleon_main, R.drawable.charmeleon_map, R.drawable.charmeleon_back, 45, 7, 1));
        addPokemon(new Pokemon(6, "Charizard", "When expelling a blast of super hot fire, the red flame at the tip of its tail burns more intensely.", new StatSet(78, 84, 78, 109, 85, 100), Type.FIRE, R.drawable.charizard_main, R.drawable.charizard_map, R.drawable.charizard_back, 45, 7, 1));
        addPokemon(new Pokemon(7, "Squirtle", "Shoots water at prey while in the water. Withdraws into its shell when in danger.", new StatSet(44, 48, 65, 50, 64, 43), Type.WATER, R.drawable.squirtle_main, R.drawable.squirtle_map, R.drawable.squirtle_back, 190, 7, 1));
        addPokemon(new Pokemon(8, "Wartortle", "Often hides in water to stalk unwary prey. For swimming fast, it moves its ears to maintain balance.", new StatSet(59, 63, 80, 65, 80, 58), Type.WATER, R.drawable.wartortle_main, R.drawable.wartortle_map, R.drawable.wartortle_back, 45, 7, 1));
        addPokemon(new Pokemon(9, "Blastoise", "It deliberately makes itself heavy so it can withstand the recoil of the water jets it fires.", new StatSet(79, 103, 120, 135, 115, 78), Type.WATER, R.drawable.blastoise_main, R.drawable.blastoise_map, R.drawable.blastoise_back, 45, 7, 1));
        addPokemon(new Pokemon(25, "Pikachu", "It keeps its tail raised to monitor its surroundings. If you yank its tail, it will try to bite you.", new StatSet(35, 55, 40, 50, 50, 90), Type.ELECTRIC, R.drawable.pikachu_main, R.drawable.pikachu_map, R.drawable.pikachu_back, 190, 1, 1));
        addPokemon(new Pokemon(26, "Raichu", "When electricity builds up inside its body, it becomes feisty. It also glows in the dark.", new StatSet(60, 90, 55, 90, 80, 110), Type.ELECTRIC, R.drawable.raichu_main, R.drawable.raichu_map, R.drawable.raichu_back, 75, 1, 1));
        addPokemon(new Pokemon(58, "Growlithe", "It has a brave and trustworthy nature. It fearlessly stands up to bigger and stronger foes.", new StatSet(55, 70, 45, 70, 50, 60), Type.FIRE, R.drawable.growlithe_main, R.drawable.growlithe_map, R.drawable.growlithe_back, 190, 3, 1));
        addPokemon(new Pokemon(59, "Arcanine", "An ancient picture scroll shows that people were attracted to its movement as it ran through prairies.", new StatSet(90, 110, 80, 100, 80, 95), Type.FIRE, R.drawable.arcanine_main, R.drawable.arcanine_map, R.drawable.arcanine_back, 75, 3, 1));
        addPokemon(new Pokemon(72, "Tentacool", "It can sometimes be found all dry and shriveled up on a beach. Toss it back into the sea to revive it.", new StatSet(40, 40, 35, 50, 100, 70), Type.WATER, R.drawable.tentacool_main, R.drawable.tentacool_map, R.drawable.tentacool_back, 190, 1, 1));
        addPokemon(new Pokemon(73, "Tentacruel", "The tentacles are normally kept short. On hunts, they are extended to ensnare and immobilize prey", new StatSet(80, 70, 65, 80, 120, 100), Type.WATER, R.drawable.tentacruel_main, R.drawable.tentacruel_map, R.drawable.tentacruel_back, 60, 1, 1));
        addPokemon(new Pokemon(74, "Geodude", "Commonly found near mountain trails, etc. If you step on one by accident, it gets angry.", new StatSet(40, 80, 100, 30, 30, 20), Type.ROCK, R.drawable.geodude_main, R.drawable.geodude_map, R.drawable.geodude_back, 255, 1, 1));
        addPokemon(new Pokemon(75, "Graveler", "Often seen rolling down mountain trails. Obstacles are just things to roll straight over, not avoid.", new StatSet(55, 95, 115, 45, 45, 35), Type.ROCK, R.drawable.graveler_main, R.drawable.graveler_map, R.drawable.graveler_back, 120, 1, 1));
        addPokemon(new Pokemon(76, "Golem", "Its boulder-like body is extremely hard. It can easily withstand dynamite blasts without damage.", new StatSet(80, 120, 130, 55, 65, 45), Type.ROCK, R.drawable.golem_main, R.drawable.golem_map, R.drawable.golem_back, 45, 1, 1));
        addPokemon(new Pokemon(127, "Pinsir", "Grips its prey in its pincers and squeezes hard! It can't move if it's cold, so it lives in warm places", new StatSet(65, 125, 100, 55, 70, 85), Type.NORMAL, R.drawable.pinsir_main, R.drawable.pinsir_map, R.drawable.pinsir_back, 45, 1, 1));
        addPokemon(new Pokemon(128, "Tauros", "They fight each other by locking horns. The herd's protector takes pride in its battle-scarred horns.", new StatSet(75, 100, 95, 40, 70, 110), Type.NORMAL, R.drawable.tauros_main, R.drawable.tauros_map, R.drawable.tauros_back, 45, 1, 0));
    }

    public void loadAllPokemonMoves(){
        mMoves.add(new Move("Vine Whip", Type.GRASS, Move.PHYSICAL, 25, 25, 45, 100));
        mMoves.add(new Move("Energy Ball", Type.GRASS, Move.SPECIAL, 10, 10, 90, 100));
        mMoves.add(new Move("Razor Leaf", Type.GRASS, Move.PHYSICAL, 25, 25, 55, 95));
        mMoves.add(new Move("Giga Drain", Type.GRASS, Move.SPECIAL, 10, 10, 75, 100));
        mMoves.add(new Move("Frenzy Plant", Type.GRASS, Move.SPECIAL, 5, 5, 150, 90));

        mMoves.add(new Move("Ember", Type.FIRE, Move.SPECIAL, 25, 25, 40, 100));
        mMoves.add(new Move("Fire Fang", Type.FIRE, Move.PHYSICAL, 15, 15, 65, 95));
        mMoves.add(new Move("Fire Spin", Type.FIRE, Move.SPECIAL, 15, 15, 35, 85));
        mMoves.add(new Move("Flame Charge", Type.FIRE, Move.PHYSICAL, 20, 20, 50, 100));
        mMoves.add(new Move("Flamethrower", Type.FIRE, Move.SPECIAL, 15, 15, 90, 100));
        mMoves.add(new Move("Blast Burn", Type.FIRE, Move.SPECIAL, 5, 5, 150, 90));

        mMoves.add(new Move("Aqua Jet", Type.WATER, Move.PHYSICAL, 20, 20, 40, 100));
        mMoves.add(new Move("Water Gun", Type.WATER, Move.SPECIAL, 25, 25, 40, 100));
        mMoves.add(new Move("Aqua Tail", Type.WATER, Move.PHYSICAL, 10, 10, 90, 90));
        mMoves.add(new Move("Waterfall", Type.WATER, Move.PHYSICAL, 15, 15, 80, 100));
        mMoves.add(new Move("Muddy Water", Type.WATER, Move.SPECIAL, 10, 10, 90, 85));
        mMoves.add(new Move("Hydro Cannon", Type.WATER, Move.SPECIAL, 5, 5, 150, 90));

        mMoves.add(new Move("Thunder Punch", Type.ELECTRIC, Move.PHYSICAL, 15, 15, 75, 100));
        mMoves.add(new Move("Spark", Type.ELECTRIC, Move.PHYSICAL, 20, 20, 65, 100));
        mMoves.add(new Move("Charge Beam", Type.ELECTRIC, Move.SPECIAL, 10, 10, 50, 90));
        mMoves.add(new Move("Thunder Shock", Type.ELECTRIC, Move.SPECIAL, 30, 30, 40, 100));
        mMoves.add(new Move("Volt Tackle", Type.ELECTRIC, Move.SPECIAL, 5, 5, 150, 90));

        mMoves.add(new Move("Rock Throw", Type.ROCK, Move.PHYSICAL, 15, 15, 50, 90));
        mMoves.add(new Move("Rock Tomb", Type.ROCK, Move.PHYSICAL, 15, 15, 60, 95));
        mMoves.add(new Move("Stone Edge", Type.ROCK, Move.PHYSICAL, 5, 5, 100, 90));
        mMoves.add(new Move("Rollout", Type.ROCK, Move.PHYSICAL, 20, 20, 30, 90));
        mMoves.add(new Move("Rock Slide", Type.ROCK, Move.PHYSICAL, 10, 10, 75, 90));

        mMoves.add(new Move("Tackle", Type.NORMAL, Move.PHYSICAL, 35, 35, 40, 100));
        mMoves.add(new Move("Take Down", Type.NORMAL, Move.PHYSICAL, 20, 20, 90, 85));
        mMoves.add(new Move("Thrash", Type.NORMAL, Move.PHYSICAL, 10, 10, 120, 100));
        mMoves.add(new Move("Hidden Power", Type.NORMAL, Move.SPECIAL, 15, 15, 60, 100));
        mMoves.add(new Move("Façade", Type.NORMAL, Move.PHYSICAL, 20, 20, 70, 100));

    }

    public void loadAllPokemonTypes(){
        double[] normal = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.5f, 0, 1, 1, 0.5f, 1};
        double[] fire = new double[]{1, 0.5f, 0.5f, 1, 2, 2, 1, 1, 1, 1, 1, 2, 0.5f, 1, 0.5f, 1, 2,
                1};
        double[] water = new double[]{1, 2, 0.5f, 1, 0.5f, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0.5f, 1, 1,
                1};
        double[] electric = new double[]{1, 1, 2, 0.5f, 0.5f, 1, 1, 1, 0, 2, 1, 1, 1, 1, 0.5f, 1, 1,
                1};
        double[] grass = new double[]{1, 0.5f, 2, 1, 0.5f, 1, 1, 0.5f, 2, 0.5f, 1, 0.5f, 2, 1, 0.5f,
                1, 0.5f, 1};
        double[] ice = new double[]{1, 0.5f, 0.5f, 1, 2, 0.5f, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1, 0.5f,
                1};
        double[] fighting = new double[]{2, 1, 1, 1, 1, 2, 1, 0.5f, 1, 0.5f, 0.5f, 0.5f, 2, 0, 1, 2,
                2, 0.5f};
        double[] poison = new double[]{1, 1, 1, 1, 2, 1, 1, 0.5f, 0.5f, 1, 1, 1, 0.5f, 0.5f, 1, 1,
                0, 2};
        double[] ground = new double[]{1, 2, 1, 2, 0.5f, 1, 1, 2, 1, 0, 1, 0.5f, 2, 1, 1, 1, 2, 1};
        double[] flying = new double[]{1, 1, 1, 0.5f, 2, 1, 2, 1, 1, 1, 1, 2, 0.5f, 1, 1, 1, 0.5f,
                1};
        double[] psychic = new double[]{1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 0.5f, 1, 1, 1, 1, 0, 0.5f, 1};
        double[] bug = new double[]{1, 0.5f, 1, 1, 2, 1, 0.5f, 0.5f, 1, 0.5f, 2, 1, 1, 0.5f, 1, 2,
                0.5f, 0.5f};
        double[] rock = new double[]{1, 2, 1, 1, 1, 2, 0.5f, 1, 0.5f, 2, 1, 2, 1, 1, 1, 1, 0.5f, 1};
        double[] ghost = new double[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0.5f, 1, 1};
        double[] dragon = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0.5f, 0};
        double[] dark = new double[]{1, 1, 1, 1, 1, 1, 0.5f, 1, 1, 1, 2, 1, 1, 2, 1, 0.5f, 1, 0.5f};
        double[] steel = new double[]{1, 0.5f, 0.5f, 0.5f, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 0.5f,
                2};
        double[] fairy = new double[]{1, 0.5f, 1, 1, 1, 1, 2, 0.5f, 1, 1, 1, 1, 1, 1, 2, 2, 0.5f,
                1};

        mTypes.add(new Type(normal));
        mTypes.add(new Type(fire));
        mTypes.add(new Type(water));
        mTypes.add(new Type(electric));
        mTypes.add(new Type(grass));
        mTypes.add(new Type(ice));
        mTypes.add(new Type(fighting));
        mTypes.add(new Type(poison));
        mTypes.add(new Type(ground));
        mTypes.add(new Type(flying));
        mTypes.add(new Type(psychic));
        mTypes.add(new Type(bug));
        mTypes.add(new Type(rock));
        mTypes.add(new Type(ghost));
        mTypes.add(new Type(dragon));
        mTypes.add(new Type(dark));
        mTypes.add(new Type(steel));
        mTypes.add(new Type(fairy));
    }


}
