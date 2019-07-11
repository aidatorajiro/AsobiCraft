package com.example.examplemod.tileentity;

import com.example.examplemod.item.NumberItem;
import com.example.examplemod.item.OperatorItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class CalculatorTileEntity extends BaseTileEntity {
    public int INPUT_SIZE = 16;
    public int OUTPUT_SIZE = 48;

    private ItemStackHandler input = new ItemStackHandler(INPUT_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            CalculatorTileEntity.this.markDirty();
        }

        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (isItemValid(slot, stack)) {
                return super.insertItem(slot, stack, simulate);
            } else {
                return ItemStack.EMPTY;
            }
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot % 2 == 0 && stack.getItem() instanceof OperatorItem) {
                return true;
            }
            if (slot % 2 == 1 && stack.getItem() instanceof NumberItem) {
                return true;
            }
            return false;
        }
    };

    private ItemStackHandler output = new ItemStackHandler(OUTPUT_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            CalculatorTileEntity.this.markDirty();
        }
    };

    public ItemStackHandler getInput () {
        return input;
    }

    public ItemStackHandler getOutput () {
        return output;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("input")) {
            input.deserializeNBT((NBTTagCompound) compound.getTag("input"));
        }
        if (compound.hasKey("output")) {
            output.deserializeNBT((NBTTagCompound) compound.getTag("output"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("input", input.serializeNBT());
        compound.setTag("output", output.serializeNBT());
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing == EnumFacing.UP && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if (facing == EnumFacing.DOWN && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing == EnumFacing.UP && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T)input;
        }
        if (facing == EnumFacing.DOWN && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T)output;
        }
        return super.getCapability(capability, facing);
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

    }
}
