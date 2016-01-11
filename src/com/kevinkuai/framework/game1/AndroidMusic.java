package com.kevinkuai.framework.game1;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.kevinkuai.framework.Music;

public class AndroidMusic implements Music, OnCompletionListener {
	
	MediaPlayer mediaPlayer;
	boolean isPrepared =false;

	public AndroidMusic(AssetFileDescriptor assetDescriptor) {
		// TODO Auto-generated constructor stub
		mediaPlayer = new MediaPlayer();
		try{
			mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(), assetDescriptor.getStartOffset(),
					assetDescriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
			
		}catch (Exception e){
			throw new RuntimeException ("Couldn't load Music");
		}
		
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		if (mediaPlayer.isPlaying())
			return;
		try{
			synchronized (this){
				if (!isPrepared)
					mediaPlayer.prepare();
				mediaPlayer.start();
			}
		}catch (IllegalStateException | IOException e){
			e.printStackTrace();
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		mediaPlayer.stop();
		synchronized (this){
			isPrepared = false;
		}

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		mediaPlayer.pause();

	}

	@Override
	public void setLooping(boolean looping) {
		// TODO Auto-generated method stub
		mediaPlayer.setLooping(isLooping());

	}

	@Override
	public void setVolume(float volume) {
		// TODO Auto-generated method stub
		mediaPlayer.setVolume(volume, volume);

	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return mediaPlayer.isPlaying();
	}

	@Override
	public boolean isStopped() {
		// TODO Auto-generated method stub
		return !isPrepared;
	}

	@Override
	public boolean isLooping() {
		// TODO Auto-generated method stub
		return mediaPlayer.isLooping();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if (mediaPlayer.isPlaying())
			mediaPlayer.stop();
		mediaPlayer.release();

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		synchronized (this){
			isPrepared = false;
		}
		
	}

}
