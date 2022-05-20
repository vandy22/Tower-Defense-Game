package data;

import org.lwjgl.opengl.Display;

import helpers.Clock;
import helpers.StateManager;

import static helpers.Artist.*;

public class Boot {
	
	public Boot() {
		
		beginSession();

		//Game game = new Game(map);
		
		while(!Display.isCloseRequested()) {
			
			Clock.update();
			//game.update();
			StateManager.update();
			
			Display.update();
			Display.sync(60);
		} 
		
		Display.destroy();
	}
	
	public static void main(String[] args) {
		new Boot();
	}

}
