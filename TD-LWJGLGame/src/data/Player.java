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
	private Tower tempTower;
	public static int cash, lives;
	
	private float previousMouseX;
	private float previousMouseY;
	
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
		cash = 1000;
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
			if(tm.getOpen())
				tm.update();
		}
		
		//Handle mouse input
		if(!Mouse.isButtonDown(0) && leftMouseButtonDown) {
			placeTower();
		}
		
		//If clicked on tower
		if(towerList != null) {
			
			
			
			for(int i = 0; i < towerList.size(); i++) {
				
				if(towerMenus.get(i).getTowerUpgradeIndex() == 0) {
					System.out.println("Ran");
					towerMenus.get(i).setUpgradeTowerTitle(towerList.get(i).getType().name().substring(0, towerList.get(i).getType().name().length()-1)+(towerMenus.get(i).getTowerUpgradeIndex()+2));

				}
				
				//Targeting Tower Button
				if(towerMenus.get(i).getTargetingType() == 0) {
					towerList.get(i).setTargetingType(1);
				}else {
					towerList.get(i).setTargetingType(0);
				}
				
				//Set current tower in menu
				towerMenus.get(i).setCurrentTowerMenuTexture(towerList.get(i).getType().textures[0]);
				towerMenus.get(i).setCurrentTowerTitle(towerList.get(i).getType().name());
				
				//Upgrade Tower Button
				if(Mouse.isButtonDown(0) && !leftMouseButtonDown && towerMenus.get(i).isUpgrade()) {
					
					
					//System.out.println("Tower Cannon " + towerMenus.get(i).getTowerUpgradeIndex());
					//if(modifyCash(-TowerType.valueOf("TOWER_CANNON"+towerMenus.get(i).getTowerUpgradeIndex()).cost)) {
						//Update menu texture
						//System.out.println(towerMenus.get(i).getTowerUpgradeIndex());
						if(towerMenus.get(i).getTowerUpgradeIndex() < towerMenus.get(i).getTowerMenuUI().getButton("upgrade").getTextures().length-1) {	
							towerMenus.get(i).getTowerMenuUI().getButton("upgrade").setCurrentButtonTexture(towerMenus.get(i).getTowerUpgradeIndex()+1);
						}
						//Update tower type (includes texture, damage, etc)
						System.out.println(towerMenus.get(i).getTowerUpgradeIndex());
						if(towerMenus.get(i).getTowerMenuUI().getButton("upgrade").getTextures().length < 3) {
							towerList.get(i).setType(TowerType.valueOf("TOWER_CANNON"+(towerMenus.get(i).getTowerUpgradeIndex()+1)));
							towerMenus.get(i).setUpgradeTowerTitle(towerList.get(i).getType().name().substring(0, towerList.get(i).getType().name().length()-1)+(towerMenus.get(i).getTowerUpgradeIndex()+2));
						}else {
							towerList.get(i).setType(TowerType.valueOf("TOWER_CANNON"+(towerMenus.get(i).getTowerUpgradeIndex())));
							towerMenus.get(i).setUpgradeTowerTitle(towerList.get(i).getType().name().substring(0, towerList.get(i).getType().name().length()-1)+(towerMenus.get(i).getTowerUpgradeIndex()+1));
						}

						//Set the upgrade to false to stop loop
						towerMenus.get(i).setTowerUpgradeIndex(towerMenus.get(i).getTowerUpgradeIndex()+1);
						towerMenus.get(i).setUpgrade(false);
						
						
					//x}
				}
				
				
				//Checking if tower is clicked; if clicked then setting it to true
				if(Mouse.isButtonDown(0) && !leftMouseButtonDown && towerList.get(i).getTile() == getMouseTile()) {
					towerList.get(i).setClicked(true);
				}
				
				//Checking if tower is clicked & the the exit button has not been clicked in tower menu
				if(towerList.get(i).isClicked() && !towerMenus.get(i).isExitClicked()) {
					
					//Opening tower menu and updating tower upgrade menu
					//towerMenus.get(i).open();
					towerMenus.get(i).setOpen(true);
					//towerMenus.get(i).getTowerMenuUI().getMenu("TowerUpgradeMenu").update();
					
					//Checking if mouse button is clicked once
					if(Mouse.isButtonDown(0) && !leftMouseButtonDown) {
						
						//Set the intitial previous mouse position
						previousMouseX = Mouse.getX();
						previousMouseY = HEIGHT - Mouse.getY() - 1;
						
						//Checking if mouse is on the menu
						if(isMouseInBounds(i)) {
							//Set the holding menu to true
							towerMenus.get(i).setHoldingMenu(true);
						}	
					}
					
					//Check if left mouse is down, in bounds and the menu is being held.
					if(Mouse.isButtonDown(0) && isMouseInBounds(i) && towerMenus.get(i).getHoldingMenu()) {
						
						//Calculate the distance between the current mouse position and the previous mouse position.
						float xDist = Mouse.getX() - previousMouseX;
						float yDist = HEIGHT - Mouse.getY() - 1 - previousMouseY;
						
						//Set the tower menu to the mouse position respectively
						towerMenus.get(i).setX(towerMenus.get(i).getX()+xDist);
						towerMenus.get(i).setY(towerMenus.get(i).getY()+yDist);
						
						//Reset the previous mouse position each update
						previousMouseX = Mouse.getX();
						previousMouseY = HEIGHT - Mouse.getY() -1 ;
					
					}else{
						//Set holding menu to false
						towerMenus.get(i).setHoldingMenu(false);
					}
				}else{
					//Reset the variables to allow tower to be re-clicked to open menu 
					towerMenus.get(i).setExitClicked(false);
					towerList.get(i).setClicked(false);
				}
			}
		}
		
		//Set the previous mouse isDown boolean. Used to allow update to only run once
		leftMouseButtonDown = Mouse.isButtonDown(0);
		
		//Handle keyboard input
		/*while(Keyboard.next()) {
			if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				//moveIndex();
				Clock.changeMultiplier(0.2f);
			}
		}*/
		
	}
	
	public boolean isMouseInBounds(int i) {
		return Mouse.getX() > towerMenus.get(i).getX() && Mouse.getX() < towerMenus.get(i).getX()+towerMenus.get(i).getW() && HEIGHT - Mouse.getY() - 1 > towerMenus.get(i).getY() && HEIGHT - Mouse.getY() - 1 < towerMenus.get(i).getY()+towerMenus.get(i).getH();
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
	
	public ArrayList<TowerMenu> getMenuList() {
		return towerMenus;
	}
	
	private Tile getMouseTile() {
		return grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
	}
		
	
}
