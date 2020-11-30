package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.packet.ModMessage;
import com.example.examplemod.packet.ModPacketHandler;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class ChunkChestGui extends GuiContainer {
    private static ChunkChestTileEntity tile;
    private static ChunkChestContainer container;
    private static GuiTextField jumpTo;

    private static final ResourceLocation background = new ResourceLocation(ExampleMod.MODID, "textures/gui/chunkchest.png");

    @Override
    public void initGui() {
        super.initGui();
        int baseX = width/2 - xSize/2;
        int baseY = height/2 - ySize/2;
        int bw = 20;
        int bh = 20;
        this.addButton(new GuiButton(1, baseX + 9             , baseY + 70, bw, bh, "<"));
        this.addButton(new GuiButton(2, baseX + xSize - bw - 9, baseY + 70, bw, bh, ">"));
        this.addButton(new GuiButton(3, baseX + xSize - bw - 40, baseY + 70, bw, bh, "JUMP"));
        jumpTo = new GuiTextField(4, this.fontRenderer, baseX + 40, baseY + 70, xSize - bw - 90,20);
        jumpTo.setMaxStringLength(4);
        jumpTo.setText("1");
        jumpTo.setFocused(true);
        jumpTo.setValidator((x) -> x.matches("\\d*"));
    }

    public ChunkChestGui(ChunkChestTileEntity tileIn, ChunkChestContainer containerIn) {
        super(containerIn);

        xSize = 178;
        ySize = 234;

        tile = tileIn;
        container = containerIn;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        int pageNo = tile.getPageNo(player);
        switch (button.id) {
            case 1:
                pageNo -= 1;
                break;
            case 2:
                pageNo += 1;
                break;
            case 3:
                if (jumpTo.getText().equals("")) {
                    return;
                } else {
                    pageNo = Integer.parseInt(jumpTo.getText()) - 1;
                }
                break;
        }
        pageNo = Math.min(Math.max(pageNo, 0), tile.getHandlerSize()/27 - 1);
        ModPacketHandler.INSTANCE.sendToServer(new ModMessage().chunkChestMessage(tile.getPos(), pageNo, -1));
        container.emptyItemStacks();
        tile.setPageNo(Minecraft.getMinecraft().player, pageNo);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        jumpTo.updateCursorCounter();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        super.drawScreen(mouseX, mouseY, partialTicks);
        int relX = mouseX - width/2 + xSize/2;
        int relY = mouseY - height/2 + ySize/2;
        renderHoveredToolTip(mouseX, mouseY);
        if (ChunkChestContainer.chestSlotX <= relX && relX <= ChunkChestContainer.chestSlotX + 18
         && ChunkChestContainer.chestSlotY <= relY && relY <= ChunkChestContainer.chestSlotY + 18) {
            this.drawHoveringText("Put chests here to expand pages.", mouseX, mouseY);
        }
        drawString(fontRenderer, "Page " + (tile.getPageNo(player) + 1) + " of " + tile.getHandlerSize()/27, mouseX, mouseY, 0xFFFFFF);
        jumpTo.drawTextBox();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        jumpTo.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
