package de.strubel.gravitygun;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InvisibilityRunnable implements Runnable {

	@Override
	public void run() {
		
		for (Entity ent : GravityGunMain.map.values()) {
			
			if (ent instanceof LivingEntity) {
			
			PotionEffect pot = new PotionEffect(PotionEffectType.INVISIBILITY, 6000, 1);
			((LivingEntity) ent).addPotionEffect(pot);
			
			}
			
		}
		
	}

}
