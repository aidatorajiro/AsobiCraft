package com.example.examplemod.packet;

import com.example.examplemod.gui.ChunkChestContainer;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import com.example.examplemod.tileentity.FloatingChestTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModMessageHandlerClient implements IMessageHandler<ModMessage, IMessage> {
    @Override
    public IMessage onMessage(ModMessage message, MessageContext ctx) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        World world = Minecraft.getMinecraft().world;
        if (message.type == ModMessage.TYPE_CHUNK_CHEST) {
            TileEntity tile = world.getTileEntity(message.blockPos);
            if (tile instanceof ChunkChestTileEntity) {
                ((ChunkChestTileEntity) tile).setPageNo(player, message.pageNo);
                ((ChunkChestTileEntity) tile).setHandlerSize(message.maxPages);
            }
        }
        if (message.type == ModMessage.TYPE_FLOATING_CHEST) {
            TileEntity tile = world.getTileEntity(message.blockPos);
            if (tile instanceof FloatingChestTileEntity) {
                ((FloatingChestTileEntity) tile).readChest(message.nbtData);
            }
        }
        if (message.type == ModMessage.TYPE_EXPLOSION) {
            Explosion explosion = new Explosion(world, player, message.blockPos.getX(), message.blockPos.getY(), message.blockPos.getZ(), 2.0F, true, true);
            explosion.doExplosionB(true);
        }
        return null;
    }
}
