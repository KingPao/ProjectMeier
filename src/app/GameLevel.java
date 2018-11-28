package app;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import behaviour.PlayerMovement;
import entities.GameEntity;
import entities.Player;
import map.MapManager;

public class GameLevel extends GameEntity {

	private Player player;
	private MapManager mapManager;

	public GameLevel() {
		player = new Player();
		try {
			mapManager = new MapManager();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		mapManager.render();
		player.render();

	}

	public void checkCollisions() {
		if (player.checkMapBounds()) {
			player.blockMovement();
		}

		for (int x = 0; x < 32; x++) {
			for (int y = 0; y < 18; y++) {
				if (mapManager.getCollideMatrix()[x][y]
						&& new Rectangle(x * 32 + 1, y * 32 + 1, 30, 30).intersects(player.getCollisionRect())) {
					player.blockMovement();
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

		if (gc.getInput().isKeyDown(Input.KEY_LCONTROL)) {
			player.setSprinting(true);
		} else {
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

}
