package arcer.entity;

import arcer.core.Zone;
public abstract class Unit extends Entity {
	public static final int BASE_COLLISION_DAMAGE = 25;
	protected int health = 0;
	protected int collisionDamage = BASE_COLLISION_DAMAGE;

	public Unit(Zone zone, float x, float y) {
		super(zone, x, y);
		health = getBaseHealth();
	}
	/**
	 * Add this to every entity, used for checking base maximum health and resetting levels.
	 */
	public abstract int getBaseHealth();

	public void dt() {
		if (isDead()) {
			die();
		} else {
			super.dt();
		}
	}

	/**
	 * Takes damage inflicted by an attacker and possibly gives score to attacker.<br>
	 * Projectiles should declare their owner as the attacker.
	 */
	public void hitBy(Entity attacker, int damage) {
		health -= damage;
	}

	public int getHealth() { return health; }
	public void setHealth(int newHealth) { health = newHealth; }
	public boolean isDead(){ return health <= 0; }
}
