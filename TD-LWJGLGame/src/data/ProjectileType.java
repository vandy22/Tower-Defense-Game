package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public enum ProjectileType {
	
	CANNON_BALL(loadTexture("cannon_bullet_1"), loadTexture("cannon_explosion"), 50, 360),
	CANNON_BALL1(loadTexture("cannon_bullet_2"), loadTexture("cannon_explosion"), 100, 440),
	CANNON_BALL2(loadTexture("cannon_bullet_3"), loadTexture("cannon_explosion"), 150, 560),
	ICE_SHARD(loadTexture("ice_bullet"), loadTexture("cannon_explosion"), 5, 500);
	
	Texture texture, explosion;
	int damage, range;
	float speed;
	
	ProjectileType(Texture texture, Texture explosion, int damage, int speed){
		this.texture = texture;
		this.explosion = explosion;
		this.damage = damage;
		this.speed = speed;
	}
}
