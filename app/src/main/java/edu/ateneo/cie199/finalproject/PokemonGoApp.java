package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.util.Log;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.Image;
import android.support.v7.widget.PopupMenu;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.Image;
import android.support.v7.widget.PopupMenu;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by John on 11/3/2017.
 */

public class PokemonGoApp extends Application{

    public static int STATE_MESSAGE_FIRST = 0;
    public static int STATE_MESSAGE_LAST = 1;
    public static int STATE_MAIN = 2;
    public static int STATE_FIGHT = 3;
    public static int STATE_POKEMON = 4;
    public static int STATE_BAG = 5;
    public static int STATE_USE_ITEM = 6;
    public static int STATE_SWAP_POKEMON1 = 7;
    public static int STATE_SWAP_POKEMON2 = 8;


    public static int FIGHT_COLOR = Color.argb(255, 238, 41, 41);
    public static int POKEMON_COLOR = Color.argb(255, 44, 224, 49);
    public static int DEAD_COLOR = Color.argb(255, 137, 17, 6);
    public static int BAG_COLOR = Color.argb(255, 252, 190, 26);
    public static int RUN_COLOR = Color.argb(255, 43, 154, 255);
    public static int BACK_COLOR = Color.argb(255, 3, 111, 114);
    public static int BAR_COLOR = Color.argb(255, 0, 225, 231);
    public static int TRANSPARENT_COLOR = Color.argb(0, 0, 0, 0);

    private GoogleMap mMap;
    private Player mPlayer = new Player();
    private String playerDataFileName = "player_data.csv";
    private Marker mSelectedMarker = null;
    private Marker mCurrentGoal = null;

    private boolean loadData = false;

    private MusicHandler musicHandler = new MusicHandler();
    private boolean mMusicSwitch = true;
    private boolean mSFXSwitch = true;


    private HttpClient mHttpClient = new DefaultHttpClient();

