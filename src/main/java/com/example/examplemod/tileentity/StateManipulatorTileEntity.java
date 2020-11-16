package com.example.examplemod.tileentity;

import com.example.examplemod.ModObjects;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class StateManipulatorTileEntity extends BaseTileEntity {
    private int counter = 0;

    public StateManipulatorTileEntity() {
        super(ModObjects.stateManipulatorType);
    }

    public int increase () {
        counter = (counter + 1) & 15;
        markDirty();
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
