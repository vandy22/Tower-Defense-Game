package data;

import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Artist.TILE_SIZE;

public class TowerIce extends Tower{

	public TowerIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}
	
	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new ProjectileIce(super.type.projectileType, super.enemies, super.target, super.getX() + TILE_SIZE / 2 - TILE_SIZE / 4, super.getY() + TILE_SIZE / 2 - TILE_SIZE / 4, super.getW(), super.getH()));
	}

}
