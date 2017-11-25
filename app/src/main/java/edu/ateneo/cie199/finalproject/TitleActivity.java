package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

public class TitleActivity extends AppCompatActivity {
    MusicHandler music;
//
//    private class MovesAPI extends AsyncTask<String, Void, Void>{
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            final PokemonGoApp app = (PokemonGoApp) getApplication();
//            String jsonMoves = app.getStringFromApi(app.getMovesApiUrl());
//            try {
//                app.parseJsonMoveData(jsonMoves, app.getAllMoves());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        final PokemonGoApp app = (PokemonGoApp) getApplication();

        //Plays music
        music = new MusicHandler();
        music.loopMusic(this, MusicHandler.MUSIC_TITLE);

        Button btnNewGame = (Button) findViewById(R.id.btn_title_new_game);
//        try {
//            MovesAPI moves = new MovesAPI();
//            moves.execute();
//        }
//        catch(Error e){
//            Log.e("Erorr", "Something went wrong");
//        }

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sound effect
                app.getMusicHandler().playSfx(TitleActivity.this, MusicHandler.SFX_SELECT);
                Intent beginMainActivityIntent = new Intent(TitleActivity.this, MainActivity.class);
                startActivity(beginMainActivityIntent);
                return;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(music == null){
            music.loopMusic(this, MusicHandler.MUSIC_TITLE);
        }
        if(!music.getMusicPlayer().isPlaying()) {
            music.getMusicPlayer().start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.getMusicPlayer().pause();
    }

    @Override
    public void onBackPressed(){

    }
}
