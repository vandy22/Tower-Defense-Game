package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

import static helpers.Artist.*;

import java.util.ArrayList;

public class Player {
	
	private TileGrid grid;
	private TileType[] types;
	private int index;
	private WaveManager waveManager;
	private ArrayList<TowerCannon> towerList;
	private boolean leftMouseButtonDown;
	
	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.index = 0;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<TowerCannon>();
		this.leftMouseButtonDown = false;
	}
	
	public void setTile() {
		grid.setTile((int) Math.floor(Mouse.getX() / 64), (int) Math.floor((HEIGHT - Mouse.getY() -1)) /64, types[index]);
	}
	
	public void update() {
		
		for(TowerCannon t : towerList) {
			t.update();
		}
		
		//Handle mouse input
		if(Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			towerList.add(new TowerCannon(loadTexture("tower_base"), grid.getTile(Mouse.getX() / 64, (HEIGHT - Mouse.getY() - 1) / 64), 10, waveManager.getCurrentWave().getEnemyList()));
			//setTile();
		}
		
		leftMouseButtonDown = Mouse.isButtonDown(0);
		
		//Handle keyboard input
		while(Keyboard.next()) {
			if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				//moveIndex();
				//Clock.changeMultiplier(0.2f);
			}
		}
	}
		
	public void moveIndex() {
		index++;
		if(index > types.length-1) {
			index = 0;
		}
	}
}
