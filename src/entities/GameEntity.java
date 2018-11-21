package entities;

import java.awt.geom.Point2D;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class GameEntity {

	private int id;
	protected Point2D pixelPosition;
	protected Point2D tilePosition;

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

}
