package data;

import org.lwjgl.opengl.Display;

import audio.AudioLibrary;
import audio.AudioMaster;
import helpers.Clock;
import helpers.StateManager;

import static helpers.Artist.*;

public class Boot {
	
	public Boot() {
		
		AudioMaster.init();
		AudioMaster.setListenerData();
		
		beginSession();
		
		while(!Display.isCloseRequested()) {
			
			Clock.update();
			//game.update();
			StateManager.update();
			
			Display.update();
			Display.sync(120);
		} 
		
		AudioLibrary.audioSource.delete();
		AudioMaster.cleanUp();
		Display.destroy();
	}
	
	public static void main(String[] args) {
		new Boot();
	}

}
