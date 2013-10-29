package arcer.entity;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import arcer.core.Sprite;
import arcer.core.Zone;
import arcer.resource.Art;
import arcer.resource.Settings;
import arcer.resource.Tuple;
import arcer.resource.Utility;

// represents a game entity with position and image
public abstract class Entity {
	public static final int TERMINAL_VELOCITY = 10; // Max due to gravity
	protected static final Random rand = new Random(System.currentTimeMillis());
	// Spatial
	protected Zone container;
	protected int sx, sy; // use setters to change, hitbox derives from this
	protected float px, py; // use setters to change, hitbox derives from this
	protected float vx=0, vy=0;
	protected float angle=0;
	protected Rectangle2D.Float hitbox = new Rectangle2D.Float(); // derived from px,py sx,sy
	protected boolean terrainCollidable = true;
	// Graphical
	protected Sprite sprite;
	protected boolean flipHorizontal = false; // facing right by default
	protected boolean tiledHorizontally = false, tiledVertically = false;

	public Entity(Zone container, float xpos, float ypos) {
		this.container = container;
		setXpos(xpos);
		setYpos(ypos);
		loadSprite();
		autoResize();
	}

	private void loadSprite() {
		sprite = Art.getSprite(this);
	}
	protected void autoResize() { // resize to match image size
		if (sprite == null) return;
		autoResize(sprite.getFrame());
	}
	protected void autoResize(Image frame) { // resize to match image size
		setXsize(frame.getWidth());
		setYsize(frame.getHeight());
	}

	protected Image getFrameToDraw() { // override to draw different frames
		if (sprite == null) return null;
		return sprite.getFrame();
	}
	/**
	 * Draws this image, doing tiling, flipping and either cropping if tiled or scaling if not.
	 */
	public void draw(Graphics g) {
		Image toDraw = getFrameToDraw();
		if (toDraw == null) {
			Utility.printError("No frame to draw for " + this + "! Skipping.");
			return;
		}
		g.rotate(px + sx/2, py + sy/2, angle);
		int imageWidth = toDraw.getWidth();
		int imageHeight = toDraw.getHeight();
		if (tiledHorizontally || tiledVertically) {
			imageWidth = imageWidth > sx ? sx : imageWidth;
			imageHeight = imageHeight > sy ? sy : imageHeight;
			//goes through and draws all ground sprites until it reaches the necessary width
			int cropWidth, cropHeight; // for cropping
			int drawWidth, drawHeight; // for cropping
			for (int i = 0; i < sx/imageWidth + (sx%imageWidth==0 ? 0 : 1); i++) {
				for (int j = 0; j < sy/imageHeight + (sy%imageHeight==0 ? 0 : 1); j++) {
					cropWidth = imageWidth; drawWidth = imageWidth;
					cropHeight = imageHeight; drawHeight = imageHeight;
					if (i == sx/imageWidth && sx%imageWidth != 0) {
						drawWidth = sx%imageWidth; // horizontal leftover
						if (tiledHorizontally) {
							cropWidth = drawWidth;
						}
					}
					if (j == sy/imageHeight && sy%imageHeight != 0) {
						drawHeight = sy%imageHeight; // vertical leftover
						if (tiledVertically) {
							cropHeight = drawHeight;
						}
					}

					toDraw.draw(px+imageWidth*(i)-this.container.getXscroll(),
							py+imageHeight*(j)-this.container.getYscroll(),
							px+imageWidth*(i)-this.container.getXscroll()+drawWidth,
							py+imageHeight*(j)-this.container.getYscroll()+drawHeight,
							0, 0, cropWidth, cropHeight);
				}
			}
		} else { // sprite flipping and offsets added for non-tiling
			float offsetX = 0, offsetY = 0;
			int drawSizeX = (flipHorizontal?-sx:sx);
			int drawSizeY = sy;

			Tuple<Integer, Integer> offsets = sprite.getOffsets(toDraw);
			if (offsets != null) {
				offsetX = offsets.left;
				offsetY = offsets.right;
				drawSizeX = (flipHorizontal?-imageWidth:imageWidth);
				drawSizeY = imageHeight;
			}
			float drawStartX = px-this.container.getXscroll()+(flipHorizontal?sx:0)+offsetX;
			float drawStartY = py-this.container.getYscroll()+offsetY;

			toDraw.draw(drawStartX, drawStartY, drawSizeX, drawSizeY);
		}
		if (Settings.valueBoolean("showHitbox")) { // shows entity hitbox, not frame drawbox
			g.setColor(Color.red);
			g.drawRect(px-this.container.getXscroll(), py-this.container.getYscroll(), sx, sy);
		}
		g.rotate(px + sx/2, py + sy/2, -angle);
	}
	/**
	 * Position & Velocity changing code goes here, so that dt() does appropriate collision checking afterwards.
	 */
	protected void preDt() { }
	/**
	 * Advances one frame in the game logic, updating position based on velocity and collision.
	 */
	public void dt() {
		preDt();
		// Facing
		if (vx < 0) {
			flipHorizontal = true;
		} else if (vx > 0) {
			flipHorizontal = false;
		}
		// Velocity and Acceleration
		float newx = px + vx;
		if (newx < container.getMinX()) {
			newx = container.getMinX();
		} else if (newx + sx > container.getMaxX()) {
			newx = container.getMaxX() - sx;
		}
		setXpos(newx);
		// negative y is upwards, positive y is downwards
		float newy = py + vy;
		if (newy - sy < container.getMinY()) {
//			Utility.log("miny" + newy);
			newy = container.getMinY() + sy;
		} else if (newy > container.getMaxY()) {
//			Utility.log("maxy" + newy);
			newy = container.getMaxY();
		}
		setYpos(newy);
		if (terrainCollidable && vy < TERMINAL_VELOCITY && !isOnPlatform()){ // Gravity
			vy++;
		}
		// Collision Detection
		if(container.collidesWithRightOf(this) != null){
//			Utility.log("Collider Right: "+container.collidesWithRightOf(this).getXpos()+" Player: "+(this.getXpos()+this.getXsize()));
			this.setXpos(container.collidesWithRightOf(this).getXpos() - this.getXsize());
		}
		if(container.collidesWithLeftOf(this) != null){
//			Utility.log("Collider Left: "+(container.collidesWithLeftOf(this).getXpos() + container.collidesWithLeftOf(this).getXsize())+" Player: "+this.getXpos());
			this.setXpos(container.collidesWithLeftOf(this).getXpos() + container.collidesWithLeftOf(this).getXsize());
		}
		// prevent falling through platforms
		if (container.collidesWithBottomOf(this) != null){
			vy = 0;
//			Utility.log("Collider Bottom: "+container.collidesWithBottomOf(this).getYpos() +" Player: "+this.getYpos()+this.getYsize());
			this.setYpos(container.collidesWithBottomOf(this).getYpos() - this.getYsize());
		}
		if (container.collidesWithTopOf(this) != null){
			vy = 0;
//			Utility.log("Collider Top: "+container.collidesWithTopOf(this).getYpos() + container.collidesWithTopOf(this).getYsize()+" Player: "+this.getYpos());
			this.setYpos(container.collidesWithTopOf(this).getYpos() + container.collidesWithTopOf(this).getYsize());
		}

		postDt();
	}
	/**
	 * Projectile firing and other non-position/velocity changing code goes here.
	 */
	protected void postDt() { }

