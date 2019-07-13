package com.example.examplemod.itemstack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class FloatingItemStack implements INBTSerializable<NBTTagCompound> {
    public static final FloatingItemStack EMPTY = new FloatingItemStack(ItemStack.EMPTY, 0);
    private ItemStack itemStack;
    private double stackSize;

    public FloatingItemStack(NBTTagCompound nbt) {
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

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("itemStack", this.itemStack.serializeNBT());
        nbt.setDouble("stackSize", this.stackSize);
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.itemStack = nbt.hasKey("itemStack") ? new ItemStack(nbt.getCompoundTag("itemStack")) : ItemStack.EMPTY;
        this.stackSize = nbt.hasKey("stackSize") ? nbt.getDouble("itemStack") : 0.0;
    }
}
