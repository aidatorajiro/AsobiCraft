package com.example.examplemod.tileentity;

import net.minecraft.nbt.CompoundNBT;

public class StateManipulatorTileEntity extends BaseTileEntity {
    private int counter = 0;

    public int increase () {
        counter = (counter + 1) & 15;
        markDirty();
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
