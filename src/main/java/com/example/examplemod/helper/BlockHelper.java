package com.example.examplemod.helper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.inventory.InventoryHelper.spawnItemStack;

public class BlockHelper {
    public static void spawnBlockWithNBT(World worldIn, BlockPos pos, Block block, TileEntity tile) {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(block));
        NBTTagCompound entityTag = tile.serializeNBT();
        entityTag.removeTag("x");
        entityTag.removeTag("y");
        entityTag.removeTag("z");
        stack.setTagInfo("tileNBT", entityTag);
        spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
    }

    public static void restoreTE(TileEntity tile, ItemStack stack) {
        NBTTagCompound compound = stack.getSubCompound("tileNBT");
        if (compound != null) {
            NBTTagCompound orig = tile.serializeNBT();
            orig.merge(compound);
            tile.readFromNBT(orig);
        }
    }
}
