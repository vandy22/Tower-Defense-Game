package data;

import static helpers.Artist.loadTexture;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.drawQuadRect;
import static helpers.Artist.drawHollowCircle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import helpers.Clock;
import helpers.StateManager;
import ui.Button;
import ui.UI;
import ui.UI.Menu;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	
	//Not permanent
	Texture gameMenuUI;
	
	private Menu towerPickerMenu;
	private Menu towerPickerText;
	private Menu playerControls;
	public UI gameUI;
	private boolean leftMouseButtonDown;
	
	public Game(TileGrid grid) {
		this.grid = grid;
		gameMenuUI = loadTexture("game_menu_background");
		waveManager = new WaveManager(new Enemy(loadTexture("egg_enemy"), grid.getTile(0, 1), grid, 64, 64, 100, 200), 1f, 20);
		player = new Player(grid, waveManager);
		player.setup();
		setupGameUI();
	}
	
	private void setupGameUI() {
		String[] targetTypeTextures = new String[] {"closest_enemy", "least_health_enemy"};
		
		gameUI = new UI();
		
		gameUI.createMenu("TowerPicker", 1280, 70, 192, 960, 2, 0, 35, false, 0);
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		
		gameUI.createMenu("TowerPickerText", 0, 0, 0, 0, 0, 0, 15, false, 0);
		towerPickerText = gameUI.getMenu("TowerPickerText");
		
		gameUI.createMenu("PlayerControls", 1315, 900, 192, 960, 3, 0, 10, true, -10);
		playerControls = gameUI.getMenu("PlayerControls");
		
		playerControls.quickAddButton("Play", "play");
		playerControls.quickAddButton("Pause", "pause");
		playerControls.quickAddButton("SpeedUp", "speed_up");
		
		towerPickerMenu.quickAddTowerButton("TowerCannon", ("tower_cannon/tower_cannon_full_1"));
		towerPickerMenu.quickAddTowerButton("TowerIce", ("tower_ice/tower_ice_full_1"));
		
		
		
		gameUI.addButton("targetType", targetTypeTextures, 1300, 700);
	}
	
	private void setupTowerUI() {
		towerPickerText.drawString("Cost: " + TowerType.TOWER_CANNON0.cost, towerPickerMenu.getButton("TowerCannon").getX(), towerPickerMenu.getButton("TowerCannon").getY()+TILE_SIZE+1);
		towerPickerText.drawString("Cost: " + TowerType.TOWER_ICE0.cost, towerPickerMenu.getButton("TowerIce").getX(), towerPickerMenu.getButton("TowerIce").getY() + TILE_SIZE+1);

	}
	
	private void updateUI() {
		gameUI.draw();
		setupTowerUI();
	
		towerPickerMenu.drawString("Lives: " + Player.lives, 1300, 500);
		towerPickerMenu.drawString("Cash: " + Player.cash, 1300, 550);
		towerPickerMenu.drawString("Wave: " + waveManager.getWaveNumber(), 1300, 600);
		
		towerPickerText.drawString("FPS: " + StateManager.framesInLastSecond, 0, 0);
		
		
		if(Mouse.next()) {
			if(Mouse.isButtonDown(0) && !leftMouseButtonDown) {
				if(towerPickerMenu.isButtonClicked("TowerCannon")) {
					player.pickTower(new TowerCannon(TowerType.TOWER_CANNON0, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
				}
				else if(towerPickerMenu.isButtonClicked("TowerIce")) {
					player.pickTower(new TowerIce(TowerType.TOWER_ICE0, grid.getTile(1, 0), waveManager.getCurrentWave().getEnemyList()));
				}
				else if(playerControls.isButtonClicked("Play")) {
					if(Clock.multiplier == 0) {
						Clock.multiplier = 1;
					}else if(waveManager.getCurrentWave().isCompleted()) {
						//waveManager.newWave();
					}
				}
				else if(playerControls.isButtonClicked("Pause")) {
					Clock.multiplier = 0;
				}
				else if(playerControls.isButtonClicked("SpeedUp")) {
					if(Clock.multiplier == 1) {
						Clock.multiplier = 2;
					}
				}
			}
		}
		
		leftMouseButtonDown = Mouse.isButtonDown(0);
	}
	
	public void update() {
		drawQuadTex(gameMenuUI, 1280, 0, 192, 960);
		grid.draw();
		waveManager.update();
		player.update();
		updateUI();
		
		
		
		
	}

}
