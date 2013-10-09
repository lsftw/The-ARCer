package arcer.entity.projectile;

import arcer.entity.Entity;
import arcer.entity.Unit;

public abstract class Projectile extends Entity {
	protected Entity owner;
	// Targetting (used in collision checking)
	protected enum TargetType {ALL, ENEMY, PLAYER}
	protected TargetType targetting = TargetType.ALL;
	// Attributes
	protected int duration = -1; // -1 = never decays

	protected int damage;

	public Projectile(Entity owner, float xpos, float ypos) {
		super(owner.getZone(), xpos, ypos);
		this.owner = owner;
	}

	public void dt() {
		if (duration == 0) {
			container.removeEntity(this);
			return;
		} else if (duration > 0) {
			duration--;
		}
		preDt();
		setXpos(px + vx);
		setYpos(py + vy);
		checkCollision();
		postDt();
	}
	// Collision
	protected Entity checkCollision() {
		Entity collided = getCollided();
		if (collided != null) hit(collided);
		return collided;
	}
	protected Entity getCollided() {
		Entity collided = null;
		switch (targetting) {
		case ALL:
			collided = container.getUnitCollided(this);
			break;
		case ENEMY:
			collided = container.getEnemyCollided(this);
			break;
		case PLAYER:
			collided = container.getPlayerCollided(this);
			break;
		}
		return collided;
	}
	protected void hit(Entity collided) {
		if (collided instanceof Unit) {
			collided.hitBy(this.owner, damage);
			container.removeEntity(this);
		}
	}
	public void boostDmg(int boost) {
		damage += boost;
	}
	public void multDmg(double boost) {
		damage = (int)(damage * boost);
	}
}
