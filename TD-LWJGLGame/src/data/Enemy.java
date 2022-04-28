package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

public class Enemy {
	private int w, h, health, currentCheckpoint;
	private float speed, x, y;
	Texture texture;
	private Tile startTile;
	private boolean first = true;
	private TileGrid grid;
	
	private ArrayList<Checkpoint> checkpoints;
	private int[] directions;
	
	public Enemy(Texture texture, Tile startTile, TileGrid grid, int w, int h, float speed) {
		this.texture = texture;
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.w = w;
		this.h = h;
		this.speed = speed;
		this.grid = grid;
		
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		this.directions[0] = 0;
		this.directions[1] = 0;
		this.directions = findNextD(startTile);
		this.currentCheckpoint = 0;
		populateCheckpointList();
	}
	
	public void draw() {
		drawQuadTex(texture, x, y, w, h);
	}
	
	public void update() {
		if(first)
			first = false;
		else {
			x += delta() * directions[0];
			y += delta() * directions[1] * speed;
		}
	}
	
	private int[] findNextD(Tile t) {
		int[] dir = new int[2];
		Tile up = grid.getTile(t.getXPlace(), t.getYPlace() - 1);
		Tile right = grid.getTile(t.getXPlace() + 1, t.getYPlace());
		Tile down = grid.getTile(t.getXPlace(), t.getYPlace() + 1);
		Tile left = grid.getTile(t.getXPlace() - 1, t.getYPlace());
		
		if(t.getType() == up.getType()) {
			dir[0] = 0;
			dir[1] = -1;
		}else if(t.getType() == right.getType()) {
			dir[0] = 1;
			dir[1] = 0;
		}else if(t.getType() == down.getType()) {
			dir[0] = 0;
			dir[1] = 1;
		}else if(t.getType() == left.getType()) {
			dir[0] = -1;
			dir[1] = 0;
		}else {
			dir[0] = 2;
			dir[1] = 2;
		}
		return dir;
	}
	
	private void populateCheckpointList() {
		checkpoints.add(findNextC(startTile, directions = findNextD(startTile)));
		
		int counter = 0;
		boolean cont = true;
		while(cont) {
			int[] currentD = findNextD(checkpoints.get(counter).getTile());
			if(currentD[0] == 2) {
				cont = false;
			}else {
				checkpoints.add(findNextC((checkpoints.get(counter).getTile()), directions = findNextD(checkpoints.get(counter).getTile())));
			}
			counter++;
		}
	}
	
	private Checkpoint findNextC(Tile t, int[] dir) {
		Tile next = null;
		Checkpoint c = null;
		
		boolean found = false;
		int counter = 1;
		
		while(!found) {
			
			if(t.getType() != grid.getTile(t.getXPlace() + dir[0] * counter, t.getYPlace() + dir[1] * counter).getType()) {
				found = true;
				counter -= 1;
				next = grid.getTile(t.getXPlace() + dir[0] * counter, t.getYPlace() + dir[1] * counter);
			}
			
			counter++;
		}
		
		c = new Checkpoint(next, dir[0], dir[1]);
		return c;
		
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
	
	public TileGrid getTileGrid() {
		return grid;
	}
	
}
