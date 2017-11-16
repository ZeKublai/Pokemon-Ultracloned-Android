package edu.ateneo.cie199.finalproject;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Field;
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


    private MusicHandler musicHandler = new MusicHandler();
    private boolean mMusicSwitch = true;
    private boolean mSFXSwitch = true;


    private int mSpawnCount = 0;
    private ArrayList<Marker> mMarkers = new ArrayList<>();
    private ArrayList<Pokemon> mPokemons = new ArrayList<>();
    private ArrayList<Move> mMoves = new ArrayList<>();
    private ArrayList<Type> mTypes = new ArrayList<>();

    private ArrayList<Item> mItems = new ArrayList<>();

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

    public MusicHandler getMusicHandler() {
        return musicHandler;
    }

    public boolean getMusicSwitch() { return mMusicSwitch;}
    public boolean getSFXSwitch() {return mSFXSwitch;}

    public void MusicOn() {mMusicSwitch = true;}
    public void MusicOff() {mMusicSwitch = false;}
    public void SFXOn() {mSFXSwitch = true;}
    public void SFXOff() {mSFXSwitch = false;}

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
        return new Pokemon();
    }
    public Pokemon getPokemon(int dexNumber){
        for(int index = 0; index < mPokemons.size(); index++){
            if(mPokemons.get(index).getDexNumber() == dexNumber){
                return mPokemons.get(index);
            }
        }
        return new Pokemon();
    }

    public Item getItem(String title){
        for(int index = 0; index < mItems.size(); index++){
            if(mItems.get(index).getName().equals(title)){
                return mItems.get(index);
            }
        }
        return new Item();
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
        addPokemon(new Pokemon(1, "Bulbasaur", mTypes.get(Type.GRASS), mTypes.get(Type.POISON), "It can go for days without eating a single morsel. In the bulb on its back, it stores energy.", 190, 1, 7, 45, 49, 49, 65, 65, 45, 0, 2, R.drawable.bulbasaur_main, R.drawable.bulbasaur_back, R.drawable.bulbasaur_map, R.raw.bulbasaur));
        addPokemon(new Pokemon(2, "Ivysaur", mTypes.get(Type.GRASS), mTypes.get(Type.POISON), "The bulb on its back grows by drawing energy. It gives off an aroma when it is ready to bloom.", 45, 1, 7, 60, 62, 63, 80, 80, 60, 16, 3, R.drawable.ivysaur_main, R.drawable.ivysaur_back, R.drawable.ivysaur_map, R.raw.ivysaur));
        addPokemon(new Pokemon(3, "Venusaur", mTypes.get(Type.GRASS), mTypes.get(Type.POISON), "The flower on its back catches the sun's rays. The sunlight is then absorbed and used for energy.", 45, 1, 7, 80, 82, 83, 100, 100, 80, 32, 0, R.drawable.venusaur_main, R.drawable.venusaur_back, R.drawable.venusaur_map, R.raw.venusaur));
        addPokemon(new Pokemon(4, "Charmander", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "The flame at the tip of its tail makes a sound as it burns. You can only hear it in quiet places.", 190, 1, 7, 39, 52, 43, 60, 50, 65, 0, 5, R.drawable.charmander_main, R.drawable.charmander_back, R.drawable.charmander_map, R.raw.charmander));
        addPokemon(new Pokemon(5, "Charmeleon", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "It is very hotheaded by nature, so it constantly seeks opponents. It calms down only when it wins.", 45, 1, 7, 58, 64, 58, 80, 65, 80, 16, 6, R.drawable.charmeleon_main, R.drawable.charmeleon_back, R.drawable.charmeleon_map, R.raw.charmeleon));
        addPokemon(new Pokemon(6, "Charizard", mTypes.get(Type.FIRE), mTypes.get(Type.FLYING), "When expelling a blast of super hot fire, the red flame at the tip of its tail burns more intensely.", 45, 1, 7, 78, 84, 78, 109, 85, 100, 36, 0, R.drawable.charizard_main, R.drawable.charizard_back, R.drawable.charizard_map, R.raw.charizard));
        addPokemon(new Pokemon(7, "Squirtle", mTypes.get(Type.WATER), mTypes.get(Type.NONE), "Shoots water at prey while in the water. Withdraws into its shell when in danger.", 190, 1, 7, 44, 48, 65, 50, 64, 43, 0, 8, R.drawable.squirtle_main, R.drawable.squirtle_back, R.drawable.squirtle_map, R.raw.squirtle));
        addPokemon(new Pokemon(8, "Wartortle", mTypes.get(Type.WATER), mTypes.get(Type.NONE), "Often hides in water to stalk unwary prey. For swimming fast, it moves its ears to maintain balance.", 45, 1, 7, 59, 63, 80, 65, 80, 58, 16, 9, R.drawable.wartortle_main, R.drawable.wartortle_back, R.drawable.wartortle_map, R.raw.wartortle));
        addPokemon(new Pokemon(9, "Blastoise", mTypes.get(Type.WATER), mTypes.get(Type.NONE), "It deliberately makes itself heavy so it can withstand the recoil of the water jets it fires.", 45, 1, 7, 79, 103, 120, 135, 115, 78, 36, 0, R.drawable.blastoise_main, R.drawable.blastoise_back, R.drawable.blastoise_map, R.raw.blastoise));
        addPokemon(new Pokemon(16, "Pidgey", mTypes.get(Type.NORMAL), mTypes.get(Type.FLYING), "Very docile. If attacked, it will often kick up sand to protect itself rather than fight back.", 255, 1, 1, 40, 45, 40, 35, 35, 56, 0, 17, R.drawable.pidgey_main, R.drawable.pidgey_back, R.drawable.pidgey_map, R.raw.pidgey));
        addPokemon(new Pokemon(17, "Pidgeotto", mTypes.get(Type.NORMAL), mTypes.get(Type.FLYING), "It has outstanding vision. However high it flies, it is able to distinguish the movements of its prey.", 120, 1, 1, 63, 60, 55, 50, 50, 71, 18, 18, R.drawable.pidgeotto_main, R.drawable.pidgeotto_back, R.drawable.pidgeotto_map, R.raw.pidgeotto));
        addPokemon(new Pokemon(18, "Pidgeot", mTypes.get(Type.NORMAL), mTypes.get(Type.FLYING), "Its well-developed chest muscles make it strong enough to whip up a gusty windstorm with just a few flaps.", 45, 1, 1, 83, 80, 75, 70, 70, 101, 36, 0, R.drawable.pidgeot_main, R.drawable.pidgeot_back, R.drawable.pidgeot_map, R.raw.pidgeot));
        addPokemon(new Pokemon(25, "Pikachu", mTypes.get(Type.ELECTRIC), mTypes.get(Type.NONE), "It keeps its tail raised to monitor its surroundings. If you yank its tail, it will try to bite you.", 190, 1, 1, 35, 55, 40, 50, 50, 90, 0, 26, R.drawable.pikachu_main, R.drawable.pikachu_back, R.drawable.pikachu_map, R.raw.pikachu));
        addPokemon(new Pokemon(26, "Raichu", mTypes.get(Type.ELECTRIC), mTypes.get(Type.NONE), "When electricity builds up inside its body, it becomes feisty. It also glows in the dark.", 75, 1, 1, 60, 90, 55, 90, 80, 110, 22, 0, R.drawable.raichu_main, R.drawable.raichu_back, R.drawable.raichu_map, R.raw.raichu));
        addPokemon(new Pokemon(58, "Growlithe", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "It has a brave and trustworthy nature. It fearlessly stands up to bigger and stronger foes.", 190, 1, 3, 55, 70, 45, 70, 50, 60, 0, 59, R.drawable.growlithe_main, R.drawable.growlithe_back, R.drawable.growlithe_map, R.raw.growlithe));
        addPokemon(new Pokemon(59, "Arcanine", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "An ancient picture scroll shows that people were attracted to its movement as it ran through prairies.", 75, 1, 3, 90, 110, 80, 100, 80, 95, 22, 0, R.drawable.arcanine_main, R.drawable.arcanine_back, R.drawable.arcanine_map, R.raw.arcanine));
        addPokemon(new Pokemon(63, "Abra", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "Sleeps 18 hours a day. If it senses danger, it will teleport itself to safety even as it sleeps.", 200, 1, 3, 25, 20, 15, 105, 55, 90, 0, 64, R.drawable.abra_main, R.drawable.abra_back, R.drawable.abra_map, R.raw.abra));
        addPokemon(new Pokemon(64, "Kadabra", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "It possesses strong spiritual power. The more danger it faces, the stronger its psychic power.", 100, 1, 3, 40, 35, 30, 120, 70, 105, 16, 65, R.drawable.kadabra_main, R.drawable.kadabra_back, R.drawable.kadabra_map, R.raw.kadabra));
        addPokemon(new Pokemon(65, "Alakazam", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "Closing both its eyes heightens all its other senses. This enables it to use its abilities to their extremes.", 50, 1, 3, 55, 50, 45, 135, 95, 120, 40, 0, R.drawable.alakazam_main, R.drawable.alakazam_back, R.drawable.alakazam_map, R.raw.alakazam));
        addPokemon(new Pokemon(66, "Machop", mTypes.get(Type.FIGHTING), mTypes.get(Type.NONE), "Very powerful in spite of its small size. Its mastery of many types of martial arts makes it very tough.", 180, 1, 3, 70, 80, 50, 35, 35, 35, 0, 67, R.drawable.machop_main, R.drawable.machop_back, R.drawable.machop_map, R.raw.machop));
        addPokemon(new Pokemon(67, "Machoke", mTypes.get(Type.FIGHTING), mTypes.get(Type.NONE), "Its muscular body is so powerful, it must wear a power save belt to be able to regulate its motions.", 90, 1, 3, 80, 100, 70, 50, 60, 45, 28, 68, R.drawable.machoke_main, R.drawable.machoke_back, R.drawable.machoke_map, R.raw.machoke));
        addPokemon(new Pokemon(68, "Machamp", mTypes.get(Type.FIGHTING), mTypes.get(Type.NONE), "Using its heavy muscles, it throws powerful punches that can send the victim clear over the horizon.", 45, 1, 3, 90, 130, 80, 65, 85, 55, 40, 0, R.drawable.machamp_main, R.drawable.machamp_back, R.drawable.machamp_map, R.raw.machamp));
        addPokemon(new Pokemon(72, "Tentacool", mTypes.get(Type.WATER), mTypes.get(Type.POISON), "It can sometimes be found all dry and shriveled up on a beach. Toss it back into the sea to revive it.", 190, 1, 1, 40, 40, 35, 50, 100, 70, 0, 73, R.drawable.tentacool_main, R.drawable.tentacool_back, R.drawable.tentacool_map, R.raw.tentacool));
        addPokemon(new Pokemon(73, "Tentacruel", mTypes.get(Type.WATER), mTypes.get(Type.POISON), "The tentacles are normally kept short. On hunts, they are extended to ensnare and immobilize prey", 60, 1, 1, 80, 70, 65, 80, 120, 100, 30, 0, R.drawable.tentacruel_main, R.drawable.tentacruel_back, R.drawable.tentacruel_map, R.raw.tentacruel));
        addPokemon(new Pokemon(74, "Geodude", mTypes.get(Type.ROCK), mTypes.get(Type.GROUND), "Commonly found near mountain trails, etc. If you step on one by accident, it gets angry.", 255, 1, 1, 40, 80, 100, 30, 30, 20, 0, 75, R.drawable.geodude_main, R.drawable.geodude_back, R.drawable.geodude_map, R.raw.geodude));
        addPokemon(new Pokemon(75, "Graveler", mTypes.get(Type.ROCK), mTypes.get(Type.GROUND), "Often seen rolling down mountain trails. Obstacles are just things to roll straight over, not avoid.", 120, 1, 1, 55, 95, 115, 45, 45, 35, 25, 76, R.drawable.graveler_main, R.drawable.graveler_back, R.drawable.graveler_map, R.raw.graveler));
        addPokemon(new Pokemon(76, "Golem", mTypes.get(Type.ROCK), mTypes.get(Type.GROUND), "Its boulder-like body is extremely hard. It can easily withstand dynamite blasts without damage.", 45, 1, 1, 80, 120, 130, 55, 65, 45, 40, 0, R.drawable.golem_main, R.drawable.golem_back, R.drawable.golem_map, R.raw.golem));
        addPokemon(new Pokemon(81, "Magnemite", mTypes.get(Type.ELECTRIC), mTypes.get(Type.STEEL), "It is born with the ability to defy gravity. It floats in air on powerful electromagnetic waves.", 190, 0, 0, 25, 35, 70, 95, 55, 45, 0, 82, R.drawable.magnemite_main, R.drawable.magnemite_back, R.drawable.magnemite_map, R.raw.magnemite));
        addPokemon(new Pokemon(82, "Magneton", mTypes.get(Type.ELECTRIC), mTypes.get(Type.STEEL), "Generates strange radio signals. It raises the temperature by 3.6F degrees within 3,300 feet.", 60, 0, 0, 50, 60, 95, 120, 70, 70, 30, 0, R.drawable.magneton_main, R.drawable.magneton_back, R.drawable.magneton_map, R.raw.magneton));
        addPokemon(new Pokemon(92, "Gastly", mTypes.get(Type.GHOST), mTypes.get(Type.POISON), "Said to appear in decrepit, deserted buildings. It has no real shape as it appears to be made of a gas.", 190, 1, 1, 30, 35, 30, 100, 35, 80, 0, 93, R.drawable.gastly_main, R.drawable.gastly_back, R.drawable.gastly_map, R.raw.gastly));
        addPokemon(new Pokemon(93, "Haunter", mTypes.get(Type.GHOST), mTypes.get(Type.POISON), "By licking, it saps the victim's life. It causes shaking that won't stop until the victim's demise.", 90, 1, 1, 45, 50, 45, 115, 55, 95, 25, 94, R.drawable.haunter_main, R.drawable.haunter_back, R.drawable.haunter_map, R.raw.haunter));
        addPokemon(new Pokemon(94, "Gengar", mTypes.get(Type.GHOST), mTypes.get(Type.POISON), "To steal the life of its target, it slips into the prey's shadow and silently waits for an opportunity.", 45, 1, 1, 60, 65, 60, 130, 75, 110, 40, 0, R.drawable.gengar_main, R.drawable.gengar_back, R.drawable.gengar_map, R.raw.gengar));
        addPokemon(new Pokemon(109, "Koffing", mTypes.get(Type.POISON), mTypes.get(Type.NONE), "In hot places, its internal gases could expand and explode without any warning. Be very careful!", 190, 1, 1, 40, 65, 95, 60, 45, 35, 0, 110, R.drawable.koffing_main, R.drawable.koffing_back, R.drawable.koffing_map, R.raw.koffing));
        addPokemon(new Pokemon(110, "Weezing", mTypes.get(Type.POISON), mTypes.get(Type.NONE), "It lives and grows by absorbing dust, germs and poison gases that are contained in toxic waste and garbage.", 60, 1, 1, 65, 90, 120, 85, 70, 60, 35, 0, R.drawable.weezing_main, R.drawable.weezing_back, R.drawable.weezing_map, R.raw.weezing));
        addPokemon(new Pokemon(111, "Rhyhorn", mTypes.get(Type.GROUND), mTypes.get(Type.ROCK), "It is inept at turning because of its four short legs. It can only charge and run in one direction.", 120, 1, 1, 80, 85, 95, 30, 20, 25, 0, 112, R.drawable.rhyhorn_main, R.drawable.rhyhorn_back, R.drawable.rhyhorn_map, R.raw.rhyhorn));
        addPokemon(new Pokemon(112, "Rhydon", mTypes.get(Type.GROUND), mTypes.get(Type.ROCK), "Walks on its hind legs. Shows signs of intelligence. Its armor-like hide even repels molten lava.", 60, 1, 1, 105, 130, 120, 45, 45, 40, 42, 0, R.drawable.rhydon_main, R.drawable.rhydon_back, R.drawable.rhydon_map, R.raw.rhydon));
        addPokemon(new Pokemon(122, "Mr. Mime", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "Always practices its pantomime act. It makes enemies believe something exists that really doesn't.", 45, 1, 1, 40, 45, 65, 100, 120, 90, 0, 0, R.drawable.mr_mime_main, R.drawable.mr_mime_back, R.drawable.mr_mime_map, R.raw.mr_mime));
        addPokemon(new Pokemon(124, "Jynx", mTypes.get(Type.ICE), mTypes.get(Type.PSYCHIC), "Appears to move to a rhythm of its own, as if it were dancing. It wiggles its hips as it walks.", 45, 1, 0, 65, 50, 35, 115, 95, 95, 0, 0, R.drawable.jynx_main, R.drawable.jynx_back, R.drawable.jynx_map, R.raw.jynx));
        addPokemon(new Pokemon(125, "Electabuzz", mTypes.get(Type.ELECTRIC), mTypes.get(Type.NONE), "Electricity runs across the surface of its body. In darkness, its entire body glows a whitish-blue.", 45, 1, 3, 65, 83, 57, 95, 85, 105, 0, 0, R.drawable.electabuzz_main, R.drawable.electabuzz_back, R.drawable.electabuzz_map, R.raw.electabuzz));
        addPokemon(new Pokemon(126, "Magmar", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "Born in an active volcano. Its body is always cloaked in flames, so it looks like a big ball of fire.", 45, 1, 3, 65, 95, 57, 100, 85, 93, 0, 0, R.drawable.magmar_main, R.drawable.magmar_back, R.drawable.magmar_map, R.raw.magmar));
        addPokemon(new Pokemon(127, "Pinsir", mTypes.get(Type.BUG), mTypes.get(Type.NONE), "Grips its prey in its pincers and squeezes hard! It can't move if it's cold, so it lives in warm places", 45, 1, 1, 65, 125, 100, 55, 70, 85, 0, 0, R.drawable.pinsir_main, R.drawable.pinsir_back, R.drawable.pinsir_map, R.raw.pinsir));
        addPokemon(new Pokemon(128, "Tauros", mTypes.get(Type.NORMAL), mTypes.get(Type.NONE), "They fight each other by locking horns. The herd's protector takes pride in its battle-scarred horns.", 45, 0, 1, 75, 100, 95, 40, 70, 110, 0, 0, R.drawable.tauros_main, R.drawable.tauros_back, R.drawable.tauros_map, R.raw.tauros));
        addPokemon(new Pokemon(131, "Lapras", mTypes.get(Type.WATER), mTypes.get(Type.ICE), "They have gentle hearts. Because they rarely fight, many have been caught. Their number has dwindled.", 45, 1, 1, 130, 85, 80, 85, 95, 60, 0, 0, R.drawable.lapras_main, R.drawable.lapras_back, R.drawable.lapras_map, R.raw.lapras));
        addPokemon(new Pokemon(138, "Omanyte", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "Although long extinct, in rare cases, it can be genetically resurrected from fossils.", 45, 1, 7, 35, 40, 100, 90, 55, 35, 0, 139, R.drawable.omanyte_main, R.drawable.omanyte_back, R.drawable.omanyte_map, R.raw.omanyte));
        addPokemon(new Pokemon(139, "Omastar", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "Sharp beaks ring its mouth. Its shell was too big for it to move freely, so it became extinct.", 45, 1, 7, 70, 60, 125, 115, 70, 55, 40, 0, R.drawable.omastar_main, R.drawable.omastar_back, R.drawable.omastar_map, R.raw.omastar));
        addPokemon(new Pokemon(140, "Kabuto", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "On rare occasions, some have been found as fossils which they became while hiding on the ocean floor.", 45, 1, 7, 30, 80, 90, 55, 45, 55, 0, 141, R.drawable.kabuto_main, R.drawable.kabuto_back, R.drawable.kabuto_map, R.raw.kabuto));
        addPokemon(new Pokemon(141, "Kabutops", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "In the water, it tucks in its limbs to become more compact, then it wiggles its shell to swim fast.", 45, 1, 7, 60, 115, 105, 65, 70, 80, 40, 0, R.drawable.kabutops_main, R.drawable.kabutops_back, R.drawable.kabutops_map, R.raw.kabutops));
        addPokemon(new Pokemon(147, "Dratini", mTypes.get(Type.DRAGON), mTypes.get(Type.NONE), "It is born large to start with. It repeatedly sheds its skin as it steadily grows longer.", 45, 1, 1, 41, 64, 45, 50, 50, 50, 0, 148, R.drawable.dratini_main, R.drawable.dratini_back, R.drawable.dratini_map, R.raw.dratini));
        addPokemon(new Pokemon(148, "Dragonair", mTypes.get(Type.DRAGON), mTypes.get(Type.NONE), "According to a witness, its body was surrounded by a strange aura that gave it a mystical look.", 45, 1, 1, 61, 84, 65, 70, 70, 70, 30, 149, R.drawable.dragonair_main, R.drawable.dragonair_back, R.drawable.dragonair_map, R.raw.dragonair));
        addPokemon(new Pokemon(149, "Dragonite", mTypes.get(Type.DRAGON), mTypes.get(Type.FLYING), "It is said that somewhere in the ocean lies an island where these gather. Only they live there.", 45, 1, 1, 91, 134, 95, 100, 100, 80, 55, 0, R.drawable.dragonite_main, R.drawable.dragonite_back, R.drawable.dragonite_map, R.raw.dragonite));
    }

    //LOADS ALL MOVES
    public void loadAllPokemonMoves(){
        mMoves.add(new Move("Vine Whip", mTypes.get(Type.GRASS), Move.PHYSICAL, 25, 25, 45, 100));
        mMoves.add(new Move("Energy Ball", mTypes.get(Type.GRASS), Move.SPECIAL, 10, 10, 90, 100));
        mMoves.add(new Move("Razor Leaf", mTypes.get(Type.GRASS), Move.PHYSICAL, 25, 25, 55, 95));
        mMoves.add(new Move("Giga Drain", mTypes.get(Type.GRASS), Move.SPECIAL, 10, 10, 75, 100));
        mMoves.add(new Move("Frenzy Plant", mTypes.get(Type.GRASS), Move.SPECIAL, 5, 5, 150, 90));

        mMoves.add(new Move("Ember", mTypes.get(Type.FIRE), Move.SPECIAL, 25, 25, 40, 100));
        mMoves.add(new Move("Fire Fang", mTypes.get(Type.FIRE), Move.PHYSICAL, 15, 15, 65, 95));
        mMoves.add(new Move("Fire Spin", mTypes.get(Type.FIRE), Move.SPECIAL, 15, 15, 35, 85));
        mMoves.add(new Move("Flame Charge", mTypes.get(Type.FIRE), Move.PHYSICAL, 20, 20, 50, 100));
        mMoves.add(new Move("Flamethrower", mTypes.get(Type.FIRE), Move.SPECIAL, 15, 15, 90, 100));
        mMoves.add(new Move("Blast Burn", mTypes.get(Type.FIRE), Move.SPECIAL, 5, 5, 150, 90));

        mMoves.add(new Move("Aqua Jet", mTypes.get(Type.WATER), Move.PHYSICAL, 20, 20, 40, 100));
        mMoves.add(new Move("Water Gun", mTypes.get(Type.WATER), Move.SPECIAL, 25, 25, 40, 100));
        mMoves.add(new Move("Aqua Tail", mTypes.get(Type.WATER), Move.PHYSICAL, 10, 10, 90, 90));
        mMoves.add(new Move("Waterfall", mTypes.get(Type.WATER), Move.PHYSICAL, 15, 15, 80, 100));
        mMoves.add(new Move("Muddy Water", mTypes.get(Type.WATER), Move.SPECIAL, 10, 10, 90, 85));
        mMoves.add(new Move("Hydro Cannon", mTypes.get(Type.WATER), Move.SPECIAL, 5, 5, 150, 90));

        mMoves.add(new Move("Thunder Punch", mTypes.get(Type.ELECTRIC), Move.PHYSICAL, 15, 15, 75, 100));
        mMoves.add(new Move("Spark", mTypes.get(Type.ELECTRIC), Move.PHYSICAL, 20, 20, 65, 100));
        mMoves.add(new Move("Charge Beam", mTypes.get(Type.ELECTRIC), Move.SPECIAL, 10, 10, 50, 90));
        mMoves.add(new Move("Thunder Shock", mTypes.get(Type.ELECTRIC), Move.SPECIAL, 30, 30, 40, 100));
        mMoves.add(new Move("Volt Tackle", mTypes.get(Type.ELECTRIC), Move.SPECIAL, 5, 5, 150, 90));

        mMoves.add(new Move("Rock Throw", mTypes.get(Type.ROCK), Move.PHYSICAL, 15, 15, 50, 90));
        mMoves.add(new Move("Rock Tomb", mTypes.get(Type.ROCK), Move.PHYSICAL, 15, 15, 60, 95));
        mMoves.add(new Move("Stone Edge", mTypes.get(Type.ROCK), Move.PHYSICAL, 5, 5, 100, 90));
        mMoves.add(new Move("Rollout", mTypes.get(Type.ROCK), Move.PHYSICAL, 20, 20, 30, 90));
        mMoves.add(new Move("Rock Slide", mTypes.get(Type.ROCK), Move.PHYSICAL, 10, 10, 75, 90));

        mMoves.add(new Move("Tackle", mTypes.get(Type.NORMAL), Move.PHYSICAL, 35, 35, 40, 100));
        mMoves.add(new Move("Take Down", mTypes.get(Type.NORMAL), Move.PHYSICAL, 20, 20, 90, 85));
        mMoves.add(new Move("Thrash", mTypes.get(Type.NORMAL), Move.PHYSICAL, 10, 10, 120, 100));
        mMoves.add(new Move("Hidden Power", mTypes.get(Type.NORMAL), Move.SPECIAL, 15, 15, 60, 100));
        mMoves.add(new Move("Façade", mTypes.get(Type.NORMAL), Move.PHYSICAL, 20, 20, 70, 100));

    }

    public void loadAllPokemonTypes(){
        double[] normal = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.5f, 0, 1, 1, 0.5f,
                1};
        double[] fire = new double[]{1, 1, 0.5f, 0.5f, 1, 2, 2, 1, 1, 1, 1, 1, 2, 0.5f, 1, 0.5f, 1,
                2, 1};
        double[] water = new double[]{1, 1, 2, 0.5f, 1, 0.5f, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0.5f, 1, 1,
                1};
        double[] electric = new double[]{1, 1, 1, 2, 0.5f, 0.5f, 1, 1, 1, 0, 2, 1, 1, 1, 1, 0.5f, 1,
                1, 1};
        double[] grass = new double[]{1, 1, 0.5f, 2, 1, 0.5f, 1, 1, 0.5f, 2, 0.5f, 1, 0.5f, 2, 1,
                0.5f, 1, 0.5f, 1};
        double[] ice = new double[]{1, 1, 0.5f, 0.5f, 1, 2, 0.5f, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1,
                0.5f, 1};
        double[] fighting = new double[]{1, 2, 1, 1, 1, 1, 2, 1, 0.5f, 1, 0.5f, 0.5f, 0.5f, 2, 0, 1,
                2, 2, 0.5f};
        double[] poison = new double[]{1, 1, 1, 1, 1, 2, 1, 1, 0.5f, 0.5f, 1, 1, 1, 0.5f, 0.5f, 1,
                1, 0, 2};
        double[] ground = new double[]{1, 1, 2, 1, 2, 0.5f, 1, 1, 2, 1, 0, 1, 0.5f, 2, 1, 1, 1, 2,
                1};
        double[] flying = new double[]{1, 1, 1, 1, 0.5f, 2, 1, 2, 1, 1, 1, 1, 2, 0.5f, 1, 1, 1,
                0.5f, 1};
        double[] psychic = new double[]{1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 0.5f, 1, 1, 1, 1, 0, 0.5f,
                1};
        double[] bug = new double[]{1, 1, 0.5f, 1, 1, 2, 1, 0.5f, 0.5f, 1, 0.5f, 2, 1, 1, 0.5f, 1,
                2, 0.5f, 0.5f};
        double[] rock = new double[]{1, 1, 2, 1, 1, 1, 2, 0.5f, 1, 0.5f, 2, 1, 2, 1, 1, 1, 1, 0.5f,
                1};
        double[] ghost = new double[]{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0.5f, 1, 1};
        double[] dragon = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0.5f, 0};
        double[] dark = new double[]{1, 1, 1, 1, 1, 1, 1, 0.5f, 1, 1, 1, 2, 1, 1, 2, 1, 0.5f, 1,
                0.5f};
        double[] steel = new double[]{1, 1, 0.5f, 0.5f, 0.5f, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1,
                0.5f, 2};
        double[] fairy = new double[]{1, 1, 0.5f, 1, 1, 1, 1, 2, 0.5f, 1, 1, 1, 1, 1, 1, 2, 2, 0.5f,
                1};

        mTypes.add(new Type());
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

        mTypes.get(1).setName("NORMAL");
        mTypes.get(2).setName("FIRE");
        mTypes.get(3).setName("WATER");
        mTypes.get(4).setName("ELECTRIC");
        mTypes.get(5).setName("GRASS");
        mTypes.get(6).setName("ICE");
        mTypes.get(7).setName("FIGHTING");
        mTypes.get(8).setName("POISON");
        mTypes.get(9).setName("GROUND");
        mTypes.get(10).setName("FLYING");
        mTypes.get(11).setName("PSYCHIC");
        mTypes.get(12).setName("BUG");
        mTypes.get(13).setName("ROCK");
        mTypes.get(14).setName("GHOST");
        mTypes.get(15).setName("DRAGON");
        mTypes.get(16).setName("DARK");
        mTypes.get(17).setName("STEEL");
        mTypes.get(18).setName("FAIRY");

        mTypes.get(1).setId(Type.NORMAL);
        mTypes.get(2).setId(Type.FIRE);
        mTypes.get(3).setId(Type.WATER);
        mTypes.get(4).setId(Type.ELECTRIC);
        mTypes.get(5).setId(Type.GRASS);
        mTypes.get(6).setId(Type.ICE);
        mTypes.get(7).setId(Type.FIGHTING);
        mTypes.get(8).setId(Type.POISON);
        mTypes.get(9).setId(Type.GROUND);
        mTypes.get(10).setId(Type.FLYING);
        mTypes.get(11).setId(Type.PSYCHIC);
        mTypes.get(12).setId(Type.BUG);
        mTypes.get(13).setId(Type.ROCK);
        mTypes.get(14).setId(Type.GHOST);
        mTypes.get(15).setId(Type.DRAGON);
        mTypes.get(16).setId(Type.DARK);
        mTypes.get(17).setId(Type.STEEL);
        mTypes.get(18).setId(Type.FAIRY);

        mTypes.get(1).setColor(Type.NORMAL_COLOR);
        mTypes.get(2).setColor(Type.FIRE_COLOR);
        mTypes.get(3).setColor(Type.WATER_COLOR);
        mTypes.get(4).setColor(Type.ELECTRIC_COLOR);
        mTypes.get(5).setColor(Type.GRASS_COLOR);
        mTypes.get(6).setColor(Type.ICE_COLOR);
        mTypes.get(7).setColor(Type.FIGHTING_COLOR);
        mTypes.get(8).setColor(Type.POISON_COLOR);
        mTypes.get(9).setColor(Type.GROUND_COLOR);
        mTypes.get(10).setColor(Type.FLYING_COLOR);
        mTypes.get(11).setColor(Type.PSYCHIC_COLOR);
        mTypes.get(12).setColor(Type.BUG_COLOR);
        mTypes.get(13).setColor(Type.ROCK_COLOR);
        mTypes.get(14).setColor(Type.GHOST_COLOR);
        mTypes.get(15).setColor(Type.DRAGON_COLOR);
        mTypes.get(16).setColor(Type.DARK_COLOR);
        mTypes.get(17).setColor(Type.STEEL_COLOR);
        mTypes.get(18).setColor(Type.FAIRY_COLOR);
    }

    public ArrayList<Item> getAllItems() {
        return mItems;
    }

    public void setAllItems(ArrayList<Item> mItems) {
        this.mItems = mItems;
    }

    public void loadAllItems(){
        mItems.add(new Item("Potion", 0, R.drawable.bag_potion_icon, R.drawable.bag_potion, R.drawable.bag_potion_icon, Item.POTION_HEAL));
        mItems.add(new Item("Super Potion", 0, R.drawable.bag_superpotion_icon, R.drawable.bag_superpotion, R.drawable.bag_superpotion_icon, Item.SUPER_POTION_HEAL));
        mItems.add(new Item("Hyper Potion", 0, R.drawable.bag_hyperpotion_icon, R.drawable.bag_hyperpotion, R.drawable.bag_hyperpotion_icon, Item.HYPER_POTION_HEAL));
        mItems.add(new Item("Max Potion", 0, R.drawable.bag_maxpotion_icon, R.drawable.bag_maxpotion, R.drawable.bag_maxpotion_icon, Item.MAX_POTION_HEAL));
        mItems.add(new Item("Revive", 0, R.drawable.bag_revive_icon, R.drawable.bag_revive, R.drawable.bag_revive_icon, Item.REVIVE_DIVIDER));
        mItems.add(new Item("Max Revive", 0, R.drawable.bag_maxrevive_icon, R.drawable.bag_maxrevive, R.drawable.bag_maxrevive_icon, Item.MAX_REVIVE_DIVIDER));
        mItems.add(new Item("Poke Ball", 0, R.drawable.bag_pokeball_icon, R.drawable.bag_pokeball, R.drawable.bag_pokeball_sprite, Item.NO_EFFECT));
        mItems.add(new Item("Great Ball", 0, R.drawable.bag_greatball_icon, R.drawable.bag_greatball, R.drawable.bag_greatball_sprite, Item.NO_EFFECT));
        mItems.add(new Item("Ultra Ball", 0, R.drawable.bag_ultraball_icon, R.drawable.bag_ultraball, R.drawable.bag_ultraball_sprite, Item.NO_EFFECT));
        mItems.add(new Item("Elixir", 0, R.drawable.bag_elixir_icon, R.drawable.bag_elixir, R.drawable.bag_elixir_icon, Item.ELIXIR_RESTORE));
        mItems.add(new Item("Max Elixir", 0, R.drawable.bag_maxrevive_icon, R.drawable.bag_maxelixir, R.drawable.bag_maxelixir_icon, Item.MAX_ELIXIR_RESTORE));

    }

    public void setFontForContainer(ViewGroup contentLayout, String fontName) {
        for (int index = 0; index < contentLayout.getChildCount(); index++) {
            View view = contentLayout.getChildAt(index);
            if (view instanceof TextView)
                ((TextView)view).setTypeface(Typeface.createFromAsset(getAssets(), fontName));
            else if (view instanceof ViewGroup)
                setFontForContainer((ViewGroup) view, fontName);
        }
    }

    public void loadPlayer(LatLng initialPosition) {

        getPlayer().setMarker(getMap().addMarker(
                new MarkerOptions().position(initialPosition).title("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.player_stand))));
        getPlayer().getPokemons()[0] = new PokemonProfile(getSpawnCount(), 50, getAllPokemons().get(2));
        getPlayer().getPokemons()[1] = new PokemonProfile(getSpawnCount(), 50, getAllPokemons().get(4));
        getPlayer().getPokemons()[0].getMoves()[0] = new Move(getAllMoves().get(2));
        getPlayer().getPokemons()[0].getMoves()[1] = new Move(getAllMoves().get(5));
        getPlayer().getPokemons()[0].getMoves()[2] = new Move(getAllMoves().get(16));
        getPlayer().getPokemons()[1].getMoves()[0] = new Move(getAllMoves().get(19));
        getPlayer().getPokemons()[1].getMoves()[1] = new Move(getAllMoves().get(25));
        getPlayer().getBag()[0] = new Item(getAllItems().get(0));
        getPlayer().getBag()[1] = new Item(getAllItems().get(4));
        getPlayer().getBag()[2] = new Item(getAllItems().get(9));
        getPlayer().getBag()[3] = new Item(getAllItems().get(6));
        getPlayer().getBag()[4] = new Item(getAllItems().get(7));
        getPlayer().getBag()[5] = new Item(getAllItems().get(8));
        getPlayer().getBag()[0].setQuantity(10);
        getPlayer().getBag()[1].setQuantity(10);
        getPlayer().getBag()[2].setQuantity(10);
        getPlayer().getBag()[3].setQuantity(10);
        getPlayer().getBag()[4].setQuantity(10);
        getPlayer().getBag()[5].setQuantity(10);
    }

}
