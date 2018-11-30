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

	// currently moving
	private boolean moving;
	// keep moving into same direction until new tile is reached
	private PlayerMovement lastmoved = PlayerMovement.NONE;
	// collides
	private boolean sprinting;
	private float sprintSpeed;

	private SpriteSheet heroSheet;
	private Animation currentAnimation, right, rightstand, down, downstand, left, leftstand, up, upstand;

	public Player() {
		super(new Point(0, 0));
		pixelPosition.setLocation(0, 0);
		sprintSpeed = 1f;
		this.collisionRect = new Rectangle(4, 8, GameConfig.TILE_SIZE - 8, GameConfig.TILE_SIZE / 2);
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

		currentAnimation.draw(pixelPosition.getX(), pixelPosition.getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
		if (GameConfig.DEBUG_MODE)
			g.draw(collisionRect);

	}

	public float getSprintSpeed() {
		return sprintSpeed;
	}

	public void setSprintSpeed(float sprintSpeed) {
		this.sprintSpeed = sprintSpeed;
	}

	public void blockMovement() {
		switch (lastmoved) {
		case RIGHT:
			getPosition().setLocation(getPosition().getX() - GameConfig.PLAYER_SPEED * sprintSpeed,
					getPosition().getY());
			break;
		case LEFT:
			getPosition().setLocation(getPosition().getX() + GameConfig.PLAYER_SPEED * sprintSpeed,
					getPosition().getY());
			break;
		case UP:
			getPosition().setLocation(getPosition().getX(),
					getPosition().getY() + GameConfig.PLAYER_SPEED * sprintSpeed);
			break;
		case DOWN:
			getPosition().setLocation(getPosition().getX(),
					getPosition().getY() - GameConfig.PLAYER_SPEED * sprintSpeed);
			break;
		default:
			break;
		}

		updateCollisionRectangle();

	}

	public void setStandingAnimation() {
		
		switch (lastmoved) {
		case RIGHT:
			currentAnimation = rightstand;
			break;
		case LEFT:
			currentAnimation = leftstand;
			break;
		case UP:
			currentAnimation = upstand;
			break;
		case DOWN:
			currentAnimation = downstand;
			break;
		default:
			break;
		}
	}

	public void updateCollisionRectangle() {
		collisionRect.setX(pixelPosition.getX() + 4);
		collisionRect.setY(pixelPosition.getY() + 8);
	}

	public void checkMapBounds() {
		if (collisionRect.getX() > GameConfig.SCREEN_WIDTH - GameConfig.TILE_SIZE) {
			blockMovement();
		} else if (collisionRect.getX() < 0) {
			blockMovement();
		} else if (collisionRect.getY() < 0) {
			blockMovement();
		} else if (collisionRect.getY() > GameConfig.SCREEN_HEIGHT - GameConfig.TILE_SIZE) {
			blockMovement();
		}

	}

	public void sprint() {
		/*
		 * Avoid wrong collision detection by allowing sprint trigger only at convenient
		 * times Only allow sprint if current position is divisible by 4.
		 */
//		if (pixelPosition.getX() % 2 == 0 && pixelPosition.getY() % 2 == 0) {
		sprintSpeed = 2.0f;
//		}

	}

	public void normalWalking(PlayerMovement direction) {
		
		if (sprinting) {
			sprint();
		} else {
			sprintSpeed = 1.0f;
		}

		switch (direction) {
		case RIGHT:
			pixelPosition.setLocation(pixelPosition.getX() + GameConfig.PLAYER_SPEED * sprintSpeed,
					pixelPosition.getY());
			currentAnimation = right;
			moving = true;
			break;
		case LEFT:
			pixelPosition.setLocation(pixelPosition.getX() - GameConfig.PLAYER_SPEED * sprintSpeed,
					pixelPosition.getY());
			currentAnimation = left;
			moving = true;
			break;
		case UP:
			pixelPosition.setLocation(pixelPosition.getX(),
					pixelPosition.getY() - GameConfig.PLAYER_SPEED * sprintSpeed);
			currentAnimation = up;
			moving = true;
			break;
		case DOWN:
			pixelPosition.setLocation(pixelPosition.getX(),
					pixelPosition.getY() + GameConfig.PLAYER_SPEED * sprintSpeed);
			currentAnimation = down;
			moving = true;
			break;
		default:
			break;
		}
		lastmoved = direction;
		updateCollisionRectangle();
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


	public boolean collidesWith(Rectangle rect) {
		return collisionRect.intersects(rect);
	}

	public boolean isSprinting() {
		return sprinting;
	}

	public void setSprinting(boolean sprinting) {
		this.sprinting = sprinting;
	}

}
