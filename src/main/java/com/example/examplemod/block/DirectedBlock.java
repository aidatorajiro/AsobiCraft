package com.example.examplemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class DirectedBlock extends BaseBlock {
    public static final DirectionProperty FACING = DirectionProperty.create("facing");

    public DirectedBlock() {
        super();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        world.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    //@Override TODO
    //public IBlockState getStateFromMeta(int meta) {
    //    return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    //}

    //@Override TODO
    //public int getMetaFromState(IBlockState state) {
    //    return state.getValue(FACING).getIndex();
    //}

    //@Override TODO
    //protected BlockStateContainer createBlockState() {
    //    return new BlockStateContainer(this, FACING);
    //}

    /**
     * Get the BlockPos in front of the block with given World and BlockPos.
     * @param world
     * @param pos
     * @return
     */
    public BlockPos getFrontBlockPos (World world, BlockPos pos) {
        BlockPos frontPos = pos;

        switch (world.getBlockState(pos).get(FACING)) {
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
