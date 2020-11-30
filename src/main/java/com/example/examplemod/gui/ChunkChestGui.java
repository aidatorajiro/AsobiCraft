package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.helper.GuiHelper;
import com.example.examplemod.packet.ModMessage;
import com.example.examplemod.packet.ModPacketHandler;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Arrays;

import static com.example.examplemod.gui.ChunkChestContainer.chestSlotX;
import static com.example.examplemod.gui.ChunkChestContainer.chestSlotY;

public class ChunkChestGui extends BaseGui {
    private static ChunkChestTileEntity tile;
    private static ChunkChestContainer container;
    private static GuiTextField jumpTo;
    private static int jumpToX = 40;
    private static int jumpToY = 70;
    private static int jumpToW;
    private static int jumpToH = 20;

    private static final ResourceLocation background = new ResourceLocation(ExampleMod.MODID, "textures/gui/chunkchest.png");
    private static ItemStack chestItemStack;

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
        jumpToW = xSize - bw - 90;
        jumpTo = new GuiTextField(4, this.fontRenderer, baseX + jumpToX, baseY + jumpToY, jumpToW, jumpToH);
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

        chestItemStack = new ItemStack(Blocks.CHEST);
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
        container.emptySlots();
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

        //coordinate calculation
        int baseX = width/2 - xSize/2;
        int baseY = height/2 - ySize/2;
        int relX = mouseX - baseX;
        int relY = mouseY - baseY;

        this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, chestItemStack, baseX + chestSlotX - 42, baseY + chestSlotY);
        this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, chestItemStack, baseX + chestSlotX - 42, baseY + chestSlotY, "");

        // page no and text input
        drawString(fontRenderer, "Page " + (tile.getPageNo(player) + 1) + " of " + tile.getHandlerSize()/27, baseX + 6, baseY + 5, 0xF0F0F0);
        jumpTo.drawTextBox();

        //hovering
        renderHoveredToolTip(mouseX, mouseY);
        if (GuiHelper.collision(chestSlotX, chestSlotY,
                                18, 18, relX, relY)) {
            this.drawHoveringText("Put chests here to expand pages.", mouseX, mouseY);
        }
        if (GuiHelper.collision(jumpToX, jumpToY, jumpToW, jumpToH, relX, relY)) {
            this.drawHoveringText(Arrays.asList("Type the page number", "you want to jump to."), mouseX, mouseY);
        }
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
