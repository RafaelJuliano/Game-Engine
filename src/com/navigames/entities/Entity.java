package com.navigames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.navigames.main.Game;

/**
 * Classe pai de todas as entidades do jogo.
 * @author Rafael Juliano Ferreira
 */
public class Entity {    
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6*16, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(6*16, 16, 16, 16);
	public static BufferedImage ENEMY_EN =  Game.spritesheet.getSprite(2*16, 2*16, 16, 16);	
	protected double x;
	protected double y;
	protected int width;
	protected int height;	
	private BufferedImage sprite;
	/**
	 * 
	 * @param x Posição inicial no eixo x
	 * @param y Posição inicial no eixo y
	 * @param width Largura do sprite
	 * @param height Altura do sprite
	 * @param sprite Imagem da renderização inicial.
	 */
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	/**
	 * Define novo valor para x.
	 * @param newX Valor a ser atribuido em x.
	 */
    public void setX(int newX){
        this.x = newX;
    }
    
    /**
     * Define novo valor para y.
     * @param newY Valor a ser atribuido em y.
     */
    public void setY(int newY){
        this.y = newY;
    }
    
    /**
     * Retorna o valor de x da entidade
     * @return Valor x do tipo double convertido para tipo inteiro.
     */
	public int getX() {
		return (int)this.x;
	}
	
	/**
	 * Retorna o valor de y da entidade
	 * @return Valor y do tipo double convertido para tipo inteiro.
	 */
	public int getY() {
		return (int)this.y;
	}
	
	/**
	 * Retorna o valor de width da entidade
	 * @return int width
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Retorna o valor de heigth da entidade
	 * @return int heigth
	 */
	public int getHeight() {
		return this.height;
	}

	public void update() {
		
	}
	
	/**
	 * Renderiza o sprite na variavel g
	 * @param g BufferedImage a qual o sprite é renderizado.
	 */
	public void render(Graphics g) {
		g.drawImage(sprite, Camera.posX(this.getX()), Camera.posY(this.getY()), null);
	}
}
