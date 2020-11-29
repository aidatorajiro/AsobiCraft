package com.example.examplemod.packet;

import com.example.examplemod.tileentity.CalculatorTileEntity;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ModMessage implements IMessage {
    public int type = 0;

    @Override
    public void fromBytes(ByteBuf buf) {
        type = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(type);
    }
}
