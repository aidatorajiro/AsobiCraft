package com.example.examplemod;

import com.example.examplemod.block.*;
import com.example.examplemod.eventhandler.ModEventHandler;
import com.example.examplemod.item.AdbmalItem;
import com.example.examplemod.item.BaseItem;
import com.example.examplemod.item.NumberItem;
import com.example.examplemod.item.OperatorItem;
import com.example.examplemod.recipe.IBlockPatternRecipe;
import com.example.examplemod.recipe.BlockPatternRecipePlane;
import com.example.examplemod.tileentity.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.fml.common.registry.GameRegistry.registerTileEntity;

/**
 * A registry for mod objects.
 */
public class ModObjects {
    public static List<BaseBlock> blocks = new ArrayList();
    public static List<Class<?extends BaseTileEntity>> tileEntities = new ArrayList();
    public static List<String> tileEntityNames = new ArrayList();
    public static List<BaseItem> items = new ArrayList();
    public static List<IBlockPatternRecipe> pickupRecipes = new ArrayList();

    public static final CreativeTabs tabExampleMod = (new CreativeTabs("tabExampleMod") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(adbmalItem);
        }
    });

    public static ExampleBlock exampleBlock;
    public static CounterBlock counterBlock;
    public static StateManipulatorBlock stateManipulatorBlock;
    public static CalculatorBlock calculatorBlock;
    public static FloatingChestBlock floatingChestBlock;
    public static ChunkChestBlock chunkChestBlock;
    public static PointerBlock pointerBlock;

    public static AdbmalItem adbmalItem;
    public static OperatorItem plusOperatorItem;
    public static OperatorItem minusOperatorItem;
    public static OperatorItem divOperatorItem;
    public static OperatorItem mulOperatorItem;
    public static NumberItem numberItem;

    /**
     * Create and register all items to ModObjects.
     */
    public static void initialize() {
        // Register Item
        adbmalItem = new AdbmalItem();
        registerItem(adbmalItem, "adbmalitem");

        plusOperatorItem = new OperatorItem((a, b) -> (a + b));
        registerItem(plusOperatorItem, "plusoperatoritem");

        minusOperatorItem = new OperatorItem((a, b) -> (a - b));
        registerItem(minusOperatorItem, "minusoperatoritem");

        divOperatorItem = new OperatorItem((a, b) -> (a / b));
        registerItem(divOperatorItem, "divoperatoritem");

        mulOperatorItem = new OperatorItem((a, b) -> (a * b));
        registerItem(mulOperatorItem, "muloperatoritem");

        numberItem = new NumberItem();
        registerItem(numberItem, "numberitem");

        // Register Block and TileEntity
        exampleBlock = new ExampleBlock();
        registerBlock(exampleBlock, "exampleblock");

        counterBlock = new CounterBlock();
        registerBlock(counterBlock, "counterblock");
        registerTileEntity(CounterTileEntity.class, "countertileentity");

        stateManipulatorBlock = new StateManipulatorBlock();
        registerBlock(stateManipulatorBlock, "statemanipulatorblock");
        registerTileEntity(StateManipulatorTileEntity.class, "statemanipulatortileentity");

        calculatorBlock = new CalculatorBlock();
        registerBlock(calculatorBlock, "calculatorblock");
        registerTileEntity(CalculatorTileEntity.class, "calculatortileentity");

        floatingChestBlock = new FloatingChestBlock();
        registerBlock(floatingChestBlock, "floatingchestblock");
        registerTileEntity(FloatingChestTileEntity.class, "floatingchesttileentity");

        chunkChestBlock = new ChunkChestBlock();
        registerBlock(chunkChestBlock, "chunkchestblock");
        registerTileEntity(ChunkChestTileEntity.class, "chunkchesttileentity");

        pointerBlock = new PointerBlock();
        registerBlock(pointerBlock, "pointerblock");
        registerTileEntity(PointerTileEntity.class, "pointertileentity");

        // Register EventHandler
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
    }

    public static void afterRegisterItems() {
        // Register pickupRecipes
        pickupRecipes.add(new BlockPatternRecipePlane(
                Items.WOODEN_HOE,
                1,
                0,
                1,
                NonNullList.withSize(1, new ItemStack(adbmalItem, 3)),
                "GGG",
                "GWG",
                "GGG",
                'G', Blocks.GLASS,
                'W', Blocks.WATER
        ));

        pickupRecipes.add(new BlockPatternRecipePlane(
                adbmalItem,
                1,
                0,
                1,
                NonNullList.withSize(1, new ItemStack(counterBlock, 10)),
                "RRR",
                "RAR",
                "RRR",
                'R', Blocks.STONE,
                'A', Blocks.AIR
        ));

        pickupRecipes.add(new BlockPatternRecipePlane(
                adbmalItem,
                1,
                0,
                1,
                NonNullList.withSize(1, new ItemStack(exampleBlock, 64)),
                "TTT",
                "TAT",
                "TTT",
                'T', Blocks.TORCH,
                'A', Blocks.AIR
        ));

        pickupRecipes.add(new BlockPatternRecipePlane(
                adbmalItem,
                1,
                0,
                1,
                NonNullList.withSize(1, new ItemStack(calculatorBlock, 1)),
                "CEC",
                "EAE",
                "CEC",
                'C', counterBlock,
                'A', Blocks.AIR,
                'E', exampleBlock
        ));

        pickupRecipes.add(new BlockPatternRecipePlane(
                adbmalItem,
                1,
                0,
                1,
                NonNullList.withSize(1, new ItemStack(floatingChestBlock, 1)),
                "DDD",
                "DWD",
                "DDD",
                'D', Blocks.DIAMOND_BLOCK,
                'W', Blocks.WATER
        ));

        pickupRecipes.add(new BlockPatternRecipePlane(
                adbmalItem,
                1,
                2,
                1,
                NonNullList.withSize(1, new ItemStack(plusOperatorItem, 64)),
                "AEA",
                "EEE",
                "AEA",
                'E', exampleBlock,
                'A', Blocks.AIR
        ));

        pickupRecipes.add(new BlockPatternRecipePlane(
                adbmalItem,
                1,
                2,
                1,
                NonNullList.withSize(1, new ItemStack(mulOperatorItem, 64)),
                "EAE",
                "AEA",
                "EAE",
                'E', exampleBlock,
                'A', Blocks.AIR
        ));

        pickupRecipes.add(new BlockPatternRecipePlane(
                adbmalItem,
                1,
                2,
                1,
                NonNullList.withSize(1, new ItemStack(divOperatorItem, 64)),
                "AAE",
                "AEA",
                "EAA",
                'E', exampleBlock,
                'A', Blocks.AIR
        ));

        pickupRecipes.add(new BlockPatternRecipePlane(
                adbmalItem,
                1,
                2,
                1,
                NonNullList.withSize(1, new ItemStack(minusOperatorItem, 64)),
                "AAA",
                "EEE",
                "AAA",
                'E', exampleBlock,
                'A', Blocks.AIR
        ));

        pickupRecipes.add(new BlockPatternRecipePlane(
                adbmalItem,
                1,
                0,
                1,
                NonNullList.withSize(1, new ItemStack(numberItem, 64)),
                "EEE",
                "EAE",
                "EEE",
                'E', exampleBlock,
                'A', Blocks.AIR
        ));
    }

    /**
     * Register an item to ModObjects.
     * @param item A item instance to add.
     * @param id Unique identifier for the item.
     */
    private static void registerItem (BaseItem item, String id) {
        item.setRegistryName(id);
        item.setUnlocalizedName(ExampleMod.MODID + "." + id);
        item.setCreativeTab(ModObjects.tabExampleMod);
        items.add(item);
    }

    /**
     * Register a block to ModObjects.
     * @param block A block instance to add.
     * @param id Unique identifier for the block.
     */
    private static void registerBlock (BaseBlock block, String id) {
        block.setRegistryName(id);
        block.setUnlocalizedName(ExampleMod.MODID + "." + id);
        block.setCreativeTab(ModObjects.tabExampleMod);
        blocks.add(block);
    }

    /**
     * Register a tile entity to ModObjects.
     * @param tileEntityClass A tile entity instance to add.
     * @param id Unique identifier for the tile entity.
     */
    private static void registerTileEntity(Class<?extends BaseTileEntity> tileEntityClass, String id) {
        tileEntities.add(tileEntityClass);
        tileEntityNames.add(id);
    }
}
