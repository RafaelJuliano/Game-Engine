package com.navigames.world;

/*=====================================================+
 |             IMPORT DE PACOTES JAVA					|
 +=====================================================*/
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.navigames.entities.*;
import com.navigames.main.Game;
//=============	CRIAÇÃO DA CLASSE =======================//
public class World {
	/*===========================================================+
    |                           VARIÁVEIS                        |
    +===========================================================*/
	private Tile[] tiles;
	public static int WIDTH, HEIGHT;
    /*==================================================+
    |	  			MÉTODO CONSTRUTOR				    |
    +==================================================*/
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
						//floor						
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					}else if(pixelAtual == 0xFFFFFFFF) {
						//parede						
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					}else if(pixelAtual == 0xFF00E1FF) {
						//personagem		
						
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if(pixelAtual == 0xFFFF0000){
						//enemy
						Game.entities.add(new Enemy(xx*16, yy*16,16, 16, Entity.ENEMY_EN));					
					}else if(pixelAtual == 0xFFFF6A00) {
						//weapon 
						Game.entities.add(new Wepon(xx*16, yy*16,16, 16, Entity.WEAPON_EN));
					}else if (pixelAtual == 0xFFFFD800) {
						//lifepack
						Game.entities.add(new LifePack(xx*16, yy*16,16, 16, Entity.LIFEPACK_EN));
					}else if (pixelAtual == 0xFF808080) {
						//ammo
						Game.entities.add(new Bullet(xx*16, yy*16,16, 16, Entity.BULLET_EN));
						
					}
				}
			}
					
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*==========================================================+
    |	                RENDERIZAÇÃO DE GRAFICOS 	         	|
    +==========================================================*/
	public void render(Graphics g) {
		int xstart = Camera.x/16;
		int ystart = Camera.y/16;
		int xfinal = xstart + Game.WIDTH/16;
		int yfinal = ystart + Game.HEIGHT/16;
		for (int xx = xstart; xx <= xfinal; xx ++) {
			for (int yy = ystart; yy <= yfinal; yy ++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) 
					continue;
				Tile tile = tiles[xx +(yy*WIDTH)];
				tile.render(g);
				
			}
		}
	}
	//=========================================================//
}


