package com.kevinkuai.framework.game1;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;


import com.kevinkuai.framework.Audio;
import com.kevinkuai.framework.Music;
import com.kevinkuai.framework.Sound;

public class AndroidAudio implements Audio{
	
	AssetManager assets;
	SoundPool soundPool;
	
	@SuppressWarnings("deprecation")
	public AndroidAudio(Activity activity){
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20,AudioManager.STREAM_MUSIC, 0);
	}

	@Override
	public Music newMusic(String fileName) {
		// TODO Auto-generated method stub
		try{
		AssetFileDescriptor assetDescriptor = assets.openFd(fileName);
		return new AndroidMusic(assetDescriptor);
		}catch (IOException e){
			throw new RuntimeException("Couldn't load music '"+fileName+"'");
		}
		
	}

	@Override
	public Sound newSound(String fileName) {
		// TODO Auto-generated method stub
		try{
			AssetFileDescriptor assetDescriptor = assets.openFd(fileName);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
			}catch (IOException e){
				throw new RuntimeException("Couldn't load sound '"+fileName+"'");
			}
	}

}
