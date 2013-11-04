package arcer.entity;

import arcer.core.Zone;
public abstract class Unit extends Entity {
	public enum Team { NEUTRAL, PLAYER, ENEMY }
	protected Team allegiance = Team.PLAYER;
	protected int health = 0;
	protected boolean canDie = true; // should die if health <= 0

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

	public Team getAllegiance() { return allegiance; }
	public void setAllegiance(Team allegiance) { this.allegiance = allegiance; }

	public int getHealth() { return health; }
	public void setHealth(int newHealth) { health = newHealth; }
	public boolean isDead() { return canDie && health <= 0; }
}
