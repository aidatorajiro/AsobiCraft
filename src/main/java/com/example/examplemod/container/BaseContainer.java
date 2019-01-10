package com.example.examplemod.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public abstract class BaseContainer extends Container {
    @Override
    public abstract boolean canInteractWith(EntityPlayer playerIn);
}
