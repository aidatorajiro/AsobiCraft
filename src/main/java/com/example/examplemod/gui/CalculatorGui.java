package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.container.CalculatorContainer;
import com.example.examplemod.tileentity.CalculatorTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class CalculatorGui extends GuiContainer {

    private static final ResourceLocation background = new ResourceLocation(ExampleMod.MODID, "textures/gui/calculator.png");

    public CalculatorGui(CalculatorTileEntity tile, CalculatorContainer container) {
        super(container);

        xSize = 176;
        ySize = 228;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
