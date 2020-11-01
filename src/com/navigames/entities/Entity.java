package com.navigames.entities;

/*=====================================================+
 |             IMPORT DE PACOTES JAVA					|
 +=====================================================*/
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.navigames.main.Game;

//=============	CRIAÇÃO DA CLASSE =======================//
public class Entity {
//=======================================================//


    /*===========================================================+
    |                           VARIÁVEIS                        |
    +===========================================================*/
   
    
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6*16, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(6*16, 16, 16, 16);
	public static BufferedImage ENEMY_EN =  Game.spritesheet.getSprite(2*16, 2*16, 16, 16);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	//==========================================================//
	
	/*==================================================+
    |	  			MÉTODO CONSTRUTOR				    |
    +==================================================*/
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	//==========================================================//

	
	/*==================================================+
    |	  			MÉTODOS DE BUSCA DE VARIÁVEIS	    |
    +==================================================*/
    public void setX(int newX){
        this.x = newX;
    }
    
      public void setY(int newY){
        this.y = newY;
    }
    
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	//==========================================================//
	
	/*==================================================+
    |	  			EXECUÇÃO DE LÓGICA POR FRAME	    |
    +==================================================*/
	public void tick() {
		
	}
	//==========================================================//
	
	/*==========================================================+
    |	RENDERIZAÇÃO DE GRAFICOS APÓS EXECUÇÃO DE LÓGICA		|
    +==========================================================*/
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
    //==========================================================//
}
