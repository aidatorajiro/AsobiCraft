package com.example.examplemod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public abstract class BaseContainer extends Container {
    protected BaseContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }

    @Override
    public abstract boolean canInteractWith(PlayerEntity playerIn);

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
                this.addSlot(new SlotItemHandler(itemHandler, index, x, y));
                index++;
            }
        }
    }

    /**
     * Draw slots of given IItemHandler, using the number of columns and rows of slots.
     * @param itemHandler
     * @param offsetX
     * @param offsetY
     * @param shapeX
     * @param shapeY
     */
    public void drawSlots(IItemHandler itemHandler, int offsetX, int offsetY, int shapeX, int shapeY) {
        int index = 0;
        for (int row = 0; row < shapeY; ++row) {
            for (int col = 0; col < shapeX; ++col) {
                int x = col * 18 + offsetX;
                int y = row * 18 + offsetY;
                this.addSlot(new SlotItemHandler(itemHandler, index, x, y));
                index++;
            }
        }
    }

    /**
     * Draw slots of player inventory
     * @param offsetX
     * @param offsetY
     */
    public void drawPlayerSlots(IInventory playerInventory, int offsetX, int offsetY) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = col * 18 + offsetX;
                int y = row * 18 + offsetY;
                this.addSlot(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = row * 18 + offsetX;
            int y = 58 + offsetY;
            this.addSlot(new Slot(playerInventory, row, x, y));
        }
    }
}
