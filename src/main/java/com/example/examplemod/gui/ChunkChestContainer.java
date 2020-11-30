package com.example.examplemod.gui;

import com.example.examplemod.tileentity.ChunkChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ChunkChestContainer extends BaseContainer {
    private ChunkChestTileEntity tile;
    public static int chestSlotX = 135;
    public static int chestSlotY = 88;
    private IInventory playerInventory;

    public ChunkChestContainer(IInventory playerInventory, ChunkChestTileEntity tile) {
        this.tile = tile;
        this.playerInventory = playerInventory;
        redraw();
        this.addSlotToContainer(new SlotItemHandler(tile.getHandlerChest(), 0, chestSlotX, chestSlotY));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canInteractWith(playerIn);
    }

    public void redraw() {
        clearSlots();
        drawPlayerSlots(playerInventory, 9, 151);
        drawSlots(tile.getHandler(), 9, 16, 27*tile.getPageNo(), 9, 3);
    }

    public void emptyItemStacks() {
        for (int i = 36; i < this.inventoryItemStacks.size(); i++) {
            this.inventoryItemStacks.set(i, ItemStack.EMPTY);
            this.inventorySlots.get(i).getStack().setCount(0);
        }
    }
}
