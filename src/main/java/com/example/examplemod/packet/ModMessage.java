package com.example.examplemod.packet;

import com.example.examplemod.tileentity.CalculatorTileEntity;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ModMessage implements IMessage {
    public int type = 0;
    /* frequently used arguments */
    public BlockPos blockPos;
    public NBTTagCompound nbtData;

    /* chunk chest */
    public static int TYPE_CHUNK_CHEST = 1;
    public int pageNo;
    public int maxPages;

    /* explosion */
    public static int TYPE_EXPLOSION = 2;

    /* NBT */
    public static int TYPE_FLOATING_CHEST = 3;

    public ModMessage chunkChestMessage(BlockPos pos, int no, int max) {
        type = TYPE_CHUNK_CHEST;
        blockPos = pos;
        pageNo = no;
        maxPages = max;
        return this;
    }

    public ModMessage explosionMessage(BlockPos pos) {
        type = TYPE_EXPLOSION;
        blockPos = pos;
        return this;
    }

    public ModMessage floatingChestMessage(BlockPos pos, NBTTagCompound tag) {
        type = TYPE_FLOATING_CHEST;
        blockPos = pos;
        nbtData = tag;
        return this;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = buf.readInt();
        blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        if (type == TYPE_CHUNK_CHEST) {
            pageNo = buf.readInt();
            maxPages = buf.readInt();
        }
        if (type == TYPE_EXPLOSION) {
        }
        if (type == TYPE_FLOATING_CHEST) {
            nbtData = ByteBufUtils.readTag(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(type);
        buf.writeInt(blockPos.getX());
        buf.writeInt(blockPos.getY());
        buf.writeInt(blockPos.getZ());
        if (type == TYPE_CHUNK_CHEST) {
            buf.writeInt(pageNo);
            buf.writeInt(maxPages);
        }
        if (type == TYPE_EXPLOSION) {
        }
        if (type == TYPE_FLOATING_CHEST) {
            ByteBufUtils.writeTag(buf, nbtData);
        }
    }
}
