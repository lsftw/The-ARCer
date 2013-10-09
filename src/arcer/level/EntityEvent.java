package arcer.level;

import arcer.core.Zone;
import arcer.enemy.Snake;
import arcer.entity.Entity;
import arcer.entity.terrain.Exit;
import arcer.entity.terrain.Platform;
import arcer.resource.Utility;
/**
 * This class spawns entities that you add. In order to add an entity you must:
 * 	- Add the entity as an enum value in EntityType
 * 	- Add the path to the entity in data/spritelist.txt
 * 	- Create the class for the Entity (with the same name as enum) in com.bbr.entity.*
 * 	- Add a block in the case statement in trigger spawning it into zone.
 */
public class EntityEvent {
	public enum EntityType { PLATFORM, EXIT, SNAKE, BACKGROUND }
	protected EntityType entityType;
	protected int sx, sy;
	protected int px, py;

	protected EntityEvent(String entityName, int sx, int sy, int px, int py) {
		this.entityType = EntityType.valueOf(entityName.toUpperCase());
		this.sx = sx;
		this.sy = sy;
		this.px = px;
		this.py = py;
	}

	public void trigger(Zone zone) {
		Entity e = null;
		switch (entityType) {
		case PLATFORM:
			e = new Platform(zone, px, py);
			zone.addEntity(e);
			break;
		case EXIT:
			e = new Exit(zone, px, py);
			zone.addEntity(e);
			break;
		case SNAKE:
			e = new Snake(zone, px, py);
			zone.addEntity(e);
			break;
		default:
			Utility.printWarning("Ignoring EntityEvent: " + entityType);
			break;
		}
		if (e != null) {
			if (sx > 0) e.setXsize(sx);
			if (sy > 0) e.setYsize(sy);
		}
	}
}
