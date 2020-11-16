package com.example.examplemod.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import java.lang.Double;
import java.util.function.BiFunction;

import com.example.examplemod.ModObjects;
import net.minecraftforge.fml.common.Mod;

public class NumberItem extends BaseItem {
    public NumberItem () {
        super();
    }

    public double getNumber(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (nbt == null) {
            nbt = new CompoundNBT();
            stack.setTag(nbt);
        }
        if (!nbt.contains("number")) {
            nbt.putDouble("number", 0);
        }
        if (!nbt.contains("isNaN")) {
            nbt.putBoolean("isNaN", false);
        }
        if (Double.isNaN(nbt.getDouble("number"))) {
            nbt.putDouble("number", 0);
            nbt.putBoolean("isNaN", true);
        }
        if (nbt.getBoolean("isNaN") && nbt.getDouble("number") != 0) {
            nbt.putDouble("number", 0);
        }
        if (nbt.getBoolean("isNaN")) {
            return Double.NaN;
        } else {
            return nbt.getDouble("number");
        }
    }

    public void setNumber(ItemStack stack, double number) {
        CompoundNBT nbt = stack.getTag();
        if (nbt == null) {
            nbt = new CompoundNBT();
            stack.setTag(nbt);
        }
        if (Double.isNaN(number)) {
            nbt.putDouble("number", 0);
            nbt.putBoolean("isNaN", true);
        } else {
            nbt.putDouble("number", number);
            nbt.putBoolean("isNaN", false);
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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        double num = getNumber(stack);
        tooltip.add(new TranslationTextComponent("message.examplemod.statemanipulator", num));
    }
}
