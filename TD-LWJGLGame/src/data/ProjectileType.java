package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public enum ProjectileType {
	
	CANNON_BALL(loadTexture("cannon_bullet"), 50, 380),
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
