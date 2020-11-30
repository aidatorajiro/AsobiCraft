package com.example.examplemod.gui;

import com.example.examplemod.itemhandler.FloatingItemStack;
import com.example.examplemod.itemhandler.FloatingItemStackHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import scala.Tuple4;

import java.util.List;

import static com.example.examplemod.gui.ChunkChestContainer.chestSlotX;
import static com.example.examplemod.gui.ChunkChestContainer.chestSlotY;

public abstract class BaseGui extends GuiContainer {
    public BaseGui(BaseContainer inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    public void drawItemStack(ItemStack itemstack, int x, int y) {
        drawItemStack(itemstack, x, y, "");
    }

    public void drawItemStack(ItemStack itemstack, int x, int y, String str) {
        this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, itemstack, x, y);
        this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemstack, x, y, str);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        List<Tuple4<FloatingItemStackHandler, Integer, Integer, Integer>> floatingSlots
                = ((BaseContainer)inventorySlots).floatingSlots;
        for (Tuple4<FloatingItemStackHandler, Integer, Integer, Integer> tup : floatingSlots) {
            FloatingItemStackHandler handler = tup._1();
            int index = tup._2();
            int x = tup._3();
            int y = tup._4();
            int baseX = width/2 - xSize/2;
            int baseY = height/2 - ySize/2;
            FloatingItemStack stack = handler.getStackInSlotFloating(index);
            Double size = stack.getStackSize();
            String str;
            if (Double.isNaN(size)) {
                str = "?";
            } else if (Double.isInfinite(size)) {
                str = "!";
            } else {
                int index_scale = (int) Math.floor(Math.max(Math.log10(Math.abs(stack.getStackSize())), 0));
                str = Integer.toString(index_scale, 36);
            }
            if (size < 0) {
                str = "-" + str;
            }
            drawItemStack(
                    stack.getItemStack(),
                    baseX + x,
                    baseY + y,
                    str
            );
        }
    }
}
