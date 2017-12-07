package edu.ateneo.cie199.finalproject;


import android.content.Context;
import android.media.MediaPlayer;


/**
 * Created by John on 11/12/2017.
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

    public void initMusic(Context ctx, int musicId){
        mMusicPlayer = MediaPlayer.create(ctx, musicId);
        mMusicPlayer.setLooping(true);
    }

    public MediaPlayer getMusicPlayer() {
        return mMusicPlayer;
    }

    public void setMusicVolume(boolean musicEnabler){
        if (musicEnabler) {mMusicPlayer.setVolume(20, 20);}
        else {mMusicPlayer.setVolume(0, 0);}
    }

    public void playMusic(boolean musicEnabler){
        if(musicEnabler){
            mMusicPlayer.start();
        }
    }

    public void setButtonVolume(boolean sfxEnabler){
        if (sfxEnabler) {mButtonSfxPlayer.setVolume(150, 150);}
        else {mButtonSfxPlayer.setVolume(0, 0);}
    }

    public void playSfx(Context ctx, int sfxId, boolean sfxEnabler){
        if(sfxEnabler){
            mSfxPlayer = MediaPlayer.create(ctx, sfxId);
            mSfxPlayer.setLooping(false);
            mSfxPlayer.start();
        }
    }

    public void stopMusic(){
        mMusicPlayer.release();
        mMusicPlayer = null;
    }

    public void initButtonSfx(Context ctx){
        mButtonSfxPlayer = MediaPlayer.create(ctx, R.raw.btn_pressed);
        mButtonSfxPlayer.setLooping(false); // Set looping
    }

    public void playButtonSfx(boolean sfxEnabler){
        if(sfxEnabler){
            mButtonSfxPlayer.start();
        }
    }
}
