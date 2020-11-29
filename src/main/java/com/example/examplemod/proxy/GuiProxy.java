package com.example.examplemod.proxy;

import com.example.examplemod.container.CalculatorContainer;
import com.example.examplemod.container.ChunkChestContainer;
import com.example.examplemod.container.FloatingChestContainer;
import com.example.examplemod.gui.CalculatorGui;
import com.example.examplemod.gui.ChunkChestGui;
import com.example.examplemod.gui.FloatingChestGui;
import com.example.examplemod.tileentity.CalculatorTileEntity;
import com.example.examplemod.tileentity.ChunkChestTileEntity;
import com.example.examplemod.tileentity.FloatingChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof CalculatorTileEntity) {
            return new CalculatorContainer(player.inventory, (CalculatorTileEntity) tile);
        }
        if (tile instanceof FloatingChestTileEntity) {
            return new FloatingChestContainer(player.inventory, (FloatingChestTileEntity) tile);
        }
        if (tile instanceof ChunkChestTileEntity) {
            return new ChunkChestContainer(player.inventory, (ChunkChestTileEntity) tile);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof CalculatorTileEntity) {
            CalculatorTileEntity casted = (CalculatorTileEntity)tile;
            return new CalculatorGui(casted, new CalculatorContainer(player.inventory, casted));
        }
        if (tile instanceof FloatingChestTileEntity) {
            FloatingChestTileEntity casted = (FloatingChestTileEntity) tile;
            return new FloatingChestGui(casted, new FloatingChestContainer(player.inventory, casted));
        }
        if (tile instanceof ChunkChestTileEntity) {
            ChunkChestTileEntity casted = (ChunkChestTileEntity) tile;
            return new ChunkChestGui(casted, new ChunkChestContainer(player.inventory, casted));
        }
        return null;
    }
}
