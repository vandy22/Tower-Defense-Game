package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class Tile {

	private float x, y;
	private int w, h;
	private Texture texture;
	private TileType type;
	private boolean occupied;
	
	public Tile(float x, float y, int w, int h, TileType type) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.type = type;
		this.texture = loadTexture(type.textureName);
		if(type.buildable)
			occupied = false;
		else
			occupied = true;
	}
	
	public void draw() {
		drawQuadTex(texture, x, y, w, h);
		
	}

	public float getX() {
		return x;
	}
	
	public int getXPlace() {
		return (int) x / TILE_SIZE;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	
	public int getYPlace() {
		return (int) y / TILE_SIZE;
	}

	public void setY(float y) {
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
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	
}
