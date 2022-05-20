package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import helpers.StateManager;
import helpers.StateManager.GameState;
import ui.UI;

import static helpers.Artist.*;

public class MainMenu {

	private Texture background;
	private UI menuUI;
	
	public MainMenu() {
		background = loadTexture("mainmenu");
		menuUI = new UI();
		menuUI.addButton("Play", "play_button", WIDTH / 2 - 128, (int) (HEIGHT * 0.40f));
		menuUI.addButton("Editor", "editor_button", WIDTH / 2 - 128, (int) (HEIGHT * 0.50f));
		menuUI.addButton("Quit", "quit_button", WIDTH / 2 - 128, (int) (HEIGHT * 0.60f));
	}
	
	private void updateButtons() {
		if(Mouse.isButtonDown(0)) {
			if(menuUI.isButtonClicked("Play")) {
				StateManager.setState(GameState.GAME);
			}
			
			if(menuUI.isButtonClicked("Editor")) {
				StateManager.setState(GameState.EDITOR);
			}
			
			if(menuUI.isButtonClicked("Quit")) {
				System.exit(0);
			}
		}
	}
	
	public void update() {
		drawQuadTex(background, 0, 0, 2048, 1024);
		menuUI.draw();
		updateButtons();
	}
}
