package map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import config.GameConfig;
import entities.GameEntity;
import graphics.SpriteSheet;

public class TileMap extends GameEntity {

	private Random random = new Random();
	// map size in tiles
	private int mapWidth, mapHeight;
	private Image[][] tileMap;
	private int[][] intMap;
	private SpriteSheet sprites;

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
	      int rows = 32;
	      int columns = 18;
	      intMap = new int[columns][rows];
	      while(sc.hasNextLine()) {
	         for (int i=0; i<intMap.length; i++) {
	            String[] line = sc.nextLine().trim().split(" ");
	            for (int j=0; j<line.length; j++) {
	            	intMap[i][j] = Integer.parseInt(line[j]);
	            }
	         }
	      }
	      System.out.println(Arrays.deepToString(intMap));

	}

	void randomTile() {
		// TODO: load map from file
		mapWidth = GameConfig.SCREEN_WIDTH / GameConfig.TILE_SIZE ;
		mapHeight = GameConfig.SCREEN_HEIGHT / GameConfig.TILE_SIZE ;
		System.out.println(mapWidth);
		System.out.println(mapHeight);
		tileMap = new Image[mapWidth][mapHeight];
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
//				tileMap[x][y] = randomTile();
				int index = intMap[y][x];
				//counting from zero
				//[spalte][reihe]
				tileMap[x][y] = sprites.getTileSprites()[5][3];
			}
		}
		
		//		for (int x = 0; x < 32; x++) {
//			for (int y = 0; y < 18; y++) {
//				
//			}
//		}	
//		return sprites.getTileSprites()[5][3];
	}

	public void randomizeMap() {


	}

	public void readmap() {

	}

	@Override
	public void render(Graphics g) {
		g.setDrawMode(Graphics.MODE_NORMAL);

		sprites.getSpriteSheet().startUse();
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				tileMap[x][y].drawEmbedded(x * GameConfig.TILE_SIZE, y * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
			}
		}
		sprites.getSpriteSheet().endUse();
	}

	@Override
	public void tick(GameContainer gc) {

	}

}
