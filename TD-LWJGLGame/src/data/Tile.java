package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class Tile {

	private float x, y, w, h;
	private Texture texture;
	private TileType type;
	
	public Tile(float x, float y, float w, float h, TileType type) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.type = type;
		this.texture = loadTexture(type.textureName);
	}
	
	public void draw() {
		drawQuadTex(texture, x, y, w, h);
		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}
	
	
}
