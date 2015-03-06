package com.arichardson.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.arichardson.graphics.Tile;

public class Game extends Canvas implements Runnable {
	
	//BASE VARIABLES
	private static final long serialVersionUID = 1L;

	private int width = 1280;
	private int height = (int) ((double) width / (16.0 / 9.0));

	private boolean running = false;

	private Thread thread;

	private JFrame frame;

	private int ups = 0;
	private int fps = 0;

	private static String title = "Rogue Like";
	//BASE VARIABLES
	
	//VARIABLES
	Tile stone;
	
	public Game() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		
		stone = new Tile("stone", 16, 16, 1);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame = new JFrame(title);
		game.frame.setResizable(false);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		game.frame.setVisible(true);
		game.start();

	}

	public synchronized void start() {
		running = true;

		thread = new Thread(this, "Rogue Like Game Thread");
		thread.start();
	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long oldTime = System.nanoTime();
		long timer = System.currentTimeMillis();

		double ns = 1000000000.0 / 60.0;
		long newTime;
		double delta = 0;

		while (running) {
			newTime = System.nanoTime();
			delta += (double) (newTime - oldTime) / ns;
			oldTime = newTime;
			if (delta >= 1) {
				update();
				delta--;
				ups++;
			}
			render();
			fps++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + fps + " fps");
				System.out.println(ups + " ups, " + fps + " fps");
				fps = 0;
				ups = 0;
			}
		}
		stop();
	}

	private void update() {

	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		g.setColor(new Color(0x000000));
		g.fillRect(0, 0, width, height);

		/*
		 * BufferedImage b = new BufferedImage(width, height,
		 * BufferedImage.TYPE_INT_RGB); int[] pixels = ((DataBufferInt)
		 * b.getRaster().getDataBuffer()).getData(); for(int y = 0; y < height;
		 * y++){ for(int x = 0; x < width; x++){ pixels[x + y * width] =
		 * 0xffffff+(int)(Math.random()*100000000); } }
		 * 
		 * g.drawImage(b, 0, 0, null);
		 */

		g.dispose();
		bs.show();
	}

}
