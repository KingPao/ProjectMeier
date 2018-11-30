package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public abstract class GameEntity {

	private int id;
	protected Point pixelPosition;
	protected Rectangle collisionRect;

	public GameEntity() {

	}

	public GameEntity(Point position) {
		this.pixelPosition = position;
	}

	public abstract void render(Graphics g);

	public abstract void tick(GameContainer gc);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Point getPosition() {
		return pixelPosition;
	}

	public void setPosition(Point position) {
		this.pixelPosition = position;
	}

	public Point getPixelPosition() {
		return pixelPosition;
	}

	public void setPixelPosition(Point pixelPosition) {
		this.pixelPosition = pixelPosition;
	}

	public Rectangle getCollisionRect() {
		return collisionRect;
	}

	public void setCollisionRect(Rectangle collisionRect) {
		this.collisionRect = collisionRect;
	}

}
