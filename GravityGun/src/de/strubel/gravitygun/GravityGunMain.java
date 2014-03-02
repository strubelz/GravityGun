package de.strubel.gravitygun;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class GravityGunMain extends JavaPlugin {
	
	public static int moveid;
	public static int invisibiltyid;
	
	public static HashMap<String, Bat> map = new HashMap<>();
	
	public static final UUID ID = UUID.fromString("5f473430-3275-4951-abc9-d39f4be8ce26");
	
	static File f = new File("plugins/GravityGun/GravityGuns.yml");
	@SuppressWarnings("static-access")
	static FileConfiguration con = new YamlConfiguration().loadConfiguration(f);
	
	public static HashMap<String, GravityGun> reg = new HashMap<>();
	
	public static Logger log;
	
	@Override
	public void onEnable() {
		
		log = this.getLogger();
		
		CustomEntityType.registerEntities();
		
		if (con.getList("GravityGuns") == null) {
			reg = new HashMap<>();
			GravityGun g = new GravityGun("default");
			
			ArrayList<String> lore = new ArrayList<>();
			ArrayList<String> blockpickup = new ArrayList<>();
			ArrayList<String> blockpickupexceptions = new ArrayList<>();
			ArrayList<String> entitypickup = new ArrayList<>();
			ArrayList<String> entitypickupexception = new ArrayList<>();
			ArrayList<ItemStack> recipepart = new ArrayList<>();
			
			lore.add("§6Move Blocks and Entitys!");
			g.setLore(lore);
			
			blockpickup.add("all");
			g.setBlockpickup(blockpickup);
			
			blockpickupexceptions.add("bedrock");
			g.setBlockpickupexeptions(blockpickupexceptions);
			
			entitypickup.add("all");
			g.setEntitypickup(entitypickup);
			
			entitypickupexception.add("player");
			entitypickupexception.add("wither");
			entitypickupexception.add("enderdragon");
			g.setEntitypickupexeptions(entitypickupexception);
			
			g.setCanthrow(true);
			g.setDisplay(new ItemStack(Material.COAL));
			
			recipepart.add(new ItemStack(Material.STONE));
			recipepart.add(new ItemStack(Material.STONE));
			recipepart.add(new ItemStack(Material.STONE));
			recipepart.add(new ItemStack(Material.STONE));
			recipepart.add(new ItemStack(Material.STONE));
			recipepart.add(new ItemStack(Material.STONE));
			recipepart.add(new ItemStack(Material.STONE));
			recipepart.add(new ItemStack(Material.STONE));
			recipepart.add(new ItemStack(Material.STONE));
			
			CustomRecipe c = new CustomRecipe(recipepart);
			
			g.setRecipe(c);
			
			ArrayList<Map<String, Object>> gravl = new ArrayList<>();
			gravl.add(g.serialize());
			
			con.set("GravityGuns", gravl);
			
			try {
				con.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			reg.put(g.getName(), g);
			
		}else {
			@SuppressWarnings("unchecked")
			ArrayList<Map<String, Object>> temp = (ArrayList<Map<String, Object>>) con.getList("GravityGuns");
			
			for (Map<String, Object> map : temp) {
				
				GravityGun g = GravityGun.deserialize(map);
				g.setName(g.getName().replace(";", ":"));
				
				reg.put(GravityGun.deserialize(map).getName().replace(";", ":"), GravityGun.deserialize(map));
			}
			
			for (String s : reg.keySet()) {
				
				ItemStack i = reg.get(s).getItemStack();
				
		        ShapedRecipe recipe = new ShapedRecipe(i);
		        recipe.shape(new String[] {"ABC", "DEF", "GHI"});
		        recipe.setIngredient('A', reg.get(s).getRecipe().getRecipe().get(0).getType());
		        recipe.setIngredient('B', reg.get(s).getRecipe().getRecipe().get(1).getType());
		        recipe.setIngredient('C', reg.get(s).getRecipe().getRecipe().get(2).getType());
		        recipe.setIngredient('D', reg.get(s).getRecipe().getRecipe().get(3).getType());
		        recipe.setIngredient('E', reg.get(s).getRecipe().getRecipe().get(4).getType());
		        recipe.setIngredient('F', reg.get(s).getRecipe().getRecipe().get(5).getType());
		        recipe.setIngredient('G', reg.get(s).getRecipe().getRecipe().get(6).getType());
		        recipe.setIngredient('H', reg.get(s).getRecipe().getRecipe().get(7).getType());
		        recipe.setIngredient('I', reg.get(s).getRecipe().getRecipe().get(8).getType());
		        this.getServer().addRecipe(recipe);
				
			}
			
		}
		
		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
		this.getServer().getPluginManager().registerEvents(new ItemCraftListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerItemHeldListener(), this);
		
		this.getConfig().addDefault("Movement-update-intervall", 5);
		this.getConfig().addDefault("Sound", "note_pling");
		this.getConfig().addDefault("Sound-pitch", 1);
		this.getConfig().addDefault("Sound-volume", 1);
		this.getConfig().addDefault("Auto-ignite-tnt", true);
		this.getConfig().addDefault("Tnt-fuseticks", 50);
		this.getConfig().addDefault("Vector", 0.2);
		this.getConfig().addDefault("Distance", 5);
		
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		moveid = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new MoveBlockRunnable(this), this.getConfig().getLong("Movement-update-intervall"), 1);
	    invisibiltyid = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new InvisibilityRunnable(), 5000, 1);
		
		log.info("GravityGun Version: " + this.getDescription().getVersion() +  " by strubel (DerStrubel, strubelcopter) has been enabled!");
		
	}
	
	@Override
	public void onDisable() {
		
		this.getServer().getScheduler().cancelTask(moveid);
		this.getServer().getScheduler().cancelTask(invisibiltyid);
		
		this.getServer().resetRecipes();
		
		map.clear();
		reg.clear();
		
		log.info("GravityGun Version: " + this.getDescription().getVersion() +  " by strubel (DerStrubel, strubelcopter) has been disabled!");
		
	}
	
    public static Location getTargetLocation(Player p, int maxDistance) {
    	
    	Location loc = p.getEyeLocation();
    	 
    	Vector v = loc.getDirection().normalize();
    	 
    	for(int i = 1 ; i <= maxDistance ; i++) {
    	  loc.add(v);
    	  if(loc.getBlock().getType() != Material.AIR)
    	    return loc;
    	}
    	
    	return loc;
    	
    }
    
    @SuppressWarnings("deprecation")
	public static Entity getTargetEntity(Player p) {
    	
    	for (Block targetBlock : p.getLineOfSight(null, 6)) {
		
        Location blockLoc = targetBlock.getLocation();
        double bx = blockLoc.getX();
        double by = blockLoc.getY();
        double bz = blockLoc.getZ();
        List<Entity> e = p.getNearbyEntities(6, 6, 6);
   
        for (Entity entity : e) {
            Location loc = entity.getLocation();
            double ex = loc.getX();
            double ey = loc.getY();
            double ez = loc.getZ();
       
            if ((bx-1.5 <= ex && ex <= bx+2) && (bz-1.5 <= ez && ez <= bz+2) && (by-1 <= ey && ey <= by+2.5)) {
        		return entity;
            }
        }
    	
    	}
    	
        return null;
    	
    }
    
    public static ArrayList<String> toUpperCase(ArrayList<String> strings)
    {
    	for(int i=0,l=strings.size();i<l;++i)
    	{
    	  strings.set(i, strings.get(i).toUpperCase());
    	}
		return strings;
        
    }
	
}
