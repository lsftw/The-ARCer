package arcer.entity.player;

import arcer.entity.Entity;

public class Shield extends Entity {
	protected Entity followed;
	public Shield(Entity followed) {
		super(followed.getZone(), followed.getXpos(), followed.getYpos());
		this.followed = followed;
	}
	public void preDt() {
		setXpos(followed.getXpos());
		setYpos(followed.getYpos());
	}
}
