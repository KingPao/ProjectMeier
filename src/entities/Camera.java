package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Camera extends GameEntity {

	public float offsetMaxX;
	public float offsetMaxY;
	public float offsetMinX;
	public float offsetMinY;

	public float camX;
	public float camY;

	private Player player;

	public Camera(Player p) {

		// Setting offset max's and minimums
		offsetMaxX = 0;
		offsetMaxY = 0;
		offsetMinX = 0;
		offsetMinY = 0;

		player = p;

	}

	@Override
	public void render(Graphics g) {

		g.translate(-camX, -camY);
		g.scale(2f, 2f);

	}

	@Override
	public void tick(GameContainer gc) {
		camX = player.getPosition().getX() *2 - (gc.getWidth() / 2);
		camY = player.getPosition().getY() *2- (gc.getHeight() / 2);

	}

}