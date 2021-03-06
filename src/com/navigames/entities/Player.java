package com.navigames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.navigames.main.Game;
import com.navigames.main.GameInput;
import com.navigames.main.GameScreen;
import com.navigames.world.World;

/**
 * Respon�vel pela l�gica e renderiza��o da entidade controlada pelo jogador.
 * 
 * @author Rafael Juliano Ferreira
 *
 */
public class Player extends Entity {
	public double speed = 0.9;
	private boolean direcao = true;
	private boolean moved = false;
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 3;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] rightDmgPlayer;
	private BufferedImage[] leftDmgPlayer;
	private boolean isRFree, isLFree, isUFree, isDFree;
	private int cooldown = 0, damage = 0;
	private int life = 50, maxLife = 50;

	/**
	 * 
	 * @param x      Posi��o inicial no eixo x
	 * @param y      Posi��o inicial no eixo y
	 * @param width  Largura do sprite
	 * @param height Altura do sprite
	 * @param sprite Imagem da renderiza��o inicial.
	 */
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		rightDmgPlayer = new BufferedImage[4];
		leftDmgPlayer = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, World.D_SIZE, World.D_SIZE);
		}
		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, World.D_SIZE, World.D_SIZE);
		}
		for (int i = 0; i < 4; i++) {
			leftDmgPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 80, World.D_SIZE, World.D_SIZE);
		}
		for (int i = 0; i < 4; i++) {
			rightDmgPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 96, World.D_SIZE, World.D_SIZE);
		}
	}

	/**
	 * Verifica se as dire��es ao redor do player est�o livres.
	 */
	private void arround() {
		isRFree = World.isWall((int) (x + speed), (int) y, 2) ? false : true;
		isLFree = World.isWall((int) (x - speed), (int) y, 2) ? false : true;
		isUFree = World.isWall((int) x, (int) (y - speed), 2) ? false : true;
		isDFree = World.isWall((int) x, (int) (y + speed), 2) ? false : true;
	}

	/**
	 * Utiliza as vari�veis l�gicas da classe GameInput para movimentar o player nos
	 * eixos x e y de acordo com a velociade declarada no atributo speed. Tamb�m
	 * utiliza os resultados do m�todo arround() para impedir a movimenta��o atrav�s
	 * de parades.
	 */
	private void move() {
		if (GameInput.isRight && isRFree) {
			x += speed;
		} else if (GameInput.isLeft && isLFree) {
			x -= speed;
		}
		if (GameInput.isUp && isUFree) {
			y -= speed;
		} else if (GameInput.isDown && isDFree) {
			y += speed;
		}
	}
		
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getLife() {
		return this.life;
	}
	
	public int getMaxLife() {
		return this.maxLife;
	}
	
	private void damage() {
		if (cooldown <= 0) {
			if (damage > 0) {
				life -= damage;
				damage = 0;
				cooldown = 120;
			}
		}else {
			cooldown--;
			damage = 0;
		}
	}

	/**
	 * Detecta a movimenta��o do personagem e define o sprite utilizado na
	 * renderiza��o. A vari�vel direcao indica a orienta��o quando h� troca na
	 * dire��o no eixo y. A varivael moved indica a movimenta��o do player,
	 * permitindo a parada da anima��o quando o player permanece im�vel.
	 */
	private void animate() {
		direcao = GameInput.isRight ? true : GameInput.isLeft ? false : direcao;

		if (GameInput.isRight || GameInput.isLeft || GameInput.isUp || GameInput.isDown) {
			moved = true;
		} else {
			moved = false;
		}

		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex)
					index = 0;
			}
		}
	}

	/**
	 * Define as posi��es do player como referencia para movimenta��o da c�mera.
	 */
	public void moveCamera() {
		Camera.move(this.getX() - (GameScreen.WIDTH / 2), this.getY() - (GameScreen.HEIGHT / 2));
	}

	/**
	 * Chama todos os m�todos respons�veis pela l�gica do player.
	 */
	public void update() {		
		animate();
		moveCamera();
		arround();
		move();
		damage();
		if (life == 0) {
			//System.exit(1);
		}
	}

	/**
	 * Desenha o sprite do player na vari�vel image da classe GameScreen.	 * 
	 * @param g BufferedImage a qual o sprite � renderizado.
	 */
	public void render(Graphics g) {
		if (direcao) {
			if (cooldown > 0) {
				g.drawImage(rightDmgPlayer[index], Camera.posX(this.getX()), Camera.posY(this.getY()), null);
			}else {
				g.drawImage(rightPlayer[index], Camera.posX(this.getX()), Camera.posY(this.getY()), null);
			}
		} else if (direcao == false) {
			if (cooldown > 0) {
				g.drawImage(leftDmgPlayer[index], Camera.posX(this.getX()), Camera.posY(this.getY()), null);
			}else {
				g.drawImage(leftPlayer[index], Camera.posX(this.getX()), Camera.posY(this.getY()), null);
			}
		}
	}
}
