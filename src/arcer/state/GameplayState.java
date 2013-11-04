package arcer.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import arcer.core.Animation;
import arcer.core.TickHandler;
import arcer.core.Zone;
import arcer.entity.player.Player;
import arcer.entity.player.Shield;
import arcer.gui.BbrGameState;
import arcer.gui.HealthController;
import arcer.level.Level;
import arcer.level.LevelHandler;
import arcer.level.Levels;
import arcer.main.ArcerRunner;

public class GameplayState extends BbrGameState implements LevelHandler, TickHandler {
	protected boolean won = false;
	protected boolean lost = false;
	protected long tickCount = 0; // used for animation

	protected Zone zone;
	protected Level curLevel;
	protected Player player;
	protected Shield shield;
	protected HealthController healthBar;

	public GameplayState() throws SlickException {
		super(ArcerRunner.States.GAME);
		Animation.setFrameHandler(this);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		zone = new Zone(this);
		curLevel = Levels.getFirstLevel();
		curLevel.loadInto(zone);
		player = zone.getPlayer();
		player.setGameplayState(this);
		healthBar = new HealthController(player);
		shield = new Shield(player);
		zone.addEntity(shield);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		zone.draw(g);
		shield.updatePosition(gc.getInput().getMouseX(), gc.getInput().getMouseY());
		if (healthBar != null) {
			healthBar.draw();
		}
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		zone.dt();
		tickCount++;
	}

	/**
	 * Proceeds to the next level.
	 */
	public void nextLevel() {
		Level nextLevel = Levels.getNextLevel(curLevel);
		if (nextLevel != null) {
			if (nextLevel.getName().equals("win.txt")) {
				won = true;
				healthBar = null;
				curLevel = nextLevel;
				curLevel.loadInto(zone);
				zone.clear();
			} else {
				curLevel = nextLevel;
				zone.clear();
				curLevel.loadInto(zone);
				player = zone.getPlayer();
				healthBar.changeUnit(player);
				player.setGameplayState(this);
			}
		}
	}
	/**
	 * Resets the current level, reloading all the level information and resetting all entity states.
	 */
	public void resetLevel(){
		zone.clear();
		curLevel.loadInto(zone);
		player = zone.getPlayer();
		if (healthBar == null) {
			healthBar = new HealthController(player);
		}
		healthBar.changeUnit(player);
		player.setGameplayState(this);
	}
	/**
	 * Ends the game, moving into the game over level.
	 */
	public void gameOver() {
		healthBar = null; // hide healthbar
		Levels.gameOver().loadInto(zone);
		zone.clear();
	}

	public Level getCurLevel(){ return curLevel; }
	public void setCurLevel(Level l) { curLevel = l; }

	public boolean hasWon() { return won; }
	public long getTickCount() { return tickCount; }

	public void keyPressed(int key, char c) { player.keyPressed(key); }
	public void keyReleased(int key, char c) { player.keyReleased(key); }
}
