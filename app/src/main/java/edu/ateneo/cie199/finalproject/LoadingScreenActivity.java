package edu.ateneo.cie199.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class LoadingScreenActivity extends AppCompatActivity {
    private boolean dataLoaded = false;
    private boolean movesApiState = false;
    private boolean pokemonApiState = false;
    private boolean loadData = true;

    private class APIData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            final PokemonGoApp app = (PokemonGoApp) getApplication();
            app.loadAllPokemonTypes();
            try {
                if(app.loadAllMovesApi()){
                    Log.d("Load Moves", "LOADING MOVES..");
                    movesApiState = true;
                }
                else{
                    movesApiState= false;
                }
            } catch (JSONException e) {
                Log.e("PARSE ERROR", "FAILED TO PARSE JSON MOVE DATA");
                e.printStackTrace();
            }
            return null;
        }
    }

    private class PokemonAPI extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            final PokemonGoApp app = (PokemonGoApp) getApplication();
            try {
                if(app.loadAllPokemonApi()){
                    Log.d("Load Pokemons", "LOADING PKMN...");
                    pokemonApiState = true;
                }
                else{
                    pokemonApiState = false;
                }
            } catch (JSONException e) {
                Log.e("Parsing err", "Failed to save json PokéDexData data");
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) throws Error {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loading_screen);
        final Dialog dialog = new Dialog(LoadingScreenActivity.this);
        dialog.setContentView(R.layout.activity_loading_screen);


        final ProgressBar loadBar = (ProgressBar) dialog.findViewById(R.id.progBar_load);

        final PokemonGoApp app = (PokemonGoApp) getApplication();

        Intent recvdIntent = getIntent();
        loadData = recvdIntent.getBooleanExtra("Continue?", false);
        if (app.isNetworkConnected()) {
            try {
                APIData data = new APIData();
                data.execute();
                Log.e("BAR PROG", Double.toString(loadBar.getMax() / 4));
                loadBar.setMax(100);
                loadBar.setProgress((int) Math.floor(loadBar.getMax() / 4));
                PokemonAPI pokeData = new PokemonAPI();
                pokeData.execute();
                loadBar.setMax(76);
                loadBar.setProgress((int) Math.floor(loadBar.getMax() * 2 / 4));
                app.loadAllItems();
                loadBar.setMax(56);
                loadBar.setProgress((int) Math.floor(loadBar.getMax() * 3 / 4));
                app.loadAllPokemonTypes();
                app.loadAllTrainers();
            } catch (Error e) {
                Log.e("Error", "Unable to load moves");
            }


            final Handler handler = new Handler();
            final Runnable delay = new Runnable() {
                @Override
                public void run() {

                    if(pokemonApiState && movesApiState) {
                        if (loadData) {
                            app.loadPlayerDate();
                            loadBar.setMax(100);
                            loadBar.setProgress(loadBar.getMax());
                        } else {
                            loadBar.setMax(75);
                            loadBar.setProgress(loadBar.getMax());
                            app.initPlayer();
                        }

                        if (loadBar.getProgress() == loadBar.getMax()) {
                            if (loadData) {
                                Intent beginMainActivity = new Intent(LoadingScreenActivity.this, MainActivity.class);
                                setResult(RESULT_OK);
                                startActivity(beginMainActivity);
                            } else {
                                Intent beginIntroductionActivity = new Intent(LoadingScreenActivity.this, IntroductionActivity.class);
                                setResult(RESULT_OK);
                                startActivity(beginIntroductionActivity);
                            }
                            app.setOnline(true);
                        } else {
                            Log.e("Error Loading", "There was an error loading the data");
                            setResult(RESULT_CANCELED, new Intent().putExtra("MSG", "Data was not properly loaded, please retry."));
                            getFailedDialog("Data was not properly loaded, please retry.");
                        }
                    }
                    else{
                        setResult(RESULT_CANCELED, new Intent().putExtra("MSG", "Data was not properly loaded, please retry."));
                        getFailedDialog("Data was not properly loaded, please retry.");
                    }
                }
            };
            handler.postDelayed(delay, 10000);
        }
        else{
            Toast.makeText(LoadingScreenActivity.this, "Your device is not connected to the internet.", Toast.LENGTH_LONG).show();
            getFailedDialog("Your device is not connected to the internet.");
        }
    }

    public void getFailedDialog(String setResult){
        final PokemonGoApp app = (PokemonGoApp) getApplication();
        final Dialog dialog = new Dialog(LoadingScreenActivity.this);
        dialog.setContentView(R.layout.got_item_dialog);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        // set the custom dialog components - text, image and button
        TextView txvDialog = (TextView) dialog.findViewById(R.id.txv_dialog_message);
        txvDialog.setText(setResult + " Would you like to play offline?");
        txvDialog.setTypeface(Typeface.createFromAsset(getAssets(), "generation6.ttf"));
        ImageView dialogImage = (ImageView) dialog.findViewById(R.id.img_dialog);
        dialogImage.setImageResource(R.drawable.mjys1998_main);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog_ok);
        dialogButton.setTypeface(Typeface.createFromAsset(getAssets(), "generation6.ttf"));
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.getMusicHandler().playSfx(LoadingScreenActivity.this, MusicHandler.SFX_SELECT, app.getSFXSwitch());
                app.loadAllItems();
                app.loadAllPokemonTypes();
                app.loadAllPokemonMoves();
                app.loadAllPokemon();
                app.loadAllTrainers();
                app.setOnline(false);
                if (loadData) {
                    app.loadPlayerDate();
                    Intent beginMainActivity = new Intent(LoadingScreenActivity.this, MainActivity.class);
                    setResult(RESULT_OK);
                    startActivity(beginMainActivity);
                } else {
                    app.initPlayer();
                    Intent beginIntroductionActivity = new Intent(LoadingScreenActivity.this, IntroductionActivity.class);
                    setResult(RESULT_OK);
                    startActivity(beginIntroductionActivity);
                }

                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed(){

    }
}