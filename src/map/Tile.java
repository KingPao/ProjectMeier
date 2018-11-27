package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import config.GameConfig;
import entities.GameEntity;

/*
 * A single Tile, can be rendered, updated therefore extending GameEntity. 
 * Enables collision with the player.
 */
public class Tile extends GameEntity {

	private Image tileTexture;

	public Tile(Image image, int x, int y) {
		super(new Point(x, y));
		this.collisionRect = new Rectangle(x * GameConfig.TILE_SIZE +1, y * GameConfig.TILE_SIZE +1,
				GameConfig.TILE_SIZE-2, GameConfig.TILE_SIZE-2);
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
