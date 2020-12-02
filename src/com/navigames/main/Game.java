package com.navigames.main;

import java.util.ArrayList;
import java.util.List;

import com.navigames.entities.Enemy;
import com.navigames.entities.Entity;
import com.navigames.entities.Player;
import com.navigames.graficos.Spritesheet;
import com.navigames.graficos.UI;
import com.navigames.world.World;

/**
 * A classe Game é responsável por instanciar os principais objetos do jogo, executar o loop e chamar os métodos
 * lógicos de cada objeto.
 * @author Rafael
 */
public class Game implements Runnable {

	private Thread thread;
	private boolean isRunning = true;
	public static List<Enemy> enemies;
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;
	public GameScreen screen;
	public static GameInput input;
	public static UI ui;

	public static void main(String[] args) {
		Game main = new Game();
		main.start();
	}

	public Game() {
		input = new GameInput();
		screen = new GameScreen();
		screen.initFrame();
		enemies = new ArrayList<Enemy>();
		entities = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, World.D_SIZE, World.D_SIZE));
		world = new World("/map3.png");
		ui = new UI();
		entities.add(player);
	}

	/**
	 * Inicializa a thread. A thread recebe o objeto game; isRunning recebe o valor
	 * true para validar a condição da execução do loop no método run(); A
	 * tread é iniciada;
	 */
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	/**
	 * Encerra execução da thread.
	 * isRunning recebe o valor false para impedir a execução do loop no método run();
	 */
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Responsavel por chamar os ,métodos de atualização de cada entidade.
	 */
	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update();
		}
	}

	/**
	 * Executa o loop responsável por chamar os métodos de atuzalização e
	 * rederização. A quantidade de frames por segundo é definida em frameRate.
	 * Cada frame tem um quantidade x de nanosegundos definida na variável
	 * nanosPerFrame. A cada frame, os métodos tick() e render() são chamados.
	 */
	public void run() {
		long lastTime = System.nanoTime();
		double frameRate = 60.0;
		double nanosPerFrame = 1000000000 / frameRate;
		while (isRunning) {
			long now = System.nanoTime();
			if ((now - lastTime) / nanosPerFrame >= 1) {
				lastTime = now;
				update();
				screen.render();
			}
		}
		stop();
	}

}
