package app;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import config.GameConfig;

public class GameApp extends BasicGame {

	private GameLevel level;

	public GameApp(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		level = new GameLevel();

	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		level.tick(gc);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		level.render(g);
		if(GameConfig.DEBUG_MODE)
			printScreen(g);

	}

	public void printScreen(Graphics g) {

		Graphics blackbox = new Graphics();
		blackbox.setColor(Color.black);
		blackbox.fillRect(10, 10, 200, 20);
		
		g.drawString(String.valueOf(level.getPlayer().getPosition().getX()) + "/"
				+ String.valueOf(level.getPlayer().getPosition().getY()), 100, 10);

		g.flush();
		blackbox.flush();
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new GameApp(GameConfig.GAME_TITLE));
			appgc.setDisplayMode(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT, false);
			appgc.setShowFPS(GameConfig.SHOW_FPS);
			appgc.setTargetFrameRate(60);
			appgc.setVSync(true);
			appgc.start();

		} catch (SlickException ex) {
			Logger.getLogger(GameApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}