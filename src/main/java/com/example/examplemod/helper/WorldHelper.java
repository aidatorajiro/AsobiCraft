package com.example.examplemod.helper;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WorldHelper {
    public static void createExplosionParticle(World world, BlockPos blockpos, double centerX, double centerY, double centerZ, double size) {
        double d0 = (double)((float)blockpos.getX() + world.rand.nextFloat());
        double d1 = (double)((float)blockpos.getY() + world.rand.nextFloat());
        double d2 = (double)((float)blockpos.getZ() + world.rand.nextFloat());
        double d3 = d0 - centerX;
        double d4 = d1 - centerY;
        double d5 = d2 - centerZ;
        double d6 = (double) MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
        d3 = d3 / d6;
        d4 = d4 / d6;
        d5 = d5 / d6;
        double d7 = 0.5D / (d6 / size + 0.1D);
        d7 = d7 * (double)(world.rand.nextFloat() * world.rand.nextFloat() + 0.3F);
        d3 = d3 * d7;
        d4 = d4 * d7;
        d5 = d5 * d7;
        world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + centerX) / 2.0D, (d1 + centerY) / 2.0D, (d2 + centerZ) / 2.0D, d3, d4, d5);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5);
    }
}
