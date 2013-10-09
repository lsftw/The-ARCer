package arcer.level;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;

import arcer.core.Zone;
import arcer.entity.player.Knight;
import arcer.resource.Art;
import arcer.resource.Song;

// TODO some way to finalize a level to prevent changes. Don't call it finalize()
// TODO have level bounds?
// Individual level objects
public class Level {
	protected String levelName;
	protected int spawnX, spawnY; // player spawn point
	protected List<EntityEvent> entityEvents = new ArrayList<EntityEvent>();
	protected Image background;
	protected String music;

	protected Level() { }

	/**
	 * Adds an entity event, usually used for adding an entity to the level.
	 */
	public void addEntityEvent(String entityName, int px, int py) {
		addEntityEvent(entityName, -1,-1, px,py);
	}
	/**
	 * Adds an entity event, usually used for adding an entity to the level.
	 */
	public void addEntityEvent(String entityName, int sx, int sy, int px, int py) {
		entityEvents.add(new EntityEvent(entityName, sx, sy, px, py));
	}

	/**
	 * Loads this level's information into a level container.<br>
	 * Used for changing levels.
	 */
	public void loadInto(Zone zone) {
		Knight p = new Knight(zone, spawnX, spawnY);
		zone.addEntity(p);
		zone.follow(p);
		zone.setBackground(background);
		Song.playMusic(music);
		for (EntityEvent ee : entityEvents) {
			ee.trigger(zone);
		}
	}

	public void setName(String name) { levelName = name; }
	public void setBackground(String bg) { this.background = Art.getImage(bg); }
	public void setMusic(String song) { music = song; }
	public void setSpawnPoint(int spawnX, int spawnY) {
		this.spawnX = spawnX;
		this.spawnY = spawnY;
	}

	public String getName() { return levelName; }
}
