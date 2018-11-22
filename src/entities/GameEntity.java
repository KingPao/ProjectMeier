package entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class GameEntity {

	private int id;
	protected Point2D pixelPosition;
	protected Point2D gridPosition;
	protected Rectangle2D collisionRect;

	public GameEntity() {

	}

	public GameEntity(Point2D position) {
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

	public Point2D getPosition() {
		return pixelPosition;
	}

	public void setPosition(Point2D position) {
		this.pixelPosition = position;
	}

	public Point2D getPixelPosition() {
		return pixelPosition;
	}

	public void setPixelPosition(Point2D pixelPosition) {
		this.pixelPosition = pixelPosition;
	}

	public Point2D getGridPosition() {
		return gridPosition;
	}

	public void setGridPosition(Point2D gridPosition) {
		this.gridPosition = gridPosition;
	}

	public Rectangle2D getCollisionRect() {
		return collisionRect;
	}

	public void setCollisionRect(Rectangle2D collisionRect) {
		this.collisionRect = collisionRect;
	}

	
}
