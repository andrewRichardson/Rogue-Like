package com.arichardson.graphics;

public class Tile {
	
	public Sprite sprite;
	private int layer;
	private int appearance;
	
	public Tile(String path, int width, int height, int layer){
		sprite = new Sprite(path, width, height, 0);
		this.layer = layer;
	}
	
	public int getLayer(){
		return layer;
	}
	
	public void setAppearance(int app){
		appearance = app;
		sprite.setImage(appearance);
	}
	
	public int getAppearance(){
		return appearance;
	}
}
