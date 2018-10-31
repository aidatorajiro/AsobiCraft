package com.example.examplemod;

import com.example.examplemod.block.*;
import com.example.examplemod.item.BaseItem;
import com.example.examplemod.item.ExampleItem;
import com.example.examplemod.item.OperatorItem;
import com.example.examplemod.tileentity.BaseTileEntity;
import com.example.examplemod.tileentity.CalculatorTileEntity;
import com.example.examplemod.tileentity.CounterTileEntity;
import com.example.examplemod.tileentity.StateManipulatorTileEntity;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.fml.common.registry.GameRegistry.registerTileEntity;

/**
 * A registry for mod objects.
 */
public class ModObjects {
    public static List<BaseBlock> blocks = new ArrayList();
    public static ExampleBlock exampleBlock;
    public static CounterBlock counterBlock;
    public static StateManipulatorBlock stateManipulatorBlock;
    public static CalculatorBlock calculatorBlock;

    public static List<Class<?extends BaseTileEntity>> tileEntities = new ArrayList();
    public static List<String> tileEntityNames = new ArrayList();

    public static final CreativeTabs tabExampleMod = (new CreativeTabs("tabExampleMod") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(exampleItem);
        }
    });

    public static List<BaseItem> items = new ArrayList();

    public static ExampleItem exampleItem;
    public static OperatorItem plusOperatorItem;
    public static OperatorItem minusOperatorItem;
    public static OperatorItem divOperatorItem;
    public static OperatorItem mulOperatorItem;

    /**
     * Create and register all items to ModObjects.
     */
    public static void initialize() {
        exampleItem = new ExampleItem();
        registerItem(exampleItem, "exampleItem");

        plusOperatorItem = new OperatorItem((a, b) -> (a + b));
        registerItem(plusOperatorItem, "plusOperatorItem");

        minusOperatorItem = new OperatorItem((a, b) -> (a - b));
        registerItem(minusOperatorItem, "minusOperatorItem");

        divOperatorItem = new OperatorItem((a, b) -> (a / b));
        registerItem(divOperatorItem, "divOperatorItem");

        mulOperatorItem = new OperatorItem((a, b) -> (a * b));
        registerItem(mulOperatorItem, "mulOperatorItem");

        exampleBlock = new ExampleBlock();
        registerBlock(exampleBlock, "exampleBlock");

        counterBlock = new CounterBlock();
        registerBlock(counterBlock, "counterBlock");
        registerTileEntity(CounterTileEntity.class, "counterTileEntity");

        stateManipulatorBlock = new StateManipulatorBlock();
        registerBlock(stateManipulatorBlock, "stateManipulatorBlock");
        registerTileEntity(StateManipulatorTileEntity.class, "stateManipulatorTileEntity");

        calculatorBlock = new CalculatorBlock();
        registerBlock(calculatorBlock, "calculatorBlock");
        registerTileEntity(CalculatorTileEntity.class, "calculatorTileEntity");
    }

    /**
     * Register an item to ModObjects.
     * @param item A item instance to add.
     * @param id Unique identifier for the item.
     */
    private static void registerItem (BaseItem item, String id) {
        item.setRegistryName(id);
        item.setUnlocalizedName(ExampleMod.MODID + "." + id);
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
