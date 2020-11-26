package com.navigames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.navigames.main.Game;
import com.navigames.main.GameInput;
import com.navigames.main.GameScreen;
import com.navigames.world.World;

/**
 * Responável pela lógica e renderização da entidade controlada pelo jogador.
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
	private boolean isRFree, isLFree, isUFree, isDFree;

	/**
	 * 
	 * @param x      Posição inicial no eixo x
	 * @param y      Posição inicial no eixo y
	 * @param width  Largura do sprite
	 * @param height Altura do sprite
	 * @param sprite Imagem da renderização inicial.
	 */
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, World.D_SIZE, World.D_SIZE);
		}
		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, World.D_SIZE, World.D_SIZE);
		}
	}

	/**
	 * Verifica se as direções ao redor do player estão livres.
	 */
	private void arround() {
		isRFree = World.isWall((int) (x + speed), (int) y, 2) ? false : true;
		isLFree = World.isWall((int) (x - speed), (int) y, 2) ? false : true;
		isUFree = World.isWall((int) x, (int) (y - speed), 2) ? false : true;
		isDFree = World.isWall((int) x, (int) (y + speed), 2) ? false : true;
	}

	/**
	 * Utiliza as variáveis lógicas da classe GameInput para movimentar o player nos
	 * eixos x e y de acordo com a velociade declarada no atributo speed. Também
	 * utiliza os resultados do método arround() para impedir a movimentação através
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

	/**
	 * Detecta a movimentação do personagem e define o sprite utilizado na
	 * renderização. A variável direcao indica a orientação quando há troca na
	 * direção no eixo y. A varivael moved indica a movimentação do player,
	 * permitindo a parada da animação quando o player permanece imóvel.
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
	 * Define as posições do player como referencia para movimentação da câmera.
	 */
	public void moveCamera() {
		Camera.move(this.getX() - (GameScreen.WIDTH / 2), this.getY() - (GameScreen.HEIGHT / 2));
	}

	/**
	 * Chama todos os métodos responsáveis pela lógica do player.
	 */
	public void update() {
		move();
		animate();
		moveCamera();
		arround();
	}

	/**
	 * Desenha o sprite do player na variável image da classe GameScreen.
	 * 
	 * @param g BufferedImage a qual o sprite é renderizado.
	 */
	public void render(Graphics g) {
		if (direcao) {
			g.drawImage(rightPlayer[index], Camera.posX(this.getX()), Camera.posY(this.getY()), null);
		} else if (direcao == false) {
			g.drawImage(leftPlayer[index], Camera.posX(this.getX()), Camera.posY(this.getY()), null);
		}
	}
}
