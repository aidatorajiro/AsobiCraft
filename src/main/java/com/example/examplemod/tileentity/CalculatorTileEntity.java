package com.example.examplemod.tileentity;

import com.example.examplemod.helper.ItemHandlerHelper;
import com.example.examplemod.item.NumberItem;
import com.example.examplemod.item.OperatorItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class CalculatorTileEntity extends BaseTileEntity implements IInventory {
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

    @Override
    public int getSizeInventory() {
        return INPUT_SIZE + OUTPUT_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return ItemHandlerHelper.isEmpty(input) && ItemHandlerHelper.isEmpty(output);
    }

    @Override
    public ItemStack getStackInSlot(int prev_index) {
        return ItemHandlerHelper.getStackInSlotMerged(prev_index, input, output);
    }

    @Override
    public ItemStack decrStackSize(int prev_index, int num) {
        return ItemHandlerHelper.extractItemMerged(prev_index, num, false, input, output);
    }

    @Override
    public ItemStack removeStackFromSlot(int prev_index) {
        return ItemHandlerHelper.clearSlotMerged(prev_index, input, output);
    }

    @Override
    public void setInventorySlotContents(int prev_index, ItemStack itemStack) {
         ItemHandlerHelper.setSlotMerged(prev_index, itemStack, input, output);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer entityPlayer) {

    }

    @Override
    public void closeInventory(EntityPlayer entityPlayer) {

    }

    @Override
    public boolean isItemValidForSlot(int prev_index, ItemStack itemStack) {
        return ItemHandlerHelper.isItemValidMerged(prev_index, itemStack, input, output);
    }

    @Override
    public int getField(int i) {
        return 0;
    }

    @Override
    public void setField(int i, int i1) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        ItemHandlerHelper.clear(input);
        ItemHandlerHelper.clear(output);
    }

    @Override
    public String getName() {
        return "container.calculator";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
