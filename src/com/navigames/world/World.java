package com.navigames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.navigames.entities.*;
import com.navigames.main.Game;
import com.navigames.main.GameScreen;

public class World {

	private Tile[] tiles;
	private int[] pixels;
	public static int WIDTH, HEIGHT;
	private final int FLOOR = 0xFF000000;
	private final int WALL = 0xFFFFFFFF;
	private final int PLAYER = 0xFF00E1FF;
	private final int ENEMY = 0xFFFF0000;
	private final int WEAPON = 0xFFFF6A00;
	private final int LIFE_PACK = 0xFFFFD800;
	private final int BULLET = 0xFF808080;
	private int index;

	private BufferedImage map;

	public World(String path) {
		try {
			map = ImageIO.read(getClass().getResource(path));

		} catch (IOException e) {
			e.printStackTrace();
		}

		WIDTH = map.getWidth();
		HEIGHT = map.getHeight();
		pixels = new int[WIDTH * HEIGHT];
		tiles = new Tile[WIDTH * HEIGHT];
		map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
		drawMap();		
	}
	
	private void drawMap() {
		for (int yy = 0; yy < HEIGHT; yy++) {
			for (int xx = 0; xx < WIDTH; xx++) {

				index = xx + (yy * WIDTH);
				int posX = xx * 16;
				int posY = yy * 16;

				int pixelAtual = pixels[index];
				tiles[index] = new FloorTile(posX, posY, Tile.TILE_FLOOR);

				switch (pixelAtual) {
				case FLOOR:
					tiles[index] = new FloorTile(posX, posY, Tile.TILE_FLOOR);
					break;
				case WALL:
					tiles[index] = new WallTile(posX, posY, Tile.TILE_WALL);
					break;
				case PLAYER:
					Game.player.setX(posX);
					Game.player.setY(posY);
					break;
				case ENEMY:
					Game.entities.add(new Enemy(posX, posY, 16, 16, Entity.ENEMY_EN));
					break;
				case WEAPON:
					Game.entities.add(new Wepon(posX, posY, 16, 16, Entity.WEAPON_EN));
					break;
				case LIFE_PACK:
					Game.entities.add(new LifePack(posX, posY, 16, 16, Entity.LIFEPACK_EN));
					break;
				case BULLET:
					Game.entities.add(new Bullet(posX, posY, 16, 16, Entity.BULLET_EN));
					break;
				default:
					tiles[index] = new FloorTile(posX, posY, Tile.TILE_FLOOR);
					break;
				}
			}
		}
	}

	public void render(Graphics g) {
		int xstart = Camera.x / 16;
		int ystart = Camera.y / 16;
		int xfinal = xstart + GameScreen.WIDTH / 16;
		int yfinal = ystart + GameScreen.HEIGHT / 16;
		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				index = xx + (yy * WIDTH);
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[index];
				tile.render(g);

			}
		}
	}
}
