package com.navigames.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.navigames.main.Game;
import com.navigames.world.World;

public class Enemy extends Entity {

	private static BufferedImage[] enemySprite;
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 3;
	private double speed = 0.4;
	private boolean isRFree, isLFree, isUFree, isDFree;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		enemySprite = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			enemySprite[i] = Game.spritesheet.getSprite(2 * 16 + (i * 16), 2 * 16, World.D_SIZE, World.D_SIZE);
		}
	}

	/**
	 * Faz com que a cada x frames, o sprite de renderiza��o seja alterado.
	 */
	private void animate() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex)
				index = 0;
		}
	}
	
	/**
	 * Verifica se as dire��es ao redor do enemy est�o livres.
	 */
	private void arround() {
		int right[] = {(int) (x + speed), (int) y};
		int left[] = {(int) (x - speed), (int) y};
		int up[] = {(int) x, (int) (y - speed)};
		int down[] = {(int) x, (int) (y + speed)};
		
		isRFree = World.isWall(right[0], right[1], 2) ? false : isColliding(right[0], right[1], 8)? false:true;
		isLFree = World.isWall(left[0], left[1], 2) ? false : isColliding(left[0], left[1], 8)? false:true;
		isUFree = World.isWall(up[0], up[1], 2) ? false : isColliding(up[0], up[1], 8)? false:true;
		isDFree = World.isWall(down[0], down[1], 2) ? false : isColliding(down[0], down[1], 8)? false:true;
	}
	
	/**
	 * Verifica colis�o entre outros inimigos do mapa.
	 * @param x posi��o x do retangulo a ser verificado.
	 * @param y posi��o y do retangulo a ser verificado.
	 * @param p Valor para reduzir a largura do tamanho padr�o do sistema.
	 * @return Valor booleano de colidindo ou n�o.
	 */
	private boolean isColliding(int x, int y, int p) {
		int w = World.D_SIZE - p;
		int h = World.D_SIZE;
		Rectangle currentEnemy = new Rectangle(x, y, w, h);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), w, h);
			if (currentEnemy.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}
	
	private void move() {
		int plx = Game.player.getX();
		int ply = Game.player.getY();
		
		if (plx > x && isRFree) {
			x += speed;
		}else  if (plx < x && isLFree) {
			x -= speed;			
		}
		if (ply < y && isUFree) {
			y -= speed;		
		}else if (ply > y && isDFree) {
			y += speed;
		}
	}		

	/**
	 * Executa toda l�gica do objeto.
	 */
	public void update() {
		animate();
		arround();
		move();
	}

	public void render(Graphics g) {
		g.drawImage(enemySprite[index], Camera.posX(this.getX()), Camera.posY(this.getY()), null);
	}
}
