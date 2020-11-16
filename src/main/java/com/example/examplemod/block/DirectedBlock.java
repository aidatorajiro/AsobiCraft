package com.example.examplemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class DirectedBlock extends BaseBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    public DirectedBlock() {
        super();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
    }

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
