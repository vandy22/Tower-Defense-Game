package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public enum TowerType {
	
	TOWER_CANNON0("TOWER CANNON 1", new Texture[] {loadTexture("tower_cannon/tower_cannon_base_1"), loadTexture("tower_cannon/tower_cannon_gun_1"), loadTexture("tower_cannon/tower_cannon_full_1")}, ProjectileType.CANNON_BALL, 30, 250, 1f, 30, false),
	TOWER_CANNON1("TOWER CANNON 2", new Texture[] {loadTexture("tower_cannon/tower_cannon_base_2"), loadTexture("tower_cannon/tower_cannon_gun_2"), loadTexture("tower_cannon/tower_cannon_full_2")}, ProjectileType.CANNON_BALL1, 80, 350, 0.75f, 100, false),
	TOWER_CANNON2("TOWER CANNON 3", new Texture[] {loadTexture("tower_cannon/tower_cannon_base_3"), loadTexture("tower_cannon/tower_cannon_gun_3"), loadTexture("tower_cannon/tower_cannon_full_3")}, ProjectileType.CANNON_BALL2, 200, 450, 0.5f, 300, true),
	TOWER_ICE0("TOWER ICE 1", new Texture[] {loadTexture("tower_ice/tower_ice_base_1"), loadTexture("tower_ice/tower_ice_gun_1"), loadTexture("tower_ice/tower_ice_full_1")}, ProjectileType.ICE_SHARD, 5, 200, 0.5f, 50, false),
	TOWER_ICE1("TOWER ICE 2", new Texture[] {loadTexture("tower_ice/tower_ice_base_2"), loadTexture("tower_ice/tower_ice_gun_2"), loadTexture("tower_ice/tower_ice_full_2")}, ProjectileType.ICE_SHARD, 20, 250, 0.4f, 100, false),
	TOWER_ICE2("TOWER ICE 3", new Texture[] {loadTexture("tower_ice/tower_ice_base_3"), loadTexture("tower_ice/tower_ice_gun_3"), loadTexture("tower_ice/tower_ice_full_3")}, ProjectileType.ICE_SHARD, 5, 200, 0.5f, 10, true);

	Texture[] textures;
	ProjectileType projectileType;
	int damage, range, cost;
	float firingSpeed;
	String towerName;
	boolean maxUpgrade;
	String upgradeTextures;
	
	private static TowerType[] vals = values();
	
	TowerType(String towerName, Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed, int cost, boolean maxUpgrade){
		this.textures = textures;
		this.projectileType = projectileType;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
		this.cost = cost;
		this.towerName = towerName;
		this.maxUpgrade = maxUpgrade;
	}
	
	public TowerType next() {
		return vals[(this.ordinal()+1) % vals.length];
	}
}
