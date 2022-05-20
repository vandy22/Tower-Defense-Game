package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public enum TowerType {
	
	TOWER_ICE(new Texture[] {loadTexture("tower_ice_base"), loadTexture("tower_ice_gun")}, ProjectileType.ICE_SHARD, 5, 300, 0.5f, 10),
	TOWER_CANNON(new Texture[] {loadTexture("tower_cannon_base"), loadTexture("tower_cannon_gun")}, ProjectileType.CANNON_BALL, 10, 300, 1f, 30);

	Texture[] textures;
	ProjectileType projectileType;
	int damage, range, cost;
	float firingSpeed;
	
	TowerType(Texture[] texture, ProjectileType projectileType, int damage, int range, float firingSpeed, int cost){
		this.textures = texture;
		this.projectileType = projectileType;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
		this.cost = cost;
	}
}
