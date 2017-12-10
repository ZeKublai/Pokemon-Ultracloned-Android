package edu.ateneo.cie199.finalproject;


import android.content.Context;
import android.media.MediaPlayer;


/**
 * Created by John on 11/12/2017.
 * This class contains all the functions and data members
 * needed to handle the background music and sound effects.
 */

public class MusicHandler {

    public static int MUSIC_MAIN = R.raw.main_song;
    public static int MUSIC_BATTLE = R.raw.battle_song;
    public static int MUSIC_TITLE = R.raw.opening;
    public static int MUSIC_MANAGE = R.raw.manage_song;
    public static int MUSIC_INTRO = R.raw.intro_song;
    public static int SFX_SELECT = R.raw.btn_pressed;

    private MediaPlayer mMusicPlayer;
    private MediaPlayer mSfxPlayer;
    private MediaPlayer mButtonSfxPlayer;

    /**
     * Initializes the music given the context and music resource identification number.
     * @param context   The context needed to initialize the music.
     * @param musicId   The resource identification number of the music.
     */
    public void initMusic(Context context, int musicId){
        mMusicPlayer = MediaPlayer.create(context, musicId);
        mMusicPlayer.setLooping(true);
    }

    /**
     * Returns the MediaPlayer object that contains the music.
     * @return  The MediaPlayer object that contains the music.
     */
    public MediaPlayer getMusicPlayer() {
        return mMusicPlayer;
    }

    /**
     * Given a boolean, the function decides whether to
     * start playing the music in the MusicHandler or not.
     * @param musicEnabler  True if the user wants to hear the music else false.
     */
    public void playMusic(boolean musicEnabler){
        if(musicEnabler){
            mMusicPlayer.start();
        }
    }

    /**
     * Given a boolean, the function decides whether to
     * initialize and play the sound effect given or not.
     * @param context   The context needed to initialize the music.
     * @param sfxId     The resource identification number of the sound effect.
     * @param sfxEnabler    True if the user wants to hear the sound effect else false.
     */
    public void playSfx(Context context, int sfxId, boolean sfxEnabler){
        if(sfxEnabler){
            mSfxPlayer = MediaPlayer.create(context, sfxId);
            mSfxPlayer.setLooping(false);
            mSfxPlayer.start();
        }
    }

    /**
     * Initializes the button sound effect given the context
     * and sound effect resource identification number.
     * @param context
     */
    public void initButtonSfx(Context context){
        mButtonSfxPlayer = MediaPlayer.create(context, SFX_SELECT);
        mButtonSfxPlayer.setLooping(false); // Set looping
    }

    /**
     * Given a boolean, the function decides whether to
     * play the button sound effect or not.
     * @param sfxEnabler    True if the user wants to hear the sound effect else false.
     */
    public void playButtonSfx(boolean sfxEnabler){
        if(sfxEnabler){
            mButtonSfxPlayer.start();
        }
    }
}
