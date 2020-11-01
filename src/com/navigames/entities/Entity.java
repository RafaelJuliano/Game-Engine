package com.navigames.entities;

/*=====================================================+
 |             IMPORT DE PACOTES JAVA					|
 +=====================================================*/
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.navigames.main.Game;

//=============	CRIA��O DA CLASSE =======================//
public class Entity {
//=======================================================//


    /*===========================================================+
    |                           VARI�VEIS                        |
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
    |	  			M�TODO CONSTRUTOR				    |
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
    |	  			M�TODOS DE BUSCA DE VARI�VEIS	    |
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
    |	  			EXECU��O DE L�GICA POR FRAME	    |
    +==================================================*/
	public void tick() {
		
	}
	//==========================================================//
	
	/*==========================================================+
    |	RENDERIZA��O DE GRAFICOS AP�S EXECU��O DE L�GICA		|
    +==========================================================*/
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
    //==========================================================//
}
