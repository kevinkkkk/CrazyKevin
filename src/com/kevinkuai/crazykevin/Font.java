package com.kevinkuai.crazykevin;

import com.kevinkuai.GLGame.SpriteBatcher;
import com.kevinkuai.GLGame.Texture;
import com.kevinkuai.GLGame.TextureRegion;

public class Font {
	public final Texture texture;
	public final int glyWidth;
	public final int glyHeight;
	public final TextureRegion[] glyphs = new TextureRegion[96];
	
	public Font(Texture texture, int offsetX, int offsetY, 
			int glyPerRow, int glyWidth, int glyHeight){
		this.glyWidth = glyWidth;
		this.glyHeight = glyHeight;
		this.texture = texture;
		int x = offsetX;
		int y = offsetY;
		for (int i =0; i<96; i++){
			glyphs[i]=new TextureRegion(texture, x,y,glyWidth,glyHeight);
			x += glyWidth;
			if(x == offsetX+glyPerRow*glyWidth){
				x = offsetX;
				y += glyHeight;
			}
		}
	}
	
	public void drawText(SpriteBatcher batcher, String text, float x, float y){
		int len = text.length();
		for (int i = 0; i<len; i++){
			int c = text.charAt(i)-' ';
			if(c<0 || c>(glyphs.length-1))
				continue;
			
			TextureRegion glyph = glyphs[c];
			batcher.drawSprite(x, y, glyWidth, glyHeight, glyph);
			x += glyWidth;
		}
	}

}
