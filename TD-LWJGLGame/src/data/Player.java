package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

import static helpers.Artist.*;

import java.util.ArrayList;

public class Player {
	
	private TileGrid grid;
	private TileType[] types;
	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	private ArrayList<TowerMenu> towerMenus;
	private boolean leftMouseButtonDown;
	private boolean holdingTower;
	private boolean holdingTowerMenu;
	private boolean towerClicked;
	private Tower tempTower;
	public static int cash, lives;
	
	
	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower>();
		this.towerMenus = new ArrayList<TowerMenu>();
		this.leftMouseButtonDown = false;
		this.holdingTower = false;
		this.tempTower = null;
		
	}
	
	public void setup() {
		cash = 100;
		lives = 10;
	}
	
	public static boolean modifyCash(int amount) {
		
		if(cash + amount >= 0) {
			cash += amount;
			return true;
		}
		return false;
	}
	
	public static void modifyLives(int amount) {
		lives += amount;
	}
	
	public void update() {
		
		if(holdingTower) {
			tempTower.setX(getMouseTile().getX());
			tempTower.setY(getMouseTile().getY());
			tempTower.draw();
		}
		
		for(Tower t : towerList) {
			t.update();
			t.draw();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}
		
		for(TowerMenu tm: towerMenus) {
			tm.update();
		}
		
		//Handle mouse input
		if(!Mouse.isButtonDown(0) && leftMouseButtonDown) {
			placeTower();
		}
		
		//If clicked on tower
		if(towerList != null) {
			for(int i = 0; i < towerList.size(); i++) {
				if(Mouse.isButtonDown(0) && leftMouseButtonDown && towerList.get(i).getTile() == getMouseTile()) {
					towerList.get(i).setClicked(true);
					//towerClicked(i);
				}
				if(towerList.get(i).isClicked()) {
					towerMenus.get(i).draw();
					if(Mouse.isButtonDown(0) && leftMouseButtonDown && Mouse.getX() > towerMenus.get(i).getX()) {
						towerMenus.get(i).setX(Mouse.getX());
						towerMenus.get(i).setY(Mouse.getY());
					}
					//System.out.println(towerList.get(i).getCost());
				}
			}
			
		}
		
		
		
		leftMouseButtonDown = Mouse.isButtonDown(0);
		
		//Handle keyboard input
		while(Keyboard.next()) {
			if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				//moveIndex();
				Clock.changeMultiplier(0.2f);
			}
		}
		
	}
	
	private void towerClicked(int index) {
		if(towerClicked) {
			towerMenus.get(index).draw();
			System.out.println(towerList.get(index).getCost());
		}
	}
	
	private void placeTower() {
		Tile currentTile = getMouseTile();
		if(holdingTower)
			if(!currentTile.isOccupied() && modifyCash(-tempTower.getCost())){
				towerList.add(tempTower);
				towerMenus.add(new TowerMenu());
				tempTower.setTile(currentTile);
				currentTile.setOccupied(true);
			}
		holdingTower = false;
		tempTower = null;
	}
	
	//private void clickedTower() {
	//	Tile currentTile = getMouseTile();
		//if(currentTile == tempTower.get)
	//}
	
	public void pickTower(Tower t) {
		tempTower = t;
		holdingTower = true;
	}
	
	public ArrayList<Tower> getTowerList() {
		return towerList;
	}
	
	private Tile getMouseTile() {
		return grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
	}
		
	
}
