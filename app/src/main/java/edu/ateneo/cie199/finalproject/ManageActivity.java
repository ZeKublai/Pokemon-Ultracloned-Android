package edu.ateneo.cie199.finalproject;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ManageActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;

    Button btnHeal;
    Button btnRevive;
    Button btnRestore;

    ImageView icon1;
    ImageView icon2;
    ImageView icon3;
    ImageView icon4;
    ImageView icon5;
    ImageView icon6;

    ImageView iconHeal;
    ImageView iconRevive;
    ImageView iconRestore;

    ProgressBar bar1;
    ProgressBar bar2;
    ProgressBar bar3;
    ProgressBar bar4;
    ProgressBar bar5;
    ProgressBar bar6;

    TextView txvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setFontForContainer((RelativeLayout) findViewById(R.id.manager_group), "generation6.ttf");

        btn1 = (Button) findViewById(R.id.btn_pokemon1);
        btn2 = (Button) findViewById(R.id.btn_pokemon2);
        btn3 = (Button) findViewById(R.id.btn_pokemon3);
        btn4 = (Button) findViewById(R.id.btn_pokemon4);
        btn5 = (Button) findViewById(R.id.btn_pokemon5);
        btn6 = (Button) findViewById(R.id.btn_pokemon6);

        btnHeal = (Button) findViewById(R.id.btn_pokemon_heal);
        btnRevive = (Button) findViewById(R.id.btn_pokemon_revive);
        btnRestore = (Button) findViewById(R.id.btn_pokemon_restore);

        icon1 = (ImageView) findViewById(R.id.img_pokemon1);
        icon2 = (ImageView) findViewById(R.id.img_pokemon2);
        icon3 = (ImageView) findViewById(R.id.img_pokemon3);
        icon4 = (ImageView) findViewById(R.id.img_pokemon4);
        icon5 = (ImageView) findViewById(R.id.img_pokemon5);
        icon6 = (ImageView) findViewById(R.id.img_pokemon6);

        iconHeal = (ImageView) findViewById(R.id.img_pokemon_heal);
        iconRevive = (ImageView) findViewById(R.id.img_pokemon_revive);
        iconRestore = (ImageView) findViewById(R.id.img_pokemon_restore);

        bar1 = (ProgressBar) findViewById(R.id.bar_pokemon1);
        bar2 = (ProgressBar) findViewById(R.id.bar_pokemon2);
        bar3 = (ProgressBar) findViewById(R.id.bar_pokemon3);
        bar4 = (ProgressBar) findViewById(R.id.bar_pokemon4);
        bar5 = (ProgressBar) findViewById(R.id.bar_pokemon5);
        bar6 = (ProgressBar) findViewById(R.id.bar_pokemon6);

        txvMessage = (TextView) findViewById(R.id.txv_pokemon_message);

        txvMessage.setText("");

        Button btnBack = (Button) findViewById(R.id.btn_pokemon_back);
        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mainActivityIntent = new Intent(ManageActivity.this, MainActivity.class);
                        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(mainActivityIntent, 0);
                        finish();
                    }
                }
        );

        updatePokemons();
        updateItems();
    }

    public void updatePokemons(){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setPokemonButton(btn1, app.getPlayer().getPokemons()[0], bar1, icon1);
        app.setPokemonButton(btn2, app.getPlayer().getPokemons()[1], bar2, icon2);
        app.setPokemonButton(btn3, app.getPlayer().getPokemons()[2], bar3, icon3);
        app.setPokemonButton(btn4, app.getPlayer().getPokemons()[3], bar4, icon4);
        app.setPokemonButton(btn5, app.getPlayer().getPokemons()[4], bar5, icon5);
        app.setPokemonButton(btn6, app.getPlayer().getPokemons()[5], bar6, icon6);
    }

    public void updateItems(){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        app.setBagButton(btnHeal, app.getPlayer().getBag()[0], iconHeal);
        app.setBagButton(btnRevive, app.getPlayer().getBag()[1], iconRevive);
        app.setBagButton(btnRestore, app.getPlayer().getBag()[2], iconRestore);
    }
}
