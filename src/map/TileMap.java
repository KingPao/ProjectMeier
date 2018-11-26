package map;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import config.GameConfig;
import entities.GameEntity;
import graphics.SpriteSheet;

public class TileMap extends GameEntity {

	private Tile[][] tileMap;
	private Point[][] coordinateMap;
	private SpriteSheet sprites;
	private int columnsMap;
	private int rowsMap;
	private int columnsMatrix;
	private int rowsMatrix;
	private boolean[][] collideMatrix;

	public TileMap(SpriteSheet sprites) {
		this.sprites = sprites;

		try {
			getCollidableTextures();
			readMapFromFile();
			generateMap();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getCollidableTextures() throws FileNotFoundException {
		Scanner sc = new Scanner(new BufferedReader(new FileReader(GameConfig.FILE_DEMOMAP_COLLIDABLE)));
		String[] matrixSize = sc.nextLine().replace(";", "").split("\\|");

		columnsMatrix = Integer.parseInt(matrixSize[0]);
		rowsMatrix = Integer.parseInt(matrixSize[1]);

		collideMatrix = new boolean[columnsMatrix][rowsMatrix];

		for (int y = 0; y < rowsMatrix; y++) {
			String line = sc.nextLine();
			String[] textures = line.split(";");

			for (int x = 0; x < columnsMatrix; x++) {
				if (textures[x].equals("1")) {
					collideMatrix[x][y] = true;
				}

			}
		}
	}

	public void readMapFromFile() throws IOException {
		Scanner sc = new Scanner(new BufferedReader(new FileReader(GameConfig.FILE_DEMOMAP)));
		String[] mapsSize = sc.nextLine().replace(";", "").split("\\|");// Delete Excel Generated Delimitors in first
																		// line

		columnsMap = Integer.parseInt(mapsSize[0]);
		rowsMap = Integer.parseInt(mapsSize[1]);

		coordinateMap = new Point[columnsMap][rowsMap];

		for (int y = 0; y < rowsMap; y++) {
			String line = sc.nextLine();
			String sprites[] = line.split(";");

			for (int x = 0; x < columnsMap; x++) {
				int spriteX = Integer.parseInt(sprites[x].split("\\|")[0]);
				int spriteY = Integer.parseInt(sprites[x].split("\\|")[1]);
				coordinateMap[x][y] = new Point(spriteX, spriteY);
			}
		}

	}

	void generateMap() {
		tileMap = new Tile[columnsMap][rowsMap];

		for (int y = 0; y < rowsMap; y++) {
			for (int x = 0; x < columnsMap; x++) {
				int spriteX = coordinateMap[x][y].x;
				int spriteY = coordinateMap[x][y].y;
				tileMap[x][y] = new Tile(sprites.getTileTextures()[spriteX][spriteY].getTileTexture(), x, y);
				if (collideMatrix[spriteX][spriteY]) {
					tileMap[x][y].setCollidable(true);
				}

			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setDrawMode(Graphics.MODE_NORMAL);
		sprites.getSpriteSheet().startUse();

		for (int x = 0; x < GameConfig.SCREEN_WIDTH / GameConfig.TILE_SIZE; x++) {
			for (int y = 0; y < GameConfig.SCREEN_HEIGHT / GameConfig.TILE_SIZE; y++) {
				tileMap[x][y].getTileTexture().drawEmbedded(x * GameConfig.TILE_SIZE, y * GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
			}
		}
		sprites.getSpriteSheet().endUse();
	}

	@Override
	public void tick(GameContainer gc) {

	}

	public Tile[][] getTileMap() {
		return tileMap;
	}

	public void setTileMap(Tile[][] tileMap) {
		this.tileMap = tileMap;
	}

	public SpriteSheet getSprites() {
		return sprites;
	}

	public void setSprites(SpriteSheet sprites) {
		this.sprites = sprites;
	}

}
