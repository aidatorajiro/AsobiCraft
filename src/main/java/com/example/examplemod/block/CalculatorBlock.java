package com.example.examplemod.block;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.helper.BlockHelper;
import com.example.examplemod.tileentity.CalculatorTileEntity;
import com.example.examplemod.tileentity.StateManipulatorTileEntity;
import com.example.examplemod.helper.ItemHandlerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class CalculatorBlock extends BaseBlock implements ITileEntityProvider {
    public CalculatorBlock() {
        super(Material.ROCK);
        this.setTickRandomly(true);
        this.setHardness(1.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new CalculatorTileEntity();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(ExampleMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public int tickRate(World worldIn) {
        return 10;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        CalculatorTileEntity tile = (CalculatorTileEntity) world.getTileEntity(pos);
        tile.updateTick(world, pos, state, rand);
    }

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
        return new AxisAlignedBB(1.0/16.0, 0, 1.0/16.0, 15.0/16.0, 2.0/16.0, 15.0/16.0);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        if (fortune > 5) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        BlockHelper.restoreTE(world.getTileEntity(pos), stack);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            BlockHelper.spawnBlockWithNBT(worldIn, pos, this, worldIn.getTileEntity(pos));
        }
        super.breakBlock(worldIn, pos, state);
    }
}
