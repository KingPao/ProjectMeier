package entities;

import java.awt.geom.Point2D;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import config.GameConfig;

public class Player extends GameEntity {

	// states
	private int score;
	private boolean moving;
	private Point2D oldpos = new Point2D.Double(0, 0);
	private String lastmoved = null;
	private boolean rightcl, downcl, leftcl, upcl = false;

	public Player() {
		super(new Point2D.Double());
		pixelPosition.setLocation(0, 0);
		this.score = 0;
	}

	@Override
	public void tick(GameContainer gc) {
		handleInput(gc);
		checkCollision();
	}

	@Override
	public void render(Graphics g) {
		g.setDrawMode(Graphics.MODE_NORMAL);
		g.fillOval((float) pixelPosition.getX(), (float) pixelPosition.getY(), 32, 32);
	}

	public void checkCollision() {
		if (getPosition().getX() > GameConfig.SCREEN_WIDTH - GameConfig.TILE_SIZE) {
			setPosition(new Point2D.Double(GameConfig.SCREEN_WIDTH - GameConfig.TILE_SIZE, getPosition().getY()));
			oldpos = new Point2D.Double(getPosition().getX(), getPosition().getY());
			rightcl = true;
			moving = false;
		} else if (getPosition().getX() < 0) {
			setPosition(new Point2D.Double(0, getPosition().getY()));
			oldpos = new Point2D.Double(getPosition().getX(), getPosition().getY());
			leftcl = true;
			moving = false;
		} else if (getPosition().getY() < 0) {
			setPosition(new Point2D.Double(getPosition().getX(), 0));
			oldpos = new Point2D.Double(getPosition().getX(), getPosition().getY());
			upcl = true;
			moving = false;
		} else if (getPosition().getY() > GameConfig.SCREEN_HEIGHT - GameConfig.TILE_SIZE) {
			setPosition(new Point2D.Double(getPosition().getX(), GameConfig.SCREEN_HEIGHT - GameConfig.TILE_SIZE));
			oldpos = new Point2D.Double(getPosition().getX(), getPosition().getY());
			downcl = true;
			moving = false;
		}

		rightcl = false;
		leftcl = false;
		downcl = false;
		upcl = false;
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

	// TODO: Use vector for point manipulation
	public boolean walkTowardsTile(String dir) {
		moving = true;
		if (!rightcl && dir == "RIGHT") {
			if (pixelPosition.getX() < oldpos.getX() + GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX() + GameConfig.PLAYER_SPEED, pixelPosition.getY());
				lastmoved = dir;
				return true;
			}
		} else if (!downcl && dir == "DOWN") {
			if (pixelPosition.getY() < oldpos.getY() + GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX(), pixelPosition.getY() + GameConfig.PLAYER_SPEED);
				lastmoved = dir;
				return true;
			}
		} else if (!leftcl && dir == "LEFT") {
			if (pixelPosition.getX() > oldpos.getX() - GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX() - GameConfig.PLAYER_SPEED, pixelPosition.getY());
				lastmoved = dir;
				return true;
			}
		} else if (!upcl && dir == "UP") {
			if (pixelPosition.getY() > oldpos.getY() - GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX(), pixelPosition.getY() - GameConfig.PLAYER_SPEED);
				lastmoved = dir;
				return true;
			}
		}

		oldpos.setLocation(pixelPosition.getX(), pixelPosition.getY());
		moving = false;
		return false;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
