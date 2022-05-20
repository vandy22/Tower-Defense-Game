package data;

import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Clock.*;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned;
	private boolean waveCompleted;
	
	public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		
		spawn();
	}
	
	public void update() {
		if(enemiesSpawned < enemiesPerWave) {
			timeSinceLastSpawn += delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				timeSinceLastSpawn = 0;
			}
		}else {
			waveCompleted = true;
		}
		for(Enemy e: enemyList) {
			if(e.isAlive()) {
				waveCompleted = false;
				e.update();
				e.draw();
			}else {
				enemyList.remove(e);
			}
		}
	}
	
	public boolean isCompleted() {
		return waveCompleted;
	}
	
	public void spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getTileGrid(), 64, 64, enemyType.getSpeed(), enemyType.getHealth()));
		enemiesSpawned++;
	}
	
	public CopyOnWriteArrayList<Enemy> getEnemyList(){
		return enemyList;
	}
	
}
