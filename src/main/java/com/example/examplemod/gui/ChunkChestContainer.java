package com.example.examplemod.gui;

import com.example.examplemod.tileentity.ChunkChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.SlotItemHandler;

public class ChunkChestContainer extends BaseContainer {
    private ChunkChestTileEntity tile;
    public static int chestSlotX = 135;
    public static int chestSlotY = 88;

    public ChunkChestContainer(IInventory playerInventory, ChunkChestTileEntity tile) {
        this.tile = tile;
        drawPlayerSlots(playerInventory, 9, 151);
        drawSlots(tile.getHandler(), 9, 16, 27*tile.getPageNo(), 9, 3);
        this.addSlotToContainer(new SlotItemHandler(tile.getHandlerChest(), 0, chestSlotX, chestSlotY));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canInteractWith(playerIn);
    }
}
