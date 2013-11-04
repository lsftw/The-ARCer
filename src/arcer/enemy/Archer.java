package arcer.enemy;

import arcer.core.Zone;
import arcer.entity.Unit;
import arcer.entity.projectile.Arrow;
import arcer.resource.Settings;

public class Archer extends Unit {
	public int getBaseHealth() { return 75; }

	protected static final int FIRE_COOLDOWN = Settings.valueInt("fps")/2;
	protected int fireDelay = 0;
	protected Arrow arrow;

	public Archer(Zone zone, float x, float y) {
		super(zone, x, y);
		flipHorizontal = true;
//		terrainCollidable = true;
		allegiance = Team.ENEMY;
	}

	public void preDt() {
		vy = 0; // antigrav kit
		if (fireDelay == 0) {
			if (!container.hasEntity(arrow)){
				Arrow fired = new Arrow(this, px + (flipHorizontal ? -20 : 20 + sx), py);
				container.addEntity(fired);
				arrow = fired;
				fireDelay = FIRE_COOLDOWN;
			}
		} else {
			fireDelay--;
		}
	}
}
