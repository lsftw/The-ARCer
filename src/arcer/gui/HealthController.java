package arcer.gui;

import org.newdawn.slick.Image;

import arcer.entity.Unit;
import arcer.resource.Art;

public class HealthController{
	private static final int HEALTH_PER_HEART = 100;
	private static final Image positiveUnitImage = Art.getImage("PositiveHealth");
	private static final Image negativeUnitImage = Art.getImage("NegativeHealth");
	private Unit unit;
	private int numTotalUnits = 0;

	public HealthController(Unit unit) {
		this.unit = unit;
		numTotalUnits = unit.getBaseHealth() / HEALTH_PER_HEART;

	}
	public void changeUnit(Unit unit){
		this.unit = unit;
	}
	public void draw(){
		int numHearts = 0;
		if (unit.getHealth() > 0) {
			numHearts = unit.getHealth() / HEALTH_PER_HEART;
		}
		for (int i=0; i<numTotalUnits; i++){
			if (i < numHearts) {
				positiveUnitImage.draw(i * positiveUnitImage.getWidth(), 0);
			} else{
				negativeUnitImage.draw(i* negativeUnitImage.getWidth(), 0);
			}

		}
	}
}
