package com.arichardson.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite{
	
	public BufferedImage image;
	private int width;
	private int height;
	private int appearance;
	private String path;
	
	public Sprite(String p, int wid, int ht, int app){
		width = wid;
		height = ht;
		appearance = app;
		path = p;
		
		String newPath = "res/"+path+appearance+".png";
		try {
			image = ImageIO.read(new FileInputStream(newPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Image temp = image.getScaledInstance(width, height, BufferedImage.SCALE_REPLICATE);
		
		BufferedImage bimage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(temp, 0, 0, null);
	    bGr.dispose();

	    image = bimage;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setImage(int app){
		appearance = app;
		
		String newPath = "res/"+path+appearance+".png";
		try {
			image = ImageIO.read(new FileInputStream(newPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Image temp = image.getScaledInstance(width, height, BufferedImage.SCALE_REPLICATE);
		
		BufferedImage bimage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(temp, 0, 0, null);
	    bGr.dispose();

	    image = bimage;
	}
	
	public void drawImage(Graphics2D g, int x, int y){
		g.drawImage(image, x*width, y*height, null);
	}
	
}
