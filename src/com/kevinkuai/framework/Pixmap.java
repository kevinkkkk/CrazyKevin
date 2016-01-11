package com.kevinkuai.framework;

import com.kevinkuai.framework.Graphics.PixmapFormat;

public interface Pixmap {
	
	public int getWidth();
	public int getHeight();
	public PixmapFormat getFormat();
	public void dispose();

}
