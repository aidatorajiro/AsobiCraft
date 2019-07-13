package com.example.examplemod.helper;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import static net.minecraft.inventory.InventoryHelper.spawnItemStack;

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

    public static ItemStack clearSlotMerged(int prev_index, IItemHandlerModifiable... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandlerModifiable handler = (IItemHandlerModifiable) t.getFirst();
        int index = t.getSecond();
        ItemStack copy = handler.getStackInSlot(index).copy();
        handler.setStackInSlot(index, ItemStack.EMPTY);
        return copy;
    }

    public static void setSlotMerged(int prev_index, ItemStack itemStack, IItemHandlerModifiable... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandlerModifiable handler = (IItemHandlerModifiable)  t.getFirst();
        int index = t.getSecond();
        handler.setStackInSlot(index, itemStack.copy());
    }

    public static boolean isItemValidMerged(int prev_index, ItemStack itemStack, IItemHandler... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandler handler = t.getFirst();
        int index = t.getSecond();
        return handler.isItemValid(index, itemStack);
    }

    public static void dropHandlerItems(World worldIn, BlockPos pos, IItemHandler handler)
    {
        dropHandlerItems(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), handler);
    }

    public static void dropHandlerItems(World worldIn, Entity entityAt, IItemHandler handler)
    {
        dropHandlerItems(worldIn, entityAt.posX, entityAt.posY, entityAt.posZ, handler);
    }

    public static void dropHandlerItems(World worldIn, double x, double y, double z, IItemHandler handler) {
        int count = handler.getSlots();
        for (int i = 0; i < count; i++) {
            ItemStack stack = handler.extractItem(i, handler.getStackInSlot(i).getCount(), false);
            spawnItemStack(worldIn, x, y, z, stack);
        }
    }
}
