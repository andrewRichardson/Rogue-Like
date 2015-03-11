package com.arichardson.graphics;

import java.awt.Graphics2D;

public class TileMap {
	
	public int[][] tileMap;
	public int[][] wallMap;
	public Tile[][] tiles;
	private int width;
	private int height;
	private int tileSize;
	
	public TileMap(int wid, int ht, int tileSiz){
		this.width = wid;
		this.height = ht;
		this.tileSize = tileSiz;
		
		tileMap = new int[width/tileSize][height/tileSize];
		tiles = new Tile[width/tileSize][height/tileSize];
		wallMap = new int[width/tileSize][height/tileSize];
		
		GenerateWalls gen = new GenerateWalls(width/tileSize, height/tileSize);
		
		for(int x = 0; x < tileMap.length; x++){
			for(int y = 0; y < tileMap[0].length; y++){
				tileMap[x][y] = gen.getMap()[x][y];
			}
		}
		
		getSurroundingWalls();
		setTiles();
	}
	
	public void setTiles() {
		for(int x = 0; x < tileMap.length; x++){
			for(int y = 0; y < tileMap[0].length; y++){
				if(tileMap[x][y] == 1){
					tiles[x][y] = new Tile("stone", tileSize, tileSize, 1);
					tiles[x][y].setAppearance(wallMap[x][y]);
				}
			}
		}
	}

	public void getSurroundingWalls(){
		for(int x = 1; x < tileMap.length-1; x++){
			for(int y = 1; y < tileMap[0].length-1; y++){
				wallMap[x][y] = 0;
				if(tileMap[x][y-1] == 0){
					wallMap[x][y] = 1;
				}
				if(tileMap[x-1][y] == 0){
					wallMap[x][y] = 2;
				}
				if(tileMap[x+1][y] == 0){
					wallMap[x][y] = 3;
				}
				if(tileMap[x][y+1] == 0){
					wallMap[x][y] = 4;
				}
				if(tileMap[x][y-1] == 0 && tileMap[x-1][y] == 0){
					wallMap[x][y] = 12;
				}
				if(tileMap[x-1][y] == 0 && tileMap[x+1][y] == 0){
					wallMap[x][y] = 23;
				}
				if(tileMap[x+1][y] == 0 && tileMap[x][y+1] == 0){
					wallMap[x][y] = 34;
				}
				if(tileMap[x][y+1] == 0 && tileMap[x][y-1] == 0){
					wallMap[x][y] = 41;
				}
				if(tileMap[x][y-1] == 0 && tileMap[x-1][y] == 0 && tileMap[x+1][y] == 0){
					wallMap[x][y] = 123;
				}
				if(tileMap[x-1][y] == 0 && tileMap[x+1][y] == 0 && tileMap[x][y+1] == 0){
					wallMap[x][y] = 234;
				}
				if(tileMap[x+1][y] == 0 && tileMap[x][y+1] == 0 && tileMap[x][y-1] == 0){
					wallMap[x][y] = 341;
				}
				if(tileMap[x][y-1] == 0 && tileMap[x-1][y] == 0 && tileMap[x+1][y] == 0 && tileMap[x][y+1] == 0){
					wallMap[x][y] = 1234;
				}
			}
		}
	}
	
	public void drawMap(Graphics2D g){
		for(int x = 0; x < tileMap.length; x++){
			for(int y = 0; y < tileMap[0].length; y++){
				if(tileMap[x][y] == 1){
					tiles[x][y].sprite.drawImage(g, x, y);
				}
			}
		}
	}
	
}
