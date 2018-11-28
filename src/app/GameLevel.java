package app;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import behaviour.PlayerMovement;
import config.GameConfig;
import entities.Camera;
import entities.GameEntity;
import entities.Player;
import map.MapManager;

public class GameLevel extends GameEntity {

	private Player player;
	private MapManager mapManager;
	private Camera camera;

	public GameLevel() {
		player = new Player();
		camera = new Camera(player);
		try {
			mapManager = new MapManager();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void render(Graphics g) {
		camera.render(g);
		mapManager.render(g);
		player.render(g);
		
	}

	public void checkCollisions() {
		if (player.checkMapBounds()) {
			player.blockMovement();
		}

		for (int x = 0; x < mapManager.getWidth(); x++) {
			for (int y = 0; y < mapManager.getHeight(); y++) {
				if (mapManager.getCollideMatrix()[x][y] && new Rectangle(x * GameConfig.TILE_SIZE + 1,
						y * GameConfig.TILE_SIZE + 1, GameConfig.TILE_SIZE - 2, GameConfig.TILE_SIZE - 2)
								.intersects(player.getCollisionRect())) {
					player.blockMovement();
				}
			}
		}
	}

	@Override
	public void tick(GameContainer gc) {
		camera.tick(gc);
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
