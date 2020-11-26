package com.navigames.entities;

import com.navigames.main.GameScreen;
import com.navigames.world.World;

/**
 * Faz a movimentação da câmera do jogo e também auxilia na otimização da renderização.
 * @author Rafael Juliano Ferreira
 *
 */
public class Camera {
	private static int x;
	private static int y;
	
	/**
	 * Impede que a câmera se movimente quando chega ao limite do mapa.
	 * @param xAtual Recebe a posição atual.
	 * @param xMin Recebe o valor minimo permitido no eixo. O ideal é 0.
	 * @param xMax Recebe o valor máximo permitido no eixo. O ideal é o limite do mapa.
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
	 * Define quais posições do mapa serão o ponto de partida da rederização no canvas.
	 * @param refX Ponto de referência no eixo X do mapa.
	 * @param refY Ponto de referência no eixo Y do mapa.
	 */
	public static void move(int refX, int refY) {
		x = Camera.clamp(refX, 0, World.WIDTH * 16 - GameScreen.WIDTH);
		y = Camera.clamp(refY, 0, World.HEIGHT * 16 - GameScreen.HEIGHT);		
	}
	
	/**
	 * Converte a posição X da entidade no mapa em uma posição no canvas.
	 * @param x Recebe a posição da entidade no mapa.
	 * @return Retorna a posição no canvas.
	 */
	public static int posX(int v) {
		return v - Camera.x;
	}
	
	/**
	 * Converte a posição Y da entidade no mapa em uma posição no canvas.
	 * @param x Recebe a posição da entidade no mapa.
	 * @return Retorna a posição no canvas.
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
