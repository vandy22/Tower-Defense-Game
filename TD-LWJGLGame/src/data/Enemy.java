 package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

public class Enemy implements Entity{
	private int w, h, currentCheckpoint;
	private float speed, x, y, health, startHealth;
	Texture texture, healthBackground, healthForeground, healthBorder;
	private Tile startTile;
	private boolean first = true, alive = true;
	private TileGrid grid;
	
	private float timeSinceFreeze;
	private boolean isFrozen;
	private float originalSpeed;
	private float freezeSpeed;
	private float freezeTime;
	
	private int enemyWorth = 15;
	
	
	private ArrayList<Checkpoint> checkpoints;
	private int[] directions;
	
	public Enemy(Texture texture, Tile startTile, TileGrid grid, int w, int h, float speed, float health) {
		this.texture = texture;
		this.healthBackground = loadTexture("health_background");
		this.healthForeground = loadTexture("health_foreground");
		this.healthBorder = loadTexture("health_border");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.w = w;
		this.h = h;
		this.speed = speed;
		this.grid = grid;
		this.health = health;
		this.startHealth = health;
		
		//Freeze Variables
		this.originalSpeed = speed;
		this.freezeSpeed = 0;
		this.freezeTime = 0;
		this.timeSinceFreeze = 0;
		this.isFrozen = false;
		
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		//X Direction
		this.directions[0] = 0;
		//Y Direction
		this.directions[1] = 0;
		this.directions = findNextD(startTile);
		this.currentCheckpoint = 0;
		populateCheckpointList();
	}
	
	public void draw() {
		float healthPercentage = health / startHealth;
		drawQuadTex(texture, x, y, w, h);
		drawQuadTex(healthBackground, x, y - 16, w, 8);
		drawQuadTex(healthForeground, x, y - 16, w * healthPercentage, 8);
		drawQuadTex(healthBorder, x, y - 16, w, 8);
	}
	
	public void update() {
		if(first)
			first = false;
		else {
			if(checkpointReached()) {
				if(currentCheckpoint + 1 == checkpoints.size())
					endReached();
				else
					currentCheckpoint++;
			}else {
				x += delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
				y += delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
			}
			
			if(isFrozen) {
				timeSinceFreeze += delta();
				if (timeSinceFreeze < freezeTime) {
					speed = freezeSpeed;
				}else {
					speed = originalSpeed;
					timeSinceFreeze = 0;
					isFrozen = false;
				}	
			}
		}
	}
	
	private void endReached() {
		Player.modifyLives(-1);
		die();
	}
	
	private int[] findNextD(Tile t) {
		int[] dir = new int[2];
		//Create tiles that precede the current tile to eventually tell its type.
		Tile up = grid.getTile(t.getXPlace(), t.getYPlace() - 1);
		Tile right = grid.getTile(t.getXPlace() + 1, t.getYPlace());
		Tile down = grid.getTile(t.getXPlace(), t.getYPlace() + 1);
		Tile left = grid.getTile(t.getXPlace() - 1, t.getYPlace());
		
		//Check the types of all tiles around the current one to see if it is similiar and change direciton based upon this.
		if(t.getType() == up.getType() && directions[1] != 1) {
			dir[0] = 0;
			dir[1] = -1;
		}else if(t.getType() == right.getType() && directions[0] != -1) {
			dir[0] = 1;
			dir[1] = 0;
		}else if(t.getType() == down.getType() && directions[1] != -1) {
			dir[0] = 0;
			dir[1] = 1;
		}else if(t.getType() == left.getType() && directions[0] != 1) {
			dir[0] = -1;
			dir[1] = 0;
		}else {
			dir[0] = 2;
			dir[1] = 2;
		}
		return dir;
	}
	
	private boolean checkpointReached() {
		boolean reached = false;
		//Create a tile that is set to the current checkpoint
		Tile t = checkpoints.get(currentCheckpoint).getTile();
		//Check if the players x coord is within 3 of this tile inside its boundaries (for delta sake)
		if(x > t.getX() - 3 && x < t.getX() + 3 && y > t.getY() - 3 && y < t.getY() + 3) {
			//Set that the checkpoint is reached
			reached = true;
			//Set the x of the enemy to be exactly on the square instead of 3 pixels off
			x = t.getX();
			y = t.getY();
		}
		return reached;
	}
	
	private void populateCheckpointList() {
		//Create the initial checkpoint using the startingTile.
		checkpoints.add(findNextC(startTile, directions = findNextD(startTile)));
		
		//Create a counter for checking distance of different tile type.
		int counter = 0;
		//While loop to check for all checkpoints
		boolean cont = true;
		while(cont) {
			//Gets the current direction of the current checkpoint.
			int[] currentD = findNextD(checkpoints.get(counter).getTile());
			//Checks if the current direction is 2 (the end)
			if(currentD[0] == 2 || counter == 20) {
				//Stops adding checkpoints
				cont = false;
			}else {
				//Adds checkpoints
				checkpoints.add(findNextC(checkpoints.get(counter).getTile(), directions = findNextD(checkpoints.get(counter).getTile())));
			}
			//Increases counter until while loop finishes due to no more checkpoints.
			counter++;
		}
	}
	
	private Checkpoint findNextC(Tile t, int[] dir) {
		//Setting variables to null (for saftey I assume)
		Tile next = null;
		Checkpoint c = null;
		
		//Boolean to find if the checkpoint is found
		boolean found = false;
		//Creating a counter (basically to scan blocks horizontally and vertically)
		int counter = 1;
		
		while(!found) {
			//Checks where the tiletype is different than the current one based on the direction of the moving enemy.
			if(t.getXPlace() + dir[0] * counter == grid.getTilesWide() || t.getYPlace() + dir[1] * counter == grid.getTilesHigh() || t.getType() != grid.getTile(t.getXPlace() + dir[0] * counter, t.getYPlace() + dir[1] * counter).getType()) {
				//Finds the checkpoint
				found = true;
				//Sets the found checkpoint to the tile behind it. The tile behind the new tiletype
				counter -= 1;
				//next is the variable that holds the tile right before the tile with the new tiletype.
				next = grid.getTile(t.getXPlace() + dir[0] * counter, t.getYPlace() + dir[1] * counter);
			}
			//Counter increases until a new tiletype is found.
			counter++;
		}
		//Checkpoint is returned with the tile and the direction in which the tile is moving.
		c = new Checkpoint(next, dir[0], dir[1]);
		return c;
	}
	
	public void damage(int amount) {
		health -= amount;
		if(health <= 0) {
			Player.modifyCash(enemyWorth);
			die();
		}
	}
	
	private void die() {
		alive = false;
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

	public float getHealth() {
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
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setFrozen(boolean frozen, float freezeSpeed, float freezeTime) {
		this.isFrozen = frozen;
		this.freezeSpeed = freezeSpeed;
		this.freezeTime = freezeTime;
	}

	public int getCurrentCheckpoint() {
		return currentCheckpoint;
	}
}
