package com.example.examplemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BaseBlock extends Block {
    public BaseBlock() {
        super(Material.ROCK);
        this.setHardness(1.0F);
    }
}
