package graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import config.GameConfig;

public class SpriteSheet {

	// the "sprite sheet" or texture atlas image
	private Image spriteSheet;

	// the sub-images of our sprite sheet
	private Image[][] tileSprites;

	// TODO: beautify
	public SpriteSheet(String path) {
		try {
			this.spriteSheet = new Image(path, false, Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.tileSprites = new Image[8][6];

		for (int x = 0; x < tileSprites.length; x++) {
			for (int y = 0; y < tileSprites[x].length; y++) {
				tileSprites[x][y] = spriteSheet.getSubImage(x * GameConfig.TILE_SIZE + (GameConfig.TILE_SPACING * x),
						y * GameConfig.TILE_SIZE + (GameConfig.TILE_SPACING * y), GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE);

			}
		}
	}

	public Image getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(Image spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public Image[][] getTileSprites() {
		return tileSprites;
	}

	public void setTileSprites(Image[][] tileSprites) {
		this.tileSprites = tileSprites;
	}

}
