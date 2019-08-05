package com.example.examplemod.container;

import com.example.examplemod.tileentity.CalculatorTileEntity;
import com.example.examplemod.tileentity.FloatingChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
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
        IItemHandler itemHandler = this.tile.getHandler();
        drawSlots(itemHandler, 9, 16, 9);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack ret = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack = slot.getStack();
            ret = itemstack.copy();

            if (index < 36) {
                if (!this.mergeItemStack(itemstack, 36, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack, 0, 36, false)) {
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
