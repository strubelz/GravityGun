package de.strubel.gravitygun;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MoveBlockRunnable implements Runnable {
	
	private GravityGunMain plugin;
	
	public MoveBlockRunnable(GravityGunMain g) {
		this.plugin = g;
	}

	@Override
	public void run() {
		
		for (String s : GravityGunMain.map.keySet()) {
			
			Player p = Bukkit.getPlayerExact(s);
			
			Vector vec = GravityGunMain.getTargetLocation(p, plugin.getConfig().getInt("Distance")).toVector().subtract(GravityGunMain.map.get(s).getLocation().toVector());
			
			try {
				
			GravityGunMain.map.get(s).setVelocity(vec);
			
			} catch (Exception exc) {
				GravityGunMain.log.warning("An Exception occured while moving an Entity!");
				GravityGunMain.log.warning("This is a bug I can't fix, so I wrote this lines :)");
				GravityGunMain.map.remove(p.getName());
			}
			
		}
		
	}

}
