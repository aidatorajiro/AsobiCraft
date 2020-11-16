package com.example.examplemod.tileentity;

import com.example.examplemod.ModObjects;
import com.example.examplemod.itemhandler.FloatingItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class FloatingChestTileEntity extends BaseTileEntity {
    private int INV_SIZE = 40;

    private FloatingItemStackHandler handler = new FloatingItemStackHandler(INV_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            FloatingChestTileEntity.this.markDirty();
        }
    };

    public FloatingChestTileEntity() {
        super(ModObjects.floatingChestType);
    }

    public FloatingItemStackHandler getHandler() {
        return handler;
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> handler).cast();
        }
        return super.getCapability(capability, facing);
    }

    /* TODO
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }*/

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        if (compound.contains("chest")) {
            handler.deserializeNBT(compound.getCompound("chest"));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("chest", handler.serializeNBT());
        return compound;
    }
}
