package com.example.examplemod.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class CounterTileEntity extends BaseTileEntity {
    private int counter = 0;

    public int increase () {
        counter++;
        markDirty();
        return counter;
    }

    public int getValue () {
        return counter;
    }

    @Override
    public void readFromNBT(CompoundNBT compound) {
        super.readFromNBT(compound);
        counter = compound.getInteger("counter");
    }

    @Override
    public CompoundNBT writeToNBT(CompoundNBT compound) {
        super.writeToNBT(compound);
        compound.setInteger("counter", counter);
        return compound;
    }
}
