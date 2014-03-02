package de.strubel.gravitygun;

import java.lang.reflect.Field;

import net.minecraft.server.v1_7_R1.DamageSource;
import net.minecraft.server.v1_7_R1.EntityBat;
import net.minecraft.server.v1_7_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R1.World;

import org.bukkit.craftbukkit.v1_7_R1.util.UnsafeList;

public class NewBat extends EntityBat {

	public NewBat(World world) {
		super(world);
		
		this.setInvisible(true);
		
	    try {
	        Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
	        bField.setAccessible(true);
	        Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
	        cField.setAccessible(true);
	        bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
	        bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
	        cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
	        cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
	        } catch (Exception exc) {
	        exc.printStackTrace();
	        }
		
	}
	
	@Override
	public boolean bk() {
		return false;
	}
	
	@Override
    public boolean damageEntity(DamageSource damagesource, float f) {
            return false;
        }
}