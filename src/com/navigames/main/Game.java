package com.navigames.main;

 /*=====================================================+
 |             IMPORT DE PACOTES JAVA					|
 +=====================================================*/
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

//=============	CRIAÇÃO DA CLASSE =======================//
public class Game extends Canvas implements Runnable, KeyListener{
//=======================================================//  
	
  /*==================================================+
  |				   		VARIÁVEIS 	         		  |
  +==================================================*/
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
        
    /*==================================================+
    |===================================================|
    |				MÉTODO PRINCIPAL					|
    |===================================================|
    +==================================================*/
    public static void main(String[] args) {
        Game main = new Game();
        main.start();
	}
    //==================================================//
    //==================================================//
    
    
    /*==================================================+
    |	  			MÉTODO CONSTRUTOR				    |
    +==================================================*/
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
    //==================================================//
    
    
    
    /*====================================================+
    |			MÉTODO CRIAÇÃO DE JANELA				  |
    +====================================================*/
    public void initFrame(){
        frame = new JFrame("Jogo"); //
        frame.add(this); // puxa propriedades canvas
        frame.setResizable(false); // bloqueia reajuste de janela
        frame.pack(); //calcula dimensoes
        frame.setLocationRelativeTo(null); //janela no centro
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // para método quando fecha janela
        frame.setVisible(true); // deixa visivel quando inicia
    }
    //=====================================================//
    
    
    
    /*======================================================+
    |			MÉTODO DE INICIALIZAÇÃO DE THREADS			|
    +======================================================*/
    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }
    //=====================================================//
    
       
    
    
    /*=========================================================+
    |		MÉTODO DE EXECUÇÃO DE LÓGICA DE CLASSES			   |
    +=========================================================*/
    public void tick(){    	
        for(int i = 0; i < entities.size(); i++) {
        	Entity e = entities.get(i);
        	e.tick();
        	
        	
        }
    }
    //=====================================================//
    
    
    
    /*==========================================================+
    |	RENDERIZAÇÃO DE GRAFICOS APÓS EXECUÇÃO DE LÓGICA		|
    +==========================================================*/
	public void render(){
    	BufferStrategy bs = this.getBufferStrategy(); //metodo renderiza graficos
		if(bs == null) { // se buffer não existe 
			this.createBufferStrategy(3); //crie um buffer = 3
			return;
		}
		Graphics g = image.getGraphics();
		//FUNDO PRETO
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Graphics2D g2 = (Graphics2D) g;
		
		//RENDERIZA ENTITIES
		world.render(g);
		for(int i = 0; i < entities.size(); i++) {
        	Entity e = entities.get(i);          	
        	e.render(g);
		}
		
		
		//LIMPA A TELA
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);		
		bs.show();
		
		
		
    }
    //=====================================================//
	
	
	
    /*======================================================+
    |				MÉTODO DE LOOPING 						|
    +======================================================*/
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while(isRunning){
            long now = System.nanoTime();
            delta+= (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }
            
            if(System.currentTimeMillis() - timer >= 1000){
               System.out.println("FPS: "+ frames);
                frames = 0;
                timer+= 1000;
            }
        }
        stop();
    }
    //=====================================================//    
    
    
    
    /*=======================================================+
    |					MÉTODO DE PARADA                     |
    +=======================================================*/
    //Força parada de threads caso o saia do Loop		
    public synchronized void stop(){
    	isRunning = false;
        try {
			thread.join();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
    }
    //=====================================================//


	/*=====================================================+
	|	MÉTODOS DE LEITURA DE EVENTOS DO TECLADO		   |
	+=====================================================*/
    
    //QUANDO TECLA ESTÁ PRESSIONADA=====================================//
	public void keyPressed(KeyEvent e) {
		
		//MOVIMENTO EIXO X-------------------------------------------------//
		if(e.getKeyCode()== KeyEvent.VK_RIGHT || e.getKeyCode()== KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode()	== KeyEvent.VK_LEFT || e.getKeyCode()== KeyEvent.VK_A) {
			player.left = true;
		}
		//-------------------------------------------------------------------//
		
		//MOVIMENTO EIXO Y---------------------------------------------------//
		if(e.getKeyCode()	== KeyEvent.VK_UP || e.getKeyCode()== KeyEvent.VK_W) {
			player.up = true;
		}else if(e.getKeyCode()	== KeyEvent.VK_DOWN || e.getKeyCode()== KeyEvent.VK_S) {
			player.down = true;
		}
		//-------------------------------------------------------------------//
	}

	//QUANDO SOLTA A TECLA===============================================//
	public void keyReleased(KeyEvent e) {
		
		//MOVIMENTO EIXO X-------------------------------------------------//
		if(e.getKeyCode()== KeyEvent.VK_RIGHT || e.getKeyCode()== KeyEvent.VK_D) {
			player.right = false;
		}else if(e.getKeyCode()	== KeyEvent.VK_LEFT || e.getKeyCode()== KeyEvent.VK_A) {
			player.left = false;
		}
		//-------------------------------------------------------------------//
		
		//MOVIMENTO EIXO Y---------------------------------------------------//
		if(e.getKeyCode()	== KeyEvent.VK_UP || e.getKeyCode()== KeyEvent.VK_W) {
			player.up = false;
		}else if(e.getKeyCode()	== KeyEvent.VK_DOWN || e.getKeyCode()== KeyEvent.VK_S) {
			player.down = false;
		}
		//-------------------------------------------------------------------//
	}

	
	public void keyTyped(KeyEvent e) {
		
		
	}
    //=====================================================//
}


