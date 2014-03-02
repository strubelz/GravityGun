package de.strubel.gravitygun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomRecipe {
	
	private ArrayList<ItemStack> recipe;
	
	public CustomRecipe(ArrayList<ItemStack> recipe) {
		this.recipe = recipe;
	}
	
	public Map<String, Object> serialize() {
		
		Map<String, Object> map = new HashMap<>();
		
		ArrayList<String> slist = new ArrayList<>();
		
		for (ItemStack i : this.recipe) {
			slist.add(i.getType().toString().toLowerCase());
		}
		
		map.put("Recipe", slist);
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static CustomRecipe deserialize(Map<String, Object> map) {
		
		ArrayList<ItemStack> ilist = new ArrayList<>();
		
		for (String s : (ArrayList<String>) map.get("Recipe")) {
			ilist.add(new ItemStack(Material.getMaterial(s.toUpperCase())));
		}
		
		CustomRecipe c = new CustomRecipe(ilist);
		
		return c;
	}

	public ArrayList<ItemStack> getRecipe() {
		return recipe;
	}

	public void setRecipe(ArrayList<ItemStack> recipe) {
		this.recipe = recipe;
	}
	
}
