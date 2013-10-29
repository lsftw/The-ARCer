package arcer.entity.projectile;

import arcer.entity.Entity;
import arcer.resource.Settings;

// TODO angle of fire
public class Arrow extends Projectile {
	public Arrow(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		damage = 25;
		targetting = TargetType.ALL;
		vx = owner.isFacingRight() ? 2 : -2;
		vy = -10;

		duration = Settings.valueInt("fps") * 10;
		terrainCollidable = false;
	}
	public void preDt() {
		vy += 5f / (Settings.valueInt("fps")); // gravity
//		angle = (float) Math.toDegrees(Math.atan2(vy, vx));
	}
}