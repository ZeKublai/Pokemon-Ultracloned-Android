package edu.ateneo.cie199.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/13/2017.
 * This class handles different interaction about the caught Pokemons.
 * It allows release, evolve, switch, view Pokemon details and many more.
 */

public class ManageActivity extends AppCompatActivity {
    ArrayList<PokemonButton> btnPokemons = new ArrayList<>();
    MusicHandler music;
    Manager manager;

    /**
     * Initialize the Manage Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        final PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setFontForContainer((RelativeLayout) findViewById(R.id.manager_group), "generation6.ttf");

        music = new MusicHandler();
        music.initMusic(ManageActivity.this, MusicHandler.MUSIC_MANAGE);
        music.playMusic(app.getMusicSwitch());
        app.getMusicHandler().initButtonSfx(this);

        manager = new Manager();
        manager.setPlayer(app.getPlayer());
        manager.setPokemonAdapter(new PokemonList(ManageActivity.this, app.getPlayer().getBox()));
        manager.setItemAdapter(new ItemList(ManageActivity.this, app.getPlayer().getBag()));

        final ListView lsvPokemons = (ListView)findViewById(R.id.lsv_pokemon_box);
        final ListView lsvItems = (ListView) findViewById(R.id.lsv_player_bag);

        lsvPokemons.setAdapter(manager.getPokemonAdapter());
        lsvItems.setAdapter(manager.getItemAdapter());

        app.setFontForContainer((ListView) findViewById(R.id.lsv_pokemon_box), "generation6.ttf");
        app.setFontForContainer((ListView) findViewById(R.id.lsv_player_bag), "generation6.ttf");

        btnPokemons.add(new PokemonButton((Button) findViewById(R.id.btn_pokemon1), (ImageView) findViewById(R.id.img_pokemon1), (ProgressBar) findViewById(R.id.bar_pokemon1)));
        btnPokemons.add(new PokemonButton((Button) findViewById(R.id.btn_pokemon2), (ImageView) findViewById(R.id.img_pokemon2), (ProgressBar) findViewById(R.id.bar_pokemon2)));
        btnPokemons.add(new PokemonButton((Button) findViewById(R.id.btn_pokemon3), (ImageView) findViewById(R.id.img_pokemon3), (ProgressBar) findViewById(R.id.bar_pokemon3)));
        btnPokemons.add(new PokemonButton((Button) findViewById(R.id.btn_pokemon4), (ImageView) findViewById(R.id.img_pokemon4), (ProgressBar) findViewById(R.id.bar_pokemon4)));
        btnPokemons.add(new PokemonButton((Button) findViewById(R.id.btn_pokemon5), (ImageView) findViewById(R.id.img_pokemon5), (ProgressBar) findViewById(R.id.bar_pokemon5)));
        btnPokemons.add(new PokemonButton((Button) findViewById(R.id.btn_pokemon6), (ImageView) findViewById(R.id.img_pokemon6), (ProgressBar) findViewById(R.id.bar_pokemon6)));

        manager.setState(new ManagerMainState(btnPokemons,
                (Button) findViewById(R.id.btn_pokemon_back),
                (Button) findViewById(R.id.btn_pokemon_switch),
                (TextView) findViewById(R.id.txv_pokemon_message), manager));

        for(int index = 0; index < manager.getPlayer().getPokemons().size(); index++){
            final int pos = index;
            btnPokemons.get(index).getButton().setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                            manager.getState().executePokemonButton(ManageActivity.this, app, pos);
                        }
                    }
            );
        }

        lsvPokemons.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        manager.getState().executePokemonListView(ManageActivity.this, view, app, pos);
                    }
                }
        );

        lsvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        manager.getState().executeItemListView(pos);
                    }
                }
        );

        manager.getState().getBackButton().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        if(manager.getState() instanceof ManagerUseItemState){
                            manager.getState().executeBackButton();
                        }
                        else{
                            Intent mainActivityIntent = new Intent(ManageActivity.this, MainActivity.class);
                            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivityIfNeeded(mainActivityIntent, 0);
                            finish();
                        }

                    }
                }
        );
        manager.getState().getSwitchButton().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        manager.getState().executeSwitchButton();
                    }
                }
        );
    }

    /**
     * Disables the back button
     */
    @Override
    public void onBackPressed(){

    }

    /**
     * Continues the music when switching activities
     */
    @Override
    protected void onResume() {
        super.onResume();
        PokemonGoApp app = (PokemonGoApp) getApplication();
        if(music == null){
            music.initMusic(this, MusicHandler.MUSIC_MANAGE);
        }
        if(!music.getMusicPlayer().isPlaying()) {
            music.playMusic(app.getMusicSwitch());
        }
    }

    /**
     * Pause the music when switching activities
     */
    @Override
    protected void onPause() {
        super.onPause();
        music.getMusicPlayer().pause();
        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.savePlayerData();
    }
}
