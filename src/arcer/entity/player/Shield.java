package arcer.entity.player;

import arcer.entity.Entity;
import arcer.entity.Unit;
import arcer.resource.Settings;

// TODO fix rotated hitbox for this and arrow
public class Shield extends Unit {
	// Window border + title bar offset
	protected static final int WINDOW_Y_OFFSET = 59;
	protected static final int FOLLOWED_X_DISTANCE = 30; // constant distance in front of player
	protected Entity followed;
	protected int mouseX, mouseY;

	public int getBaseHealth() { return 0; }

	public Shield(Entity followed) {
		super(followed.getZone(), followed.getXpos(), followed.getYpos());
		this.followed = followed;
		terrainCollidable = false;
		canDie = false;
		allegiance = Team.PLAYER;
	}
	public void preDt() {
		if (!Settings.valueBoolean("freeShield")) {
			float dx = mouseX - container.getAdjustedX(followed.getXcenter());
			float dy = mouseY - container.getAdjustedY(followed.getYcenter());
			double shieldAngle = Math.atan2(dy, dx);
			if (!followed.isFacingRight()) {
				shieldAngle = shieldAngle + Math.PI;
			}
			setAngle((float)Math.toDegrees(shieldAngle));

			
			double newX = followed.getXcenter();
			if (followed.isFacingRight()) {
				newX += Math.cos(shieldAngle)*(FOLLOWED_X_DISTANCE + followed.getXsize())/2;
			} else {
				newX += Math.cos(shieldAngle)*(-FOLLOWED_X_DISTANCE)/2;
				newX -= sx;
			}
			setXpos((float)newX);
			setYpos((float) (followed.getYpos() + Math.sin(shieldAngle)*2*FOLLOWED_X_DISTANCE));
		} else {
			setXpos(mouseX);
			setYpos(mouseY);
		}
	}

	public void updatePosition(int mouseX, int mouseY) {
		this.mouseX = mouseX - sx / 2;
		this.mouseY = mouseY - Settings.valueInt("windowHeight") + WINDOW_Y_OFFSET - sy / 2;
	}
	// TODO override, or maybe not if you change the hitbox
	public float getXrotCenter() { return getXcenter(); }
	public float getYrotCenter() { return getYcenter(); }
}
