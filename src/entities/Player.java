package entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import config.GameConfig;

public class Player extends GameEntity {

	// states
	private int score;
	// currently moving
	private boolean moving;
	// previous position during moving to a new tile
	private Point2D oldpos = new Point2D.Double(0, 0);
	// keep moving into same direction until new tile is reached
	private String lastmoved = null;
	//collides
	private boolean colliding;


	public Player() {
		super(new Point2D.Double());
		pixelPosition.setLocation(0, 0);
		this.score = 0;
		this.collisionRect = new Rectangle2D.Double(0, 0, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
	}

	@Override
	public void tick(GameContainer gc) {

	}

	@Override
	public void render(Graphics g) {
		g.setDrawMode(Graphics.MODE_NORMAL);
		g.fillOval((float) pixelPosition.getX(), (float) pixelPosition.getY(), 32, 32);
		g.drawRect((float) collisionRect.getX(), (float) collisionRect.getY(), (float) collisionRect.getWidth(),
				(float) collisionRect.getHeight());
	}

	public void blockMovement() {
		getPosition().setLocation(oldpos);
		collisionRect.setFrame(getPosition().getX(), getPosition().getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
		moving = false;

	}
	
	public boolean checkMapBounds() {
		if (collisionRect.getX() > GameConfig.SCREEN_WIDTH - GameConfig.TILE_SIZE) {
			return true;
		} else if (collisionRect.getX() < 0) {
			return true;
		} else if (collisionRect.getY() < 0) {
			return true;
		} else if (collisionRect.getY() > GameConfig.SCREEN_HEIGHT - GameConfig.TILE_SIZE) {
			return true;
		}else return false;
	}

	public void handleInput(GameContainer gc) {

		if (!moving) {
			if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
				walkTowardsTile("RIGHT");
			} else if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
				walkTowardsTile("DOWN");
			} else if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
				walkTowardsTile("LEFT");
			} else if (gc.getInput().isKeyDown(Input.KEY_UP)) {
				walkTowardsTile("UP");
			}
		} else {
			walkTowardsTile(lastmoved);
		}
	}

	// TODO: minimize
	public boolean walkTowardsTile(String dir) {
		moving = true;
		if (dir == "RIGHT") {
			if (pixelPosition.getX() < oldpos.getX() + GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX() + GameConfig.PLAYER_SPEED, pixelPosition.getY());
				lastmoved = dir;
				collisionRect.setFrame(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				return true;
			}
		} else if (!colliding && dir == "DOWN") {
			if (pixelPosition.getY() < oldpos.getY() + GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX(), pixelPosition.getY() + GameConfig.PLAYER_SPEED);
				lastmoved = dir;
				collisionRect.setFrame(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				return true;
			}
		} else if (!colliding && dir == "LEFT") {
			if (pixelPosition.getX() > oldpos.getX() - GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX() - GameConfig.PLAYER_SPEED, pixelPosition.getY());
				lastmoved = dir;
				collisionRect.setFrame(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				return true;
			}
		} else if (!colliding && dir == "UP") {
			if (pixelPosition.getY() > oldpos.getY() - GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX(), pixelPosition.getY() - GameConfig.PLAYER_SPEED);
				lastmoved = dir;
				collisionRect.setFrame(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				return true;
			}
		}

		oldpos.setLocation(getPosition());
		moving = false;
		return false;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean collidesWith(Rectangle2D rect) {
		return collisionRect.intersects(rect);
	}

}
