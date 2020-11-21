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
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        initFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        entities = new ArrayList<Entity>();         
        spritesheet = new Spritesheet("/spritesheet.png");        
        player = new Player(0, 0, 16, 16,spritesheet.getSprite(32, 0, 16, 16));
        world = new World("/map.png");        
        entities.add(player);    
    }
    /**
    * Inicia a janela do jogo.
    * As dimensões da janela são definidas no método construtor da classe;
    * A sua posição padrão é no centro da tela;
    * Seu tamanho pode ser alterado;
    * é visivel desde sua execução;
    * Ao ser fechada encerra o programa;
    */
    public void initFrame(){
        frame = new JFrame("Jogo"); //
        frame.add(this);
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    /**
    * Inicializa a thread.
    * A thread recebe o objeto game;
    * isRunning	recebe o valor true para validar a condição da execução do loop no método run();
    * A tread é iniciada;
    */
    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }
    /**
    * Encerra execução da thread
    * isRunning recebe o valor false para impedir a execução do loop no método run();
    */
    public synchronized void stop(){
	isRunning = false;
	try {
		thread.join();
	} catch (InterruptedException e) {			
		e.printStackTrace();
	}
    }
	
    /**
    * Responsavel por chamar os métodos de atualização de cada entidade. 
    */
    public void tick(){    	
        for(int i = 0; i < entities.size(); i++) {
        	Entity e = entities.get(i);
        	e.tick();        	
        }
    }
	/**
	/ Renderiza as imagens utilizando BufferStrategy.
	* A tela é primeiro desenhada no objeto image que recebe as dimensões padrões de game.
	* Então a imagem é redenrizada responsivamente pelo objeto bs.
	*/
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
    /**
    * Executa o loop responsável por chamar os métodos de atuzalização e rederização.
    * A quantidade de frames por segundo é definida em frameRate.
    * Cada frame tem um quantidade x de nanosegundos definida na variável nanosPerFrame.
    * A cada frame, os métodos tick() e render() são chamados.
    */
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

	/**
	* Identifica quando uma tecla é pressionada e torna verdadeira a respectiva condição apra execução da lógica na classe Player.
	* Quando a tecla RIGHT ou D é pressioanda, a codição right recebe true.
	* Quando a tecla LEFT ou A é pressioanda, a codição left recebe true.
	* Quando a tecla UP ou W é pressioanda, a codição up recebe true.
	* Quando a tecla DOWN ou S é pressioanda, a codição down recebe true.	
	*/
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();		
		player.right |= code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D;
		player.left |= code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A;
		player.up |= code == KeyEvent.VK_UP || code == KeyEvent.VK_W;
		player.down |= code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S;
	}

	/**
	* Identifica quando uma tecla deixa de ser pressionada e informa a classe Player qual lógica deve interrompida.
	* Quando a tecla RIGHT ou D deixa de ser pressionada, a codição right recebe false.
	* Quando a tecla LEFT ou A deixa de ser pressionada, a codição left recebe false.
	* Quando a tecla UP ou W deixa de ser pressionada, a codição up recebe false.
	* Quando a tecla DOWN ou S deixa de ser pressionada, a codição down recebe false.	
	*/
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();		
		player.right = code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D? false:player.right;
		player.left = code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A? false:player.left;
		player.up = code == KeyEvent.VK_UP || code == KeyEvent.VK_W? false:player.up;
		player.down = code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S? false:player.down;
	}
	
	public void keyTyped(KeyEvent e) {}	
	
}
