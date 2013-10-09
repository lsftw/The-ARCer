package arcer.entity.player;

import org.newdawn.slick.Image;

import arcer.core.Zone;
import arcer.entity.projectile.Projectile;
import arcer.entity.projectile.SwordAttack;
import arcer.resource.Settings;

//
public class Knight extends Player {
	public int getBaseHealth() { return 1000; }
	public static final int BASE_MOVESPEED = 10;

	protected int attackingFrames = 0; // for animation

	public Knight(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		moveSpeed = BASE_MOVESPEED;
	}

	protected void fireProjectile() { // sword slash!
		attackingFrames = Settings.valueInt("fps")/2;
		Projectile fired = new SwordAttack(this, px + (flipHorizontal ? 0 : sx), py);
		fired = new SwordAttack(this, px + (flipHorizontal ? 0 : sx), py + sy /3);
		container.addEntity(fired);
		fired = new SwordAttack(this, px + (flipHorizontal ? 0 : sx), py + sy *2/3);
	}
	public Image getFrameToDraw() {
		if (attackingFrames > 0) {
			return sprite.getFrame("attack");
		} else if (vy < 0) {
			return sprite.getFrame("jump");
		} else if (Math.abs(vx) > 0.01) {
			return sprite.getFrame("move");
		}
		return super.getFrameToDraw();
	}

	protected void preDt() {
		if (attackingFrames > 0) {
			attackingFrames--;
		}
		super.preDt();
	}
}
