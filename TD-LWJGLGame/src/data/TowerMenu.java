package data;

import org.lwjgl.input.Mouse;

import org.newdawn.slick.opengl.Texture;

import ui.UI;
import ui.UI.Menu;

import static helpers.Artist.drawQuadTex;
import static helpers.Artist.loadTexture;
import static helpers.Artist.TILE_SIZE;

public class TowerMenu{
	
	private Texture background;
	private boolean holdingMenu;
	private boolean exitClicked;
	private int targetingType = 0;
	private UI ui;
	private float x = 100;
	private float y = 100;
	
	private int w = 256;
	private int h = 256;
	
	private boolean leftMouseButtonDown;
	private boolean open;
	private int towerUpgradeIndex = 0;
	private boolean upgrade;
	
	private Texture currentTowerMenuTexture;
	private Menu textMenu;
	
	public String currentTowerTitle = " ";
	public String upgradeTowerTitle = "UPGRADE";
	public String currentTowerRange = " ";
	public String upgradeTowerRange = " ";
	public String upgradeTowerCost = " ";
	public String upgradeTowerDamage = " ";
	public String currentTowerDamage = " ";
	public String currentTowerFiringSpeed = " ";
	public String upgradeTowerFiringSpeed = " ";
	
	private int titleFontSize = 15;
	private boolean deleteTower = false;
	
	private String[] upgradeTextures;
	
	public TowerMenu(TowerType towerType) {
		String[] targetTypeTextures = new String[] {"least_health_enemy", "farthest_enemy", "closest_enemy"};
		
		if(towerType.name() == "TOWER_CANNON0") {
			upgradeTextures = new String[] {"tower_cannon/tower_cannon_full_2", "tower_cannon/tower_cannon_full_3"};
		}else if(towerType.name() == "TOWER_ICE0") {
			upgradeTextures = new String[] {"tower_ice/tower_ice_full_2", "tower_ice/tower_ice_full_3"};
		}
	
		//background = loadTexture("tower_menu_background");
		background = loadTexture("menu_background_test");
		ui = new UI();
		ui.addButton("Exit", "tower_menu_exit", (int)x, (int)y);
		ui.addButton("TargetingType", targetTypeTextures, (int)x, (int)y);
		
		ui.addButton("Delete", "delete_tower", (int)x, (int)y, 32, 32);
		ui.createMenu("TextMenu", (int)x, (int)y+30, w, h, 3, 0, 30, false, 0);
		textMenu = ui.getMenu("TextMenu");
		exitClicked = false;
		leftMouseButtonDown = false;
		ui.addButton("upgrade", upgradeTextures, (int)x, (int)y);
		
	}
	
	private void placeButtons() {
		//Place buttons on the menu
		ui.getButton("Exit").setX((int)x+w-TILE_SIZE/2);
		ui.getButton("Exit").setY((int)y);
		ui.getButton("TargetingType").setX((int)x + w - w/2 + TILE_SIZE / 2 + TILE_SIZE / 4);
		ui.getButton("TargetingType").setY((int)y+50);
		ui.getButton("Delete").setX((int) x+w-TILE_SIZE/2-10);
		ui.getButton("Delete").setY((int) y+h-TILE_SIZE/2-10);
		ui.getButton("upgrade").setX((int) x+w/2-TILE_SIZE/2);
		ui.getButton("upgrade").setY((int) y+160);
	}
	
	private void placeText() {
		if(textMenu.getFontSize() != titleFontSize)
			textMenu.updateFont(titleFontSize);
		
		drawCurrentTowerStrings();
		drawUpgradeTowerStrings();
		
		textMenu.drawString("Target", (int) x + w/2 + TILE_SIZE / 2 + textMenu.getFont().getWidth("Target") / 2, (int) y+35);
	}
	
