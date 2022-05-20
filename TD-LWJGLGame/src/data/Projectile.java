package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Clock.*;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Artist.*;

public abstract class Projectile implements Entity{
	
	private Texture texture;
	private float x, y, speed, xVelocity, yVelocity;
	private int damage, w, h;
	private Enemy target;
	private boolean alive;
	private CopyOnWriteArrayList<Enemy> allEnemies;
	
	public Projectile(ProjectileType type, CopyOnWriteArrayList<Enemy> allEnemies, Enemy target, float x, float y, float w, float h) {
		this.texture = type.texture;
		this.x = x;
		this.y = y;
		this.w = (int) texture.getWidth();
		this.h = (int) texture.getHeight();
		this.speed = type.speed;
		this.damage = type.damage;
		this.target = target;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		this.alive = true;

		this.allEnemies = allEnemies;
		calculateDirection();
	}
	
	private void calculateDirection() {
		float totalAllowedMovement = 1.0f;
		float xDistanceFromTarget = Math.abs(target.getX() - x - TILE_SIZE / 4 + TILE_SIZE / 2);
		float yDistanceFromTarget = Math.abs(target.getY() - y - TILE_SIZE / 4 + TILE_SIZE / 2);
		float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
		float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
		xVelocity = xPercentOfMovement;
		yVelocity = totalAllowedMovement - xPercentOfMovement;
		if(target.getX() < x)
			xVelocity *= -1;
		if(target.getY() < y)
			yVelocity *= -1;
	}
	
	public void damage(Enemy e) {
		e.damage(damage);
		alive = false;
	}
	
	public void update() {
		if(alive) {
			x += xVelocity * speed * delta();
			y += yVelocity * speed * delta();
			for(Enemy e : allEnemies) {
				if(e.isAlive()) {
					if(checkCollision(x, y, w, h, e.getX(), e.getY(), e.getW(), e.getH())) { 
						damage(e);
					}	
				}
			}
			draw();
		}
	}
	
	public void draw() {
		drawQuadTex(texture, x, y, 32, 32);
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
	
	public void setAlive(boolean status) {
		this.alive = status;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
