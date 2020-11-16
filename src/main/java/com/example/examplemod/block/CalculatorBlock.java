package com.example.examplemod.block;

import com.example.examplemod.helper.BlockHelper;
import com.example.examplemod.tileentity.CalculatorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class CalculatorBlock extends BaseBlock {
    public CalculatorBlock() {
        super();
    }

    @Override
    public boolean ticksRandomly(BlockState p_149653_1_) {
        return true;
    }

    @Override
    public void onBlockAdded(BlockState p_220082_1_, World worldIn, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
        super.onBlockAdded(p_220082_1_, worldIn, pos, p_220082_4_, p_220082_5_);
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, 10);
    }

    /*
        Tile entity
     */

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CalculatorTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    /*
        GUI
     */

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult res) {
        return BlockHelper.onBlockActivatedNormal(this, state, world, pos, entity, hand, res);
    }

    /*
        Tick
     */

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        super.tick(state, world, pos, rand);
        CalculatorTileEntity tile = (CalculatorTileEntity) world.getTileEntity(pos);
        world.getPendingBlockTicks().scheduleTick(pos, this, 10); // TODO
        // tile.updateTick(state, world, pos, rand); TODO
    }

    /*
        Partial Block
     */

    //@Override  TODO
    //public boolean isOpaqueCube(IBlockState state) {
    //    return false;
    //}

    //@Override  TODO
    //public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    //    return true;
    //}

    //@Override  TODO
    //public boolean isFullCube(IBlockState state) {
    //    return false;
    //}

    //@Override  TODO
    //public BlockRenderLayer getBlockLayer() {
    //    return BlockRenderLayer.CUTOUT;
    //}

    //@Override  TODO
    //public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    //    return new AxisAlignedBB(1.0/16.0, 0, 1.0/16.0, 15.0/16.0, 2.0/16.0, 15.0/16.0);
    //}

    /*
        NBT Items
     */

    //@Override  TODO
    //public int quantityDropped(IBlockState state, int fortune, Random random) {
    //    if (fortune > 9) {
    //        return 1;
    //    } else {
    //        return 0;
    //    }
    //}


    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        BlockHelper.restoreTE(world, pos, stack);
    }

    @Override
    public void spawnAdditionalDrops(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        super.spawnAdditionalDrops(state, world, pos, stack);
        if (!world.isRemote) {
            BlockHelper.spawnTE(world, pos, this);
        }
    }
}
