package com.navigames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.navigames.entities.*;
import com.navigames.main.Game;
import com.navigames.main.GameScreen;

/**
 * Utiliza uma imagem para definir automaticamente as posições de cada tile e
 * entidade do jogo. Também utiliza posições da câmera para otimizar a
 * renderização do mapa.
 * 
 * @author Rafael Juliano Ferreira
 *
 */
public class World {

	private static Tile[] tiles;
	private int[] pixels;
	public static int D_SIZE = 16;
	public static int WIDTH, HEIGHT;
	private final int FLOOR = 0xFF000000;
	private final int WALL = 0xFFFFFFFF;
	private final int PLAYER = 0xFF00E1FF;
	private final int ENEMY = 0xFFFF0000;
	private final int WEAPON = 0xFFFF6A00;
	private final int LIFE_PACK = 0xFFFFD800;
	private final int BULLET = 0xFF808080;
	private int index;

	private BufferedImage map;

	/**
	 * Faz a leitura de uma imagem e verifica a cor RGB de cada pixel armazenando em
	 * um vetor int com os respectivos valores em hexadecial.
	 * 
	 * @param path Recebe o caminho da imagem.
	 */
	public World(String path) {
		try {
			map = ImageIO.read(getClass().getResource(path));

		} catch (IOException e) {
			e.printStackTrace();
		}

		WIDTH = map.getWidth();
		HEIGHT = map.getHeight();
		pixels = new int[WIDTH * HEIGHT];
		tiles = new Tile[WIDTH * HEIGHT];
		map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
		drawMap();
	}

	/**
	 * Utiliza um aninhamento de for para converter o index de pixels[] em posições
	 * X e Y. Faz a verificação do código hexadecimal e define a posição e tipo do
	 * objeto no mapa. Após este processo, cada pixel do mapa de referencia, se
	 * torna uma imagem de 16 pixels, sendo necessário prestar atenção a esta
	 * conversão em outros métodos. Por padrão todo, todo tile é "chão" no inicio da
	 * verificação.
	 */
	private void drawMap() {
		for (int yy = 0; yy < HEIGHT; yy++) {
			for (int xx = 0; xx < WIDTH; xx++) {

				index = index(xx, yy);
				int posX = xx * D_SIZE;
				int posY = yy * D_SIZE;

				int pixelAtual = pixels[index];
				tiles[index] = new FloorTile(posX, posY, Tile.TILE_FLOOR);

				switch (pixelAtual) {
				case FLOOR:
					tiles[index] = new FloorTile(posX, posY, Tile.TILE_FLOOR);
					break;
				case WALL:
					tiles[index] = new WallTile(posX, posY, Tile.TILE_WALL);
					break;
				case PLAYER:
					Game.player.setX(posX);
					Game.player.setY(posY);
					break;
				case ENEMY:
					Game.entities.add(new Enemy(posX, posY, D_SIZE, D_SIZE, Entity.ENEMY_EN));
					break;
				case WEAPON:
					Game.entities.add(new Wepon(posX, posY, D_SIZE, D_SIZE, Entity.WEAPON_EN));
					break;
				case LIFE_PACK:
					Game.entities.add(new LifePack(posX, posY, D_SIZE, D_SIZE, Entity.LIFEPACK_EN));
					break;
				case BULLET:
					Game.entities.add(new Bullet(posX, posY, D_SIZE, D_SIZE, Entity.BULLET_EN));
					break;
				default:
					tiles[index] = new FloorTile(posX, posY, Tile.TILE_FLOOR);
					break;
				}
			}
		}
	}

	/**
	 * Transforma posições dos tiles em um indice de pixels;
	 * 
	 * @param x Posição x do mapa, no formato de 1 pixel.
	 * @param y Posição y do mapa, no formato de 1 pixel.
	 * @return Indice correspondente no vetor pixels.
	 */
	private int index(int x, int y) {
		return x + (y * WIDTH);
	}

	/**
	 * Converte o valor final das posições ao formato de 1 pixel e transforma em um
	 * indice do vetor pixels.
	 * 
	 * @param x Posição x do mapa, no formato final.
	 * @param y Posição y do mapa, no formato final.
	 * @return Indice correspondente no vetor pixels.
	 */
	private static int indexS(int x, int y) {
		return (x / D_SIZE) + ((y / D_SIZE) * WIDTH);
	}

	/**
	 * Detecta se a posição corresponde a um tile wall.
	 * 
	 * @param x Posição x do mapa, no formato final.
	 * @param y Posição y do mapa, no formato final.
	 * @param p Diminui as laterais do retangulo para ajuste fino da colisão.
	 * @return Valor booleano.
	 */
	public static boolean isWall(int x, int y, int p) {
		int index1 = indexS(x + p, y);
		int index2 = indexS(x + D_SIZE - p, y);
		int index3 = indexS(x + p, y + D_SIZE);
		int index4 = indexS(x + D_SIZE - p, y + D_SIZE);
		if (tiles[index1] instanceof WallTile || tiles[index2] instanceof WallTile || tiles[index3] instanceof WallTile
				|| tiles[index4] instanceof WallTile) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica quais tiles estão visiveis no canvas e as renderiza.
	 */
	public void render(Graphics g) {
		int xstart = Camera.getX() / D_SIZE;
		int ystart = Camera.getY() / D_SIZE;
		int xfinal = xstart + GameScreen.WIDTH / D_SIZE;
		int yfinal = ystart + GameScreen.HEIGHT / D_SIZE;
		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				index = index(xx, yy);
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[index];
				tile.render(g);
			}
		}
	}
}
