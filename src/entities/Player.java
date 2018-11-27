package entities;


import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import behaviour.PlayerMovement;
import config.GameConfig;

public class Player extends GameEntity {

	// states
	private int score;
	// currently moving
	private boolean moving, blocked;
	// previous position during moving to a new tile
	private Point oldpos = new Point(0, 0);
	// keep moving into same direction until new tile is reached
	private PlayerMovement lastmoved = PlayerMovement.NONE;
	// collides
	private boolean colliding;
	
	private SpriteSheet heroSheet;
	private Animation rightAnimation;
	
	public Player() {
		super(new Point(0,0));
		pixelPosition.setLocation(0, 0);
		this.score = 0;
		this.collisionRect = new Rectangle(0, 0, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
		try {
			this.heroSheet = new SpriteSheet(GameConfig.PLAYER_SHEET, 32, 32);
			rightAnimation = new Animation(heroSheet, 0, 2, 2, 2, true, 120, true);
;		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void tick(GameContainer gc) {

	}

	@Override
	public void render(Graphics g) {
//		g.drawRect(collisionRect.getX(), collisionRect.getY(), collisionRect.getWidth(),
//				collisionRect.getHeight());
		
		g.drawImage(heroSheet.getSprite(0, 0), pixelPosition.getX(), pixelPosition.getY());
		rightAnimation.draw(pixelPosition.getX(), pixelPosition.getY());
	}

	public void blockMovement() {
		moving = false;
		blocked = true;
		getPosition().setLocation(oldpos.getLocation());
		collisionRect.setBounds(getPosition().getX(), getPosition().getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
		
		System.out.println("blocked");

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
		} else
			return false;
	}

	// TODO: minimize
	public void walkTowardsTile(PlayerMovement direction) {
		moving = true;
		if (direction == PlayerMovement.RIGHT) {
			if (pixelPosition.getX() < oldpos.getX() + GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX() + GameConfig.PLAYER_SPEED, pixelPosition.getY());
				lastmoved = direction;
				collisionRect.setBounds(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				return;
			}
		} else if (direction == PlayerMovement.DOWN) {
			if (pixelPosition.getY() < oldpos.getY() + 32) {
				pixelPosition.setLocation(pixelPosition.getX(), pixelPosition.getY() + GameConfig.PLAYER_SPEED);
				lastmoved = direction;
				collisionRect.setBounds(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				return;
			}
		} else if (direction == PlayerMovement.LEFT) {
			if (pixelPosition.getX() > oldpos.getX() - GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX() - GameConfig.PLAYER_SPEED, pixelPosition.getY());
				lastmoved = direction;
				collisionRect.setBounds(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				return;
			}
		} else if (direction == PlayerMovement.UP) {
			if (pixelPosition.getY() > oldpos.getY() - GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX(), pixelPosition.getY() - GameConfig.PLAYER_SPEED);
				lastmoved = direction;
				collisionRect.setBounds(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				return;
			}
		}

		oldpos.setLocation(getPosition().getLocation());
		moving = false;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

//	public Point2D getOldpos() {
//		return oldpos;
//	}
//
//	public void setOldpos(Point2D oldpos) {
//		this.oldpos = oldpos;
//	}

	public PlayerMovement getLastmoved() {
		return lastmoved;
	}

	public void setLastmoved(PlayerMovement lastmoved) {
		this.lastmoved = lastmoved;
	}

	public boolean isColliding() {
		return colliding;
	}

	public void setColliding(boolean colliding) {
		this.colliding = colliding;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean collidesWith(Rectangle rect) {
		return collisionRect.intersects(rect);
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	

}
