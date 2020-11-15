package com.example.examplemod.container;

import com.example.examplemod.helper.ItemHelper;
import com.example.examplemod.tileentity.CalculatorTileEntity;
import com.example.examplemod.tileentity.FloatingChestTileEntity;
import com.example.examplemod.itemhandler.FloatingItemStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class FloatingChestContainer extends BaseContainer {
    private FloatingChestTileEntity tile;

    public FloatingChestContainer(IInventory playerInventory, FloatingChestTileEntity tile) {
        this.tile = tile;
        drawPlayerSlots(playerInventory, 9, 151);
        drawItems();
    }

    private void drawItems() {

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
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
