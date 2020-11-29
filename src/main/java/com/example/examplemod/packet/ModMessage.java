package com.example.examplemod.packet;

import com.example.examplemod.tileentity.CalculatorTileEntity;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ModMessage implements IMessage {
    public int type = 0;

    /* chunk chest */
    public static int TYPE_CHUNK_CHEST = 1;
    public BlockPos chunkChestPos;
    public int chunkChestPageNo;

    public ModMessage chunkChestMessage(BlockPos pos, int pageNo) {
        type = TYPE_CHUNK_CHEST;
        chunkChestPos = pos;
        chunkChestPageNo = pageNo;
        return this;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = buf.readInt();
        if (type == TYPE_CHUNK_CHEST) {
            chunkChestPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            chunkChestPageNo = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(type);
        if (type == TYPE_CHUNK_CHEST) {
            buf.writeInt(chunkChestPos.getX());
            buf.writeInt(chunkChestPos.getY());
            buf.writeInt(chunkChestPos.getZ());
            buf.writeInt(chunkChestPageNo);
        }
    }
}
