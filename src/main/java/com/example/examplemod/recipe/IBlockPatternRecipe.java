package com.example.examplemod.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public interface IBlockPatternRecipe {
    /**
     * Check whether given World, Item, BlockPos (position of the item) matches to trigger item creation.
     * Returns a tuple of match result (boolean) and a set of positions of blocks that matches (Iterable<BlockPos>).
     * Must be pure function, making no changes to World or Item.
     * @return test result
     */
    public Tuple<Boolean, Iterable<BlockPos>> triggerMatches(World world, Item itemIn, BlockPos triggerItemPos);

    /**
     * Get output of this recipe
     * @return output, multiple ItemStack
     */
    public NonNullList<ItemStack> getOutput();

    /**
     * Get output of this recipe
     * @return output, multiple ItemStack
     */
    public NonNullList<ItemStack> getOutput(World world, Item itemIn, BlockPos triggerItemPos);
}
