package arcer.entity.player;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import arcer.core.Zone;
import arcer.entity.Entity;
import arcer.entity.Unit;
import arcer.level.Levels;
import arcer.resource.Utility;
import arcer.state.GameplayState;
public abstract class Player extends Unit {
	private GameplayState state;
	protected long score = 0;
	// Firing
	protected int fireDelay = 10;
	protected int fireCooldown = 0;
	// Movement
	protected float moveSpeed = 8;
	protected float jumpSpeed = 18;
	public enum Action {MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT,
		ACT_FIRE, TPHOME, NLEVEL, RESTART};
	public static final int[] DEFAULT_KEYS = {Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT,
		Input.KEY_X, Input.KEY_K, Input.KEY_N, Input.KEY_R};
	// Controls
	private ArrayList<Action> controlAction = new ArrayList<Action>();
	private ArrayList<Integer> controlKey = new ArrayList<Integer>();
	private ArrayList<Boolean> controlHeld = new ArrayList<Boolean>();

	public Player(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		for (int i = 0; i < Action.values().length; i++) {
			controlAction.add(Action.values()[i]);
			controlKey.add(DEFAULT_KEYS[i]);
			controlHeld.add(false);
		}
	}
	////////////////////////////////////////////////
	//               Keyboard Input               //
	////////////////////////////////////////////////
	public void keyPressed(int key) {
		Action action = actionOf(key);
		if (action == null) return;
		pressKey(action);
	}
	public void keyReleased(int key) {
		Action action = actionOf(key);
		if (action == null) return;
		releaseKey(action);

		switch (action) {
		case MOVE_UP:
			if (vy < 0) { vy = 0; }
			break;
		case MOVE_DOWN:
			if (vy > 0) { vy = 0; }
			break;
		case MOVE_LEFT:
			if (vx < 0) { vx = 0; }
			break;
		case MOVE_RIGHT:
			if (vx > 0) { vx = 0; }
			break;
		case ACT_FIRE:
			break;
		case TPHOME:
			die();
			break;
		case NLEVEL:
			nextLevel();
			break;
		case RESTART:
			if (isDead()) {
				state.resetLevel();
			} else if (state.hasWon()) {
				state.setCurLevel(Levels.getFirstLevel());
				state.resetLevel();
			}
			break;
		}
	}
	public void keyTyped(KeyEvent ke) { }

	protected void pressKey(Action action) {
		controlHeld.set(action.ordinal(), true);
	}
	protected void releaseKey(Action action) {
		controlHeld.set(action.ordinal(), false);
	}
	protected boolean keyHeld(Action action) {
		return controlHeld.get(action.ordinal());
	}
	protected Action actionOf(int keyCode) {
		int index = controlKey.indexOf(keyCode);
		if (index != -1) {
			return controlAction.get(index);
		}
		return null;
	}

	public void hitBy(Entity attacker, int damage) {
		super.hitBy(attacker, damage);
	}

	protected void preDt() { // handle shooting and movement
		super.preDt();

		if (keyHeld(Action.MOVE_UP) && onPlatform) {
			vy = -jumpSpeed; this.moved();
		}
		if (keyHeld(Action.MOVE_LEFT)) {
			vx = -moveSpeed; this.moved();
		} else if (keyHeld(Action.MOVE_RIGHT)) {
			vx = moveSpeed; this.moved();
		}
	}
	protected void postDt() {
		// prevent moving out of bounds
		if (px < 0) px = 0;
		if (py < 0) py = 0;
	}
	// Player actions
	protected void moved() { } // Called whenever player tries to move
	protected abstract void fireProjectile();
	///////////////////////////////////////
	//               Score               //
	///////////////////////////////////////
	public void addScore(long points) { score += points; }
	public void setScore(long points) { score = points; }
	public long getScore() { return score; }
	// game container specific
	public void setGameplayState(GameplayState state){
		this.state = state;
	}
	private void nextLevel(){
		if (state != null) {
			state.nextLevel();
		} else {
			Utility.printError("GameplayState not set in Player");
		}
	}
	public void die(){
		Utility.log("GAME OVER");
		health = 0;
		state.gameOver();
	}

	public void resetKeys() {
		for (Action action: controlAction){
			releaseKey(action);
		}
		vx = 0;
	}

	public void draw(Graphics g) {
		super.draw(g);
	}
}
