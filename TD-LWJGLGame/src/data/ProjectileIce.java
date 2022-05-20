package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class ProjectileIce extends Projectile{

	private float freezeSpeed = 20;
	private float freezeLength = 2;
	
	public ProjectileIce(ProjectileType type, CopyOnWriteArrayList<Enemy> allEnemies, Enemy target, float x, float y, float w, float h) {
		super(type, allEnemies, target, x, y, w, h);
	}
	
	@Override
	public void damage(Enemy e) {
		e.setFrozen(true, freezeSpeed, freezeLength);
		super.damage(e);
	}

}
