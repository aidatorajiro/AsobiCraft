package com.example.examplemod.item;

import java.util.function.BiFunction;

public class OperatorItem extends BaseItem {
    private BiFunction<Double,Double,Double> func;
    public OperatorItem (BiFunction<Double,Double,Double> func) {
        super();
        this.func = func;
    }
    public double calculate (double a, double b) {
        return func.apply(a, b);
    }
}
