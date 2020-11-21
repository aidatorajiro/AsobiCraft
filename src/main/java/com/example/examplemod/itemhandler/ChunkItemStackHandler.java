package com.example.examplemod.itemhandler;

import com.example.examplemod.itemstack.FloatingItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class ChunkItemStackHandler extends ItemStackHandler implements IItemHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound> {
    public ChunkItemStackHandler()
    {
        this(1);
    }

    public ChunkItemStackHandler(int size) {
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public ChunkItemStackHandler(NonNullList<ItemStack> stacks)
    {
        this.stacks = stacks;
    }
}
