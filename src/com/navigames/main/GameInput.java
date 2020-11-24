package com.navigames.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Identifica eventos do teclado e os registra em vari�veis l�gicas p�blicas.
 * @author Rafael Juliano Ferreira
 *
 */
public class GameInput implements KeyListener {
	
	public static boolean isRight, isLeft, isUp, isDown = false;
	
	/**
	* Identifica quando uma tecla � pressionada e torna verdadeira a respectiva condi��o apra execu��o da
	* l�gica na classe Player.
	* Quando a tecla RIGHT ou D � pressioanda, a condi��o right recebe true.
	* Quando a tecla LEFT ou A � pressioanda, a condi��o left recebe true.
	* Quando a tecla UP ou W � pressioanda, a condi��o up recebe true.
	* Quando a tecla DOWN ou S � pressioanda, a condi��o down recebe true.	
	*/
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		isRight |= code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D;
		isLeft |= code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A;
		isUp |= code == KeyEvent.VK_UP || code == KeyEvent.VK_W;
		isDown |= code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S;
	}

	/**
	* Identifica quando uma tecla deixa de ser pressionada e informa a classe Player qual l�gica deve interrompida.
	* Quando a tecla RIGHT ou D deixa de ser pressionada, a condi��o right recebe false.
	* Quando a tecla LEFT ou A deixa de ser pressionada, a condi��o left recebe false.
	* Quando a tecla UP ou W deixa de ser pressionada, a condi��o up recebe false.
	* Quando a tecla DOWN ou S deixa de ser pressionada, a condi��o down recebe false.	
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