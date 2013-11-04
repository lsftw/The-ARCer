package arcer.entity.player;

import org.newdawn.slick.Image;

import arcer.core.Zone;
import arcer.entity.projectile.Projectile;
import arcer.entity.projectile.SwordAttack;

// TODO rotated rectangle shield
public class Knight extends Player {
	public int getBaseHealth() { return 1000; }
	public static final int BASE_MOVESPEED = 10;

	public Knight(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		moveSpeed = BASE_MOVESPEED;
	}

	protected void fireProjectile() { // sword slash!
		Projectile fired = new SwordAttack(this, px + (flipHorizontal ? 0 : sx), py);
		fired = new SwordAttack(this, px + (flipHorizontal ? 0 : sx), py + sy /3);
		container.addEntity(fired);
		fired = new SwordAttack(this, px + (flipHorizontal ? 0 : sx), py + sy *2/3);
	}
	public Image getFrameToDraw() {
		if (vy < 0) {
			container.addEntity(new Shield(container, px, py));
			return sprite.getFrame("jump");
		} else if (Math.abs(vx) > 0.01) {
			return sprite.getFrame("move");
		}
		return super.getFrameToDraw();
	}

	protected void preDt() {
		super.preDt();
	}
}
