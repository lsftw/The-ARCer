package arcer.entity.projectile;

import arcer.entity.Entity;
import arcer.resource.Settings;

public class Arrow extends Projectile {
	public Arrow(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		damage = 25;
		targetting = TargetType.ENEMY;
		// TODO arcs like a parabola
		vx = owner.isFacingRight() ? 5 : -5;
		vy = 10;

		duration = Settings.valueInt("fps");
		terrainCollidable = false;
	}
	public void preDt() {
		vy -= .05; // gravity
	}
}