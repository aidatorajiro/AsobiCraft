package com.example.examplemod.slot;

import com.example.examplemod.itemhandler.FloatingItemStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

class FloatingSlot extends Slot {
    private static IInventory emptyInventory = new InventoryBasic("[Null]", true, 0);

    protected FloatingItemStackHandler itemHandler;
    protected int index;

    public FloatingSlot(FloatingItemStackHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(emptyInventory, index, xPosition, yPosition);
        this.itemHandler = itemHandler;
        this.index = index;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        if (stack.isEmpty())
            return false;
        return itemHandler.isItemValid(index, stack);
    }

    @Override
    @Nonnull
    public ItemStack getStack()
    {
        ItemStack stack = this.getItemHandler().getStackInSlot(index).copy();
        if (stack.getCount() > 64) {
            stack.setCount(64);
        }
        return stack;
    }

    @Override
    public void putStack(@Nonnull ItemStack stack)
    {
        this.onSlotChanged();
    }

    @Override
    public void onSlotChange(@Nonnull ItemStack a, @Nonnull ItemStack b)
    {

    }

    @Override
    public int getSlotStackLimit()
    {
        return 64;
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack)
    {
        return 64;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return !this.getItemHandler().extractItem(index, 1, true).isEmpty();
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int amount)
    {
        return this.getItemHandler().extractItem(index, amount, false);
    }
}