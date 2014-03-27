package de.strubel.gravitygun;

import org.bukkit.SkullType;
import org.bukkit.block.BlockFace;

public class SkullData {
	
	private String name;
	private SkullType skulltype;
	private BlockFace rotation;
	
	public SkullData(String name, SkullType skulltype, BlockFace rotation) {
		
		this.setName(name);
		this.setSkulltype(skulltype);
		this.setRotation(rotation);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SkullType getSkulltype() {
		return skulltype;
	}

	public void setSkulltype(SkullType skulltype) {
		this.skulltype = skulltype;
	}

	public BlockFace getRotation() {
		return rotation;
	}

	public void setRotation(BlockFace rotation) {
		this.rotation = rotation;
	}
	
}
