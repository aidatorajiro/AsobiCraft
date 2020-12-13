package com.example.examplemod.slot;

import com.example.examplemod.itemhandler.FloatingItemStackHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;

import java.util.List;

public class FloatingSlot {
    private final FloatingItemStackHandler handler;
    private final Integer index;
    private final int x;
    private final int y;

    public FloatingItemStackHandler getHandler() {
        return handler;
    }

    public boolean isEmpty() {
        return handler.getStackInSlotFloating(index).isEmpty();
    }

    public Double getSize() {
        return handler.getStackInSlotFloating(index).getStackSize();
    }

    public Integer getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FloatingSlot (FloatingItemStackHandler handler, Integer index, int x, int y) {
        this.handler = handler;
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public List<String> getTooltip(EntityPlayerSP player, ITooltipFlag flag) {
        return handler.getStackInSlot(index).getTooltip(player, flag);
    }

    public ItemStack getItemStack() {
        return handler.getStackInSlotFloating(index).getItemStack();
    }
}
