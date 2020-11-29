package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.tileentity.CalculatorTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class CalculatorGui extends GuiContainer {

    private static CalculatorTileEntity tile;

    private static final ResourceLocation background = new ResourceLocation(ExampleMod.MODID, "textures/gui/calculator.png");

    public CalculatorGui(CalculatorTileEntity tileIn, CalculatorContainer container) {
        super(container);

        xSize = 253;
        ySize = 234;

        tile = tileIn;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
