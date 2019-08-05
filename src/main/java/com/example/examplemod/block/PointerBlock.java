package com.example.examplemod.block;

import com.example.examplemod.tileentity.PointerTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PointerBlock extends DirectedBlock implements ITileEntityProvider {
    public PointerBlock() {
        super();
    }

    /*
        Tile entity
     */

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new PointerTileEntity();
    }
}
