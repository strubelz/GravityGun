package de.strubel.gravitygun;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		
		GravityGunMain.map.remove(e.getPlayer().getName());
		
	}
	
}
