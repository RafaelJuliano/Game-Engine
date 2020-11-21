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
	public static int WIDTH, HEIGHT;

	public World (String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));			
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();			
			int[] pixels = new int[WIDTH * HEIGHT];
			tiles = new Tile[WIDTH * HEIGHT];			
			map.getRGB(0,  0 , WIDTH, HEIGHT, pixels, 0, WIDTH);			
			for (int xx = 0; xx < WIDTH; xx++) {
				for(int yy = 0; yy < HEIGHT; yy ++) {
					int pixelAtual = pixels[xx +(yy*WIDTH)];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					if (pixelAtual == 0xFF000000) {		
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					}else if(pixelAtual == 0xFFFFFFFF) {					
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					}else if(pixelAtual == 0xFF00E1FF) {						
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if(pixelAtual == 0xFFFF0000){
						Game.entities.add(new Enemy(xx*16, yy*16,16, 16, Entity.ENEMY_EN));					
					}else if(pixelAtual == 0xFFFF6A00) {
						Game.entities.add(new Wepon(xx*16, yy*16,16, 16, Entity.WEAPON_EN));
					}else if (pixelAtual == 0xFFFFD800) {
						Game.entities.add(new LifePack(xx*16, yy*16,16, 16, Entity.LIFEPACK_EN));
					}else if (pixelAtual == 0xFF808080) {
						Game.entities.add(new Bullet(xx*16, yy*16,16, 16, Entity.BULLET_EN));						
					}
				}
			}				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void render(Graphics g) {
		int xstart = Camera.x/16;
		int ystart = Camera.y/16;
		int xfinal = xstart + GameScreen.WIDTH/16;
		int yfinal = ystart + GameScreen.HEIGHT/16;
		for (int xx = xstart; xx <= xfinal; xx ++) {
			for (int yy = ystart; yy <= yfinal; yy ++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) 
					continue;
				Tile tile = tiles[xx +(yy*WIDTH)];
				tile.render(g);
				
			}
		}
	}
}


