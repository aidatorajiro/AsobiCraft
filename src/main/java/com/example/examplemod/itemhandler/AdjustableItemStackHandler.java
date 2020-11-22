package com.example.examplemod.itemhandler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class AdjustableItemStackHandler extends ItemStackHandler {
    public AdjustableItemStackHandler()
    {
        this(1);
    }

    public AdjustableItemStackHandler(int size)
    {
        stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public AdjustableItemStackHandler(NonNullList<ItemStack> stacks)
    {
        this.stacks = stacks;
    }
    public void modifySize(int size) {
        NonNullList<ItemStack> stacks_old = stacks;
        stacks = NonNullList.withSize(size + stacks.size(), ItemStack.EMPTY);
        for (int i = 0; i < stacks_old.size(); i++) {
            stacks.set(i, stacks_old.get(i));
        }
        onContentsChanged(-1);
    }
}
