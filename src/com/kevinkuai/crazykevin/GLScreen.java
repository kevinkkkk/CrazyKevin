package com.kevinkuai.crazykevin;

import com.kevinkuai.GLGame.GLGame;
import com.kevinkuai.GLGame.GLGraphics;
import com.kevinkuai.framework.Game;
import com.kevinkuai.framework.Screen;

public abstract class GLScreen extends Screen{

	protected final GLGraphics glGraphics;
	protected final GLGame glGame;
	
	public GLScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		glGame = (GLGame)game;
		glGraphics = ((GLGame)game).getGLGraphics();
	}
	
	

}
