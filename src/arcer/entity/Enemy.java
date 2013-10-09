package arcer.entity;

import org.newdawn.slick.Graphics;

import arcer.core.Zone;

public abstract class Enemy extends Unit {
	public Enemy(Zone zone, float x, float y) {
		super(zone, x, y);
	}

	public void draw(Graphics g) {
		super.draw(g);
	}
}
