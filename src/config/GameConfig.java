package config;

public class GameConfig {

	// PLAYER
	public static final float PLAYER_SPEED = 2f;
	public static final int ANIMATION_SPEED_MOVEMENT = 100;

	// ENEMIES
	public static final int NUMBER_ENEMIES = 2;
	public static final int ENEMY_MAX_SIZE = 80;
	public static final double ENEMY_MIN_GROWTH = 0.04;
	public static final double ENEMY_MAX_GROWTH = 0.09;

	// FILES
	public static final String PLAYER_SHEET = "res/hero.png";
	public static final String SPRITESHEET_MAP = "res/desert.png";
	public static final String FILE_DEMOMAP = "res/demomap_coordinates.csv";
	public static final String FILE_DEMOMAP_COLLIDABLE = "res/demomap_collidable_tiles.csv";

	// TILES
	public static final int TILE_COUNT = 48;
	public static final int TILE_SIZE = 32;
	public static final int TILE_SPACING = 1;

	// GAME
	public static final String GAME_TITLE = "Aim Trainer";
	public static final boolean SHOW_FPS = true;
	public static final int MAX_TIME = 60;
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 576;
}
