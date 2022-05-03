package data;

import java.util.ArrayList;

import static helpers.Clock.*;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private ArrayList<Enemy> enemyList;
	private int enemiesPerWave;
	private boolean waveCompleted;
	
	public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new ArrayList<Enemy>();
		this.waveCompleted = false;
		
		spawn();
	}
	
	public void update() {
		if(enemyList.size() < enemiesPerWave) {
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
			}	
		}
	}
	
	public boolean isCompleted() {
		return waveCompleted;
	}
	
	public void spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getTileGrid(), 64, 64, enemyType.getSpeed()));
		
	}
	
	public ArrayList<Enemy> getEnemyList(){
		return enemyList;
	}
	
}
