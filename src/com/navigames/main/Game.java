package com.navigames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import com.navigames.entities.Entity;
import com.navigames.entities.Player;
import com.navigames.graficos.Spritesheet;
import com.navigames.world.World;

public class Game extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
    private Thread thread;
    private boolean isRunning = true;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    private final int SCALE = 3;    
    private BufferedImage image;
    public static List<Entity> entities;
    public static Spritesheet spritesheet;
    public static World world;
    public static Player player;
    public static boolean left, right, up, down;
        

    public static void main(String[] args) {
        Game main = new Game();
        main.start();
	}

    public Game(){
    	addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE)); //CRIA DIMENÇÕES DA JANELA
        initFrame();// #MÉTODO DE CRIAÇÃO DE JANELA
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        entities = new ArrayList<Entity>();         
        spritesheet = new Spritesheet("/spritesheet.png");        
        player = new Player(0, 0, 16, 16,spritesheet.getSprite(32, 0, 16, 16));
        world = new World("/map.png");        
        entities.add(player);    
    }

    public void initFrame(){
        frame = new JFrame("Jogo"); //
        frame.add(this);
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public void tick(){    	
        for(int i = 0; i < entities.size(); i++) {
        	Entity e = entities.get(i);
        	e.tick();        	
        }
    }

	public void render(){
    	BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		world.render(g);
		for(int i = 0; i < entities.size(); i++) {
        	Entity e = entities.get(i);          	
        	e.render(g);
		}
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);	
			
		bs.show();		
    }

    public void run(){
		long lastTime = System.nanoTime();
		double frameRate = 60.0;
		double nanosPerFrame = 1000000000 / frameRate;
		while(isRunning) {
			long now = System.nanoTime();
			if ((now - lastTime)/nanosPerFrame>=1) {
				lastTime = now;
				tick();
				render();
			}

		}
        stop();
    }
    public synchronized void stop(){
    	isRunning = false;
        try {
			thread.join();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
    }

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();		
		player.right |= code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D;
		player.left |= code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A;
		player.up |= code == KeyEvent.VK_UP || code == KeyEvent.VK_W;
		player.down |= code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S;
	}


	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();		
		player.right = code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D? false:player.right;
		player.left = code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A? false:player.left;
		player.up = code == KeyEvent.VK_UP || code == KeyEvent.VK_W? false:player.up;
		player.down = code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S? false:player.down;
	}
	
	public void keyTyped(KeyEvent e) {}
	
	
}
