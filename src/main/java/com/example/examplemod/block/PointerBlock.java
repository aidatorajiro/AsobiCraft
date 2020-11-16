package com.example.examplemod.block;

import com.example.examplemod.tileentity.PointerTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PointerBlock extends DirectedBlock {
    public PointerBlock() {
        super();
    }

    /*
        Tile entity
     */

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PointerTileEntity();
    }
}
