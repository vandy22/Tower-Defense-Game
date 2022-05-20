package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.loadTexture;
import static helpers.Artist.drawQuadTex;
import static helpers.Leveler.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import ui.UI;
import ui.UI.Menu;

public class Editor {

	private TileGrid grid;
	private int index;
	private TileType[] types;
	
	private UI editorUI;
	private Menu tilePickerMenu;
	private Texture tilePickerUI;

	public Editor() {
		this.grid = loadMap("newMap1");
		this.tilePickerUI = loadTexture("editor_menu_background");
		this.index = 0;
		
		
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		setupUI();
	}
	
	private void setupUI() {
		editorUI = new UI();
		editorUI.createMenu("TilePicker", 1280, 70, 192, 960, 2, 0, 0, false, 0);
		tilePickerMenu = editorUI.getMenu("TilePicker");
		tilePickerMenu.quickAdd("grass", "grass");
		tilePickerMenu.quickAdd("dirt", "dirt");
		tilePickerMenu.quickAdd("water", "water");
	}

	public void update() {
		draw();

		// Handle mouse input
		if(Mouse.next()) {
			if(Mouse.isButtonDown(0)) {
				if(tilePickerMenu.isButtonClicked("grass")) {// && Tower selected = false)
					index = 0;
				}
				else if(tilePickerMenu.isButtonClicked("dirt")) {
					index = 1;
				}else if(tilePickerMenu.isButtonClicked("water")) {
					index = 2;
				}else
					setTile();
			}
		}
		
		while(Keyboard.next()) {
			if(Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
				saveMap("newMap1", grid);
			}
				
		}
	}

	private void setTile() {
		grid.setTile((int) Math.floor(Mouse.getX() / 64), (int) Math.floor((HEIGHT - Mouse.getY() - 1)) / 64, types[index]);
	}
	
	private void draw() {
		drawQuadTex(tilePickerUI, 1280, 0, 192, 960);
		grid.draw();
		editorUI.draw();
	}
	
	public void moveIndex() {
		index++;
		if(index > types.length-1) {
			index = 0;
		}
	}
}
