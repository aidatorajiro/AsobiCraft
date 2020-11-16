package com.example.examplemod.block;

import com.example.examplemod.tileentity.StateManipulatorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class StateManipulatorBlock extends DirectedBlock {
    public StateManipulatorBlock() {
        super();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new StateManipulatorTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult res) {
        if (!world.isRemote) {
            try {
                BlockPos frontPos = getFrontBlockPos(world, pos);
                Block block = world.getBlockState(frontPos).getBlock();
                int meta = ((StateManipulatorTileEntity) world.getTileEntity(pos)).increase();
                world.setBlockState(frontPos, getStateById(meta));
            } catch (Exception e) {
                entity.sendStatusMessage(new TranslationTextComponent("message.examplemod.statemanipulator", e.getMessage()),false);
            }
            return ActionResultType.CONSUME;
        } else {
            return ActionResultType.SUCCESS;
        }
    }
}
