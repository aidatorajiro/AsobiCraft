package com.example.examplemod.proxy;

import com.example.examplemod.ModObjects;
import com.example.examplemod.block.BaseBlock;
import com.example.examplemod.item.BaseItem;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (BaseBlock block : ModObjects.blocks) {
            block.initModel();
        }

        for (BaseItem item : ModObjects.items) {
            item.initModel();
        }
    }
}