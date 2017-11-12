package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        //Plays music
        MusicBackground.OpeningSong(this);

        Button btnNewGame = (Button) findViewById(R.id.btn_title_new_game);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sound effect
                MusicBackground.ButtonPressed(TitleActivity.this);
                Intent beginMainActivityIntent = new Intent(TitleActivity.this, MainActivity.class);
                startActivity(beginMainActivityIntent);
                return;
            }
        });
    }

    @Override
    public void onBackPressed(){

    }
}
