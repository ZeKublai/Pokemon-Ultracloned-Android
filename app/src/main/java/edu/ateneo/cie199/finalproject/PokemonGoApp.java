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
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by John, Duke and JV on 11/7/2017.
 * This is the application class that contains application wide used functions and data
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

    public void setPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

    private Player mPlayer = new Player();
    private String playerDataFileName = "player_data.csv";
    private Marker mSelectedMarker = null;
    private Marker mCurrentGoal = null;

    private boolean loadData = false;

    private MusicHandler musicHandler = new MusicHandler();
    private boolean mMusicSwitch = true;
    private boolean mSFXSwitch = true;
    private boolean mOnline = false;


    private HttpClient mHttpClient = new DefaultHttpClient();

    /*
    private String movesApiUrl = "http://192.168.43.195:8000/moves/moves";
    private String pokemonApiUrl = "http://192.168.43.195:8000/pokemon/get_all_pokemon";
    private String randPokemonApiUrl = "http://192.168.43.195:8000/pokemon/random_list";
    private String trainerApiUrl = "http://192.168.43.195:8000/trainer/get_all_trainer";
    */

    private String movesApiUrl = "https://rrttp.localtunnel.me/moves/moves";
    private String pokemonApiUrl = "https://rrttp.localtunnel.me/pokemon/get_all_pokemon";
    private String randPokemonApiUrl = "https://rrttp.localtunnel.me/pokemon/random_list";
    private String trainerApiUrl = "https://rrttp.localtunnel.me/trainer/get_all_trainer";


    private int mSpawnCount = 0;
    private ArrayList<Marker> mMarkers = new ArrayList<>();
    private ArrayList<PokéDexData> mPokemons = new ArrayList<>();
    private ArrayList<Move> mMoves = new ArrayList<>();
    private ArrayList<Type> mTypes = new ArrayList<>();
    private ArrayList<Item> mItems = new ArrayList<>();
    private ArrayList<Trainer> mTrainers = new ArrayList<>();

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

    /**
     * This function returns a random double value 0.0 to 1.0
     * @return the randomly generated double
     */
    public double getDoubleRNG(){
        Random s = new Random();
        double mDoubleSelect = (s.nextDouble());
        return mDoubleSelect;
    }

    /**
     * This function returns the mPlayer object of Application class
     * @return private mPlayer variable of Application class
     */
    public Player getPlayer(){
        return mPlayer;
    }

    /**
     * This function returns the name of the player
     * @return private string name of the player object of the Application class
     */
    public String getPlayerName(){
        return mPlayer.getName();
    }

    /**
     * This function returns the gender of the player
     * @return private gender object from the mPlayer class of the Application class
     */
    public String getPlayerGender(){
        return mPlayer.getGender().getName();
    }

    /**
     * This function returns a boolean that indicates whether to continue a save data or not
     * @return private boolean value of the application
     */
    public boolean getLoadData(){return loadData;}

    /**
     * This function sets the value for the private variable loadData
     * @param loadData the boolean value which will be the value of the Application's private variable
     */
    public void setLoadData(boolean loadData) {
        this.loadData = loadData;
    }

    /**
     * This function returns the GoogleMap object used by the Application
     * @return private GoogleMap object of the application
     */
    public GoogleMap getMap(){
        return mMap;
    }

    /**
     * This function sets the GoogleMap object of the app to the param
     * @param map The GoogleMap object to set the private variable of the application
     */
    public void setMap(GoogleMap map){
        this.mMap = map;
    }

    /**
     * This function the mode of playing the user chooses as
     * a boolean function whether online or offline
     * @return private boolean value mOnline of the application
     */
    public boolean isOnline() {
        return mOnline;
    }

    /**
     * This funcction sets the private mOnline variable to param
     * @param mOnline Boolean value that indicates Online mode or not
     */
    public void setOnline(boolean mOnline) {
        this.mOnline = mOnline;
    }

    /**
     * This function returns the mSelectedMarker object of the Application class
     * @return private Marker object of the application class
     */
    public Marker getSelectedMarker(){
        return mSelectedMarker;
    }

    /**
     * This function sets the mSelectedMarker object of the Application to the param
     * @param marker Marker object which the mSelectedMarker will be set to
     */
    public void setSelectedMarker(Marker marker){
        mSelectedMarker = marker;
    }

    /**
     * This function returns the int mSpawnCount of the Application
     * @return private int mSpawnCount of the Application class
     */
    public int getSpawnCount() {
        return mSpawnCount;
    }

    /**
     * This function sets the mSpawnCount of the Application class to the param
     * @param mSpawnCount integer which private mSpawnCount will be set to
     */
    public void setSpawnCount(int mSpawnCount) {
        this.mSpawnCount = mSpawnCount;
    }

    /**
     * This function returns the mCurrentGoal Marker of the Application
     * @return private Marker mCurrentGoal
     */
    public Marker getCurrentGoal() {
        return mCurrentGoal;
    }

    /**
     * This function sets the mCurrentGoal of the Application class to the param
     * @param marker Marker object which the mCurrentGoal will be set to
     */
    public void setCurrentGoal(Marker marker) {
        this.mCurrentGoal = marker;
    }

    /**
     * This function returns the URL for the Random Pokemon API
     * @return private String randPokemonApiUrl of the Application
     */
    public String getRandPokemonApiUrl() {return randPokemonApiUrl;}

    /**
     * This function MusicHandler object of the Application class
     * @return private MusicHandler of the musicHandler
     */
    public MusicHandler getMusicHandler() {
        return musicHandler;
    }

    /**
     * This function returns the HttpClient object of the Application class
     * @return private HttpClient mHttpClient
     */
    public HttpClient getmHttpClient(){ return mHttpClient; }

    /**
     * This function returns the boolean mMusicSwitch of the Application class
     * @return private boolean mMusicSwitch
     */
    public boolean getMusicSwitch() { return mMusicSwitch;}

    /**
     * This function returns the boolean mSFXSwitch of the Application class
     * @return private boolean mSFXSwitch
     */
    public boolean getSFXSwitch() {return mSFXSwitch;}

    /**
     * This function sets the Music On by setting mMusicSwitch to true
     */
    public void setMusicOn() {mMusicSwitch = true;}

    /**
     * This function sets the Music Off by setting mMusicSwitch to false
     */
    public void setMusicOff() {mMusicSwitch = false;}

    /**
     * This function sets the SFX On by setting mSFXSwitch to true
     */
    public void setSFXOn() {mSFXSwitch = true;}

    /**
     * This function sets to the SFX Off by setting mSFXSwitch to false
     */
    public void setSFXOff() {mSFXSwitch = false;}

    /**
     * This function gets the ArrayList of Markers mMarkers
     * @return ArrayList of Marker object mMarkers
     */
    public ArrayList<Marker> getMarkers(){
        return mMarkers;
    }

    /**
     * This function adds the param Marker object to ArrayList of Markers of Application class
     * @param marker Marker object to be added to mMarkers ArrayList
     */
    public void addMarkers(Marker marker){
        mMarkers.add(marker);
    }

    /**
     * This function deletes the param Marker object from the ArrayList mMarkers
     * @param marker The Marker Object to be deleted from the ArrayList mMarkers
     */
    public void deleteMarker(Marker marker){
        for(int index = 0; index < mMarkers.size(); index++){
            if(marker.getId() == mMarkers.get(index).getId()){
                mMarkers.remove(index);
            }
        }
    }

    /**
     * This function returns the ArrayList of Pokemon mPokemons
     * @return ArrayList of Pokemon Object mPokemons
     */
    public ArrayList<PokéDexData> getAllPokemons(){
        return mPokemons;
    }
    public void addPokemon(PokéDexData pokemon){

    /**
     * This function adds the param Pokemon object to the ArrayList mPokemons
     * @param pokemon Pokemon object to be added to ArrayList mPokemons
     */
    public void addPokemon(PokéDexData pokemon){
        mPokemons.add(pokemon);
    }

    /**
     * This function returns the Pokemon Object from ArrayList mPokemons with the name param String
     * @param title String to match the name of Pokemon from ArrayList mPokemons
     * @return Pokemon object with name equal to the param title or an empty Pokemon if no match
     */
    public PokéDexData getPokemon(String title){
        for(int index = 0; index < mPokemons.size(); index++){
            if(mPokemons.get(index).getName().equals(title)){
                return mPokemons.get(index);
            }
        }
        return new PokéDexData();
    }

    /**
     * This function returns the Pokemon Object from the ArrayList mPokemon with dexNumber of param
     * @param dexNumber integer to search from the dexNumber of the Pokemon object in mPokemon ArrayList
     * @return Pokemon Object with the matching dexNumber to the param, empty Pokemon Object if no match
     */
    public PokéDexData getPokemon(int dexNumber){
        for(int index = 0; index < mPokemons.size(); index++){
            if(mPokemons.get(index).getDexNumber() == dexNumber){
                return mPokemons.get(index);
            }
        }
        return new PokéDexData();
    }

    /**
     * This function returns the Trainer object with the name equal to the param title from the ArrayList mTrainers
     * @param title String to match from the Trainer name in mTrainers ArrayList
     * @return Trainer object with name matching param title, empty Trainer if no match
     */
    public Trainer getTrainer(String title){
        for(int index = 0; index < mTrainers.size(); index++){
            if(mTrainers.get(index).getName().equals(title)){
                return mTrainers.get(index);
            }
        }
        return new Trainer();
    }

    /**
     * This function returns the Move object with the name equal to the param title from the ArrayList mMoves
     * @param title String to match from the Move name in mMoves ArrayList
     * @return Move object with name matching param title, empty MovePhysical if no match
     */
    public Move findMove(String title){
        for(Move move : this.getAllMoves()){
            if(move.getName().equals(title)){
                return move;
            }
        }
        return new MovePhysical();
    }

    /**
     * This function returns the Item object with the name equal to the param title from the ArrayList mItems
     * @param title String to match from the Item name in mItems ArrayList
     * @return Item object with name matching param title, empty ItemPotion if no match
     */
    public Item getItem(String title){
        for(int index = 0; index < mItems.size(); index++){
            if(mItems.get(index).getName().equals(title)){
                return mItems.get(index);
            }
        }
        return new ItemPotion();
    }

    /**
     * This function returns the ArrayList of Type Object mTypes
     * @return ArrayList mTypes
     */
    public ArrayList<Type> getAllTypes(){return mTypes;}

    /**
     * This functionr eturns the ArrayList of Move Object mMoves
     * @return ArrayList mMoves
     */
    public ArrayList<Move> getAllMoves(){return mMoves;}

    /**
     * This function returns the ArrayList of Trainer Object mTrainers
     * @return ArrayList mTrainers
     */
    public ArrayList<Trainer> getTrainers() {
        return mTrainers;
    }

    /**
     * This function moves what the GoogleMap mMap displays
     * @param position a LatLng object of double latitude, and longitude to set the position of mMap
     * @param zoom a float that sets the zoom of the GoogleMap mMap display
     */
    public void moveMapCamera(LatLng position, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
    }

    /**
     * This function adds a predetermined set of data to the mTrainer ArrayList
     */
    public void loadAllTrainers(){
        mTrainers.add(new Trainer("Nekomonsterr", new Professor(), 6, "Professor", "I'm a coffee-fueled travelling researcher!",	"I will take over the world using Pokémons!", "The light inside has broken but I still work.", getPokemon(139), getPokemon(141), R.drawable.jerome_main, R.drawable.jerome_map));
        mTrainers.add(new Trainer("AsacFaleX", new Valor(), 4, "CEO", "I wanna be the very best!", "Haha! All part of the plan!", "I'll remember this!!", getPokemon("Arcanine"), getPokemon("Raichu"), R.drawable.asacfalex_main, R.drawable.asacfalex_map));
        mTrainers.add(new Trainer("CamiloTheHero", new Valor(), 2, "Shiny-less", "Open minded ka ba?", "Tara usap tayo ulit haha!", "Argh! RNGesus hates me! ", getPokemon("Alakazam"), getPokemon("Charmeleon"), R.drawable.camilothehero_main, R.drawable.camilothehero_map));
        mTrainers.add(new Trainer("Clashing08", new Instinct(), 1, "MO Prisoner", "I am the Guardian of Ignatius!", "Yaaaaay! This is my first win!", "Yameteeeeh! Kyaaah!", getPokemon("Bulbasaur"), getPokemon("Magnemite"), R.drawable.clashing08_main, R.drawable.clashing08_map));
        mTrainers.add(new Trainer("DaddyZreo", new Mystic(), 5, "Game Master", "All Mystic players should bow down to me", "Totally worth it!! <3", "BOOOOOOOOOO!!!", getPokemon("Lapras"), getPokemon("Dragonite"), R.drawable.daddyzreo_main, R.drawable.daddyzreo_map));
        mTrainers.add(new Trainer("Dorables", new Instinct(), 2, "Super Musician", "Hello po. Pahintay sa  raid. On my way!", "So cuuuuuute!", "Grrrrrr! (insert cat emoji)", getPokemon("Jynx"), getPokemon("Pikachu"), R.drawable.dorables_main, R.drawable.dorables_map));
        mTrainers.add(new Trainer("DrMacabebe", new Mystic(), 3, "The Queen", "Pineapple in Pizza is Heart!", "Is that all? Even ReesPhD can beat you!", "Remember: nagtatanim ako nang galit.", getPokemon("Dragonair"), getPokemon("Tauros"), R.drawable.drmacabebe_main, R.drawable.drmacabebe_map));
        mTrainers.add(new Trainer("FlameJacen", new Instinct(), 1, "Lugia Raider", "Hey. Is there a raid today??", "I'd rather raid than play against you again.", "This reminds me of the time I raided Lugia.", getPokemon("Squirtle"), getPokemon("Tentacool"), R.drawable.flamejacen_main, R.drawable.flamejacen_map));
        mTrainers.add(new Trainer("GaleMacBain", new Mystic(), 4, "Busy Prof", "I'm with Francis. Tara raid!", "Let's play again!!", "Argh! Balik na ako CTC.", getPokemon("Pidgeot"), getPokemon("Haunter"), R.drawable.galemacbain_main, R.drawable.galemacbain_map));
        mTrainers.add(new Trainer("GlennToT69", new Instinct(), 4, "Proud Daddy", "Let's make this quick. I have a meeting!", "I'm just at Faura if you want a rematch", "Gotta run! I'm late for a meeting!", getPokemon("Electabuzz"), getPokemon("Lapras"), R.drawable.glenntot69_main, R.drawable.glenntot69_map));
        mTrainers.add(new Trainer("LordGerold22", new Mystic(), 5, "Trigger Happy", "Sorry guys. Pero natrigger talaga ako", "I've calmed down now. Thank you guys.", "Hmph! You suck Nekomonsterr!!", getPokemon("Kabutops"), getPokemon("Blastoise"), R.drawable.lordgerold22_main, R.drawable.lordgerold22_map));
        mTrainers.add(new Trainer("MJYS1998", new Mystic(), 1, "Dolphin", "Guys, is it gay to flex on the haters?", "I caught these Pokémon on my OWN too", "How could I lose? Are you a Spooofer?!", getPokemon("Machamp"), getPokemon("Pidgey"), R.drawable.mjys1998_main, R.drawable.mjys1998_map));
        mTrainers.add(new Trainer("Naqraumor", new Valor(), 5, "Pass Hoarder", "Did you just use gay as an insult? Please don’t", "Did you really expect to win against me?", "Wait until I reach level 40!!", getPokemon("Machamp"), getPokemon("Golem"), R.drawable.naqraumor_main, R.drawable.naqraumor_map));
        mTrainers.add(new Trainer("RaniRP", new Mystic(), 4, "Rich Kid", "What's the MAGIC NUMBER??!!", "Suck it Shau!! Suck it JoshuaD!!", "Oh come on!!!", getPokemon("Wartortle"), getPokemon("Pinsir"), R.drawable.ranirp_main, R.drawable.ranirp_map));
        mTrainers.add(new Trainer("RazolDazzle", new Valor(), 1, "Stranger", "Don’t mind me. I'm just a Valor Filler Character.", "Hahaha! I expected this outcome.", "Ughhhhh. I shamed the Valor flag.", getPokemon("Growlithe"), getPokemon("Abra"), R.drawable.razoldazzle_main, R.drawable.razoldazzle_map));
        mTrainers.add(new Trainer("SavageCy", new Instinct(), 2, "Casual Player", "I wasn't able to catch Mewtwo…", "I can finally be proud of something!!", "Boohoo! Why can't I win anything?!", getPokemon("Ivysaur"), getPokemon("Kadabra"), R.drawable.savagecy_main, R.drawable.savagecy_map));
        mTrainers.add(new Trainer("SergieWonder", new Mystic(), 3, "2AM Raider", "Do you have time to talk about God our Savior?", "All part of the divine providence", "I humbly accept my defeat.", getPokemon("Rhydon"), getPokemon("Weezing"), R.drawable.sergiewonder_main, R.drawable.sergiewonder_map));
        mTrainers.add(new Trainer("TrainerKneeko", new Valor(), 3, "Shikamaru", "Wanna raid the Mother and Child gym?", "I only won due to my luck hehe!", "I should have just stayed at home…", getPokemon("Machoke"), getPokemon("Charmeleon"), R.drawable.trainerkneeko_main, R.drawable.trainerkneeko_map));
        mTrainers.add(new Trainer("TrevorStream", new Instinct(), 5, "Zapdos Fanboy", "I'm G on that Jolteon raid!", "Tsk. You should know your limitations", "You guys are borderline offensive", getPokemon("Raichu"), getPokemon("Magneton"), R.drawable.trevorstream_main, R.drawable.trevorstream_map));
        mTrainers.add(new Trainer("TrueBlueBeary", new Mystic(), 3, "Mountaineer", "Power of friendship!! Unite!!!", "You did great! Let's play again next time!", "Good Job dude! What a nice game!", getPokemon("Wartortle"), getPokemon("Graveler"), R.drawable.truebluebeary_main, R.drawable.truebluebeary_map));
        mTrainers.add(new Trainer("Vnpnpn", new Instinct(), 2, "Premium Whale", "Womp! Womp! Womp!", "Premium User is the way to go!", "Ugh… My hundred raid Raikous wasn't enough.", getPokemon("Venusaur"), getPokemon("Haunter"), R.drawable.vnpnpn_main, R.drawable.vnpnpn_map));
        mTrainers.add(new Trainer("Pepoy", new Mystic(), 1, "Anak ni sir Matt", "Boss, boss…", "Ako si kuya…. JOAO!!!", "pls! huwag mo ako iunfriend!", getPokemon("Mr. Mime"), getPokemon("Rhyhorn"), R.drawable.pepoy_main, R.drawable.pepoy_map));
        mTrainers.add(new Trainer("JoshuaDIsBack", new Valor(), 4, "Spoofer", "Guess who's baaaack?!", "I'm back boyzzz. Your bans won't work!", "Hmph! I'll destroy your gyms tonight!", getPokemon("Gengar"), getPokemon("Magmar"), R.drawable.joshuadisback_main, R.drawable.joshuadisback_map));
        mTrainers.add(new Trainer("Zsawong", new Mystic(), 3, "Hitcher", "I'm just here to hitch on the Mystic gym", "You can't beat me!!", "My defeat will be avenged!", getPokemon("Haunter"), getPokemon("Jynx"), R.drawable.zsawong_main, R.drawable.zsawong_map));
        mTrainers.add(new Trainer("RogerA121", new Valor(), 5, "Holy Father", "Hello there. I'm father Roger", "You can't win against a lvl 40 overlord", "I'm off to fly to the unknown world", getPokemon("Pidgeot"), getPokemon("Tauros"), R.drawable.rogera121_main, R.drawable.rogera121_map));
        mTrainers.add(new Trainer("Ewwe1334", new Instinct(), 2, "Chinito", "Zeph zeph zeph zeph", "Zeph zeph zeph zeph", "Zeph zeph zeph zeph", getPokemon("Charmeleon"), getPokemon("Pikachu"), R.drawable.ewwe1334_main, R.drawable.ewwe1334_map));
        mTrainers.add(new Trainer("JCNMonje", new Valor(), 4, "Firelord", "What time raid? Di pa ako naliligo hehe", "You shall not PASS! Bwahaha", "Hmph! You have what it takes to pass ELC 106.", getPokemon("Lapras"), getPokemon("Venusaur"), R.drawable.jcnmonje_main, R.drawable.jcnmonje_map));

    }

    /**
     * This function adds a predetermined set of data to the mPokemon ArrayList
     */
    public void loadAllPokemon(){
        addPokemon(new PokéDexData(1, "Bulbasaur", mTypes.get(Type.GRASS), mTypes.get(Type.POISON), "It can go for days without eating a single morsel. In the bulb on its back, it stores energy.", 190, 1, 7, 45, 49, 49, 65, 65, 45, 0, 2, R.drawable.bulbasaur_main, R.drawable.bulbasaur_back, R.drawable.bulbasaur_map, R.raw.bulbasaur, "2'4\"", "15.2 lbs"));
        addPokemon(new PokéDexData(2, "Ivysaur", mTypes.get(Type.GRASS), mTypes.get(Type.POISON), "The bulb on its back grows by drawing energy. It gives off an aroma when it is ready to bloom.", 45, 1, 7, 60, 62, 63, 80, 80, 60, 16, 3, R.drawable.ivysaur_main, R.drawable.ivysaur_back, R.drawable.ivysaur_map, R.raw.ivysaur, "3'3\"", "28.7 lbs"));
        addPokemon(new PokéDexData(3, "Venusaur", mTypes.get(Type.GRASS), mTypes.get(Type.POISON), "The flower on its back catches the sun's rays. The sunlight is then absorbed and used for energy.", 45, 1, 7, 80, 82, 83, 100, 100, 80, 32, 0, R.drawable.venusaur_main, R.drawable.venusaur_back, R.drawable.venusaur_map, R.raw.venusaur, "6'7\"", "220.5 lbs"));
        addPokemon(new PokéDexData(4, "Charmander", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "The flame at the tip of its tail makes a sound as it burns. You can only hear it in quiet places.", 190, 1, 7, 39, 52, 43, 60, 50, 65, 0, 5, R.drawable.charmander_main, R.drawable.charmander_back, R.drawable.charmander_map, R.raw.charmander, "2'0\"", "18.7 lbs"));
        addPokemon(new PokéDexData(5, "Charmeleon", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "It is very hotheaded by nature, so it constantly seeks opponents. It calms down only when it wins.", 45, 1, 7, 58, 64, 58, 80, 65, 80, 16, 6, R.drawable.charmeleon_main, R.drawable.charmeleon_back, R.drawable.charmeleon_map, R.raw.charmeleon, "3'7\"", "41.9 lbs"));
        addPokemon(new PokéDexData(6, "Charizard", mTypes.get(Type.FIRE), mTypes.get(Type.FLYING), "When expelling a blast of super hot fire, the red flame at the tip of its tail burns more intensely.", 45, 1, 7, 78, 84, 78, 109, 85, 100, 36, 0, R.drawable.charizard_main, R.drawable.charizard_back, R.drawable.charizard_map, R.raw.charizard, "5'7\"", "199.5 lbs"));
        addPokemon(new PokéDexData(7, "Squirtle", mTypes.get(Type.WATER), mTypes.get(Type.NONE), "Shoots water at prey while in the water. Withdraws into its shell when in danger.", 190, 1, 7, 44, 48, 65, 50, 64, 43, 0, 8, R.drawable.squirtle_main, R.drawable.squirtle_back, R.drawable.squirtle_map, R.raw.squirtle, "1'8\"", "19.8 lbs"));
        addPokemon(new PokéDexData(8, "Wartortle", mTypes.get(Type.WATER), mTypes.get(Type.NONE), "Often hides in water to stalk unwary prey. For swimming fast, it moves its ears to maintain balance.", 45, 1, 7, 59, 63, 80, 65, 80, 58, 16, 9, R.drawable.wartortle_main, R.drawable.wartortle_back, R.drawable.wartortle_map, R.raw.wartortle, "3'3\"", "49.6 lbs"));
        addPokemon(new PokéDexData(9, "Blastoise", mTypes.get(Type.WATER), mTypes.get(Type.NONE), "It deliberately makes itself heavy so it can withstand the recoil of the water jets it fires.", 45, 1, 7, 79, 103, 120, 135, 115, 78, 36, 0, R.drawable.blastoise_main, R.drawable.blastoise_back, R.drawable.blastoise_map, R.raw.blastoise, "5'3\"", "188.5 lbs"));
        addPokemon(new PokéDexData(16, "Pidgey", mTypes.get(Type.NORMAL), mTypes.get(Type.FLYING), "Very docile. If attacked, it will often kick up sand to protect itself rather than fight back.", 255, 1, 1, 40, 45, 40, 35, 35, 56, 0, 17, R.drawable.pidgey_main, R.drawable.pidgey_back, R.drawable.pidgey_map, R.raw.pidgey, "1'0\"", "4 lbs"));
        addPokemon(new PokéDexData(17, "Pidgeotto", mTypes.get(Type.NORMAL), mTypes.get(Type.FLYING), "It has outstanding vision. However high it flies, it is able to distinguish the movements of its prey.", 120, 1, 1, 63, 60, 55, 50, 50, 71, 18, 18, R.drawable.pidgeotto_main, R.drawable.pidgeotto_back, R.drawable.pidgeotto_map, R.raw.pidgeotto, "3'7\"", "66.1 lbs"));
        addPokemon(new PokéDexData(18, "Pidgeot", mTypes.get(Type.NORMAL), mTypes.get(Type.FLYING), "Its well-developed chest muscles make it strong enough to whip up a gusty windstorm with just a few flaps.", 45, 1, 1, 83, 80, 75, 70, 70, 101, 36, 0, R.drawable.pidgeot_main, R.drawable.pidgeot_back, R.drawable.pidgeot_map, R.raw.pidgeot, "4'11\"", "87.1 lbs"));
        addPokemon(new PokéDexData(25, "Pikachu", mTypes.get(Type.ELECTRIC), mTypes.get(Type.NONE), "It keeps its tail raised to monitor its surroundings. If you yank its tail, it will try to bite you.", 190, 1, 1, 35, 55, 40, 50, 50, 90, 0, 26, R.drawable.pikachu_main, R.drawable.pikachu_back, R.drawable.pikachu_map, R.raw.pikachu, "1'4\"", "13.2 lbs"));
        addPokemon(new PokéDexData(26, "Raichu", mTypes.get(Type.ELECTRIC), mTypes.get(Type.NONE), "When electricity builds up inside its body, it becomes feisty. It also glows in the dark.", 75, 1, 1, 60, 90, 55, 90, 80, 110, 22, 0, R.drawable.raichu_main, R.drawable.raichu_back, R.drawable.raichu_map, R.raw.raichu, "2'7\"", "66.1 lbs"));
        addPokemon(new PokéDexData(58, "Growlithe", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "It has a brave and trustworthy nature. It fearlessly stands up to bigger and stronger foes.", 190, 1, 3, 55, 70, 45, 70, 50, 60, 0, 59, R.drawable.growlithe_main, R.drawable.growlithe_back, R.drawable.growlithe_map, R.raw.growlithe, "2'4\"", "41.9 lbs"));
        addPokemon(new PokéDexData(59, "Arcanine", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "An ancient picture scroll shows that people were attracted to its movement as it ran through prairies.", 75, 1, 3, 90, 110, 80, 100, 80, 95, 22, 0, R.drawable.arcanine_main, R.drawable.arcanine_back, R.drawable.arcanine_map, R.raw.arcanine, "6'3\"", "341.7 lbs"));
        addPokemon(new PokéDexData(63, "Abra", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "Sleeps 18 hours a day. If it senses danger, it will teleport itself to safety even as it sleeps.", 200, 1, 3, 25, 20, 15, 105, 55, 90, 0, 64, R.drawable.abra_main, R.drawable.abra_back, R.drawable.abra_map, R.raw.abra, "2'11\"", "43 lbs"));
        addPokemon(new PokéDexData(64, "Kadabra", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "It possesses strong spiritual power. The more danger it faces, the stronger its psychic power.", 100, 1, 3, 40, 35, 30, 120, 70, 105, 16, 65, R.drawable.kadabra_main, R.drawable.kadabra_back, R.drawable.kadabra_map, R.raw.kadabra, "4'3\"", "124.6 lbs"));
        addPokemon(new PokéDexData(65, "Alakazam", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "Closing both its eyes heightens all its other senses. This enables it to use its abilities to their extremes.", 50, 1, 3, 55, 50, 45, 135, 95, 120, 40, 0, R.drawable.alakazam_main, R.drawable.alakazam_back, R.drawable.alakazam_map, R.raw.alakazam, "4'11\"", "105.8 lbs"));
        addPokemon(new PokéDexData(66, "Machop", mTypes.get(Type.FIGHTING), mTypes.get(Type.NONE), "Very powerful in spite of its small size. Its mastery of many types of martial arts makes it very tough.", 180, 1, 3, 70, 80, 50, 35, 35, 35, 0, 67, R.drawable.machop_main, R.drawable.machop_back, R.drawable.machop_map, R.raw.machop, "2'7\"", "43 lbs"));
        addPokemon(new PokéDexData(67, "Machoke", mTypes.get(Type.FIGHTING), mTypes.get(Type.NONE), "Its muscular body is so powerful, it must wear a power save belt to be able to regulate its motions.", 90, 1, 3, 80, 100, 70, 50, 60, 45, 28, 68, R.drawable.machoke_main, R.drawable.machoke_back, R.drawable.machoke_map, R.raw.machoke, "4'11\"", "155.4 lbs"));
        addPokemon(new PokéDexData(68, "Machamp", mTypes.get(Type.FIGHTING), mTypes.get(Type.NONE), "Using its heavy muscles, it throws powerful punches that can send the victim clear over the horizon.", 45, 1, 3, 90, 130, 80, 65, 85, 55, 40, 0, R.drawable.machamp_main, R.drawable.machamp_back, R.drawable.machamp_map, R.raw.machamp, "5'3\"", "286.6 lbs"));
        addPokemon(new PokéDexData(72, "Tentacool", mTypes.get(Type.WATER), mTypes.get(Type.POISON), "It can sometimes be found all dry and shriveled up on a beach. Toss it back into the sea to revive it.", 190, 1, 1, 40, 40, 35, 50, 100, 70, 0, 73, R.drawable.tentacool_main, R.drawable.tentacool_back, R.drawable.tentacool_map, R.raw.tentacool, "2'11\"", "100.3 lbs"));
        addPokemon(new PokéDexData(73, "Tentacruel", mTypes.get(Type.WATER), mTypes.get(Type.POISON), "The tentacles are normally kept short. On hunts, they are extended to ensnare and immobilize prey", 60, 1, 1, 80, 70, 65, 80, 120, 100, 30, 0, R.drawable.tentacruel_main, R.drawable.tentacruel_back, R.drawable.tentacruel_map, R.raw.tentacruel, "5'3\"", "121.3 lbs"));
        addPokemon(new PokéDexData(74, "Geodude", mTypes.get(Type.ROCK), mTypes.get(Type.GROUND), "Commonly found near mountain trails, etc. If you step on one by accident, it gets angry.", 255, 1, 1, 40, 80, 100, 30, 30, 20, 0, 75, R.drawable.geodude_main, R.drawable.geodude_back, R.drawable.geodude_map, R.raw.geodude, "1'4\"", "44.1 lbs"));
        addPokemon(new PokéDexData(75, "Graveler", mTypes.get(Type.ROCK), mTypes.get(Type.GROUND), "Often seen rolling down mountain trails. Obstacles are just things to roll straight over, not avoid.", 120, 1, 1, 55, 95, 115, 45, 45, 35, 25, 76, R.drawable.graveler_main, R.drawable.graveler_back, R.drawable.graveler_map, R.raw.graveler, "3'3\"", "231.5 lbs"));
        addPokemon(new PokéDexData(76, "Golem", mTypes.get(Type.ROCK), mTypes.get(Type.GROUND), "Its boulder-like body is extremely hard. It can easily withstand dynamite blasts without damage.", 45, 1, 1, 80, 120, 130, 55, 65, 45, 40, 0, R.drawable.golem_main, R.drawable.golem_back, R.drawable.golem_map, R.raw.golem, "4'7\"", "661.4 lbs"));
        addPokemon(new PokéDexData(81, "Magnemite", mTypes.get(Type.ELECTRIC), mTypes.get(Type.STEEL), "It is born with the ability to defy gravity. It floats in air on powerful electromagnetic waves.", 190, 0, 0, 25, 35, 70, 95, 55, 45, 0, 82, R.drawable.magnemite_main, R.drawable.magnemite_back, R.drawable.magnemite_map, R.raw.magnemite, "1'0\"", "13.2 lbs"));
        addPokemon(new PokéDexData(82, "Magneton", mTypes.get(Type.ELECTRIC), mTypes.get(Type.STEEL), "Generates strange radio signals. It raises the temperature by 3.6F degrees within 3,300 feet.", 60, 0, 0, 50, 60, 95, 120, 70, 70, 30, 0, R.drawable.magneton_main, R.drawable.magneton_back, R.drawable.magneton_map, R.raw.magneton, "3'3\"", "132.3 lbs"));
        addPokemon(new PokéDexData(92, "Gastly", mTypes.get(Type.GHOST), mTypes.get(Type.POISON), "Said to appear in decrepit, deserted buildings. It has no real shape as it appears to be made of a gas.", 190, 1, 1, 30, 35, 30, 100, 35, 80, 0, 93, R.drawable.gastly_main, R.drawable.gastly_back, R.drawable.gastly_map, R.raw.gastly, "4'3\"", "0.2 lbs"));
        addPokemon(new PokéDexData(93, "Haunter", mTypes.get(Type.GHOST), mTypes.get(Type.POISON), "By licking, it saps the victim's life. It causes shaking that won't stop until the victim's demise.", 90, 1, 1, 45, 50, 45, 115, 55, 95, 25, 94, R.drawable.haunter_main, R.drawable.haunter_back, R.drawable.haunter_map, R.raw.haunter, "5'3\"", "0.2 lbs"));
        addPokemon(new PokéDexData(94, "Gengar", mTypes.get(Type.GHOST), mTypes.get(Type.POISON), "To steal the life of its target, it slips into the prey's shadow and silently waits for an opportunity.", 45, 1, 1, 60, 65, 60, 130, 75, 110, 40, 0, R.drawable.gengar_main, R.drawable.gengar_back, R.drawable.gengar_map, R.raw.gengar, "4'11\"", "89.3 lbs"));
        addPokemon(new PokéDexData(109, "Koffing", mTypes.get(Type.POISON), mTypes.get(Type.NONE), "In hot places, its internal gases could expand and explode without any warning. Be very careful!", 190, 1, 1, 40, 65, 95, 60, 45, 35, 0, 110, R.drawable.koffing_main, R.drawable.koffing_back, R.drawable.koffing_map, R.raw.koffing, "2'0\"", "2.2 lbs"));
        addPokemon(new PokéDexData(110, "Weezing", mTypes.get(Type.POISON), mTypes.get(Type.NONE), "It lives and grows by absorbing dust, germs and poison gases that are contained in toxic waste and garbage.", 60, 1, 1, 65, 90, 120, 85, 70, 60, 35, 0, R.drawable.weezing_main, R.drawable.weezing_back, R.drawable.weezing_map, R.raw.weezing, "3'11\"", "20.9 lbs"));
        addPokemon(new PokéDexData(111, "Rhyhorn", mTypes.get(Type.GROUND), mTypes.get(Type.ROCK), "It is inept at turning because of its four short legs. It can only charge and run in one direction.", 120, 1, 1, 80, 85, 95, 30, 20, 25, 0, 112, R.drawable.rhyhorn_main, R.drawable.rhyhorn_back, R.drawable.rhyhorn_map, R.raw.rhyhorn, "3'3\"", "253.5 lbs"));
        addPokemon(new PokéDexData(112, "Rhydon", mTypes.get(Type.GROUND), mTypes.get(Type.ROCK), "Walks on its hind legs. Shows signs of intelligence. Its armor-like hide even repels molten lava.", 60, 1, 1, 105, 130, 120, 45, 45, 40, 42, 0, R.drawable.rhydon_main, R.drawable.rhydon_back, R.drawable.rhydon_map, R.raw.rhydon, "6'3\"", "264.6 lbs"));
        addPokemon(new PokéDexData(122, "Mr. Mime", mTypes.get(Type.PSYCHIC), mTypes.get(Type.NONE), "Always practices its pantomime act. It makes enemies believe something exists that really doesn't.", 45, 1, 1, 40, 45, 65, 100, 120, 90, 0, 0, R.drawable.mr_mime_main, R.drawable.mr_mime_back, R.drawable.mr_mime_map, R.raw.mr_mime, "4'3\"", "120.2 lbs"));
        addPokemon(new PokéDexData(124, "Jynx", mTypes.get(Type.ICE), mTypes.get(Type.PSYCHIC), "Appears to move to a rhythm of its own, as if it were dancing. It wiggles its hips as it walks.", 45, 1, 0, 65, 50, 35, 115, 95, 95, 0, 0, R.drawable.jynx_main, R.drawable.jynx_back, R.drawable.jynx_map, R.raw.jynx, "4'7\"", "89.5 lbs"));
        addPokemon(new PokéDexData(125, "Electabuzz", mTypes.get(Type.ELECTRIC), mTypes.get(Type.NONE), "Electricity runs across the surface of its body. In darkness, its entire body glows a whitish-blue.", 45, 1, 3, 65, 83, 57, 95, 85, 105, 0, 0, R.drawable.electabuzz_main, R.drawable.electabuzz_back, R.drawable.electabuzz_map, R.raw.electabuzz, "3'7\"", "66.1 lbs"));
        addPokemon(new PokéDexData(126, "Magmar", mTypes.get(Type.FIRE), mTypes.get(Type.NONE), "Born in an active volcano. Its body is always cloaked in flames, so it looks like a big ball of fire.", 45, 1, 3, 65, 95, 57, 100, 85, 93, 0, 0, R.drawable.magmar_main, R.drawable.magmar_back, R.drawable.magmar_map, R.raw.magmar, "4'3\"", "98.1 lbs"));
        addPokemon(new PokéDexData(127, "Pinsir", mTypes.get(Type.BUG), mTypes.get(Type.NONE), "Grips its prey in its pincers and squeezes hard! It can't move if it's cold, so it lives in warm places", 45, 1, 1, 65, 125, 100, 55, 70, 85, 0, 0, R.drawable.pinsir_main, R.drawable.pinsir_back, R.drawable.pinsir_map, R.raw.pinsir, "4'11\"", "121.3 lbs"));
        addPokemon(new PokéDexData(128, "Tauros", mTypes.get(Type.NORMAL), mTypes.get(Type.NONE), "They fight each other by locking horns. The herd's protector takes pride in its battle-scarred horns.", 45, 0, 1, 75, 100, 95, 40, 70, 110, 0, 0, R.drawable.tauros_main, R.drawable.tauros_back, R.drawable.tauros_map, R.raw.tauros, "4'7\"", "194.9 lbs"));
        addPokemon(new PokéDexData(131, "Lapras", mTypes.get(Type.WATER), mTypes.get(Type.ICE), "They have gentle hearts. Because they rarely fight, many have been caught. Their number has dwindled.", 45, 1, 1, 130, 85, 80, 85, 95, 60, 0, 0, R.drawable.lapras_main, R.drawable.lapras_back, R.drawable.lapras_map, R.raw.lapras, "8'2\"", "485 lbs"));
        addPokemon(new PokéDexData(138, "Omanyte", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "Although long extinct, in rare cases, it can be genetically resurrected from fossils.", 45, 1, 7, 35, 40, 100, 90, 55, 35, 0, 139, R.drawable.omanyte_main, R.drawable.omanyte_back, R.drawable.omanyte_map, R.raw.omanyte, "1'4\"", "16.5 lbs"));
        addPokemon(new PokéDexData(139, "Omastar", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "Sharp beaks ring its mouth. Its shell was too big for it to move freely, so it became extinct.", 45, 1, 7, 70, 60, 125, 115, 70, 55, 40, 0, R.drawable.omastar_main, R.drawable.omastar_back, R.drawable.omastar_map, R.raw.omastar, "3'3\"", "77.2 lbs"));
        addPokemon(new PokéDexData(140, "Kabuto", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "On rare occasions, some have been found as fossils which they became while hiding on the ocean floor.", 45, 1, 7, 30, 80, 90, 55, 45, 55, 0, 141, R.drawable.kabuto_main, R.drawable.kabuto_back, R.drawable.kabuto_map, R.raw.kabuto, "1'8\"", "25.4 lbs"));
        addPokemon(new PokéDexData(141, "Kabutops", mTypes.get(Type.ROCK), mTypes.get(Type.WATER), "In the water, it tucks in its limbs to become more compact, then it wiggles its shell to swim fast.", 45, 1, 7, 60, 115, 105, 65, 70, 80, 40, 0, R.drawable.kabutops_main, R.drawable.kabutops_back, R.drawable.kabutops_map, R.raw.kabutops, "4'3\"", "89.3 lbs"));
        addPokemon(new PokéDexData(147, "Dratini", mTypes.get(Type.DRAGON), mTypes.get(Type.NONE), "It is born large to start with. It repeatedly sheds its skin as it steadily grows longer.", 45, 1, 1, 41, 64, 45, 50, 50, 50, 0, 148, R.drawable.dratini_main, R.drawable.dratini_back, R.drawable.dratini_map, R.raw.dratini, "5'11\"", "7.3 lbs"));
        addPokemon(new PokéDexData(148, "Dragonair", mTypes.get(Type.DRAGON), mTypes.get(Type.NONE), "According to a witness, its body was surrounded by a strange aura that gave it a mystical look.", 45, 1, 1, 61, 84, 65, 70, 70, 70, 30, 149, R.drawable.dragonair_main, R.drawable.dragonair_back, R.drawable.dragonair_map, R.raw.dragonair, "13'1\"", "36.4 lbs"));
        addPokemon(new PokéDexData(149, "Dragonite", mTypes.get(Type.DRAGON), mTypes.get(Type.FLYING), "It is said that somewhere in the ocean lies an island where these gather. Only they live there.", 45, 1, 1, 91, 134, 95, 100, 100, 80, 55, 0, R.drawable.dragonite_main, R.drawable.dragonite_back, R.drawable.dragonite_map, R.raw.dragonite, "7'3\"", "463 lbs"));
    }

    /**
     * This function returns if the device is connected to a network
     * @return Network info of the ConnectivityManager object
     */
    public boolean isNetworkConnected() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    return cm.getActiveNetworkInfo() != null;
    }

    /**
     * This function executes a get request and returns the Entity response of the request
     * @param url String for GET request
     * @return response of the GET request in String
     * @throws IOException
     */
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

    /**
     * This function executes a POST request to the String param url returns a data
     * @param url String where to execute a POST request from
     * @param data Array of String that will serve as data for the encoded POST request parameters
     * @param header Array of headers that will serve as header for each data in the POST parameters
     * @return returns the response in String
     */
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

    /**
     * This function parses the param jsonData which is in JSON format and adds each entry into mMoves
     * returns true on succesful parsing, and false if not
     * @param jsonData String in JSON format of all the Moves from the server
     * @return true on successful loop through false if not
     * @throws JSONException
     */
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

    /**
     * This function parses the param jsonData which is in JSON format and adds each entry into mPokemon
     * returns true on succesful parsing, and false if not
     * @param jsonData String in JSON format of all the Pokemon from the server
     * @return boolean true on successful loop through false if not
     * @throws JSONException
     */
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
                addPokemon(new PokéDexData(dex, name, typeOne, typeTwo, desc, catchRate, femaleRatio, maleRatio, hp, atk, def, spAtk, spDef, spd, lv1Req, nextDex,
                        mainImg, backImg, mapImg, rawImg, height, weight));
