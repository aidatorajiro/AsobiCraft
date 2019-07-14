package com.example.examplemod.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import java.lang.Double;
import java.util.function.BiFunction;

import com.example.examplemod.ModObjects;
import net.minecraftforge.fml.common.Mod;

public class NumberItem extends BaseItem {
    public NumberItem () {
    }

    public double getNumber(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        if (!nbt.hasKey("number")) {
            nbt.setDouble("number", 0);
        }
        if (!nbt.hasKey("isNaN")) {
            nbt.setBoolean("isNaN", false);
        }
        if (Double.isNaN(nbt.getDouble("number"))) {
            nbt.setDouble("number", 0);
            nbt.setBoolean("isNaN", true);
        }
        if (nbt.getBoolean("isNaN") == true && nbt.getDouble("number") != 0) {
            nbt.setDouble("number", 0);
        }
        if (nbt.getBoolean("isNaN") == true) {
            return Double.NaN;
        } else {
            return nbt.getDouble("number");
        }
    }

    public void setNumber(ItemStack stack, double number) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        if (Double.isNaN(number)) {
            nbt.setDouble("number", 0);
            nbt.setBoolean("isNaN", true);
        } else {
            nbt.setDouble("number", number);
            nbt.setBoolean("isNaN", false);
        }
    }

    public ItemStack applyOperator (ItemStack stack1, ItemStack stack2, OperatorItem operator) {
        double num1 = getNumber(stack1);
        double num2 = getNumber(stack2);
        double num_result = operator.calculate(num1, num2);

        ItemStack stack_result = new ItemStack(this);
        setNumber(stack_result, num_result);
        return stack_result;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        double num = getNumber(stack);
        tooltip.add("Stored number: " + num);
    }
}
