package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.container.FloatingChestContainer;
import com.example.examplemod.tileentity.FloatingChestTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class FloatingChestGui extends GuiContainer {

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
        // drawCenteredString(fontRenderer, String.valueOf(tile.getHandler().getStackInSlotFloating(0).getStackSize()), 10, 10, 0x000000);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
