package entities.enemies;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import behaviour.Attackable;
import config.GameConfig;
import entities.GameEntity;
import entities.Player;

public class Bubble extends GameEntity implements Attackable{

	private double size;
	private boolean alive;
	private double maxsize;
	private Ellipse2D circle;
	private double growFactor;
	private Player player;

	public Bubble(GameContainer gc, Player player) {
		// Place bubble in random location
		super(new Point2D.Double(ThreadLocalRandom.current().nextDouble(0, GameConfig.SCREEN_WIDTH),
				(double) ThreadLocalRandom.current().nextDouble(0, GameConfig.SCREEN_HEIGHT)));

		// use player for aim and score reference
		this.player = player;
		this.size = 0;
		this.alive = true;
		this.maxsize = GameConfig.ENEMY_MAX_SIZE;
		this.circle = new Ellipse2D.Double(pixelPosition.getX(), pixelPosition.getY(), size, size);

		// random growth speed
		this.growFactor = ThreadLocalRandom.current().nextDouble(GameConfig.ENEMY_MIN_GROWTH,
				GameConfig.ENEMY_MAX_GROWTH);
	}

	@Override
	public void tick(GameContainer gc) {
		if (checkClick(player.getPosition(), gc)) {
			alive = false;
		}

		if (alive) {
			grow();
		} else
			respawn();
	}

	@Override
	public void render(Graphics g) {
		g.setDrawMode(Graphics.MODE_NORMAL);
		g.fillOval((float) (circle.getFrame().getX()), (float) (circle.getFrame().getY()), (float) size, (float) size);
	}

	@Override
	public boolean checkClick(Point2D aim, GameContainer gc) {
		if (circle.contains(aim) && gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			handleAttack();
			return true;
		}
		return false;
	}

	@Override
	public void handleAttack() {
		player.setScore((int) (player.getScore() + (GameConfig.ENEMY_MAX_SIZE - this.getSize())));
		alive = false;
	}

	public void grow() {
		if (size < maxsize) {
			size += growFactor;
			circle.setFrame(pixelPosition.getX() - size / 2, pixelPosition.getY() - size / 2, size, size);
		} else {
			alive = false;
		}
	}

	public void respawn() {
		size = 0;
		this.getPosition().setLocation(ThreadLocalRandom.current().nextInt(0, GameConfig.SCREEN_WIDTH),
				ThreadLocalRandom.current().nextInt(0, GameConfig.SCREEN_HEIGHT));
		circle.setFrame(pixelPosition.getX(), pixelPosition.getY(), size, size);
		alive = true;
	}

	public double getMaxsize() {
		return maxsize;
	}

	public void setMaxsize(double maxsize) {
		this.maxsize = maxsize;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Ellipse2D getCircle() {
		return circle;
	}

	public void setCircle(Ellipse2D circle) {
		this.circle = circle;
	}

}
