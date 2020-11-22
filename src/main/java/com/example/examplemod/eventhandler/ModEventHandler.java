package com.example.examplemod.eventhandler;

import com.example.examplemod.ModObjects;
import com.example.examplemod.helper.ItemHelper;
import com.example.examplemod.helper.WorldHelper;
import com.example.examplemod.recipe.IBlockPatternRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;

/**
 * Event handler for overwriting vanilla behavior.
 */
public class ModEventHandler {
    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        Item item = event.getItem().getItem().getItem();
        EntityPlayer player = event.getEntityPlayer();
        BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
        World world = event.getEntityPlayer().getEntityWorld();
        for (IBlockPatternRecipe recipe : ModObjects.pickupRecipes) {
            Tuple<Boolean, Iterable<BlockPos>> result = recipe.triggerMatches(world, item, pos);
            if (result.getFirst()) {
                for (BlockPos delatePos : result.getSecond()) {
                    world.setBlockState(delatePos, Blocks.AIR.getDefaultState());
                }
                world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 1.0D, 1.0D, 1.0D);
                ItemHelper.dropStackList(world, pos, recipe.getOutput(world, item, pos));
                return;
            }
        }
    }
}
