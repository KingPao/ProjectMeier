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
	private Point oldpos;
	// keep moving into same direction until new tile is reached
	private PlayerMovement lastmoved = PlayerMovement.NONE;
	// collides
	private boolean colliding;

	private boolean sprinting;
	private float sprintSpeed;

	private SpriteSheet heroSheet;
	private Animation currentAnimation, right, rightstand, down, downstand, left, leftstand, up, upstand;

	public Player() {
		super(new Point(0, 0));
		pixelPosition.setLocation(0, 0);
		oldpos = new Point(pixelPosition.getX(), pixelPosition.getY());
		sprintSpeed = 1f;
		this.score = 0;
		this.collisionRect = new Rectangle(0, 0, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
		try {
			this.heroSheet = new SpriteSheet(GameConfig.PLAYER_SHEET, 32, 32);
			downstand = new Animation(heroSheet, 1, 0, 1, 0, true, 1, true);
			down = new Animation(heroSheet, 0, 0, 3, 0, true, 120, true);
			rightstand = new Animation(heroSheet, 1, 2, 1, 2, true, 1, true);
			right = new Animation(heroSheet, 0, 2, 3, 2, true, GameConfig.ANIMATION_SPEED_MOVEMENT, true);
			upstand = new Animation(heroSheet, 1, 3, 1, 3, true, 1, true);
			up = new Animation(heroSheet, 0, 3, 3, 3, true, GameConfig.ANIMATION_SPEED_MOVEMENT, true);
			leftstand = new Animation(heroSheet, 1, 1, 1, 1, true, 1, true);
			left = new Animation(heroSheet, 0, 1, 3, 1, true, GameConfig.ANIMATION_SPEED_MOVEMENT, true);
			currentAnimation = downstand;
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void tick(GameContainer gc) {

	}

	@Override
	public void render(Graphics g) {


//			new Graphics().draw(collisionRect);
//			new Graphics().fillOval(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
			currentAnimation.draw(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
			if(GameConfig.DEBUG_MODE)
				g.draw(collisionRect);
		

	}

	public void blockMovement() {
		getPosition().setLocation(oldpos.getLocation());
		collisionRect.setBounds(getPosition().getX(), getPosition().getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
		moving = false;
		setStandingAnimation();
	}

	public void setStandingAnimation() {
		if (lastmoved == PlayerMovement.DOWN) {
			currentAnimation = downstand;
		} else if (lastmoved == PlayerMovement.RIGHT) {
			currentAnimation = rightstand;
		} else if (lastmoved == PlayerMovement.UP) {
			currentAnimation = upstand;
		} else if (lastmoved == PlayerMovement.LEFT) {
			currentAnimation = leftstand;
		}
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

	public void sprint() {
		/*
		 * Avoid wrong collision detection by allowing sprint trigger only at convenient
		 * times Only allow sprint if current position is divisible by 4.
		 */
		if (pixelPosition.getX() % 4 == 0 && pixelPosition.getY() % 4 == 0) {
			sprintSpeed = 2.0f;
		}

	}

	// TODO: minimize
	public void walkTowardsTile(PlayerMovement direction) {
		moving = true;

		if (sprinting) {
			sprint();
		} else {
			sprintSpeed = 1.0f;
		}
		if (direction == PlayerMovement.RIGHT) {
			if (pixelPosition.getX() < oldpos.getX() + GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX() + GameConfig.PLAYER_SPEED * sprintSpeed,
						pixelPosition.getY());
				lastmoved = direction;
				collisionRect.setBounds(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				currentAnimation = right;
				return;
			}
		} else if (direction == PlayerMovement.DOWN) {
			if (pixelPosition.getY() < oldpos.getY() + GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX(),
						pixelPosition.getY() + Math.round(GameConfig.PLAYER_SPEED * sprintSpeed / 1.0f) * 1.0f);
				lastmoved = direction;
				collisionRect.setBounds(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				currentAnimation = down;
				return;
			}
		} else if (direction == PlayerMovement.LEFT) {
			if (pixelPosition.getX() > oldpos.getX() - GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(
						pixelPosition.getX() - Math.round(GameConfig.PLAYER_SPEED * sprintSpeed / 2.0f) * 2.0f,
						pixelPosition.getY());
				lastmoved = direction;
				collisionRect.setBounds(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				currentAnimation = left;
				return;
			}
		} else if (direction == PlayerMovement.UP) {
			if (pixelPosition.getY() > oldpos.getY() - GameConfig.TILE_SIZE) {
				pixelPosition.setLocation(pixelPosition.getX(),
						pixelPosition.getY() - Math.round(GameConfig.PLAYER_SPEED * sprintSpeed / 2.0f) * 2.0f);
				lastmoved = direction;
				collisionRect.setBounds(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);
				currentAnimation = up;
				return;
			}
		}

		moving = false;
		blocked = false;

		setStandingAnimation();

		oldpos.setLocation(getPosition().getLocation());
		collisionRect.setBounds(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);

	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

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

	public boolean isSprinting() {
		return sprinting;
	}

	public void setSprinting(boolean sprinting) {
		this.sprinting = sprinting;
	}

}
