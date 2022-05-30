package ui;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class UI {

	private ArrayList<Button> buttonList;
	private ArrayList<Menu> menuList;
	
	public int currentButtonTexture = 0;
	
	public UI() {
		buttonList = new ArrayList<Button>();
		menuList = new ArrayList<Menu>();
	}
	
	public void addButton(String name, String textureName, int x, int y) {
		buttonList.add(new Button(name, loadTexture(textureName), x, y));
	}
	//Two textures
	public void addButton(String name, String[] textureNames, int x, int y) {
		Texture[] textures = new Texture[textureNames.length];
		for(int i = 0; i < textures.length; i++) {
			textures[i] = loadTexture(textureNames[i]);
		}
		buttonList.add(new Button(name, textures, x, y));
	}

	public boolean isButtonClicked(String buttonName) {
		Button b = getButton(buttonName);
		float mouseY = HEIGHT - Mouse.getY() - 1;
		if(Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getW() && mouseY > b.getY() && mouseY < b.getY() + b.getH()) {
			return true;
		}
		return false;
	}
	
	public Button getButton(String buttonName) {
		for(Button b: buttonList) {
			if(b.getName() == buttonName) {
				return b;
			}
		}
		return null;
	}
	
	public void draw() {
		for(Button b: buttonList) {
			if(!b.isMultipleTextures()) {
				drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getW(), b.getH());
			}else {
				drawQuadTex(b.getTextures()[b.getCurrentButtonTexture()], b.getX(), b.getY(), b.getW(), b.getH());
			}
				
		}
		for(Menu m: menuList) {
			m.draw();
		}
	}
	
	public void createMenu(String name, int x, int y, int w, int h, int optionsWidth, int optionsHeight, int fontSize, boolean customPad, int customPadding) {
		menuList.add(new Menu(name, x, y, w, h, optionsWidth, optionsHeight, fontSize, customPad, customPadding));
	}
	
	public Menu getMenu(String name) {
		for(Menu m: menuList) {
			if(name.equals(m.getName())) {
				return m;
			}
		}
		return null;
	}
	
	public class Menu{
		
		String name;
		private ArrayList<Button> menuButtons;
		private int x, y, w, h, numOfButtons, numOfCols, optionsWidth, optionsHeight, padding;
		
		private TrueTypeFont font;
		private Font awtFont;
		private int fontSize;
		
		public Menu(String name, int x, int y, int w, int h, int optionsWidth, int optionsHeight, int fontSize, boolean customPad, int customPadding) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.optionsWidth = optionsWidth;
			this.optionsHeight = optionsHeight;
			this.fontSize = fontSize;
			if(customPad)
				this.padding = customPadding;
			else
				this.padding = (w - optionsWidth * TILE_SIZE) / (optionsWidth + 1);
			
			this.numOfButtons = 0;
			this.numOfCols = 0;
			this.menuButtons = new ArrayList<Button>();
			
			awtFont = new Font("Times New Roman", Font.BOLD, fontSize);
			font = new TrueTypeFont(awtFont, false);
		}
		
		
		
		public void drawString(String text, int x, int y) {
			font.drawString(x, y, text);
		}
		
		public void addButton(Button b) {
			setButton(b);
		}
		
		public void setButton(Button b) {
			if(optionsWidth != 0)
				b.setY(y + (numOfButtons / optionsWidth) * (padding + TILE_SIZE) + padding);
			b.setX(x + (numOfButtons % optionsWidth) * (padding + TILE_SIZE) + padding);
			System.out.println(b.getName());
			numOfButtons++;
			menuButtons.add(b);
		}
		
		public void updateFont(int fontSize) {
			this.fontSize = fontSize;
			awtFont = new Font("Times New Roman", Font.BOLD, fontSize);
			font = new TrueTypeFont(awtFont, false);
			System.out.println("running");
		}
		
		public void update() {
			numOfButtons = 0;
			for(Button b: menuButtons) {
				b.setY(y + (numOfButtons / optionsWidth) * (padding + TILE_SIZE) + padding);
				b.setX(x + (numOfButtons % optionsWidth) * (padding + TILE_SIZE) + padding);
				numOfButtons++;
			}
			
			
		}
		
		public boolean isButtonClicked(String buttonName) {
			Button b = getButton(buttonName);
			float mouseY = HEIGHT - Mouse.getY() - 1;
			if(Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getW() && mouseY > b.getY() && mouseY < b.getY() + b.getH()) {
				return true;
			}
			return false;
		}
		
		public Button getButton(String buttonName) {
			for(Button b: menuButtons) {
				if(b.getName() == buttonName) {
					return b;
				}
			}
			return null;
		}
		
		public void quickAdd(String buttonName, String buttonTextureName) {
			Button b = new Button(buttonName, loadTexture(buttonTextureName), 0, 0);
			setButton(b);
		}
		
		public void draw() {
			for(Button b: menuButtons) {
				drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getW(), b.getH());
			}
		}
		
		public ArrayList<Button> getMenuButtons(){
			return menuButtons;
		}
		
		public String getName() {
			return name;
		}
		
		public void setX(int x) {
			this.x = x;
		}
		
		public int getX() {
			return x;
		}
		
		public void setY(int y) {
			this.y = y;
		}
		
		public int getY() {
			return y;
		}

		public TrueTypeFont getFont() {
			return font;
		}

		public void setFont(TrueTypeFont font) {
			this.font = font;
		}

		public int getFontSize() {
			return fontSize;
		}

		public void setFontSize(int fontSize) {
			this.fontSize = fontSize;
		}
		
	}
}
