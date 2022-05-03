package data;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class TowerCannon {

	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int w, h, damage;
	private Texture baseTexture, cannonTexture;
	private Tile startTile;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private Enemy target;
	
	public TowerCannon(Texture baseTexture, Tile startTile, int damage, ArrayList<Enemy> enemies) {
		this.baseTexture = baseTexture;
		this.cannonTexture = loadTexture("tower_gun");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.w = (int)startTile.getW();
		this.h = (int)startTile.getH();
		this.damage = damage;
		this.firingSpeed = 2;
		this.timeSinceLastShot = 0;
		this.projectiles = new ArrayList<Projectile>();
		this.enemies = enemies;
		this.target = findTarget();
		this.angle = calculateAngle();
	}
	
	private Enemy findTarget() {
		return enemies.get(0);
	}
	
	private float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;
	}
	
	public void shoot() {
		timeSinceLastShot = 0;
		projectiles.add(new Projectile(loadTexture("bullet"), target, x + Game.TILE_SIZE / 2 - Game.TILE_SIZE / 4, y + Game.TILE_SIZE / 2 - Game.TILE_SIZE / 4, 900, 10));
	}
	
	public void update() {
		timeSinceLastShot += delta();
		if(timeSinceLastShot > firingSpeed) {
			shoot();
		}
		
		for(Projectile p: projectiles) {
			p.update();
		}
		
		angle = calculateAngle();
		draw();
	}
	
	public void draw() {
		drawQuadTex(baseTexture, x, y, w, h);
		drawQuadTexRot(cannonTexture, x, y, w, h, angle);
	}
}
