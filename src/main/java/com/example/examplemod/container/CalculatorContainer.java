package com.example.examplemod.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class CalculatorContainer extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