    private String movesApiUrl = "https://rrttp.localtunnel.me/moves/moves";//"http://192.168.43.195:8000/moves/moves";
    private String pokemonApiUrl = "https://rrttp.localtunnel.me/pokemon/get_all_pokemon";//"http://192.168.43.195:8000/pokemon/get_all_pokemon";
    private String randPokemonApiUrl = "https://rrttp.localtunnel.me/pokemon/random_list";//"http://192.168.43.195:8000/pokemon/random_list";

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
    public boolean getLoadData(){return loadData;}
    public void setLoadData(boolean loadData) {
        this.loadData = loadData;
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
    public String getRandPokemonApiUrl() {return randPokemonApiUrl;}

    public MusicHandler getMusicHandler() {
        return musicHandler;
    }
    public HttpClient getmHttpClient(){ return mHttpClient; }
    public boolean getMusicSwitch() { return mMusicSwitch;}
    public boolean getSFXSwitch() {return mSFXSwitch;}

    public void setMusicOn() {mMusicSwitch = true;}
    public void setMusicOff() {mMusicSwitch = false;}
    public void setSFXOn() {mSFXSwitch = true;}
    public void setSFXOff() {mSFXSwitch = false;}

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

    public Move findMove(String title){
        for(Move move : this.getAllMoves()){
            if(move.getName().equals(title)){
                return move;
            }
        }
        return new MovePhysical();
    }

    public Item getItem(String title){
        for(int index = 0; index < mItems.size(); index++){
            if(mItems.get(index).getName().equals(title)){
                return mItems.get(index);
            }
        }
        return new ItemPotion();
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

//    //TODO: MAKE IT LOAD FROM FILE INSTEAD OF HARD CODE
//    public void loadAllPokemon(){
//        addPokemon(new Pokemon(1, "Bulbasaur", mTypes.get(Type.GRASS), mTypes.get(Type.POISON), "It can go for days without eating a single morsel. In the bulb on its back, it stores energy.", 190, 1, 7, 45, 49, 49, 65, 65, 45, 0, 2, R.drawable.bulbasaur_main, R.drawable.bulbasaur_back, R.drawable.bulbasaur_map, R.raw.bulbasaur));
//        addPokemon(new Pokemon(2, "Ivysaur", mTypes.get(Type.GRASS), mTypes.get(Type.POISON), "The bulb on its back grows by drawing energy. It gives off an aroma when it is ready to bloom.", 45, 1, 7, 60, 62, 63, 80, 80, 60, 16, 3, R.drawable.ivysaur_main, R.drawable.ivysaur_back, R.drawable.ivysaur_map, R.raw.ivysaur));
//        addPokemon(new Pokemon(3, "Venusaur", mTypes.get(Type.GRASS), mTypes.get(Type.POISON), "The flower on its back catches the sun's rays. The sunlight is then absorbed and used for energy.", 45, 1, 7, 80, 82, 83, 100, 100, 80, 32, 0, R.drawable.venusaur_main, R.drawable.venusaur_back, R.drawable.venusaur_map, R.raw.venusaur));
//        addPokemon(new Pokemon(4, "Charmander", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "The flame at the tip of its tail makes a sound as it burns. You can only hear it in quiet places.", 190, 1, 7, 39, 52, 43, 60, 50, 65, 0, 5, R.drawable.charmander_main, R.drawable.charmander_back, R.drawable.charmander_map, R.raw.charmander));
//        addPokemon(new Pokemon(5, "Charmeleon", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "It is very hotheaded by nature, so it constantly seeks opponents. It calms down only when it wins.", 45, 1, 7, 58, 64, 58, 80, 65, 80, 16, 6, R.drawable.charmeleon_main, R.drawable.charmeleon_back, R.drawable.charmeleon_map, R.raw.charmeleon));
//        addPokemon(new Pokemon(6, "Charizard", mTypes.get(Type.FIRE), mTypes.get(Type.FLYING), "When expelling a blast of super hot fire, the red flame at the tip of its tail burns more intensely.", 45, 1, 7, 78, 84, 78, 109, 85, 100, 36, 0, R.drawable.charizard_main, R.drawable.charizard_back, R.drawable.charizard_map, R.raw.charizard));
//        addPokemon(new Pokemon(7, "Squirtle", mTypes.get(Type.WATER), mTypes.get(Type.NONE), "Shoots water at prey while in the water. Withdraws into its shell when in danger.", 190, 1, 7, 44, 48, 65, 50, 64, 43, 0, 8, R.drawable.squirtle_main, R.drawable.squirtle_back, R.drawable.squirtle_map, R.raw.squirtle));
//        addPokemon(new Pokemon(8, "Wartortle", mTypes.get(Type.WATER), mTypes.get(Type.NONE), "Often hides in water to stalk unwary prey. For swimming fast, it moves its ears to maintain balance.", 45, 1, 7, 59, 63, 80, 65, 80, 58, 16, 9, R.drawable.wartortle_main, R.drawable.wartortle_back, R.drawable.wartortle_map, R.raw.wartortle));
//        addPokemon(new Pokemon(9, "Blastoise", mTypes.get(Type.WATER), mTypes.get(Type.NONE), "It deliberately makes itself heavy so it can withstand the recoil of the water jets it fires.", 45, 1, 7, 79, 103, 120, 135, 115, 78, 36, 0, R.drawable.blastoise_main, R.drawable.blastoise_back, R.drawable.blastoise_map, R.raw.blastoise));
//        addPokemon(new Pokemon(16, "Pidgey", mTypes.get(Type.NORMAL), mTypes.get(Type.FLYING), "Very docile. If attacked, it will often kick up sand to protect itself rather than fight back.", 255, 1, 1, 40, 45, 40, 35, 35, 56, 0, 17, R.drawable.pidgey_main, R.drawable.pidgey_back, R.drawable.pidgey_map, R.raw.pidgey));
//        addPokemon(new Pokemon(17, "Pidgeotto", mTypes.get(Type.NORMAL), mTypes.get(Type.FLYING), "It has outstanding vision. However high it flies, it is able to distinguish the movements of its prey.", 120, 1, 1, 63, 60, 55, 50, 50, 71, 18, 18, R.drawable.pidgeotto_main, R.drawable.pidgeotto_back, R.drawable.pidgeotto_map, R.raw.pidgeotto));
//        addPokemon(new Pokemon(18, "Pidgeot", mTypes.get(Type.NORMAL), mTypes.get(Type.FLYING), "Its well-developed chest muscles make it strong enough to whip up a gusty windstorm with just a few flaps.", 45, 1, 1, 83, 80, 75, 70, 70, 101, 36, 0, R.drawable.pidgeot_main, R.drawable.pidgeot_back, R.drawable.pidgeot_map, R.raw.pidgeot));
//        addPokemon(new Pokemon(25, "Pikachu", mTypes.get(Type.ELECTRIC), mTypes.get(Type.NONE), "It keeps its tail raised to monitor its surroundings. If you yank its tail, it will try to bite you.", 190, 1, 1, 35, 55, 40, 50, 50, 90, 0, 26, R.drawable.pikachu_main, R.drawable.pikachu_back, R.drawable.pikachu_map, R.raw.pikachu));
//        addPokemon(new Pokemon(26, "Raichu", mTypes.get(Type.ELECTRIC), mTypes.get(Type.NONE), "When electricity builds up inside its body, it becomes feisty. It also glows in the dark.", 75, 1, 1, 60, 90, 55, 90, 80, 110, 22, 0, R.drawable.raichu_main, R.drawable.raichu_back, R.drawable.raichu_map, R.raw.raichu));
//        addPokemon(new Pokemon(58, "Growlithe", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "It has a brave and trustworthy nature. It fearlessly stands up to bigger and stronger foes.", 190, 1, 3, 55, 70, 45, 70, 50, 60, 0, 59, R.drawable.growlithe_main, R.drawable.growlithe_back, R.drawable.growlithe_map, R.raw.growlithe));
//        addPokemon(new Pokemon(59, "Arcanine", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "An ancient picture scroll shows that people were attracted to its movement as it ran through prairies.", 75, 1, 3, 90, 110, 80, 100, 80, 95, 22, 0, R.drawable.arcanine_main, R.drawable.arcanine_back, R.drawable.arcanine_map, R.raw.arcanine));
//        addPokemon(new Pokemon(63, "Abra", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "Sleeps 18 hours a day. If it senses danger, it will teleport itself to safety even as it sleeps.", 200, 1, 3, 25, 20, 15, 105, 55, 90, 0, 64, R.drawable.abra_main, R.drawable.abra_back, R.drawable.abra_map, R.raw.abra));
//        addPokemon(new Pokemon(64, "Kadabra", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "It possesses strong spiritual power. The more danger it faces, the stronger its psychic power.", 100, 1, 3, 40, 35, 30, 120, 70, 105, 16, 65, R.drawable.kadabra_main, R.drawable.kadabra_back, R.drawable.kadabra_map, R.raw.kadabra));
//        addPokemon(new Pokemon(65, "Alakazam", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "Closing both its eyes heightens all its other senses. This enables it to use its abilities to their extremes.", 50, 1, 3, 55, 50, 45, 135, 95, 120, 40, 0, R.drawable.alakazam_main, R.drawable.alakazam_back, R.drawable.alakazam_map, R.raw.alakazam));
//        addPokemon(new Pokemon(66, "Machop", mTypes.get(Type.FIGHTING), mTypes.get(Type.NONE), "Very powerful in spite of its small size. Its mastery of many types of martial arts makes it very tough.", 180, 1, 3, 70, 80, 50, 35, 35, 35, 0, 67, R.drawable.machop_main, R.drawable.machop_back, R.drawable.machop_map, R.raw.machop));
//        addPokemon(new Pokemon(67, "Machoke", mTypes.get(Type.FIGHTING), mTypes.get(Type.NONE), "Its muscular body is so powerful, it must wear a power save belt to be able to regulate its motions.", 90, 1, 3, 80, 100, 70, 50, 60, 45, 28, 68, R.drawable.machoke_main, R.drawable.machoke_back, R.drawable.machoke_map, R.raw.machoke));
//        addPokemon(new Pokemon(68, "Machamp", mTypes.get(Type.FIGHTING), mTypes.get(Type.NONE), "Using its heavy muscles, it throws powerful punches that can send the victim clear over the horizon.", 45, 1, 3, 90, 130, 80, 65, 85, 55, 40, 0, R.drawable.machamp_main, R.drawable.machamp_back, R.drawable.machamp_map, R.raw.machamp));
//        addPokemon(new Pokemon(72, "Tentacool", mTypes.get(Type.WATER), mTypes.get(Type.POISON), "It can sometimes be found all dry and shriveled up on a beach. Toss it back into the sea to revive it.", 190, 1, 1, 40, 40, 35, 50, 100, 70, 0, 73, R.drawable.tentacool_main, R.drawable.tentacool_back, R.drawable.tentacool_map, R.raw.tentacool));
//        addPokemon(new Pokemon(73, "Tentacruel", mTypes.get(Type.WATER), mTypes.get(Type.POISON), "The tentacles are normally kept short. On hunts, they are extended to ensnare and immobilize prey", 60, 1, 1, 80, 70, 65, 80, 120, 100, 30, 0, R.drawable.tentacruel_main, R.drawable.tentacruel_back, R.drawable.tentacruel_map, R.raw.tentacruel));
//        addPokemon(new Pokemon(74, "Geodude", mTypes.get(Type.ROCK), mTypes.get(Type.GROUND), "Commonly found near mountain trails, etc. If you step on one by accident, it gets angry.", 255, 1, 1, 40, 80, 100, 30, 30, 20, 0, 75, R.drawable.geodude_main, R.drawable.geodude_back, R.drawable.geodude_map, R.raw.geodude));
//        addPokemon(new Pokemon(75, "Graveler", mTypes.get(Type.ROCK), mTypes.get(Type.GROUND), "Often seen rolling down mountain trails. Obstacles are just things to roll straight over, not avoid.", 120, 1, 1, 55, 95, 115, 45, 45, 35, 25, 76, R.drawable.graveler_main, R.drawable.graveler_back, R.drawable.graveler_map, R.raw.graveler));
//        addPokemon(new Pokemon(76, "Golem", mTypes.get(Type.ROCK), mTypes.get(Type.GROUND), "Its boulder-like body is extremely hard. It can easily withstand dynamite blasts without damage.", 45, 1, 1, 80, 120, 130, 55, 65, 45, 40, 0, R.drawable.golem_main, R.drawable.golem_back, R.drawable.golem_map, R.raw.golem));
//        addPokemon(new Pokemon(81, "Magnemite", mTypes.get(Type.ELECTRIC), mTypes.get(Type.STEEL), "It is born with the ability to defy gravity. It floats in air on powerful electromagnetic waves.", 190, 0, 0, 25, 35, 70, 95, 55, 45, 0, 82, R.drawable.magnemite_main, R.drawable.magnemite_back, R.drawable.magnemite_map, R.raw.magnemite));
//        addPokemon(new Pokemon(82, "Magneton", mTypes.get(Type.ELECTRIC), mTypes.get(Type.STEEL), "Generates strange radio signals. It raises the temperature by 3.6F degrees within 3,300 feet.", 60, 0, 0, 50, 60, 95, 120, 70, 70, 30, 0, R.drawable.magneton_main, R.drawable.magneton_back, R.drawable.magneton_map, R.raw.magneton));
//        addPokemon(new Pokemon(92, "Gastly", mTypes.get(Type.GHOST), mTypes.get(Type.POISON), "Said to appear in decrepit, deserted buildings. It has no real shape as it appears to be made of a gas.", 190, 1, 1, 30, 35, 30, 100, 35, 80, 0, 93, R.drawable.gastly_main, R.drawable.gastly_back, R.drawable.gastly_map, R.raw.gastly));
//        addPokemon(new Pokemon(93, "Haunter", mTypes.get(Type.GHOST), mTypes.get(Type.POISON), "By licking, it saps the victim's life. It causes shaking that won't stop until the victim's demise.", 90, 1, 1, 45, 50, 45, 115, 55, 95, 25, 94, R.drawable.haunter_main, R.drawable.haunter_back, R.drawable.haunter_map, R.raw.haunter));
//        addPokemon(new Pokemon(94, "Gengar", mTypes.get(Type.GHOST), mTypes.get(Type.POISON), "To steal the life of its target, it slips into the prey's shadow and silently waits for an opportunity.", 45, 1, 1, 60, 65, 60, 130, 75, 110, 40, 0, R.drawable.gengar_main, R.drawable.gengar_back, R.drawable.gengar_map, R.raw.gengar));
//        addPokemon(new Pokemon(109, "Koffing", mTypes.get(Type.POISON), mTypes.get(Type.NONE), "In hot places, its internal gases could expand and explode without any warning. Be very careful!", 190, 1, 1, 40, 65, 95, 60, 45, 35, 0, 110, R.drawable.koffing_main, R.drawable.koffing_back, R.drawable.koffing_map, R.raw.koffing));
//        addPokemon(new Pokemon(110, "Weezing", mTypes.get(Type.POISON), mTypes.get(Type.NONE), "It lives and grows by absorbing dust, germs and poison gases that are contained in toxic waste and garbage.", 60, 1, 1, 65, 90, 120, 85, 70, 60, 35, 0, R.drawable.weezing_main, R.drawable.weezing_back, R.drawable.weezing_map, R.raw.weezing));
//        addPokemon(new Pokemon(111, "Rhyhorn", mTypes.get(Type.GROUND), mTypes.get(Type.ROCK), "It is inept at turning because of its four short legs. It can only charge and run in one direction.", 120, 1, 1, 80, 85, 95, 30, 20, 25, 0, 112, R.drawable.rhyhorn_main, R.drawable.rhyhorn_back, R.drawable.rhyhorn_map, R.raw.rhyhorn));
//        addPokemon(new Pokemon(112, "Rhydon", mTypes.get(Type.GROUND), mTypes.get(Type.ROCK), "Walks on its hind legs. Shows signs of intelligence. Its armor-like hide even repels molten lava.", 60, 1, 1, 105, 130, 120, 45, 45, 40, 42, 0, R.drawable.rhydon_main, R.drawable.rhydon_back, R.drawable.rhydon_map, R.raw.rhydon));
//        addPokemon(new Pokemon(122, "Mr. Mime", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "Always practices its pantomime act. It makes enemies believe something exists that really doesn't.", 45, 1, 1, 40, 45, 65, 100, 120, 90, 0, 0, R.drawable.mr_mime_main, R.drawable.mr_mime_back, R.drawable.mr_mime_map, R.raw.mr_mime));
//        addPokemon(new Pokemon(124, "Jynx", mTypes.get(Type.ICE), mTypes.get(Type.PSYCHIC), "Appears to move to a rhythm of its own, as if it were dancing. It wiggles its hips as it walks.", 45, 1, 0, 65, 50, 35, 115, 95, 95, 0, 0, R.drawable.jynx_main, R.drawable.jynx_back, R.drawable.jynx_map, R.raw.jynx));
//        addPokemon(new Pokemon(125, "Electabuzz", mTypes.get(Type.ELECTRIC), mTypes.get(Type.NONE), "Electricity runs across the surface of its body. In darkness, its entire body glows a whitish-blue.", 45, 1, 3, 65, 83, 57, 95, 85, 105, 0, 0, R.drawable.electabuzz_main, R.drawable.electabuzz_back, R.drawable.electabuzz_map, R.raw.electabuzz));
//        addPokemon(new Pokemon(126, "Magmar", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "Born in an active volcano. Its body is always cloaked in flames, so it looks like a big ball of fire.", 45, 1, 3, 65, 95, 57, 100, 85, 93, 0, 0, R.drawable.magmar_main, R.drawable.magmar_back, R.drawable.magmar_map, R.raw.magmar));
//        addPokemon(new Pokemon(127, "Pinsir", mTypes.get(Type.BUG), mTypes.get(Type.NONE), "Grips its prey in its pincers and squeezes hard! It can't move if it's cold, so it lives in warm places", 45, 1, 1, 65, 125, 100, 55, 70, 85, 0, 0, R.drawable.pinsir_main, R.drawable.pinsir_back, R.drawable.pinsir_map, R.raw.pinsir));
//        addPokemon(new Pokemon(128, "Tauros", mTypes.get(Type.NORMAL), mTypes.get(Type.NONE), "They fight each other by locking horns. The herd's protector takes pride in its battle-scarred horns.", 45, 0, 1, 75, 100, 95, 40, 70, 110, 0, 0, R.drawable.tauros_main, R.drawable.tauros_back, R.drawable.tauros_map, R.raw.tauros));
//        addPokemon(new Pokemon(131, "Lapras", mTypes.get(Type.WATER), mTypes.get(Type.ICE), "They have gentle hearts. Because they rarely fight, many have been caught. Their number has dwindled.", 45, 1, 1, 130, 85, 80, 85, 95, 60, 0, 0, R.drawable.lapras_main, R.drawable.lapras_back, R.drawable.lapras_map, R.raw.lapras));
//        addPokemon(new Pokemon(138, "Omanyte", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "Although long extinct, in rare cases, it can be genetically resurrected from fossils.", 45, 1, 7, 35, 40, 100, 90, 55, 35, 0, 139, R.drawable.omanyte_main, R.drawable.omanyte_back, R.drawable.omanyte_map, R.raw.omanyte));
//        addPokemon(new Pokemon(139, "Omastar", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "Sharp beaks ring its mouth. Its shell was too big for it to move freely, so it became extinct.", 45, 1, 7, 70, 60, 125, 115, 70, 55, 40, 0, R.drawable.omastar_main, R.drawable.omastar_back, R.drawable.omastar_map, R.raw.omastar));
//        addPokemon(new Pokemon(140, "Kabuto", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "On rare occasions, some have been found as fossils which they became while hiding on the ocean floor.", 45, 1, 7, 30, 80, 90, 55, 45, 55, 0, 141, R.drawable.kabuto_main, R.drawable.kabuto_back, R.drawable.kabuto_map, R.raw.kabuto));
//        addPokemon(new Pokemon(141, "Kabutops", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "In the water, it tucks in its limbs to become more compact, then it wiggles its shell to swim fast.", 45, 1, 7, 60, 115, 105, 65, 70, 80, 40, 0, R.drawable.kabutops_main, R.drawable.kabutops_back, R.drawable.kabutops_map, R.raw.kabutops));
//        addPokemon(new Pokemon(147, "Dratini", mTypes.get(Type.DRAGON), mTypes.get(Type.NONE), "It is born large to start with. It repeatedly sheds its skin as it steadily grows longer.", 45, 1, 1, 41, 64, 45, 50, 50, 50, 0, 148, R.drawable.dratini_main, R.drawable.dratini_back, R.drawable.dratini_map, R.raw.dratini));
//        addPokemon(new Pokemon(148, "Dragonair", mTypes.get(Type.DRAGON), mTypes.get(Type.NONE), "According to a witness, its body was surrounded by a strange aura that gave it a mystical look.", 45, 1, 1, 61, 84, 65, 70, 70, 70, 30, 149, R.drawable.dragonair_main, R.drawable.dragonair_back, R.drawable.dragonair_map, R.raw.dragonair));
//        addPokemon(new Pokemon(149, "Dragonite", mTypes.get(Type.DRAGON), mTypes.get(Type.FLYING), "It is said that somewhere in the ocean lies an island where these gather. Only they live there.", 45, 1, 1, 91, 134, 95, 100, 100, 80, 55, 0, R.drawable.dragonite_main, R.drawable.dragonite_back, R.drawable.dragonite_map, R.raw.dragonite));
//    }
    public boolean isNetworkConnected() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    return cm.getActiveNetworkInfo() != null;
    }

    public String getStringFromApi(String url) throws IOException {
        HttpClient hc = this.getmHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        String message = null;
        try {
            response = hc.execute(request);
            int retStatus = response.getStatusLine().getStatusCode();
            if (retStatus != HttpStatus.SC_OK) {
                Log.e("Error", String.valueOf(retStatus));
            }
            else {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    message = EntityUtils.toString(entity);
                } else {
                    Log.e("Entity Null", "HTTP Entity response is Null");
                    response.getEntity().consumeContent();
                }
            }

        } catch (IOException e) {
            Log.e("Error", "IOException occurred for "+ url);
        }

        return message;
    }

