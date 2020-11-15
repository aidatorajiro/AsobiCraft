package com.example.examplemod.block;

import com.example.examplemod.item.NumberItem;
import com.example.examplemod.tileentity.CounterTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

import static com.example.examplemod.ModObjects.numberItem;

public class CounterBlock extends BaseBlock {
    public CounterBlock() {
        super();
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return return new CounterTileEntity();
    }


    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult res) {
        if (!world.isRemote) {
            int counter = ((CounterTileEntity) world.getTileEntity(pos)).increase();
            TranslationTextComponent component = new TranslationTextComponent("message.examplemod.counter", counter);
            component.getStyle().setColor(Color.fromInt(0x00ff00));
            entity.sendStatusMessage(component, false);
            return ActionResultType.CONSUME;
        } else {
            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void spawnAdditionalDrops(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        super.spawnAdditionalDrops( state,  world,  pos,  stack);
        int counter = ((CounterTileEntity) world.getTileEntity(pos)).getValue();
        ItemStack item = new ItemStack(numberItem);
        numberItem.setNumber(item, (double) counter);
        world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), item));
    }
}
