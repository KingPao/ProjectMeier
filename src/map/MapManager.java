package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import config.GameConfig;
import entities.GameEntity;
import tiled.core.Map;
import tiled.io.TMXMapReader;;

public class MapManager extends GameEntity {

	private boolean[][] collideMatrix;
	private TiledMap tiledMap;
	private int width, height;
	Map map;
	TMXMapReader mapReader;

	public MapManager() throws SlickException {
		tiledMap = new TiledMap("/res/unbenannt.tmx", "/res");

		generateMap();
	}

	void generateMap() {
		width = tiledMap.getWidth();
		height = tiledMap.getHeight();
		collideMatrix = new boolean[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				if (tiledMap.getTileProperty(tiledMap.getTileId(x, y, 3), "col", "x").equals("1")) {
					collideMatrix[x][y] = true;
				}
			}
		}
	}

	public void render(Graphics g) {

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (GameConfig.DEBUG_MODE) {
					if (collideMatrix[x][y]) {
						g.drawRect(x * GameConfig.TILE_SIZE, y * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
								GameConfig.TILE_SIZE);
					}
				}
			}
		}
	}

	@Override
	public void tick(GameContainer gc) {

	}

	public boolean[][] getCollideMatrix() {
		return collideMatrix;
	}

	public void setCollideMatrix(boolean[][] collideMatrix) {
		this.collideMatrix = collideMatrix;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public TiledMap getTiledMap() {
		return tiledMap;
	}

}
