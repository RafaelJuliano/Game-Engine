package com.navigames.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameInput implements KeyListener{

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();		
		Game.player.right |= code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D;
		Game.player.left |= code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A;
		Game.player.up |= code == KeyEvent.VK_UP || code == KeyEvent.VK_W;
		Game.player.down |= code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S;
	}


	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();		
		Game.player.right = code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D? false:Game.player.right;
		Game.player.left = code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A? false:Game.player.left;
		Game.player.up = code == KeyEvent.VK_UP || code == KeyEvent.VK_W? false:Game.player.up;
		Game.player.down = code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S? false:Game.player.down;
	}
	
	public void keyTyped(KeyEvent e) {}
	


	
}