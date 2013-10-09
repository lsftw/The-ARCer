package arcer.core;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import arcer.entity.Enemy;
import arcer.entity.Entity;
import arcer.entity.Unit;
import arcer.entity.player.Player;
import arcer.entity.terrain.Platform;
import arcer.gui.Drawable;
import arcer.level.LevelHandler;
import arcer.resource.Settings;
import arcer.resource.Song;

public class Zone implements Drawable {
	// Lists of Fliers
	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Entity> entitiesToAdd = new ArrayList<Entity>();
	protected List<Entity> entitiesToRemove = new ArrayList<Entity>();
	protected Player player;
	protected LevelHandler levelHandler;
	// Scrolling
	protected Entity followed;
	protected int xScroll = 0, yScroll = 0;
	protected int xScrollTarget = 0, yScrollTarget = 0; // further = faster scroll
	// Multimedia Experience
	protected Image background;

	public Zone(LevelHandler levelHandler) {
		this.levelHandler = levelHandler;
	}
	public void nextLevel() {
		levelHandler.nextLevel();
	}

	public void addEntity(Entity entity) {
		entitiesToAdd.add(entity);
		if (entity instanceof Player) player = (Player)entity;
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

		Entity flyer;
		for (int i = 0; i < entities.size(); i++) {
			flyer = entities.get(i);
			flyer.dt();
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
	// collision detection
	public Platform getPlatformBelow(Entity mover) {
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != mover) {
				if (collided instanceof Platform && collided.collidesWith(mover)) {
					return (Platform)collided;
				}
			}
		}
		return null;
	}
	public Player getPlayer() {
		return player;
	}
	public List<Entity> getTerrainCollided(Entity collider) {
		List<Entity> entitiesCollided = new ArrayList<Entity>();
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != collider) {
				if (collided.isTerrainCollidable() && collided.collidesWith(collider)) {
					entitiesCollided.add(collided);
				}
			}
		}
		return entitiesCollided;
	}
	public Entity getCollided(Entity collider) {
		Entity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			if (entity != collider) {
				if (entity.collidesWith(collider)) {
					return entity;
				}
			}
		}
		return null;
	}
	public Unit getUnitCollided(Entity collider) {
		Entity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			if (entity != collider) {
				if (entity instanceof Unit && entity.collidesWith(collider)) {
					return (Unit)entity;
				}
			}
		}
		return null;
	}
	public Enemy getEnemyCollided(Entity collider) {
		Entity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			if (entity != collider) {
				if (entity instanceof Enemy && entity.collidesWith(collider)) {
					return (Enemy)entity;
				}
			}
		}
		return null;
	}
	public Player getPlayerCollided(Entity collider) {
		Entity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			if (entity != collider) {
				if (entity instanceof Player && entity.collidesWith(collider)) {
					return (Player)entity;
				}
			}
		}
		return null;
	}
	// collision detection with platforms
	public Platform collidesWithBottomOf(Entity mover) {
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != mover) {
				if (collided instanceof Platform && collided.collidesWith(mover)) {
					float moverYpos = mover.getYpos() + mover.getYsize();
					if (moverYpos > collided.getYpos() && moverYpos < collided.getYpos() + collided.getYsize()) {
						return (Platform)collided;
					}
				}
			}
		}
		return null;
	}
	public Platform collidesWithRightOf(Entity mover) {
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != mover) {
				if (collided instanceof Platform && collided.collidesWith(mover)) {
					float moverXpos = mover.getXpos() + mover.getXsize();
					if(moverXpos >= collided.getXpos() && moverXpos <= (collided.getXpos() + collided.getXsize()))
						return (Platform) collided;

				}
			}
		}
		return null;
	}
	public Platform collidesWithLeftOf(Entity mover) {
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != mover) {
				if (collided instanceof Platform && collided.collidesWith(mover)) {
					float moverXpos = mover.getXpos();
					if(moverXpos >= collided.getXpos() && moverXpos <= (collided.getXpos() + collided.getXsize()))
						return (Platform) collided;

				}
			}
		}
		return null;
	}
	public Platform collidesWithTopOf(Entity mover) {
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != mover) {
				if (collided instanceof Platform && collided.collidesWith(mover)) {
					float moverYpos = mover.getYpos();
					if (moverYpos <= collided.getYpos() + collided.getYsize() && moverYpos >= collided.getYpos()) {
						return (Platform)collided;
					}
				}
			}
		}
		return null;
	}
	// Multimedia
	public void setBackground(Image bg){ this.background = bg; }
}