package com.navigames.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Identifica eventos do teclado e os registra em variáveis lógicas públicas.
 * @author Rafael Juliano Ferreira
 *
 */
public class GameInput implements KeyListener {
	
	public static boolean isRight, isLeft, isUp, isDown = false;
	
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
		isRight |= code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D;
		isLeft |= code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A;
		isUp |= code == KeyEvent.VK_UP || code == KeyEvent.VK_W;
		isDown |= code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S;
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
		isRight = code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D ? false : isRight;
		isLeft = code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A ? false : isLeft;
		isUp = code == KeyEvent.VK_UP || code == KeyEvent.VK_W ? false : isUp;
		isDown = code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S ? false : isDown;
	}

	public void keyTyped(KeyEvent e) {
	}

}