package com.example.examplemod.tileentity;

import com.example.examplemod.itemhandler.AdjustableItemStackHandler;
import com.example.examplemod.data.ModWorldData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class ChunkChestTileEntity extends BaseTileEntity {

    private AdjustableItemStackHandler handler;
    private ItemStackHandler handler_chest;
    private int pageNo;

    public int getHandlerSize() { return handler.getSlots(); }

    public ItemStackHandler getHandler() {
        return handler;
    }

    public ItemStackHandler getHandlerChest() {
        return handler_chest;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 2);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.DOWN) {
                return (T) handler_chest;
            } else {
                return (T) handler;
            }
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
    public void onLoad() {
        super.onLoad();
        int chunkx = this.pos.getX() / 16;
        int chunkz = this.pos.getZ() / 16;
        handler = ModWorldData.get(world).getChunkChest(chunkx, chunkz);
        handler_chest = new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return stack.getItem() == Item.getItemFromBlock(Blocks.CHEST);
            }

            @Override
            protected void onContentsChanged(int slot) {
                int count = this.getStackInSlot(0).getCount();
                if (count > 0 && handler.getSlots() <= ModWorldData.CHUNK_CHEST_SIZE_MAX) {
                    int slots_to_add = count * 27;
                    if (handler.getSlots() + slots_to_add <= ModWorldData.CHUNK_CHEST_SIZE_MAX) {
                        handler.modifySize(slots_to_add);
                        this.getStackInSlot(0).setCount(0);
                    } else {
                        handler.modifySize(ModWorldData.CHUNK_CHEST_SIZE_MAX - handler.getSlots());
                        int remain = (handler.getSlots() + slots_to_add - ModWorldData.CHUNK_CHEST_SIZE_MAX) / 27;
                        this.getStackInSlot(0).setCount(remain);
                    }
                    IBlockState state = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos, state, state, 2);
                }
            }
        };
    }

    // client sync on notifyBlockUpdate

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound compound = pkt.getNbtCompound();
        if (compound.hasKey("numSlots")) {
            handler.setSize(compound.getInteger("numSlots"));
        }
        if (compound.hasKey("pageNo")) {
            pageNo = compound.getInteger("pageNo");
        }
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("numSlots", handler.getSlots());
        compound.setInteger("pageNo", pageNo);
        return new SPacketUpdateTileEntity(getPos(), -1, compound);
    }
}
