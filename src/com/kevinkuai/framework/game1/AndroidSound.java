package com.kevinkuai.framework.game1;

import android.media.SoundPool;

import com.kevinkuai.framework.Sound;

public class AndroidSound implements Sound {
	
	int soundId;
	SoundPool soundPool; 

	public AndroidSound(SoundPool soundPool, int soundId) {
		// TODO Auto-generated constructor stub
		this.soundId = soundId;
		this.soundPool = soundPool;
	}

	@Override
	public void play(float volume) {
		// TODO Auto-generated method stub
		soundPool.play(soundId, volume, volume, 0, 0, 1);

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		soundPool.unload(soundId);

	}

}
