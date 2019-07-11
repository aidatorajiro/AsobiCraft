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

    public static Tuple<IItemHandler, Integer> getMergedHandler(int index, IItemHandler... handlers) {
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

    public static ItemStack getStackInSlotMerged(int prev_index, IItemHandler... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandler handler = t.getFirst();
        int index = t.getSecond();
        return handler.getStackInSlot(index);
    }

    public static ItemStack extractItemMerged(int prev_index, int num, boolean simulate, IItemHandler... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandler handler = t.getFirst();
        int index = t.getSecond();
        return handler.extractItem(index, num, simulate);
    }

    public static ItemStack clearSlotMerged(int prev_index, IItemHandler... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandler handler = t.getFirst();
        int index = t.getSecond();
        return handler.extractItem(index, handler.getStackInSlot(index).getCount(), false);
    }

    public static void setSlotMerged(int prev_index, ItemStack itemStack, IItemHandler... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandler handler = t.getFirst();
        int index = t.getSecond();
        handler.extractItem(index, handler.getStackInSlot(index).getCount(), false);
        handler.insertItem(index, itemStack, false);
    }

    public static boolean isItemValidMerged(int prev_index, ItemStack itemStack, IItemHandler... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandler handler = t.getFirst();
        int index = t.getSecond();
        return handler.isItemValid(index, itemStack);
    }
}
