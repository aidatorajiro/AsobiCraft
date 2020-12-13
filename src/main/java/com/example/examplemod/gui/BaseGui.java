package com.example.examplemod.gui;

import com.example.examplemod.helper.GuiHelper;
import com.example.examplemod.packet.ModMessage;
import com.example.examplemod.packet.ModPacketHandler;
import com.example.examplemod.slot.FloatingSlot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import java.io.IOException;
import java.util.List;

public abstract class BaseGui extends GuiContainer {
    public BaseGui(BaseContainer inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    private FloatingSlot hoveredSlotFloating;

    public void drawItemStack(ItemStack itemstack, int x, int y) {
        drawItemStack(itemstack, x, y, "");
    }

    public void drawItemStack(ItemStack itemstack, int x, int y, String str) {
        this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, itemstack, x, y);
        this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemstack, x, y, str);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        } catch (IOException e) {
            // do nothing
        }
        List<FloatingSlot> floatingSlots
                = ((BaseContainer)inventorySlots).floatingSlots;
        for (int i = 0; i < floatingSlots.size(); i++) {
            FloatingSlot slot = floatingSlots.get(i);
            int x = slot.getX();
            int y = slot.getY();
            int baseX = width/2 - xSize/2;
            int baseY = height/2 - ySize/2;
            if (GuiHelper.collision(baseX + x, baseY + y, 18, 18, mouseX, mouseY)) {
                ModPacketHandler.INSTANCE.sendToServer(new ModMessage().floatingSlotMessage(i));
                ((BaseContainer) inventorySlots).floatingSlotClicked(i);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        hoveredSlotFloating = null;
        List<FloatingSlot> floatingSlots
                = ((BaseContainer)inventorySlots).floatingSlots;
        for (FloatingSlot slot : floatingSlots) {
            int x = slot.getX();
            int y = slot.getY();
            int baseX = width/2 - xSize/2;
            int baseY = height/2 - ySize/2;
            Double size = slot.getSize();
            String str;
            if (Double.isNaN(size)) {
                str = "?";
            } else if (Double.isInfinite(size)) {
                str = "!";
            } else if (size <= 0) {
                str = "@";
            } else {
                int index_scale = (int) Math.floor(Math.log10(size));
                str = Integer.toString(index_scale, 36);
            }
            ItemStack itemStack = slot.getItemStack();
            drawItemStack(
                    itemStack,
                    baseX + x,
                    baseY + y,
                    str
            );
            if (GuiHelper.collision(baseX + x, baseY + y, 18, 18, mouseX, mouseY)) {
                hoveredSlotFloating = slot;
            }
        }
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        super.renderHoveredToolTip(mouseX, mouseY);
        if (hoveredSlotFloating != null && !hoveredSlotFloating.isEmpty()) {
            List<String> list = hoveredSlotFloating.getTooltip(this.mc.player, () -> false);
            list.add("x " + hoveredSlotFloating.getSize());
            drawHoveringText(list, mouseX, mouseY);
        }
    }
}
