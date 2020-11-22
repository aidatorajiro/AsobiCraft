package com.example.examplemod.container;

import com.example.examplemod.helper.GuiHelper;
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
        drawFloatingSlots(tile.getHandler(), 9, 16, 9);
    }

    public boolean mergeItemStack(FloatingItemStackHandler handler, ItemStack itemstack) {
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack result = handler.insertItem(i, itemstack, false);
            if (result.isEmpty()) {
                itemstack.setCount(0);
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack ret = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack = slot.getStack();
            ret = itemstack.copy();

            if (!mergeItemStack(tile.getHandler(), itemstack)) {
                return ItemStack.EMPTY;
            }

            if (itemstack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return ret;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canInteractWith(playerIn);
    }
}
