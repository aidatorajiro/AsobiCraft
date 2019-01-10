package com.example.examplemod.proxy;

import com.example.examplemod.container.CalculatorContainer;
import com.example.examplemod.gui.CalculatorGui;
import com.example.examplemod.tileentity.CalculatorTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiProxy implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof CalculatorTileEntity) {
            return new CalculatorContainer(player.inventory, (CalculatorTileEntity) tile);
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
        return null;
    }
}
