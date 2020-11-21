package com.navigames.main;

import java.util.ArrayList;
import java.util.List;
import com.navigames.entities.Entity;
import com.navigames.entities.Player;
import com.navigames.graficos.Spritesheet;
import com.navigames.world.World;

public class Game implements Runnable {

	private Thread thread;
	private boolean isRunning = true;
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;
	public static boolean left, right, up, down;
	public GameScreen screen;

	public static void main(String[] args) {
		Game main = new Game();
		main.start();
	}

	public Game() {
		screen = new GameScreen();
		screen.initFrame();
		entities = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
		world = new World("/map.png");
		entities.add(player);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double frameRate = 60.0;
		double nanosPerFrame = 1000000000 / frameRate;
		while (isRunning) {
			long now = System.nanoTime();
			if ((now - lastTime) / nanosPerFrame >= 1) {
				lastTime = now;
				tick();
				screen.render();
			}
		}
		stop();
	}
}
