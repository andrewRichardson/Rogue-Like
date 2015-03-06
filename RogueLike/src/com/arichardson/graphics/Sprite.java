package com.arichardson.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite{
	
	private BufferedImage image;
	private int width;
	private int height;
	private int appearance;
	private String path;
	
	public Sprite(String p, int wid, int ht, int app){
		width = wid;
		height = ht;
		appearance = app;
		path = p;
		try {
			image = ImageIO.read(Sprite.class.getResource("/"+path+appearance+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void setImage(int app){
		appearance = app;
		try {
			image = ImageIO.read(Sprite.class.getResource("/"+path+appearance+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
