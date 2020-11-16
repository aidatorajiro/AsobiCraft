package com.example.examplemod.container;

import com.example.examplemod.tileentity.CalculatorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class CalculatorContainer extends BaseContainer {
    private CalculatorTileEntity tile;

    public CalculatorContainer (@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory, CalculatorTileEntity tile) {
        super(type, id);
        this.tile = tile;
        drawPlayerSlots(playerInventory, 9, 151);
        drawInputs();
        drawOutputs();
    }

    private void drawInputs() {
        IItemHandler itemHandler = this.tile.getInput();
        int centerX = 89;
        int centerY = 76;
        int radius = 60;
        double theta = 2*Math.PI/16.0;

        if (itemHandler == null) { return; }

        // Add our own slots
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            int x = (int)(-Math.sin(theta*i)*radius + centerX - 8);
            int y = (int)(Math.cos(theta*i)*radius + centerY - 8);
            addSlot(new SlotItemHandler(itemHandler, i, x, y));
        }
    }

    private void drawOutputs() {
        IItemHandler itemHandler = this.tile.getOutput();
        if (itemHandler == null) { return; }
        drawSlots(itemHandler, 174, 9, 4, 12);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack ret = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack = slot.getStack();
            ret = itemstack.copy();

            if (index < 36) {
                if (!this.mergeItemStack(itemstack, 36, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return ret;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return tile.canInteractWith(playerIn);
    }
}
