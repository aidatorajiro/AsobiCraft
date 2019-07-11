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

public class FloatingItemStackHandler implements IItemHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound> {
    private NonNullList<FloatingItemStack> stacks;

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
        this.stacks.set(slot, new FloatingItemStack(stack));
        onContentsChanged(slot);
    }

    @Override
    public int getSlots() {
        return stacks.size();
    }

    @Nonnull
    @Override
    /* SERIOUSLY, AGAIN, DO NOT MODIFY THIS ITEMSTACK! */
    public ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);
        return this.stacks.get(slot).asItemStack();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        FloatingItemStack existing = this.stacks.get(slot);

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing.asItemStack()))
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

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        if (existing.getStackSize() <= amount)
        {
            if (!simulate)
            {
                this.stacks.set(slot, FloatingItemStack.EMPTY);
                onContentsChanged(slot);
            }
            return existing.asItemStack();
        }
        else
        {
            if (!simulate)
            {
                this.stacks.set(slot, existing.copy().modifyStackSize(-amount));
                onContentsChanged(slot);
            }

            return existing.copy().setStackSize(amount).asItemStack();
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 2147483647;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }

    protected void onContentsChanged(int slot)
    {
    }
}
