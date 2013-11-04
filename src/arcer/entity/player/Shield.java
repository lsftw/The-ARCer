package arcer.entity.player;

import arcer.entity.Entity;
import arcer.resource.Settings;

public class Shield extends Entity {
	// Window border + title bar offset
	protected static final int WINDOW_Y_OFFSET = 59;
	protected static final int FOLLOWED_X_DISTANCE = 30; // constant distance in front of player
	protected Entity followed;
	protected int mouseX, mouseY;
	public Shield(Entity followed) {
		super(followed.getZone(), followed.getXpos(), followed.getYpos());
		this.followed = followed;
	}
	public void preDt() {
		//setXpos(followed.getXpos() + (followed.isFacingRight() ? followed.getXsize() + X_OFFSET : -sx-X_OFFSET));
		//setYpos(followed.getYpos());
		// TODO shield should be at a constant distance
		setXpos(mouseX);
		setYpos(mouseY);
	}
	public void updatePosition(int mouseX, int mouseY) {
		this.mouseX = mouseX - sx / 2;
		this.mouseY = mouseY - Settings.valueInt("windowHeight") + WINDOW_Y_OFFSET - sy / 2;
	}
}
