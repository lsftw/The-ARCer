package arcer.core;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import arcer.entity.Entity;
import arcer.entity.player.Player;
import arcer.gui.Drawable;
import arcer.level.LevelHandler;
import arcer.resource.Settings;
import arcer.resource.Song;

public class NewZone implements Drawable {
	// Lists of Entities
	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Entity> entitiesToAdd = new ArrayList<Entity>();
	protected List<Entity> entitiesToRemove = new ArrayList<Entity>();
	protected char[][] terrainTiles; // TODO load tiles
	protected Player player;
	protected LevelHandler levelHandler;
	// Scrolling
	protected Entity followed;
	protected int xScroll = 0, yScroll = 0;
	protected int xScrollTarget = 0, yScrollTarget = 0; // further = faster scroll
	// Multimedia Experience
	protected Image background;

	public NewZone(LevelHandler levelHandler) {
		this.levelHandler = levelHandler;
	}
	public void nextLevel() {
		levelHandler.nextLevel();
	}
	public void loadTiles(char[][] levelTiles) {
		// TODO merge entities and place appropriately
		this.terrainTiles = levelTiles;
	}

	public void addEntity(Entity entity) {
		entitiesToAdd.add(entity);
	}
	public void removeEntity(Entity entity) {
		entitiesToRemove.add(entity);
	}
	public boolean hasEntity(Entity entity) {
		return entities.contains(entity);
	}

	public void draw(Graphics g) {
		updateScrolling();
		if (background != null) background.draw(0, 0);
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g);
		}
	}
	public void dt() {
		updateEntities();

		Entity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			entity.dt();
		}

		updateEntities();
		// scrolling velocity
		float xScrollDelta = (xScrollTarget - xScroll) * .1f;
		float yScrollDelta = (yScrollTarget - yScroll) * .1f;
		xScroll += xScrollDelta;
		yScroll += yScrollDelta;
	}

	private void updateEntities() {
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();
		entities.removeAll(entitiesToRemove);
		entitiesToRemove.clear();
	}

	public void clear() {
		Song.stopMusic();
		entitiesToAdd.clear();
		entitiesToRemove.addAll(entities);
	}
	// Scrolling
	public void follow(Entity entity) {
		followed = entity;
	}
	private void updateScrolling() {
		if (followed != null) {
			float xPos = followed.getXpos();
			if (!followed.isFacingRight()) {
				xPos += followed.getXsize();
			} else {
				xPos += followed.getXsize();
			}
			xScrollTarget = (int)(xPos - Settings.valueInt("windowWidth")/2);
		}
	}
	public int getXscroll() { return xScroll; }
	public int getYscroll() { return yScroll; }

	// Multimedia
	public void setBackground(Image bg){ this.background = bg; }
}
