package com.example.examplemod.container;

import com.example.examplemod.tileentity.CalculatorTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CalculatorContainer extends BaseContainer {
    private CalculatorTileEntity tile;

    public CalculatorContainer (IInventory playerInventory, CalculatorTileEntity tile) {
        this.tile = tile;
        addPlayerSlots(playerInventory);
        addOwnSlots();
    }

    private void addPlayerSlots(IInventory playerInventory) {
        int offsetY = 151;
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 9 + col * 18;
                int y = row * 18 + offsetY;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 9 + row * 18;
            int y = 58 + offsetY;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnSlots() {
        IItemHandler itemHandler = this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int centerX = 88;
        int centerY = 73;
        int radius = 60;
        double theta = 2*Math.PI/16.0;

        // Add our own slots
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            int x = (int)(-Math.sin(theta*i)*radius + centerX - 8);
            int y = (int)(Math.cos(theta*i)*radius + centerY - 8);
            addSlotToContainer(new SlotItemHandler(itemHandler, i, x, y));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canInteractWith(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return super.transferStackInSlot(playerIn, index);
    }
}
