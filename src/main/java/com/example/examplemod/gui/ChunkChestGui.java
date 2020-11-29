package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.container.ChunkChestContainer;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class ChunkChestGui extends GuiContainer {
    private static ChunkChestTileEntity tile;

    private static final ResourceLocation background = new ResourceLocation(ExampleMod.MODID, "textures/gui/floatingchest.png");

    public ChunkChestGui(ChunkChestTileEntity tileIn, ChunkChestContainer container) {
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
