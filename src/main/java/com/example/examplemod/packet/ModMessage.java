package com.example.examplemod.packet;

import com.example.examplemod.tileentity.CalculatorTileEntity;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ModMessage implements IMessage {
    public int type = 0;
    /* frequently used arguments */
    public BlockPos blockPos;

    /* chunk chest */
    public static int TYPE_CHUNK_CHEST = 1;
    public int pageNo;

    /* particle */
    public static int TYPE_EXPLOSION = 2;

    public ModMessage chunkChestMessage(BlockPos pos, int no) {
        type = TYPE_CHUNK_CHEST;
        blockPos = pos;
        pageNo = no;
        return this;
    }

    public ModMessage explosionMessage(BlockPos pos) {
        type = TYPE_EXPLOSION;
        blockPos = pos;
        return this;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = buf.readInt();
        if (type == TYPE_CHUNK_CHEST) {
            blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            pageNo = buf.readInt();
        }
        if (type == TYPE_EXPLOSION) {
            blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(type);
        if (type == TYPE_CHUNK_CHEST) {
            buf.writeInt(blockPos.getX());
            buf.writeInt(blockPos.getY());
            buf.writeInt(blockPos.getZ());
            buf.writeInt(pageNo);
        }
        if (type == TYPE_EXPLOSION) {
            buf.writeInt(blockPos.getX());
            buf.writeInt(blockPos.getY());
            buf.writeInt(blockPos.getZ());
        }
    }
}
