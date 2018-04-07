package com.packtpub.libgdx.canyonbunny.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by anxi.xue on 2018/4/7.
 */

public class AudioManager {
    public static AudioManager instance = new AudioManager();
    private Music playingMusic;

    private AudioManager(){}

    public void play(Music music,boolean isLooping){
        if(music == null) return;
        stopMusic();
        this.playingMusic = music;
        this.playingMusic.setLooping(isLooping);
        this.playingMusic.setVolume(0.3f);
        this.playingMusic.play();
    }

    public void stopMusic(){
        if(playingMusic != null){
            playingMusic.stop();
        }
    }

    public void play(Sound sound){
        if(sound == null) return;
        sound.play(0.5f,1,0);
    }

    public void play(Sound sound,float volume){
        if(sound == null) return;
        sound.play(volume,1,0);
    }

    public void play(Sound sound,float volume,float pitch){
        if(sound == null) return;
        sound.play(volume,pitch,0);
    }

    /** Plays the sound. If the sound is already playing, it will be played again, concurrently.
     * @param volume the volume in the range [0,1]
     * @param pitch the pitch multiplier, 1 == default, >1 == faster, <1 == slower, the value has to be between 0.5 and 2.0
     * @param pan panning in the range -1 (full left) to 1 (full right). 0 is center position.
     * @return the id of the sound instance if successful, or -1 on failure. */
    public void play(Sound sound,float volume,float pitch,float pan){
        if(sound == null) return;
        sound.play(volume,pitch,pan);
    }
}
