package edu.ateneo.cie199.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class IntroductionActivity extends AppCompatActivity {

    private Gender mChosenGender = new Gender();
    private String mChosenName = "";
    private Pokemon mChosenStarter = new Pokemon();
    private int mCurrentMessage = 1;

    private String[] mScript = new String[28];

    MusicHandler music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        final PokemonGoApp app = (PokemonGoApp) getApplication();

        //Plays music
        music = new MusicHandler();
        music.initMusic(this, MusicHandler.MUSIC_TITLE);
        music.playMusic(app.getMusicSwitch());
        app.getMusicHandler().initButtonSfx(this);

        app.setFontForContainer((RelativeLayout)findViewById(R.id.intro_group), "generation6.ttf");
        final Button btnAction = (Button) findViewById(R.id.btn_intro_action);
        ImageView imgJerome = (ImageView) findViewById(R.id.img_intro_jerome);
        final ImageView imgOmastar = (ImageView) findViewById(R.id.img_intro_pokemon);
        final TextView txvMessage = (TextView) findViewById(R.id.txv_intro_message);
        final ImageButton imgbtnPokeBall = (ImageButton) findViewById(R.id.imgbtn_intro_pokeball);

        updateScript();

        imgJerome.setImageResource(R.drawable.jerome_intro);
        imgOmastar.setImageResource(R.drawable.jerome_pokemon);
        imgOmastar.setVisibility(View.INVISIBLE);
        txvMessage.setText(mScript[0] + "∇");
        btnAction.setBackgroundColor(PokemonGoApp.TRANSPARENT_COLOR);
        imgbtnPokeBall.setBackgroundResource(R.drawable.intro_pokeball);
        imgbtnPokeBall.setVisibility(View.INVISIBLE);
        imgbtnPokeBall.setClickable(false);

        btnAction.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                        txvMessage.setText(mScript[mCurrentMessage] + "∇");
                        if(mCurrentMessage == 5){
                            mCurrentMessage++;
                            imgbtnPokeBall.setVisibility(View.VISIBLE);
                            imgbtnPokeBall.setClickable(true);
                        }
                        else if(mCurrentMessage == 6){
                            imgbtnPokeBall.setClickable(true);
                            btnAction.setClickable(false);
                        }
                        else if(mCurrentMessage == 14){
                            btnAction.setClickable(false);

                            final Dialog genderDialog = new Dialog(IntroductionActivity.this);
                            genderDialog.setContentView(R.layout.choose_gender_dialog);
                            app.setFontForContainer((RelativeLayout) genderDialog.findViewById(R.id.gender_group), "generation6.ttf");
                            genderDialog.setTitle("");
                            genderDialog.setCancelable(false);
                            genderDialog.setCanceledOnTouchOutside(false);
                            ImageButton imgbtnBoy = (ImageButton) genderDialog.findViewById(R.id.imgbtn_choose_boy);
                            ImageButton imgbtnGirl = (ImageButton) genderDialog.findViewById(R.id.imgbtn_choose_girl);

                            // if button is clicked, close the custom dialog
                            imgbtnBoy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                                    setGender(true, txvMessage);
                                    btnAction.setClickable(true);
                                    genderDialog.dismiss();
                                }
                            });
                            imgbtnGirl.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                                    setGender(false, txvMessage);
                                    btnAction.setClickable(true);
                                    genderDialog.dismiss();
                                }
                            });

                            genderDialog.show();
                        }
                        else if(mCurrentMessage == 16){
                            btnAction.setClickable(false);

                            final Dialog nameDialog = new Dialog(IntroductionActivity.this);
                            nameDialog.setContentView(R.layout.input_name_dialog);
                            app.setFontForContainer((RelativeLayout) nameDialog.findViewById(R.id.name_group), "generation6.ttf");
                            nameDialog.setTitle("");
                            nameDialog.setCancelable(false);
                            nameDialog.setCanceledOnTouchOutside(false);

                            Button btnOk = (Button) nameDialog.findViewById(R.id.btn_name_ok);
                            final EditText edtName = (EditText) nameDialog.findViewById(R.id.edt_name_input);
                            app.setAsOkButton(btnOk);

                            btnOk.setOnClickListener(
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                                            if(edtName.getText().toString().length() > 15){
                                                edtName.setError("Name is too long");
                                            }
                                            else if(edtName.getText().toString().isEmpty()){
                                                edtName.setError("Please input a name");
                                            }
                                            else{
                                                mChosenName = edtName.getText().toString();
                                                mCurrentMessage++;
                                                mScript[17] = "OK... So, you're " + mChosenName + "?";
                                                mScript[19] = "All right, " + mChosenName + ", time to cram a life decision. Again. Maybe.";
                                                txvMessage.setText(mScript[mCurrentMessage] + "∇");
                                                mCurrentMessage = 18;
                                                btnAction.setClickable(true);
                                                nameDialog.dismiss();
                                            }
                                        }
                                    }
                            );


                            nameDialog.show();
                        }
                        else if(mCurrentMessage == 20){
                            btnAction.setClickable(false);

                            final Dialog starterDialog = new Dialog(IntroductionActivity.this);
                            starterDialog.setContentView(R.layout.choose_starter_dialog);
                            app.setFontForContainer((RelativeLayout) starterDialog.findViewById(R.id.starter_group), "generation6.ttf");
                            starterDialog.setTitle("");
                            starterDialog.setCancelable(false);
                            starterDialog.setCanceledOnTouchOutside(false);
                            ImageButton imgbtnBulbasaur = (ImageButton) starterDialog.findViewById(R.id.imgbtn_bulbasaur);
                            ImageButton imgbtnCharmander = (ImageButton) starterDialog.findViewById(R.id.imgbtn_charmander);
                            ImageButton imgbtnSquirtle = (ImageButton) starterDialog.findViewById(R.id.imgbtn_squirtle);

                            // if button is clicked, close the custom dialog
                            imgbtnBulbasaur.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                                    setStarter(1, txvMessage);
                                    btnAction.setClickable(true);
                                    starterDialog.dismiss();
                                }
                            });
                            imgbtnCharmander.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                                    setStarter(4, txvMessage);
                                    btnAction.setClickable(true);
                                    starterDialog.dismiss();
                                }
                            });
                            imgbtnSquirtle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    app.getMusicHandler().playButtonSfx(app.getSFXSwitch());
                                    setStarter(7, txvMessage);
                                    btnAction.setClickable(true);
                                    starterDialog.dismiss();
                                }
                            });
                            starterDialog.show();
                        }
                        else if(mCurrentMessage == mScript.length - 1){
                            //NEEDS TWEAKING
                            PokemonProfile starter = new PokemonProfile(0, 5, mChosenStarter);
                            starter.getMoves().add(app.generateRandomMove());
                            starter.getMoves().add(app.generateRandomMove());
                            starter.getMoves().add(app.generateRandomMove());
                            starter.getMoves().add(app.generateRandomMove());
                            app.setPlayer(new Player(mChosenGender, mChosenName, starter));
                            Intent beginMainActivityIntent = new Intent(IntroductionActivity.this, MainActivity.class);
                            startActivity(beginMainActivityIntent);
                        }
                        else {
                            mCurrentMessage++;
                        }
                    }
                }
        );

        imgbtnPokeBall.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        app.getMusicHandler().playSfx(IntroductionActivity.this, R.raw.omastar, app.getSFXSwitch());
                        mCurrentMessage = 7;
                        imgOmastar.setVisibility(View.VISIBLE);
                        imgbtnPokeBall.setVisibility(View.INVISIBLE);
                        imgbtnPokeBall.setClickable(false);
                        btnAction.setClickable(true);
                    }
                }
        );

    }

    public void updateScript(){
        mScript = new String[]{
                "Hello there! It's so very nice to meet you!",
                "Welcome to the world of Pokemon!",
                "My name is Jerome.",
                "However, everyone just calls me Nekomonsterr.",
                "This world is widely inhabited by creatures known as Pokemon.",
                "Here, I have a Poke Ball.",
                "Touch the button on the middle of the Poke Ball, if you'd please.",
                "We humans live alongside Pokemon as friends.",
                "At times we play together, and at other times we work together.",
                "Some people use their Pokemon to battle while others try to rule the world.",
                "What do I do?",
                "I am a coffee-fueled travelling researcher from parts Unown.",
                "Part of my endgame is using an army of robodogs to take over the world.",
                "Now, why don't you tell me a little bit about yourself?",
                "Are you a boy? Or are you a girl?",
                "All right, so you're a " + mChosenGender + "?",
                "Tell me, what is your name?",
                "OK... So, you're " + mChosenName + "?",
                "I'll probably forget that by next semester.",
                "All right, " + mChosenName + ", time to cram a life decision. Again. Maybe.",
                "Which starter do you want?",
                "Hmm... " + mChosenStarter + " seems to be rather happy.",
                "All righty then!",
                "I'll give " + mChosenStarter + " to you as a gift.",
                "Your very own tale of grand adventure is about to unfold.",
                "Now, go on, I have been awake now for 24 + 10 hours.",
                "Barely staggered through the work day! ",
                "The light inside has broken but I still work."
        };
    }

    public void setGender(boolean selectedGender, TextView message){
        mCurrentMessage++;
        mChosenGender = new Gender(selectedGender);
        mScript[15] = "All right, so you're a " + mChosenGender.getName() + "?";
        message.setText(mScript[mCurrentMessage] + "∇");
        mCurrentMessage = 16;
    }

    public void setStarter(int dexNumber, TextView message){
        PokemonGoApp app = (PokemonGoApp) getApplication();
        mCurrentMessage++;
        mChosenStarter = app.getPokemon(dexNumber);
        mScript[21] = "Hmm... " + mChosenStarter.getName() + " seems to be rather happy.";
        mScript[23] = "I'll give " + mChosenStarter.getName() + " to you as a gift.";
        message.setText(mScript[mCurrentMessage] + "∇");
        mCurrentMessage = 22;
    }


    @Override
    protected void onResume() {
        super.onResume();
        PokemonGoApp app = (PokemonGoApp) getApplication();
        if(music == null){
            music.initMusic(this, MusicHandler.MUSIC_TITLE);
        }
        if(!music.getMusicPlayer().isPlaying()) {
            music.playMusic(app.getMusicSwitch());
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
