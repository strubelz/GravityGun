package de.strubel.gravitygun;

import org.bukkit.entity.Bat;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InvisibilityRunnable implements Runnable {

	@Override
	public void run() {
		
		for (Bat bat : GravityGunMain.map.values()) {
			
			PotionEffect pot = new PotionEffect(PotionEffectType.INVISIBILITY, 6000, 1);
			bat.addPotionEffect(pot);
			
		}
		
	}

}
