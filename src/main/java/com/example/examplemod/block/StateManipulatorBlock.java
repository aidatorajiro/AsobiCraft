package com.example.examplemod.block;

import com.example.examplemod.tileentity.StateManipulatorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StateManipulatorBlock extends DirectedBlock implements ITileEntityProvider {
    public StateManipulatorBlock() {
        super(Material.ROCK);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new StateManipulatorTileEntity();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            BlockPos frontPos = getFrontBlockPos(world, pos);
            Block block = world.getBlockState(frontPos).getBlock();
            int meta = ((StateManipulatorTileEntity) world.getTileEntity(pos)).increase();
            world.setBlockState(frontPos, block.getStateFromMeta(meta));
        }
        return true;
    }
}
