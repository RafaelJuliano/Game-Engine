package com.navigames.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.navigames.main.Game;
import com.navigames.world.World;

public class Enemy extends Entity {

	private static BufferedImage[] enemySprite;
	Random rand = new Random();
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 3;
	private int count = rand.nextInt(1000);
	private int damage = 5;
	private double speed = 0.8;
	private boolean isRFree, isLFree, isUFree, isDFree, isRBlck, isLBlck, isUBlck, isDBlck;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		enemySprite = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			enemySprite[i] = Game.spritesheet.getSprite(2 * 16 + (i * 16), 2 * 16, World.D_SIZE, World.D_SIZE);
		}
	}

	/**
	 * Faz com que a cada x frames, o sprite de renderização seja alterado.
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
	 * Verifica se as direções ao redor do enemy estão livres.
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
		
		isRBlck = World.isWall(right[0]+1, right[1]+1, 2);
		isLBlck = World.isWall(left[0]-1, left[1], 2);
		isUBlck = World.isWall(up[0], up[1], 2);
		isDBlck = World.isWall(down[0]-1, down[1], 2);
	}
	
	/**
	 * Verifica colisão entre outros inimigos do mapa.
	 * @param x posição x do retangulo a ser verificado.
	 * @param y posição y do retangulo a ser verificado.
	 * @param p Valor para reduzir a largura do tamanho padrão do sistema.
	 * @return Valor booleano de colidindo ou não.
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
	
	/**
	 * Verifica colisão com o player e sinaliza ataque do inimigo.
	 * @return Se está colidindo.
	 */
	public boolean isCollidingWithPlayer() {
		int w = World.D_SIZE - 5;
		int h = World.D_SIZE;
		Rectangle enemy = new Rectangle(this.getX(), this.getY(), w, h);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), w, h);
		if (enemy.intersects(player)) {
			Game.player.setDamage(damage);
		}
		return enemy.intersects(player);		
	}
	
	private void move() {
		int plx = Game.player.getX();
		int ply = Game.player.getY();
		
		if (count <= 0) {
			count = rand.nextInt(1500);
		}		
		
		if (count > 450 && isDBlck && ply > (int)y && isLFree || count > 450 && isUBlck && ply < (int)y  && isLFree) {
			x -= speed;			
		}else if (plx > (int)x && isRFree) {
			x += speed;
		}else  if (plx < (int)x && isLFree) {
			x -= speed;
		}		
		
		if (count > 450 && isRBlck && plx > (int)x && isUFree || count > 450 && isLBlck && plx < (int)x  && isUFree ) {
			y -= speed;
		}else if (ply < (int)y && isUFree) {		
			y -= speed;
		}else if (ply > (int)y && isDFree) {
			y += speed;
		}
		count--;
	}		

	/**
	 * Executa toda lógica do objeto.
	 */
	public void update() {
		animate();
		arround();
		if (!isCollidingWithPlayer())
			move();
	}

	public void render(Graphics g) {
		g.drawImage(enemySprite[index], Camera.posX(this.getX()), Camera.posY(this.getY()), null);
	}
}
