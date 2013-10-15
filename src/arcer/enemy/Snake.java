package arcer.enemy;

import org.newdawn.slick.Image;

import arcer.core.Zone;
import arcer.entity.Unit;

public class Snake extends Unit {
	public int getBaseHealth() { return 75; }

	protected float startX;
	protected int hitDelay;

	protected float lastX, lastY;

	public Snake(Zone zone, float x, float y) {
		super(zone, x, y);
		flipHorizontal = true;
		vx = -1;
		terrainCollidable = true;
		startX = x;
		allegiance = Team.ENEMY;
	}

	public Image getFrameToDraw() {
		if (Math.abs(vx) > 0.01) {
//			Utility.log("Snake move");
			return sprite.getFrame("snakeMove");
		}
//		Utility.log("Snake normal");
		return super.getFrameToDraw();
	}

	public void preDt() {
		if(vx < 0 && container.collidesWithLeftOf(this) != null) {
			vx = -vx;
		}
		else if(vx > 0 && container.collidesWithRightOf(this) != null) {
		//if(Math.abs(px - startX) > 300){
		//if (Math.abs(lastX - px) < 0.1 && Math.abs(lastY - py) < 0.1) {
			vx = -vx;
		}

		if (container.getPlayer().collidesWith(this) && hitDelay <=0) {
			container.getPlayer().hitBy(this, 100);
			hitDelay = 25;
		}
		if (hitDelay > 0)
			hitDelay--;

		lastX = px;
		lastY = py;
	}
}
