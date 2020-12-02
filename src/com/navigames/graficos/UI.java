package com.navigames.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.navigames.main.Game;

public class UI {

	/**
	 * Renderiza a barra de vida do player
	 * @param Graphics g
	 */
	public void render(Graphics g) {
		int playerLife = Game.player.getLife();
		int maxLife = Game.player.getMaxLife();		
		g.setColor(Color.RED);
		g.fillRect(5, 5, maxLife, 8);
		g.setColor(Color.GREEN);
		g.fillRect(5, 5, playerLife, 8);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 7));
		g.drawString("HP", 5, 12);
	}
}
