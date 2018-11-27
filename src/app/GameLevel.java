package app;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import behaviour.PlayerMovement;
import config.GameConfig;
import entities.GameEntity;
import entities.Player;
import graphics.MeierSpriteSheet;
import map.Tile;
import map.TileMap;

public class GameLevel extends GameEntity {

	private Player player;
	private TileMap map;

	public GameLevel() {
		map = new TileMap(new MeierSpriteSheet(GameConfig.SPRITESHEET_MAP));
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
			player.setBlocked(false);
		}

		for (Tile[] tiles : map.getTileMap()) {
			for (Tile tile : tiles) {
				if (tile.isCollidable() && tile.getCollisionRect().intersects(player.getCollisionRect())) {
					player.blockMovement();
					player.setBlocked(false);
				}
			}
		}
	}

	@Override
	public void tick(GameContainer gc) {
		handleInput(gc);
		checkCollisions();
	}

	public void handleInput(GameContainer gc) {

		if(gc.getInput().isKeyDown(Input.KEY_LCONTROL)) {
			player.setSprinting(true);
		}else {
			player.setSprinting(false);
		}
		
		if (!player.isMoving()) {
			if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
				player.walkTowardsTile(PlayerMovement.RIGHT);
			} else if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
				player.walkTowardsTile(PlayerMovement.DOWN);
			} else if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
				player.walkTowardsTile(PlayerMovement.LEFT);
			} else if (gc.getInput().isKeyDown(Input.KEY_UP)) {
				player.walkTowardsTile(PlayerMovement.UP);
			}
		} else {
			player.walkTowardsTile(player.getLastmoved());
		}


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
