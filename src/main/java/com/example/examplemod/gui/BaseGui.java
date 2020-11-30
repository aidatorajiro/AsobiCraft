package com.example.examplemod.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class BaseGui extends GuiContainer {
    public BaseGui(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }
}
