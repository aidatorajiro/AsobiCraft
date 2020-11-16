package com.example.examplemod.item;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.ModObjects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public abstract class BaseItem extends Item {
    public BaseItem (Item.Properties properties) {
        super(properties);
    }
    public BaseItem () {
        super(new Item.Properties().group(ModObjects.itemGroupExampleMod));
    }
}
