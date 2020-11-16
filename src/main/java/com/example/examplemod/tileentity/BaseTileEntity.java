package com.example.examplemod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public abstract class BaseTileEntity extends TileEntity {
    public BaseTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        Vector3d center = new Vector3d(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D);
        return !isRemoved() && playerIn.getDistanceSq(center) <= 64D;
    }
}
