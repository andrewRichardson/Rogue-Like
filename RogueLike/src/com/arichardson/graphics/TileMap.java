package com.arichardson.graphics;

public class TileMap {
	
	public int[][] tileMap;
	public Tile[][] tiles;
	private int width;
	private int height;
	private int tileSize;
	
	public TileMap(int width, int height, int tileSize){
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		
		tileMap = new int[width/tileSize][height/tileSize];
	}
	
}
