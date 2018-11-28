package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import config.GameConfig;
import entities.GameEntity;

public class MapManager extends GameEntity {

	private boolean[][] collideMatrix;
	private TiledMap tiledMap;
	private int width, height;

	public MapManager() throws SlickException {
		tiledMap = new TiledMap("/res/demomap.tmx", "/res");

		generateMap();
	}

	void generateMap() {
		width = tiledMap.getWidth();
		height = tiledMap.getHeight();
		collideMatrix = new boolean[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiledMap.getTileProperty(tiledMap.getTileId(x, y, 0), "col", "x").equals("1")) {
					collideMatrix[x][y] = true;
				}
			}
		}

	}

	@Override
	public void render(Graphics g) {
		tiledMap.render(0, 0);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (GameConfig.DEBUG_MODE)
					g.drawRect(x * GameConfig.TILE_SIZE + 1, y * GameConfig.TILE_SIZE + 1, GameConfig.TILE_SIZE - 2,
							GameConfig.TILE_SIZE - 2);
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
