package com.example.examplemod.container;

import com.example.examplemod.helper.ItemHelper;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import com.example.examplemod.tileentity.FloatingChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ChunkChestContainer extends BaseContainer {
    private ChunkChestTileEntity tile;

    public ChunkChestContainer(IInventory playerInventory, ChunkChestTileEntity tile) {
        this.tile = tile;
        drawPlayerSlots(playerInventory, 9, 151);
        drawSlots(tile.getHandler(), 9, 16, 9, 3);
        this.addSlotToContainer(new SlotItemHandler(tile.getHandlerChest(), 0, 135, 88));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canInteractWith(playerIn);
    }
}
