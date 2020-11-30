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
    public void setSize(int size) {
        stacks = ItemHelper.withListSize(stacks, size, ItemStack.EMPTY);
        onSizeChanged(0);
    }

    protected void onSizeChanged(int index) {}

    public void modifySize(int size) {
        setSize(size + stacks.size());
    }
}
