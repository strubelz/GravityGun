package de.strubel.gravitygun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.strubel.gravitygun.attributes.AttributeStorage;
import de.strubel.gravitygun.attributes.NbtFactory;

public class GravityGun {
	
	private String name;
	private ItemStack display;
	private ArrayList<String> lore;
	private boolean canthrow;
	private CustomRecipe recipe;
	private ArrayList<String> blockpickup;
	private ArrayList<String> blockpickupexeptions;
	private ArrayList<String> entitypickup;
	private ArrayList<String> entitypickupexeptions;
	
	public static final UUID ID = UUID.fromString("5f473430-3275-4951-abc9-d39f4be8ce26");
	
	public GravityGun(String name) {
		
		this.setName(name);
		
	}
	
	public ItemStack getItemStack() {
		
		ItemStack i = NbtFactory.getCraftItemStack(this.display);
		
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(this.name);
		im.setLore(this.lore);
		i.setItemMeta(im);
		
		AttributeStorage storage = AttributeStorage.newTarget(i, ID);
		storage.setData("gravitygun;" + this.name);
		
		return storage.getTarget();
	}
	
	@SuppressWarnings("unchecked")
	public static GravityGun deserialize(Map<String, Object> map) {
		
		GravityGun grav = new GravityGun((String) map.get("Name"));
		
		grav.setLore((ArrayList<String>) map.get("Lore"));
		grav.setDisplay(new ItemStack(Material.getMaterial(((String) map.get("Item")).toUpperCase())));
		grav.setCanthrow((boolean) map.get("CanThrow"));
		grav.setBlockpickup(GravityGunMain.toUpperCase((ArrayList<String>) map.get("BlockPickups")));
		grav.setBlockpickupexeptions(GravityGunMain.toUpperCase((ArrayList<String>) map.get("BlockPickupExceptions")));
		grav.setEntitypickup(GravityGunMain.toUpperCase((ArrayList<String>) map.get("EntityPickups")));
		grav.setEntitypickupexeptions(GravityGunMain.toUpperCase((ArrayList<String>) map.get("EntityPickupExceptions")));
		grav.setRecipe(CustomRecipe.deserialize((Map<String, Object>) map.get("Recipe")));
		
		return grav;
	}
	
	public Map<String, Object> serialize() {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("Name", this.name);
		map.put("Item", this.display.getType().toString().toLowerCase());
		map.put("Lore", this.lore);
		map.put("CanThrow", this.canthrow);
		map.put("BlockPickups", this.blockpickup);
		map.put("BlockPickupExceptions", this.blockpickupexeptions);
		map.put("EntityPickups", this.entitypickup);
		map.put("EntityPickupExceptions", this.entitypickupexeptions);
		map.put("Recipe", this.recipe.serialize());
		
		return map;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getLore() {
		return lore;
	}

	public void setLore(ArrayList<String> lore) {
		this.lore = lore;
	}

	public boolean isCanthrow() {
		return canthrow;
	}

	public void setCanthrow(boolean canthrow) {
		this.canthrow = canthrow;
	}

	public ArrayList<String> getBlockpickup() {
		return blockpickup;
	}

	public void setBlockpickup(ArrayList<String> blockpickup) {
		this.blockpickup = blockpickup;
	}

	public ArrayList<String> getBlockpickupexeptions() {
		return blockpickupexeptions;
	}

	public void setBlockpickupexeptions(ArrayList<String> blockpickupexeptions) {
		this.blockpickupexeptions = blockpickupexeptions;
	}

	public ArrayList<String> getEntitypickup() {
		return entitypickup;
	}

	public void setEntitypickup(ArrayList<String> entitypickup) {
		this.entitypickup = entitypickup;
	}

	public ArrayList<String> getEntitypickupexeptions() {
		return entitypickupexeptions;
	}

	public void setEntitypickupexeptions(ArrayList<String> entitypickupexeptions) {
		this.entitypickupexeptions = entitypickupexeptions;
	}

	public ItemStack getDisplay() {
		return display;
	}

	public void setDisplay(ItemStack display) {
		this.display = display;
	}

	public CustomRecipe getRecipe() {
		return recipe;
	}

	public void setRecipe(CustomRecipe recipe) {
		this.recipe = recipe;
	}
	
}
