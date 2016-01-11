package com.kevinkuai.crazykevin;

import com.kevinkuai.GLGame.Animation;
import com.kevinkuai.GLGame.GLGame;
import com.kevinkuai.GLGame.Texture;
import com.kevinkuai.GLGame.TextureRegion;
import com.kevinkuai.framework.Music;
import com.kevinkuai.framework.Sound;

public class Assets {

	public static Texture background;
	public static Texture level;
	public static TextureRegion backgroundRegion;
	public static TextureRegion levelRegion;
	
	public static Texture items;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoreRegion;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion spring;
	public static TextureRegion castle;
	
	public static Animation coinAnim;
	public static Animation kevinUp;
	public static Animation kevinFall;
	public static TextureRegion kevinHit;
	public static Animation badFly;
	public static TextureRegion platform;
	public static Animation brokenPlatform;
	
	public static Font font;
	
	public static Music music;
	public static Sound upSound;
	public static Sound highUpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;
	
	public static void load(GLGame game){
		background = new Texture(game, "background.png");
		backgroundRegion = new TextureRegion(background, 0,0,320,480);
		
		level = new Texture(game, "level.png");
		levelRegion = new TextureRegion(level, 0,0,320,234);
		
		items = new Texture(game, "items.png");
		mainMenu = new TextureRegion(items, 0,224,300,110);
		pauseMenu = new TextureRegion(items,224,128,192,96);
		ready = new TextureRegion(items, 320,224,192,32);
		gameOver = new TextureRegion(items,352,256,160,96);
		highScoreRegion = new TextureRegion(items, 0,257,300,110/3);
		logo = new TextureRegion(items,0,352,274,142);
		soundOff = new TextureRegion(items, 0,0,64,64);
		soundOn = new TextureRegion(items,64,0,64,64);
		arrow = new TextureRegion(items, 0,64,64,64);
		pause = new TextureRegion(items,64,64,64,64);
		
		spring = new TextureRegion(items, 128,0,32,32);
		castle = new TextureRegion(items,128,64,64,64);
		
		coinAnim = new Animation(0.2f,
								new TextureRegion(items, 128,32,32,32),
								new TextureRegion(items, 160,32,32,32),
								new TextureRegion(items, 192,32,32,32),
								new TextureRegion(items, 160,32,32,32));
		
		kevinUp = new Animation(0.2f,
								new TextureRegion(items, 0,128,32,32),
								new TextureRegion(items, 32,128,32,32));
		
		kevinFall = new Animation(0.2f,
								new TextureRegion(items, 64,128,32,32),
								new TextureRegion(items, 96,128,32,32));
		
		kevinHit = new TextureRegion(items, 128,128,32,32);
		
		badFly = new Animation(0.2f,
								new TextureRegion(items, 0,160,32,32),
								new TextureRegion(items, 32,160,32,32));
		
		platform = new TextureRegion(items, 64,160,64,16);
		brokenPlatform = new Animation(0.2f,
								new TextureRegion(items, 64,160,64,16),
								new TextureRegion(items, 64,176,64,16),
								new TextureRegion(items, 64,192,64,16),
								new TextureRegion(items, 64,208,64,16));
		
		font = new Font(items, 225,0,16,16,16);
		
		music = game.getAudio().newMusic("music.mp3");
		music.setLooping(true);
		music.setVolume(0.5f);
		if(Settings.soundEnabled)
			music.play();
		upSound = game.getAudio().newSound("up.ogg");
		highUpSound = game.getAudio().newSound("highup.ogg");
		hitSound = game.getAudio().newSound("hit.ogg");
		coinSound = game.getAudio().newSound("coin.ogg");
		clickSound = game.getAudio().newSound("click.ogg");
	
	}
	
	public static void reload(){
		background.reload();
		items.reload();
		if(Settings.soundEnabled)
			music.play();
	}
	
	public static void playSound(Sound sound){
		if(Settings.soundEnabled)
			sound.play(1);
	}
}
