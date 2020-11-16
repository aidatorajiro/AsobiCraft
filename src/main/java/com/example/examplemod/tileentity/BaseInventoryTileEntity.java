package com.example.examplemod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

public abstract class BaseInventoryTileEntity extends BaseTileEntity implements IInventory {
    public BaseInventoryTileEntity(TileEntityType<?> type) { super(type); }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return canInteractWith(player);
    }

    @Override
    public void openInventory(PlayerEntity player) {
    }

    @Override
    public void closeInventory(PlayerEntity player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    /*
    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) { }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }*/
}
