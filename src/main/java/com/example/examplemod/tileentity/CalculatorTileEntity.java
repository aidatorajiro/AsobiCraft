package com.example.examplemod.tileentity;

import com.example.examplemod.item.NumberItem;
import com.example.examplemod.item.OperatorItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static com.example.examplemod.ModObjects.calculatorType;
import static com.example.examplemod.ModObjects.numberItem;

public class CalculatorTileEntity extends BaseTileEntity implements ITickableTileEntity, ICapabilityProvider {
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

    public CalculatorTileEntity() {
        super(calculatorType);
    }

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
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        if (compound.contains("input")) {
            input.deserializeNBT((CompoundNBT) compound.getCompound("input"));
        }
        if (compound.contains("output")) {
            output.deserializeNBT((CompoundNBT) compound.getCompound("output"));
        }
        if (compound.contains("current_operator")) {
            current_operator = compound.getInt("current_operator");
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("input", input.serializeNBT());
        compound.put("output", output.serializeNBT());
        compound.putInt("current_operator", current_operator);
        return compound;
    }

    /* TODO
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable Direction facing) {
        if (facing == Direction.UP && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if (facing == Direction.DOWN && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }*/

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (facing == Direction.UP && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> input).cast();
        }
        if (facing == Direction.DOWN && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> output).cast();
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

    @Override
    public void tick() {
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
