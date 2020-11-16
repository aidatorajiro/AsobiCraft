package com.example.examplemod;

import com.example.examplemod.block.*;
import com.example.examplemod.eventhandler.BlockPatternRecipeEventHandler;
import com.example.examplemod.item.AdbmalItem;
import com.example.examplemod.item.BaseItem;
import com.example.examplemod.item.NumberItem;
import com.example.examplemod.item.OperatorItem;
import com.example.examplemod.recipe.IBlockPatternRecipe;
import com.example.examplemod.recipe.BlockPatternRecipePlane;
import com.example.examplemod.tileentity.*;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;

import javax.management.AttributeList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A registry for mod objects.
 */
public class ModObjects {
    public static List<BaseBlock> blocks = new ArrayList();
    public static List<TileEntityType<?extends BaseTileEntity>> tileEntityTypes = new ArrayList();
    public static List<String> tileEntityNames = new ArrayList();
    public static List<BaseItem> items = new ArrayList();
    public static List<IBlockPatternRecipe> pickupRecipes = new ArrayList();

    public static final ItemGroup itemGroupExampleMod = new ItemGroup("tabExampleMod") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(adbmalItem);
        }
    };

    public static TileEntityType<?extends BaseTileEntity> counterType;
    public static TileEntityType<?extends BaseTileEntity> stateManipulatorType;
    public static TileEntityType<?extends BaseTileEntity> calculatorType;
    public static TileEntityType<?extends BaseTileEntity> floatingChestType;
    public static TileEntityType<?extends BaseTileEntity> pointerType;

    public static ExampleBlock exampleBlock;
    public static CounterBlock counterBlock;
    public static StateManipulatorBlock stateManipulatorBlock;
    public static CalculatorBlock calculatorBlock;
    public static FloatingChestBlock floatingChestBlock;
    public static PointerBlock pointerBlock;

    public static AdbmalItem adbmalItem;
    public static OperatorItem plusOperatorItem;
    public static OperatorItem minusOperatorItem;
    public static OperatorItem divOperatorItem;
    public static OperatorItem mulOperatorItem;
    public static NumberItem numberItem;

    public static boolean initialized = false;

    /**
     * Create and register all items to ModObjects.
     */
    public static void initialize() {
        if (initialized) {
            return;
        }

        initialized = true;

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
        counterType = generateType(CounterTileEntity::new, counterBlock);
        registerTileEntity(counterType, "countertileentity");

        stateManipulatorBlock = new StateManipulatorBlock();
        registerBlock(stateManipulatorBlock, "statemanipulatorblock");
        stateManipulatorType = generateType(StateManipulatorTileEntity::new, stateManipulatorBlock);
        registerTileEntity(stateManipulatorType, "statemanipulatortileentity");

        calculatorBlock = new CalculatorBlock();
        registerBlock(calculatorBlock, "calculatorblock");
        calculatorType = generateType(CalculatorTileEntity::new, calculatorBlock);
        registerTileEntity(calculatorType, "calculatortileentity");

        floatingChestBlock = new FloatingChestBlock();
        registerBlock(floatingChestBlock, "floatingchestblock");
        floatingChestType = generateType(FloatingChestTileEntity::new, floatingChestBlock);
        registerTileEntity(floatingChestType, "floatingchesttileentity");

        pointerBlock = new PointerBlock();
        registerBlock(pointerBlock, "pointerblock");
        pointerType = generateType(PointerTileEntity::new, pointerBlock);
        registerTileEntity(pointerType, "pointertileentity");

        // Register EventHandler
        MinecraftForge.EVENT_BUS.register(new BlockPatternRecipeEventHandler());

        // Register pickupRecipes
        pickupRecipes.add(new BlockPatternRecipePlane(
                Items.WOODEN_HOE,
                1,
                0,
                1,
                NonNullList.withSize(1, new ItemStack(adbmalItem, 3)),
                3, 3,
                NonNullList.from(
                        Ingredient.fromItems(Items.GLASS),
                        Ingredient.fromItems(Items.GLASS),
                        Ingredient.fromItems(Items.GLASS),
                        Ingredient.fromItems(Items.GLASS),
                        Ingredient.fromItems(Item.getItemFromBlock(Blocks.WATER)),
                        Ingredient.fromItems(Items.GLASS),
                        Ingredient.fromItems(Items.GLASS),
                        Ingredient.fromItems(Items.GLASS),
                        Ingredient.fromItems(Items.GLASS))
        ));
    }

    private static TileEntityType<?extends BaseTileEntity> generateType (Supplier<? extends BaseTileEntity> factoryIn, BaseBlock... blocks) {
        return TileEntityType.Builder.create(factoryIn, blocks).build(null);
    }

    /**
     * Register an item to ModObjects.
     * @param item A item instance to add.
     * @param id Unique identifier for the item.
     */
    private static void registerItem (BaseItem item, String id) {
        item.setRegistryName(id);
        items.add(item);
    }

    /**
     * Register a block to ModObjects.
     * @param block A block instance to add.
     * @param id Unique identifier for the block.
     */
    private static void registerBlock (BaseBlock block, String id) {
        block.setRegistryName(id);
        blocks.add(block);
    }

    /**
     * Register a tile entity to ModObjects.
     * @param tileEntityType A tile entity type to add.
     * @param id Unique identifier for the tile entity.
     */
    private static void registerTileEntity(TileEntityType<?extends BaseTileEntity> tileEntityType, String id) {
        tileEntityTypes.add(tileEntityType);
        tileEntityNames.add(id);
    }
}
