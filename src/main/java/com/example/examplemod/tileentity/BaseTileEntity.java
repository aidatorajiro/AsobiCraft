package com.example.examplemod.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public abstract class BaseTileEntity extends TileEntity {
    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }
}