//                PokéDexData m = new PokéDexData(name, type, category, maxpp, maxpp , power, acc);
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

    /**
     * This function returns the JSON object if the param is not null
     * @param jsonData String in JSON format
     * @return JSONObject  if the param is not null else it is null
     * @throws JSONException
     */
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

    /**
     * This function returns true on successfully looping through the jsonData param in JSON format
     * and adding each entry to mTrainers
     * @param jsonData String in JSON format
     * @return true if successful false if not
     * @throws JSONException
     */
    public boolean parseJsonTrainers(String jsonData) throws JSONException {
        if (jsonData != null) {
            JSONArray poiArr = new JSONArray(jsonData);
            for (int iIdx = 0; iIdx < poiArr.length(); iIdx++) {
                JSONObject placeObj = poiArr.getJSONObject(iIdx);
                String username = placeObj.getString("Username");
                String title= placeObj.getString("Title");
                String team = placeObj.getString("Team");
                int tier = Integer.parseInt(placeObj.getString("Tier"));
                String intro = placeObj.getString("Intro");
                String win = placeObj.getString("Win");
                String lose = placeObj.getString("Lose");
                String pokemon1 = placeObj.getString("Fave PokéDexData 1");
                String pokemon2 = placeObj.getString("Fave PokéDexData 2");
                int main = getResources().getIdentifier(placeObj.getString("Main"), "drawable", getPackageName());
                int map =  getResources().getIdentifier(placeObj.getString("Map"), "drawable", getPackageName());

                if(team.equals("Valor")){
                    mTrainers.add(new Trainer(username, new Valor(), tier, title, intro, win, lose, getPokemon(pokemon1), getPokemon(pokemon2), main, map));
                }
                else if (team.equals("Instinct")){
                    mTrainers.add(new Trainer(username, new Instinct(), tier, title, intro, win, lose, getPokemon(pokemon1), getPokemon(pokemon2), main, map));
                }
                else if(team.equals("Mystic")){
                    mTrainers.add(new Trainer(username, new Mystic(), tier, title, intro, win, lose, getPokemon(pokemon1), getPokemon(pokemon2), main, map));
                }
                else {
                    mTrainers.add(new Trainer("Nekomonsterr", new Professor(), 6, "Professor", "I'm a coffee-fueled travelling researcher!", "I will take over the world using Pokémons!", "The light inside has broken but I still work.", getPokemon(139), getPokemon(141), R.drawable.jerome_main, R.drawable.jerome_map));
                }


                Log.e("Test", mTrainers.get(iIdx).toString());
            }
            return true;
        }
        else{
            Log.e("Error", "json is null");
            return false;
        }
    }


    /**
     * This function adds a predetermined set of data to mMoves ArrayList
     */
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

    /**
     * This function calls the GET request function using the Moves API URL and returns true if parsing was succesful
     * @return boolean true for successful parsing of data false if parsing failed
     * @throws JSONException
     */
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

    /**
     * This function calls the GET request function on Pokemon API UrL
     * @return boolean true for succesful parsing and false if not
     * @throws JSONException
     */
    public boolean loadAllPokemonApi() throws JSONException {
        String jsonPokemon = null;
        try {
            jsonPokemon = getStringFromApi(pokemonApiUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(parseJsonPokemonData(jsonPokemon)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This function calls the GET request function from the Trainer API URL
     * @return boolean true if succesfully parsed, false if not
     * @throws JSONException
     */
    public boolean loadAllTrainerApi() throws JSONException{
        String jsonTrainer = null;
        try{
            jsonTrainer = getStringFromApi(trainerApiUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(parseJsonTrainers(jsonTrainer)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This function instantiates the data for mTypes Arraylist
     */
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
        mTypes.add(new Type("NORMAL", Type.NORMAL, Type.NORMAL_COLOR, normal, R.drawable.normal));
        mTypes.add(new Type("FIRE", Type.FIRE, Type.FIRE_COLOR, fire, R.drawable.fire));
        mTypes.add(new Type("WATER", Type.WATER, Type.WATER_COLOR, water, R.drawable.water));
        mTypes.add(new Type("ELECTRIC", Type.ELECTRIC, Type.ELECTRIC_COLOR, electric, R.drawable.electric));
        mTypes.add(new Type("GRASS", Type.GRASS, Type.GRASS_COLOR, grass, R.drawable.grass));
        mTypes.add(new Type("ICE", Type.ICE, Type.ICE_COLOR, ice, R.drawable.ice));
        mTypes.add(new Type("FIGHTING", Type.FIGHTING, Type.FIGHTING_COLOR, fighting, R.drawable.fighting));
        mTypes.add(new Type("POISON", Type.POISON, Type.POISON_COLOR, poison, R.drawable.poison));
        mTypes.add(new Type("GROUND", Type.GROUND, Type.GROUND_COLOR, ground, R.drawable.ground));
        mTypes.add(new Type("FLYING", Type.FLYING, Type.FLYING_COLOR, flying, R.drawable.flying));
        mTypes.add(new Type("PSYCHIC", Type.PSYCHIC, Type.PSYCHIC_COLOR, psychic, R.drawable.psychic));
        mTypes.add(new Type("BUG", Type.BUG, Type.BUG_COLOR, bug, R.drawable.bug));
        mTypes.add(new Type("ROCK", Type.ROCK, Type.ROCK_COLOR, rock, R.drawable.rock));
        mTypes.add(new Type("GHOST", Type.GHOST, Type.GHOST_COLOR, ghost, R.drawable.ghost));
        mTypes.add(new Type("DRAGON", Type.DRAGON, Type.DRAGON_COLOR, dragon, R.drawable.dragon));
        mTypes.add(new Type("DARK", Type.DARK, Type.DARK_COLOR, dark, R.drawable.dark));
        mTypes.add(new Type("STEEL", Type.STEEL, Type.STEEL_COLOR, steel, R.drawable.steel));
        mTypes.add(new Type("FAIRY", Type.FAIRY, Type.FAIRY_COLOR, fairy, R.drawable.fairy));

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

    /**
     * This function returns the mItems ArrayList of Item object
     * @return private ArrayList of Item mItems
     */
    public ArrayList<Item> getAllItems() {
        return mItems;
    }

    /**
     * This function instantiates the mItems ArrayList of Item object
     */
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

    /**
     * This function returns a copy of the item that matches the name with the param
     * @param name String to match to the item's name
     * @return Item object with name that match the param, ItemPotion if not
     */
    public Item getGeneratedItem(String name){
        for(int index = 0; index < mItems.size(); index++){
            if(mItems.get(index).getName().equals(name)){
                return mItems.get(index).generateCopy();
            }
        }
        return new ItemPotion(10);
    }

    /**
     * This function returns a random Item object from the ArrayList mItems
     * @return random Item object
     */
    public Item generateRandomItem(){
        return mItems.get(getIntegerRNG(mItems.size()));
    }

    /**
     * This function returns a copy of a random Move object from the mMoves ArrayList
     * @return random Move object
     */
    public Move generateRandomMove(){
        return mMoves.get(getIntegerRNG(mMoves.size())).generateCopy();
    }

    /**
     * This function sets the font style for a certain ViewGroup object from the Layout
     * @param contentLayout ViewGroup object to set the font of
     * @param fontName String name of font
     */
    public void setFontForContainer(ViewGroup contentLayout, String fontName) {
        for (int index = 0; index < contentLayout.getChildCount(); index++) {
            View view = contentLayout.getChildAt(index);
            if (view instanceof TextView)
                ((TextView)view).setTypeface(Typeface.createFromAsset(getAssets(), fontName));
            else if (view instanceof ViewGroup)
                setFontForContainer((ViewGroup) view, fontName);
        }
    }

    /**
     * This function sets a marker for the player in the param position
     * @param initialPosition LatLng object that indicates the position of marker to be set
     */
    public void loadPlayer(LatLng initialPosition) {
        getPlayer().setMarker(getMap().addMarker(
                new MarkerOptions().position(initialPosition).title("")
                        .icon(BitmapDescriptorFactory.fromResource(mPlayer.getGender().getStandImage()))));
    }

    /**
     * This function instantiates a Player object in the Application class
     */
    public void initPlayer(){
        this.mPlayer = new Player();
    }

    /**
     * This function sets the border of the Button object with the color value int color
     * @param btn Button object to set the borderColor of
     * @param color int color value to set the borderColor to
     */
    public static void setButtonBorder(Button btn, int color){
        btn.setBackground(getShape(color));
    }

    /**
     * This function applies a font to a MenuItem object from the Activity object in the param
     * @param ctx Activity that contains mi param
     * @param mi MenuItem object to apply font to
     */
    public static void applyFontToMenuItem(Activity ctx, MenuItem mi) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "generation6.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    /**
     * This fucntion creates a new ShapeDrawable object and returns that object
     * @param color int value of the color to set the ShapeDrawable object to
     * @return Shapedrawable object created
     */
    public static ShapeDrawable getShape(int color){
        ShapeDrawable shapedrawable = new ShapeDrawable();
        shapedrawable.setShape(new RectShape());
        shapedrawable.getPaint().setColor(color);
        shapedrawable.getPaint().setStrokeWidth(30f);
        shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
        return shapedrawable;
    }

    /**
     * This function sets the color of the progress bar based on the certain conditions
     * @param currentHp int HP value of the Pokemon
     * @param maxHp int total HP value of the pokemon
     * @param bar Progressbar object to set the color of
     */
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

    /**
     * This function sets a param Button object to certain properties of a Back button
     * @param btn Button object to change the properties of
     */
    public static void setAsBackButton(Button btn){
        btn.setClickable(true);
        btn.setText("BACK");
        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundColor(PokemonGoApp.BACK_COLOR);
    }

    /**
     * This function sets a param Button object to certain properties of a Ok button
     * @param btn Button object to change the properties of
     */
    public void setAsOkButton(Button btn){
        btn.setClickable(true);
        btn.setText("OK");
        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundColor(PokemonGoApp.RUN_COLOR);
    }

    /**
     * This function sets the param Button object to certain properties of a Cancel button
     * @param btn Button object to change the properties of
     */
    public static void setAsCancelButton(Button btn){
        btn.setClickable(true);
        btn.setText("CANCEL");
        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundColor(PokemonGoApp.FIGHT_COLOR);
    }

    /**
     * This function sets the param Button object to certain properties of a Switch button
     * @param btn Button object to change the properties of
     */
    public static void setAsSwitchButton(Button btn){
        btn.setClickable(true);
        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundColor(PokemonGoApp.RUN_COLOR);
        btn.setText("SWITCH");
    }

    /****************************************************/
    /** PokéDexData Cloned CSV Encoding / Decoding Functions /
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

    /**
     * This function adds a pokemon data in csv format pero new line and returns the String of all the
     * pokemon data
     * @return String containing all Pokemon data of player
     */
    public String encodePokemonToCsv(){
        String csvStr = "";
        for(PokémonProfile pokemon : this.getPlayer().getPokemons()){
            if(pokemon.getDexNumber() != 0) {
                csvStr += extractPlayerPokemonData(pokemon);
                csvStr += "\n";
            }
        }
        for(PokémonProfile pokemon : this.getPlayer().getBox()){
            if(pokemon.getDexNumber() != 0){
                csvStr += extractPlayerPokemonData(pokemon);
                csvStr+= "\n";
            }
        }
        return csvStr;
    }

    /**
     * This function adds Item data into a String, returns this String
     * @return String containing all Item data of the player
     */
    public String encodeItemsToCsv(){
        String csvStr = "";
        for(Item item : this.getPlayer().getBag()) {
            if (!item.getName().isEmpty()) {
                csvStr += item.getName()+","+item.getQuantity()+"\n";
            }
        }
        Log.e("PLAYER", this.getPlayerName());
        Log.e("PLAYER", this.getPlayerGender());
        return csvStr;
    }

    /**
     * This function returns the Name and Gender of the player int
     * @return String with ':' delimiter
     */
    public String encodePlayertoCsv(){
        return this.getPlayerName() + ":" + this.getPlayerGender();
    }

    /**
     * This function breaks down the param String into each data creating the object it is meant for
     * @param csvStr String of Pokemon data in csv format to be decoded
     */
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

                PokémonProfile playerPokemon = new PokémonProfile(getSpawnCount(), getPokemon(dexNumber), currentLvl);

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

    /**
     * This function breaks down the param String into each data creating the object it is meant for
     * @param csvStr String of Item data in csv format to be decoded
     */
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

    /**
     * This function Output streams the necessary data to be saved into a CSV file in the
     * local directory of the Android device
     * @return true on successful saving, false if not
     */
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
                    String playerDatatoCsv = encodePlayertoCsv()+":"+ encodePokemonToCsv() +":"+ encodeItemsToCsv();
                    fos.write(playerDatatoCsv.getBytes());
                    fos.close();
                    Log.d("Save Data", "Successfully Saved Data");
                    Log.d("Save Data", playerDatatoCsv);
                }
                else{
                    FileOutputStream overwrite = new FileOutputStream(targetFile, false);
                    String playerDatatoCsv = encodePlayertoCsv()+":"+ encodePokemonToCsv() +":"+ encodeItemsToCsv();
                    overwrite.write(playerDatatoCsv.getBytes());
                    overwrite.close();
                    Log.d("Save Data", "Successfully Overwritten Data");
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

    /**
     * This function checks whether the player data file exists in the android device
     * @return true if the file exists. False if not
     */
    public boolean doesPlayerDataExist(){
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
            return true;
        }
    }

    /**
     * This function retrieves the CSV file and  decodes its content creating the necessary
     * in the process
     * @return true if successful, false if not
     */
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
            Log.e("Decoding Name", playerData[0]);
            Log.e("Decoding Gender", playerData[1]);
            Log.e("Decoding Pokemon", playerData[2]);
            Log.e("Decoding Items", playerData[playerData.length-1]);
            if(playerData[1].equals("boy")){
                mPlayer.setGender(new Gender(true));
            }
            else{
                mPlayer.setGender(new Gender(false));
            }
            mPlayer.setName(playerData[0]);
            decodePokemonFromCsv(playerData[2]);
            decodeItemsFromCsv(playerData[playerData.length-1]);
            return true;
        }
    }

    /**
     * This function compiles the data of the param PokemonProfile object
     * @param playerPokemon PokemonProfile object to get data from
     * @return String of the data in csv format
     */
    public String extractPlayerPokemonData(PokémonProfile playerPokemon){
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

    /**
     * This function sets the values of the layout with the data of the Pokemon
     * @param dialog Dialog object to set the data of
     * @param ctx Activity containing the dialog object
     * @param profile Pokemon that contains the data to be displayed
     */
    public void loadPokemonDetails(final Dialog dialog, Activity ctx, final PokémonProfile profile){
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

    /**
     * This function creates a Dialog that shows the Pokedex data of the Pokemon object param
     * @param ctx Activity that will contain the Dialog
     * @param selectedPokemon Pokemon that has the data to be displayed
     */
    public void showPokedexDialog(Context ctx, PokéDexData selectedPokemon){

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
