/*

package com.example.examplemod.proxy;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.ModObjects;
import com.example.examplemod.block.BaseBlock;
import com.example.examplemod.item.BaseItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;

public class CommonProxy {
    public void preInit(FMLCommonSetupEvent event) {
        ModObjects.initialize();
    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.registerGuiHandler(ExampleMod.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent event) {
        for (int i = 0; i < ModObjects.tileEntities.size(); i++) {
            GameRegistry.registerTileEntity(ModObjects.tileEntities.get(i), new ResourceLocation(ExampleMod.MODID, ModObjects.tileEntityNames.get(i)));
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for (BaseBlock block : ModObjects.blocks) {
            event.getRegistry().register(block);
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (BaseBlock block : ModObjects.blocks) {
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
        for (BaseItem item : ModObjects.items) {
            event.getRegistry().register(item);
        }
    }
}
*/