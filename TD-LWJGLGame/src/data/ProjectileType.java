package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public enum ProjectileType {
	
	CANNON_BALL(loadTexture("cannon_bullet"), 50, 360),
	CANNON_BALL1(loadTexture("cannon_bullet"), 100, 440),
	CANNON_BALL2(loadTexture("cannon_bullet"), 150, 560),
	ICE_SHARD(loadTexture("ice_bullet"), 5, 500);
	
	Texture texture;
	int damage, range;
	float speed;
	
	ProjectileType(Texture texture, int damage, int speed){
		this.texture = texture;
		this.damage = damage;
		this.speed = speed;
	}
}
