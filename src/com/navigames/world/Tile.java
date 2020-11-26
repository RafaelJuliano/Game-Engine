package com.navigames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.navigames.entities.Camera;
import com.navigames.main.Game;

/**
 * Classe pai de todos os objetos do mapa, como chão e parede.
 * @author Rafael Juliano Ferreira
 *
 */
public class Tile {
	
	public static final BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static final BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);

	private BufferedImage sprite;
	private int x, y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,Camera.posX(x), Camera.posY(y) , null);
	}
	
}
