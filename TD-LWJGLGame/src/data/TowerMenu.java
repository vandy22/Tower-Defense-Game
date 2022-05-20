package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import ui.UI;

import static helpers.Artist.drawQuadTex;
import static helpers.Artist.loadTexture;

public class TowerMenu{
	
	private Texture background;
	private UI towerMenuUI;
	private float x, y = 100;
	
	public TowerMenu() {
		background = loadTexture("tower_menu_background");
		towerMenuUI = new UI();
		
	}
	
	private void updateButtons() {
		if(Mouse.isButtonDown(0)) {
			/*if(towerMenuUI.isButtonClicked("Play")) {
				StateManager.setState(GameState.GAME);
			}
			
			if(towerMenuUI.isButtonClicked("Editor")) {
				StateManager.setState(GameState.EDITOR);
			}
			
			if(towerMenuUI.isButtonClicked("Quit")) {
				System.exit(0);
			}*/
		}
	}
	
	public void draw() {
		drawQuadTex(background, x, y, 320, 512);
		towerMenuUI.draw();
	}
	
	public void update() {
		updateButtons();
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

}
