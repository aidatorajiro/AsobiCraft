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
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModMessageHandlerServer implements IMessageHandler<ModMessage, IMessage> {
    @Override
    public IMessage onMessage(ModMessage message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        WorldServer world = player.getServerWorld();
        if (message.type == ModMessage.TYPE_CHUNK_CHEST) {
            TileEntity tile = world.getTileEntity(message.blockPos);
            if (tile instanceof ChunkChestTileEntity) {
                ((ChunkChestTileEntity) tile).setPageNo(player, message.pageNo);
                if (player.openContainer instanceof ChunkChestContainer) {
                    ((ChunkChestContainer)player.openContainer).redraw();
                }
            }
        }
        return null;
    }
}
