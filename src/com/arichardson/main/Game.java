package com.arichardson.main;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL12;

public class Game {

	public static void main(String[] args) throws Exception{
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.create();
		
		BufferedImage image = ImageIO.read(Game.class.getResource("/floor.png"));
		
		while(!Display.isCloseRequested()){
			setCamera();
			drawBackground();
			drawImage(image, 250, 200);
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
	}
	
	private static final int BYTES_PER_PIXEL = 4;
	
	public static void drawImage(BufferedImage image, int xx, int yy){
		int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
        
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

        // You now have a ByteBuffer filled with the color data of each pixel.
        // Now just create a texture ID and bind it. Then you can load it using 
        // whatever OpenGL method you want, for example:
        
        int textureID = glGenTextures();
        
        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
        
        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        
        glEnable(GL_TEXTURE_2D);
        
        glPushMatrix();
        glTranslatef(xx, yy, 0);
        glBindTexture(GL_TEXTURE_2D, textureID);
        glBegin(GL_QUADS);
        {
           glTexCoord2f(0, 0);
           glVertex2f(0, 0);
           
           glTexCoord2f(1, 0);
           glVertex2f(image.getWidth(), 0);
           
           glTexCoord2f(1, 1);
           glVertex2f(image.getWidth(), image.getHeight());
           
           glTexCoord2f(0, 1);
           glVertex2f(0, image.getHeight());
        }
        glEnd();
        glPopMatrix();
	}
	
	public static void drawRect(double red, double green, double blue, double x, double y, double width, double height){
		glBegin(GL_QUADS);
		glColor3d(red, green, blue);
		glVertex2d(x, y);
		glVertex2d(x+width, y);
		
		glVertex2d(x+width, y+height);
		glVertex2d(x, y+height);
		glEnd();
	}
	
	public static void drawRectGradient(double red, double green, double blue, double red2, double green2, double blue2, double x, double y, double width, double height){
		glBegin(GL_QUADS);
		glColor3d(red, green, blue);
		glVertex2d(x, y);
		glVertex2d(x+width, y);
		
		glColor3d(red2, green2, blue2);
		glVertex2d(x+width, y+height);
		glVertex2d(x, y+height);
		glEnd();
	}
	
	public static void drawBackground() {
		drawRectGradient(0.7, 0.8, 0.9, 0.5, 0.6, 0.8, 0, 0, 640, 480);
		
		drawRect(0.5, 0.4, 0.4, 0, 0, 640, 32);
		
		drawRect(0.2, 0.8, 0.2, 0, 25, 640, 7);
	}

	public static void setCamera(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		glOrtho(0, 640, 0, 480, -1, 1);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

}