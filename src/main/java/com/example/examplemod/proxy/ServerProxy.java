package com.example.examplemod.proxy;

import com.example.examplemod.packet.ModMessage;
import com.example.examplemod.packet.ModMessageHandlerServer;
import com.example.examplemod.packet.ModPacketHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.SERVER)
public class ServerProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }
}