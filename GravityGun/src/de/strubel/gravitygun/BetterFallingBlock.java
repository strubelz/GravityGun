package de.strubel.gravitygun;

import java.util.Iterator;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.BlockFalling;
import net.minecraft.server.v1_7_R1.Blocks;
import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityFallingBlock;
import net.minecraft.server.v1_7_R1.IContainer;
import net.minecraft.server.v1_7_R1.ItemStack;
import net.minecraft.server.v1_7_R1.Material;
import net.minecraft.server.v1_7_R1.MathHelper;
import net.minecraft.server.v1_7_R1.NBTBase;
import net.minecraft.server.v1_7_R1.NBTTagCompound;
import net.minecraft.server.v1_7_R1.TileEntity;
import net.minecraft.server.v1_7_R1.World;
 
public class BetterFallingBlock extends EntityFallingBlock {
	
	boolean f;
 
    public BetterFallingBlock(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, Block paramInt, int i) {
    	super(paramWorld, paramDouble1, paramDouble2, paramDouble3, paramInt);
        this.dropItem = false;
        this.data = i;
    }
    
    @Override
    public void h() {	
        if (this.id.getMaterial() == Material.AIR) {
            this.die();
        } else {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            ++this.b;
            this.motY -= 0.03999999910593033D;
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.9800000190734863D;
            this.motY *= 0.9800000190734863D;
            this.motZ *= 0.9800000190734863D;
            if (!this.world.isStatic) {
                int i = MathHelper.floor(this.locX);
                int j = MathHelper.floor(this.locY);
                int k = MathHelper.floor(this.locZ);

                if (this.b == 1) {
                    if (this.world.getType(i, j, k) != this.id) {
                        //this.die();
                        return;
                    }

                    this.world.setAir(i, j, k);
                }

                if (this.onGround) {
                    this.motX *= 0.699999988079071D;
                    this.motZ *= 0.699999988079071D;
                    this.motY *= -0.5D;
                    if (this.world.getType(i, j, k) != Blocks.PISTON_MOVING) {
                        this.die();
                        if (!this.f && this.world.mayPlace(this.id, i, j, k, true, 1, (Entity) null, (ItemStack) null) && !BlockFalling.canFall(this.world, i, j - 1, k) && this.world.setTypeAndData(i, j, k, this.id, this.data, 3)) {
                            if (this.id instanceof BlockFalling) {
                                ((BlockFalling) this.id).a(this.world, i, j, k, this.data);
                            }

                            if (this.tileEntityData != null && this.id instanceof IContainer) {
                                TileEntity tileentity = this.world.getTileEntity(i, j, k);

                                if (tileentity != null) {
                                    NBTTagCompound nbttagcompound = new NBTTagCompound();

                                    tileentity.b(nbttagcompound);
                                    
                                    @SuppressWarnings("rawtypes")
									Iterator iterator = this.tileEntityData.c().iterator();

                                    while (iterator.hasNext()) {
                                        String s = (String) iterator.next();
                                        NBTBase nbtbase = this.tileEntityData.get(s);

                                        if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
                                            nbttagcompound.set(s, nbtbase.clone());
                                        }
                                    }

                                    tileentity.a(nbttagcompound);
                                    tileentity.update();
                                }
                            }
                        } else if (this.dropItem && !this.f) {
                            this.a(new ItemStack(this.id, 1, this.id.getDropData(this.data)), 0.0F);
                        }
                    }
                } else if (this.b > 100 && !this.world.isStatic && (j < 1 || j > 256) || this.b > 600) {
                    if (this.dropItem) {
                        this.a(new ItemStack(this.id, 1, this.id.getDropData(this.data)), 0.0F);
                    }

                    //this.die();
                }
            }
        }
    }
    
}