package com.example.examplemod.itemhandler;

import com.example.examplemod.helper.ItemHelper;
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
        ItemHelper.changeListSize(stacks, size + stacks.size(), ItemStack.EMPTY);
        onContentsChanged(-1);
    }
}
