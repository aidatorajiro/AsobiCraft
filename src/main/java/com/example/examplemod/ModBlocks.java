package com.example.examplemod;

import com.example.examplemod.block.BaseBlock;
import com.example.examplemod.block.CounterBlock;
import com.example.examplemod.block.ExampleBlock;
import com.example.examplemod.block.StateManipulatorBlock;
import com.example.examplemod.tileentity.BaseTileEntity;
import com.example.examplemod.tileentity.CounterTileEntity;
import com.example.examplemod.tileentity.StateManipulatorTileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A registry for blocks and tile entities.
 */
public class ModBlocks {
    public static List<BaseBlock> blocks = new ArrayList();
    public static List<Class<?extends BaseTileEntity>> tileEntities = new ArrayList();
    public static List<String> tileEntityNames = new ArrayList();

    public static ExampleBlock exampleBlock;
    public static CounterBlock counterBlock;
    public static StateManipulatorBlock stateManipulatorBlock;

    /**
     * Create and register all blocks to ModBlocks.
     */
    public static void initialize() {
        exampleBlock = new ExampleBlock();
        registerBlock(exampleBlock, "exampleblock");

        counterBlock = new CounterBlock();
        registerBlock(counterBlock, "counterblock");
        registerTileEntity(CounterTileEntity.class, "countertileentity");

        stateManipulatorBlock = new StateManipulatorBlock();
        registerBlock(stateManipulatorBlock, "statemanipulatorblock");
        registerTileEntity(StateManipulatorTileEntity.class, "statemanipulatortileentity");
    }

    /**
     * Register a block to ModBlocks
     * @param block A block instance to add.
     * @param id Unique identifier for the block.
     */
    private static void registerBlock (BaseBlock block, String id) {
        block.setRegistryName(id);
        block.setUnlocalizedName(ExampleMod.MODID + "." + id);
        blocks.add(block);
    }

    /**
     * Register a tile entity to ModBlocks
     * @param tileEntityClass A tile entity instance to add.
     * @param id Unique identifier for the tile entity.
     */
    private static void registerTileEntity(Class<?extends BaseTileEntity> tileEntityClass, String id) {
        tileEntities.add(tileEntityClass);
        tileEntityNames.add(id);
    }
}
