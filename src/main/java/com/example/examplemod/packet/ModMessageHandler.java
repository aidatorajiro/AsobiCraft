package com.example.examplemod.packet;

import com.example.examplemod.gui.ChunkChestContainer;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ModMessageHandler implements IMessageHandler<ModMessage, IMessage> {
    @Override
    public IMessage onMessage(ModMessage message, MessageContext ctx) {
        if (ctx.side == Side.SERVER){
            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer world = player.getServerWorld();
            if (message.type == ModMessage.TYPE_CHUNK_CHEST) {
                TileEntity tile = world.getTileEntity(message.chunkChestPos);
                if (tile instanceof ChunkChestTileEntity) {
                    ((ChunkChestTileEntity) tile).setPageNo(message.chunkChestPageNo);
                    if (player.openContainer instanceof ChunkChestContainer) {
                        ((ChunkChestContainer)player.openContainer).redraw();
                    }
                }
            }
        } else if (ctx.side == Side.CLIENT) {
        }
        return null;
    }
}
