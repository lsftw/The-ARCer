package arcer.enemy;

import arcer.core.Zone;
import arcer.entity.Unit;
import arcer.entity.projectile.Arrow;
import arcer.resource.Settings;

public class Archer extends Unit {
	public int getBaseHealth() { return 75; }

	protected static final int FIRE_COOLDOWN = Settings.valueInt("fps");
	protected int fireDelay = 0;

	public Archer(Zone zone, float x, float y) {
		super(zone, x, y);
		flipHorizontal = true;
//		terrainCollidable = true;
		allegiance = Team.ENEMY;
	}

	public void preDt() {
		vy = -1; // antigrav kit
		if (fireDelay == 0) {
			Arrow fired = new Arrow(this, px + (flipHorizontal ? -50 : 100), py);
			container.addEntity(fired);
			fireDelay = FIRE_COOLDOWN;
		} else {
			fireDelay--;
		}
	}
}