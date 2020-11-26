package com.navigames.entities;

import com.navigames.main.GameScreen;
import com.navigames.world.World;

/**
 * Faz a movimenta��o da c�mera do jogo e tamb�m auxilia na otimiza��o da renderiza��o.
 * @author Rafael Juliano Ferreira
 *
 */
public class Camera {
	private static int x;
	private static int y;
	
	/**
	 * Impede que a c�mera se movimente quando chega ao limite do mapa.
	 * @param xAtual Recebe a posi��o atual.
	 * @param xMin Recebe o valor minimo permitido no eixo. O ideal � 0.
	 * @param xMax Recebe o valor m�ximo permitido no eixo. O ideal � o limite do mapa.
	 * @return O valor de xAtual sempre dentro dos limites estabelecidos.
	 */
	private static int clamp(int xAtual, int xMin, int xMax) {
		if(xAtual < xMin) {
			xAtual = xMin;
		}
		if(xAtual > xMax) {
			xAtual = xMax;
		}
		return xAtual;
	}
	
	/**
	 * Define quais posi��es do mapa ser�o o ponto de partida da rederiza��o no canvas.
	 * @param refX Ponto de refer�ncia no eixo X do mapa.
	 * @param refY Ponto de refer�ncia no eixo Y do mapa.
	 */
	public static void move(int refX, int refY) {
		x = Camera.clamp(refX, 0, World.WIDTH * 16 - GameScreen.WIDTH);
		y = Camera.clamp(refY, 0, World.HEIGHT * 16 - GameScreen.HEIGHT);		
	}
	
	/**
	 * Converte a posi��o X da entidade no mapa em uma posi��o no canvas.
	 * @param x Recebe a posi��o da entidade no mapa.
	 * @return Retorna a posi��o no canvas.
	 */
	public static int posX(int v) {
		return v - Camera.x;
	}
	
	/**
	 * Converte a posi��o Y da entidade no mapa em uma posi��o no canvas.
	 * @param x Recebe a posi��o da entidade no mapa.
	 * @return Retorna a posi��o no canvas.
	 */
	public static int posY(int v) {
		return v - Camera.y;
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
}
