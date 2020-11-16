package com.example.examplemod.eventhandler;

import com.example.examplemod.ModObjects;
import com.example.examplemod.helper.ItemHelper;
import com.example.examplemod.recipe.IBlockPatternRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Event handler for overwriting vanilla behavior.
 */
public class BlockPatternRecipeEventHandler {
    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        Item item = event.getItem().getItem().getItem();
        PlayerEntity player = event.getPlayer();
        BlockPos pos = new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ());
        World world = event.getPlayer().getEntityWorld();
        for (IBlockPatternRecipe recipe : ModObjects.pickupRecipes) {
            Tuple<Boolean, Iterable<BlockPos>> result = recipe.triggerMatches(world, item, pos);
            if (result.getA()) {
                for (BlockPos delatePos : result.getB()) {
                    world.setBlockState(delatePos, Blocks.AIR.getDefaultState());
                }
                world.addParticle(ParticleTypes.EXPLOSION_EMITTER, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 1.0D, 0.0D, 0.0D);
                ItemHelper.dropStackList(world, pos, recipe.getOutput(world, item, pos));
                return;
            }
        }
    }
}
