package com.navigames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.navigames.main.Game;

public class Enemy extends Entity {

	private static BufferedImage[] enemySprite;
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 3;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		enemySprite = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			enemySprite[i] = Game.spritesheet.getSprite(2 * 16 + (i * 16), 2 * 16, 16, 16);
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
	 * Executa toda lógica do objeto.
	 */
	public void update() {
		animate();
	}

	public void render(Graphics g) {
		g.drawImage(enemySprite[index], Camera.posX(this.getX()), Camera.posY(this.getY()), null);
	}
}
