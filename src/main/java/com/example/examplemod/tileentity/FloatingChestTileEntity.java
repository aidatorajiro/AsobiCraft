package com.example.examplemod.tileentity;

import com.example.examplemod.itemhandler.FloatingItemStackHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
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

    public FloatingItemStackHandler getHandler() {
        return handler;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T)handler;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public void readFromNBT(CompoundNBT compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("chest")) {
            handler.deserializeNBT((CompoundNBT) compound.getTag("chest"));
        }
    }

    @Override
    public CompoundNBT writeToNBT(CompoundNBT compound) {
        super.writeToNBT(compound);
        compound.setTag("chest", handler.serializeNBT());
        return compound;
    }
}
