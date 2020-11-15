package com.example.examplemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BaseBlock extends Block {
    public BaseBlock() {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0f, 1.0f));
    }
}
