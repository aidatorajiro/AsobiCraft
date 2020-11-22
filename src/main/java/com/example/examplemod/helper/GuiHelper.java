package com.example.examplemod.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Container;

public class GuiHelper {
    public static ItemStack transferStackInSlotDefault(Container containerIn, Arg4<ItemStack, Integer, Integer, Boolean, Boolean> mis, EntityPlayer playerIn, int index) {
        ItemStack ret = ItemStack.EMPTY;
        Slot slot = containerIn.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack = slot.getStack();
            ret = itemstack.copy();

            if (index < 36) {
                if (!mis.apply(itemstack, 36, containerIn.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mis.apply(itemstack, 0, 36, false)) {
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
}
