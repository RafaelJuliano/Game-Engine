package com.navigames.main;

import com.navigames.entities.Entity;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Cria a janela do jogo e renderiza todos os sprites.
 * @author Rafael Juliano Ferreira
 */
public class GameScreen extends Canvas {
	public static JFrame frame;
	private BufferedImage image;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public final static int SCALE = 3;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Define as dimensões padrões da janela.
	 * Adiciona o obejto input como KeyLestener.
	 * Instancia a BufferedImage que receberá os sprites do mapa e entidades.
	 */
	public GameScreen() {		
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		addKeyListener(Game.input);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}

	/**
	    * Inicia a janela do jogo.
	    * As dimensões da janela são definidas no método construtor da classe;
	    * A sua posição padrão é no centro da tela;
	    * Seu tamanho pode ser alterado;
	    * É visivel desde sua execução;
	    * Ao ser fechada encerra o programa;
	    */
	public void initFrame() {
		frame = new JFrame("Jogo"); //
		frame.add(this);
		frame.setResizable(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		requestFocus();
	}
	
	/**
	*Renderiza as imagens utilizando BufferStrategy.
	* A tela é primeiro desenhada no objeto image que recebe as dimensões padrões de game.
	* Então a imagem é redenrizada responsivamente pelo objeto bs.
	*/
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		Game.world.render(g);
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			e.render(g);
		}
		Game.ui.render(g);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);

		bs.show();
	}
}
