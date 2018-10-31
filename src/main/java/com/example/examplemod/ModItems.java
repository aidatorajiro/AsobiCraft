package com.example.examplemod;

import com.example.examplemod.item.BaseItem;
import com.example.examplemod.item.ExampleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A registry for items.
 */
public class ModItems {
    public static List<BaseItem> items = new ArrayList();

    public static ExampleItem exampleItem;

    /**
     * Create and register all items to ModItems.
     */
    public static void initialize() {
        exampleItem = new ExampleItem();
        registerItem(exampleItem, "exampleitem");
    }

    /**
     * Register an item to ModItems.
     * @param item A item instance to add.
     * @param id Unique identifier for the item.
     */
    private static void registerItem (BaseItem item, String id) {
        item.setRegistryName(id);
        item.setUnlocalizedName(ExampleMod.MODID + "." + id);
        items.add(item);
    }
}
