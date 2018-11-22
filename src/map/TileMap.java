package map;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

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
			randomTile();


		} catch (IOException e) {
			// TODO Auto-generated catch block
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
//TODO:WTF
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


//	public void readMapFromFile() throws IOException {
//		@SuppressWarnings("resource")
//		Scanner sc = new Scanner(new BufferedReader(new FileReader(GameConfig.FILE_DEMOMAP)));
//		int rows = 32;
//		int columns = 18;
//		intMap = new int[columns][rows];
//		while (sc.hasNextLine()) {
//			for (int i = 0; i < intMap.length; i++) {
//				String[] line = sc.nextLine().trim().split(" ");
//				for (int j = 0; j < line.length; j++) {
//					intMap[i][j] = Integer.parseInt(line[j]);
//				}
//			}
//		}
//	}

	void randomTile() {


		tileMap = new Tile[columns][rows];

		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				int spriteX = coordinateMap[x][y].x;
				int spriteY = coordinateMap[x][y].y;
				tileMap[x][y] = new Tile(sprites.getTileTextures()[spriteX][spriteY].getTileTexture(), x, y);
				//System.out.println(spriteX + "  " + spriteY);
				//tileMap[x][y] = new Tile(sprites.getTileTextures()[0][0].getTileTexture(), x, y);
				

			}
		}

	}

	public void randomizeMap() {

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
