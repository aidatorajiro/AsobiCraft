package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.container.CalculatorContainer;
import com.example.examplemod.container.FloatingChestContainer;
import com.example.examplemod.tileentity.CalculatorTileEntity;
import com.example.examplemod.tileentity.FloatingChestTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FloatingChestGui extends ContainerScreen<FloatingChestContainer> {

    private static FloatingChestTileEntity tile;

    private static final ResourceLocation background = new ResourceLocation(ExampleMod.MODID, "textures/gui/floatingchest.png");

    public FloatingChestGui(FloatingChestContainer container, PlayerInventory inv, FloatingChestTileEntity tileIn) {
        super(container, inv, new TranslationTextComponent("message.examplemod.floatingchesttitle"));

        xSize = 178;
        ySize = 234;

        tile = tileIn;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(background);
    }
}
