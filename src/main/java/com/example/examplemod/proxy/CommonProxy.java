package com.example.examplemod.proxy;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.ModObjects;
import com.example.examplemod.block.BaseBlock;
import com.example.examplemod.item.BaseItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ModObjects.initialize();
    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ExampleMod.instance, new GuiProxy());
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
        ModObjects.afterRegisterItems();
    }
}
