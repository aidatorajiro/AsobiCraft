package com.example.examplemod.container;

import com.example.examplemod.slot.FloatingSlot;
import com.example.examplemod.itemhandler.FloatingItemStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public abstract class BaseContainer extends Container {
    @Override
    public abstract boolean canInteractWith(EntityPlayer playerIn);

    public void drawFloatingSlots(FloatingItemStackHandler itemHandler, int offsetX, int offsetY, int shapeX) {
        int index = 0;
        for (int row = 0; row < (itemHandler.getSlots() / shapeX) + 1; ++row) {
            int num_slots = row == (itemHandler.getSlots() / shapeX) ? itemHandler.getSlots() % shapeX : shapeX;
            for (int col = 0; col < num_slots; ++col) {
                int x = col * 18 + offsetX;
                int y = row * 18 + offsetY;
                this.addSlotToContainer(new FloatingSlot(itemHandler, index, x, y));
                index++;
            }
        }
    }

    public void drawFloatingSlots(FloatingItemStackHandler itemHandler, int offsetX, int offsetY, int shapeX, int shapeY) {
        int index = 0;
        for (int row = 0; row < shapeY; ++row) {
            for (int col = 0; col < shapeX; ++col) {
                int x = col * 18 + offsetX;
                int y = row * 18 + offsetY;
                this.addSlotToContainer(new FloatingSlot(itemHandler, index, x, y));
                index++;
            }
        }
    }

    /**
     * Draw slots of given IItemHandler, only using the number of column of slots.
     * If the length of itemHandler is 13 and shapeX == 5, slots will look like this:
     * .....
     * .....
     * ...
     * @param itemHandler
     * @param offsetX
     * @param offsetY
     * @param shapeX
     */
    public void drawSlots(IItemHandler itemHandler, int offsetX, int offsetY, int shapeX) {
        int index = 0;
        for (int row = 0; row < (itemHandler.getSlots() / shapeX) + 1; ++row) {
            int num_slots = row == (itemHandler.getSlots() / shapeX) ? itemHandler.getSlots() % shapeX : shapeX;
            for (int col = 0; col < num_slots; ++col) {
                int x = col * 18 + offsetX;
                int y = row * 18 + offsetY;
                this.addSlotToContainer(new SlotItemHandler(itemHandler, index, x, y));
                index++;
            }
        }
    }

    public void drawSlots(IItemHandler itemHandler, int offsetX, int offsetY, int shapeX, int shapeY) {
        int index = 0;
        for (int row = 0; row < shapeY; ++row) {
            for (int col = 0; col < shapeX; ++col) {
                int x = col * 18 + offsetX;
                int y = row * 18 + offsetY;
                this.addSlotToContainer(new SlotItemHandler(itemHandler, index, x, y));
                index++;
            }
        }
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

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = row * 18 + offsetX;
            int y = 58 + offsetY;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }
}
