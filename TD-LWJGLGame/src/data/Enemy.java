package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
import static helpers.Clock.*;

public class Enemy {
	private int w, h, health;
	private float speed, x, y;
	Texture texture;
	private Tile startTile;
	private boolean first = true;
	
	public Enemy(Texture texture, Tile startTile, int w, int h, float speed) {
		this.texture = texture;
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.w = w;
		this.h = h;
		this.speed = speed;
	}
	
	public void draw() {
		drawQuadTex(texture, x, y, w, h);
	}
	
	public void update() {
		if(first)
			first = false;
		else
			x += delta() * speed;
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
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

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
	
}
