package de.strubel.gravitygun;

import java.util.UUID;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.World;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.strubel.gravitygun.attributes.AttributeStorage;
import de.strubel.gravitygun.attributes.NbtFactory;

public class PlayerInteractListener implements Listener {
	
	public static final UUID ID = UUID.fromString("5f473430-3275-4951-abc9-d39f4be8ce26");
	
	private GravityGunMain plugin;
	
	public PlayerInteractListener(GravityGunMain g) {
		this.plugin = g;
	}
	
	@SuppressWarnings({ "unused", "deprecation" })
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		
		if (e.getAction() == Action.PHYSICAL) {
			return;
		}
		
		Player p = e.getPlayer();
		
		if (e.getItem() == null) {
			return;
		}
		
		AttributeStorage store = AttributeStorage.newTarget(NbtFactory.getCraftItemStack(e.getItem()), ID);
		
		if (store.getData(null) == null) {
			return;
		}
		
		if (!(store.getData(null).startsWith("gravitygun;"))) {
			return;
		}
		
		String[] split = store.getData(null).split(";");
		
		if ((!split[0].equals("gravitygun"))) {
			return;
		}
			
		for (String s : GravityGunMain.reg.keySet()) {
			
			if (s.equals(split[1])) {
				GravityGun g = GravityGunMain.reg.get(s);
				
				if (!(p.hasPermission("gravitygun.use." + s))) {
					
					if (!(GravityGunMain.map.containsKey(p.getName())) && e.getClickedBlock() == null) {
						e.setCancelled(true);
						return;
					}
					
					p.sendMessage("�4You don't have permission to use this GravityGun!");
					e.setCancelled(true);
					return;
				}
				
				for (Entity ent : p.getNearbyEntities(10, 10, 10)) {
					
					if (ent instanceof LivingEntity) {
						
						if (((LivingEntity)ent).getCustomName() != null) {
						
							if (((LivingEntity)ent).getCustomName().equals("invulnerable")) {
							
								ent.remove();
							}
						
						}
					}
					
				}
				
				if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
					
					if (g.isCanthrow()) {
						
						if (!(GravityGunMain.map.containsKey(p.getName()))) {
							return;
						}
						
						Vector v = GravityGunMain.map.get(p.getName()).getLocation().toVector().subtract(p.getLocation().toVector());
						
						Entity ent = GravityGunMain.map.get(p.getName()).getPassenger();
						
						GravityGunMain.map.get(p.getName()).remove();
						
						GravityGunMain.map.remove(p.getName());
						
						if (e == null) {
							e.setCancelled(true);
							
							return;
						}
						
						try {
							
							if (ent instanceof FallingBlock) {
								
								FallingBlock fb = (FallingBlock) ent;
								
								if (fb.getMaterial().equals(Material.TNT)) {
									
									TNTPrimed tnt = (TNTPrimed) p.getWorld().spawnEntity(fb.getLocation(), EntityType.PRIMED_TNT);
									
									tnt.setVelocity(v.multiply(plugin.getConfig().getDouble("Vector")));
									
									fb.remove();
									
									p.getWorld().playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound").toUpperCase()), plugin.getConfig().getInt("Sound-volume"), plugin.getConfig().getInt("Sound-pitch"));
								}else {
									
									ent.setVelocity(v.multiply(plugin.getConfig().getDouble("Vector")));
									p.getWorld().playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound").toUpperCase()), plugin.getConfig().getInt("Sound-volume"), plugin.getConfig().getInt("Sound-pitch"));
									
								}
								
							}else {
						
						ent.setVelocity(v.multiply(0.2));
						p.getWorld().playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound").toUpperCase()), plugin.getConfig().getInt("Sound-volume"), plugin.getConfig().getInt("Sound-pitch"));
						
							}
						
						} catch (Exception exc) {
							GravityGunMain.log.warning("An Exception occured while moving a Entity!");
							GravityGunMain.log.warning("This is a bug I can't fix, so I wrote this lines :)");
							GravityGunMain.map.remove(p.getName());
						}
						
						e.setCancelled(true);
						
						return;
						
					}
					
				}
				
				if (GravityGunMain.map.containsKey(p.getName())) {
					
					Entity ent = GravityGunMain.map.get(p.getName());
					
					if (ent.getPassenger() instanceof FallingBlock) {
						
						FallingBlock fb = (FallingBlock) ent.getPassenger();
					
					org.bukkit.block.Block b = ent.getWorld().getBlockAt(ent.getLocation());
					
					b.setType(fb.getMaterial());
					b.setData(fb.getBlockData());
					
					GravityGunMain.map.get(p.getName()).remove();
					GravityGunMain.map.remove(p.getName());
					fb.remove();
					
					e.setCancelled(true);
					p.getWorld().playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound").toUpperCase()), plugin.getConfig().getInt("Sound-volume"), plugin.getConfig().getInt("Sound-pitch"));
					return;
					}
					
					p.getWorld().playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound").toUpperCase()), plugin.getConfig().getInt("Sound-volume"), plugin.getConfig().getInt("Sound-pitch"));
					
					GravityGunMain.map.get(p.getName()).remove();
					GravityGunMain.map.remove(p.getName());
					
					e.setCancelled(true);
					return;
					
				}
				
				if (e.getClickedBlock() == null) {
					return;
				}
				if (!(g.getBlockpickupexeptions().contains(e.getClickedBlock().getType().toString())) || ((g.getBlockpickupexeptions().contains("ALL")))) {
					if (g.getBlockpickup().contains(e.getClickedBlock().getType().toString()) || g.getBlockpickup().contains("ALL")) {
						World mcWorld = ((CraftWorld)e.getPlayer().getWorld()).getHandle();
						
						NewBat bat = new NewBat(mcWorld);
						Bat bukkitbat = (Bat) bat.getBukkitEntity();
						bat.setPosition(e.getClickedBlock().getLocation().getX(), e.getClickedBlock().getLocation().getY(), e.getClickedBlock().getLocation().getZ());
						
					    Block b = CraftMagicNumbers.getBlock(e.getClickedBlock().getType());
						e.getClickedBlock().setType(Material.AIR);
						BetterFallingBlock falling = new BetterFallingBlock(mcWorld, e.getClickedBlock().getLocation().getX(), e.getClickedBlock().getLocation().getY() + 1, e.getClickedBlock().getLocation().getZ(), b, e.getClickedBlock().getData());
						
						FallingBlock bukkitfalling = (FallingBlock) falling.getBukkitEntity();
						
						bukkitbat.setPassenger(bukkitfalling);
						bukkitbat.setCustomNameVisible(false);
						
						mcWorld.addEntity(bat);
						mcWorld.addEntity(falling);
						
						PotionEffect pot = new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 1);
						
						bukkitbat.addPotionEffect(pot);
						
						GravityGunMain.map.put(p.getName(), bukkitbat);
						
						p.getWorld().playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound").toUpperCase()), plugin.getConfig().getInt("Sound-volume"), plugin.getConfig().getInt("Sound-pitch"));
						
						e.setCancelled(true);
						
						return;
					}
					
				}
				
			}
			
		}
		
	}
	
}