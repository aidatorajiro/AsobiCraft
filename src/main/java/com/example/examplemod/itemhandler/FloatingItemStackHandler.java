package com.example.examplemod.itemhandler;

import com.example.examplemod.itemstack.FloatingItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

import java.lang.Math;

public class FloatingItemStackHandler implements IItemHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound> {
    public static double MAX_ITEMSTACK_EXPORT_SIZE = 2147483647;

    protected NonNullList<FloatingItemStack> stacks;

    public FloatingItemStackHandler()
    {
        this(1);
    }

    public FloatingItemStackHandler(int size)
    {
        this.stacks = NonNullList.withSize(size, FloatingItemStack.EMPTY);
    }

    public FloatingItemStackHandler(NonNullList<FloatingItemStack> stacks)
    {
        this.stacks = stacks;
    }

    public void addFloatingItemStack(FloatingItemStack item) {
        stacks.add(item);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < stacks.size(); i++)
        {
            if (!stacks.get(i).isEmpty())
            {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setInteger("Slot", i);
                stacks.get(i).writeToNBT(itemTag);
                nbtTagList.appendTag(itemTag);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", nbtTagList);
        nbt.setInteger("Size", stacks.size());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        int size = nbt.hasKey("Size", Constants.NBT.TAG_INT) ? nbt.getInteger("Size") : stacks.size();
        this.stacks = NonNullList.withSize(size, FloatingItemStack.EMPTY);
        NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
            int slot = itemTags.getInteger("Slot");

            if (slot >= 0 && slot < stacks.size())
            {
                stacks.set(slot, new FloatingItemStack(itemTags));
            }
        }
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
        validateSlotIndex(slot);
        // protective; if the amount to be deleted is greater than MAX_ITEMSTACK_EXPORT_SIZE, just ignore...
        if (this.stacks.get(slot).getStackSize() > MAX_ITEMSTACK_EXPORT_SIZE) {
            return;
        }
        this.stacks.set(slot, new FloatingItemStack(stack));
        onContentsChanged(slot);
    }

    @Override
    public int getSlots() {
        return stacks.size();
    }

    /**
     * Return the slot designated by given slot id as an ItemStack.
     * Modifying any ItemStack which is returned by this function will do no change.
     * @param slot slot id
     */
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);
        FloatingItemStack stack = this.stacks.get(slot);
        ItemStack export = stack.getItemStack();
        export.setCount((int) Math.min(MAX_ITEMSTACK_EXPORT_SIZE, stack.getStackSize()));
        return export;
    }

    @Nonnull
    public FloatingItemStack getStackInSlotFloating(int slot) {
        validateSlotIndex(slot);
        return this.stacks.get(slot).copy();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        FloatingItemStack existing = this.stacks.get(slot);

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing.getItemStack()))
                return stack;
        }

        if (!simulate)
        {
            if (existing.isEmpty())
            {
                this.stacks.set(slot, new FloatingItemStack(stack.copy()));
            }
            else
            {
                existing.modifyStackSize(stack.getCount());
            }
            onContentsChanged(slot);
        }

        return ItemStack.EMPTY;
    }

    public FloatingItemStack insertItemFloating(int slot, @Nonnull FloatingItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return FloatingItemStack.EMPTY;

        validateSlotIndex(slot);

        FloatingItemStack existing = this.stacks.get(slot);

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack.getItemStack(), existing.getItemStack()))
                return stack;
        }

        if (!simulate) {
            if (existing.isEmpty())
            {
                this.stacks.set(slot, stack.copy());
            }
            else
            {
                existing.modifyStackSize(stack.getStackSize());
            }
            onContentsChanged(slot);
        }

        return FloatingItemStack.EMPTY;
    }

    protected void validateSlotIndex(int slot)
    {
        if (slot < 0 || slot >= stacks.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        FloatingItemStack existing = this.stacks.get(slot);
        double existingStackSize = existing.getStackSize();
        ItemStack export = existing.getItemStack();

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        if (existingStackSize <= amount)
        {
            if (!simulate)
            {
                this.stacks.set(slot, FloatingItemStack.EMPTY);
                onContentsChanged(slot);
            }

            export.setCount((int) Math.min(MAX_ITEMSTACK_EXPORT_SIZE, existingStackSize));
            return export;
        }
        else
        {
            if (!simulate)
            {
                this.stacks.set(slot, existing.copy().modifyStackSize(-amount));
                onContentsChanged(slot);
            }

            export.setCount(amount);
            return export;
        }
    }

    public FloatingItemStack extractItemFloating(int slot, double amount, boolean simulate) {
        if (amount == 0)
            return FloatingItemStack.EMPTY;

        validateSlotIndex(slot);

        FloatingItemStack existing = this.stacks.get(slot);
        double existingStackSize = existing.getStackSize();
        FloatingItemStack export = new FloatingItemStack(existing.getItemStack());

        if (existing.isEmpty())
            return FloatingItemStack.EMPTY;

        if (existingStackSize <= amount)
        {
            if (!simulate)
            {
                this.stacks.set(slot, FloatingItemStack.EMPTY);
                onContentsChanged(slot);
            }

            export.setStackSize(existingStackSize);
            return export;
        }
        else
        {
            if (!simulate)
            {
                this.stacks.set(slot, existing.copy().modifyStackSize(-amount));
                onContentsChanged(slot);
            }

            export.setStackSize(amount);
            return export;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return (int) MAX_ITEMSTACK_EXPORT_SIZE;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }

    protected void onContentsChanged(int slot)
    {
    }
}
