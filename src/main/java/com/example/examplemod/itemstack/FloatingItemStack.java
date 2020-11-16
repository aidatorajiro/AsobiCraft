package com.example.examplemod.itemstack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class FloatingItemStack implements INBTSerializable<CompoundNBT> {
    public static final FloatingItemStack EMPTY = new FloatingItemStack(ItemStack.EMPTY, 0);
    protected ItemStack itemStack;
    protected double stackSize;

    public FloatingItemStack(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public FloatingItemStack(ItemStack itemStackIn) {
        this.itemStack = itemStackIn;
        this.stackSize = itemStackIn.getCount();
    }

    public FloatingItemStack(ItemStack itemStackIn, double amount) {
        this.itemStack = itemStackIn;
        this.stackSize = amount;
    }

    public FloatingItemStack copy () {
        return new FloatingItemStack(itemStack.copy(), stackSize);
    }

    public boolean isEmpty() {
        return stackSize == 0 || itemStack.isEmpty();
    }

    /**
     * Get *copied* ItemStack of this FloatingItemStack.
     * @return ItemStack
     */
    public ItemStack getItemStack() {
        return  this.itemStack.copy();
    }

    public FloatingItemStack modifyStackSize (double amount) {
        stackSize += amount;
        return this;
    }

    public double getStackSize () {
        return stackSize;
    }

    public FloatingItemStack setStackSize (double amount) {
        stackSize = amount;
        return this;
    }

    public CompoundNBT writeToNBT(CompoundNBT nbt) {
        nbt.put("itemStack", this.itemStack.serializeNBT());
        nbt.putDouble("stackSize", this.stackSize);
        return nbt;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        final ItemStack itemStack = ItemStack.read(nbt.getCompound("itemStack"));
        this.itemStack = nbt.contains("itemStack") ? itemStack : ItemStack.EMPTY;
        this.stackSize = nbt.contains("stackSize") ? nbt.getDouble("stackSize") : 0.0;
    }
}