    public String postStringToApi(String url, String data[],String header[]) {
        HttpClient hc = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        List<NameValuePair> postParams = new ArrayList<>();
        String message = "";
        for (int index = 0; index < data.length; index++) {
            postParams.add(new BasicNameValuePair(header[index], data[index]));
        }
        try {
            request.setEntity(new UrlEncodedFormEntity(postParams));
            HttpResponse response = hc.execute(request);
            int retStatus = response.getStatusLine().getStatusCode();
            if (retStatus != HttpStatus.SC_OK) {
                Log.e("Error", String.valueOf(retStatus));
            } else {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    message = EntityUtils.toString(entity);
                    Log.e("Entity Respons", message);
                } else {
                    Log.e("Entity Null", "HTTP Entity response is Null");
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }


    public boolean parseJsonMoveData(String jsonData) throws JSONException {
        if (jsonData!=null){
            JSONArray poiArr = new JSONArray(jsonData);
            for (int iIdx = 0; iIdx < poiArr.length(); iIdx++) {
                JSONObject placeObj = poiArr.getJSONObject(iIdx);
                String name = placeObj.getString("Name");
                Type type = mTypes.get(this.getTypeIdFromString(placeObj.getString("Type")));
                int category = Move.decodeCategory(placeObj.getString("Category"));
                int power = Integer.parseInt(placeObj.getString("Power"));
                int acc = Integer.parseInt(placeObj.getString("Accuracy"));
                int maxpp = Integer.parseInt(placeObj.getString("Max PP"));
                if(category == 0){
                    mMoves.add(new MovePhysical(name, type,maxpp, maxpp, power, acc));
                }
                else{
                    mMoves.add(new MoveSpecial(name, type, maxpp, maxpp, power, acc));
                }
                Log.e("Test", mMoves.get(iIdx).toString());
            }
            return true;
        }
        else{
            Log.e("Error", "json is null");
            return false;
        }
    }

    public boolean parseJsonPokemonData(String jsonData) throws JSONException {
        if (jsonData != null) {
            JSONArray poiArr = new JSONArray(jsonData);
            for (int iIdx = 0; iIdx < poiArr.length(); iIdx++) {
                JSONObject placeObj = poiArr.getJSONObject(iIdx);
                int dex = Integer.parseInt(placeObj.getString("Dex #"));
                String name = placeObj.getString("Name");
                Type typeOne = mTypes.get(this.getTypeIdFromString(placeObj.getString("Type 1")));
                Type typeTwo = mTypes.get(this.getTypeIdFromString(placeObj.getString("Type 2")));
                String desc = placeObj.getString("Description");
                int catchRate = Integer.parseInt(placeObj.getString("Catch Rate"));
                int femaleRatio = Integer.parseInt(placeObj.getString("Female Ratio (%)"));
                int maleRatio = Integer.parseInt(placeObj.getString("Male Ratio (%)"));
                int hp = Integer.parseInt(placeObj.getString("Base HP"));
                int atk = Integer.parseInt(placeObj.getString("Base Atk"));
                int def = Integer.parseInt(placeObj.getString("Base Def"));
                int spAtk = Integer.parseInt(placeObj.getString("Base SP Atk"));
                int spDef = Integer.parseInt(placeObj.getString("Base SP Def"));
                int spd = Integer.parseInt(placeObj.getString("speed"));
                int lv1Req = Integer.parseInt(placeObj.getString("Lvl Req"));
                int nextDex = Integer.parseInt(placeObj.getString("NextDex"));
                int mainImg = getResources().getIdentifier(placeObj.getString("Main Img"), "drawable", getPackageName());
                int backImg = getResources().getIdentifier(placeObj.getString("Back Img"), "drawable", getPackageName());
                int mapImg = getResources().getIdentifier(placeObj.getString("Map IMG"), "drawable", getPackageName());
                int rawImg = getResources().getIdentifier(placeObj.getString("Raw Img"), "raw", getPackageName());
                String height = placeObj.getString("Height");
                String weight = placeObj.getString("Weight");
                addPokemon(new Pokemon(dex, name, typeOne, typeTwo, desc, catchRate, femaleRatio, maleRatio, hp, atk, def, spAtk, spDef, spd, lv1Req, nextDex,
                        mainImg, backImg, mapImg, rawImg, height, weight));
//                Pokemon m = new Pokemon(name, type, category, maxpp, maxpp , power, acc);
//                mTest.add(m);
                Log.e("Test", mPokemons.get(iIdx).toString());
            }
            return true;
        }
        else{
            Log.e("Error", "json is null");
            return false;
        }
    }

    public JSONObject parseRandSpawner(String jsonData) throws JSONException {
        if(jsonData!=null) {
            JSONObject randJson = new JSONObject(jsonData);
            return randJson;
        }
        else{
            Log.e("Error Parsing Rand", "JSON Data Random List is Null");
            return null;
        }
    }


    //LOADS ALL MOVES
    public void loadAllPokemonMoves(){
        mMoves.add(new MovePhysical("Vine Whip", mTypes.get(Type.GRASS), 25, 25, 45, 100));
        mMoves.add(new MoveSpecial("Energy Ball", mTypes.get(Type.GRASS), 10, 10, 90, 100));
        mMoves.add(new MovePhysical("Razor Leaf", mTypes.get(Type.GRASS), 25, 25, 55, 95));
        mMoves.add(new MoveSpecial("Giga Drain", mTypes.get(Type.GRASS), 10, 10, 75, 100));
        mMoves.add(new MoveSpecial("Frenzy Plant", mTypes.get(Type.GRASS), 5, 5, 150, 90));

        mMoves.add(new MoveSpecial("Ember", mTypes.get(Type.FIRE), 25, 25, 40, 100));
        mMoves.add(new MovePhysical("Fire Fang", mTypes.get(Type.FIRE), 15, 15, 65, 95));
        mMoves.add(new MoveSpecial("Fire Spin", mTypes.get(Type.FIRE), 15, 15, 35, 85));
        mMoves.add(new MovePhysical("Flame Charge", mTypes.get(Type.FIRE), 20, 20, 50, 100));
        mMoves.add(new MoveSpecial("Flamethrower", mTypes.get(Type.FIRE), 15, 15, 90, 100));
        mMoves.add(new MoveSpecial("Blast Burn", mTypes.get(Type.FIRE), 5, 5, 150, 90));

        mMoves.add(new MovePhysical("Aqua Jet", mTypes.get(Type.WATER), 20, 20, 40, 100));
        mMoves.add(new MoveSpecial("Water Gun", mTypes.get(Type.WATER), 25, 25, 40, 100));
        mMoves.add(new MovePhysical("Aqua Tail", mTypes.get(Type.WATER), 10, 10, 90, 90));
        mMoves.add(new MovePhysical("Waterfall", mTypes.get(Type.WATER), 15, 15, 80, 100));
        mMoves.add(new MoveSpecial("Muddy Water", mTypes.get(Type.WATER), 10, 10, 90, 85));
        mMoves.add(new MoveSpecial("Hydro Cannon", mTypes.get(Type.WATER), 5, 5, 150, 90));

        mMoves.add(new MovePhysical("Thunder Punch", mTypes.get(Type.ELECTRIC), 15, 15, 75, 100));
        mMoves.add(new MovePhysical("Spark", mTypes.get(Type.ELECTRIC), 20, 20, 65, 100));
        mMoves.add(new MoveSpecial("Charge Beam", mTypes.get(Type.ELECTRIC), 10, 10, 50, 90));
        mMoves.add(new MoveSpecial("Thunder Shock", mTypes.get(Type.ELECTRIC), 30, 30, 40, 100));
        mMoves.add(new MoveSpecial("Volt Tackle", mTypes.get(Type.ELECTRIC), 5, 5, 150, 90));

        mMoves.add(new MovePhysical("Rock Throw", mTypes.get(Type.ROCK), 15, 15, 50, 90));
        mMoves.add(new MovePhysical("Rock Tomb", mTypes.get(Type.ROCK), 15, 15, 60, 95));
        mMoves.add(new MovePhysical("Stone Edge", mTypes.get(Type.ROCK), 5, 5, 100, 90));
        mMoves.add(new MovePhysical("Rollout", mTypes.get(Type.ROCK), 20, 20, 30, 90));
        mMoves.add(new MovePhysical("Rock Slide", mTypes.get(Type.ROCK), 10, 10, 75, 90));

        mMoves.add(new MovePhysical("Tackle", mTypes.get(Type.NORMAL), 35, 35, 40, 100));
        mMoves.add(new MovePhysical("Take Down", mTypes.get(Type.NORMAL), 20, 20, 90, 85));
        mMoves.add(new MovePhysical("Thrash", mTypes.get(Type.NORMAL), 10, 10, 120, 100));
        mMoves.add(new MoveSpecial("Hidden Power", mTypes.get(Type.NORMAL), 15, 15, 60, 100));
        mMoves.add(new MovePhysical("Façade", mTypes.get(Type.NORMAL), 20, 20, 70, 100));
        mMoves.add(new MovePhysical("Body Slam", mTypes.get(Type.NORMAL), 15, 15, 85, 100));
        mMoves.add(new MovePhysical("Horn Attack", mTypes.get(Type.NORMAL), 25, 25, 65, 100));

        mMoves.add(new MovePhysical("Ice Ball", mTypes.get(Type.ICE), 20, 20, 30, 90));
        mMoves.add(new MovePhysical("Avalanche", mTypes.get(Type.ICE), 10, 10, 60, 100));
        mMoves.add(new MoveSpecial("Freeze-Dry", mTypes.get(Type.ICE), 20, 20, 70, 100));
        mMoves.add(new MovePhysical("Freeze Shock", mTypes.get(Type.ICE), 5, 5, 140, 90));
        mMoves.add(new MovePhysical("Ice Punch", mTypes.get(Type.ICE), 15, 15, 75, 100));

        mMoves.add(new MovePhysical("Brick Break", mTypes.get(Type.FIGHTING), 20, 20, 75, 100));
        mMoves.add(new MovePhysical("Double Kick", mTypes.get(Type.FIGHTING), 30, 30, 30, 100));
        mMoves.add(new MovePhysical("Dynamic Punch", mTypes.get(Type.FIGHTING), 5, 5, 100, 50));
        mMoves.add(new MovePhysical("Karate Chop", mTypes.get(Type.FIGHTING), 25, 25, 50, 100));
        mMoves.add(new MovePhysical("Submission", mTypes.get(Type.FIGHTING), 20, 20, 80, 80));

        mMoves.add(new MoveSpecial("Acid", mTypes.get(Type.POISON), 30, 30, 40, 100));
        mMoves.add(new MoveSpecial("Belch", mTypes.get(Type.POISON), 10, 10, 120, 90));
        mMoves.add(new MovePhysical("Cross Poison", mTypes.get(Type.POISON), 20, 20, 70, 100));
        mMoves.add(new MovePhysical("Poison Jab", mTypes.get(Type.POISON), 20, 20, 80, 100));
        mMoves.add(new MovePhysical("Poison Tail", mTypes.get(Type.POISON), 25, 25, 50, 100));

        mMoves.add(new MoveSpecial("Earth Power", mTypes.get(Type.GROUND), 10, 10, 90, 100));
        mMoves.add(new MovePhysical("Sand Tomb", mTypes.get(Type.GROUND), 15, 15, 35, 100));
        mMoves.add(new MoveSpecial("Mud Shot", mTypes.get(Type.GROUND), 15, 15, 55, 95));
        mMoves.add(new MovePhysical("Precipic Blades", mTypes.get(Type.GROUND), 10, 10, 120, 85));
        mMoves.add(new MoveSpecial("Mud Bomb", mTypes.get(Type.GROUND), 10, 10, 65, 90));

        mMoves.add(new MovePhysical("Aerial Ace", mTypes.get(Type.FLYING), 20, 20, 60, 100));
        mMoves.add(new MovePhysical("Air Cutter", mTypes.get(Type.FLYING), 25, 25, 60, 95));
        mMoves.add(new MovePhysical("Brave Bird", mTypes.get(Type.FLYING), 15, 15, 120, 85));
        mMoves.add(new MovePhysical("Drill Peck", mTypes.get(Type.FLYING), 20, 20, 80, 100));
        mMoves.add(new MovePhysical("Peck", mTypes.get(Type.FLYING), 35, 35, 35, 100));

        mMoves.add(new MoveSpecial("Confusion", mTypes.get(Type.PSYCHIC), 25, 25, 50, 100));
        mMoves.add(new MoveSpecial("Extrasensory", mTypes.get(Type.PSYCHIC), 20, 20, 80, 100));
        mMoves.add(new MovePhysical("Heart Stamp ", mTypes.get(Type.PSYCHIC), 25, 25, 60, 100));
        mMoves.add(new MovePhysical("Psycho Cut", mTypes.get(Type.PSYCHIC), 20, 20, 70, 100));
        mMoves.add(new MoveSpecial("Psycho Boost", mTypes.get(Type.PSYCHIC), 5, 5, 140, 85));

        mMoves.add(new MovePhysical("Bug Bite", mTypes.get(Type.BUG), 20, 20, 60, 100));
        mMoves.add(new MovePhysical("X-Scissor", mTypes.get(Type.BUG), 15, 15, 80, 100));
        mMoves.add(new MoveSpecial("Signal Beam", mTypes.get(Type.BUG), 15, 15, 75, 100));
        mMoves.add(new MovePhysical("Fury Cutter", mTypes.get(Type.BUG), 20, 20, 40, 95));
        mMoves.add(new MovePhysical("Megahorn", mTypes.get(Type.BUG), 10, 10, 120, 85));

        mMoves.add(new MovePhysical("Astonish", mTypes.get(Type.GHOST), 15, 15, 30, 100));
        mMoves.add(new MoveSpecial("Hex", mTypes.get(Type.GHOST), 10, 10, 65, 100));
        mMoves.add(new MoveSpecial("Shadow Ball", mTypes.get(Type.GHOST), 15, 15, 80, 100));
        mMoves.add(new MovePhysical("Shadow Claw", mTypes.get(Type.GHOST), 15, 15, 70, 100));
        mMoves.add(new MovePhysical("Shadow Force", mTypes.get(Type.GHOST), 5, 5, 120, 85));

        mMoves.add(new MovePhysical("Dragon Claw", mTypes.get(Type.DRAGON), 15, 15, 80, 100));
        mMoves.add(new MovePhysical("Outrage", mTypes.get(Type.DRAGON), 10, 10, 120, 85));
        mMoves.add(new MovePhysical("Dragon Tail", mTypes.get(Type.DRAGON), 10, 10, 60, 90));
        mMoves.add(new MoveSpecial("Dragon Pulse", mTypes.get(Type.DRAGON), 10, 10, 85, 100));
        mMoves.add(new MoveSpecial("Dragon Breath", mTypes.get(Type.DRAGON), 20, 20, 60, 100));

        mMoves.add(new MovePhysical("Bite ", mTypes.get(Type.DARK), 25, 25, 60, 100));
        mMoves.add(new MovePhysical("Crunch", mTypes.get(Type.DARK), 15, 15, 80, 100));
        mMoves.add(new MovePhysical("Foul Play", mTypes.get(Type.DARK), 15, 15, 95, 100));
        mMoves.add(new MovePhysical("Night Slash", mTypes.get(Type.DARK), 15, 15, 70, 100));
        mMoves.add(new MoveSpecial("Dark  Pulse", mTypes.get(Type.DARK), 15, 15, 80, 100));

        mMoves.add(new MovePhysical("Bullet Punch", mTypes.get(Type.STEEL), 30, 30, 40, 100));
        mMoves.add(new MovePhysical("Iron Head", mTypes.get(Type.STEEL), 15, 15, 80, 100));
        mMoves.add(new MovePhysical("Steel Wing", mTypes.get(Type.STEEL), 25, 25, 70, 90));
        mMoves.add(new MovePhysical("Meteor Mash", mTypes.get(Type.STEEL), 10, 10, 90, 90));
        mMoves.add(new MovePhysical("Iron Tail", mTypes.get(Type.STEEL), 15, 15, 120, 85));

        mMoves.add(new MoveSpecial("Dazzling Gleam", mTypes.get(Type.FAIRY), 10, 10, 80, 100));
        mMoves.add(new MoveSpecial("Fleur Cannon", mTypes.get(Type.FAIRY), 5, 5, 130, 85));
        mMoves.add(new MoveSpecial("Moon Blast", mTypes.get(Type.FAIRY), 15, 15, 95, 100));
        mMoves.add(new MovePhysical("Play Rough", mTypes.get(Type.FAIRY), 10, 10, 90, 90));
        mMoves.add(new MoveSpecial("Draining Kiss", mTypes.get(Type.FAIRY), 10, 10, 50, 100));
    }

    public boolean loadAllMovesApi() throws JSONException {
        String jsonMoves = null;
        try {
            jsonMoves = getStringFromApi(movesApiUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (parseJsonMoveData(jsonMoves)){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean loadAllPokemonApi() throws JSONException {
        String jsonMoves = null;
        try {
            jsonMoves = getStringFromApi(pokemonApiUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(parseJsonPokemonData(jsonMoves)){
            return true;
        }
        else{
            return false;
        }
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

        mTypes.get(1).setIcon(R.drawable.normal);
        mTypes.get(2).setIcon(R.drawable.fire);
        mTypes.get(3).setIcon(R.drawable.water);
        mTypes.get(4).setIcon(R.drawable.electric);
        mTypes.get(5).setIcon(R.drawable.grass);
        mTypes.get(6).setIcon(R.drawable.ice);
        mTypes.get(7).setIcon(R.drawable.fighting);
        mTypes.get(8).setIcon(R.drawable.poison);
        mTypes.get(9).setIcon(R.drawable.ground);
        mTypes.get(10).setIcon(R.drawable.flying);
        mTypes.get(11).setIcon(R.drawable.psychic);
        mTypes.get(12).setIcon(R.drawable.bug);
        mTypes.get(13).setIcon(R.drawable.rock);
        mTypes.get(14).setIcon(R.drawable.ghost);
        mTypes.get(15).setIcon(R.drawable.dragon);
        mTypes.get(16).setIcon(R.drawable.dark);
        mTypes.get(17).setIcon(R.drawable.steel);
        mTypes.get(18).setIcon(R.drawable.fairy);
    }

    public int getTypeIdFromString(String type) {
        if (type.equals("None")) {
            return Type.NONE;
        } else if (type.equals("Normal")) {
            return Type.NORMAL;
        } else if (type.equals("Fire")) {
            return Type.FIRE;
        } else if (type.equals("Water")) {
            return Type.WATER;
        } else if (type.equals("Electric")) {
            return Type.ELECTRIC;
        } else if (type.equals("Grass")) {
            return Type.GRASS;
        } else if (type.equals("Ice")) {
            return Type.ICE;
        } else if (type.equals("Fighting")) {
            return Type.FIGHTING;
        } else if (type.equals("Poison")) {
            return Type.POISON;
        } else if (type.equals("Ground")) {
            return Type.GROUND;
        } else if (type.equals("Flying")) {
            return Type.FLYING;
        } else if (type.equals("Psychic")) {
            return Type.PSYCHIC;
        } else if (type.equals("Bug")) {
            return Type.BUG;
        } else if (type.equals("Rock")) {
            return Type.ROCK;
        } else if (type.equals("Ghost")) {
            return Type.GHOST;
        } else if (type.equals("Dragon")) {
            return Type.DRAGON;
        } else if (type.equals("Dark")) {
            return Type.DARK;
        } else if (type.equals("Steel")) {
            return Type.STEEL;
        } else if (type.equals("Fairy")) {
            return Type.FAIRY;
        }
        return 0;
    }

    public ArrayList<Item> getAllItems() {
        return mItems;
    }
    public void setAllItems(ArrayList<Item> mItems) {
        this.mItems = mItems;
    }


    public void loadAllItems(){

        mItems.add(new ItemPotion());
        mItems.add(new ItemSuperPotion());
        mItems.add(new ItemHyperPotion());
        mItems.add(new ItemMaxPotion());
        mItems.add(new ItemRevive());
        mItems.add(new ItemMaxRevive());
        mItems.add(new ItemPokeBall());
        mItems.add(new ItemGreatBall());
        mItems.add(new ItemUltraBall());
        mItems.add(new ItemElixir());
        mItems.add(new ItemMaxElixir());

    }


    public Item getGeneratedItem(String name){
        for(int index = 0; index < mItems.size(); index++){
            if(mItems.get(index).getName().equals(name)){
                return mItems.get(index).generateCopy();
            }
        }
        return new ItemPotion(10);
    }

    public Item generateRandomItem(){
        return mItems.get(getIntegerRNG(mItems.size()));
    }

    public Move generateRandomMove(){
        return mMoves.get(getIntegerRNG(mMoves.size())).generateCopy();
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
    //HARD CODED PLAYER STATE
    public void loadPlayer(LatLng initialPosition) {
        getPlayer().setMarker(getMap().addMarker(
                new MarkerOptions().position(initialPosition).title("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.player_stand))));
    }
    public void initPlayer(){
        getPlayer().getPokemons().add(new PokemonProfile(getSpawnCount(), 15, getAllPokemons().get(3)));
        getPlayer().getPokemons().get(0).getMoves().add(generateRandomMove());
        getPlayer().getPokemons().get(0).getMoves().add(generateRandomMove());
        getPlayer().getPokemons().get(0).getMoves().add(generateRandomMove());
        getPlayer().getPokemons().get(0).getMoves().add(generateRandomMove());
        getPlayer().getPokemons().add(new PokemonProfile(getSpawnCount(), 5, getAllPokemons().get(6)));
        getPlayer().getPokemons().get(1).getMoves().add(generateRandomMove());
        getPlayer().getPokemons().get(1).getMoves().add(generateRandomMove());
        getPlayer().getPokemons().get(1).getMoves().add(generateRandomMove());
        getPlayer().getPokemons().get(1).getMoves().add(generateRandomMove());

        getPlayer().getBag().add(new ItemPotion(10));
        getPlayer().getBag().add(new ItemRevive(10));
        getPlayer().getBag().add(new ItemElixir(10));
        getPlayer().getBag().add(new ItemPokeBall(10));
        getPlayer().getBag().add(new ItemGreatBall(10));
        getPlayer().getBag().add(new ItemUltraBall(10));
    }

    public void setButtonBorder(Button btn, int color){
        btn.setBackground(getShape(color));
    }

    public void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "generation6.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public static ShapeDrawable getShape(int color){
        ShapeDrawable shapedrawable = new ShapeDrawable();
        shapedrawable.setShape(new RectShape());
        shapedrawable.getPaint().setColor(color);
        shapedrawable.getPaint().setStrokeWidth(30f);
        shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
        return shapedrawable;
    }

    public void setPokemonButton(Button btn, PokemonProfile profile, ProgressBar bar, ImageView icon){
        btn.setClickable(!profile.isEmpty());
        btn.setText(profile.getButtonString());
        if(profile.getCurrentHP() <= 0 && !profile.isEmpty()){
            setButtonBorder(btn, PokemonGoApp.DEAD_COLOR);
        }
        else{
            setButtonBorder(btn, PokemonGoApp.POKEMON_COLOR);
        }
        if(!profile.isEmpty()){
            btn.setVisibility(View.VISIBLE);
            bar.setVisibility(View.VISIBLE);
            bar.setMax(profile.getHP());
            bar.setProgress(profile.getCurrentHP());
            updateHpBarColor(profile.getCurrentHP(), profile.getHP(), bar);
            icon.setVisibility(View.VISIBLE);
            icon.setImageResource(profile.getDexData().getIcon());
        }
        else{
            btn.setVisibility(View.INVISIBLE);
            bar.setVisibility(View.INVISIBLE);
            icon.setVisibility(View.INVISIBLE);
        }
    }

    public static void updateHpBarColor(int currentHp, int maxHp, ProgressBar bar){
        if(((double)currentHp) > ((double)maxHp)/2){
            bar.getProgressDrawable().setColorFilter(
                    PokemonGoApp.BAR_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        else if(((double)currentHp) < ((double)maxHp)/2 && ((double)currentHp) > ((double)maxHp)/5){
            bar.getProgressDrawable().setColorFilter(
                    PokemonGoApp.BAG_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        else if(((double)currentHp) < ((double)maxHp)/5){
            bar.getProgressDrawable().setColorFilter(
                    PokemonGoApp.FIGHT_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    public void setAsBackButton(Button btn){
        btn.setClickable(true);
        btn.setText("BACK");
        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundColor(PokemonGoApp.BACK_COLOR);
    }

    public void setAsOkButton(Button btn){
        btn.setClickable(true);
        btn.setText("OK");
        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundColor(PokemonGoApp.RUN_COLOR);
    }

    public void setAsCancelButton(Button btn){
        btn.setClickable(true);
        btn.setText("CANCEL");
        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundColor(PokemonGoApp.FIGHT_COLOR);
    }

    /****************************************************/
    /** Pokemon Cloned CSV Encoding / Decoding Functions /
    /****************************************************/
       /*
        CSV Format Pokemon
        0<Pokemon Count (n)>; 1<Id>, 2<DexNumber>, 3<name>, 4<Gender>, 5<currentLvl>, 6<currentHp>, 7<mCurrentExp>,
        8<Move 1>, 9<Move1 PP>, ..14<Move 4>, 15<Move4 PP>, 16-21<IV Stats>, 22-27<EV Stats>, 28-33<Nature Stats> \n
        34-66<Pokemon Profile 2> \n... 32n+1-32(n+1)+1<Pokemon Profile(n)>

        CSV Format Item
        <Item 1 Name>, <Item 1 Qty> ... <Item 6 Name>, <Item 6 Qty>

         **Stat format:   <HP>, <Attack>, <Defense>, <SpAttack>, <SpDefense>, <Spd>
       */


    public String encodePokemonToCsv(){
        String csvStr = "";
        for(PokemonProfile pokemon : this.getPlayer().getPokemons()){
            if(pokemon.getDexNumber() != 0) {
                csvStr += extractPlayerPokemonData(pokemon);
                csvStr += "\n";
            }
        }
        for(PokemonProfile pokemon : this.getPlayer().getBox()){
            if(pokemon.getDexNumber() != 0){
                csvStr += extractPlayerPokemonData(pokemon);
                csvStr+= "\n";
            }
        }
        return csvStr;
    }

    public String encodeItemsToCsv(){
        String csvStr = "";
        for(Item item : this.getPlayer().getBag()) {
            if (!item.getName().isEmpty()) {
                csvStr += item.getName()+","+item.getQuantity()+"\n";
            }
        }
        return csvStr;
    }

    public void decodePokemonFromCsv(String csvStr){
        String playerPokemonData [] = csvStr.split("\n");
        int dataIndex = 0;
        int pokemonCount = 0;
        for(String pokemonData : playerPokemonData){
            Log.e("Debugging", Integer.toString(dataIndex));
            Log.e("Debugging", pokemonData);
            String playerData[] = pokemonData.split(",");
                int dexNumber = Integer.parseInt(playerData[0].trim());
                String name = playerData[1];
                int gender = Integer.parseInt(playerData[2].trim());
                int currentLvl = Integer.parseInt(playerData[3].trim());
                int currentHp = Integer.parseInt(playerData[4].trim());
                int currentExp = Integer.parseInt(playerData[5].trim());

                PokemonProfile playerPokemon = new PokemonProfile(getSpawnCount(), currentLvl, getPokemon(dexNumber));

                for (int moveCount = 0; moveCount<4; moveCount++){
                    playerPokemon.getMoves().add(findMove(playerData[6+(moveCount)*2].trim()));
                    playerPokemon.getMoves().get(moveCount).setCurrentPP(Integer.parseInt(playerData[7+(moveCount)*2].trim()));
                }

                int evHp = Integer.parseInt(playerData[14].trim());
                int evAtk = Integer.parseInt(playerData[15].trim());
                int evDef = Integer.parseInt(playerData[16].trim());
                int evSpAtk = Integer.parseInt(playerData[17].trim());
                int evSpDef = Integer.parseInt(playerData[18].trim());
                int evSpd = Integer.parseInt(playerData[19].trim());
                int ivHp = Integer.parseInt(playerData[20].trim());
                int ivAtk = Integer.parseInt(playerData[21].trim());
                int ivDef = Integer.parseInt(playerData[22].trim());
                int ivSpAtk = Integer.parseInt(playerData[23].trim());
                int ivSpDef = Integer.parseInt(playerData[24].trim());
                int ivSpd = Integer.parseInt(playerData[25].trim());
                int natureHp = Integer.parseInt(playerData[26].trim());
                int natureAtk = Integer.parseInt(playerData[27].trim());
                int natureDef = Integer.parseInt(playerData[28].trim());
                int natureSpAtk = Integer.parseInt(playerData[29].trim());
                int natureSpDef = Integer.parseInt(playerData[30].trim());
                int natureSpd = Integer.parseInt(playerData[31].trim());
                playerPokemon.setNickname(name);
                playerPokemon.setGender(gender);
                playerPokemon.setCurrentHP(currentHp);
                playerPokemon.setCurrentExp(currentExp);
                playerPokemon.setEV(new StatSet(evHp, evAtk, evDef, evSpAtk, evSpDef, evSpd));
                playerPokemon.setIV(new StatSet(ivHp, ivAtk, ivDef, ivSpAtk, ivSpDef, ivSpd));
                playerPokemon.setNature(new StatSet(natureHp, natureAtk, natureDef, natureSpAtk, natureSpDef, natureSpd));
                if(dataIndex < 6) {
                    this.getPlayer().getPokemons().add(playerPokemon);
                    Log.e("Loading",
                            this.getPlayer().getPokemons().get(pokemonCount).getNickname() + " Loaded to Profile");
                }
                else{
                    this.getPlayer().getBox().add(playerPokemon);
                    Log.e("Adding to Box", this.getPlayer().getBox().get(pokemonCount-getPlayer().getPokemons().size()).getNickname()+ "Loaded to Box");
                }
                pokemonCount++;
                dataIndex++;

        }
    }
    public void decodeItemsFromCsv(String csvStr){
        String allPlayerItems [] = csvStr.split("\n");
        int itemCount = 0;
        for (String playerItem : allPlayerItems){
            String item[] = playerItem.split(",");
            Log.e("Checker", Integer.toString(itemCount));
            Log.e("Item", item[0].trim());
            Log.e("Qty", item[1].trim());
            Log.e("DEBUG", getGeneratedItem(item[0].trim()).getName());
            this.getPlayer().getBag().add(getGeneratedItem(item[0].trim()));
            this.getPlayer().getBag().get(itemCount).setQuantity(Integer.parseInt(item[1].trim()));
            itemCount += 1;
            }
        }


    public boolean savePlayerData(){
        File targetDirectory = getFilesDir();
        if (targetDirectory.exists() == false) {
            Log.w("Warning", "Directory does not exist. Creating the directory...");
            return false;
        }
        else {
            File targetFile = new File(targetDirectory,
                    playerDataFileName);
            try {
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(targetFile, true);
                    String playerDatatoCsv = encodePokemonToCsv() +":"+ encodeItemsToCsv();
                    fos.write(playerDatatoCsv.getBytes());
                    fos.close();
                    Log.d("Save Data", "Successfully Saved Data");
                    Log.d("Save Data", playerDatatoCsv);
                }
                else{
                    FileOutputStream overwrite = new FileOutputStream(targetFile, false);
                    String playerDatatoCsv = encodePokemonToCsv() +":"+ encodeItemsToCsv();
                    overwrite.write(playerDatatoCsv.getBytes());
                    overwrite.close();
                    Log.d("Save Data", "Successfully Saved Data");
                    Log.d("Save Data", playerDatatoCsv);
                }


                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean loadPlayerDate(){
        File targetDirectory = getFilesDir();
        if (targetDirectory.exists() == false) {
            Log.w("Warning", "Directory does not exist. Creating the directory...");
            return false;
        }
        else {
            File targetFile = new File(targetDirectory,
                    playerDataFileName);
            int nBytesRead = 0;
            byte buf[] = new byte[32];
            String contentStr = "";

            try {
                InputStream is = new FileInputStream(targetFile);

                while (is.available() > 0) {
                    nBytesRead = is.read(buf, 0, 32);
                    contentStr += new String(buf, 0, nBytesRead);
                }

                is.close();

                } catch (IOException e) {
                e.printStackTrace();
                return false;
                 }
            String playerData [] = contentStr.split(":");
            int iterCount = 0;
            for(String segment : playerData){
                Log.e("Debug Decode", Integer.toString(iterCount));
                Log.e("Debug Decode", segment);
                iterCount += 1;
            }
            Log.e("Decoding Pokemon", playerData[0]);
            Log.e("Decoding Items", playerData[playerData.length-1]);
            decodePokemonFromCsv(playerData[0]);
            decodeItemsFromCsv(playerData[playerData.length-1]);
            return true;
        }
    }

    public String extractPlayerPokemonData(PokemonProfile playerPokemon){
        String dexNumber = Integer.toString(playerPokemon.getDexNumber());
        String nickName = playerPokemon.getNickname();
        String gender = Integer.toString(playerPokemon.getGender());
        String currentLvl = Integer.toString(playerPokemon.getLevel());
        String currentHp = Integer.toString(playerPokemon.getCurrentHP());
        String currentExp = Integer.toString(playerPokemon.getCurrentExp());
        String moves = "";
        for(Move move:playerPokemon.getMoves()){
            moves += move.getName() + ",";
            moves += move.getCurrentPP() + ",";
        }
        String playerPokemonIV = playerPokemon.getIV().toString();
        String playerPokemonEV = playerPokemon.getEV().toString();
        String playerPokemonNature = playerPokemon.getNature().toString();

        String extractData =  dexNumber + "," + nickName + "," + gender + "," + currentLvl
                + "," + currentHp  + "," + currentExp  + "," + moves + playerPokemonEV  + "," + playerPokemonIV
                + "," + playerPokemonNature;

        return extractData;

    }

    public void loadPokemonDetails(final Dialog dialog, Activity ctx, final PokemonProfile profile){
            setFontForContainer((RelativeLayout) dialog.findViewById(R.id.pokemon_profile_group), "generation6.ttf");
            dialog.setTitle("");

            // set the custom dialog components - text, image and button
            TextView txvDexNumber = (TextView) dialog.findViewById(R.id.txv_profile_dex);
            TextView txvName = (TextView) dialog.findViewById(R.id.txv_profile_name);
            ImageView imgType1 = (ImageView) dialog.findViewById(R.id.img_profile_type1);
            ImageView imgType2 = (ImageView) dialog.findViewById(R.id.img_profile_type2);
            TextView txvOT = (TextView) dialog.findViewById(R.id.txv_profile_ot);
            TextView txvId = (TextView) dialog.findViewById(R.id.txv_profile_id);
            TextView txvExp = (TextView) dialog.findViewById(R.id.txv_profile_exp);
            TextView txvNextLevel = (TextView) dialog.findViewById(R.id.txv_profile_nextlevel);
            ProgressBar barExp = (ProgressBar) dialog.findViewById(R.id.bar_profile_exp);
            TextView txvGender = (TextView) dialog.findViewById(R.id.txv_profile_gender);
            TextView txvLevel = (TextView) dialog.findViewById(R.id.txv_profile_level);
            ImageView imgProfile = (ImageView) dialog.findViewById(R.id.img_profile_main);
            TextView txvHp = (TextView) dialog.findViewById(R.id.txv_profile_hp);
            ProgressBar barHp = (ProgressBar) dialog.findViewById(R.id.bar_profile_hp);
            TextView txvAttack = (TextView) dialog.findViewById(R.id.txv_profile_attack);
            TextView txvDefense = (TextView) dialog.findViewById(R.id.txv_profile_defense);
            TextView txvSpAttack = (TextView) dialog.findViewById(R.id.txv_profile_sp_attack);
            TextView txvSpDefense = (TextView) dialog.findViewById(R.id.txv_profile_sp_defense);
            TextView txvSpeed = (TextView) dialog.findViewById(R.id.txv_profile_speed);
            ListView lsvMoves = (ListView) dialog.findViewById(R.id.lsv_profile_moves);

            txvDexNumber.setText(profile.getDexData().getDexNumber() + "");
            txvName.setText(profile.getDexData().getName());
            imgType1.setImageResource(profile.getDexData().getType1().getIcon());
            imgType2.setImageResource(profile.getDexData().getType2().getIcon());

            //TODO Implement when trading is possible
            txvOT.setText(mPlayer.getName());

            txvId.setText(profile.getId() + "");
            txvExp.setText(profile.getTotalExperience() + "");
            txvNextLevel.setText(profile.getExperienceNeeded() - profile.getCurrentExp() + "");
            barExp.setMax(profile.getExperienceNeeded());
            barExp.setProgress(profile.getCurrentExp());
            barExp.getProgressDrawable().setColorFilter(
                    PokemonGoApp.RUN_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
            txvGender.setText(profile.getGenderString());
            txvLevel.setText("Lv. " + profile.getLevel());
            imgProfile.setBackgroundResource(profile.getDexData().getMainImage());
            txvHp.setText(profile.getCurrentHP() + "/" + profile.getHP());
            barHp.setMax(profile.getHP());
            barHp.setProgress(profile.getCurrentHP());
            barHp.getProgressDrawable().setColorFilter(
                PokemonGoApp.BAR_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);

            txvAttack.setText(profile.getAttack() + "");
            txvDefense.setText(profile.getDefense() + "");
            txvSpAttack.setText(profile.getSpAttack() + "");
            txvSpDefense.setText(profile.getSpDefense() + "");
            txvSpeed.setText(profile.getSpeed() + "");

            MoveList mMoves = new MoveList(ctx, profile.getMoves());
            lsvMoves.setAdapter(mMoves);

            dialog.show();
    }

    public void showPokedexDialog(Context ctx, Pokemon selectedPokemon){

        final Dialog dexDialog = new Dialog(ctx);
        dexDialog.setContentView(R.layout.pokedex_dialog);
        setFontForContainer((RelativeLayout) dexDialog.findViewById(R.id.dex_group), "generation6.ttf");
        dexDialog.setTitle("");

        // set the custom dialog components - text, image and button
        TextView txvNumber = (TextView) dexDialog.findViewById(R.id.txv_dex_number);
        TextView txvName = (TextView) dexDialog.findViewById(R.id.txv_dex_name);
        ImageView imgType1 = (ImageView) dexDialog.findViewById(R.id.img_dex_type1);
        ImageView imgType2 = (ImageView) dexDialog.findViewById(R.id.img_dex_type2);
        TextView txvHeight = (TextView) dexDialog.findViewById(R.id.txv_dex_height);
        TextView txvWeight = (TextView) dexDialog.findViewById(R.id.txv_dex_weight);
        ImageView imgMain = (ImageView) dexDialog.findViewById(R.id.img_dex_main);

        txvNumber.setText(selectedPokemon.getDexNumber() + "");
        txvName.setText(selectedPokemon.getName());
        imgType1.setImageResource(selectedPokemon.getType1().getIcon());
        imgType2.setImageResource(selectedPokemon.getType2().getIcon());
        txvHeight.setText(selectedPokemon.getHeight());
        txvWeight.setText(selectedPokemon.getWeight());
        imgMain.setBackgroundResource(selectedPokemon.getMainImage());

        Button dialogButton = (Button) dexDialog.findViewById(R.id.btn_dex_ok);
        setAsOkButton(dialogButton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMusicHandler().playButtonSfx(getSFXSwitch());
                dexDialog.dismiss();
            }
        });

        dexDialog.show();
    }
}
