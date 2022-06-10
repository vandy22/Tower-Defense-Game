package data;

import org.newdawn.slick.opengl.Texture;

import audio.AudioLibrary;

import static helpers.Artist.*;
import static helpers.Clock.delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Tower implements Entity{

	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int w, h, damage, range, cost;
	public Enemy target;
	private Texture[] textures;
	public CopyOnWriteArrayList<Enemy> enemies;
	public ArrayList<Projectile> projectiles;
	public TowerType type;
	private int targetingType;
	private Tile currentTile;
	private boolean isClicked;
	private Texture rangeCircle;
	public float circleDistance;
	
	private int ID;
	
	public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		this.type = type;
		this.textures = type.textures;
		this.damage = type.damage;
		this.range = type.range;
		this.cost = type.cost;
		this.firingSpeed = type.firingSpeed;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.w = startTile.getW();
		this.h = startTile.getH();
		this.enemies = enemies;
		this.projectiles = new ArrayList<Projectile>();
		this.angle = 0f;
		this.targetingType = 0;
		this.ID = 0;
		this.rangeCircle = loadTexture("tower_range_circle");
	}
	
	private Enemy findClosestTarget() {
		Enemy closest = null;
		float closestDistance = 10000;
		for(Enemy e: enemies) {
			if(isInRange(e) && findDistance(e) < closestDistance && e.isAlive()) {
				closestDistance = findDistance(e);
				closest = e;
			}
		}
		return closest;
	}
	/*
	private Enemy findFarthestTarget() {
		Enemy farthest = null;
		float farthestDistance = 0;
		int currentCheckpoint = 0;
		for(Enemy e: enemies) {
			if(isInRange(e) && findDistance(e) > farthestDistance && e.isAlive() && currentCheckpoint < e.getCurrentCheckpoint()) {
				farthestDistance = findDistance(e);
				currentCheckpoint = e.getCurrentCheckpoint();
				farthest = e;
				
			}
		}
		return farthest;
	}*/
	
	private Enemy findFarthestTarget() {
		Enemy farthest = null;
		float farthestDistance = 0;
		for(Enemy e: enemies) {
			if(isInRange(e) && findDistance(e) > farthestDistance && e.isAlive()) {
				farthestDistance = findDistance(e);
				farthest = e;
				
			}
		}
		return farthest;
	}

	private Enemy findLeastHealthTarget() {
		Enemy leastHealth = null;
		float lowestHealth = 10000;
		for(Enemy e: enemies) {
			if(isInRange(e) && e.getHealth() < lowestHealth && e.isAlive()) {
				lowestHealth = e.getHealth();
				leastHealth = e;
			}
		}
		return leastHealth;
	}
	
	private float findDistance(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		return xDistance+yDistance;
	}
	
	private boolean isInRange(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		float radius = (float) Math.sqrt((Math.pow(xDistance, 2)+Math.pow(yDistance, 2)));
		if(radius < range) {
			return true;
		}
		
		return false;
	}
	
	private float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;
	}
	
	public abstract void shoot(Enemy target);
	
	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
		enemies = newList;
	}
	
	public void setTile(Tile currentTile) {
		this.currentTile = currentTile;
		this.x = currentTile.getX();
		this.y = currentTile.getY();
	}
	
	public void updateData() {
		this.textures = type.textures;
		this.damage = type.damage;
		this.range = type.range;
		this.cost = type.cost;
	}
	
	public Tile getTile() {
		return currentTile;
	}
	
	public void update() {
		//For now, when more targeting types, create a switch statement
		if(targetingType == 0) {
			target = findClosestTarget();
		}else if(targetingType == 1){
			target = findLeastHealthTarget();
		}else if(targetingType == 2) {
			target = findFarthestTarget();
		}
		
		if(target == null || target.isAlive() == false) {
			//targeted = false;
		}else {
			angle = calculateAngle();
		}
		
		timeSinceLastShot += delta();
		
		if(timeSinceLastShot > firingSpeed && target != null) {
			//AudioLibrary.audioSource.play(AudioLibrary.shootSound);
			shoot(target);
			timeSinceLastShot = 0;
		}
		
		for(Projectile p: projectiles) {
			p.update();
		}
		
		//draw();
	}

	public void draw() {
		this.textures = type.textures;
		drawQuadTex(textures[0], x, y, w, h);
		if(textures.length > 1) {
			drawQuadTexRot(textures[1], x, y, w, h, angle);
		}
		
	}
	
	public void setDrawRangeRadius(boolean draw) {
		if(draw)
			drawQuadTex(rangeCircle, x-range+w/2, y-range+h/2, range*2, range*2);
	}
	
	public float getTileX() {
		return x / TILE_SIZE;
	}
	
	public float getTileY() {
		return y / TILE_SIZE;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setW(int w) {
		this.w = w;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public Enemy getTarget() {
		return target;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setTargetingType(int type) {
		this.targetingType = type;
	}
	
	public int getTargetingType() {
		return targetingType;
	}
	
	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}
	
	public boolean isClicked() {
		return isClicked;
	}
	
	public TowerType getType() {
		return type;
	}

	public void setType(TowerType type) {
		this.type = type;
	}

	public int getId() {
		return ID;
	}

	public void setId(int towerId) {
		this.ID = towerId;
	}
	
	

}
