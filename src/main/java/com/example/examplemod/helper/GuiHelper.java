package com.example.examplemod.helper;

import com.example.examplemod.gui.ChunkChestContainer;

public class GuiHelper {
    public static boolean collision (int boundX, int boundY, int width, int height, int testX, int testY) {
        return boundX <= testX && testX <= boundX+width && boundY <= testY && testY <= boundY+height;
    }
    /*
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
    }*/
}
