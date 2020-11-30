package com.example.examplemod.tileentity;

import com.example.examplemod.itemhandler.AdjustableItemStackHandler;
import com.example.examplemod.data.ModWorldData;
import com.example.examplemod.packet.ModMessage;
import com.example.examplemod.packet.ModPacketHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import java.util.HashMap;
import java.util.UUID;

public class ChunkChestTileEntity extends BaseTileEntity {

    private AdjustableItemStackHandler handler;
    private ItemStackHandler handler_chest;
    private HashMap<UUID, Integer> pageNo = new HashMap<>();

    public int getHandlerSize() { return handler.getSlots(); }

    public void setHandlerSize(int size) {
        handler.setSize(size);
    }

    public ItemStackHandler getHandler() {
        return handler;
    }

    public ItemStackHandler getHandlerChest() {
        return handler_chest;
    }

    public int getPageNo(EntityPlayer player) {
        if (!pageNo.containsKey(player.getUniqueID())) {
            this.pageNo.put(player.getUniqueID(), 0);
        }
        return pageNo.get(player.getUniqueID());
    }

    public void setPageNo(EntityPlayer player, int no) {
        pageNo.put(player.getUniqueID(), no);
        IBlockState state = world.getBlockState(pos);
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
                }
            }
        };
    }
}
