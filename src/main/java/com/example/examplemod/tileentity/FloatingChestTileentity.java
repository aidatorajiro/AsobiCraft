package com.example.examplemod.tileentity;

import com.example.examplemod.helper.ItemHandlerHelper;
import com.example.examplemod.itemhandler.FloatingItemStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class FloatingChestTileentity extends BaseInventoryTileEntity {
    int INV_SIZE = 40;
    private static double EXPORT_SIZE = FloatingItemStackHandler.MAX_ITEMSTACK_EXPORT_SIZE;

    private FloatingItemStackHandler handler = new FloatingItemStackHandler(INV_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            FloatingChestTileentity.this.markDirty();
        }
    };

    @Override
    public int getSizeInventory() {
        return INV_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return ItemHandlerHelper.isEmpty(handler);
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return handler.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return handler.extractItem(index, count, false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return decrStackSize(index, (int) EXPORT_SIZE);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (handler.getStackInSlotFloating(index).getStackSize() <= EXPORT_SIZE) {
            handler.setStackInSlot(index, stack);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return (int)EXPORT_SIZE;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return "mod.examplemod.container.floatingchest";
    }
}
