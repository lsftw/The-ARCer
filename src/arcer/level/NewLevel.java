package arcer.level;

import org.newdawn.slick.Image;

import arcer.core.NewZone;
import arcer.resource.Art;
import arcer.resource.Song;

// TODO some way to finalize a level to prevent changes. Don't call it finalize()
// TODO have level bounds?
// Individual level objects
public class NewLevel {
	protected String levelName;
	protected int spawnX, spawnY; // player spawn point
	protected String originalLevelText; // what was used to load the level
	protected char[][] tiles; // block by block, original, without 2-height entities merged
	protected Image background;
	protected String music;

	protected NewLevel() { }

	/**
	 * Loads this level's information into a level container.<br>
	 * Used for changing levels.
	 */
	public void loadInto(NewZone zone) {
		// TODO add and follow knight player
		//Knight p = new Knight(zone, spawnX, spawnY);
		//zone.addEntity(p);
		//zone.follow(p);
		zone.setBackground(background);
		Song.playMusic(music);
		zone.loadTiles(tiles);
		// TODO load entities
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
