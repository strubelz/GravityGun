package de.strubel.gravitygun;

import java.util.UUID;

import net.minecraft.server.v1_7_R1.World;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.strubel.gravitygun.attributes.AttributeStorage;
import de.strubel.gravitygun.attributes.NbtFactory;

public class PlayerInteractEntityListener implements Listener {
	
	public static final UUID ID = UUID.fromString("5f473430-3275-4951-abc9-d39f4be8ce26");
	
	private GravityGunMain plugin;
	
	public PlayerInteractEntityListener(GravityGunMain g) {
		this.plugin = g;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEntityEvent e) {
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("GravityMachine"), new Runnable() {

			@Override
			public void run() {
		
		final Player p = e.getPlayer();
		
		if (e.getPlayer().getItemInHand().getType() == Material.AIR) {
			return;
		}
		
		AttributeStorage store = AttributeStorage.newTarget(NbtFactory.getCraftItemStack(e.getPlayer().getItemInHand()), ID);
		
		if (store.getData(null) == null) {
			return;
		}
		
		if (!(store.getData(null).startsWith("gravitygun;"))) {
			return;
		}
		
		final String[] split = store.getData(null).split(";");
		
		if ((!split[0].equals("gravitygun"))) {
			return;
		}
		
		if (GravityGunMain.map.containsKey(p.getName())) {
			
			Entity ent = GravityGunMain.map.get(p.getName());
			
			if (ent.getPassenger() instanceof FallingBlock) {
				
				FallingBlock fb = (FallingBlock) ent.getPassenger();
			
			org.bukkit.block.Block b = ent.getWorld().getBlockAt(ent.getLocation());
			
			b.setType(fb.getMaterial());
			b.setData(fb.getBlockData());
			
			GravityGunMain.map.get(p.getName()).remove();
			fb.remove();
			
			e.setCancelled(true);
			p.getWorld().playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound").toUpperCase()), plugin.getConfig().getInt("Sound-volume"), plugin.getConfig().getInt("Sound-pitch"));
			return;
			}
			
			GravityGunMain.map.get(p.getName()).remove();
			
		}
		
		for (String s : GravityGunMain.reg.keySet()) {
			
			if (!(p.hasPermission("gravitygun.use." + s))) {
				
				p.sendMessage("§4You don't have permission to use this GravityGun!");
				
				e.setCancelled(true);
				return;
			}
			
			if (s.equals(split[1])) {
				GravityGun g = GravityGunMain.reg.get(s);
				
				for (Entity ent : p.getNearbyEntities(10, 10, 10)) {
					
					if (ent instanceof NewBat) {
						ent.remove();
					}
					
				}
				
				if (!(g.getEntitypickupexeptions().contains(e.getRightClicked().getType().toString())) || ((g.getEntitypickupexeptions().contains("ALL")))) {
					
					if (g.getEntitypickup().contains(e.getRightClicked().getType().toString()) || g.getEntitypickup().contains("ALL")) {
						
						World mcWorld = ((CraftWorld)e.getPlayer().getWorld()).getHandle();
						
						NewBat bat = new NewBat(mcWorld);
						Bat bukkitbat = (Bat) bat.getBukkitEntity();
						bat.setPosition(e.getRightClicked().getLocation().getX(), e.getRightClicked().getLocation().getY(), e.getRightClicked().getLocation().getZ());
						
						bukkitbat.setPassenger(e.getRightClicked());
						bukkitbat.setCustomName("invulnerable");
						bukkitbat.setCustomNameVisible(false);
						
						mcWorld.addEntity(bat);
						
						PotionEffect pot = new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 1);
						
						bukkitbat.addPotionEffect(pot);
						
						GravityGunMain.map.put(p.getName(), bukkitbat);
						
						e.setCancelled(true);
						
						p.getWorld().playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound").toUpperCase()), plugin.getConfig().getInt("Sound-volume"), plugin.getConfig().getInt("Sound-pitch"));
						
						return;
					}
					
				}
				
			}
			
		}
		
			}
			
		}, 1);
		
	}
	
}
