package com.example.examplemod.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import static net.minecraft.inventory.InventoryHelper.spawnItemStack;

public class BlockHelper {
    public static ActionResultType onBlockActivatedNormal(Block block, BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult res) {
        if (!world.isRemote) {
            INamedContainerProvider cont = block.getContainer(state, world, pos);
            if (cont != null) {
                entity.openContainer(cont);
                entity.addStat(Stats.OPEN_CHEST); // TODO
            }
            return ActionResultType.CONSUME;
        } else {
            return ActionResultType.SUCCESS;
        }
    }

    /**
     * Spawn an item block, preserving tile entity NBT.
     * @param worldIn
     * @param pos
     * @param block
     */
    public static void spawnTE(World worldIn, BlockPos pos, Block block) {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(block));
        CompoundNBT entityTag = worldIn.getTileEntity(pos).serializeNBT();
        entityTag.remove("x");
        entityTag.remove("y");
        entityTag.remove("z");
        stack.setTagInfo("tileNBT", entityTag);
        spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
    }

    /**
     * Restore tile entity NBT from an item stack.
     * @param worldIn
     * @param pos
     * @param stack
     */
    public static void restoreTE(World worldIn, BlockPos pos, ItemStack stack) {
        TileEntity tile = worldIn.getTileEntity(pos);
        CompoundNBT compound = stack.getChildTag("tileNBT");
        if (compound != null) {
            CompoundNBT orig = tile.serializeNBT();
            orig.merge(compound);
            tile.read(worldIn.getBlockState(pos), orig);
        }
    }
}
