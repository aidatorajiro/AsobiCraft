package com.example.examplemod.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerHelper extends net.minecraftforge.items.ItemHandlerHelper {
    public static boolean isEmpty(IItemHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static Tuple<IItemHandler, Integer> getMergedSlot(int index, IItemHandler... handlers) {
        for (IItemHandler handler : handlers) {
            int slots = handler.getSlots();
            if (index >= slots) {
                index -= slots;
                continue;
            } else {
                return new Tuple(handler, index);
            }
        }
        return new Tuple(null, 0);
    }

    public static void clear(IItemHandler handler) {
        int count = handler.getSlots();
        for (int i = 0; i < count; i++) {
            handler.extractItem(i, handler.getStackInSlot(i).getCount(), false);
        }
    }
}
