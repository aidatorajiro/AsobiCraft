package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.container.CalculatorContainer;
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
        if (tile.canCalculate()) {
            drawString(fontRenderer, "Calculating: ", 20, 20, 0xffffff);
        } else {
            drawString(fontRenderer, "Set numbers and operators", 20, 20, 0xffffff);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
        super.drawScreen(mouseX, mouseY, f);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
