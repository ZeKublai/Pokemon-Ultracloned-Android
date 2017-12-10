package edu.ateneo.cie199.finalproject;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by John, Duke and JV on 11/13/2017.
 * This activity is where the user could choose to continue an existing game or start a new game.
 */

public class TitleActivity extends AppCompatActivity {
    MusicHandler music;
    int REQUEST_DATA_LOADED = 0;

    /**
     * Initializes the Title Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        final PokemonApp app = (PokemonApp) getApplication();
        app.setFontForContainer((RelativeLayout)findViewById(R.id.title_group), "generation6.ttf");

        //Plays music
        music = new MusicHandler();
        music.initMusic(this, MusicHandler.MUSIC_TITLE);
        music.playMusic(app.getMusicSwitch());

        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();

        Button btnNewGame = (Button) findViewById(R.id.btn_title_new_game);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setLoadData(false);
                app.getMusicHandler().playSfx(
                        TitleActivity.this,
                        MusicHandler.SFX_SELECT,
                        app.getSFXSwitch()
                );
                Intent beginMainActivityIntent = new Intent(
                        TitleActivity.this,
                        LoadingScreenActivity.class
                );
                beginMainActivityIntent.putExtra("Continue?", app.getLoadData());
                startActivityForResult(beginMainActivityIntent, REQUEST_DATA_LOADED);
                return;
            }
        });

        Button btnContinueGame = (Button) findViewById(R.id.btn_title_continue_game);
        if(app.doesPlayerDataExist()){
            btnContinueGame.setClickable(true);
            btnContinueGame.setVisibility(View.VISIBLE);
        }
        else{
            btnContinueGame.setClickable(false);
            btnContinueGame.setVisibility(View.INVISIBLE);
        }

        btnContinueGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setLoadData(true);
                app.getMusicHandler().playSfx(
                        TitleActivity.this,
                        MusicHandler.SFX_SELECT,
                        app.getSFXSwitch()
                );
                Intent beginMainActivityIntent = new Intent(
                        TitleActivity.this,
                        LoadingScreenActivity.class
                );
                beginMainActivityIntent.putExtra("Continue?", app.getLoadData());
                startActivityForResult(beginMainActivityIntent, REQUEST_DATA_LOADED);
                return;
            }
        });
    }

    /**
     * Continues the music when switching Activities.
     */
    @Override
    protected void onResume() {
        super.onResume();
        PokemonApp app = (PokemonApp) getApplication();
        if(music == null){
            music.initMusic(this, MusicHandler.MUSIC_TITLE);
        }
        if(!music.getMusicPlayer().isPlaying()) {
            music.playMusic(app.getMusicSwitch());
        }
    }

    /**
     * Pause the music when switching Activities.
     */
    @Override
    protected void onPause() {
        super.onPause();
        music.getMusicPlayer().pause();
    }

    /**
     * Disables the back button.
     */
    @Override
    public void onBackPressed(){

    }

    /**
     * Mainly used to check the results and output it to the Log and Toast.
     * @param requestCode   The request code of the result.
     * @param resultCode    The result code of the request.
     * @param data          The Intent that contains the data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("INTENT FINISH", Integer.toString(requestCode));
        Log.e("INTENT RESULT", Integer.toString(resultCode));
        String msg = "";
        if(requestCode == REQUEST_DATA_LOADED && resultCode == RESULT_CANCELED ) {
             msg = data.getStringExtra("MSG");
        }
        Toast.makeText(TitleActivity.this, msg, Toast.LENGTH_LONG).show();
    }

}
