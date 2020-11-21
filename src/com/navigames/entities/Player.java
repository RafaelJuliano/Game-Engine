package com.navigames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.navigames.main.Game;
import com.navigames.main.GameScreen;
import com.navigames.world.World;

public class Player extends Entity {
	public boolean right, up, left, down;
	public double speed = 0.9;
	private boolean dir = true;
	private boolean moved = false;
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 3;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
		}
		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
		}
	}

	public void tick() {
		x = right ? x + speed : left ? x - speed : x;
		y = up ? y - speed : down ? y + speed : y;
		moved = up || down || left || right ? true : false;
		dir = left ? false : right ? true : dir;

		// LÓGICA DE ANIMAÇÃO
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex)
					index = 0;
			}
		}
		Camera.x = Camera.clamp(this.getX() - (GameScreen.WIDTH / 2), 0, World.WIDTH * 16 - GameScreen.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (GameScreen.HEIGHT / 2), 0, World.HEIGHT * 16 - GameScreen.HEIGHT);
	}

	public void render(Graphics g) {
		if (dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (dir == false) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
