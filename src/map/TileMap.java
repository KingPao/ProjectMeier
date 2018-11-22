package map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import config.GameConfig;
import entities.GameEntity;
import graphics.SpriteSheet;

public class TileMap extends GameEntity {

	// map size in tiles
	private int mapWidth = 32;
	private int mapHeight = 18;
	private Tile[][] tileMap;
	private int[][] intMap;
	private SpriteSheet sprites;

	public TileMap(SpriteSheet sprites) {
		this.sprites = sprites;

		try {
			loadMap();
			randomTile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadMap() throws IOException {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new BufferedReader(new FileReader(GameConfig.FILE_DEMOMAP)));
		int rows = 32;
		int columns = 18;
		intMap = new int[columns][rows];
		while (sc.hasNextLine()) {
			for (int i = 0; i < intMap.length; i++) {
				String[] line = sc.nextLine().trim().split(" ");
				for (int j = 0; j < line.length; j++) {
					intMap[i][j] = Integer.parseInt(line[j]);
				}
			}
		}
	}

	void randomTile() {
		// TODO: load map from file
		mapWidth = GameConfig.SCREEN_WIDTH / GameConfig.TILE_SIZE;
		mapHeight = GameConfig.SCREEN_HEIGHT / GameConfig.TILE_SIZE;
		tileMap = new Tile[mapWidth][mapHeight];
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				tileMap[x][y] = new Tile(sprites.getTileSprites()[5][3], x, y);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setDrawMode(Graphics.MODE_NORMAL);
		sprites.getSpriteSheet().startUse();
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				tileMap[x][y].getTileTexture().drawEmbedded(x * GameConfig.TILE_SIZE, y * GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
			}
		}
		sprites.getSpriteSheet().endUse();
	}

	@Override
	public void tick(GameContainer gc) {

	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	public Tile[][] getTileMap() {
		return tileMap;
	}

	public void setTileMap(Tile[][] tileMap) {
		this.tileMap = tileMap;
	}

	public int[][] getIntMap() {
		return intMap;
	}

	public void setIntMap(int[][] intMap) {
		this.intMap = intMap;
	}

	public SpriteSheet getSprites() {
		return sprites;
	}

	public void setSprites(SpriteSheet sprites) {
		this.sprites = sprites;
	}

}
