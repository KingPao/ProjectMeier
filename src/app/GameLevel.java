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
		}

		for (Tile[] tiles : map.getTileMap()) {
			for (Tile tile : tiles) {
				if (tile.isCollidable() && tile.getCollisionRect().intersects(player.getCollisionRect())) {
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

		/*
		 * sprinting
		 */
		if (!player.isJumping() && gc.getInput().isKeyDown(Input.KEY_LCONTROL))
			player.setSprinting(true);
		else
			player.setSprinting(false);

		/*
		 * jumping
		 */
		if (!player.isSprinting() && !player.isJumping() && gc.getInput().isKeyDown(Input.KEY_SPACE)) {
			player.setJumping(true);
			player.setGravity(-4f);
			System.out.println("jump");
		}

		if (player.isJumping()) {
			player.jump();
		}

		/*
		 * movement
		 */
		if (!player.isMoving() && gc.getInput().isKeyDown(Input.KEY_RIGHT))
			player.walkTowardsTile(PlayerMovement.RIGHT);
		else if (!player.isMoving() && gc.getInput().isKeyDown(Input.KEY_DOWN))
			player.walkTowardsTile(PlayerMovement.DOWN);
		else if (!player.isMoving() && gc.getInput().isKeyDown(Input.KEY_LEFT))
			player.walkTowardsTile(PlayerMovement.LEFT);
		else if (!player.isMoving() && gc.getInput().isKeyDown(Input.KEY_UP))
			player.walkTowardsTile(PlayerMovement.UP);

		/*
		 * keep moving into direction
		 */
		if (player.isMoving())
			player.walkTowardsTile(player.getLastmoved());

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
