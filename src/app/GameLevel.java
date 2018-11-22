package app;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import config.GameConfig;
import entities.GameEntity;
import entities.Player;
import graphics.SpriteSheet;
import map.Tile;
import map.TileMap;

public class GameLevel extends GameEntity {

	private Player player;
	private TileMap map;

	public GameLevel() {
		map = new TileMap(new SpriteSheet(GameConfig.SPRITESHEET_MAP));
		player = new Player();
	}

	@Override
	public void render(Graphics g) {
		map.render(g);
		player.render(g);

	}

	public void checkCollisions() {
		if (player.checkMapBounds()) {
			player.blockMovement();
		}

		for (Tile[] tiles : map.getTileMap()) {
			for (Tile tile : tiles) {
				if (tile.isCollidable() && player.collidesWith(tile.getCollisionRect())) {
					player.blockMovement();
				}
			}
		}

	}

	@Override
	public void tick(GameContainer gc) {
		player.handleInput(gc);
		checkCollisions();

	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public TileMap getMap() {
		return map;
	}

	public void setMap(TileMap map) {
		this.map = map;
	}

}
