package app;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import config.GameConfig;

public class GameApp extends BasicGame {

	private boolean gameover;
	private GameLevel level;
	private int time;

	public GameApp(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gameover = false;
		level = new GameLevel();

	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		if (!gameover) {
			level.tick(gc);
			time += i;
		}

		if (time / 1000 >= GameConfig.MAX_TIME) {
			gameover = true;
		}

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (!gameover) {
			level.render(g);
			printScreen(g);
			
		}
	}

	public void printScreen(Graphics g) {
		g.drawString("Time left: " + String.valueOf(GameConfig.MAX_TIME - time / 1000), 400, 10);
		g.drawString(String.valueOf(Math.round(level.getPlayer().getPosition().getX())) + "/"
				+ String.valueOf(Math.round(level.getPlayer().getPosition().getY())), 100, 10);
		g.drawString("Score: " + String.valueOf(level.getPlayer().getScore()), 250, 10);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new GameApp(GameConfig.GAME_TITLE));
			appgc.setDisplayMode(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT, false);
			appgc.setShowFPS(GameConfig.SHOW_FPS);
			appgc.setTargetFrameRate(60);
			appgc.start();

		} catch (SlickException ex) {
			Logger.getLogger(GameApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}