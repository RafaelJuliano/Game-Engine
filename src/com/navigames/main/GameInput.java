package com.navigames.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameInput implements KeyListener {
	
	/**
	* Identifica quando uma tecla é pressionada e torna verdadeira a respectiva condição apra execução da
	* lógica na classe Player.
	* Quando a tecla RIGHT ou D é pressioanda, a condição right recebe true.
	* Quando a tecla LEFT ou A é pressioanda, a condição left recebe true.
	* Quando a tecla UP ou W é pressioanda, a condição up recebe true.
	* Quando a tecla DOWN ou S é pressioanda, a condição down recebe true.	
	*/
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		Game.player.right |= code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D;
		Game.player.left |= code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A;
		Game.player.up |= code == KeyEvent.VK_UP || code == KeyEvent.VK_W;
		Game.player.down |= code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S;
	}

	/**
	* Identifica quando uma tecla deixa de ser pressionada e informa a classe Player qual lógica deve interrompida.
	* Quando a tecla RIGHT ou D deixa de ser pressionada, a condição right recebe false.
	* Quando a tecla LEFT ou A deixa de ser pressionada, a condição left recebe false.
	* Quando a tecla UP ou W deixa de ser pressionada, a condição up recebe false.
	* Quando a tecla DOWN ou S deixa de ser pressionada, a condição down recebe false.	
	*/
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		Game.player.right = code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D ? false : Game.player.right;
		Game.player.left = code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A ? false : Game.player.left;
		Game.player.up = code == KeyEvent.VK_UP || code == KeyEvent.VK_W ? false : Game.player.up;
		Game.player.down = code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S ? false : Game.player.down;
	}

	public void keyTyped(KeyEvent e) {
	}

}