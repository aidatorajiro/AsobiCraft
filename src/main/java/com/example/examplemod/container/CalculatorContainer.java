package com.example.examplemod.container;

import com.example.examplemod.helper.GuiHelper;
import com.example.examplemod.tileentity.CalculatorTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CalculatorContainer extends BaseContainer {
    private CalculatorTileEntity tile;

    public CalculatorContainer (IInventory playerInventory, CalculatorTileEntity tile) {
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
            addSlotToContainer(new SlotItemHandler(itemHandler, i, x, y));
        }
    }

    private void drawOutputs() {
        IItemHandler itemHandler = this.tile.getOutput();
        if (itemHandler == null) { return; }
        drawSlots(itemHandler, 174, 9, 4, 12);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return GuiHelper.transferStackInSlotDefault(this, this::mergeItemStack, playerIn, index);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canInteractWith(playerIn);
    }
}
