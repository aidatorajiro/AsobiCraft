package com.example.examplemod.tileentity;

import com.example.examplemod.ModObjects;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class CounterTileEntity extends BaseTileEntity {
    private int counter = 0;

    public CounterTileEntity() {
        super(ModObjects.counterType);
    }

    public int increase () {
        counter++;
        markDirty();
        return counter;
    }

    public int getValue () {
        return counter;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        counter = compound.getInt("counter");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("counter", counter);
        return compound;
    }
}
