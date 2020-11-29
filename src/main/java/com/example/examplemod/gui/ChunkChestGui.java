package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.container.ChunkChestContainer;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class ChunkChestGui extends GuiContainer {
    private static ChunkChestTileEntity tile;

    private static int pageNo;

    private static final ResourceLocation background = new ResourceLocation(ExampleMod.MODID, "textures/gui/chunkchest.png");

    public ChunkChestGui(ChunkChestTileEntity tileIn, ChunkChestContainer container) {
        super(container);

        xSize = 253;
        ySize = 234;

        pageNo = 0;

        tile = tileIn;

        this.addButton(new GuiButton(1, 9, 70, "<"));
        this.addButton(new GuiButton(2, 59, 70, ">"));
        this.addButton(new GuiButton(3, 109, 70, "JUMP"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        if (ChunkChestContainer.chestSlotX <= mouseX && mouseX <= ChunkChestContainer.chestSlotX + 18
         && ChunkChestContainer.chestSlotY <= mouseY && mouseY <= ChunkChestContainer.chestSlotY + 18) {
            this.drawHoveringText("Put chests here to expand pages.", mouseX, mouseY);
        }
        drawString(fontRenderer, "Page " + (pageNo + 1) + " of " + tile.getHandler().getSlots()/27, mouseX, mouseY, 0xFFFFFFFF);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
