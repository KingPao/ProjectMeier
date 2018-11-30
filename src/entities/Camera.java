package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Camera extends GameEntity {

	public float camX;
	public float camY;

	private Player player;

	public Camera(Player p) {


		player = p;

	}

	@Override
	public void render(Graphics g) {

		g.translate(-camX, -camY);
		g.scale(2f, 2f);
	}

	@Override
	public void tick(GameContainer gc) {
		camX = player.getPosition().getX() * 2 - (gc.getWidth() / 2);
		camY = player.getPosition().getY() * 2 - (gc.getHeight() / 2);
	}

}