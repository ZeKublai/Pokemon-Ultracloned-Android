package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends AppCompatActivity {

    MusicHandler music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        final PokemonGoApp app = (PokemonGoApp) getApplication();

        //Plays music
        music = new MusicHandler();
        music.loopMusic(this, MusicHandler.MUSIC_TITLE,app.getMusicSwitch());

        Button btnNewGame = (Button) findViewById(R.id.btn_title_new_game);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sound effect
                app.getMusicHandler().playSfx(TitleActivity.this, MusicHandler.SFX_SELECT,app.getSFXSwitch());
                Intent beginMainActivityIntent = new Intent(TitleActivity.this, MainActivity.class);
                startActivity(beginMainActivityIntent);
                return;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final PokemonGoApp app = (PokemonGoApp) getApplication();
        if(music == null){
            music.loopMusic(this, MusicHandler.MUSIC_TITLE,app.getMusicSwitch());
        }
        if(app.getMusicSwitch()) {
            music.getMusicPlayer().start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        final PokemonGoApp app = (PokemonGoApp) getApplication();
        if(app.getMusicSwitch())
        music.getMusicPlayer().pause();
    }

    @Override
    public void onBackPressed(){

    }
}
