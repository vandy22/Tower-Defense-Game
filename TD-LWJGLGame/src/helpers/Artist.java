package helpers;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {

	public static final int WIDTH = 1472, HEIGHT = 960;
	public static final int TILE_SIZE = 64;
	
	public static void beginSession() {
		Display.setTitle("TD Game");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); 
		
	}
	
	public static boolean checkCollision(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
		if(x1 + w1 > x2 && x1 < x2 + w2 && y1 + h1 > y2 && y1 < y2 + h2) {
			return true;
		}
		return false;
	}
	
	public static void drawQuad(float x, float y, float w, float h) {
		glBegin(GL_QUADS);
		glVertex2f(x,y); //Top left corner
		glVertex2f(x+w,y); //Top right corner
		glVertex2f(x+w,y+h); //Bottom right corner
		glVertex2f(x,y+h); //Bottom left corner
		glEnd();
		glLoadIdentity();
	}
	
	public static void drawQuadTrans(float x, float y, float w, float h) {
		glBegin(GL_LINES);
		//glVertex2f(x,y); //Top left corner
		//glVertex2f(x+w,y); //Top right corner
		//glColor3f(1.0f, 0.0f, 0.0f);
		glVertex2f(10,10); //Top left corner
		glVertex2f(100,100); //Top right corner
		//glVertex2f(x+w,y+h); //Bottom right corner
		//glVertex2f(x,y+h); //Bottom left corner
		glEnd();
	}
	
	public static void drawQuadTex(Texture tex, float x, float y, float w, float h) {
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		
		glTexCoord2f(0,0);
		glVertex2f(0,0);
		
		glTexCoord2f(1,0);
		glVertex2f(w, 0);
		
		glTexCoord2f(1, 1);
		glVertex2f(w,h);
		
		glTexCoord2f(0,1);
		glVertex2f(0,h);
		
		glEnd();
		glLoadIdentity();
	}
	
	public static void drawQuadTexRot(Texture tex, float x, float y, float w, float h, float angle) {
		tex.bind();
		glTranslatef(x+(w/2) , y+(h/2) , 0);
		glRotatef(angle, 0, 0, 1);
		glTranslatef(- w / 2, - h / 2, 0);
		glBegin(GL_QUADS);
		
		
		glTexCoord2f(0,0);
		glVertex2f(0,0);
		
		glTexCoord2f(1,0);
		glVertex2f(w, 0);
		
		glTexCoord2f(1, 1);
		glVertex2f(w,h);
		
		glTexCoord2f(0,1);
		glVertex2f(0,h);
		
		glEnd();
		glLoadIdentity();
	}
	
	public static Texture loadTexture(String imgName) {
		//All textures must be set as PNG
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream("res/"+imgName+".png");
		try {
			tex = TextureLoader.getTexture("PNG", in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
}
