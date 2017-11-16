package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final PokemonGoApp app = (PokemonGoApp) getApplication();
        final Button btnBack = (Button) findViewById(R.id.btn_back);
        final Switch MusicEnabled = (Switch) findViewById(R.id.switch_music);
        final Switch SFXEnabled = (Switch) findViewById(R.id.switch_sfx);

        MusicEnabled.setChecked(app.getMusicSwitch());
        SFXEnabled.setChecked(app.getSFXSwitch());

        MusicEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    app.MusicOn();
                }
                else app.MusicOff();
            }
        });

        SFXEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    app.SFXOn();
                }
                else app.SFXOff();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                app.getMusicHandler().playSfx(SettingsActivity.this, MusicHandler.SFX_SELECT,app.getSFXSwitch());
                BackToMain();
            }
        });

    }

    private void BackToMain(){
        Intent MainActivityIntent = new Intent(SettingsActivity.this, MainActivity.class);
        finish();
    }
}
