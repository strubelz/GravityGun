package de.strubel.gravitygun;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class PlayerItemHeldListener implements Listener {
	
	@EventHandler
	public void onPlayerItemHeld(PlayerItemHeldEvent e) {
		
		if (GravityGunMain.map.containsKey(e.getPlayer().getName())) {
			
			e.setCancelled(true);
			
		}
		
	}
	
}