	/**
	 * Gets the level container for this entity to remove it.
	 */
	public void die() {
		container.removeEntity(this);
	}

	// Rectangle collision check
	public boolean collidesWith(Entity other) {
		return hitbox.intersects(other.hitbox);
	}
	public boolean futureCollidesWith(Entity other, float xmod, float ymod) {
		Rectangle2D.Float temp = (Float) other.hitbox.clone();
		temp.setFrame(temp.getX() + xmod, temp.getY()+ymod, temp.getWidth(), temp.getHeight());
		return temp.intersects(this.hitbox);
	}

	public void hitBy(Entity attacker, int damage) { }

	// Java Boilerplate
	public Zone getZone() { return container; }
	public int getXsize() { return sx; }
	public int getYsize() { return sy; }
	public float getXpos() { return px; }
	public float getYpos() { return py; }
	public float getXvel() { return vx; }
	public float getYvel() { return vy; }
	public boolean isTerrainCollidable() { return terrainCollidable; }
	public boolean isOnPlatform() { return (container.collidesWithBottomOf(this) != null) || Math.abs(py - container.getMaxY()) < .01; }
	public boolean isFacingRight() { return !flipHorizontal; }
	// Java Boilerplate
	public void setZone(Zone zone) { container = zone; }
	public void setXsize(int newxsize) { sx = newxsize; hitbox.width = sx; }
	public void setYsize(int newysize) { sy = newysize; hitbox.height = sy; }
	public void setXpos(float newxpos) { px = newxpos; hitbox.x = px; }
	public void setYpos(float newypos) { py = newypos; hitbox.y = py; }
	public void setXvel(float newxvel) { vx = newxvel; }
	public void setYvel(float newyvel) { vy = newyvel; }
	public void setTerrainCollidable(boolean collidability) { terrainCollidable = collidability; }

	public String toString() {
		return this.getClass().getSimpleName();
	}
}