	private void drawCurrentTowerStrings() {
		//Current Tower
		textMenu.drawString(currentTowerTitle, (int) x+w/2-textMenu.getFont().getWidth(currentTowerTitle)/2, (int) y+14);
		textMenu.drawString(currentTowerDamage, (int) x+14, (int) y+45);
		textMenu.drawString(currentTowerFiringSpeed, (int) x+14, (int) y+60);
		textMenu.drawString(currentTowerRange, (int) x+14, (int) y+75);
		
		
	}
	
	private void drawUpgradeTowerStrings() {
		//Upgrade tower
		textMenu.drawString(upgradeTowerTitle, (int) x+w/2-textMenu.getFont().getWidth(upgradeTowerTitle)/2, (int) y+140);
	//	textMenu.drawString("UPGRADE", (int) x+w/2-textMenu.getFont().getWidth("UPGRADE")/2, (int) y+140);
		textMenu.drawString(upgradeTowerCost, (int) x+w/2-textMenu.getFont().getWidth(upgradeTowerCost)/2, (int) y+224);
		//textMenu.drawString(upgradeTowerRange, (int) x+40, (int) y+290);
		//textMenu.drawString(upgradeTowerDamage, (int) x+40, (int) y+310);
		//textMenu.drawString(upgradeTowerFiringSpeed, (int) x+40, (int) y+330);
	}
	
	public void setCurrentTowerMenuTexture(Texture t) {
		this.currentTowerMenuTexture = t;
	}
	
	private void updateButtons() {
		placeButtons();
		
		if(Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			if(ui.isButtonClicked("Exit")) {
				close();
			}
			if(ui.isButtonClicked("TargetingType")) {
				if(targetingType == 0) {
					ui.getButton("TargetingType").setCurrentButtonTexture(1);
					targetingType = 1;
				}else if(targetingType == 1){
					ui.getButton("TargetingType").setCurrentButtonTexture(2);
					targetingType = 2;
				}else if(targetingType == 2) {
					ui.getButton("TargetingType").setCurrentButtonTexture(0);
					targetingType = 0;
				}
			}
			if(ui.isButtonClicked("upgrade") && !leftMouseButtonDown){
				if(towerUpgradeIndex < ui.getButton("upgrade").getTextures().length) {		
					upgrade();
				}
			}
			if(ui.isButtonClicked("Delete") && !leftMouseButtonDown) {
				deleteTower = true;
			}
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);
	}
	
	public boolean isDeleteTower() {
		return deleteTower;
	}

	public void setDeleteTower(boolean deleteTower) {
		this.deleteTower = deleteTower;
	}

	private void upgrade() {
		upgrade = true;
	}
	
	public boolean isUpgrade() {
		return upgrade;
	}

	public void setUpgrade(boolean upgrade) {
		this.upgrade = upgrade;
	}

	public void close() {
		open = false;
		exitClicked = true;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
		if(open) {
			draw();
		}
	}
	
	public boolean getOpen() {
		return this.open;
	}
	
	public void draw() {
		if(!exitClicked) {
			drawQuadTex(background, x, y, w, h);
			drawQuadTex(currentTowerMenuTexture, x+w/2-TILE_SIZE/2, y+36, currentTowerMenuTexture.getImageWidth(), currentTowerMenuTexture.getImageHeight());
			ui.draw();
			placeText();
			
		}
	}
	
	public boolean isExitClicked() {
		return exitClicked;
	}

	public void setExitClicked(boolean exitClicked) {
		this.exitClicked = exitClicked;
	}

	public void update() {
		updateButtons();
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setHoldingMenu(boolean b) {
		this.holdingMenu = b;
	}
	
	public boolean getHoldingMenu() {
		return this.holdingMenu;
	}
	
	public UI getTowerMenuUI() {
		return ui;
	}
	
	public int getTargetingType() {
		return targetingType;
	}
	
	public int getTowerUpgradeIndex() {
		return towerUpgradeIndex;
	}
	
	public void setTowerUpgradeIndex(int index) {
		this.towerUpgradeIndex = index;
	}

	public Menu getTextMenu() {
		return textMenu;
	}

	public void setTextMenu(Menu textMenu) {
		this.textMenu = textMenu;
	}

}
