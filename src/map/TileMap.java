package map;

import java.awt.Point;
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

	private Tile[][] tileMap;
	private Point[][] coordinateMap;
	private SpriteSheet sprites;
	private int columns;
	private int rows;

	public TileMap(SpriteSheet sprites) {
		this.sprites = sprites;


		try {
			readMapFromFile();
			generateMap();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void readMapFromFile() throws IOException {
		Scanner sc = new Scanner(new BufferedReader(new FileReader(GameConfig.FILE_DEMOMAP)));
		String[] mapsSize = sc.nextLine().replace(";", "").split("\\|");// Delete Excel Generated Delimitors in first
																		// line

		columns = Integer.parseInt(mapsSize[0]);
		rows = Integer.parseInt(mapsSize[1]);


		coordinateMap = new Point[columns][rows];

		for (int y = 0; y < rows; y++) {
			String line = sc.nextLine();
			String sprites[] = line.split(";");

			for (int x = 0; x < columns; x++) {
				int spriteX = Integer.parseInt(sprites[x].split("\\|")[0]);
				int spriteY = Integer.parseInt(sprites[x].split("\\|")[1]);
				coordinateMap[x][y] = new Point(spriteX, spriteY);

			}
		}
		
		
	}

	void generateMap() {
		tileMap = new Tile[columns][rows];

		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				int spriteX = coordinateMap[x][y].x;
				int spriteY = coordinateMap[x][y].y;
				tileMap[x][y] = new Tile(sprites.getTileTextures()[spriteX][spriteY].getTileTexture(), x, y);
				if(spriteX == 7 && spriteY == 3) {
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
				tileMap[x][y].getTileTexture().drawEmbedded(x * GameConfig.TILE_SIZE, y * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
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
