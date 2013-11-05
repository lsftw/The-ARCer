package arcer.entity.player;

import org.newdawn.slick.Image;

import arcer.core.Zone;
import arcer.entity.projectile.Projectile;
import arcer.entity.projectile.SwordAttack;

public class Knight extends Player {
	public int getBaseHealth() { return 500; }
	public static final int BASE_MOVESPEED = 10;
	protected Shield shield;

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
			return sprite.getFrame("jump");
		} else if (Math.abs(vx) > 0.01) {
			return sprite.getFrame("move");
		}
		return super.getFrameToDraw();
	}
	public Shield makeShield() {
		if (shield == null) {
			shield = new Shield(this);
		}
		return shield;
	}

	protected void preDt() {
		super.preDt();
	}
}
