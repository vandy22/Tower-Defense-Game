package data;

import org.lwjgl.input.Mouse;

import org.newdawn.slick.opengl.Texture;

import ui.Button;
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
	private int w = 320;
	private int h = 512;
	private boolean leftMouseButtonDown;
	private boolean open;
	private int towerUpgradeIndex = 0;
	private boolean upgrade;
	
	private Texture currentTowerMenuTexture;
	private Menu textMenu;
	
	private String currentTowerTitle = " ";
	private String upgradeTowerTitle = " ";
	
	private int titleFontSize = 20;
	
	public TowerMenu() {
		String[] targetTypeTextures = new String[] {"least_health_enemy", "closest_enemy"};
		String[] upgradeTextures = new String[] {"tower_cannon_base1", "tower_cannon_base2"};
		background = loadTexture("tower_menu_background");
		ui = new UI();
		ui.addButton("Exit", "tower_menu_exit", (int)x, (int)y);
		ui.addButton("TargetingType", targetTypeTextures, (int)x, (int)y);
		ui.createMenu("TextMenu", (int)x, (int)y+30, w, h, 3, 0, 30, false, 0);
		textMenu = ui.getMenu("TextMenu");
		exitClicked = false;
		leftMouseButtonDown = false;
		
		ui.addButton("upgrade", upgradeTextures, (int)x, (int)y);
	}
	
	private void placeButtons() {
		//Place buttons on the menu
		ui.getButton("Exit").setX((int)x+w-TILE_SIZE);
		ui.getButton("Exit").setY((int)y);
		ui.getButton("TargetingType").setX((int)x);
		ui.getButton("TargetingType").setY((int)y);
		
		ui.getButton("upgrade").setX((int) x+w/2-TILE_SIZE/2);
		ui.getButton("upgrade").setY((int) y+227);
	}
	
	private void placeText() {
		if(textMenu.getFontSize() != titleFontSize)
			textMenu.updateFont(titleFontSize);
		textMenu.drawString(currentTowerTitle, (int) x+w/2-textMenu.getFont().getWidth(currentTowerTitle)/2, (int) y+57);
		textMenu.drawString(upgradeTowerTitle, (int) x+w/2-textMenu.getFont().getWidth(upgradeTowerTitle)/2, (int) y+210);
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
				}else {
					ui.getButton("TargetingType").setCurrentButtonTexture(0);
					targetingType = 0;
				}
			}
			if(ui.isButtonClicked("upgrade")){
				if(towerUpgradeIndex < ui.getButton("upgrade").getTextures().length) {
					System.out.println("TowerUpgradeIndex" + towerUpgradeIndex);
					
					upgrade();
				}
			}
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);
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
	
	public void open() {
		draw();
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
			drawQuadTex(currentTowerMenuTexture, x+w/2-TILE_SIZE/2, y+75, currentTowerMenuTexture.getImageWidth(), currentTowerMenuTexture.getImageHeight());
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

	public String getCurrentTowerTitle() {
		return currentTowerTitle;
	}

	public void setCurrentTowerTitle(String currentTowerTitle) {
		this.currentTowerTitle = currentTowerTitle;
	}

	public String getUpgradeTowerTitle() {
		return upgradeTowerTitle;
	}

	public void setUpgradeTowerTitle(String upgradeTowerTitle) {
		this.upgradeTowerTitle = upgradeTowerTitle;
	}

	public Menu getTextMenu() {
		return textMenu;
	}

	public void setTextMenu(Menu textMenu) {
		this.textMenu = textMenu;
	}

}
