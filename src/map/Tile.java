package map;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import config.GameConfig;
import entities.GameEntity;

/*
 * A single Tile, can be rendered, updated therefore extending GameEntity. 
 * Enables collision with the player.
 */
public class Tile extends GameEntity {

	private boolean collidable;
	private Image tileTexture;

	public Tile(Image image, int x, int y) {
		super(new Point2D.Double(x, y));
		this.collisionRect = new Rectangle2D.Double(x * GameConfig.TILE_SIZE, y * GameConfig.TILE_SIZE,
				GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
		this.tileTexture = image;
		this.collidable = false;
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void tick(GameContainer gc) {

	}

	public Image getTileTexture() {
		return tileTexture;
	}

	public void setTileTexture(Image tileTexture) {
		this.tileTexture = tileTexture;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}
}

