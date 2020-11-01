package com.navigames.entities;

/*=====================================================+
|             IMPORT DE PACOTES JAVA				   |
+=====================================================*/
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.navigames.main.Game;
import com.navigames.world.World;

//=============	CRIA��O DA CLASSE =======================//
public class Player extends Entity {
//=======================================================// 

 /*==================================================+
 |				   		VARI�VEIS 	         		 |
 +==================================================*/
	public boolean right, up, left, down;
	public double speed = 0.9;
	private boolean dir = true;
	private boolean moved = false;	
	private int frames = 0, maxFrames = 12, index = 0, maxIndex =3;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	//====================================================//
	
 /*==================================================+
 |	  			M�TODO CONSTRUTOR				     |
 +==================================================*/
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
	}
 //=============================================================//
	
	
	/*======================================================+
	|		MOVIMENTA��O E ANIMA��O DO PLAYER				|
	+======================================================*/
	public void tick() {
		moved = false;
		if(right) {
			moved = true;
			dir = true;
			x+=speed;
			
			
		}else if(left) {
			moved = true;
			dir = false;
			x-=speed;
		}
		if(up) {
			moved = true;
			y-=speed;
		}else if(down) {
			moved = true;
			y+=speed;
		}
		//L�GICA DE ANIMA��O
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	//===================================================================//
	
	
	/*==========================================================+
    |	RENDERIZA��O DE GRAFICOS AP�S EXECU��O DE L�GICA		|
    +==========================================================*/
	public void render(Graphics g) {
		if(dir) {
			g.drawImage(rightPlayer[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		}else if(dir == false) {
			g.drawImage(leftPlayer[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		}
	}
	//======================================================================//



		
	



}
