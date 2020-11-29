package com.example.examplemod.tileentity;

import com.example.examplemod.itemhandler.FloatingItemStackHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class FloatingChestTileEntity extends BaseTileEntity {
    private int INV_SIZE = 27;

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

    // core read/write func

    private void read(NBTTagCompound compound) {
        if (compound.hasKey("chest")) {
            handler.deserializeNBT((NBTTagCompound) compound.getTag("chest"));
        }
    }

    private NBTTagCompound write(NBTTagCompound compound) {
        compound.setTag("chest", handler.serializeNBT());
        return compound;
    }

    // save data read/write

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        read(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        return write(compound);
    }

    // client sync on chunk load

    @Override
    public void handleUpdateTag(NBTTagCompound compound) {
        super.handleUpdateTag(compound);
        read(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = super.getUpdateTag();
        return write(compound);
    }

    // client sync on notifyBlockUpdate

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound compound = pkt.getNbtCompound();
        read(compound);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = write(new NBTTagCompound());
        return new SPacketUpdateTileEntity(getPos(), -1, compound);
    }
}
