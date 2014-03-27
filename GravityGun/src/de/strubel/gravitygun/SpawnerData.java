package de.strubel.gravitygun;

import org.bukkit.entity.EntityType;

public class SpawnerData {
	
	private int delay;
	private EntityType entitytype;
	
	public SpawnerData(int delay, EntityType entitytype) {
		
		this.setDelay(delay);
		this.setEntitytype(entitytype);
		
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public EntityType getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(EntityType entitytype) {
		this.entitytype = entitytype;
	}
	
}
