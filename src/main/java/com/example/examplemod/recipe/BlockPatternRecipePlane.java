package com.example.examplemod.recipe;

import com.example.examplemod.helper.ItemHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class BlockPatternRecipePlane implements IForgeRegistryEntry<IRecipe>, IBlockPatternRecipe, IShapedRecipe {
    protected Item triggerItem;
    protected int itemPosX;
    protected int itemPosY;
    protected int itemPosZ;
    protected NonNullList<ItemStack> output;
    protected int width;
    protected int height;
    protected NonNullList<Ingredient> triggerBlocks;
    protected ResourceLocation resourceLocation;

    @Override
    public IRecipe setRegistryName(ResourceLocation name) {
        resourceLocation = name;
        return this;
    }

    @Override
    public ResourceLocation getId() {
        return resourceLocation;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SHAPED;
    }

    @Override
    public IRecipeType<?> getType() {
        return IRecipeType.CRAFTING;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return resourceLocation;
    }

    @Override
    public Class<IRecipe> getRegistryType() {
        return null;
    }

    @Override
    public String getGroup() {
        return "pickup_recipe";
    }

    //public BlockPatternRecipePlane(Item triggerItem, int itemPosX, int itemPosY, int itemPosZ, NonNullList<ItemStack> output, Object... recipe) {
    //    this(triggerItem, itemPosX, itemPosY, itemPosZ, output, CraftingHelper.parseShaped(recipe));
    //}

    //public BlockPatternRecipePlane(Item triggerItem, int itemPosX, int itemPosY, int itemPosZ, NonNullList<ItemStack> output, ShapedPrimer primer) {
    //    this(triggerItem, itemPosX, itemPosY, itemPosZ, output, primer.width, primer.height, primer.input);
    //}

    public BlockPatternRecipePlane(Item triggerItem, int itemPosX, int itemPosY, int itemPosZ, NonNullList<ItemStack> output, int width, int height, NonNullList<Ingredient> triggerBlocks) {
        this.width = width;
        this.height = height;
        this.triggerItem = triggerItem;
        this.triggerBlocks = triggerBlocks;
        this.output = output;
        this.itemPosX = itemPosX;
        this.itemPosY = itemPosY;
        this.itemPosZ = itemPosZ;
    }

    /**
     * Get shape width
     * @return shape width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get shape height
     * @return shape height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the item which triggers output creation
     * @return trigger item (input item)
     */
    public Item getTriggerItem() {
        return triggerItem;
    }

    /**
     * Get the list of blocks which triggers output creation
     * @return trigger blocks (input blocks)
     */
    public NonNullList<Ingredient> getTriggerBlocks() {
        return triggerBlocks;
    }

    @Override
    public NonNullList<ItemStack> getOutput() {
        return ItemHelper.copyStackList(output);
    }

    @Override
    public NonNullList<ItemStack> getOutput(World world, Item itemIn, BlockPos triggerItemPos) {
        return ItemHelper.copyStackList(output);
    }

    @Override
    public Tuple<Boolean, Iterable<BlockPos>> triggerMatches(World world, Item itemIn, BlockPos triggerItemPos) {
        if (itemIn != triggerItem) {
            return new Tuple(false, null);
        }

        BlockPos origin = triggerItemPos.subtract(new BlockPos(itemPosX, itemPosY, itemPosZ));

        ArrayList<BlockPos> matchBlocks = new ArrayList();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                BlockPos currentPos = origin.add(new BlockPos(i, 0, j));
                int index = i + width*j;
                Item itemBlock = Item.getItemFromBlock(world.getBlockState(currentPos).getBlock());
                matchBlocks.add(currentPos);
                if (!triggerBlocks.get(index).test(new ItemStack(itemBlock))) {
                    return new Tuple(false, null);
                }
            }
        }

        return new Tuple(true, matchBlocks);
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width == this.width && height == this.height;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output.get(0);
    }

    @Override
    public int getRecipeWidth() {
        return width;
    }

    @Override
    public int getRecipeHeight() {
        return height;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        return triggerBlocks;
    }


}
