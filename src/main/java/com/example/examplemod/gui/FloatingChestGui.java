package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.tileentity.FloatingChestTileEntity;
import net.minecraft.util.ResourceLocation;

public class FloatingChestGui extends BaseGui {

    private static FloatingChestTileEntity tile;

    private static final ResourceLocation background = new ResourceLocation(ExampleMod.MODID, "textures/gui/floatingchest.png");

    public FloatingChestGui(FloatingChestTileEntity tileIn, FloatingChestContainer container) {
        super(container);

        xSize = 178;
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
