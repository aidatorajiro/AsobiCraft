package com.example.examplemod.gui;

import com.example.examplemod.itemhandler.FloatingItemStackHandler;
import com.example.examplemod.packet.ModMessage;
import com.example.examplemod.packet.ModPacketHandler;
import com.example.examplemod.slot.FloatingSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseContainer extends Container {
    @Override
    public abstract boolean canInteractWith(EntityPlayer playerIn);

    public List<FloatingSlot> floatingSlots = new ArrayList<>();

    public void drawFloatingSlots(FloatingItemStackHandler itemHandler, int offsetX, int offsetY, int offsetIndex, int shapeX, int shapeY) {
        int index = offsetIndex;
        for (int row = 0; row < shapeY; ++row) {
            for (int col = 0; col < shapeX; ++col) {
                int x = col * 18 + offsetX;
                int y = row * 18 + offsetY;
                floatingSlots.add(new FloatingSlot(itemHandler, index, x, y));
                index++;
            }
        }
    }

    public void drawFloatingSlots(FloatingItemStackHandler itemHandler, int offsetX, int offsetY, int shapeX, int shapeY) {
        drawFloatingSlots(itemHandler, offsetX, offsetY, 0, shapeX, shapeY);
    }

    public void clearSlots() {
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();
    }

    public void emptySlots() {
        for (int i = 36; i < this.inventoryItemStacks.size(); i++) {
            this.inventoryItemStacks.set(i, ItemStack.EMPTY);
            this.inventorySlots.get(i).getStack().setCount(0);
        }
    }

    public void drawSlots(IItemHandler itemHandler, int offsetX, int offsetY, int offsetIndex, int shapeX, int shapeY) {
        int index = offsetIndex;
        for (int row = 0; row < shapeY; ++row) {
            for (int col = 0; col < shapeX; ++col) {
                int x = col * 18 + offsetX;
                int y = row * 18 + offsetY;
                this.addSlotToContainer(new SlotItemHandler(itemHandler, index, x, y));
                index++;
            }
        }
    }

    public void drawSlots(IItemHandler itemHandler, int offsetX, int offsetY, int shapeX, int shapeY) {
        drawSlots(itemHandler, offsetX, offsetY, 0, shapeX, shapeY);
    }

    public void drawPlayerSlots(IInventory playerInventory, int offsetX, int offsetY) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = col * 18 + offsetX;
                int y = row * 18 + offsetY;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hot bar
        for (int row = 0; row < 9; ++row) {
            int x = row * 18 + offsetX;
            int y = 58 + offsetY;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack ret = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack = slot.getStack();
            ret = itemstack.copy();

            if (index < 36) {
                if (!mergeItemStack(itemstack, 36, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(itemstack, 0, 36, false)) {
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

    public void floatingSlotClicked(int i) {
        FloatingSlot slot = floatingSlots.get(i);
        ItemStack extracted = slot.getHandler().extractItem(slot.getIndex(), 64, false);
        mergeItemStack(extracted, 0, 36, false);
    }
}
