package com.example.examplemod.eventhandler;

import com.example.examplemod.ModObjects;
import com.example.examplemod.helper.ItemHelper;
import com.example.examplemod.helper.WorldHelper;
import com.example.examplemod.packet.ModMessage;
import com.example.examplemod.packet.ModPacketHandler;
import com.example.examplemod.recipe.IBlockPatternRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Event handler for overwriting vanilla behavior.
 */
public class ModEventHandler {
    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        Item item = event.getItem().getItem().getItem();
        EntityPlayer player = event.getEntityPlayer();
        BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
        World world = player.getEntityWorld();
        for (IBlockPatternRecipe recipe : ModObjects.pickupRecipes) {
            Tuple<Boolean, Iterable<BlockPos>> result = recipe.triggerMatches(world, item, pos);
            if (result.getFirst()) {
                for (BlockPos deletePos : result.getSecond()) {
                    world.setBlockState(deletePos, Blocks.AIR.getDefaultState());
                }
                ModPacketHandler.INSTANCE.sendToAllAround(
                        new ModMessage().explosionMessage(pos),
                        new NetworkRegistry.TargetPoint(
                                world.provider.getDimension(),
                                pos.getX(),
                                pos.getY(),
                                pos.getZ(),
                                128
                        )
                );
                Explosion explosion = new Explosion(world, player, pos.getX(), pos.getY(), pos.getZ(), 2.0F, true, true);
                explosion.doExplosionB(true);
                ItemHelper.dropStackList(world, pos, recipe.getOutput(world, item, pos));
                return;
            }
        }
    }
}
