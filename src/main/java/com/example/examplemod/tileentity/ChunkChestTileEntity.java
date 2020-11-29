package com.example.examplemod.tileentity;

import com.example.examplemod.itemhandler.AdjustableItemStackHandler;
import com.example.examplemod.saveddata.ModWorldData;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class ChunkChestTileEntity extends BaseTileEntity {

    private AdjustableItemStackHandler handler;
    private ItemStackHandler handler_chest;

    public ItemStackHandler getHandler() {
        return handler;
    }

    public ItemStackHandler getHandlerChest() {
        return handler_chest;
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
                int slots_to_add = count * 27;
                if (handler.getSlots() + slots_to_add <= ModWorldData.CHUNK_CHEST_SIZE_MAX) { // TODO
                    handler.modifySize(slots_to_add);
                    this.getStackInSlot(0).setCount(0);
                }
            }
        };
    }
}
