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

	public MapManager() throws SlickException {
		tiledMap = new TiledMap("/res/demomap.tmx", "/res");

		generateMap();
	}

	void generateMap() {
		int width = tiledMap.getWidth();
		int height = tiledMap.getHeight();
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
	public void render() {
		tiledMap.render(0, 0);
		for (int x = 0; x < 32; x++) {
			for (int y = 0; y < 18; y++) {
				if(GameConfig.DEBUG_MODE)
					new Graphics().drawRect(x * 32 +1, y * 32 +1, 30, 30);
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

}
