package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public enum TowerType {
	
	TOWER_CANNON0(new Texture[] {loadTexture("tower_cannon_base"), loadTexture("tower_cannon_gun"), loadTexture("dirt")}, ProjectileType.CANNON_BALL, 30, 250, 1f, 30),
	TOWER_CANNON1(new Texture[] {loadTexture("tower_cannon_base1"), loadTexture("tower_cannon_gun1")}, ProjectileType.CANNON_BALL1, 80, 350, 0.75f, 100),
	TOWER_CANNON2(new Texture[] {loadTexture("tower_cannon_base2"), loadTexture("tower_cannon_gun2")}, ProjectileType.CANNON_BALL2, 200, 500, 0.5f, 300),
	TOWER_CANNON3(new Texture[] {loadTexture("grass"), loadTexture("tower_cannon_gun2")}, ProjectileType.CANNON_BALL2, 200, 500, 0.5f, 300),
	TOWER_ICE(new Texture[] {loadTexture("tower_ice_base"), loadTexture("tower_ice_gun")}, ProjectileType.ICE_SHARD, 5, 200, 0.5f, 10);

	Texture[] textures;
	ProjectileType projectileType;
	int damage, range, cost;
	float firingSpeed;
	
	TowerType(Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed, int cost){
		this.textures = textures;
		this.projectileType = projectileType;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
		this.cost = cost;
	}
}
