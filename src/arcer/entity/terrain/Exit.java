package arcer.entity.terrain;

import java.util.List;

import arcer.core.Zone;
import arcer.entity.Entity;
import arcer.entity.player.Knight;

public class Exit extends Entity {
	public Exit(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		terrainCollidable = false;
		tiledHorizontally = false;
	}

	public void preDt(){
		List<Entity> entities = container.getTerrainCollided(this);
		for(Entity e : entities){
			if(e instanceof Knight){
				container.nextLevel();
			}
		}
	}
}
