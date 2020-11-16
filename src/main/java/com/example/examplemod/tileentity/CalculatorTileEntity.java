package com.example.examplemod.tileentity;

import com.example.examplemod.item.NumberItem;
import com.example.examplemod.item.OperatorItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static com.example.examplemod.ModObjects.numberItem;

public class CalculatorTileEntity extends BaseTileEntity implements ITickableTileEntity {
    public int INPUT_SIZE = 16;
    public int OUTPUT_SIZE = 48;
    public int current_operator = 0;

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
                return stack;
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

    public int getCurrentOperatorIndex() {
        return current_operator;
    }

    @Override
    public void readFromNBT(CompoundNBT compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("input")) {
            input.deserializeNBT((CompoundNBT) compound.getTag("input"));
        }
        if (compound.hasKey("output")) {
            output.deserializeNBT((CompoundNBT) compound.getTag("output"));
        }
        if (compound.hasKey("current_operator")) {
            current_operator = compound.getInteger("current_operator");
        }
    }

    @Override
    public CompoundNBT writeToNBT(CompoundNBT compound) {
        super.writeToNBT(compound);
        compound.setTag("input", input.serializeNBT());
        compound.setTag("output", output.serializeNBT());
        compound.setTag("current_operator", new NBTTagInt(current_operator));
        return compound;
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

    public boolean canCalculate() {
        for (int i = 0; i < input.getSlots(); i++) {
            if (input.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void updateTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (!canCalculate()) {
            return;
        }

        int index_prev = (current_operator - 1 + INPUT_SIZE) % INPUT_SIZE;
        int index_next = (current_operator + 1 + INPUT_SIZE) % INPUT_SIZE;
        int index_replace = (current_operator + 3 + INPUT_SIZE) % INPUT_SIZE;

        ItemStack num_a = input.getStackInSlot(index_prev);
        ItemStack num_b = input.getStackInSlot(index_next);
        ItemStack operator = input.getStackInSlot(current_operator % INPUT_SIZE);

        ItemStack result = numberItem.applyOperator(num_a, num_b, (OperatorItem) operator.getItem());
        result.setCount(input.getStackInSlot(index_replace).getCount());

        input.setStackInSlot(index_replace, result);
        current_operator += 2;
    }
}
