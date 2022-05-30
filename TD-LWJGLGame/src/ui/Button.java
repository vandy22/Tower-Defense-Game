package ui;

import org.newdawn.slick.opengl.Texture;

public class Button {

	private String name;
	private Texture texture;
	private Texture[] textures;
	private int x, y, w, h;
	private boolean multipleTextures;
	private int currentButtonTexture = 0;
	
	//20
	public Button(String name, Texture texture, int x, int y, int w, int h) {
		this.name = name;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.multipleTextures = false;
	}
	
	public Button(String name, Texture texture, int x, int y) {
		this.name = name;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.w = texture.getImageWidth();
		this.h = texture.getImageHeight();
		this.multipleTextures = false;
	}
	
	public Button(String name, Texture[] textures, int x, int y) {
		this.name = name;
		this.textures = textures;
		this.x = x;
		this.y = y;
		this.w = textures[0].getImageWidth();
		this.h = textures[0].getImageWidth();
		this.multipleTextures = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public boolean isMultipleTextures() {
		return multipleTextures;
	}
	
	public Texture[] getTextures() {
		return textures;
	}
	
	public int getCurrentButtonTexture() {
		return currentButtonTexture;
	}
	
	public void setCurrentButtonTexture(int currentButtonTexture) {
		this.currentButtonTexture = currentButtonTexture;
	}
	
}
