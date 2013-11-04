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
		calcAngle();
	}
	public void preDt() {
		vy += 5f / (Settings.valueInt("fps")); // gravity
		calcAngle();
//		angle++;
		System.out.println((vx > 0) + "," + (vy > 0) + "->"+angle);
	}
	public void postDt() { // go out of bounds -> remove
		if (px + sx < container.getMinX() || px > container.getMaxX()
				|| py < container.getMinY() || py - 2*sy > container.getMaxY()) {
			die();
		}
	}
	private void calcAngle() {
		double radAngle = -Math.atan2(vy, vx) + Math.PI;
		angle = (float) Math.toDegrees(radAngle);
	}
}