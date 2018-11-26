package graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import config.GameConfig;
import map.Tile;

public class SpriteSheet {

	// the "sprite sheet" or texture atlas image
	private Image spriteSheet;

	// the sub-images of our sprite sheet
	private Tile[][] tileTextures;

	// TODO: beautify
	public SpriteSheet(String path) {
		try {
			this.spriteSheet = new Image(path, false, Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		tileTextures = new Tile[8][6];

		for (int x = 0; x < tileTextures.length; x++) {
			for (int y = 0; y < tileTextures[x].length; y++) {
				tileTextures[x][y] = new Tile(spriteSheet.getSubImage(x * GameConfig.TILE_SIZE + (GameConfig.TILE_SPACING * x),
						y * GameConfig.TILE_SIZE + (GameConfig.TILE_SPACING * y), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE), x, y);
				

			}
		}
	}

	public Image getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(Image spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public Tile[][] getTileTextures() {
		return tileTextures;
	}

	public void setTileTextures(Tile[][] tileTextures) {
		this.tileTextures = tileTextures;
	}





}
