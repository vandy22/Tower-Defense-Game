package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

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
	private TowerMenu openMenu;
	
	private float previousMouseX;
	private float previousMouseY;
	
	//Set this for the mean time (want it to be a part of a tower)
	
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
		
		//Place this here for now
		
		
		
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
		
		//drawHollowCircle(200, 200, 500);
		
		//If clicked on tower
		if(towerList != null) {
			
			for(int i = 0; i < towerList.size(); i++) {
				
				//When there is no upgrade yet clicked, set the current statistics for the first upgrade
				if(towerMenus.get(i).getTowerUpgradeIndex() == 0) {
					updateTowerData(i, 1);
				}
				
				//Targeting Tower Button
				if(towerMenus.get(i).getTargetingType() == 0) {
					towerList.get(i).setTargetingType(1);
				}else if(towerMenus.get(i).getTargetingType() == 1){
					towerList.get(i).setTargetingType(2);
				}else if(towerMenus.get(i).getTargetingType() == 2){
					towerList.get(i).setTargetingType(0);
				}
				//Set current tower in menu
				updateTowerData(i, 0);
				
				//Upgrade Tower Button
				if(Mouse.isButtonDown(0) && !leftMouseButtonDown && towerMenus.get(i).isUpgrade() && modifyCash(-towerList.get(i).getType().next().cost)) {
					
					//System.out.println("Tower Cannon " + towerMenus.get(i).getTowerUpgradeIndex());
					//if(modifyCash(-towerList.get(i).getType().next().cost)) {
						//Update menu texture
						if(towerMenus.get(i).getTowerUpgradeIndex() < towerMenus.get(i).getTowerMenuUI().getButton("upgrade").getTextures().length-1) {	
							towerMenus.get(i).getTowerMenuUI().getButton("upgrade").setCurrentButtonTexture(towerMenus.get(i).getTowerUpgradeIndex()+1);
						}
						//Update tower type (includes texture, damage, etc)
						if(towerMenus.get(i).getTowerMenuUI().getButton("upgrade").getTextures().length > 1) {
							
							//Set the tower type to the next upgrade
							towerList.get(i).setType(towerList.get(i).getType().next());
							//Check if the tower type is a max upgrade
							if(towerList.get(i).getType().maxUpgrade) {
								towerMenus.get(i).upgradeTowerTitle = "MAXED";
								towerList.get(i).updateData();
							}else {
								updateTowerData(i, 1);
								towerList.get(i).updateData();
							}
						}

						//Set the upgrade to false to stop loop
						towerMenus.get(i).setTowerUpgradeIndex(towerMenus.get(i).getTowerUpgradeIndex()+1);
						towerMenus.get(i).setUpgrade(false);
					//}
				
				}
				//Checking if tower is clicked; if clicked then setting it to true
				if(Mouse.isButtonDown(0) && !leftMouseButtonDown && towerList.get(i).getTile() == getMouseTile()) {
					towerList.get(i).setClicked(true);
				}
				
				//Checking if tower is clicked & the the exit button has not been clicked in tower menu
				if(towerList.get(i).isClicked() && !towerMenus.get(i).isExitClicked()) {
		
					//Update button texture to the correct tower type (ex: Ice Tower)
					
					//Draw button slightly larger to make it look like the tower is selected
					towerList.get(i).setW(TILE_SIZE + 1);
					towerList.get(i).setH(TILE_SIZE + 1); 
					
					//Draw the range radius
					towerList.get(i).setDrawRangeRadius(true);
					
					//Opening tower menu and updating tower upgrade menu
					if(openMenu != towerMenus.get(i)) {
						openMenu.close();
					}
					
					towerMenus.get(i).setOpen(true);
					openMenu = towerMenus.get(i);
					
					//Checking if mouse button is clicked once
					if(Mouse.isButtonDown(0) && !leftMouseButtonDown) {
						
						//Set the intitial previous mouse position
						previousMouseX = Mouse.getX();
						previousMouseY = HEIGHT - Mouse.getY() - 1;
						
						//Checking if mouse is on the menu
						if(isMouseInBounds(i)) {
							//Set the holding menu to true
							openMenu.setHoldingMenu(true);
						}	
					}
					
					//Check if left mouse is down, in bounds and the menu is being held.
					if(Mouse.isButtonDown(0) && isMouseInBounds(i) && towerMenus.get(i).getHoldingMenu()) {
						
						//Calculate the distance between the current mouse position and the previous mouse position.
						float xDist = Mouse.getX() - previousMouseX;
						float yDist = HEIGHT - Mouse.getY() - 1 - previousMouseY;
						
						//Set the tower menu to the mouse position respectively
						openMenu.setX(towerMenus.get(i).getX()+xDist);
						openMenu.setY(towerMenus.get(i).getY()+yDist);
						
						//Reset the previous mouse position each update
						previousMouseX = Mouse.getX();
						previousMouseY = HEIGHT - Mouse.getY() -1 ;
					}else{
						//Set holding menu to false
						openMenu.setHoldingMenu(false);
					}
					
					//Check if tower is deleted
					if(openMenu.isDeleteTower()) {
						towerList.get(i).getTile().setOccupied(false);
						cash += towerList.get(i).getCost() * 0.5f;
						
						System.out.println("Deleted >> " + "ID: " + towerList.get(i).getId() + " >> " + towerList.get(i).getType().towerName);
						
						towerMenus.remove(i);
						towerList.remove(i);
						
					}
				}else{
					//Reset the variables to allow tower to be re-clicked to open menu 
					towerMenus.get(i).setExitClicked(false);
					towerList.get(i).setClicked(false);
					towerList.get(i).setDrawRangeRadius(false);
					
					towerList.get(i).setW(TILE_SIZE);
					towerList.get(i).setH(TILE_SIZE); 
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
	
	private void updateTowerData(int index, int currentUpgrade) {
		switch(currentUpgrade) {
		case 0:
			//Current
			towerMenus.get(index).setCurrentTowerMenuTexture(towerList.get(index).getType().textures[2]);
			towerMenus.get(index).currentTowerTitle = (towerList.get(index).getType().towerName);
			towerMenus.get(index).currentTowerRange = ("RNG: " + towerList.get(index).getType().range);
			towerMenus.get(index).currentTowerDamage = ("DMG: " + towerList.get(index).getType().damage);
			towerMenus.get(index).currentTowerFiringSpeed = ("SPD: " + towerList.get(index).getType().firingSpeed);
			break;
		case 1:
			//Upgrade
			//towerMenus.get(index).upgradeTowerTitle = towerList.get(index).getType().next().towerName;
			towerMenus.get(index).upgradeTowerCost = "COST: " + towerList.get(index).getType().next().cost;
			//towerMenus.get(index).upgradeTowerRange = "Range: " + towerList.get(index).getType().next().range;
			//towerMenus.get(index).upgradeTowerDamage = "Damage: " + towerList.get(index).getType().next().damage;
			//towerMenus.get(index).upgradeTowerFiringSpeed = "Firing Speed: " + towerList.get(index).getType().next().firingSpeed;
			break;
		}
	} 
	
	private void placeTower() {
		Tile currentTile = getMouseTile();
		if(holdingTower)
			if(!currentTile.isOccupied() && modifyCash(-tempTower.getCost())){
				tempTower.setId(towerList.size());
				towerList.add(tempTower);
				towerMenus.add(new TowerMenu(tempTower.getType()));
				openMenu = towerMenus.get(0);
				tempTower.setTile(currentTile);
				currentTile.setOccupied(true);
				
				System.out.println("Placed >> " + "ID: " + tempTower.getId() + " >> "+ tempTower.getType().towerName);
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
