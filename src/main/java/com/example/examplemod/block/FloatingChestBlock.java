package com.example.examplemod.block;

import com.example.examplemod.tileentity.FloatingChestTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FloatingChestBlock extends DirectedBlock implements ITileEntityProvider {
    public FloatingChestBlock() {
        super(Material.ROCK);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new FloatingChestTileEntity();
    }
}
