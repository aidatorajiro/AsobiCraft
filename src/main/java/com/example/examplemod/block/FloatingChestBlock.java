package com.example.examplemod.block;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.helper.BlockHelper;
import com.example.examplemod.tileentity.CounterTileEntity;
import com.example.examplemod.tileentity.FloatingChestTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class FloatingChestBlock extends DirectedBlock implements ITileEntityProvider {
    public FloatingChestBlock() {
        super();
    }

    /*
        Tile entity
     */

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new FloatingChestTileEntity();
    }

    /*
        GUI
     */

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(ExampleMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    /*
        Partial Block
     */

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0, 0, 0, 1, 8.0/16.0, 1);
    }

    /*
        NBT Items
     */

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        if (fortune > 9) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        BlockHelper.restoreTE(world, pos, stack);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            BlockHelper.spawnTE(worldIn, pos, this);
        }
        super.breakBlock(worldIn, pos, state);
    }
}
