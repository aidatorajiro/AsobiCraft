package com.example.examplemod.block;

import com.example.examplemod.item.NumberItem;
import com.example.examplemod.tileentity.CounterTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static com.example.examplemod.ModObjects.numberItem;

public class CounterBlock extends BaseBlock implements ITileEntityProvider {
    public CounterBlock() {
        super(Material.ROCK);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new CounterTileEntity();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            int counter = ((CounterTileEntity) worldIn.getTileEntity(pos)).increase();
            TextComponentTranslation component = new TextComponentTranslation("message.examplemod.counter", counter);
            component.getStyle().setColor(TextFormatting.GREEN);
            playerIn.sendStatusMessage(component, false);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            int counter = ((CounterTileEntity) world.getTileEntity(pos)).getValue();
            ItemStack item = new ItemStack(numberItem);
            numberItem.setNumber(item, (double) counter);
            world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), item));
        }
        super.breakBlock(world, pos, state);
    }
}
