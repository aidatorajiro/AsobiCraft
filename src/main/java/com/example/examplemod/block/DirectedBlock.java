package com.example.examplemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class DirectedBlock extends BaseBlock {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public DirectedBlock() {
        super();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    /**
     * Get the BlockPos in front of the block with given World and BlockPos.
     * @param world
     * @param pos
     * @return
     */
    public BlockPos getFrontBlockPos (World world, BlockPos pos) {
        BlockPos frontPos = pos;

        switch (world.getBlockState(pos).getValue(FACING)) {
            case UP: frontPos = frontPos.add(0, 1, 0);
                break;
            case DOWN: frontPos = frontPos.add(0, -1, 0);
                break;
            case WEST: frontPos = frontPos.add(-1, 0, 0);
                break;
            case EAST: frontPos = frontPos.add(1, 0, 0);
                break;
            case NORTH: frontPos = frontPos.add(0, 0, -1);
                break;
            case SOUTH: frontPos = frontPos.add(0, 0, 1);
                break;
        }
        return frontPos;
    }
}
