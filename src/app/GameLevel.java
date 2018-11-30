package app;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;

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
	private TextField text;

	public GameLevel() {
		player = new Player();
		camera = new Camera(player);
		try {
			mapManager = new MapManager();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	public void renderDebugInfo() {



	}

	@Override
	public void render(Graphics g) {
		camera.render(g);
		mapManager.getTiledMap().render(0, 0, 0);
		mapManager.getTiledMap().render(0, 0, 1);
		player.render(g);
		mapManager.getTiledMap().render(0, 0, 2);
		mapManager.render(g);


	}

	public void checkCollisions() {
		player.checkMapBounds();

		for (int x = 0; x < mapManager.getWidth(); x++) {
			for (int y = 0; y < mapManager.getHeight(); y++) {
				if (mapManager.getCollideMatrix()[x][y] && new Rectangle(x * GameConfig.TILE_SIZE + 1,
						y * GameConfig.TILE_SIZE + 1, GameConfig.TILE_SIZE - 2,
						GameConfig.TILE_SIZE - 2).intersects(player.getCollisionRect())) {
					player.blockMovement();
				}
			}
		}
	}

	@Override
	public void tick(GameContainer gc) {
		System.out.println(player.getCollisionRect().getX() + " | " + player.getCollisionRect().getY());
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

		if (gc.getInput().isKeyDown(Input.KEY_D)) {
			player.normalWalking(PlayerMovement.RIGHT);
		} else if (gc.getInput().isKeyDown(Input.KEY_S)) {
			player.normalWalking(PlayerMovement.DOWN);
		} else if (gc.getInput().isKeyDown(Input.KEY_A)) {
			player.normalWalking(PlayerMovement.LEFT);
		} else if (gc.getInput().isKeyDown(Input.KEY_W)) {
			player.normalWalking(PlayerMovement.UP);
		} else {
			player.setStandingAnimation();
		}
		if (!GameConfig.DEBUG_MODE && gc.getInput().isKeyDown(Input.KEY_0)) {
			GameConfig.DEBUG_MODE = true;

		} else if (GameConfig.DEBUG_MODE && gc.getInput().isKeyDown(Input.KEY_0)) {
			GameConfig.DEBUG_MODE = false;
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
