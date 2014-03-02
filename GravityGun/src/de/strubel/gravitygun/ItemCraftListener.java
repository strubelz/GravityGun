package de.strubel.gravitygun;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class ItemCraftListener implements Listener {
	
	@EventHandler
	public void onItemCraft(final CraftItemEvent e) {
		
		for (String s : GravityGunMain.reg.keySet()) {
			
			if (e.getRecipe().getResult().equals(GravityGunMain.reg.get(s).getItemStack())) {
				
				for (HumanEntity p : e.getViewers()) {
					
					if (!(p.hasPermission("gravitygun.craft." + s))) {
						e.setCancelled(true);
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("GravityGun"), new Runnable() {

							@Override
							public void run() {
								((Player)e.getWhoClicked()).closeInventory();
								((Player)e.getWhoClicked()).sendMessage("§4You don't have permission to craft this GravityGun!");
							}
							
						}, 5);
					}
					
				}
				
			}
			
		}
		
	}
		
	}
