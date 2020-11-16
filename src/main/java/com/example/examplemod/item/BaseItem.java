package com.example.examplemod.item;

import com.example.examplemod.ExampleMod;
import net.minecraft.item.Item;

import static com.example.examplemod.ModObjects.itemGroupExampleMod;

public abstract class BaseItem extends Item {
    public BaseItem () {
        super(new Item.Properties().group(itemGroupExampleMod));
    }
}
