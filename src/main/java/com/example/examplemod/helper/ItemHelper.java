package com.example.examplemod.helper;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.swing.plaf.basic.BasicComboBoxUI;

import static net.minecraft.inventory.InventoryHelper.spawnItemStack;

public class ItemHelper extends net.minecraftforge.items.ItemHandlerHelper {
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
        IItemHandler handler = t.getA();
        int index = t.getB();
        return handler.getStackInSlot(index);
    }

    public static ItemStack extractItemMerged(int prev_index, int num, boolean simulate, IItemHandler... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandler handler = t.getA();
        int index = t.getB();
        return handler.extractItem(index, num, simulate);
    }

    public static ItemStack clearSlotMerged(int prev_index, IItemHandlerModifiable... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandlerModifiable handler = (IItemHandlerModifiable) t.getA();
        int index = t.getB();
        ItemStack copy = handler.getStackInSlot(index).copy();
        handler.setStackInSlot(index, ItemStack.EMPTY);
        return copy;
    }

    public static void setSlotMerged(int prev_index, ItemStack itemStack, IItemHandlerModifiable... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandlerModifiable handler = (IItemHandlerModifiable)  t.getA();
        int index = t.getB();
        handler.setStackInSlot(index, itemStack.copy());
    }

    public static boolean isItemValidMerged(int prev_index, ItemStack itemStack, IItemHandler... handlers) {
        Tuple<IItemHandler, Integer> t = getMergedHandler(prev_index, handlers);
        IItemHandler handler = t.getA();
        int index = t.getB();
        return handler.isItemValid(index, itemStack);
    }

    public static void dropHandlerItems(World worldIn, BlockPos pos, IItemHandler handler)
    {
        dropHandlerItems(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), handler);
    }

    public static void dropHandlerItems(World worldIn, Entity entityAt, IItemHandler handler)
    {
        dropHandlerItems(worldIn, entityAt.getPosX(), entityAt.getPosY(), entityAt.getPosZ(), handler);
    }

    /**
     * Spawn all items in given handler and clear given handler.
     * @param worldIn
     * @param x
     * @param y
     * @param z
     * @param handler
     */
    public static void dropHandlerItems(World worldIn, double x, double y, double z, IItemHandler handler) {
        int count = handler.getSlots();
        for (int i = 0; i < count; i++) {
            ItemStack stack = handler.extractItem(i, handler.getStackInSlot(i).getCount(), false);
            spawnItemStack(worldIn, x, y, z, stack);
        }
    }

    /**
     * copy NonNullList of ItemStack
     * @param in
     * @return
     */
    public static NonNullList<ItemStack> copyStackList(NonNullList<ItemStack> in) {
        NonNullList<ItemStack> out = NonNullList.create();
        for (ItemStack stack : in) {
            out.add(stack.copy());
        }
        return out;
    }

    public static void dropStackList(World worldIn, BlockPos pos, NonNullList<ItemStack> in)
    {
        dropStackList(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), in);
    }

    public static void dropStackList(World worldIn, Entity entityAt, NonNullList<ItemStack> in)
    {
        dropStackList(worldIn, entityAt.getPosX(), entityAt.getPosY(), entityAt.getPosZ(), in);
    }

    /**
     * Spawn all items in given NonNullList of ItemStack
     * @return
     */
    public static void dropStackList(World worldIn, double x, double y, double z, NonNullList<ItemStack> in) {
        for (ItemStack stack : in) {
            spawnItemStack(worldIn, x, y, z, stack.copy());
        }
    }

    public static ItemStack insertStackToHandler(IItemHandler handler, ItemStack stack) {
        ItemStack ret = stack.copy();
        for (int i = 0; i < handler.getSlots(); i++) {
            ret = handler.insertItem(i, ret, false);
            if (ret.isEmpty()) {
                break;
            }
        }
        return ret;
    }
}
