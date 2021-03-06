package com.example.examplemod.gui;

import com.example.examplemod.helper.ItemHelper;
import com.example.examplemod.tileentity.FloatingChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FloatingChestContainer extends BaseContainer {
    private FloatingChestTileEntity tile;

    public FloatingChestContainer(IInventory playerInventory, FloatingChestTileEntity tile) {
        this.tile = tile;
        drawPlayerSlots(playerInventory, 9, 151);
        drawFloatingSlots(tile.getHandler(), 9, 16, 9, 3);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);
        if (slot == null || !slot.getHasStack() || slot.getStack().isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack remain = ItemHelper.insertStackToHandler(tile.getHandler(), slot.getStack());
        slot.putStack(remain);
        slot.onSlotChanged();
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canInteractWith(playerIn);
    }
}
