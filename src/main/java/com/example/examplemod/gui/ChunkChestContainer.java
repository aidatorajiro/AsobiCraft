package com.example.examplemod.gui;

import com.example.examplemod.tileentity.ChunkChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ChunkChestContainer extends BaseContainer {
    private ChunkChestTileEntity tile;
    public static int chestSlotX = 135;
    public static int chestSlotY = 88;
    private IInventory playerInventory;
    private EntityPlayer player;

    public ChunkChestContainer(EntityPlayer player, ChunkChestTileEntity tile) {
        this.tile = tile;
        this.playerInventory = player.inventory;
        this.player = player;
        redraw();
        this.addSlotToContainer(new SlotItemHandler(tile.getHandlerChest(), 0, chestSlotX, chestSlotY));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canInteractWith(playerIn);
    }

    public void redraw() {
        clearSlots();
        drawPlayerSlots(playerInventory, 9, 151);
        drawSlots(tile.getHandler(), 9, 16, 27*tile.getPageNo(player), 9, 3);
    }
}
