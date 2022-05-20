package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class ProjectileCannon extends Projectile{

	public ProjectileCannon(ProjectileType type, CopyOnWriteArrayList<Enemy> allEnemies, Enemy target, float x, float y, float w, float h) {
		super(type, allEnemies, target, x, y, w, h);
	}
	
	@Override
	public void damage(Enemy e) {
		super.damage(e);
	}

}
